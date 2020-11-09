package com.scut.coursemanager.controller;/*

 */

import com.scut.coursemanager.Entity.StudentInfo;
import com.scut.coursemanager.Mapper.StudentMapper;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.utility.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = "认证接口")
@RestController
public class AuthenticationController {
    @Resource
    private UserBasicMapper userBasicMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private AuthenticationManager authenticationManager;

    /**
    * @Description:认证过程：
     *1.username和password被获得后封装到一个UsernamePasswordAuthenticationToken（Authentication接口的实例）的实例中
     *2.这个token被传递给AuthenticationManager进行验证
     *3.成功认证后AuthenticationManager将返回一个得到完整填充的Authentication实例
    * @Param: [username, password]
    * @return: org.springframework.http.ResponseEntity<java.lang.String>
    * @Date: 2020/11/4
    */
    @ApiOperation("登录认证")
    @RequestMapping(value = "/authentication",method = RequestMethod.POST)
    public ResponseEntity<String> authentication(@RequestParam("username") String username,@RequestParam("password") String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        HashMap<String,Object> claims=new HashMap<>();
        String uid=userBasicMapper.getUserIdByName(username);
        //获取用户的id
        StudentInfo studentInfo=studentMapper.selectById(uid);
        //根据id确认用户身份
        if(studentInfo!=null){
            claims.put("Identity", "Student");
        }
        else {
            claims.put("Identity","Null");
        }


        claims.put("uid",userBasicMapper.getUserIdByName(username));

        //生成token

        String jwt=jwtUtil.generateToken(username, claims);

        return ResponseEntity.ok(jwt);

    }


}

