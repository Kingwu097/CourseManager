package com.scut.coursemanager.Entity;/*

 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_basic")
@Builder
public class UserBasic {
    @JsonIgnore
    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;

    private String username;
    @JsonIgnore
    private String password;
}
