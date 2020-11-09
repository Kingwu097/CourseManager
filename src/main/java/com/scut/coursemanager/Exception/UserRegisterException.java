package com.scut.coursemanager.Exception;/*

 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterException extends Exception {
    private String message;
}
