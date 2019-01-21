package com.mybooks.api.service;

import com.mybooks.api.exception.EntityNotFoundException;
import com.mybooks.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUser(Long id) throws EntityNotFoundException;

    Page<User> getUsers(Pageable pageable);

    void createUser(User user);

    void updateUser(Long id, User user) throws EntityNotFoundException;

    void deleteUser(Long id) throws EntityNotFoundException;
}
