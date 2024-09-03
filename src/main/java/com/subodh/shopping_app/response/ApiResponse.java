package com.subodh.shopping_app.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;
}
