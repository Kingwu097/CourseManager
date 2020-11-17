package com.scut.coursemanager.utility;/*

 */


import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {
    public String get32UUIDString() {
        String uuid= UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
}
