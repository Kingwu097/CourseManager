package com.scut.coursemanager.dto;/*

 */

import com.scut.coursemanager.Entity.CourseTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CourseInfoResponse {

    @ApiModelProperty(value = "课程id" , name = "courseId" ,example = "12345aqwe21dawqweqwerqw")
    private String courseId;
    @ApiModelProperty(value = "课程名字",name = "courseName",example = "高数")
    private String courseName;
    @ApiModelProperty(value = "教师id",name = "teacherId",example = "1")
    private String teacherId;
    @ApiModelProperty(value = "课程教师名字",name = "courseTeacherName",example = "王五")
    private String courseTeacherName;
    @ApiModelProperty(value = "课程次数",name = "courseTimes",example = "40")
    private String courseTimes;
    @ApiModelProperty(value = "课程教室",name = "classroom",example = "A1 202")
    private String classroom;

    private List<CourseTime> timeList;
}
