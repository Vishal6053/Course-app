package com.example.courseapp.controller;

import com.example.courseapp.model.Course;
import com.example.courseapp.service.EnrollmentService;
import com.example.courseapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudents(@RequestParam Long courseId, @RequestParam Long userId) {
        String message = enrollmentService.enrollStudentInCourse(courseId, userId);
        return ResponseEntity.ok(message);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Course>> getCoursesForUser(
            @PathVariable Long userId) {
        try {
            List<Course> courses = userService.getCoursesForUser(userId);
            return ResponseEntity.ok(courses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
