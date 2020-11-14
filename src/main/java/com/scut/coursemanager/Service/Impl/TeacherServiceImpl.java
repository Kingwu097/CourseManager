package com.scut.coursemanager.Service.Impl;/*

 */


import com.scut.coursemanager.Entity.TeacherInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Mapper.TeacherMapper;
import com.scut.coursemanager.Service.TeacherService;
import com.scut.coursemanager.dto.TeacherInfoRequest;

import com.scut.coursemanager.utility.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    @Transactional(rollbackFor = {CreateException.class})
    public void createTeacher(TeacherInfoRequest teacherInfoRequest) throws CreateException {



        TeacherInfo teacherInfo = TeacherInfo.builder()
                .teacherId(teacherInfoRequest.getTeacherId())
                .teacherName(teacherInfoRequest.getTeacherName())
                .teacherPhone(teacherInfoRequest.getTeacherPhone())
                .teacherBirth(teacherInfoRequest.getTeacherBirth())
                .teacherSex(teacherInfoRequest.getTeacherSex())
                .teacherSubject(teacherInfoRequest.getTeacherSubject())
                .build();

        if (teacherMapper.insert(teacherInfo)!=1) {
            throw new CreateException(("教师创建失败"));
        }
    }

    @Override
    public void deleteTeacher(String teacherId) throws DeleteException {
        TeacherInfo teacherInfo = teacherMapper.selectById(teacherId);
        if (teacherInfo == null) {
            throw new DeleteException("没有该教师，删除失败");
        }
        else if (teacherMapper.deleteById(teacherId)!=1){
            throw new DeleteException("删除失败");
        }
    }

    @Override
    public void modifyTeacher(TeacherInfoRequest teacherInfoRequest) throws ModifyException {
        /**
         * 只能修改自己id的信息
         */
        String uid = jwtUtil.extractUidSubject(this.httpServletRequest);

        if (!teacherInfoRequest.getTeacherId().equals(uid)) {
            throw new ModifyException("无法修改他人信息");
        } else {
            TeacherInfo teacherInfo=TeacherInfo.builder()
                    .teacherName(teacherInfoRequest.getTeacherName())
                    .teacherId(uid)
                    .teacherPhone(teacherInfoRequest.getTeacherPhone())
                    .teacherSubject(teacherInfoRequest.getTeacherSubject())
                    .teacherSex(teacherInfoRequest.getTeacherSex())
                    .teacherBirth(teacherInfoRequest.getTeacherBirth())
                    .build();
            if(teacherMapper.updateById(teacherInfo)!=1){
                throw new ModifyException("修改信息失败");
            }
        }
    }

    @Override
    public TeacherInfo queryTeacher(String teacherId) throws QueryException {
        TeacherInfo teacherInfo=teacherMapper.selectById(teacherId);
        if (teacherInfo == null) {
            throw new QueryException("无法查找到该教师信息");
        }
        return teacherInfo;
    }
}
