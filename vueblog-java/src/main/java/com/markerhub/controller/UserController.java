package com.markerhub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

    @PostMapping("/upload/{id}")
    public Result upload(@PathVariable(value = "id") Long id, @RequestParam(value = "file")MultipartFile file){
            if (file.isEmpty()){
                return Result.fail("文件为空");
            }
            String filename = file.getOriginalFilename();
            String filePath = "D:\\avatar\\";
        File dest = new File(filePath + filename );
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try{
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = userService.getById(id);
        user.setAvatar( filePath + filename );
        userService.update(user, new QueryWrapper<User>().eq("id", id));
        return Result.succ(200, "上传成功","");
    }

}
