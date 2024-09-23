package com.example.courseapp.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 to 20 character.")
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @NotBlank(message = "School name is required")
    @Size(min = 2, max = 50, message = "School name must be between 2 to 50 character")
    @Column(nullable = false)
    private String schoolname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Mobile no. is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a 10-digit number")
    @Column(nullable = false)
    private String mobile;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 character")
    @Column(nullable = false)
    private String password;

    @Column
    private String otp;

    @Column
    private Boolean isVerified = false;

    public void setVerified(Boolean verified) {
        this.isVerified = verified;
    }

}
