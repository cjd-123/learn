package com.cjd.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjd.pojo.*;
import com.cjd.pojo.vo.LayerPhoto;
import com.cjd.pojo.vo.LayerPhotoData;
import com.cjd.service.*;
import com.cjd.utils.CjdUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Controller
public class UserController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/user/{uid}")
    public String usetIndex(@PathVariable("uid") String uid, Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        Page<Blog> blogPage = new Page<>(1,10);
        blogService.page(blogPage,new QueryWrapper<Blog>().eq("author_id",uid).orderByDesc("gmt_create"));

        // 结果
        List<Blog> records = blogPage.getRecords();
        model.addAttribute("blogList",records);
        model.addAttribute("pageParam",blogPage);

        return "user/index";
    }
    // 查询用户的博客列表
    @GetMapping("/user/blog/{uid}/{page}/{limit}")
    public String userIndexBlog(@PathVariable("uid") String  uid,@PathVariable("page") int page,@PathVariable("limit") int limit,Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        if (page < 1){
            page = 1;
        }

        Page<Blog> pageParam = new Page<>(page, limit);
        blogService.page(pageParam,new QueryWrapper<Blog>().eq("author_id",uid).orderByDesc("gmt_create"));
        List<Blog> records = pageParam.getRecords();
        model.addAttribute("blogList",records);
        model.addAttribute("pageParam",pageParam);

        return "user/index";

    }
    // 查询用户的问题列表
    @GetMapping("/user/question/{uid}/{page}/{limit}")
    public String userIndexQuestion(@PathVariable String uid,
                                    @PathVariable int page,
                                    @PathVariable int limit,
                                    Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        if (page < 1){
            page = 1;
        }

        Page<Question> pageParam = new Page<>(page, limit);
        questionService.page(pageParam,new QueryWrapper<Question>().eq("author_id",uid).orderByDesc("gmt_create"));
        List<Question> records = pageParam.getRecords();
        model.addAttribute("questionList",records);
        model.addAttribute("pageParam",pageParam);

        return "user/user-question";
    }
    // 查询用户的评论列表
    @GetMapping("/user/comment/{uid}/{page}/{limit}")
    public String userIndexComment(@PathVariable String uid,
                                   @PathVariable int page,
                                   @PathVariable int limit,
                                   Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        if (page < 1){
            page = 1;
        }

        Page<Comment> pageParam = new Page<>(page, limit);
        commentService.page(pageParam,new QueryWrapper<Comment>().eq("user_id",uid).orderByDesc("gmt_create"));
        List<Comment> records = pageParam.getRecords();
        model.addAttribute("commentList",records);
        model.addAttribute("pageParam",pageParam);

        return "user/user-comment";
    }


    // 用户信息回填
    private void userInfoCallBack(String uid, Model model) {
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

    // 捐赠layer弹窗二维码
    @GetMapping("/user/donate/{uid}")
    @ResponseBody
    public String userLayerDonate(@PathVariable String uid){
        // todo: 数据库设计
        ArrayList<LayerPhotoData> layerPhotos = new ArrayList<>();
        layerPhotos.add(new LayerPhotoData().setAlt("支付宝").setPid(1).setSrc("/images/donate/alipay.png").setThumb(""));
        layerPhotos.add(new LayerPhotoData().setAlt("微信").setPid(2).setSrc("/images/donate/wechat.jpg").setThumb(""));

        LayerPhoto donate = new LayerPhoto().setTitle("赞赏").setId(666).setStart(1);
        donate.setData(layerPhotos);

        String donateJsonString = JSONObject.toJSONString(donate);
        CjdUtil.print(donateJsonString);
        return donateJsonString;
    }
    // 更新头像
    @GetMapping("/user/update-avatar/{uid}")
    public String toUpdateAvatar(@PathVariable String uid,Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        return "user/update-avatar";
    }
    @PostMapping("/upload")
    public String updateAvatar(@RequestPart(value = "image") MultipartFile file,@RequestParam("uid") String uid) throws IOException {

        User user = userService.getOne(new QueryWrapper<User>().eq("uid", uid));

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/upload/";

        //按照用户进行分类：
        path = path + "avg_head";

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }

        //上传文件地址
        CjdUtil.print("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "jd-"+ UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        // KuangUtils.print(originalFilename);
        assert originalFilename != null;
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i + 1);

        String outFilename = filename + "."+suffix;
        CjdUtil.print("文件名：" + outFilename);

        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ outFilename));
        String avgPath = realPath + "/" + outFilename;
        user.setAvatar(avgPath);
        userService.updateById(user);
        return "user/update-avatar/"+uid;
    }

}

