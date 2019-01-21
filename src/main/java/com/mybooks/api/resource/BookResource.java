package com.mybooks.api.resource;

import com.mybooks.api.controller.BookController;
import com.mybooks.api.model.Book;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@Relation(value = "book", collectionRelation = "books")
public class BookResource extends ResourceSupport {

    private final Book book;

    public BookResource(Book book) {
        this.book = book;
        add(linkTo(BookController.class).withRel("books"));
        add(linkTo(BookController.class).slash(book.getId()).withSelfRel());
    }
}
