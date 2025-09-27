package vn.iotstar.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public List<Product> getProductsByPrice() {
		return productRepository.findAllByOrderByPriceAsc();
	}

	public List<Product> getProductsByCategory(Long categoryId) {
		return productRepository.findByCategoryId(categoryId);
	}

	public Product createProduct(String title, int quantity, String desc, double price, Long userId, Long categoryId) {
		Product product = new Product();
		product.setTitle(title);
		product.setQuantity(quantity);
		product.setDesc(desc);
		product.setPrice(price);

		User user = userRepository.findById(userId).orElse(null);
		Category category = categoryRepository.findById(categoryId).orElse(null);

		if (user == null || category == null) {
			return null;
		}

		product.setUser(user);
		product.setCategory(category);

		return productRepository.save(product);
	}

	public Product updateProduct(Long id, String title, int quantity, String desc, double price, Long userId,
			Long categoryId) {
		Product product = productRepository.findById(id).orElse(null);
		if (product != null) {
			if (title != null) {
				product.setTitle(title);
			}
			product.setQuantity(quantity);
			if (desc != null) {
				product.setDesc(desc);
			}
			product.setPrice(price);

			if (userId != null) {
				User user = userRepository.findById(userId).orElse(null);
				if (user != null) {
					product.setUser(user);
				}
			}

			if (categoryId != null) {
				Category category = categoryRepository.findById(categoryId).orElse(null);
				if (category != null) {
					product.setCategory(category);
				}
			}

			return productRepository.save(product);
		}
		return null;
	}

	public boolean deleteProduct(Long id) {
		productRepository.deleteById(id);
		return true;
	}
}