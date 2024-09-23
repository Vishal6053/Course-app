package com.example.courseapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstname;
    private String lastname;
    private String schoolname;
    private String email;
    private String mobile;
    private String password;
}
