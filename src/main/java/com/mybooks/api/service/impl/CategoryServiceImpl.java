package com.mybooks.api.service.impl;

import com.mybooks.api.exception.EntityAlreadyExistsException;
import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Book;
import com.mybooks.api.model.Category;
import com.mybooks.api.repository.BookRepository;
import com.mybooks.api.repository.CategoryRepository;
import com.mybooks.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;

    @Override
    public Category getCategory(Long id) throws EntityNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()){
            throw new EntityNotFoundException("Category with this id: " + id + " not found.");
        }
        return category.get();
    }

    @Override
    public Page<Category> getCategories(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    @Override
    public void createCategory(Category category) throws EntityAlreadyExistsException {
        if(categoryRepository.existsByName(category.getName())) {
            throw new EntityAlreadyExistsException("Category with this name: " + category.getName() + " already exists.");
        } else {
            categoryRepository.save(category);
        }
    }

    @Override
    public void updateCategory(Long id, Category category) throws EntityNotFoundException{
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(!existingCategory.isPresent()){
            throw new EntityNotFoundException("Category with this id: " + id + " not found.");
        }
        category.setId(id);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) throws EntityNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()){
            throw new EntityNotFoundException("Category with this id: " + id + " not found.");
        }
        Set<Book> books = category.get().getBooks();
        for(Book book : books) {
            book.getCategories().remove(category.get());
            bookRepository.save(book);
        }
        categoryRepository.delete(category.get());
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
