package com.example.demo.graphql;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryResolver {
    private final CategoryService categoryService;

    public CategoryResolver(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<Category> allCategories() {
        return categoryService.findAll();
    }

    @QueryMapping
    public Category categoryById(@Argument Long id) {
        return categoryService.findById(id).orElse(null);
    }

    @MutationMapping
    public Category createCategory(@Argument String name, @Argument String images) {
        Category category = new Category();
        category.setName(name);
        category.setImages(images);
        return categoryService.save(category);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument String name, @Argument String images) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            if (name != null) category.setName(name);
            if (images != null) category.setImages(images);
            return categoryService.save(category);
        }
        return null;
    }

    @MutationMapping
    public boolean deleteCategory(@Argument Long id) {
        categoryService.deleteById(id);
        return true;
    }
}