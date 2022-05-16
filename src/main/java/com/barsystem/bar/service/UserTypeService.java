package com.barsystem.bar.service;


import com.barsystem.bar.model.UserType;

import java.util.Set;

public interface UserTypeService {

    UserType save(UserType userType);

    Set<UserType> findAll();

    void delete(Long id);

    void delete(String name);

    UserType findById(Long id);

    UserType findByName(String name);

    UserType update(UserType updated, Long id);


}
