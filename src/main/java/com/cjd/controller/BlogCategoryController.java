package com.cjd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjd.pojo.Blog;
import com.cjd.pojo.BlogCategory;
import com.cjd.service.BlogCategoryService;
import com.cjd.service.BlogService;
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
public class BlogCategoryController {

    @Autowired
    BlogCategoryService blogCategoryService;

    @Autowired
    BlogService blogService;

    @GetMapping("/blog/category/{bid}/{page}/{limit}")
    public String blogPage(@PathVariable("bid") int bid, @PathVariable("page") int page, @PathVariable("limit") int limit, Model model){
        if (page<1){
            page = 1;
        }
        Page<Blog> blogPage = new Page<>();
        blogService.page(blogPage,new QueryWrapper<Blog>().eq("category_id",bid).orderByDesc("gmt_create"));
        List<Blog> records = blogPage.getRecords();
        model.addAttribute("blogList",records);
        model.addAttribute("pageParam",blogPage);

        // 查询这个分类信息
        BlogCategory blogCategory = blogCategoryService.getById(bid);
        model.addAttribute("thisCategoryName",blogCategory.getCategory());

        // 全部分类信息
        List<BlogCategory> categoryList = blogCategoryService.list(null);
        model.addAttribute("categoryList",categoryList);

        return "blog/list";
    }

}

