package vn.iotstar.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;
	private final ProductRepository productRepo;

	public DataLoader(UserRepository userRepo, CategoryRepository categoryRepo, ProductRepository productRepo) {
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
		this.productRepo = productRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		// Create Users if not exist
		if (userRepo.count() == 0) {
			User user1 = new User();
			user1.setFullname("Admin User");
			user1.setEmail("admin@example.com");
			user1.setPassword("password");
			user1.setPhone("123456789");

			User user2 = new User();
			user2.setFullname("Regular User");
			user2.setEmail("user@example.com");
			user2.setPassword("password");
			user2.setPhone("987654321");

			userRepo.saveAll(List.of(user1, user2));
			System.out.println("Created initial users.");
		}

		if (categoryRepo.count() == 0) {
			Category electronics = new Category();
			electronics.setName("Electronics");
			electronics.setImages("electronics.png");

			Category books = new Category();
			books.setName("Books");
			books.setImages("books.png");

			categoryRepo.saveAll(List.of(electronics, books));
			System.out.println("Created initial categories.");
		}

		if (productRepo.count() == 0) {
			List<Category> categories = categoryRepo.findAll();
			List<User> users = userRepo.findAll();

			User productOwner = users.stream().findFirst().orElse(null);

			if (productOwner == null) {
				System.out.println("Could not find any user to assign products.");
				return;
			}

			Category electronics = categories.stream().filter(c -> c.getName().equals("Electronics")).findFirst()
					.orElse(null);
			Category books = categories.stream().filter(c -> c.getName().equals("Books")).findFirst().orElse(null);

			if (electronics != null) {
				productRepo.save(new Product(null, "Laptop Dell XPS", 100, "A high-end laptop", 25000000.0,
						productOwner, electronics));
				productRepo.save(new Product(null, "Samsung Galaxy S23", 200, "Latest Samsung phone", 15000000.0,
						productOwner, electronics));
			}
			if (books != null) {
				productRepo.save(new Product(null, "The Hobbit", 50, "Fantasy novel by J.R.R. Tolkien", 350000.0,
						productOwner, books));
			}
			System.out.println("Created initial products.");
		}
	}
}