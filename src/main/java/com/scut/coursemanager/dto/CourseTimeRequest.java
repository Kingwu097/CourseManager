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
public class CourseTimeRequest {
    @ApiModelProperty(value = "年",name = "year",example = "2020")
    private int year;
    @ApiModelProperty(value = "月",name = "month",example = "12")
    private int month;
    @ApiModelProperty(value = "日",name = "day",example = "10")
    private int day;
    @ApiModelProperty(value = "星期几",name = "week",example = "星期六")
    private String week;//星期几
    @ApiModelProperty(value = "小时",name = "hour",example = "12")
    private int hour;
    @ApiModelProperty(value = "分钟",name = "minute",example = "00")
    private int minute;
    @ApiModelProperty(value = "秒",name = "second",example = "00")
    private int second;
}
