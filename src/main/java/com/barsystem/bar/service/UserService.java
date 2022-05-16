package com.barsystem.bar.service;


import com.barsystem.bar.model.User;

import java.util.Set;

public interface UserService {

    User save(User user);

    Set<User> findAll();

    void delete(Long id);

    User findById(Long id);

    User update(User updated,Long id);
}
