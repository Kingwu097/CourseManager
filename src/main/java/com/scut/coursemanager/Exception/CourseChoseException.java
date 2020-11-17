package com.scut.coursemanager.Exception;/*

 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseChoseException extends Exception{
    private String message;
}
