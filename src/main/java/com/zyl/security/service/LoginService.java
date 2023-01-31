package com.zyl.security.service;

import com.zyl.security.domain.ResponseResult;
import com.zyl.security.domain.User;

public interface LoginService {
    ResponseResult login(User user);


    ResponseResult logout();
}
