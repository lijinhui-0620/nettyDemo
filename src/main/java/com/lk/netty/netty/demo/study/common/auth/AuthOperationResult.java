package com.lk.netty.netty.demo.study.common.auth;

import com.lk.netty.netty.demo.study.common.OperationResult;

/**
 * @auther: kim
 * @create: 2020-03-05 11:17
 * @description:
 */
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

    public AuthOperationResult(boolean passAuth) {
        this.passAuth = passAuth;
    }

    public boolean isPassAuth() {
        return passAuth;
    }
}
