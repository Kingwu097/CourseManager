package com.scut.coursemanager.controller;/*

 */

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Service.StudentService;
import com.scut.coursemanager.dto.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @ApiOperation(value = "创建学生" ,notes = "尝试一下")
    @RequestMapping(value = "/CreateStudent",method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createStudent(@RequestBody StudentInfo studentInfo) {

        try {
            studentService.createStudent(studentInfo);
            log.info("学生{} 创建成功",  studentInfo.getStudentName());
            return ResponseEntity.ok(new SuccessResponse(true, "创建学生成功"));
        } catch (CreateException e) {
            log.info("学生 {} 创建失败", studentInfo.getStudentName());
            return ResponseEntity.ok(new SuccessResponse(false, e.getMessage()));
        }
    }
    @ApiOperation("删除学生")
    @RequestMapping(value = "/DeleteStudent",method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> deleteStudent(@RequestParam("id") int studentId){
        try {
            studentService.deleteStudent(studentId);
            log.info("学生删除成功");
            return ResponseEntity.ok(new SuccessResponse(true, "删除学生成功"));
        }
        catch (DeleteException e){
            log.info("学生删除失败");
            return ResponseEntity.ok(new SuccessResponse(false, "删除学生失败"));
        }

    }

    @ApiOperation("修改学生信息")
    @RequestMapping(value = "/ModifyStudent",method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> modifyStudent(@RequestBody StudentInfo studentInfo) {
        try {
            studentService.modifyStudent(studentInfo);
            log.info("学生信息修改成功");
            return ResponseEntity.ok(new SuccessResponse(true, "修改学生成功"));

        } catch (ModifyException e) {
            log.info("学生信息修改失败");
            return ResponseEntity.ok(new SuccessResponse(false, e.getMessage()));

        }

    }

    @ApiOperation("查看学生信息")
    @RequestMapping(value = "/QueryStudent",method = RequestMethod.POST)
    public ResponseEntity queryStudent(@RequestParam("id") String studentId){
        try {

           StudentInfo studentInfo=studentService.getStudentInfo(studentId);
            log.info("成功查找学生信息");
            return ResponseEntity.ok(studentInfo);
        } catch (QueryException e) {
           log.info("查找学生信息失败");
           return ResponseEntity.ok(new SuccessResponse(false, e.getMessage()));
        }
    }


}
