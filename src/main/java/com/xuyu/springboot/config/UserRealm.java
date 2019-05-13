package com.xuyu.springboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xuyu.springboot.bean.Permission;
import com.xuyu.springboot.bean.User;
import com.xuyu.springboot.mapper.UserMapper;
import com.xuyu.springboot.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;


public class UserRealm extends AuthorizingRealm {


     @Autowired
    private UserMapper userMapper;

     @Autowired
     private PermissionService permissionService;
    /*
     *
     * 执行授权的逻辑
     *
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       // System.out.println("授权逻辑");
        //给资源进行授权
       //创建一个simpleAuthorizationInfo
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //到数据库找授权字符串
        Subject subject=SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();//如果认证成功了以后就会在全局的变量SecurityUtils.getSubject()中通过getPrincipal()获得用户信息
        User dbUser= userMapper.getEmpById(user.getId());
        //去数据库里查出权限
        List<String> permissionlist=permissionService.queryPermissionsByUserId(dbUser.getId());
        info.addRole(dbUser.getAuthor());
        info.addStringPermissions(permissionlist);
        //  info.addStringPermission("user:add");
        return info;


    }


    /*
     *
     * 执行认证的逻辑
     *
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      // System.out.println("认证逻辑");
       //假设数据库的用户名和密码,注入业务service
        //编写shiro的判断逻辑
        //判断用户名
        UsernamePasswordToken token= (UsernamePasswordToken)authenticationToken;
        User user=userMapper.getUserByName(token.getUsername());
        if(user==null){
            return null;//shiro底层会抛出UnknownAccountException异常
        }
        ByteSource salt=ByteSource.Util.bytes(user.getUsername());
        return new SimpleAuthenticationInfo(user,user.getPassword(),salt,"");
    }

    //配置shiroDialect，用于和thymeleaf和shiro标签配合使用
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}

