package com.scut.coursemanager.Entity;/*

 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "course_info")
public class CourseInfo {
    @TableId(value = "course_id",type = IdType.ASSIGN_UUID)
    private String courseId;
    private String courseName;
    private String teacherId;
    private String courseTeacherName;
    private String courseTime;
    private String classroom;


}
