package com.example.demo.graphql;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserResolver {
    private final UserService userService;
    private final CategoryService categoryService;

    public UserResolver(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<User> allUsers() {
        return userService.findAll();
    }

    @QueryMapping
    public User userById(@Argument Long id) {
        return userService.findById(id).orElse(null);
    }

    @MutationMapping
    public User createUser(@Argument String fullname, @Argument String email, @Argument String password, @Argument String phone) {
        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        return userService.save(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String fullname, @Argument String email, @Argument String password, @Argument String phone) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (fullname != null) user.setFullname(fullname);
            if (email != null) user.setEmail(email);
            if (password != null) user.setPassword(password);
            if (phone != null) user.setPhone(phone);
            return userService.save(user);
        }
        return null;
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        userService.deleteById(id);
        return true;
    }

    @MutationMapping
    public User addCategoryToUser(@Argument Long userId, @Argument Long categoryId) {
        Optional<User> optionalUser = userService.findById(userId);
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalUser.isPresent() && optionalCategory.isPresent()) {
            User user = optionalUser.get();
            user.getCategories().add(optionalCategory.get());
            return userService.save(user);
        }
        return null;
    }
}