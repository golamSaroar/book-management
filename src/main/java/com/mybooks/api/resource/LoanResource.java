package com.mybooks.api.resource;

import com.mybooks.api.controller.LoanController;
import com.mybooks.api.model.Loan;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@Relation(value = "loan", collectionRelation = "loans")
public class LoanResource extends ResourceSupport {

    private final Loan loan;

    public LoanResource(Loan loan) {
        this.loan = loan;
        add(linkTo(LoanController.class).withRel("loans"));
        add(linkTo(LoanController.class).slash(loan.getId()).withSelfRel());
    }
}
