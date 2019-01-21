package com.mybooks.api.service;

import com.mybooks.api.exception.EntityAlreadyExistsException;
import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Author getAuthor(Long id) throws EntityNotFoundException;

    Page<Author> getAuthors(Pageable pageable);

    void createAuthor(Author author) throws EntityAlreadyExistsException;

    void updateAuthor(Long id, Author author) throws EntityNotFoundException;

    void deleteAuthor(Long id) throws EntityNotFoundException;
}
