package com.scut.coursemanager.controller;/*

 */



import com.scut.coursemanager.Entity.TeacherInfo;
import com.scut.coursemanager.Entity.UserBasic;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.Service.TeacherService;
import com.scut.coursemanager.dto.FailResponse;
import com.scut.coursemanager.dto.SuccessResponse;
import com.scut.coursemanager.dto.TeacherInfoRequest;
import com.scut.coursemanager.dto.TeacherInfoResponse;
import com.scut.coursemanager.utility.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "教师接口")
@RestController
@RequestMapping("/Teacher")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Resource
    private JwtUtil jwtUtill;

    @Resource
    private UserBasicMapper userBasicMapper;

    @ApiOperation(value = "创建老师")
    @RequestMapping(value = "/createTeacher", method = RequestMethod.POST)
    public ResponseEntity createTeacher(@RequestBody TeacherInfoRequest teacherInfoRequest) {
        try{
            teacherService.createTeacher(teacherInfoRequest);
            log.info("创建老师成功");
            return ResponseEntity.ok(new SuccessResponse(true, "创建老师成功"));
        } catch (CreateException e) {
            log.info("创建老师失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }
    @ApiOperation(value ="删除老师")
    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.POST)
    public ResponseEntity deleteTeacher(@RequestParam("username")String username) {
        try {
            teacherService.deleteTeacher(username);
            log.info("删除老师成功");
            return ResponseEntity.ok(new SuccessResponse(true, "成功删除老师"));
        } catch (DeleteException e) {
            log.info("删除老师失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }
    @ApiOperation(value = "修改老师信息")
    @RequestMapping(value = "/modifyTeacher",method = RequestMethod.POST)
    public ResponseEntity modifyTeacher(@RequestBody TeacherInfo teacherInfo){
        try {
            teacherService.modifyTeacher(teacherInfo);
            log.info("修改老师信息成功");
            return ResponseEntity.ok(new SuccessResponse(true, "成功修改老师信息"));
        } catch (ModifyException e) {
            log.info("修改老师信息失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("根据账号查看老师信息(管理员)")
    @RequestMapping(value = "/queryTeaByUsername",method = RequestMethod.POST)
    public ResponseEntity queryTeaByUsername(@RequestParam("username") String username){
        try {

            TeacherInfoResponse teacherInfoResponse=teacherService.queryTeacher(username);
            log.info("成功查找老师信息");
            return ResponseEntity.ok(teacherInfoResponse);
        } catch (QueryException e) {
            log.info("查找老师信息失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("查看老师自己信息(老师)")
    @RequestMapping(value = "/queryTeacher",method = RequestMethod.GET)
    public ResponseEntity queryTeacher(HttpServletRequest httpServletRequest){
        try {
            String uid = jwtUtill.extractUidSubject(httpServletRequest);
            String username = userBasicMapper.getUsernameByUserId(uid);
            TeacherInfoResponse teacherInfoResponse=teacherService.queryTeacher(username);
            log.info("成功查找老师信息");
            return ResponseEntity.ok(teacherInfoResponse);
        } catch (QueryException e) {
            log.info("查找老师信息失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }
}
