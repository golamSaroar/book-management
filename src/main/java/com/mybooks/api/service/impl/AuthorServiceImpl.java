package com.mybooks.api.service.impl;

import com.mybooks.api.exception.EntityAlreadyExistsException;
import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Author;
import com.mybooks.api.model.Book;
import com.mybooks.api.repository.AuthorRepository;
import com.mybooks.api.repository.BookRepository;
import com.mybooks.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Override
    public Author getAuthor(Long id) throws EntityNotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if(!author.isPresent()){
            throw new EntityNotFoundException("Author with this id: " + id + " not found.");
        }
        return author.get();
    }

    @Override
    public Page<Author> getAuthors(Pageable pageable){
        return authorRepository.findAll(pageable);
    }

    @Override
    public void createAuthor(Author author) throws EntityAlreadyExistsException {
        if(authorRepository.existsByName(author.getName())) {
            throw new EntityAlreadyExistsException("Author with this name: " + author.getName() + " already exists.");
        } else {
            authorRepository.save(author);
        }
    }

    @Override
    public void updateAuthor(Long id, Author author) throws EntityNotFoundException{
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if(!existingAuthor.isPresent()){
            throw new EntityNotFoundException("Author with this id: " + id + " not found.");
        }
        author.setId(id);
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) throws EntityNotFoundException {
        Optional<Author> author = authorRepository.findById(id);
        if(!author.isPresent()){
            throw new EntityNotFoundException("Author with this id: " + id + " not found.");
        }
        Set<Book> books = author.get().getBooks();
        for(Book book : books) {
            book.getAuthors().remove(author.get());
            bookRepository.save(book);
        }
        authorRepository.delete(author.get());
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
