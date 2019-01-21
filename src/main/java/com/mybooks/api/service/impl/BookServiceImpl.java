package com.mybooks.api.service.impl;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Author;
import com.mybooks.api.model.Book;
import com.mybooks.api.model.Category;
import com.mybooks.api.repository.AuthorRepository;
import com.mybooks.api.repository.BookRepository;
import com.mybooks.api.repository.CategoryRepository;
import com.mybooks.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    @Override
    public Book getBook(Long id) throws EntityNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()){
            throw new EntityNotFoundException("Book with this id: " + id + " not found.");
        }
        return book.get();
    }

    @Override
    public Page<Book> getBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    @Override
    public void createBook(Book book) {
        Set<Author> authors = book.getAuthors();
        for(Author author: authors){
            Optional<Author> existingAuthor = authorRepository.findById(author.getId());
            if(existingAuthor.isPresent()){
                authors.remove(author);
                authors.add(existingAuthor.get());
            }
        }

        Set<Category> categories = book.getCategories();
        for(Category category: categories){
            Optional<Category> existingCategory = categoryRepository.findById(category.getId());
            if(existingCategory.isPresent()){
                categories.remove(category);
                categories.add(existingCategory.get());
            }
        }

        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, Book book) throws EntityNotFoundException{
        Optional<Book> existingBook = bookRepository.findById(id);
        if(!existingBook.isPresent()){
            throw new EntityNotFoundException("Book with this id: " + id + " not found.");
        }
        book.setId(id);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) throws EntityNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()){
            throw new EntityNotFoundException("Book with this id: " + id + " not found.");
        }
        bookRepository.delete(book.get());
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
