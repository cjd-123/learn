package com.cjd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjd.pojo.Comment;
import com.cjd.service.CommentService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    // 删除评论
    @GetMapping("/user/comment/delete/{uid}/{cid}")
    public String deleteComment(@PathVariable("uid") String uid, @PathVariable("cid") String cid, Model model){
        commentService.remove(new QueryWrapper<Comment>().eq("comment_id",cid));
        return "redirect:/user/comment/"+uid+"/1/10";
    }

}

