package com.barsystem.bar.controller;


import com.barsystem.bar.converter.UserConverter;
import com.barsystem.bar.dto.user.UserResponse;
import com.barsystem.bar.dto.user.UserSaveRequest;
import com.barsystem.bar.dto.user.UserUpdateRequest;
import com.barsystem.bar.model.User;
import com.barsystem.bar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/users")
public class UserController {

    private final UserService userService;

    private final UserConverter userConverter;

    @GetMapping
    public ResponseEntity<Set<UserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll().
                stream()
                .map(userConverter::convert)
                .collect(Collectors.toSet()));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserSaveRequest userSaveRequest){
        User user = userConverter.convert(userSaveRequest);
        User savedUser = userService.save(user);
        UserResponse userResponse = userConverter.convert(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping(value="/id/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        User user = userService.findById(id);
        UserResponse userResponse = userConverter.convert(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping(value="/id/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="id/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest userUpdateRequest,@PathVariable Long id) {
        User user = userConverter.convert(userUpdateRequest);
        User updatedUser = userService.update(user, id);
        UserResponse userResponse =userConverter.convert(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
