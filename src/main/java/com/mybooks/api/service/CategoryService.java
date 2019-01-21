package com.mybooks.api.service;

import com.mybooks.api.exception.EntityAlreadyExistsException;
import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category getCategory(Long id) throws EntityNotFoundException;

    Page<Category> getCategories(Pageable pageable);

    void createCategory(Category category) throws EntityAlreadyExistsException;

    void updateCategory(Long id, Category category) throws EntityNotFoundException;

    void deleteCategory(Long id) throws EntityNotFoundException;
}
