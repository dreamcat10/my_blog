package com.markerhub.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.InLoginDto;
import com.markerhub.common.dto.LoginDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if(!user.getPassword().equals(loginDto.getPassword())){
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("created", user.getCreated())
                .put("lastLogin", user.getLastLogin())
                .map()
        );
    }

    @PostMapping("/inLogin")
    public Result inLogin(@Validated @RequestBody User user , HttpServletResponse response){
        Date date = new Date();

        LocalDateTime ldt = date.toInstant()
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        user.setCreated(ldt);
        boolean flag = userService.save(user);

        String jwt = jwtUtils.generateToken(user.getId());
        if (flag){
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-control-Expose-Headers", "Authorization");
            return Result.succ(user);
        }else {
            return Result.fail("注册失败");
        }

    }

    @GetMapping("/logout/{id}")
    public Result logout(@PathVariable(name = "id") Long id) {
        Date date = new Date();
        LocalDateTime ldt = date.toInstant()
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        User user = userService.getById(id);
            user.setLastLogin(ldt);
            userService.update(user, new QueryWrapper<User>().eq("id", id));
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return Result.succ(null);
    }

}
