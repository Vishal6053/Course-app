package com.example.courseapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    @NotBlank(message = "Country Should not be blank.")
    private String country;

    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be a 6-digit number")
    @Column(nullable = false)
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // This will create a foreign key in the Address table
    private User user;
}
