package com.cjd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjd.pojo.Blog;
import com.cjd.pojo.Comment;
import com.cjd.pojo.Question;
import com.cjd.pojo.UserInfo;
import com.cjd.service.BlogService;
import com.cjd.service.CommentService;
import com.cjd.service.QuestionService;
import com.cjd.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Controller
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    BlogService blogService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;


    // 更新用户资料
    @GetMapping("/userinfo/setting/{uid}")
    public String userSetting(@PathVariable String uid, Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        // todo: 可扩展
        return "user/settings";
    }

    @PostMapping("/userinfo/update/{uid}")
    public String userInfo(@PathVariable String uid,UserInfo userInfo){
        // 修改用户信息;
        userInfoService.updateById(userInfo);
        return "redirect:/user/"+uid;
    }

    // 用户信息回填
    private void userInfoCallBack(String uid, Model model){
        UserInfo userInfo = userInfoService.getById(uid);
        model.addAttribute("userInfo",userInfo);
        if (userInfo.getHobby()!=null && !userInfo.getHobby().equals("")){
            String[] hobbys = userInfo.getHobby().split(",");
            model.addAttribute("infoHobbys",hobbys);
        }
        // 获取用户的问题，博客，回复数
        int blogCount = blogService.count(new QueryWrapper<Blog>().eq("author_id", uid));
        int questionCount = questionService.count(new QueryWrapper<Question>().eq("author_id", uid));
        int commentCount = commentService.count(new QueryWrapper<Comment>().eq("user_id", uid));
        model.addAttribute("blogCount",blogCount);
        model.addAttribute("questionCount",questionCount);
        model.addAttribute("commentCount",commentCount);
    }


}

