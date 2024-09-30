package com.example.courseapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name cannot be blank.")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Course duration cannot be blank.")
    @Column(nullable = false)
    private String duration;

    @NotBlank(message = "Course description cannot be blank.")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Course tags cannot be blank.")
    @Column(nullable = false)
    private String tags;

    @NotBlank(message = "Teacher name cannot be blank.")
    @Column(nullable = false)
    private String teachername;

    @Column(nullable = false)
    private Double fee;

    @Column(nullable = false)
    private int enrolledStudents = 0;

    @ManyToMany(mappedBy = "enrolledCourses", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> enrolledUsers = new HashSet<>();
}
