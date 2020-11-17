package com.scut.coursemanager.dto;/*

 */

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
public class TakesRequest {
    //感觉不用学号
    @ApiModelProperty(value = "学号",name = "username",example = "201830663155")
    private String username;

    private List<String> courseIdList;
}
