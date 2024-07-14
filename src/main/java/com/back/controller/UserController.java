package com.back.controller;

import com.back.pojo.Result;
import com.back.pojo.User;
import com.back.service.UserService;
import com.back.utils.JwtUtil;
import com.back.utils.Md5Util;
import com.back.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

import static com.back.interceptor.LoginInterceptor.AUTHORIZATION_HEADER;

@Validated
@RequestMapping
@RestController("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result<User> register(@Pattern(regexp = "^\\S{5,16}$") final String username, @Pattern(regexp = "^\\S{5,16}$") final String password) {
        final User existingUser = userService.findByUserName(username);
        if (existingUser == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") final String username, @Pattern(regexp = "^\\S{5,16}$") final String password) {
        final User existingUser = userService.findByUserName(username);
        if (existingUser == null) {
            return Result.error("用户不存在");
        }
        if (Md5Util.getMD5String(password).equals(existingUser.getPassword())) {
            final Map<String, Object> map = Map.of("id", existingUser.getId(),
                    "username", existingUser.getUsername());
            final String token = JwtUtil.genToken(map);
            // Save token to redis
            final ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            // If key is token, and when we validate, and the token doesn't exist, then it has expired.
            operations.set(token, token, Duration.ofHours(12));
            // Return JWT token
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    /**
     * 查看用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo() {

        final Map<String, Object> map = ThreadLocalUtil.get();
        final String username = (String) map.get("username");
        final User user = userService.findByUserName(username);
        return Result.success(user);
    }

    /**
     * 更改用户信息
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody @Validated final User user) {
        final Map<String, Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        if (user.getId().equals(id)) {
            userService.update(user);
            return Result.success();
        } else {
            return Result.error("非本人id");
        }
    }

    /**
     * 更改用户头像
     * @param avatarUrl
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL final String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 更改用户密码
     * @param params
     * @param token
     * @return
     */
    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> params, @RequestHeader(AUTHORIZATION_HEADER) final String token) {
        final String oldPwd = params.get("old_pwd");
        final String newPwd = params.get("new_pwd");
        final String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        final Map<String, Object> map = ThreadLocalUtil.get();
        final String username = (String) map.get("username");
        final User user = userService.findByUserName(username);
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次结果不一样");
        }
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("密码填写不正确");
        }

        userService.updatePwd(newPwd);
        // Delete token in redis.
        final ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }

}
