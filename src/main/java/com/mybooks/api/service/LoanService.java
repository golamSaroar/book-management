package com.mybooks.api.service;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    Loan getLoan(Long id) throws EntityNotFoundException;

    Page<Loan> getLoans(Pageable pageable);

    void createLoan(Loan loan);

    void updateLoan(Long id, Loan loan) throws EntityNotFoundException;

    void deleteLoan(Long id) throws EntityNotFoundException;
}
