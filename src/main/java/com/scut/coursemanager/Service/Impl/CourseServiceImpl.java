package com.scut.coursemanager.Service.Impl;/*

 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scut.coursemanager.Entity.CourseInfo;
import com.scut.coursemanager.Entity.CourseTime;
import com.scut.coursemanager.Entity.Takes;
import com.scut.coursemanager.Exception.CreateException;
import com.scut.coursemanager.Exception.DeleteException;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.QueryException;
import com.scut.coursemanager.Mapper.CourseMapper;
import com.scut.coursemanager.Mapper.CourseTimeMapper;
import com.scut.coursemanager.Mapper.TakesMapper;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.Service.CourseService;
import com.scut.coursemanager.dto.CourseInfoRequest;
import com.scut.coursemanager.dto.CourseInfoResponse;
import com.scut.coursemanager.dto.CourseTimeRequest;
import com.scut.coursemanager.utility.JwtUtil;
import com.scut.coursemanager.utility.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private UserBasicMapper userBasicMapper;
    @Resource
    private TakesMapper takesMapper;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UUIDUtil uuidUtil;
    @Resource
    private CourseTimeMapper courseTimeMapper;
    /**
    * @Description:增加课程
    * @Param: [courseInfoRequest]
    * @return: void
    * @Date: 2020/11/16
    */
    @Override
    @Transactional(rollbackFor = {CreateException.class})
    public void createCourse(CourseInfoRequest courseInfoRequest) throws CreateException {
        String identity = jwtUtil.extractIdentitySubject(this.httpServletRequest);
        //System.out.println("identity:"+identity);
        String uuid = jwtUtil.extractUidSubject(this.httpServletRequest);
        String teacherId=courseInfoRequest.getTeacherId();
        if (!identity.equals("Teacher")) {
            if(uuid!="1"){
                //System.out.println(identity);
                throw new CreateException("你没有权限进行此操作");
            }
        }

        if(!uuid.equals(teacherId)){
            //System.out.println("uuid:"+uuid+"/n"+"teacherId:"+teacherId);
            throw new CreateException("你没有权限进行此操作");
        }
        else {
            String courseId=uuidUtil.get32UUIDString();

            CourseInfo courseInfo=CourseInfo.builder()
                    .courseId(courseId)
                    .courseName(courseInfoRequest.getCourseName())
                    .courseTeacherName(courseInfoRequest.getCourseTeacherName())
                    .classroom(courseInfoRequest.getClassroom())
                    .teacherId(courseInfoRequest.getTeacherId())
                    .courseTimes(courseInfoRequest.getCourseTimes())
                    .build();

            if (courseMapper.insert(courseInfo) != 1) {
                throw new CreateException("创建课程失败");
            }

            for (CourseTimeRequest c : courseInfoRequest.getTimeList()) {
                Time time = new Time(c.getHour(), c.getMinute(), c.getSecond());
                Date date = new Date(c.getYear()-1900, c.getMonth(), c.getDay());
                //设置日期格式化样式为：yyyy-MM-dd
                //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                //格式化当前日期
                //simpleDateFormat.format(date.getTime());

                CourseTime courseTime=CourseTime.builder()
                        .courseId(courseId)
                        .timeSlot(time)
                        .week(c.getWeek())
                        .date(date)
                        .build();
                if(courseTimeMapper.insert(courseTime)!=1){
                    throw new CreateException("课程时间创建失败");
                }
            }
        }


    }

    /**
    * @Description:删除课程
    * @Param: [courseId]
    * @return: void
    * @Date: 2020/11/16
    */
    @Override
    @Transactional(rollbackFor = {DeleteException.class})
    public void deleteCourse(String courseId) throws DeleteException {
        String uid = jwtUtil.extractUidSubject(this.httpServletRequest);

        CourseInfo courseInfo = courseMapper.selectById(courseId);
        QueryWrapper<CourseTime> queryWrapper = new QueryWrapper<CourseTime>().eq("course_id", courseId);


        if(!courseInfo.getTeacherId().equals(uid)||!uid.equals("1")){
            throw new DeleteException("你没有权限进行此操作");
        }

        if (courseInfo==null) {
            throw new DeleteException("没有此课程，无法删除");
        } else if (courseMapper.deleteById(courseId) != 1) {
            throw new DeleteException("删除课程失败");
        }else if(courseTimeMapper.delete(queryWrapper)!=1){
            throw new DeleteException("课程时间删除失败");
        }
    }

    /**
    * @Description:修改课程
    * @Param: [courseInfo]
    * @return: void
    * @Date: 2020/11/16
    */
    @Override
    @Transactional(rollbackFor = {ModifyException.class})
    public void modifyCourse(CourseInfo courseInfo) throws ModifyException {
        String uuid = jwtUtil.extractUidSubject(this.httpServletRequest);
        String teacherId=courseInfo.getTeacherId();
        String identity=jwtUtil.extractIdentitySubject(this.httpServletRequest);
        //只有教师和管理员才能修改


        if ( identity.equals("Teacher")) {
            if(uuid.equals(teacherId)||uuid.equals("1")){
                if(courseMapper.updateById(courseInfo)!=1){
                    throw new ModifyException("修改失败");
                }
            }
            else {
                throw new ModifyException("你没有权限进行此操作");
            }
        }



    }
    /**
    * @Description:查找所有课程信息
    * @Param: []
    * @return: java.util.List<com.scut.coursemanager.dto.CourseInfoResponse>
    * @Date: 2020/11/15
    */
    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public List<CourseInfoResponse> queryAllCourse() throws QueryException {
        List<CourseInfo> courseInfoList = courseMapper.selectList(null);

        List<CourseInfoResponse> courseInfoResponseList=new ArrayList<>();

        for (CourseInfo courseInfo : courseInfoList) {
            //拿出该课程的时间列表
            String courseId=courseInfo.getCourseId();
            QueryWrapper<CourseTime> courseTimeQueryWrapper = new QueryWrapper<CourseTime>().eq("course_id", courseId);
            List<CourseTime> courseTimeList = courseTimeMapper.selectList(courseTimeQueryWrapper);

            CourseInfoResponse courseInfoResponse=CourseInfoResponse.builder()
                    .courseId(courseInfo.getCourseId())
                    .courseName(courseInfo.getCourseName())
                    .courseTeacherName(courseInfo.getCourseTeacherName())
                    .courseTimes(courseInfo.getCourseTimes())
                    .teacherId(courseInfo.getTeacherId())
                    .classroom(courseInfo.getClassroom())
                    .timeList(courseTimeList)
                    .build();

            courseInfoResponseList.add(courseInfoResponse);

        }
        return courseInfoResponseList;
    }
    /**
    * @Description:根据教师账号查找指定老师课程信息（管理员）
    * @Param: [username]
    * @return: java.util.List<com.scut.coursemanager.dto.CourseInfoResponse>
    * @Date: 2020/11/15
    */
    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public List<CourseInfoResponse> queryTeaCourse(String username) throws QueryException {
        String teacherId = userBasicMapper.getUserIdByName(username);
        if (teacherId==null) {
            throw new QueryException("没有找到该老师信息");
        }
        QueryWrapper<CourseInfo> courseInfoQueryWrapper = new QueryWrapper<CourseInfo>().eq("teacher_id", teacherId);
        List<CourseInfo> courseInfoList = courseMapper.selectList(courseInfoQueryWrapper);

        List<CourseInfoResponse> courseInfoResponseList=new ArrayList<>();

        for (CourseInfo courseInfo : courseInfoList) {
            //拿出该课程的时间列表
            String courseId=courseInfo.getCourseId();
            QueryWrapper<CourseTime> courseTimeQueryWrapper = new QueryWrapper<CourseTime>().eq("course_id", courseId);
            List<CourseTime> courseTimeList = courseTimeMapper.selectList(courseTimeQueryWrapper);

            CourseInfoResponse courseInfoResponse=CourseInfoResponse.builder()
                    .courseId(courseInfo.getCourseId())
                    .courseName(courseInfo.getCourseName())
                    .courseTeacherName(courseInfo.getCourseTeacherName())
                    .courseTimes(courseInfo.getCourseTimes())
                    .teacherId(courseInfo.getTeacherId())
                    .classroom(courseInfo.getClassroom())
                    .timeList(courseTimeList)
                    .build();

            courseInfoResponseList.add(courseInfoResponse);

        }
        return courseInfoResponseList;
    }
    /**
    * @Description:根据学生账号查找相关课程信息
    * @Param: [username]
    * @return: java.util.List<com.scut.coursemanager.dto.CourseInfoResponse>
    * @Date: 2020/11/17
    */
    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public List<CourseInfoResponse> queryStuCourse(String username) throws QueryException {
        String uuid = jwtUtil.extractUidSubject(this.httpServletRequest);
        String studentId = userBasicMapper.getUserIdByName(username);
        if (!uuid.equals(studentId)) {
            throw new QueryException("你无法查找其它学生课程");
        }
        //查找该学生账号对应的所有课程id
        QueryWrapper<Takes> takesQueryWrapper = new QueryWrapper<Takes>().select("course_id").eq("student_id", studentId);
        List<Takes> takesList = takesMapper.selectList(takesQueryWrapper);
        List<CourseInfoResponse> courseInfoResponseList=new ArrayList<>();

        for (Takes takes : takesList) {
            String courseId=takes.getCourseId();
            CourseInfo courseInfo = courseMapper.selectById(courseId);
            QueryWrapper<CourseTime> courseTimeQueryWrapper = new QueryWrapper<CourseTime>().eq("course_id", courseId);
            List<CourseTime> courseTimeList = courseTimeMapper.selectList(courseTimeQueryWrapper);

            CourseInfoResponse courseInfoResponse=CourseInfoResponse.builder()
                    .courseId(courseInfo.getCourseId())
                    .courseName(courseInfo.getCourseName())
                    .courseTeacherName(courseInfo.getCourseTeacherName())
                    .courseTimes(courseInfo.getCourseTimes())
                    .teacherId(courseInfo.getTeacherId())
                    .classroom(courseInfo.getClassroom())
                    .timeList(courseTimeList)
                    .build();

            courseInfoResponseList.add(courseInfoResponse);
        }


        return courseInfoResponseList;
    }


    /**
    * @Description:根据课程名字查找相关课程
    * @Param: [CourseName]
    * @return: java.util.List<com.scut.coursemanager.dto.CourseInfoResponse>
    * @Date: 2020/11/17
    */
    @Override
    @Transactional(rollbackFor = {QueryException.class})
    public List<CourseInfoResponse> queryCourseByName(String CourseName) throws QueryException {
        QueryWrapper<CourseInfo> courseInfoQueryWrapper=new QueryWrapper<CourseInfo>().eq("course_name", CourseName);
        List<CourseInfo> courseInfoList = courseMapper.selectList(courseInfoQueryWrapper);

        List<CourseInfoResponse> courseInfoResponseList=new ArrayList<>();

        for (CourseInfo courseInfo : courseInfoList) {
            //拿出该课程的时间列表
            String courseId=courseInfo.getCourseId();
            QueryWrapper<CourseTime> courseTimeQueryWrapper = new QueryWrapper<CourseTime>().eq("course_id", courseId);
            List<CourseTime> courseTimeList = courseTimeMapper.selectList(courseTimeQueryWrapper);

            CourseInfoResponse courseInfoResponse=CourseInfoResponse.builder()
                    .courseId(courseInfo.getCourseId())
                    .courseName(courseInfo.getCourseName())
                    .courseTeacherName(courseInfo.getCourseTeacherName())
                    .courseTimes(courseInfo.getCourseTimes())
                    .teacherId(courseInfo.getTeacherId())
                    .classroom(courseInfo.getClassroom())
                    .timeList(courseTimeList)
                    .build();

            courseInfoResponseList.add(courseInfoResponse);

        }
        return courseInfoResponseList;
    }
}
