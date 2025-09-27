package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import vn.iotstar.entity.Product;
import vn.iotstar.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @QueryMapping
    public List<Product> products() {
        return productService.getProducts();
    }

    @QueryMapping
    public Product product(@Argument Long id) {
        return productService.getProduct(id);
    }

    @QueryMapping
    public List<Product> productsByPrice() {
        return productService.getProductsByPrice();
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @MutationMapping
    public Product createProduct(@Argument String title, @Argument int quantity, @Argument String desc, @Argument double price, @Argument Long userId, @Argument Long categoryId) {
        return productService.createProduct(title, quantity, desc, price, userId, categoryId);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String title, @Argument int quantity, @Argument String desc, @Argument double price, @Argument Long userId, @Argument Long categoryId) {
        return productService.updateProduct(id, title, quantity, desc, price, userId, categoryId);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}
