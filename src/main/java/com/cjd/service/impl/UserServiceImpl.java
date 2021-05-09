package com.cjd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjd.pojo.User;
import com.cjd.mapper.UserMapper;
import com.cjd.pojo.UserRole;
import com.cjd.service.UserRoleService;
import com.cjd.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    HttpSession httpSession;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 通过用户名查询用户
        User user = userService.getOne(new QueryWrapper<User>().eq("username", s));
        // 放入session
        httpSession.setAttribute("loginUser",user);
        // 创建一个新的userDetails 对象，最后验证登录需要使用
        UserDetails userDetails = null;
        if (user != null){
            String password = user.getPassword();
            // 创建一个集合来存放权限
            Collection<GrantedAuthority> authoritise = getAuthorities(user);
            // 实例化userDetail对象
            userDetails = new org.springframework.security.core.userdetails.User(s,password,true,true,true,true,authoritise);
        }
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        ArrayList<GrantedAuthority> authList = new ArrayList<>();
        UserRole role = userRoleService.getById(user.getRoleId());
        authList.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        return authList;
    }
}
