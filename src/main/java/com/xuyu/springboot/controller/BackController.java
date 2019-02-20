package com.xuyu.springboot.controller;


import com.xuyu.springboot.bean.*;
import com.xuyu.springboot.service.ArticleInfoService;
import com.xuyu.springboot.service.CommentService;
import com.xuyu.springboot.service.MyAttentionService;
import com.xuyu.springboot.service.MyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BackController {


    @Autowired
    private MyAttentionService myAttentionService;

    @Autowired
    private MyCollectionService myCollectionService;

    @Autowired
    private ArticleInfoService articleInfoService;

    @Autowired
    private CommentService commentService;

    //前往后台的我的关注
    @RequestMapping("/tomyattention")
    public String tomyattention(HttpSession session, ModelMap map){
        User user = (User) session.getAttribute("user");
        Integer myid=user.getId();
        List<Integer> idList=new ArrayList<>();
        List<MyAttentionInfo> myatlists=myAttentionService.getMyAttention(myid);
        for(MyAttentionInfo item:myatlists){
            idList.add(myAttentionService.getMyAttIdByName(item.getMyattentionname()));
        }
        if(myatlists.size()<1){
            map.put("myatlists","no");
        }else {
            map.put("myatlists", myatlists);
        }
        map.put("idList",idList);
        return "stage/myattention";
    }


    //前往后台的我的收藏
    @RequestMapping("/tomycollection")
    public String tomycollection(HttpSession session,ModelMap map){
        User user = (User) session.getAttribute("user");
        Integer myid=user.getId();
        List<MyCollection> myCollections=myCollectionService.getMyCollectionById(myid);
        if(myCollections.size()<1){
            map.put("myCollections","no");
        }else {
            map.put("myCollections", myCollections);
        }
        return "stage/mycollection";
    }











    //前往后台的我的消息
    @RequestMapping("/tomymymessage")
    public String tomymymessage(HttpSession session,ModelMap map){
        User user = (User) session.getAttribute("user");
        String myname=user.getUsername();
        List<CommentInfo> commentInfos=commentService.getCommentInfoss(myname);
       /* commentInfos.forEach(commentInfo -> {
            System.out.println(commentInfo);
        });*/

       if(commentInfos.size()==0){
           map.put("status",0);
       }else {
           map.put("status",1);
       }
        map.put("commentInfos",commentInfos);
        return "stage/mymessage";
    }



    //前往系统消息群发
    @RequestMapping("/tomessageforall")
    public String messageforall(){
        return "messageforall";
    }





}
