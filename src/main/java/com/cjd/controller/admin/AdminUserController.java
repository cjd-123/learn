package com.cjd.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjd.pojo.User;
import com.cjd.service.UserService;
import com.cjd.utils.CjdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    UserService userService;


    @GetMapping("/userlist")
    public String userList(Model model){
        Page<User> page = new Page<>(1,5);
        userService.page(page,null);
        List<User> list = page.getRecords();
        model.addAttribute("pageParam",page);
        model.addAttribute("userList",list);
        return "admin/userlist";
    }
    @GetMapping("/userlist/{page}")
    public String userListPage(@PathVariable("page") int page, Model model){
        Page<User> userPage = new Page<>(page,5);
        userService.page(userPage,null);
        List<User> list = userPage.getRecords();
        model.addAttribute("pageParam",userPage);
        model.addAttribute("userList",list);
        return "admin/userlist";
    }
    @GetMapping({"/userlist/toUserAdd","/toUserAdd"})
    public String toUserAdd(){
        return "admin/useradd";
    }
    @PostMapping("/useradd")
    public void userAdd(@RequestParam("username") String username,
                          @RequestParam("password") String password
                         ){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setUid(CjdUtil.getUuid());
            user.setRoleId(2);
            user.setAvatar("/images/avatar/avatar-1.jpg");
            user.setGmtCreate(new Date());
            user.setLoginDate(new Date());
            userService.save(user);
    }

    @GetMapping("/userdel/{id}")
    public String userDel(@PathVariable("id") int id){
        User user = userService.getById(id);
        if (user != null){
            userService.removeById(id);
        }
        CjdUtil.print("删除失败");
        return "admin/userlist";
    }





}
