package com.barsystem.bar.converter;


import com.barsystem.bar.dto.userType.UserTypeRequest;
import com.barsystem.bar.model.UserType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserTypeConverter{

    public UserType convert(UserTypeRequest userTypeRequest){
        return UserType.builder()
                .name(userTypeRequest.getName())
                .build();
    }

}
