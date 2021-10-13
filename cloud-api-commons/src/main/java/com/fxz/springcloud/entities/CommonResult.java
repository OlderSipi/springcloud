package com.fxz.springcloud.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResult <T> {
        //404 not_found

        private Integer code;
        private String message;
        private T data;

        public CommonResult(Integer code,String message){
            this(code,message,null);
        }
}
