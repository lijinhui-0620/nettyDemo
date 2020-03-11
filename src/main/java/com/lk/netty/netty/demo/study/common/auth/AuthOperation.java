package com.lk.netty.netty.demo.study.common.auth;

import com.lk.netty.netty.demo.study.common.Operation;
import com.lk.netty.netty.demo.study.common.OperationResult;

/**
 * @auther: kim
 * @create: 2020-03-05 11:17
 * @description:
 */
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    public AuthOperation(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public OperationResult execute() {
        if ("admin".equalsIgnoreCase(userName)) {
            AuthOperationResult authOperationResult = new AuthOperationResult(true);
            return authOperationResult;
        }
        return new AuthOperationResult(false);
    }
}
