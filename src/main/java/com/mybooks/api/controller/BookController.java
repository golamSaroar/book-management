package com.mybooks.api.controller;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Book;
import com.mybooks.api.resource.BookResource;
import com.mybooks.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/books", produces = "application/hal+json")
public class BookController {

    private BookService bookService;

    @GetMapping
    public ResponseEntity<PagedResources<Resource<BookResource>>> all(Pageable pageable,
                                                                      PagedResourcesAssembler<BookResource> assembler) {
        final Page<Book> books = bookService.getBooks(pageable);
        final Page<BookResource> bookResources = books.map(BookResource::new);
        final PagedResources<Resource<BookResource>> resources = assembler.toResource(bookResources);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws EntityNotFoundException {
        if(id == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new BookResource(bookService.getBook(id)));
    }

    @PostMapping
    public ResponseEntity<BookResource> create(@RequestBody Book book) {
        bookService.createBook(book);
        final URI uri = linkTo(BookController.class).slash(book.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookResource(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResource> update(@PathVariable("id") Long id,
                                               @RequestBody Book book)throws EntityNotFoundException {
        bookService.updateBook(id, book);
        final URI uri = linkTo(BookController.class).slash(book.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookResource(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
