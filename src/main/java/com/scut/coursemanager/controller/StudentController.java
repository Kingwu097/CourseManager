package com.scut.coursemanager.controller;/*

 */

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.*;
import com.scut.coursemanager.Service.StudentService;
import com.scut.coursemanager.dto.*;
import com.scut.coursemanager.utility.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "这个是干嘛的",tags="学生接口")
@RequestMapping("/Student")
@RestController
/*@RestController注解，相当于@Controller+@ResponseBody两个注解的结合，
返回json数据不需要在方法前面加@ResponseBody注解了，
但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面*/
@Slf4j  //日志输出
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Resource
    private JwtUtil jwtUtil;

    @ApiOperation(value = "创建学生" ,notes = "尝试一下")
    @RequestMapping(value = "/CreateStudent",method = RequestMethod.POST)
    public ResponseEntity createStudent(@RequestBody StudentInfoRequest studentInfoRequest) {

        try {
            studentService.createStudent(studentInfoRequest);
            log.info("学生{} 创建成功",  studentInfoRequest.getStudentName());
            return ResponseEntity.ok(new SuccessResponse(true, "创建学生成功"));
        } catch (CreateException e) {
            log.info("学生 {} 创建失败", studentInfoRequest.getStudentName());
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }
    @ApiOperation("删除学生")
    @RequestMapping(value = "/DeleteStudent",method = RequestMethod.POST)
    public ResponseEntity deleteStudent(@RequestParam("username") String username){
        try {
            studentService.deleteStudent(username);
            log.info("学生删除成功");
            return ResponseEntity.ok(new SuccessResponse(true, "删除学生成功"));
        }
        catch (DeleteException e){
            log.info("学生删除失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }

    }

    @ApiOperation("修改学生信息")
    @RequestMapping(value = "/ModifyStudent",method = RequestMethod.POST)
    public ResponseEntity modifyStudent(@RequestBody StudentInfo studentInfo) {
        try {
            studentService.modifyStudent(studentInfo);
            log.info("学生信息修改成功");
            return ResponseEntity.ok(new SuccessResponse(true, "修改学生成功"));

        } catch (ModifyException e) {
            log.info("学生信息修改失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));

        }

    }

    @ApiOperation("根据学生账号查看学生信息(管理员)")
    @RequestMapping(value = "/QueryStuByUsername",method = RequestMethod.POST)
    public ResponseEntity queryStuByUsername(@RequestParam("username") String username){
        try {

           StudentInfo studentInfo=studentService.getStudentInfo(username);
            log.info("成功查找学生信息");
            return ResponseEntity.ok(studentInfo);
        } catch (QueryException e) {
           log.info("查找学生信息失败");
           return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("查看学生自己信息")
    @RequestMapping(value = "/QueryStuByUsername",method = RequestMethod.GET)
    public ResponseEntity queryStudent(HttpServletRequest httpServletRequest){
        try {
            //获取token
            String token=null;
            String authHeader = httpServletRequest.getHeader("Authorization");
            if(authHeader !=null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
            }
            String username=jwtUtil.extractSubject(token);
            StudentInfo studentInfo=studentService.getStudentInfo(username);
            log.info("成功查找学生信息");
            return ResponseEntity.ok(studentInfo);
        } catch (QueryException e) {
            log.info("查找学生信息失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("学生选课")
    @RequestMapping(value = "/courseChosen", method = RequestMethod.POST)
    public ResponseEntity courseChose(@RequestBody TakesRequest takesRequest) {
        try {
            studentService.CourseChosen(takesRequest);
            log.info("选课成功");
            return ResponseEntity.ok(new SuccessResponse(true, "选课成功"));
        } catch (CourseChoseException e) {
            log.info("选课失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("学生退课")
    @RequestMapping(value = "/courseDrop", method = RequestMethod.POST)
    public ResponseEntity courseDrop(@RequestBody DropRequest dropRequest) {
        try {
            studentService.CourseDrop(dropRequest);
            log.info("学生成功退课");
            return ResponseEntity.ok(new SuccessResponse(true, "退课成功"));
        } catch (CourseDeleteException e) {
            log.info("学生退课失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

}
