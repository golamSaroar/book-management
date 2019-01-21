package com.mybooks.api.service.impl;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Book;
import com.mybooks.api.model.Loan;
import com.mybooks.api.model.User;
import com.mybooks.api.repository.BookRepository;
import com.mybooks.api.repository.LoanRepository;
import com.mybooks.api.repository.UserRepository;
import com.mybooks.api.service.LoanService;
import com.mybooks.api.utils.BookStatus;
import com.mybooks.api.utils.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Override
    public Loan getLoan(Long id) throws EntityNotFoundException {
        Optional<Loan> loan = loanRepository.findById(id);
        if(!loan.isPresent()){
            throw new EntityNotFoundException("Loan with this id: " + id + " not found.");
        }
        return loan.get();
    }

    @Override
    public Page<Loan> getLoans(Pageable pageable){
        return loanRepository.findAll(pageable);
    }

    @Override
    public void createLoan(Loan loan) {
        Optional<User> existingBorrower = userRepository.findById(loan.getBorrower().getId());
        if(existingBorrower.isPresent()){
            loan.setBorrower(existingBorrower.get());
        }

        Optional<Book> existingBook = bookRepository.findById(loan.getBook().getId());
        if(existingBook.isPresent()){
            existingBook.get().setStatus(BookStatus.ON_LOAN);
            loan.setBook(existingBook.get());
        }
        loanRepository.save(loan);
    }

    @Override
    public void updateLoan(Long id, Loan loan) throws EntityNotFoundException{
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if(!optionalLoan.isPresent()){
            throw new EntityNotFoundException("Loan with this id: " + id + " not found.");
        }

        Loan existingLoan = optionalLoan.get();
        existingLoan.setReturnedOn(loan.getReturnedOn());
        LoanStatus status = loan.getStatus();
        existingLoan.setStatus(status);
        Book book = existingLoan.getBook();

        if(status.equals(LoanStatus.LOST)){
            book.setStatus(BookStatus.LOST);
            book.setLostBy(existingLoan.getBorrower());
        } else if (status.equals(LoanStatus.RETURNED)){
            book.setStatus(BookStatus.AVAILABLE);
        }
        loanRepository.save(existingLoan);
    }

    @Override
    public void deleteLoan(Long id) throws EntityNotFoundException {
        Optional<Loan> loan = loanRepository.findById(id);
        if(!loan.isPresent()){
            throw new EntityNotFoundException("Loan with this id: " + id + " not found.");
        }
        loanRepository.delete(loan.get());
    }

    @Autowired
    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
