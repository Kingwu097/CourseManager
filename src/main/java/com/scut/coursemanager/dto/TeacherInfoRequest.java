package com.scut.coursemanager.dto;/*

 */

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfoRequest {
    @ApiModelProperty(value = "教师id",name = "teacherId",example = "1")
    private String teacherId;
    @ApiModelProperty(value = "教师名字",name = "teacherName",example = "王五")
    private String teacherName;
    @ApiModelProperty(value = "教师电话",name = "teacherPhone",example = "18824683859")
    private String teacherPhone;
    @ApiModelProperty(value = "教师科目",name = "teacherSubject",example = "高数")
    private String teacherSubject;
    @ApiModelProperty(value = "教师生日",name ="teacherBirth",example = "1980-8-1")
    private String teacherBirth;
    @ApiModelProperty(value = "教师性别",name = "teacherSex",example = "女")
    private String teacherSex;
}
