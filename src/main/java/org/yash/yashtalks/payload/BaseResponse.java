package org.yash.yashtalks.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private String message;
    private Integer status;
    private Object response;

    public BaseResponse setData(String message, Integer status, Object response) {
        this.message = message;
        this.status = status;
        this.response = response;
        return this;
    }

    public BaseResponse setData(String message, Integer status) {
        this.status = status;
        this.message = message;
        return this;
    }

    public BaseResponse setInternalServerError() {
        this.status = 500;
        this.message = "Internal Server Error";
        return this;
    }

    public BaseResponse setUnAuthorizedServer(){
        this.status = 401;
        this.message = "Unauthorized Server";
        return this;
    }

    public BaseResponse setBadRequest(){
        this.status = 400;
        this.message = "Bad Request";
        return this;
    }

}