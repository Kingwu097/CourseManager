package com.scut.coursemanager.Entity;/*

 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


/**
* @Description:教师表
* @Author: 吴佳奇
* @Date: 2020/11/12
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("teacher_info")
public class TeacherInfo {
    @TableId(value = "teacher_id",type = IdType.ASSIGN_UUID)
    private String teacherId;
    private String teacherName;
    private String teacherPhone;
    private String teacherSubject;
    private String teacherBirth;
    private String teacherSex;
}
