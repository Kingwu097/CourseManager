package com.scut.coursemanager.Exception;/*

 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String timestamp;

    private Integer status;

    private String error;

    private String message;
}
