package com.back.mapper;

import com.back.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 添加用户
     * @param username
     * @param password
     */
    @Insert("insert into user(username,password,create_time,update_time)" +
            "   values (#{username},#{password},now(),now())")
    void add(String username, String password);

    /**
     * 根据用户名查询用户是否已存在
     */
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    /**
     * 更改用户信息
     * @param user
     */
    @Update("update user set nickname=#{nickname}, email=#{email}, update_time=now() where id=#{id}")
    void update(final User user);

    /**
     * 更改用户头像
     * @param url
     * @param id
     */
    @Update("update user set user_pic=#{url}, update_time=now() where id=#{id}")
    void updateAvatar(final String url, final Integer id);

    /**
     * 更改用户密码
     * @param md5String
     * @param id
     */
    @Update("update user set password=#{md5String}, update_time=now() where id=#{id}")
    void updatePwd(final String md5String, final Integer id);
}
