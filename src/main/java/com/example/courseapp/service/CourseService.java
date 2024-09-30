package com.example.courseapp.service;
import com.example.courseapp.exception.CourseNotFoundException;
import com.example.courseapp.exception.DuplicateCourseNameException;
import com.example.courseapp.model.Course;
import com.example.courseapp.repository.CourseRepository;
import com.example.courseapp.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;



    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
    public void saveCourse(Course course) {
        if (courseRepository.existsByName(course.getName())) {
            throw new DuplicateCourseNameException("Course cannot be created as the course with name '" + course.getName() + "' already exists.");
        }
        courseRepository.save(course);
    }
    public Optional<Course> getCourseById(Long id) {
        return Optional.ofNullable(courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id)));
    }


    public void updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDuration(updatedCourse.getDuration());
        existingCourse.setTags(updatedCourse.getTags());
        existingCourse.setTeachername(updatedCourse.getTeachername());
        existingCourse.setFee(updatedCourse.getFee());
        courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    public List<Course> filterCourses(String name, String tag, String teacherName) {
        Specification<Course> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and(CourseSpecification.hasName(name));
        }
        if (tag != null && !tag.isEmpty()) {
            spec = spec.and(CourseSpecification.hasTag(tag));
        }
        if (teacherName != null && !teacherName.isEmpty()) {
            spec = spec.and(CourseSpecification.hasTeacherName(teacherName));
        }

        return courseRepository.findAll(spec);
    }

}
