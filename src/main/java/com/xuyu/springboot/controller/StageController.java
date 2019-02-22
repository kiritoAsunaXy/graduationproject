package com.xuyu.springboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuyu.springboot.bean.*;
import com.xuyu.springboot.elasticsearchRepository.ArticleRepository;
import com.xuyu.springboot.mapper.TypeMapper;
import com.xuyu.springboot.mapper.UserMapper;
import com.xuyu.springboot.service.*;
import com.xuyu.springboot.utils.Maxindex;
import com.xuyu.springboot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StageController {


    @Autowired
    private ArticleInfoService articleInfoService;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private HotService hotService;

    @Autowired
    private CommentService commentService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    HistoryService historyService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    Recommand recommand;

    @Autowired
    UserModelService userModelService;

    @Autowired
    MyAttentionService myAttentionService;


   /*
      抽取出一个公共的方法去用来获取所有用户的文章浏览类型前三名
   */
    public List<Integer> getTopThreeRead(Integer userid){
      //  System.out.println("进了方法");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_YEAR, calendar2.get(Calendar.DAY_OF_YEAR));
        Date today2 = calendar2.getTime();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String result2 = format2.format(today2);
        //以上为获得时间
        List<Integer> listfortypeid=new ArrayList<>();
        List<HistoryInfo> historylists=new ArrayList<>();//存放历史记录
        if(historyService.selectByReaderAndTime(userid,result,result2)!=null) {
            historylists = historyService.selectByReaderAndTime(userid, result, result2);
            List<Integer> typelists=new ArrayList<>();//存放的是游览的id号
            //遍历出所有的当前用户的游览过的文章的类型的id号
            for(HistoryInfo historyInfo:historylists){
                typelists.add(articleInfoService.selectTypeIdByAid(historyInfo.getArticleid()));
            }
            Set uniqueSet = new HashSet(typelists);
            Utils utils=new Utils();
            if(uniqueSet.size()>=3) {
                for (int k = 0; k < 3; k++) {
                    System.out.println(typelists);
                    String a = utils.SortTopFour(typelists);
                    for (int i = 0; i < typelists.size(); i++) {
                        if (typelists.get(i) == Integer.parseInt(a)) {
                            typelists.remove(i);
                        }
                    }
                    listfortypeid.add(Integer.parseInt(a));
                }
                return listfortypeid;
            }else {
                for(int k=0;k<uniqueSet.size();k++){
                    String a = utils.SortTopFour(typelists);
                    for (int i = 0; i < typelists.size(); i++) {
                        if (typelists.get(i) == Integer.parseInt(a)) {
                            typelists.remove(i);
                        }
                    }
                    listfortypeid.add(Integer.parseInt(a));
                }
                int  count=3-listfortypeid.size();
                for(int i=0;i<count;i++){
                    listfortypeid.add(0);
                }
                return listfortypeid;
            }
        }else {
            listfortypeid.add(0);
            listfortypeid.add(0);
            listfortypeid.add(0);
            return listfortypeid;
        }
    }


    /*
    * 抽取一个公共方法，用来筛选出与用户相似度最高的3个用户,返回的是用户的id的list
    * */

    public List<Integer> getLikeUser(Integer id){

        //所有的用户模型
        List<UserModel> userModels=userModelService.getAllModels();
        //把自己从中排除
        for(int i=0;i<userModels.size();i++){
            if(userModels.get(i).getUserid()==id){
                userModels.remove(i);
            }
        }
        //用来存放score
        List<Integer> scores=new ArrayList<>();
        //查出自己
        UserModel myModel=userModelService.getModelByUserId(id);
            List<Integer> mytypelist=new ArrayList<>();
            mytypelist.add(myModel.getTypeone());
            mytypelist.add(myModel.getTypetwo());
            mytypelist.add(myModel.getTypethree());

            List<String> submit=Utils.backList(myModel.getSubmitname());
           //  System.out.println(submit);
           //  System.out.println(myModel.getSubmitname());
            //System.out.println(submit.get(0)=="[]");
            Utils utils=new Utils();
        for(int j=0;j<userModels.size();j++){
            List<Integer> typelists=new ArrayList<>();
            typelists.add(userModels.get(j).getTypeone());
            typelists.add(userModels.get(j).getTypetwo());
            typelists.add(userModels.get(j).getTypethree());
            List<String> submit1=Utils.backList(userModels.get(j).getSubmitname());
            Integer sco=utils.getsamenum(mytypelist,typelists);
            Integer sco2=utils.getnum(submit,submit1);
            Integer score=sco+sco2+sco;
            scores.add(score);
            //System.out.println("我的关注:"+submit+"其他用户关注"+submit1+" "+userModels.get(j).getUserid()+":得了:"+sco+":"+sco2);
           // userModelService.updateScore(score,userModels.get(j).getId());
        }
        List<Integer> result =new ArrayList<>();

        for(int k=0;k<3;k++){
             result.add(userModels.get(Maxindex.maxindex2(scores)).getUserid());
            int a=Maxindex.maxindex2(scores);
             scores.remove(a);
             userModels.remove(a);
        }
        return result;
    }

    /*
    *
    * 抽取一个公共方法，用来把传入的用户id的list的用户浏览记录找出来，并且能够去重，并且当前用户本身游览过的不会再出现了
    *
    * */
    public List<ArticleInfo>  getArticleByListsAndUserId(List<Integer> lists,Integer userid){
       // List<ArticleInfo> artList=articleInfoService.findByIdLists(lists);
        List<Integer> artIdList=historyService.findByIdLists(lists);
         //工具类里写一个list去除重复的方法
       // System.out.println("之前："+artIdList);
        Utils utils=new Utils();
        List<Integer> list=utils.removeDuplicate(artIdList);
      //  System.out.println("191行输出的是文章id的list："+list);


        /*
        * 打乱顺序，进行随机
        * */
       List<Integer> randomindex= Utils.randomCommon(0,list.size(),list.size());
       List<Integer> finallist=Utils.re(list,randomindex);
       //System.out.println(finallist);

        //并且把整个要返回的文章的list直接封装好
        //List<ArticleInfo> frontArticles=articleInfoService.selectByIdList(list);
        List<ArticleInfo> frontArticles=new ArrayList<>();
        for(int t=0;t<finallist.size();t++){
            frontArticles.add(articleInfoService.selectById(finallist.get(t)));
        }
      //  System.out.println(frontArticles);


        //System.out.println("194行输出对应的文章信息:"+frontArticles);
       // System.out.println(frontArticles.size());
        Map<String, Object> param=new HashMap<>();
        param.put("status",1);
        List<ArticleInfo> allArticles=articleInfoService.list(param);
       // List<Integer> allArticleids=articleInfoService.getAIds();
       // System.out.println(allArticles.size());
        for(int i=0;i<frontArticles.size();i++){
            for(int j=0;j<allArticles.size();j++){
                if(frontArticles.get(i).getId().equals(allArticles.get(j).getId())){
                    allArticles.remove(j);
                }
            }
        }
       frontArticles.addAll(allArticles);
       // System.out.println("209行结果信息："+frontArticles);
        return frontArticles;
    }








    //查询所有文章（正常） 分页查询,并基于协调过滤推荐算法的思想去实现首页文章推荐
    @RequestMapping("/index.action")
    public String list(ModelMap map,
                       HttpSession session,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="5") int pageSize) {

        /*
        * 从这里开始实现协调过滤推荐思想，不过是使用我自己的计算方式。
        * 1.先判断如果是游客在使用，那么就推荐今日最热和最新文章随机出现
        *
        * */
        //工具类

        System.out.println();

        if(session.getAttribute("user")!=null) {

            //return "stage/index";
            //}else {
            /*
             * 现在是用户登录后的情况了
             * 1.先要去找到与用户相似度比较高的用户进行排名，取前5名
             *
             * */
            User user = (User) session.getAttribute("user");
            System.out.println(user.getId());
            List<Integer> mymaxtypeid = getTopThreeRead(user.getId());

            //将用户的模型信息存入
            UserModel userModel = new UserModel();
            userModel.setUserid(user.getId());
            userModel.setTypeone(mymaxtypeid.get(0));
            userModel.setTypetwo(mymaxtypeid.get(1));
            userModel.setTypethree(mymaxtypeid.get(2));
            //默认三个全部都为0
            //去数据库看看是否是新人没有创建用户模型的
            int hava = userModelService.checkUserIfExist(user.getId());

            if (hava == 0) {
                //这里是表示没有，那么就创建用户的模型信息
                userModelService.insertUserModel(userModel);
                //初始化用户关注的人很重要
                List<String> attentionnames = new ArrayList<>();
                attentionnames.add("0");
                String a = attentionnames.toString();
                userModelService.updateUserModelsubmitname(a, user.getId());
            } else {
                //这里说明该用户暂时还没有关注的人
                if (myAttentionService.getMyAttention(user.getId()).size() == 0) {
                    userModelService.updateUserModel(userModel);
                    //更新用户的模型，添加关注的作者信息
                    List<String> attentionnames = new ArrayList<>();
                    attentionnames.add("0");
                    String a = attentionnames.toString();
                    userModelService.updateUserModelsubmitname(a, user.getId());
                } else {
                    List<MyAttentionInfo> myAttentionInfos = myAttentionService.getMyAttention(user.getId());
                   // System.out.println("myAttentionInfos" + myAttentionInfos);
                    List<String> attentionnames = new ArrayList<>();
                    for (MyAttentionInfo myAttentionInfo : myAttentionInfos) {
                        attentionnames.add(myAttentionInfo.getMyattentionname());
                    }
                    userModelService.updateUserModel(userModel);

                    //更新用户的模型，添加关注的作者信息
                    String a = attentionnames.toString();

                    userModelService.updateUserModelsubmitname(a, user.getId());
                }
            }
            /*
             * 这个地方已经找出了目前的最相似的top3用户
             * */
            List<Integer> idsort=new ArrayList<>();
            List<Integer> topthree=getLikeUser(user.getId());

            //System.out.println(topthree);
            List<ArticleInfo> list=getArticleByListsAndUserId(topthree,user.getId());
            for(int q=0;q<list.size();q++){
                idsort.add(Integer.parseInt(list.get(q).getId()));
            }
           // System.out.println(idsort);
            //PageHelper插件5
            PageHelper.startPage(pageNum,pageSize);
            List<ArticleInfo> resultss=articleInfoService.selectByIdList(idsort);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(resultss);
           // System.out.println(pageInfo.getPageSize());;
            map.put("pageInfo", pageInfo);
            //查询所有的文章分类
            map.put("typeList", typeMapper.selectAll(null));
           // System.out.println("进来了:"+pageInfo);
            //统计出现的
            //这里放热搜
            List<ArticleInfo> hotlists=hotService.getHotArticle();
            if(hotlists.size()!=0){
                map.put("hotarts",hotlists);
                map.put("hotnull","1");
            }else {
                map.put("hotnull","0");
            }
           // System.out.println(hotlists);
            return "stage/index";
        }else {
            Map<String, Object> param = new HashMap<>();
            param.put("status", 1);
            //PageHelper插件
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list = articleInfoService.list(param);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
           // System.out.println("没进来了:"+pageInfo);
            //查询所有的文章分类
            List<ArticleInfo> hotlists=hotService.getHotArticle();
            if(hotlists.size()!=0){
                map.put("hotarts",hotlists);
                map.put("hotnull","1");
            }else {
                map.put("hotnull","0");
            }
            //System.out.println(hotlists);
            map.put("typeList", typeMapper.selectAll(null));
            return "stage/index";
        }
    }



   //用于首页信息的展示包含热点搜索功能
    @RequestMapping("/showtypeinfo")
    @ResponseBody
    public Result showtypeinfo(){
        List<TypeInfo> typelist=typeMapper.selectAll(null);
        List<ArticleInfo> hotArticles=hotService.getHotArticle();
        return Result.success().add("TypeList", typelist).add("HotArticles",hotArticles);
    }




    //查询需要查阅的文章,相关的推荐（我的推荐是按照相同的类型文章进行推荐）,评论模块数据
    @RequestMapping("/article.action")
    public String article(ModelMap map,
                          @RequestParam(value="id") Integer id,
                          @RequestParam(value = "typeId",required = false) Integer type_id,
                          HttpSession session) {


        //判断如果不传typeid的话，只传id需要去查找typeid
           if(type_id==0) {
               type_id = articleInfoService.selectTypeIdByAid(id);
           }


        //热度加一
        hotService.increase(id);
        //查询文章详情
        ArticleInfo articleInfo=articleInfoService.selectById(id);
       int viewcount=articleInfo.getView_count()+1;
        articleInfoService.addViewCount(id,viewcount);
        //查询同类型的文章
          Map<String,Object> param=new HashMap<>();
          param.put("status","1");
          param.put("typeId",type_id);
          List<ArticleInfo> articleInfos=articleInfoService.list(param);
          //移除相同的一篇文章
          for(int i=0;i<articleInfos.size();i++){
             String aid=articleInfos.get(i).getId();
             if(Integer.parseInt(aid)==id){
                 articleInfos.remove(i);
             }
          }


          //添加到该用户的阅读历史中
          //1.如果是没有登录的用户，不需要处理
        //2.如果是已经登录存在的用户
            //2.1:先获取用户,要先判断其存在
        //2.2：判断有没有本文的游览记录，如果有，就更新游览时间
        if(session.getAttribute("user")!=null) {
            User user = (User) session.getAttribute("user");
            Integer reader = user.getId();
            List<Integer> ids = historyService.findIfReadIds(reader);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String readtime = simpleDateFormat.format(new Date());
            if (ids.contains(id)) {
                historyService.updateReadtime(readtime, reader, id);
            } else {
                String articlename = articleInfo.getTitle();
                Integer articleid = id;
                HistoryInfo historyInfo = new HistoryInfo();
                historyInfo.setArticleid(articleid);
                historyInfo.setArticlename(articlename);
                historyInfo.setReader(reader);
                historyInfo.setReadtime(readtime);
                historyService.addNotes(historyInfo);
            }
        }

          //查询此文章的评论详情
          List<CommentInfo> commentInfolists= commentService.getCommentInfo(id);
          System.out.println(commentInfolists);
          List<User> userinfo=new ArrayList<>();
          for(CommentInfo commentInfo:commentInfolists){
              int user_id= commentInfo.getUserid();
              User user=userMapper.getEmpById(user_id);
              userinfo.add(user);
          }
          map.put("comment",commentInfolists);
          map.put("userlist",userinfo);
          map.put("referenceArticle",articleInfos);
          map.put("articleInfo", articleInfo);

        //查出这个作者的作品数量和总计的阅读量和粉丝数目
         Integer arts= articleInfoService.getArticleNumsByOwner(id);
         Integer counts=articleInfoService.getAllCountsByOwner(id);
         Integer fans=userMapper.getFansNum(id);
         List<ArticleInfo> latestArts=articleInfoService.getAllArticlesByArticleId(id);
         //System.out.println(latestArts);
            map.put("latestArts",latestArts);
           map.put("arts",arts);
           map.put("counts",counts);
           map.put("fans",fans);

        return "stage/article";
    }





    //根据文章类型查询所有相同类型的文章 分页查询
    @RequestMapping("/type.action")
    public String type(ModelMap map,
                       @RequestParam(value="typeId") String typeId,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="5") int pageSize) {

        Map<String, Object> param=new HashMap<>();
        param.put("status",1);
        param.put("typeId",typeId);

        //PageHelper插件
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list= articleInfoService.list(param);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
        map.put("pageInfo", pageInfo);
        map.put("typeList", typeMapper.selectAll(null));
        map.put("typeid",typeId);
        List<ArticleInfo> hotlists=hotService.getHotArticle();
        if(hotlists.size()!=0){
            map.put("hotarts",hotlists);
            map.put("hotnull","1");
        }else {
            map.put("hotnull","0");
        }


        return "stage/index";
    }



    //测试搜索的相关模块
    @RequestMapping("/testElastic")
    public String testElastic(){
        return "stage/testElastic.html";
    }

    //获取数据
    @RequestMapping("/getthings")
    @ResponseBody
    public Result getthings(){
        Map<String, Object> param=new HashMap<>();
        param.put("status",1);
        List<ArticleInfo> lists= articleInfoService.list(param);
        List<ElasticArticle> esLists=new ArrayList<>();
        for(ArticleInfo articleInfo:lists){
            ElasticArticle elasticArticle=new ElasticArticle();
            elasticArticle.setId(articleInfo.getId());
            elasticArticle.setTitle(articleInfo.getTitle());
            esLists.add(elasticArticle);
        }
        return Result.success().add("titles",esLists);
    }


    //查询用户搜索的文章信息 分页查询
    @RequestMapping("/search.action")
    public String search(ModelMap map,
                         @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize", defaultValue="5") int pageSize,
                         HttpServletRequest request) {
        String keyword=request.getParameter("keyWord");
        keyword=keyword.trim();
        String[] keywords=keyword.split("\\s+");
        List<ElasticArticle> listsforall=new ArrayList<>();
        List<String> arid=new ArrayList<>();
        List<String> idindex = new ArrayList<>();
        int status=1;//这个用来检测关键词中是否有某个是不存在的
        //自此，就转变为了多个关键词,每个关键词单独去搜索，然后取相重复度最高的文章
        for (int i=0;i<keywords.length;i++){
            List<ElasticArticle>  list=articleRepository.findAllByTitleContains(keywords[i]);
           // System.out.println("本次结果"+list);
            if(list.isEmpty()){
                status=0;
                break;
            }
                 listsforall.addAll(list);
        }

    //System.out.println("状态码为："+status);
        if(listsforall!=null&&status!=0) {
            for (ElasticArticle article : listsforall) {
                arid.add(article.getId());
            }
            Set uniqueSet = new HashSet(arid);

            //取出重复度最高的文章id
            List<String> idto = new ArrayList<>();
            List<Integer> count = new ArrayList<>();
            for (Object o : uniqueSet) {
                // System.out.println("id"+o+"出现了:"+Collections.frequency(arid,o));
                idto.add(o.toString());
                count.add(Collections.frequency(arid, o));
            }
            List<Integer> index = Maxindex.maxindex(count);

            for (int i = 0; i < index.size(); i++) {
                idindex.add(idto.get(index.get(i)));
            }
      //  System.out.println(idindex);
            //PageHelper插件
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list = articleInfoService.searchlist(idindex);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
            //查询所有的文章分类
            map.put("typeList", typeMapper.selectAll(null));
            map.put("keyword",keyword);
            map.put("message",null);
        }
        else {
           // System.out.println("进了else");
            //PageHelper插件
            idindex.add("1");
            PageHelper.startPage(pageNum, pageSize);
            List<ArticleInfo> list = articleInfoService.searchlist(idindex);
            PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
            map.put("pageInfo", pageInfo);
            map.put("typeList", typeMapper.selectAll(null));
            map.put("message","抱歉，暂时还没有人发布含有\""+keyword+"\"的关键词的文章");
            map.put("keyword",null);
        }
        return "stage/index";
    }
}
