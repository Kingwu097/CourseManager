package com.scut.coursemanager.Service.Impl;/*

 */

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Mapper.StudentMapper;
import com.scut.coursemanager.Service.StudentService;
import com.scut.coursemanager.utility.JwtUtil;
import org.springframework.dao.QueryTimeoutException;
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

    @Resource
    private JwtUtil jwtUtil;

    /**
    * @Description:创建学生
    * @Param: [studentInfo]
    * @return: void
    * @Date: 2020/11/10
    */
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
    /**
    * @Description:删除学生
    * @Param: [student_id]
    * @return: void
    * @Date: 2020/11/10
    */
    @Override
    @Transactional(rollbackFor = {DeleteException.class})
    public void deleteStudent(String student_id) throws DeleteException {
        if(studentMapper.deleteById(student_id)!=1){
            throw new DeleteException("删除失败");

        }
    }
    /**
    * @Description:修改学生
    * @Param: [studentInfo]
    * @return: void
    * @Date: 2020/11/10
    */
    @Override
    @Transactional(rollbackFor = {ModifyException.class})
    public void modifyStudent(StudentInfo studentInfo) throws ModifyException{
        /**
         * 只能修改自己id的信息
         */
        String uid = jwtUtil.extractUidSubject(this.httpServletRequest);
        if(uid!=studentInfo.getStudentId()){
            throw new ModifyException("你没有权限进行此操作。");
        }

        //StudentInfo oldStudentInfo = studentMapper.selectById(uid);

        StudentInfo newStudentInfo = StudentInfo.builder()
                .studentId(uid)
                .studentName(studentInfo.getStudentName())
                .studentPhone(studentInfo.getStudentPhone())
                .studentSex(studentInfo.getStudentSex())
                .build();

        if (studentMapper.updateById(newStudentInfo) != 1) {
            throw new ModifyException("修改失败");
        }

    }

    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public StudentInfo getStudentInfo(String student_id) throws QueryException {
        StudentInfo studentInfo=studentMapper.selectById(student_id);
        if (studentInfo == null) {
            throw new QueryException("无法查找到该学生信息");
        }
        return studentInfo;
    }

    @Override
    public double getStudentScore(int student_id, String courseName) {
        return 0;
    }
}
