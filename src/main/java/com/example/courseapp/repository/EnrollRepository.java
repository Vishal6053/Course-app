package com.example.courseapp.repository;


import com.example.courseapp.model.User;
import com.example.courseapp.model.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollRepository extends JpaRepository<UserCourses, Long> {
    List<UserCourses> findByUser(User user);
}
