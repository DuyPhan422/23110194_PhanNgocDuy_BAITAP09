package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @QueryMapping
    public List<Category> categories() {
        return categoryService.getCategories();
    }

    @QueryMapping
    public Category category(@Argument Long id) {
        return categoryService.getCategory(id);
    }

    @MutationMapping
    public Category createCategory(@Argument String name, @Argument String images) {
        return categoryService.createCategory(name, images);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument String name, @Argument String images) {
        return categoryService.updateCategory(id, name, images);
    }

    @MutationMapping
    public boolean deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}