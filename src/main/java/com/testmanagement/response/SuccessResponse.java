package com.testmanagement.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuccessResponse {
    public SuccessResponse(String messages, int statusCode) {
        this.Message = messages;
        this.statusCode = statusCode;
    }
    private String Message;
    private int statusCode;
    private Object ModuleData;
   
}
