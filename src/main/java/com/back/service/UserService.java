package com.back.service;

import com.back.pojo.User;

public interface UserService {

    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);

    //用户信息更改
    void update(final User user);

    //更改用户头像
    void updateAvatar(final String url);

    //更改用户密码
    void updatePwd(final String newPwd);
}
