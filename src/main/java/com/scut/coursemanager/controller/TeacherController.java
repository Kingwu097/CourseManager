package com.scut.coursemanager.controller;/*

 */



import com.scut.coursemanager.Entity.TeacherInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Service.TeacherService;
import com.scut.coursemanager.dto.SuccessResponse;
import com.scut.coursemanager.dto.TeacherInfoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "教师接口")
@RestController
@RequestMapping("/Teacher")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "创建老师")
    @RequestMapping(value = "/createTeacher", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createTeacher(@RequestBody TeacherInfoRequest teacherInfoRequest) {
        try{
            teacherService.createTeacher(teacherInfoRequest);
            log.info("创建老师成功");
            return ResponseEntity.ok(new SuccessResponse(true, "创建老师成功"));
        } catch (CreateException e) {
            log.info("创建老师失败");
            return ResponseEntity.ok(new SuccessResponse(false, e.getMessage()));
        }
    }
    @ApiOperation(value ="删除老师")
    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> deleteTeacher(@RequestParam("id")String teacherId) {
        try {
            teacherService.deleteTeacher(teacherId);
            log.info("删除老师成功");
            return ResponseEntity.ok(new SuccessResponse(true, "成功删除老师"));
        } catch (DeleteException e) {
            log.info("删除老师失败");
            return ResponseEntity.ok(new SuccessResponse(false, "删除老师失败"));
        }
    }
    @ApiOperation(value = "修改老师信息")
    @RequestMapping(value = "/modifyTeacher",method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> modifyTeacher(@RequestBody TeacherInfoRequest teacherInfoRequest){
        try {
            teacherService.modifyTeacher(teacherInfoRequest);
            log.info("修改老师信息成功");
            return ResponseEntity.ok(new SuccessResponse(true, "成功修改老师信息"));
        } catch (ModifyException e) {
            log.info("修改老师信息失败");
            return ResponseEntity.ok(new SuccessResponse(false, "修改老师信息失败"));
        }
    }

    @ApiOperation("查看老师信息")
    @RequestMapping(value = "/queryTeacher",method = RequestMethod.POST)
    public ResponseEntity queryTeacher(@RequestParam("id") String teacherId){
        try {

            TeacherInfo teacherInfo=teacherService.queryTeacher(teacherId);
            log.info("成功查找老师信息");
            return ResponseEntity.ok(teacherInfo);
        } catch (QueryException e) {
            log.info("查找老师信息失败");
            return ResponseEntity.ok(new SuccessResponse(false, e.getMessage()));
        }
    }
}
