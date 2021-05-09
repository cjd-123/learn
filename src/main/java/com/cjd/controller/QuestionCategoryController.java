package com.cjd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjd.pojo.Question;
import com.cjd.pojo.QuestionCategory;
import com.cjd.service.QuestionCategoryService;
import com.cjd.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
public class QuestionCategoryController {

    @Autowired
    QuestionCategoryService questionCategoryService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/category/{cid}/{page}/{limit}")
    public String questionPage(
            @PathVariable("cid") int cid,
            @PathVariable("page") int page,
            @PathVariable("limit") int limit,
            Model model){
        if (page<1){
            page = 1;
        }
        // 查询这个分类下的所有问题，获取查询的数据信息
        Page<Question> pageParam = new Page<>(page,limit);
        questionService.page(pageParam,new QueryWrapper<Question>().eq("category_id",cid).orderByDesc("gmt_create"));
        List<Question> records = pageParam.getRecords();
        model.addAttribute("questionList",records);
        model.addAttribute("pageParam",pageParam);

        // 查询这个分类信息
        QuestionCategory category = questionCategoryService.getById(cid);
        model.addAttribute("thisCategoryName",category.getCategory());

        // 全部分类信息
        List<QuestionCategory> categoryList = questionCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);

        return "question/list";

    }

}

