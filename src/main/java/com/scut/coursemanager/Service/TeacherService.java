package com.scut.coursemanager.Service;


import com.scut.coursemanager.Entity.TeacherInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.dto.TeacherInfoRequest;
import com.scut.coursemanager.dto.TeacherInfoResponse;


public interface TeacherService {

    void createTeacher(TeacherInfoRequest teacherInfoRequest) throws CreateException;

    void deleteTeacher(String username) throws DeleteException;
    //这里用TeacherInfo是为了id判断
    void modifyTeacher(TeacherInfo teacherInfo)throws ModifyException;

    TeacherInfoResponse queryTeacher(String username) throws QueryException;
}
