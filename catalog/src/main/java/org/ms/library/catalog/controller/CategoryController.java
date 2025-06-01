package org.ms.library.catalog.controller;

import org.ms.library.catalog.dto.CategoryDTO;
import org.ms.library.catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/catalog/categories")
public class CategoryController {

    // This class will handle HTTP requests related to categories.
    // It will use the CategoryService to perform operations like creating, updating, deleting, and retrieving categories.
    // The methods will return appropriate responses based on the operations performed.
    @Autowired
    private CategoryService categoryService;

    // Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get a category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // Create a new category
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.saveCategory(categoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdCategory);
    }

    // Update an existing category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }





}
