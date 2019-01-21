package com.mybooks.api.service;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book getBook(Long id) throws EntityNotFoundException;

    Page<Book> getBooks(Pageable pageable);

    void createBook(Book book);

    void updateBook(Long id, Book book) throws EntityNotFoundException;

    void deleteBook(Long id) throws EntityNotFoundException;
}
