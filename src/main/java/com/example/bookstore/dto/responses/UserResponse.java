package com.example.bookstore.dto.responses;

import com.example.bookstore.models.enums.GenderEnum;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String fullName;
    private String phone;
    private GenderEnum gender;
    private String address;
    private Date dob;
    private List<RoleResponse> roles;
}
