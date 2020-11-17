package com.scut.coursemanager.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.coursemanager.Entity.UserBasic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserBasicMapper extends BaseMapper<UserBasic> {
    @Select("select user_id from user_basic where username=#{username}")
    String getUserIdByName(@Param("username") String username);

    @Select("select username from user_basic where user_id=#{userId}")
    String getUsernameByUserId(@Param("userId") String userId);
}
