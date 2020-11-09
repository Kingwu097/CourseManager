package com.scut.coursemanager.Service.Impl;/*

 */

import com.scut.coursemanager.Entity.UserBasic;
import com.scut.coursemanager.Exception.UserRegisterException;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import com.scut.coursemanager.Service.UserBasicService;
import com.scut.coursemanager.dto.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserBasicServiceImpl implements UserBasicService {
    @Resource
    private UserBasicMapper userBasicMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

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
}
