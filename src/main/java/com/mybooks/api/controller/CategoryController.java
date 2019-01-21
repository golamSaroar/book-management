package com.mybooks.api.controller;

import com.mybooks.api.exception.EntityAlreadyExistsException;
import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Category;
import com.mybooks.api.resource.CategoryResource;
import com.mybooks.api.service.CategoryService;
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
@RequestMapping(value = "/categories", produces = "application/hal+json")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PagedResources<Resource<CategoryResource>>> all(Pageable pageable,
                                                                        PagedResourcesAssembler<CategoryResource> assembler) {
        final Page<Category> categorys = categoryService.getCategories(pageable);
        final Page<CategoryResource> categoryResources = categorys.map(CategoryResource::new);
        final PagedResources<Resource<CategoryResource>> resources = assembler.toResource(categoryResources);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws EntityNotFoundException {
        if(id == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CategoryResource(categoryService.getCategory(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResource> create(@RequestBody Category category) throws EntityAlreadyExistsException {
        categoryService.createCategory(category);
        final URI uri = linkTo(CategoryController.class).slash(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoryResource(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResource> update(@PathVariable("id") Long id,
                                                 @RequestBody Category category)throws EntityNotFoundException {
        categoryService.updateCategory(id, category);
        final URI uri = linkTo(CategoryController.class).slash(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoryResource(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
