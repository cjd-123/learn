package com.cjd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjd.pojo.Invite;
import com.cjd.pojo.User;
import com.cjd.pojo.UserInfo;
import com.cjd.pojo.vo.RegisterForm;
import com.cjd.service.InviteService;
import com.cjd.service.UserInfoService;
import com.cjd.service.UserService;
import com.cjd.utils.CjdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    InviteService inviteService;

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    // 定位到首页
    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }
    // 定位到登录页
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String register(RegisterForm registerForm, Model model){
        CjdUtil.print("注册表单信息:"+registerForm.toString());
        // 表单密码重复判断
        String password = registerForm.getPassword();
        String repassword = registerForm.getRepassword();
        if (!password.equals(repassword)){
            model.addAttribute("registerMsg","密码输入错误");
            return "register";
        }

        // 用户名已存在
        User hasUser = userService.getOne(new QueryWrapper<User>().eq("username", registerForm.getUsername()));
        if (hasUser != null){
            model.addAttribute("registerMsg","用户名已存在");
            return "register";
        }
        // 验证邀请码
        Invite invite = inviteService.getOne(new QueryWrapper<Invite>().eq("code", registerForm.getCode()));
        if (invite == null){
            model.addAttribute("registerMsg","邀请码无效");
            return "register";
        }else {
            if (invite.getStatus() == 1){
                model.addAttribute("registerMsg","邀请码已经被使用");
                return "register";
            }else {
                // 构建用户对象
                User user = new User();
                user.setUid(CjdUtil.getUuid());
                user.setRoleId(2);
                user.setUsername(registerForm.getUsername());
                // 密码加密
                String bcryptPassword = new BCryptPasswordEncoder().encode(registerForm.getPassword());
                user.setPassword(bcryptPassword);
                user.setGmtCreate(CjdUtil.getTime());
                user.setLoginDate(CjdUtil.getTime());
                // 保存对象
                userService.save(user);
                CjdUtil.print("新用户注册成功"+ user);

                // 激活邀请码
                invite.setActiveTime(CjdUtil.getTime());
                invite.setStatus(1);
                invite.setUid(CjdUtil.getUuid());
                inviteService.save(invite);

                // 用户信息
                userInfoService.save(new UserInfo().setUid(user.getUid()));
                // 注册成功 重定向到登录页
                return "redirect:/toLogin";
            }
        }


    }

}
