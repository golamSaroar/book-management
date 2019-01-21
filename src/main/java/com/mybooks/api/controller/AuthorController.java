package com.mybooks.api.controller;

import com.mybooks.api.exception.EntityAlreadyExistsException;
import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Author;
import com.mybooks.api.resource.AuthorResource;
import com.mybooks.api.service.AuthorService;
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
@RequestMapping(value = "/authors", produces = "application/hal+json")
public class AuthorController {

    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<PagedResources<Resource<AuthorResource>>> all(Pageable pageable,
                                                                          PagedResourcesAssembler<AuthorResource> assembler) {
        final Page<Author> authors = authorService.getAuthors(pageable);
        final Page<AuthorResource> authorResources = authors.map(AuthorResource::new);
        final PagedResources<Resource<AuthorResource>> resources = assembler.toResource(authorResources);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws EntityNotFoundException {
        if(id == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AuthorResource(authorService.getAuthor(id)));
    }

    @PostMapping
    public ResponseEntity<AuthorResource> create(@RequestBody Author author) throws EntityAlreadyExistsException {
        authorService.createAuthor(author);
        final URI uri = linkTo(AuthorController.class).slash(author.getId()).toUri();
        return ResponseEntity.created(uri).body(new AuthorResource(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResource> update(@PathVariable("id") Long id,
                                                   @RequestBody Author author)throws EntityNotFoundException {
        authorService.updateAuthor(id, author);
        final URI uri = linkTo(AuthorController.class).slash(author.getId()).toUri();
        return ResponseEntity.created(uri).body(new AuthorResource(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
}
