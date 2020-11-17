package com.scut.coursemanager.Entity;/*

 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("takes")
public class Takes {
    @TableId(value = "record_id",type = IdType.ASSIGN_UUID)
    private String recordId;
    private String courseId;
    private String studentId;
}
