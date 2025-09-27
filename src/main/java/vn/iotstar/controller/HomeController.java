package vn.iotstar.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.iotstar.service.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	private final FileStorageService fileStorageService;

	public HomeController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@GetMapping("/")
	public String home() {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String index() {
		return "home";
	}

	@GetMapping("/uploads/{fileName:.+}")
	public ResponseEntity<Resource> serveFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		String contentType = "application/octet-stream";
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			// Log error or ignore
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}