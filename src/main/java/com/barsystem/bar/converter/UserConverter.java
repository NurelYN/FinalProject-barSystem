package com.barsystem.bar.converter;

import com.barsystem.bar.dto.user.UserResponse;
import com.barsystem.bar.dto.user.UserSaveRequest;
import com.barsystem.bar.dto.user.UserUpdateRequest;
import com.barsystem.bar.model.User;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.service.UserTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserConverter {

    private final UserTypeService userTypeService;

    public UserResponse convert(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .build();
    }

    public User convert(UserSaveRequest userSaveRequest){
        return User.builder()
                .name(userSaveRequest.getName())
                .pin(userSaveRequest.getPin())
                .phone(userSaveRequest.getPhone())
                .userType(userTypeService.findByName(userSaveRequest.getUserType()))
                .build();
    }

    public User convert(UserUpdateRequest userUpdateRequest){

        UserType userType = userTypeService.findByName(userUpdateRequest.getUserType());
        return  User.builder()
                .name(userUpdateRequest.getName())
                .pin(userUpdateRequest.getPin())
                .phone(userUpdateRequest.getPhone())
                .userType(userType)
                .build();
    }
}
