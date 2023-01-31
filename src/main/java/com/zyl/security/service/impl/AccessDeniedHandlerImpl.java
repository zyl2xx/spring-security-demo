package com.zyl.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.zyl.security.domain.ResponseResult;
import com.zyl.security.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败异常处理
 * 
 * @Author: zyl
 * @Date: 2023/1/31 9:19
 */

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseResult result = new ResponseResult<>(HttpStatus.FORBIDDEN.value(),"权限不足");
        String string = JSON.toJSONString(result);
        WebUtils.renderString(response,string);
    }
}
