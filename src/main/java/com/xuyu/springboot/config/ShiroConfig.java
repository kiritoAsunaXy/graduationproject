package com.xuyu.springboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/*
*
* shiro的配置类
*
*
* */
@Configuration
public class ShiroConfig {

    /*
     * 创建ShiroFilterFactoryBean
     * */
    @Bean
    public ShiroFilterFactoryBean getShiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置shiro内置过滤器
        /*
         * 常用过滤器:
         *   1.anon:无需认证
         *   2.authc：必须认证才可以访问
         *   3.user:如果使用rememberMe就可以直接访问
         *   4.perms:该资源必须得到资源权限才可以访问
         *   5.role：该资源必须得到角色权限才可以访问
         * */
        Map<String, String> filterMap = new LinkedHashMap<>();
   /* filterMap.put("/user/add","authc");//设置拦截
    filterMap.put("/user/update","authc");//设置拦截*/
        filterMap.put("/static/**", "anon");
        filterMap.put("/bootstrap-3.3.7-dist/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/jquery-3.1.1.js/**", "anon");
        filterMap.put("/javaex/**", "anon");
        filterMap.put("/mycss", "anon");
        filterMap.put("/myjs", "anon");
        filterMap.put("/druid/**","anon");
        filterMap.put("/statics/**","anon");
        filterMap.put("/picture/**","anon");
        //以上是一些公共的url和静态资源的访问
        filterMap.put("/index.action", "anon");//前台首页
        filterMap.put("/testElastic","anon");//测试elasricsearch
        filterMap.put("/getthings","anon");
        filterMap.put("/logout","anon");
        filterMap.put("/aboutPage","anon");
        filterMap.put("/search.action","anon");
        filterMap.put("/gotoBackhome","anon");
        filterMap.put("/checkUsername","anon");///用户检测用户名是否重复
        filterMap.put("/dataAnalyse","anon");//数据分析
        filterMap.put("/test", "anon");//测试
        filterMap.put("/type.action","anon");//查询同类型的文章

        filterMap.put("/showtypeinfo","anon");//前台的信息
        filterMap.put("/index", "anon");//登录页面
        filterMap.put("/user","anon");//登录逻辑的开始
        filterMap.put("/topageregis","anon");//前往注册页面
        filterMap.put("/mailre","anon");//发送邮件验证
        filterMap.put("/icode","anon");//获取和验证验证码
        filterMap.put("/getVerifyCode.action","anon");//获得session的验证码
        filterMap.put("/article.action", "anon");//前台具体文章查阅可以不用登陆，这里是游客用户



        filterMap.put("/**", "authc");//通配


        filterMap.put("/stars","authc");//要登陆才能评星
        filterMap.put("/background","authc");//前往后台
        filterMap.put("/getType","authc");//展示文章分类
        filterMap.put("/sava_addType","authc");//保存新建的文章类别
        filterMap.put("/delType","authc");//删除文章类别
        filterMap.put("/searchTypeinfo","authc");//模糊搜索文章类别信息
        filterMap.put("/toArticleEdit","authc");//查询文章列表
        filterMap.put("/edit","authc");//去文章编辑区
        filterMap.put("/upload","authc");//创建三级目录
        filterMap.put("/saveArticle","authc");//文章保存
        filterMap.put("/saveimg","authc");//文章保存
        filterMap.put("/move","authc");//文章移动到某个分类
        filterMap.put("/recycle_bin","authc");//文章放入回收站
        filterMap.put("/deleteit","authc");//回收站中的文章删除
        filterMap.put("/userlist","authc");//前往用户信息列表
        filterMap.put("/dataAnalyse2","authc");//前往数据可视化2
        filterMap.put("/dataAnalyse","authc");
        filterMap.put("/savecomment","authc");//要登陆才能评论
        filterMap.put("/tomyattention","authc");//去后台关注
        filterMap.put("/tomycollection","authc");//去后台收藏
        filterMap.put("/tomymymessage","authc");//去我的消息
        filterMap.put("/delMyAtt","authc");
        filterMap.put("/addcollection","authc");
        filterMap.put("/testcollection","authc");
        filterMap.put("/delMyCollection","authc");
        filterMap.put("/resetrecall","authc");
        filterMap.put("/replay","authc");
        filterMap.put("/savetags","authc");
        filterMap.put("/setrecall","authc");
        filterMap.put("/numofmessage","authc");
        filterMap.put("/tomessageforall","authc");
        filterMap.put("/operation","authc");
    //授权过滤器,没有授权的会去未授权页面，需要配置一下
//        filterMap.put("/user/add", "perms[user:add]");
//        filterMap.put("/user/update", "perms[user:update]");

        shiroFilterFactoryBean.setLoginUrl("/index");//设置默认登录界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/index");//设置未授权的跳转页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
}

    /*
     * 创建DefaultWebSecurityManger
     *
     * */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    /*
     * 创建realm
     *
     * */
    @Bean(name = "userRealm")
    public UserRealm getRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        UserRealm userRealm=new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }


//配置shiroDialect，用于和thymeleaf和shiro标签配合使用
  @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher=new HashedCredentialsMatcher();
        //指定加密方式
        credentialsMatcher.setHashAlgorithmName("MD5");
         //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * lifecycleBeanPostProcessor是负责生命周期的 , 初始化和销毁的类
     * (可选)
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}