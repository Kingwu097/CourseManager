package com.scut.coursemanager.Service.Impl;/*

 */

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Mapper.StudentMapper;
import com.scut.coursemanager.Service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    @Transactional(rollbackFor = {CreateException.class})
    public void createStudent(StudentInfo studentInfo) throws CreateException {
        StudentInfo studentInfo1=StudentInfo.builder()
                .studentId(studentInfo.getStudentId())
                .studentName(studentInfo.getStudentName())
                .studentSex(studentInfo.getStudentSex())
                .studentPhone(studentInfo.getStudentPhone())
                .build();


        //修改持久层并判断创建是否成功
        if (studentMapper.insert(studentInfo1) != 1) {
            throw new CreateException("创建失败");
        }


    }

    @Override
    @Transactional(rollbackFor = {DeleteException.class})
    public void deleteStudent(int student_id) throws DeleteException {
        if(studentMapper.deleteById(student_id)!=1){
            throw new DeleteException("删除失败");

        }
    }

    @Override
    @Transactional(rollbackFor = {ModifyException.class})
    public void modifyStudent(StudentInfo studentInfo) throws ModifyException{


    }

    @Override
    public StudentInfo getStudentInfo(int student_id) {
        return null;
    }

    @Override
    public double getStudentScore(int student_id, String courseName) {
        return 0;
    }
}
