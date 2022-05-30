package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.KeyDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.service.CommentService;
import com.markerhub.service.UserService;
import com.markerhub.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@RestController
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().eq("page", currentPage));
        List<Map<String, Object>> blogList = new LinkedList<>();
        for (Blog blog : blogs) {
            HashMap<String , Object> map = new HashMap<>();
            User user = userService.getById(blog.getUserId());
            map.put("blog", blog);
            map.put("user", user);
            blogList.add(map);
        }
        return Result.succ(blogList);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        HashMap<String , Object> map = new HashMap<>();
        Blog blog = blogService.getById(id);
        if (blog == null){
            return Result.fail("该博客已被删除");
        }else {
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("bid", id));

            LinkedList<Map<String ,Object>> list = new LinkedList<>();
            for (Comment comment : comments) {
                HashMap<String , Object> map1 = new HashMap<>();
                User cUser = userService.getById(comment.getUid());
                map1.put("comment", comment);
                map1.put("user", cUser);
                list.add(map1);
            }
            User user = userService.getById(blog.getUserId());
            map.put("blog", blog);
            map.put("user", user);
            map.put("comments", list);
            return Result.succ(map);
        }

    }

    @GetMapping("/myblog/{uid}")
    public Result myBlog(@PathVariable(name = "uid") Long id) {
        List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().eq("user_id", id));
        LinkedList<Map<String ,Object>> list = new LinkedList<>();
        for (Blog blog : blogs) {
            HashMap<String , Object> map = new HashMap<>();
            User user = userService.getById(id);
            map.put("blog", blog);
            map.put("user", user);
            list.add(map);
        }
            return Result.succ(list);
    }

    @PostMapping("/blog/key")
    public Result detail(@RequestBody String keyword) {

        KeyDto s = JSON.parseObject(keyword, KeyDto.class);
        List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().like("title", s.getKeyWord()));
        if (blogs.isEmpty()){
            return Result.fail("该博客已被删除");
        }else {
            List<Map<String, Object>> blogList = new LinkedList<>();
            for (Blog blog : blogs) {
                HashMap<String , Object> map = new HashMap<>();
                User user = userService.getById(blog.getUserId());
                map.put("blog", blog);
                map.put("user", user);
                blogList.add(map);
            }

            return Result.succ(blogList);
        }
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

//        Assert.isTrue(false, "公开版不能任意编辑！");

        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {

            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);
    }

    @PostMapping("/blog/add")
    public Result add(@RequestBody Blog blog){
        System.out.println(blog);
        Date date = new Date();
        LocalDateTime ldt = date.toInstant()
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        blog.setCreated(ldt);
        int page;
        for (page=1 ;page<1000;page ++ ){
            int count = blogService.count(new QueryWrapper<Blog>().eq("page", page));
            if (count < 8) {
                break;
            }
        }
        System.out.println(page);
        blog.setPage(page);
        blogService.save(blog);
        return Result.succ(200, "发表成功","");
    }
}
