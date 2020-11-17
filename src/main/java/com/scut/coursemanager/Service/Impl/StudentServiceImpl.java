package com.scut.coursemanager.Service.Impl;/*

 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scut.coursemanager.Entity.CourseInfo;
import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Entity.Takes;
import com.scut.coursemanager.Entity.UserBasic;
import com.scut.coursemanager.Exception.*;
import com.scut.coursemanager.Mapper.CourseMapper;
import com.scut.coursemanager.Mapper.StudentMapper;
import com.scut.coursemanager.Mapper.TakesMapper;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.Service.StudentService;
import com.scut.coursemanager.dto.DropRequest;
import com.scut.coursemanager.dto.StudentInfoRequest;
import com.scut.coursemanager.dto.TakesRequest;
import com.scut.coursemanager.utility.JwtUtil;
import com.scut.coursemanager.utility.UUIDUtil;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private UserBasicMapper userBasicMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TakesMapper takesMapper;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private UUIDUtil uuidUtil;

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
    public void createStudent(StudentInfoRequest studentInfoRequest) throws CreateException {

        String studentId = jwtUtil.extractUidSubject(this.httpServletRequest);

        StudentInfo studentInfo1=StudentInfo.builder()
                .studentId(studentId)
                .studentName(studentInfoRequest.getStudentName())
                .studentSex(studentInfoRequest.getStudentSex())
                .studentPhone(studentInfoRequest.getStudentPhone())
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
    public void deleteStudent(String username) throws DeleteException {
        String uuid = jwtUtil.extractUidSubject(this.httpServletRequest);
        String student_id = userBasicMapper.getUserIdByName(username);
        if(!uuid.equals("1")){
            throw new DeleteException("你不是管理员，没有权限进行此操作");
        } else if (student_id==null) {
            throw new DeleteException("没有该学生，删除失败");
        }
        if(studentMapper.deleteById(student_id)==1){
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
        if(!uid.equals("1")){
            if(!uid.equals(studentInfo.getStudentId())){
                throw new ModifyException("你没有权限进行此操作。");
            }


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

    /**
    * @Description:根据学生账号查找学生信息
    * @Param: [username]
    * @return: com.scut.coursemanager.Entity.StudentInfo
    * @Date: 2020/11/15
    */
    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public StudentInfo getStudentInfo(String username) throws QueryException {
        String student_id=userBasicMapper.getUserIdByName(username);
        StudentInfo studentInfo=studentMapper.selectById(student_id);
        if (studentInfo == null) {
            throw new QueryException("无法查找到该学生信息");
        }
        return studentInfo;
    }


    /**
    * @Description:选课
    * @Param: [takesRequest]
    * @return: void
    * @Date: 2020/11/17
    */
    @Override
    @Transactional(rollbackFor = {CourseChoseException.class})
    public void CourseChosen(TakesRequest takesRequest) throws CourseChoseException {
        String uid = jwtUtil.extractUidSubject(this.httpServletRequest);

        String identity = jwtUtil.extractIdentitySubject(this.httpServletRequest);

        //String student_id=userBasicMapper.getUserIdByName(takesRequest.getUsername());

//        if(!student_id.equals(uid)){
//            throw new CourseChoseException("你没有权限进行此操作");
//        }
        if(!identity.equals("Student"))
        {
            throw new CourseChoseException("非学生无权限进行此操作");
        }
        for(String courseId:takesRequest.getCourseIdList()){
            CourseInfo courseInfo = courseMapper.selectById(courseId);
            if(courseInfo==null){
                throw new CourseChoseException("没有id为：" + courseId + "的课程，选课失败");
            }
            String recordId=uuidUtil.get32UUIDString();
            Takes takes=Takes.builder()
                    .recordId(recordId)
                    .studentId(uid)
                    .courseId(courseId)
                    .build();

            if(takesMapper.insert(takes)!=1){
                throw new CourseChoseException("选课失败");
            }
        }

    }
    /**
    * @Description:退课
    * @Param: [courseId]
    * @return: void
    * @Date: 2020/11/17
    */
    @Override
    @Transactional(rollbackFor = {CourseDeleteException.class})
    public void CourseDrop(DropRequest dropRequest) throws CourseDeleteException {
        String studentId = jwtUtil.extractUidSubject(this.httpServletRequest);
        List<String> courseIdList=dropRequest.getCourseIdList();
        for (String courseId : courseIdList) {
            QueryWrapper<Takes> takesQueryWrapper=new QueryWrapper<Takes>().eq("student_id", studentId).eq("course_id", courseId);
            Takes takes = takesMapper.selectOne(takesQueryWrapper);
            if (takes==null) {
                throw new CourseDeleteException("你没有该课程记录，退课失败");
            }
            if(takesMapper.delete(takesQueryWrapper)!=1){
                throw new CourseDeleteException("你退课失败了");
            }
        }

    }
}
