package com.xuyu.springboot.controller;


import com.xuyu.springboot.bean.MyCollection;
import com.xuyu.springboot.bean.Result;
import com.xuyu.springboot.service.MyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MyCollectionController {

    @Autowired
    private MyCollectionService myCollectionService;


    //添加到我的收藏的数据库

    @ResponseBody
    @RequestMapping("/addcollection")
    public Result addcollection(@RequestParam("mycollectionname") String mycollectionname,
                                @RequestParam("myid") Integer id,
                                @RequestParam("articleid") Integer articleid,
                                @RequestParam("typeid") Integer typeid){
        MyCollection myCollection=new MyCollection();
        myCollection.setArticleid(articleid);
        myCollection.setMycollectionname(mycollectionname);
        myCollection.setMyid(id);
        myCollection.setTypeid(typeid);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(new Date());
        myCollection.setMycollectiontime(time);
        myCollectionService.addcollection(myCollection);
        return Result.success();
    }

    //检查是否重复收藏一篇文章
    @ResponseBody
    @RequestMapping("/testcollection")
    public Result testcollection(@RequestParam("articleid") Integer articleid,
                                 @RequestParam("myid") Integer myid){

        MyCollection myCollection=myCollectionService.testIfExist(articleid,myid);
        if(myCollection!=null){
            return Result.error("您已经收藏该文章了");
        }else {
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping("/delMyCollection")
    public Result delMyCollection(@RequestParam("id") Integer id){
        myCollectionService.delMyCollectionById(id);
        return Result.success();
    }


}


