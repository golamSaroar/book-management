package com.mybooks.api.resource;

import com.mybooks.api.controller.AuthorController;
import com.mybooks.api.model.Author;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@Relation(value = "author", collectionRelation = "authors")
public class AuthorResource extends ResourceSupport {

    private final Author author;

    public AuthorResource(Author author) {
        this.author = author;
        add(linkTo(AuthorController.class).withRel("authors"));
        add(linkTo(AuthorController.class).slash(author.getId()).withSelfRel());
    }
}
