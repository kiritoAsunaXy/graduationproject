package com.xuyu.springboot.controller;


import com.xuyu.springboot.bean.MyAttentionInfo;
import com.xuyu.springboot.bean.Result;
import com.xuyu.springboot.mapper.UserMapper;
import com.xuyu.springboot.service.MyAttentionService;
import groovy.transform.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyAttentionController {


    @Autowired
   private UserMapper userMapper;

    @Autowired
    private MyAttentionService myAttentionService;

    //添加我的关注
    @RequestMapping("/addattention")
    @ResponseBody
    @Synchronized
  public Result addattention(@RequestParam("myattname") String myattentionname,
                             @RequestParam("myid") Integer outerid){


       MyAttentionInfo myAttentionInfo= myAttentionService.testIfExit(myattentionname,outerid);
        if(myAttentionInfo!=null){
            return Result.error("已经存在了");
        }else {
            String head = userMapper.selectHeadByName(myattentionname);
            myAttentionService.addone(myattentionname, outerid, head);

           Integer fans= userMapper.findfans(myattentionname)+1;

            userMapper.addfans(myattentionname,fans);
            return Result.success();
        }

  }


    @RequestMapping("/delMyAtt")
    @ResponseBody
     public Result delMyAtt(@RequestParam("id") Integer id){

        myAttentionService.delMyAtt(id);
        return Result.success();
     }



}
