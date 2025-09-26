package vn.iotstar.controller;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;

import java.util.List;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public MutationResolver(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public User createUser(String email, String fullname, String password, String phone) {
        User user = new User();
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPassword(password); // Lưu ý: Nên mã hóa mật khẩu trong thực tế
        user.setPhone(phone);
        return userRepository.save(user);
    }

    public Product createProduct(String title, int quantity, String description, float price, Long userId, List<Long> categoryIds) {
        Product product = new Product();
        product.setTitle(title);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setPrice(price);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        product.setUser(user);

        if (categoryIds != null) {
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            product.setCategories(categories);
        }

        return productRepository.save(product);
    }

    public Category createCategory(String name, String images) {
        Category category = new Category();
        category.setName(name);
        category.setImages(images);
        return categoryRepository.save(category);
    }
}