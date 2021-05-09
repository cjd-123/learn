package com.cjd.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // 定位到登录页
    @GetMapping({"/toLogin", "/"})
    public String toLogin(){
        return "admin/login";
    }

    @PostMapping("login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){

        if (username.equals("cjdadmin")&& password.equals("123456")){
            return "admin/index";
        }
        return "admin/login";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "admin/welcome";
    }

}
