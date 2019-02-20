package com.xuyu.springboot.controller;


import com.xuyu.springboot.bean.Result;
import com.xuyu.springboot.mapper.UserMapper;
import com.xuyu.springboot.service.ArticleInfoService;
import com.xuyu.springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class commentController {


    @Autowired
    private  CommentService commentService;

    @Autowired
    private ArticleInfoService articleInfoService;


    @Autowired
    private UserMapper userMapper;


    //保存评论
    @RequestMapping("/savecomment")
    @ResponseBody
    public Result saveComment(@RequestParam("comment") String comment,
                              @RequestParam("article_id") Integer article_id,
                              @RequestParam("user_id") Integer user_id,
                              @RequestParam("recallid") Integer recallid,
                              @RequestParam("replyname") String replayname){
        Date date = new Date();
        int index=comment.indexOf(":");
        String realcomment;
        if(index!=-1) {
             realcomment = comment.substring(index + 1);
        }else {
            realcomment=comment;
        }
        String dateforcmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        commentService.savecomment(realcomment,article_id,user_id,dateforcmt,recallid,replayname);
        return Result.success();
    }




    //管理员的消息群发功能
    @RequestMapping("/sendmessageforall")
    @ResponseBody
    public Result sendmessageforall(@RequestParam("id") Integer id,@RequestParam("text") String mess){

        Integer article_id=1;
        Date date = new Date();
        String dateforcmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        List<String> usernames=userMapper.getAllUserName();
        for(int a=0;a<usernames.size();a++){
            commentService.savecomment(mess,article_id,id,dateforcmt,1,usernames.get(a));
        }
        return Result.success();
    }





    @ResponseBody
    @RequestMapping("/resetrecall")
    public Result resetrecall(@RequestParam("id") Integer id){
        commentService.resetrecall(id);
        return Result.success();
    }


    @ResponseBody
    @RequestMapping("/replay")
    public Result replay(@RequestParam("id") Integer id){
        Integer aid=commentService.getArticleId(id);
        Integer typeid= articleInfoService.selectTypeIdByAid(aid);
        return Result.success().add("aid",aid).add("type_id",typeid);
    }


}
