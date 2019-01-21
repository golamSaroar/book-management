package com.mybooks.api.model;

import com.mybooks.api.utils.LoanStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    @NotNull
    private User borrower;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "borrowed_on")
    private Date borrowedOn;

    @Temporal(TemporalType.DATE)
    @Column(name = "returned_on")
    private Date returnedOn;

}
