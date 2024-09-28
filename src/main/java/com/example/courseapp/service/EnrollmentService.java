package com.example.courseapp.service;
import com.example.courseapp.model.Course;
import com.example.courseapp.model.User;
import com.example.courseapp.repository.CourseRepository;
import com.example.courseapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public String enrollStudentInCourse(Long courseId, Long userId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (courseOpt.isEmpty()) {
            return "Course not found";
        }
        if (userOpt.isEmpty()) {
            return "User not found";
        }
        Course course = courseOpt.get();
        User user = userOpt.get();

        if (user.getEnrolledCourses().contains(course)) {
            return "User is already enrolled in this course";
        }

        course.setEnrolledStudents(course.getEnrolledStudents() + 1);
        courseRepository.save(course);

        user.addCourse(course);
        userRepository.save(user);

        return "User " + user.getFirstname() + " has been successfully enrolled in " + course.getName();
    }
}
