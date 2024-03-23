package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.repository.CourseRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
    public Course updateCourse(Long id, Course newCourseData) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse != null) {
            // 更新课程信息
            existingCourse.setName(newCourseData.getName());
            existingCourse.setDescription(newCourseData.getDescription());
            existingCourse.setTeacher(newCourseData.getTeacher());
            return courseRepository.save(existingCourse);
        } else {
            return null;
        }
    }


    public boolean deleteCourse(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            courseRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Course getCourseById(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.orElse(null);
    }

}
