package com.mybooks.api.resource;

import com.mybooks.api.controller.CategoryController;
import com.mybooks.api.model.Category;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@Relation(value = "category", collectionRelation = "categories")
public class CategoryResource extends ResourceSupport {

    private final Category category;

    public CategoryResource(Category category) {
        this.category = category;
        add(linkTo(CategoryController.class).withRel("categories"));
        add(linkTo(CategoryController.class).slash(category.getId()).withSelfRel());
    }
}
