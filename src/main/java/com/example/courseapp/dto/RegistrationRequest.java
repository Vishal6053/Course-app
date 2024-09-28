package com.example.courseapp.dto;

import com.example.courseapp.model.Address;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String schoolname;
    private String rollno;
    private String email;
    private String mobile;
    private String password;
    private String confirmPassword;
    private String role;
    private Set<Address> addresses = new HashSet<>();  // Initialize with empty set

    public Set<Address> getAddresses() {
        return addresses;
    }
}

