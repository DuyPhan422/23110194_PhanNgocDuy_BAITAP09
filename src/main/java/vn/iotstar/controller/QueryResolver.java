package vn.iotstar.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
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
public class QueryResolver implements GraphQLQueryResolver {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public QueryResolver(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> products() {
        return productRepository.findAll();
    }

    public Product product(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<User> users() {
        return userRepository.findAll();
    }

    public User user(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Category> categories() {
        return categoryRepository.findAll();
    }

    public Category category(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}