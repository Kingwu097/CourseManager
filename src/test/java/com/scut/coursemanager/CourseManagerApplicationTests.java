package com.scut.coursemanager;

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Service.Impl.StudentServiceImpl;
import com.scut.coursemanager.Service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CourseManagerApplicationTests {

    @Resource
    private StudentService studentService;

    @Test
    void contextLoads() throws CreateException {
        //StudentServiceImpl example=new StudentServiceImpl();
        StudentInfo studentInfo=new StudentInfo();
        //studentInfo.setStudentId(199);
        studentInfo.setStudentName("张三");
        studentInfo.setStudentPhone("2663365");
        studentInfo.setStudentSex("男");
        studentService.createStudent(studentInfo);



    }

}
