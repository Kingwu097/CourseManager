package com.scut.coursemanager.Service;

import com.scut.coursemanager.Entity.CourseInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.dto.CourseInfoRequest;
import com.scut.coursemanager.dto.CourseInfoResponse;

import java.util.List;

public interface CourseService {
    //创建课程
    void createCourse(CourseInfoRequest courseInfoRequest) throws CreateException;
    //删除课程
    void deleteCourse(String courseId) throws DeleteException;
    //更新课程
    void modifyCourse(CourseInfo courseInfo) throws ModifyException;
    //查找全部课程
    List<CourseInfoResponse> queryAllCourse() throws QueryException;
    //根据指定老师课程
    List<CourseInfoResponse> queryTeaCourse(String username) throws QueryException;
    //根据指定学生课程
    List<CourseInfoResponse> queryStuCourse(String username) throws QueryException;
    //根据课程名字查找课程
    List<CourseInfoResponse> queryCourseByName(String CourseName) throws QueryException;
}
