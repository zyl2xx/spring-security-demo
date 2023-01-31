package com.zyl.security.service.impl;

import com.zyl.security.domain.LoginUser;
import com.zyl.security.domain.ResponseResult;
import com.zyl.security.domain.User;
import com.zyl.security.service.LoginService;
import com.zyl.security.util.JwtUtil;
import com.zyl.security.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (ObjectUtils.isEmpty(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject("login:"+userId,loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(map);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        redisCache.deleteObject("login:"+loginUser.getUser().getId());
        return new ResponseResult("退出成功");
    }
}
