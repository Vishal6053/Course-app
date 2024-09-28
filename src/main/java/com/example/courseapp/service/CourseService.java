package com.example.courseapp.service;
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
        courseRepository.save(course);
    }
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void updateCourse(Long id, Course updatedCourse) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setName(updatedCourse.getName());
            course.setDescription(updatedCourse.getDescription());
            course.setDuration(updatedCourse.getDuration());
            course.setTags(updatedCourse.getTags());
            course.setTeachername(updatedCourse.getTeachername());
            course.setFee(updatedCourse.getFee());
            courseRepository.save(course);
        }
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
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
