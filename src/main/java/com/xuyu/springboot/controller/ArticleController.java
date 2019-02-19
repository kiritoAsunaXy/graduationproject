package com.xuyu.springboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuyu.springboot.bean.ArticleInfo;
import com.xuyu.springboot.bean.ElasticArticle;
import com.xuyu.springboot.bean.Result;
import com.xuyu.springboot.bean.User;
import com.xuyu.springboot.elasticsearchRepository.ArticleRepository;
import com.xuyu.springboot.mapper.TypeMapper;
import com.xuyu.springboot.service.ArticleInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ArticleController {

 @Autowired
 private ArticleInfoService articleInfoService;

 @Autowired
 private TypeMapper typeMapper;

 @Autowired
 JavaMailSenderImpl mailSender;

 @Autowired
 ArticleRepository articleRepository;

    //去文章列表展示页面
    //查询所有文章（正常） 分页查询，同时对于用户权限进行判断显示
    @RequestMapping("/toArticleEdit")
    public String list(ModelMap map,
                       @RequestParam(required = false, value = "typeId") String typeId,
                       @RequestParam(required = false, value = "startDate") String startDate,
                       @RequestParam(required = false, value = "endDate") String endDate,
                       @RequestParam(required = false, value = "keyWord") String keyWord,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="5") int pageSize) {
        //从subject中获取用户权限和id信息
        Subject subject=SecurityUtils.getSubject();
        User myuser= (User) subject.getPrincipal();
        String role=myuser.getAuthor();//角色


       // System.out.println(startDate+"  "+endDate);
        if(role.equals("common_user")){
            //封装查询的数据
            Map<String, Object> param=new HashMap<>();
            param.put("typeId", typeId);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("keyWord", keyWord);
            //param.put("status",1);
            param.put("owner",myuser.getId());


            //PageHelper插件
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list= articleInfoService.list(param);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
            //查询所有的文章分类
            map.put("typeList", typeMapper.selectAll(null));

            return "articleEdit";
        }else{
            Map<String, Object> param=new HashMap<>();
            param.put("typeId", typeId);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("keyWord", keyWord);
            param.put("status",1);



            //PageHelper插件
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list= articleInfoService.list(param);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
            //查询所有的文章分类
            map.put("typeList", typeMapper.selectAll(null));

            return "articleEdit";
        }

    }





    //前往回收站页面
    @RequestMapping("/torecycle_bin")
    public String showrecycle(ModelMap map,
                       @RequestParam(required = false, value = "typeId") String typeId,
                       @RequestParam(required = false, value = "startDate") String startDate,
                       @RequestParam(required = false, value = "endDate") String endDate,
                       @RequestParam(required = false, value = "keyWord") String keyWord,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="5") int pageSize) {


        //从subject中获取用户权限和id信息
        Subject subject=SecurityUtils.getSubject();
        User myuser= (User) subject.getPrincipal();
        String role=myuser.getAuthor();//角色
        if(role.equals("common_user")) {
            //封装查询的数据
            Map<String, Object> param = new HashMap<>();
            param.put("typeId", typeId);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("keyWord", keyWord);
            param.put("status", "0");
            param.put("owner", myuser.getId());
            //PageHelper插件
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list = articleInfoService.list(param);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
            //查询所有的文章分类
            map.put("typeList", typeMapper.selectAll(null));

            return "recycle_bin";
        }else {
            //封装查询的数据
            Map<String, Object> param = new HashMap<>();
            param.put("typeId", typeId);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("keyWord", keyWord);
            param.put("status", "0");
            //PageHelper插件
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list = articleInfoService.list(param);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
            //查询所有的文章分类
            map.put("typeList", typeMapper.selectAll(null));

            return "recycle_bin";
        }
    }


    //前往审核页面
    @RequestMapping("/toexamine")
    public String toexamine(ModelMap map,
                              @RequestParam(required = false, value = "typeId") String typeId,
                              @RequestParam(required = false, value = "startDate") String startDate,
                              @RequestParam(required = false, value = "endDate") String endDate,
                              @RequestParam(required = false, value = "keyWord") String keyWord,
                              @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                              @RequestParam(value="pageSize", defaultValue="5") int pageSize) {

        //封装查询的数据
        Map<String, Object> param=new HashMap<>();
        param.put("typeId", typeId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("keyWord", keyWord);
        param.put("status","3");
        //PageHelper插件
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list= articleInfoService.list(param);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
        map.put("pageInfo", pageInfo);
        //查询所有的文章分类
        map.put("typeList", typeMapper.selectAll(null));

        return "examine";
    }





    //去到文章具体编辑区域
    @RequestMapping("/edit")
    public String  editA(@RequestParam(required = false,value = "id") Integer id,ModelMap map){
            if(!StringUtils.isEmpty(id)){
                ArticleInfo articleInfo=articleInfoService.selectById(id);
                map.put("articleInfo", articleInfo);
            }else {
                ArticleInfo articleInfo=new ArticleInfo();
                articleInfo.setId(null);
                map.put("articleInfo",articleInfo);
            }
            map.put("typeList",typeMapper.selectAll(null));
            return "edit";
    }




    //去到文章审核区域
    @RequestMapping("/toexaminearticle")
    public String  examinearticle(@RequestParam(required = false,value = "id") Integer id,ModelMap map){
        if(!StringUtils.isEmpty(id)){
            ArticleInfo articleInfo=articleInfoService.selectById(id);
            map.put("articleInfo", articleInfo);
        }else {
            ArticleInfo articleInfo=new ArticleInfo();
            articleInfo.setId(null);
            map.put("articleInfo",articleInfo);
        }
        map.put("typeList",typeMapper.selectAll(null));
        return "examinearticle";
    }




    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {

        // 文件原名称
        String szFileName = file.getOriginalFilename();
        // 重命名后的文件名称
        String szNewFileName = "";
        // 根据日期自动创建3级目录
        String szDateFolder = "";

        // 上传文件
        if (file!=null && szFileName!=null && szFileName.length()>0) {
            Date date = new Date();
            szDateFolder = new SimpleDateFormat("yyyy/MM/dd").format(date);
            // 存储文件的物理路径
            String szFilePath = "G:\\upload\\" + szDateFolder;
            // 自动创建文件夹
            File f = new File(szFilePath);
            if (!f.exists()) {
                f.mkdirs();
            }

            // 新的文件名称
            szNewFileName = UUID.randomUUID() + szFileName.substring(szFileName.lastIndexOf("."));
            // 新文件
            File newFile = new File(szFilePath+"\\"+szNewFileName);

            // 将内存中的数据写入磁盘
            file.transferTo(newFile);
        }
        return Result.success().add("imgUrl", szDateFolder+"/"+szNewFileName);
    }



    //保存文章信息
    @RequestMapping("/saveArticle")
    @ResponseBody
    public Result loginCheck(ArticleInfo articleInfo){
        Subject subject=SecurityUtils.getSubject();
        User myuser= (User) subject.getPrincipal();
        articleInfo.setOwner(myuser.getId());
        articleInfoService.save(articleInfo);
        return Result.success();
    }

    //保存文章信息
    @RequestMapping("/saveArticleTrue")
    @ResponseBody
    public Result saveArticleTrue(ArticleInfo articleInfo){
        Subject subject=SecurityUtils.getSubject();
        User myuser= (User) subject.getPrincipal();
        articleInfo.setOwner(myuser.getId());
        articleInfo.setStatus(3);
        articleInfoService.save(articleInfo);
        ElasticArticle elasticArticle=new ElasticArticle();
        //此处将发布并审核通过的文章存入ES中用于搜索
        try {
            elasticArticle.setId(articleInfo.getId());
            elasticArticle.setTitle(articleInfo.getTitle());
            articleRepository.index(elasticArticle);
        }catch (Exception e){
            System.out.println("未连接elasticsearch，存入失败");
        }

        return Result.success();
    }


    //文章批量移动到某个分类
    @RequestMapping("/move")
    @ResponseBody
    public Result moveToType(
            @RequestParam(required = false, value = "idArr") String[] idArr,
            @RequestParam(required = false, value = "typeId") String typeId){

        articleInfoService.updateTypeId(idArr,typeId);
        return Result.success();
    }

    //文章批量彻底删除
    @RequestMapping("/deleteit")
    @ResponseBody
    public Result deleteit(@RequestParam(required = false, value = "idArr") String[] idArr){
        System.out.println("进来了"+idArr[0]);
        articleInfoService.deleteit(idArr);
        return Result.success();
    }



    //文章批量移动到回收站
    @RequestMapping("/recycle_bin")
    @ResponseBody
    public Result recycle_bin(
            @RequestParam(required = false, value = "idArr") String[] idArr,
            @RequestParam(required = false, value = "status") String status){

        articleInfoService.recycle_bin(idArr,status);
        return Result.success();
    }


    //文章被驳回后
    @RequestMapping("/back")
    @ResponseBody
    public Result back(@RequestParam(required = false,value = "id") String id,
                       @RequestParam(required = false,value = "backreason") String backreason,
                       @RequestParam(required = false,value = "status") String status) throws MessagingException {
          articleInfoService.updateStatus(status,id);
        Subject subject=SecurityUtils.getSubject();
        User myuser= (User) subject.getPrincipal();
        //向文章作者的邮箱发送邮件展示驳回理由
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("程序园博客反馈");
        helper.setText("<img src='http://127.0.0.1:8082/images/sorry.jpg' style='height: 300px;width: 350px'/><br><hr><h2>反馈信息:</h2><p>"+backreason+"</p>",true);
        // helper.setText("<h1 style='color:red'>哈哈哈<h1>",true);
        helper.setTo(myuser.getEmail());
        helper.setFrom("282075823@qq.com");
        mailSender.send(mimeMessage);
        return Result.success();
    }




    //文章stars
    @RequestMapping("/stars")
    @ResponseBody
    public Result stars(@RequestParam("stars") double stars,@RequestParam("id") Integer id){

        //查询原来是几分
        double before=articleInfoService.beforestars(id);
       double nowstars=(before+stars)/2.0;
        articleInfoService.addstars(id,nowstars);
        double result=articleInfoService.beforestars(id);
return Result.success().add("score",result);
    }


}
