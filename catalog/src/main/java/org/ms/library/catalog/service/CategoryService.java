package org.ms.library.catalog.service;

import org.ms.library.catalog.dto.CategoryDTO;
import org.ms.library.catalog.entity.Category;
import org.ms.library.catalog.repository.CategoryRepository;
import org.ms.library.catalog.service.exceptions.NoCategoryFoundException;
import org.ms.library.catalog.service.exceptions.NoResouceFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to find all categories
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());

    }

    // Method to find a category by ID
    public CategoryDTO findCategoryById(Long id) {

        CategoryDTO categoryDTO = new CategoryDTO(categoryRepository.findById(id).orElseThrow(() -> new NoCategoryFoundException("Category not found with ID: " + id)));
        return categoryDTO;

    }

    // Method to save a new category
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        dtoToEntity(categoryDTO, category);
        Category savedEntity = categoryRepository.save(category);
        return new CategoryDTO(savedEntity);
    }

    // Method to update an existing category
    public CategoryDTO updateCategory(Long id, CategoryDTO updatedCategory) {

        if (categoryRepository.existsById(id)) {

            Category existingCategory = categoryRepository.getReferenceById(id);
            dtoToEntity(updatedCategory, existingCategory);
            Category updatedEntity = categoryRepository.save(existingCategory);
            return new CategoryDTO(updatedEntity);
        } else throw new NoCategoryFoundException("Category not found with ID: " + id);

    }

    // Method to delete a category by ID
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new NoCategoryFoundException("Category not found with ID: " + id);
        }
    }

    private Category dtoToEntity(CategoryDTO categoryDTO, Category category) {

        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        return category;


    }

}

