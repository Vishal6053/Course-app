package com.example.courseapp.specification;

import com.example.courseapp.model.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {
    public static Specification<Course> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Course> hasTag(String tag) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("tags"), "%" + tag + "%");
    }

    public static Specification<Course> hasTeacherName(String teacherName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("teachername"), "%" + teacherName + "%");
    }
}
