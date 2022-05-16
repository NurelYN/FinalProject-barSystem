package com.barsystem.bar.service.impl;

import com.barsystem.bar.exception.DuplicateRecordException;
import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.User;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.repository.UserTypeRepository;
import com.barsystem.bar.service.UserTypeService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Override
    public UserType save(@NonNull UserType userType) {
        try{
            return userTypeRepository.save(userType);
        } catch(DataIntegrityViolationException ex){
            throw new DuplicateRecordException(
                    String.format("User type with this name: %s already exist",userType.getName()));
        }
    }

    @Override
    public Set<UserType> findAll() {
       return new HashSet<>(userTypeRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        userTypeRepository.deleteById(id);
    }

    @Override
    public void delete(String name) {
        userTypeRepository.deleteByName(name);
    }

    @Override
    public UserType findById(Long id) {
       return userTypeRepository.findById(id).orElseThrow(()->new RecordNotFoundException(
               String.format("User type with id: %s is not found",id)));
    }

    @Override
    public UserType findByName(String name) {
        return userTypeRepository.findByName(name).orElseThrow(()->new RecordNotFoundException(
                String.format("User type with name: %s is not found",name)));
    }

    @Override
    public UserType update(@NonNull UserType updated,@NonNull Long id) {

        UserType dbUserType = findById(id);
//        UserType updatedUserType = UserType.builder()
//                .id(dbUserType.getId())
//                .name(updated.getName())
//                .build();
//        return userTypeRepository.save(updatedUserType);
        dbUserType.setName(updated.getName());
        return dbUserType;
    }
}
