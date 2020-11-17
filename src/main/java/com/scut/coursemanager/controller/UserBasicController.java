package com.scut.coursemanager.controller;/*

 */

import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.UserRegisterException;
import com.scut.coursemanager.Service.UserBasicService;
import com.scut.coursemanager.dto.FailResponse;
import com.scut.coursemanager.dto.RegisterRequest;
import com.scut.coursemanager.dto.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){
        try{
            userBasicService.UserRegister(registerRequest);
            log.info("用户注册成功");
            return ResponseEntity.ok(new SuccessResponse(true, "注册成功"));

        } catch (UserRegisterException e) {
            log.info("用户注册失败");
            return ResponseEntity.ok(new FailResponse(false, e.getMessage()));
        }
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/modifyPassword",method = RequestMethod.POST)
    public ResponseEntity modifyPassword(@RequestParam("password") String newPassword){
        try {
            userBasicService.modifyPassword(newPassword);
            log.info("密码修改成功");
            return ResponseEntity.ok(new SuccessResponse(true, "密码修改成功"));
        } catch (ModifyException e) {
            log.info("密码修改失败");
            return ResponseEntity.ok(new FailResponse(false, "密码修改失败"));
        }
    }
}
