package com.scut.coursemanager.controller;/*

 */

import com.scut.coursemanager.Entity.CourseInfo;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Service.CourseService;
import com.scut.coursemanager.dto.CourseInfoRequest;
import com.scut.coursemanager.dto.CourseInfoResponse;
import com.scut.coursemanager.dto.FailResponse;
import com.scut.coursemanager.dto.SuccessResponse;
import com.scut.coursemanager.utility.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "课程管理接口")
@RequestMapping("/Course")
@RestController
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private JwtUtil jwtUtil;

    @ApiOperation(value = "创建课程" )
    @RequestMapping(value = "/createCourse",method = RequestMethod.POST)
    public ResponseEntity createCourse(@RequestBody CourseInfoRequest courseInfoRequest){
        try{
            courseService.createCourse(courseInfoRequest);
            log.info("课程创建成功");
            return ResponseEntity.ok(new SuccessResponse(true, "课程创建成功"));
        } catch (Exception e) {
            log.info("课程创建失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation(value = "删除课程")
    @RequestMapping(value = "/deleteCourse",method = RequestMethod.POST)
    public ResponseEntity deleteCourse(@RequestParam("id") String courseId){
        try{
            courseService.deleteCourse(courseId);
            log.info("删除课程成功");
            return ResponseEntity.ok(new SuccessResponse(true, "课程删除成功"));
        } catch (DeleteException e) {
            log.info("课程删除失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }
    @ApiOperation("修改课程")
    @RequestMapping(value = "/modifyCourse",method = RequestMethod.POST)
    public ResponseEntity modifyCourse(@RequestBody CourseInfo courseInfo){
        try {
            courseService.modifyCourse(courseInfo);
            log.info("修改课程成功");
            return ResponseEntity.ok(new SuccessResponse(true, "课程修改成功"));
        } catch (ModifyException e) {
            log.info("课程修改失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }
    @ApiOperation("查找所有课程")
    @RequestMapping(value = "/queryAllCourse",method =RequestMethod.GET )
    public ResponseEntity queryAllCourse(){
        try {
            List<CourseInfoResponse> courseInfoResponseList=courseService.queryAllCourse();
            log.info("课程查找成功");
            return ResponseEntity.ok(courseInfoResponseList);
        } catch (Exception e) {
            log.info("课程查找失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("查找当前老师的课程(老师功能)")
    @RequestMapping(value = "/queryTeaCourse",method = RequestMethod.GET)
    public ResponseEntity queryTeaCourse(HttpServletRequest httpServletRequest){
        try {
            //获取token
            String token=null;
            String authHeader = httpServletRequest.getHeader("Authorization");
            if(authHeader !=null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
            }
            String username=jwtUtil.extractSubject(token);
            //String teacherId= jwtUtil.extractUidSubject(httpServletRequest);
            List<CourseInfoResponse> courseInfoResponseList=courseService.queryTeaCourse(username);
            log.info("当前老师课程查找成功");
            return ResponseEntity.ok(courseInfoResponseList);
        } catch (QueryException e) {
            log.info("当前老师课程查找失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("查找当前学生的课程(学生功能)")
    @RequestMapping(value = "/queryStuCourse",method = RequestMethod.GET)
    public ResponseEntity queryStuCourse(HttpServletRequest httpServletRequest){
        try {
            //获取token
            String token=null;
            String authHeader = httpServletRequest.getHeader("Authorization");
            if(authHeader !=null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
            }
            String username=jwtUtil.extractSubject(token);

            List<CourseInfoResponse> courseInfoResponseList=courseService.queryStuCourse(username);
            log.info("当前学生课程查找成功");
            return ResponseEntity.ok(courseInfoResponseList);
        } catch (QueryException e) {
            log.info("当前学生课程查找失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("查找指定学生账号课程（管理员功能）")
    @RequestMapping(value = "/querySpecificStu",method = RequestMethod.POST)
    public ResponseEntity querySpecificStu(@RequestParam("username")String username) {
        try {
            courseService.queryStuCourse(username);
            log.info("查找指定学生课程成功");
            return ResponseEntity.ok(new SuccessResponse(true, "查找指定学生课程成功"));
        } catch (Exception e) {
            log.info("查找指定学生课程失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("查找指定教师账号课程（管理员功能）")
    @RequestMapping(value = "/querySpecificTea",method = RequestMethod.POST)
    public ResponseEntity querySpecificTea(@RequestParam("username")String username) {
        try {
            courseService.queryTeaCourse(username);
            log.info("查找指定学生课程成功");
            return ResponseEntity.ok(new SuccessResponse(true, "查找指定教师课程成功"));
        } catch (Exception e) {
            log.info("查找指定教师课程失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("根据课程名字查找课程")
    @RequestMapping(value = "/queryCourseByName",method = RequestMethod.POST)
    public ResponseEntity queryCourseByName(@RequestParam("courseName")String courseName) {
        try {
            List<CourseInfoResponse> courseInfoResponseList = courseService.queryCourseByName(courseName);
            log.info("指定名字课程查找成功");
            return ResponseEntity.ok(courseInfoResponseList);
        } catch (Exception e) {
            log.info("指定名字课程查找失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

}
