package com.plm.poelman.java_api.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.dto.categories.CategorySearchResponse;
import com.plm.poelman.java_api.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private final CategoryService _categoryService;

    public CategoryController(CategoryService categoryService) {
        this._categoryService = categoryService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCategoriesByName( @RequestParam(required = false, defaultValue = "") String searchString,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "20") int pageSize) {

        System.out.println("Searching categories with searchString: " + searchString + ", page: " + page + ", pageSize: " + pageSize);
        
        CategorySearchResponse results = _categoryService.searchCategoriesByName(searchString, page, pageSize);

        if(results.getItems() == null || results.getItems().isEmpty()) {
            return ResponseEntity.status(404).body("No categories found matching: " + searchString);
        }

        return ResponseEntity.ok(results);
    }
}
