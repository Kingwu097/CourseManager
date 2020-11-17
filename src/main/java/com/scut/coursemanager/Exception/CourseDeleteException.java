package com.scut.coursemanager.Exception;/*

 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDeleteException extends Exception {
    private String message;
}
