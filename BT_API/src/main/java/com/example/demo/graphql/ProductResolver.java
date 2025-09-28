package com.example.demo.graphql;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductResolver {
    private final ProductService productService;
    private final UserService userService;

    public ProductResolver(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @QueryMapping
    public List<Product> allProducts() {
        return productService.findAll();
    }

    @QueryMapping
    public List<Product> allProductsSortedByPrice() {
        return productService.findAllSortedByPriceAsc();
    }

    @QueryMapping
    public Product productById(@Argument Long id) {
        return productService.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument Long id) {
        return productService.findByCategoryId(id);
    }

    @MutationMapping
    public Product createProduct(@Argument String title, @Argument Integer quantity, @Argument String desc, @Argument Float price, @Argument Long userid) {
        Optional<User> optionalUser = userService.findById(userid);
        if (optionalUser.isPresent()) {
            Product product = new Product();
            product.setTitle(title);
            product.setQuantity(quantity);
            product.setDesc(desc);
            product.setPrice((double) price);
            product.setUser(optionalUser.get());
            return productService.save(product);
        }
        return null;
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String title, @Argument Integer quantity, @Argument String desc, @Argument Float price, @Argument Long userid) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (title != null) product.setTitle(title);
            if (quantity != null) product.setQuantity(quantity);
            if (desc != null) product.setDesc(desc);
            if (price != null) product.setPrice((double) price);
            if (userid != null) {
                Optional<User> optionalUser = userService.findById(userid);
                optionalUser.ifPresent(product::setUser);
            }
            return productService.save(product);
        }
        return null;
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        productService.deleteById(id);
        return true;
    }
}