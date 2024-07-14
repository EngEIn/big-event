package com.back.service.impl;

import com.back.mapper.UserMapper;
import com.back.pojo.User;
import com.back.service.UserService;
import com.back.utils.Md5Util;
import com.back.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        //密码加密
        String md5String = Md5Util.getMD5String(password);
        //添加用户
        userMapper.add(username,md5String);
    }

    @Override
    public void update(final User user) {
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(final String url) {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(url, id);
    }

    @Override
    public void updatePwd(final String newPwd) {
        final String md5String = Md5Util.getMD5String(newPwd);
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        userMapper.updatePwd(md5String, id);
    }

}
