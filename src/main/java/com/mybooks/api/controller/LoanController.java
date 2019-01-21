package com.mybooks.api.controller;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Loan;
import com.mybooks.api.resource.LoanResource;
import com.mybooks.api.service.LoanService;
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
@RequestMapping(value = "/loans", produces = "application/hal+json")
public class LoanController {

    private LoanService loanService;

    @GetMapping
    public ResponseEntity<PagedResources<Resource<LoanResource>>> all(Pageable pageable,
                                                                        PagedResourcesAssembler<LoanResource> assembler) {
        final Page<Loan> loans = loanService.getLoans(pageable);
        final Page<LoanResource> loanResources = loans.map(LoanResource::new);
        final PagedResources<Resource<LoanResource>> resources = assembler.toResource(loanResources);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws EntityNotFoundException {
        if(id == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new LoanResource(loanService.getLoan(id)));
    }

    @PostMapping
    public ResponseEntity<LoanResource> create(@RequestBody Loan loan) {
        loanService.createLoan(loan);
        final URI uri = linkTo(LoanController.class).slash(loan.getId()).toUri();
        return ResponseEntity.created(uri).body(new LoanResource(loan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanResource> update(@PathVariable("id") Long id,
                                                 @RequestBody Loan loan)throws EntityNotFoundException {
        loanService.updateLoan(id, loan);
        final URI uri = linkTo(LoanController.class).slash(loan.getId()).toUri();
        return ResponseEntity.created(uri).body(new LoanResource(loan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }
}
