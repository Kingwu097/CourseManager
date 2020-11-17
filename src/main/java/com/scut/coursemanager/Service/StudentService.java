package com.scut.coursemanager.Service;

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.*;
import com.scut.coursemanager.dto.DropRequest;
import com.scut.coursemanager.dto.StudentInfoRequest;
import com.scut.coursemanager.dto.TakesRequest;

import java.util.List;

public interface StudentService {
    /**
     *创建学生
     */
    void createStudent(StudentInfoRequest studentInfoRequest) throws CreateException;

    /**
     * 删除学生
     */
    void deleteStudent(String username) throws DeleteException;

    /**
     * 修改学生
     */
    void modifyStudent(StudentInfo studentInfo) throws ModifyException;

    /**
     * 查找学生
     */
    StudentInfo getStudentInfo(String username) throws QueryException;


    /**
     * 学生选课
     */
    void CourseChosen(TakesRequest takesRequest) throws CourseChoseException;

    /**
     * 学生退课
     */
    void CourseDrop(DropRequest dropRequest) throws CourseDeleteException;
}
