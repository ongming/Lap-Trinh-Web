package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllSortedByPriceAsc() {
        return productRepository.findAllSortedByPriceAsc();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Set<User> users = optionalCategory.get().getUsers();
            List<Product> products = new ArrayList<>();
            for (User user : users) {
                products.addAll(user.getProducts());
            }
            return products;
        }
        return List.of();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}