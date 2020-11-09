package com.scut.coursemanager.Exception;/*

 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateException extends Exception {
    private String message;
}
