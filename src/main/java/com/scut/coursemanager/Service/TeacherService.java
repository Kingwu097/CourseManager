package com.scut.coursemanager.Service;


import com.scut.coursemanager.Entity.TeacherInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.dto.TeacherInfoRequest;


public interface TeacherService {

    void createTeacher(TeacherInfoRequest teacherInfoRequest) throws CreateException;

    void deleteTeacher(String teacherId) throws DeleteException;

    void modifyTeacher(TeacherInfoRequest teacherInfoRequest)throws ModifyException;

    TeacherInfo queryTeacher(String teacherId) throws QueryException;
}
