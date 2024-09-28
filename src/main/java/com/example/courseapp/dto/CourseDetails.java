package com.example.courseapp.dto;

import com.example.courseapp.model.User;

import java.util.Set;

public class CourseDetails {

    public CourseDetails(Long id, String name, String duration, String description,
                         String tags, String teachername, Double fee,
                         int enrolledStudents, Set<User> enrolledUsers) {
    }

}

