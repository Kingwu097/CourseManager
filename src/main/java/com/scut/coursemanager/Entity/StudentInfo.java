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
@TableName("student_info")
public class StudentInfo {
    @TableId(value = "student_id",type = IdType.ASSIGN_UUID)
    private String studentId;
    private String studentName;
    private String studentSex;
    private String studentPhone;

}
