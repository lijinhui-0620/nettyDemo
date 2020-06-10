package com.ljk.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther: kim
 * @create: 2020-05-15 09:02
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private int code = 200;
    private String message;
    private T data;

    public CommonResult(int code, String message) {
        this(code, message, null);
    }

}
