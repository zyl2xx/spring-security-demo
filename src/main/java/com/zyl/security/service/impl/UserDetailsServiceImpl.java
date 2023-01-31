package com.zyl.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyl.security.domain.LoginUser;
import com.zyl.security.domain.User;
import com.zyl.security.mapper.MenuMapper;
import com.zyl.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName,userName);
        User user = userMapper.selectOne(lqw);
        if (ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户名或密码错误");
        }

        List<String> menuList = menuMapper.selectPermsByUserId(user.getId());
//        List<String> menuList = new ArrayList<>(Arrays.asList("test"));

        return new LoginUser(user,menuList);
    }
}
