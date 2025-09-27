package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import com.example.demo.service.ICategoryService;
import com.example.demo.service.IStorageService;

import org.springframework.util.StringUtils;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IStorageService storageService;

    @GetMapping("/add")
    public String add(ModelMap model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/addOrEdit";
    }

    @PostMapping("/addOrEdit")
    public String saveOrUpdate(ModelMap model, @Validated @ModelAttribute("category") Category category,
                               BindingResult result, @RequestParam("icon") MultipartFile icon) {
        if (result.hasErrors()) {
            return "category/addOrEdit";
        }
        if (!icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            category.setIcon(storageService.getSorageFilename(icon, uuString));
            storageService.store(icon, category.getIcon());
        }
        categoryService.save(category);
        model.addAttribute("message", "Category is saved!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{categoryId}")
    public String edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isPresent()) {
            Category category = optCategory.get();
            model.addAttribute("category", category);
            return "category/addOrEdit";
        } else {
            return "redirect:/admin/categories";
        }
    }

    @GetMapping("/delete/{categoryId}")
    public String delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
        categoryService.deleteById(categoryId);
        return "redirect:/admin/categories";
    }

    @GetMapping("")
    public String list(ModelMap model) {
        List<Category> list = categoryService.findAll();
        model.addAttribute("categories", list);
        return "category/list";
    }

    @GetMapping("/search")
    public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
        List<Category> list = null;
        if (StringUtils.hasText(name)) {
            list = categoryService.findByCategoryNameContaining(name);
        } else {
            list = categoryService.findAll();
        }
        model.addAttribute("categories", list);
        return "category/search";
    }

    @RequestMapping("/searchpaginated")
    public String searchPaginated(ModelMap model,
                                  @RequestParam(name = "name", required = false) String name,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryName"));
        Page<Category> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = categoryService.findByCategoryNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
            resultPage = categoryService.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 5) {
                if (end == totalPages) start = end - 5;
                else if (start == 1) end = start + 5;
            }
            List<Integer> pageNumbers = List.of(start, start + 1, start + 2, start + 3, start + 4);  // Ví dụ
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("categoryPage", resultPage);
        return "category/searchpaginated";
    }
}