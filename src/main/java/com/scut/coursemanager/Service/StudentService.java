package com.scut.coursemanager.Service;

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;

public interface StudentService {
    /**
     *创建学生
     */
    void createStudent(StudentInfo studentInfo) throws CreateException;

    /**
     * 删除学生
     */
    void deleteStudent(int student_id) throws DeleteException;

    /**
     * 修改学生
     */
    void modifyStudent(StudentInfo studentInfo) throws ModifyException;

    /**
     * 查找学生
     */
    StudentInfo getStudentInfo(String student_id) throws QueryException;

    /**
     * 学生查找成绩
     */
    double getStudentScore(int student_id,String courseName);
}
