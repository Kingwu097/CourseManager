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
public class StudentInfoRequest {
    @ApiModelProperty(value = "学生名字",name = "studentName",example = "张三")
    private String studentName;
    @ApiModelProperty(value = "学生性别",name = "studentSex",example = "男")
    private String studentSex;
    @ApiModelProperty(value = "学生手机",name = "studentPhone",example = "12615834950")
    private String studentPhone;
}
