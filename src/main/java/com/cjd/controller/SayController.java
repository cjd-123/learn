package com.cjd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjd.pojo.Say;
import com.cjd.service.SayService;
import com.cjd.utils.CjdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Controller
public class SayController {

    @Autowired
    SayService sayService;

    @GetMapping("/say")
    public String userIndexBlog(Model model){
        Page<Say> pageParam = new Page<>(1, 50);
        sayService.page(pageParam,new QueryWrapper<Say>().orderByDesc("gmt_create"));
        // 结果
        List<Say> sayList = pageParam.getRecords();
        model.addAttribute("sayList",sayList);
        model.addAttribute("pageParam",pageParam);

        return "page/say";
    }

    @PostMapping("/say/{role}")
    public String saveSay(@PathVariable("role") int role, Say say){
        // 防止请求提交
        if (role!=1){
            return "redirect:/say";
        }

        say.setId(CjdUtil.getUuid());
        say.setGmtCreate(CjdUtil.getTime());
        // 结果
        sayService.save(say);
        return "redirect:/say";
    }


}

