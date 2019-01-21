package com.mybooks.api.service.impl;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.Book;
import com.mybooks.api.model.User;
import com.mybooks.api.repository.BookRepository;
import com.mybooks.api.repository.UserRepository;
import com.mybooks.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BookRepository bookRepository;

    @Override
    public User getUser(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new EntityNotFoundException("User with this id: " + id + " not found.");
        }
        return user.get();
    }

    @Override
    public Page<User> getUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User user) throws EntityNotFoundException{
        Optional<User> existingUser = userRepository.findById(id);
        if(!existingUser.isPresent()){
            throw new EntityNotFoundException("User with this id: " + id + " not found.");
        }
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new EntityNotFoundException("User with this id: " + id + " not found.");
        }
        Set<Book> lostBooks = user.get().getLostBooks();
        if(!lostBooks.isEmpty()){
            for(Book book : lostBooks) {
                book.setLostBy(null);
                bookRepository.save(book);
            }
        }
        userRepository.delete(user.get());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
