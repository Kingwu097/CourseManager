package com.scut.coursemanager.controller;/*

 */

import com.scut.coursemanager.Exception.UserRegisterException;
import com.scut.coursemanager.Service.UserBasicService;
import com.scut.coursemanager.dto.RegisterRequest;
import com.scut.coursemanager.dto.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "用户注册接口",tags = "用户注册接口")
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserBasicController {
    @Resource
    private UserBasicService userBasicService;

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterRequest registerRequest){
        try{
            userBasicService.UserRegister(registerRequest);
            log.info("用户注册成功");
            return ResponseEntity.ok(new SuccessResponse(true, "注册成功"));

        } catch (UserRegisterException e) {
            log.info("用户注册失败");
            return ResponseEntity.ok(new SuccessResponse(false, e.getMessage()));
        }
    }
}
