package com.mybooks.api.controller;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.User;
import com.mybooks.api.resource.UserResource;
import com.mybooks.api.service.UserService;
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
@RequestMapping(value = "/users", produces = "application/hal+json")
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<PagedResources<Resource<UserResource>>> all(Pageable pageable,
                                                                        PagedResourcesAssembler<UserResource> assembler) {
        final Page<User> users = userService.getUsers(pageable);
        final Page<UserResource> userResources = users.map(UserResource::new);
        final PagedResources<Resource<UserResource>> resources = assembler.toResource(userResources);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws EntityNotFoundException {
        if(id == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserResource(userService.getUser(id)));
    }

    @PostMapping
    public ResponseEntity<UserResource> create(@RequestBody User user) {
        userService.createUser(user);
        final URI uri = linkTo(UserController.class).slash(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserResource(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> update(@PathVariable("id") Long id,
                                                 @RequestBody User user)throws EntityNotFoundException {
        userService.updateUser(id, user);
        final URI uri = linkTo(UserController.class).slash(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserResource(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
