package com.example.courseapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 to 20 characters.")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 20, message = "Last name must be between 2 to 20 characters.")
    @Column(nullable = false)
    private String lastname;

    @NotBlank(message = "School name is required")
    @Size(min = 2, max = 50, message = "School name must be between 2 to 50 characters")
    @Column(nullable = false)
    private String schoolname;

    @NotBlank(message = "Roll no. is required")
    @Column(nullable = false)
    private String rollno;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Mobile no. is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a 10-digit number")
    @Column(nullable = false)
    private String mobile;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmpassword;

    @Column
    private String otp;

    @Column
    private Boolean isVerified = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore  // Prevent serialization of enrolled courses
    private Set<Course> enrolledCourses = new HashSet<>();

    //study this cascadeType.detach
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // Ignore during serialization to prevent nesting issues
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore  // Prevent serialization of roles
    private Set<Role> roles = new HashSet<>();

    // Constructor for initializing addresses
    public User(Long id, String email, String password, String otp, Boolean isVerified,
                String rollno, String firstname, String lastname,
                String schoolname, String mobile, Set<Address> addresses) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.otp = otp;
        this.isVerified = isVerified;
        this.rollno = rollno;
        this.firstname = firstname;
        this.lastname = lastname;
        this.schoolname = schoolname;
        this.mobile = mobile;
        this.addresses = addresses != null ? addresses : new HashSet<>();
    }
    public void addCourse(Course course) {
        if (this.enrolledCourses == null) {
            this.enrolledCourses = new HashSet<>();
        }
        this.enrolledCourses.add(course);
    }


}
