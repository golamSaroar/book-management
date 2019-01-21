package com.mybooks.api.resource;

import com.mybooks.api.controller.UserController;
import com.mybooks.api.model.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@Relation(value = "user", collectionRelation = "users")
public class UserResource extends ResourceSupport {

    private final User user;

    public UserResource(User user) {
        this.user = user;
        add(linkTo(UserController.class).withRel("users"));
        add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
    }
}
