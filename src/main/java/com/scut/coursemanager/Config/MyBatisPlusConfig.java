package com.scut.coursemanager.Config;/*

 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.scut.coursemanager.Mapper"})
public class MyBatisPlusConfig {
}
