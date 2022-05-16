package com.barsystem.bar.dto.user;

import com.barsystem.bar.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserUpdateRequest {

    private String name;

    private String pin;

    private String phone;

    private String userType;
}
