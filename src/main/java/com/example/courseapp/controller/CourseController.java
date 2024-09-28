package com.example.courseapp.controller;

import com.example.courseapp.model.Course;
import com.example.courseapp.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return ResponseEntity.status(201).body("Course created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if (courseOptional.isPresent()) {
            courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok("Course updated successfully");
        } else {
            return ResponseEntity.status(404).body("Course not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if (courseOptional.isPresent()) {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Course deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Course not found");
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Course>> filterCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String teacherName) {

        List<Course> courses = courseService.filterCourses(name, tag, teacherName);
        return ResponseEntity.ok(courses);
    }

}
