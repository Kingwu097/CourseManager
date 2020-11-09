package com.scut.coursemanager.dto;/*

 */

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @ApiModelProperty(value = "用户名/手机号码",name = "username",example = "19927524782")
    private String username;
    @ApiModelProperty(value = "密码",name = "password",example = "123456")
    private String password;

}
