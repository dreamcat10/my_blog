package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.Comment;
import com.markerhub.service.CommentService;
import com.markerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody Comment comment){
        Date date = new Date();
        LocalDateTime ldt = date.toInstant()
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        comment.setCreated(ldt);
        commentService.save(comment);

        return Result.succ(200,"成功提交","");
    }
}
