package com.barsystem.bar.service.impl;

import com.barsystem.bar.exception.RecordNotFoundException;
import com.barsystem.bar.model.User;
import com.barsystem.bar.repository.UserRepostiory;
import com.barsystem.bar.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepostiory userRepository;

    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepostiory userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User save(@NonNull User user) {
            user.setPin(bCryptPasswordEncoder.encode(user.getPin()));
            return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException(
                        String.format("User with id:%s is not found",id)));
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User update(@NonNull User updated,@NonNull Long id) {
        User dbUser = findById(id);
//        User updatedUser = User.builder()
//                .id(dbUser.getId())
//                .name(updated.getName())
//                .phone(updated.getPhone())
//                .pin(updated.getPin())
//                .userType(updated.getUserType())
//                .build();
        dbUser.setName(updated.getName());
        dbUser.setUserType(updated.getUserType());
        dbUser.setPin(updated.getPin());
        dbUser.setPhone(updated.getPhone());
        return dbUser;
    }
}
