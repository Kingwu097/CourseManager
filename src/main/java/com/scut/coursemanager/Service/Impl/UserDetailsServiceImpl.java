package com.scut.coursemanager.Service.Impl;/*

 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scut.coursemanager.Entity.UserBasic;
import com.scut.coursemanager.Mapper.UserBasicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserBasicMapper userBasicMapper;

    @Override
    @Transactional(rollbackFor = {UsernameNotFoundException.class})
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserBasic> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UserBasic userBasic = userBasicMapper.selectOne(wrapper);

        if(userBasic == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户不存在");
        }
        Collection<GrantedAuthority> collection = new ArrayList<>();

        return (new User(username, userBasic.getPassword(), collection));
    }
}
