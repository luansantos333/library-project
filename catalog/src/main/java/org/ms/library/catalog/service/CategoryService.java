package org.ms.library.catalog.service;

import org.ms.library.catalog.dto.CategoryDTO;
import org.ms.library.catalog.entity.Category;
import org.ms.library.catalog.repository.CategoryRepository;
import org.ms.library.catalog.service.exceptions.NoCategoryFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableWebSecurity
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private static final Logger logger =  LoggerFactory.getLogger(CategoryService.class);

    public List<CategoryDTO> findAllCategories() {
        logger.info("Start findAllCategories");
        return categoryRepository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public CategoryDTO findCategoryById(Long id) {

        CategoryDTO categoryDTO = new CategoryDTO(categoryRepository.findById(id).orElseThrow(() -> {

            logger.info("Trying to find category with id {}...",id);
            logger.error("Category not found with id {}", id);
            return new NoCategoryFoundException("Category not found with ID: " + id);

        }));
        return categoryDTO;

    }

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        dtoToEntity(categoryDTO, category);
        Category savedEntity = categoryRepository.save(category);
        logger.info("Saved Category with id {}", savedEntity.getId());
        return new CategoryDTO(savedEntity);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO updatedCategory) {

        if (categoryRepository.existsById(id)) {

            Category existingCategory = categoryRepository.getReferenceById(id);
            dtoToEntity(updatedCategory, existingCategory);
            Category updatedEntity = categoryRepository.save(existingCategory);
            logger.info("Updated Category with id {}", updatedEntity.getId());
            return new CategoryDTO(updatedEntity);
        } else {
            logger.error("Category not found with id {}", id);
            throw new NoCategoryFoundException("Category not found with ID: " + id);
        }

    }

    @Transactional
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            logger.info("Deleted Category with id {}", id);
        } else {
            logger.error("Category not found with id {}", id);
            throw new NoCategoryFoundException("Category not found with ID: " + id);
        }
    }

    private Category dtoToEntity(CategoryDTO categoryDTO, Category category) {

        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        return category;


    }

}

