package com.scut.coursemanager.Service.Impl;/*

 */


import com.scut.coursemanager.Entity.TeacherInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Mapper.TeacherMapper;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.Service.TeacherService;
import com.scut.coursemanager.dto.TeacherInfoRequest;

import com.scut.coursemanager.dto.TeacherInfoResponse;
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
    private UserBasicMapper userBasicMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    @Transactional(rollbackFor = {CreateException.class})
    public void createTeacher(TeacherInfoRequest teacherInfoRequest) throws CreateException {

        String teacherId = jwtUtil.extractUidSubject(this.httpServletRequest);

        TeacherInfo teacherInfo = TeacherInfo.builder()
                .teacherId(teacherId)
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
    @Transactional(rollbackFor = {DeleteException.class})
    public void deleteTeacher(String username) throws DeleteException {
        String teacherId = userBasicMapper.getUserIdByName(username);
        //TeacherInfo teacherInfo = teacherMapper.selectById(teacherId);
        if (teacherId==null ) {
            throw new DeleteException("没有该教师，删除失败");
        }
        else if (teacherMapper.deleteById(teacherId)!=1){
            throw new DeleteException("删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {ModifyException.class})
    public void modifyTeacher(TeacherInfo teacherInfo) throws ModifyException {
        /**
         * 只能修改自己id的信息
         */
        String uid = jwtUtil.extractUidSubject(this.httpServletRequest);

        if (!teacherInfo.getTeacherId().equals(uid)) {
            throw new ModifyException("无法修改他人信息");
        } else {
//            TeacherInfo teacherInfoRequest=TeacherInfo.builder()
//                    .teacherName(teacherInfoRequest.getTeacherName())
//                    .teacherId(uid)
//                    .teacherPhone(teacherInfoRequest.getTeacherPhone())
//                    .teacherSubject(teacherInfoRequest.getTeacherSubject())
//                    .teacherSex(teacherInfoRequest.getTeacherSex())
//                    .teacherBirth(teacherInfoRequest.getTeacherBirth())
//                    .build();
            if(teacherMapper.updateById(teacherInfo)!=1){
                throw new ModifyException("修改信息失败");
            }
        }
    }

    /**
    * @Description:根据教师账号查找教师信息
    * @Param: [username]
    * @return: com.scut.coursemanager.dto.TeacherInfoResponse
    * @Date: 2020/11/17
    */
    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public TeacherInfoResponse queryTeacher(String username) throws QueryException {
        String teacherId=userBasicMapper.getUserIdByName(username);
        TeacherInfo teacherInfo=teacherMapper.selectById(teacherId);
        if (teacherInfo == null) {
            throw new QueryException("无法查找到该教师信息");
        }
        TeacherInfoResponse teacherInfoResponse=TeacherInfoResponse.builder()
                .teacherName(teacherInfo.getTeacherName())
                .teacherPhone(teacherInfo.getTeacherPhone())
                .teacherBirth(teacherInfo.getTeacherBirth())
                .teacherSex(teacherInfo.getTeacherSex())
                .teacherSubject(teacherInfo.getTeacherSubject())
                .build();
        return teacherInfoResponse;
    }
}
