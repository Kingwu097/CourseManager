package com.scut.coursemanager.Exception;/*

 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyException extends Exception {
    String message;
}
