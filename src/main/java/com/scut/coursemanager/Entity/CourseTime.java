package com.scut.coursemanager.Entity;/*

 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("course_time")
public class CourseTime {
    @TableId(value = "course_id",type = IdType.ASSIGN_UUID)
    private String courseId;

    private Date date;

    private String week;

    private Time timeSlot;
}
