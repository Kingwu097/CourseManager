package com.scut.coursemanager.Service.Impl;/*

 */

import com.scut.coursemanager.Entity.UserBasic;
import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.UserRegisterException;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.Service.UserBasicService;
import com.scut.coursemanager.dto.RegisterRequest;
import com.scut.coursemanager.utility.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserBasicServiceImpl implements UserBasicService {
    @Resource
    private UserBasicMapper userBasicMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    @Transactional(rollbackFor = {UserRegisterException.class})
    public void UserRegister(RegisterRequest registerRequest) throws UserRegisterException {
        String password=passwordEncoder.encode(registerRequest.getPassword());
        UserBasic userBasic=UserBasic.builder()
                .username(registerRequest.getUsername())
                .password(password)
                .build();

        if(userBasicMapper.insert(userBasic)!=1){
            throw new UserRegisterException("注册失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {ModifyException.class})
    public void modifyPassword(String password) throws ModifyException {
        String uid = jwtUtil.extractUidSubject(this.httpServletRequest);
        String username=userBasicMapper.getUsernameByUserId(uid);
        String newPassword=passwordEncoder.encode(password);
        UserBasic userBasic=UserBasic.builder()
                .userId(uid)
                .username(username)
                .password(newPassword)
                .build();

        if(userBasicMapper.updateById(userBasic)!=1){
            throw new ModifyException("密码修改失败");
        }
    }
}
