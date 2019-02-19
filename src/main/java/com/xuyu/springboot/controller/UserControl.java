package com.xuyu.springboot.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuyu.springboot.bean.*;
import com.xuyu.springboot.mapper.UserMapper;

import com.xuyu.springboot.mapper.TypeMapper;
import com.xuyu.springboot.service.ArticleInfoService;
import com.xuyu.springboot.service.CommentService;
import com.xuyu.springboot.service.EmployeeService;
import com.xuyu.springboot.utils.TestMD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class UserControl {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    ArticleInfoService articleInfoService;

    @Autowired
    CommentService commentService;




    @RequestMapping("/background")
    public String tobg(){return "background";}





    @RequestMapping("/test")
    public String test11(){
        return "stage/test";
    }

    //登录页面
   @RequestMapping("/index")
   public String toLogin(HttpServletRequest request){
       request.setAttribute("APP_PATH",request.getContextPath());
        return "login";
   }

   @RequestMapping("/emp/{ id}")
   @ResponseBody
   public User getemp(@PathVariable("id") Integer id){
       User employee= employeeService.getEmp(id);
        return employee;
   }

   //前往用户列表展示界面,这里需要携带的数据有所有用户的信息
   @RequestMapping("/userlist")
   public String toUserlist(ModelMap map,
                            @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                            @RequestParam(value="pageSize", defaultValue="2") int pageSize){
       PageHelper.startPage(pageNum,pageSize);
        List<User> userList= userMapper.selectAllUser();
       PageInfo<User> pageInfo=new PageInfo(userList,2);
      map.put("pageInfo",pageInfo);
      return "userlist";
   }

//---------------------------------------------------------------------------------------------------------------
    /**
     * 获取验证码
     *
     * @param response
     * @param session
     */
    @RequestMapping("/getVerifyCode.action")
    public void generate(HttpServletResponse response, HttpSession session) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String verifyCodeValue = drawImg(output);

        session.setAttribute("verifyCodeValue", verifyCodeValue);

        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘画验证码
     *
     * @param output
     * @return
     */
    private String drawImg(ByteArrayOutputStream output) {
        String code = "";
        // 随机产生4个字符
        for (int i = 0; i < 4; i++) {
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        // 调用Graphics2D绘画验证码
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 随机参数一个字符
     *
     * @return
     */
    private char randomChar() {
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }

//------------------------------------------------------------------------------------------------------
   //登录逻辑的开始
    @RequestMapping("/user")
    public String login(User user, Map<String,Object> map, HttpSession session, HttpServletRequest request,ModelMap modelMap){
       String checkcode= (String) session.getAttribute("verifyCodeValue");
       String usercode=request.getParameter("check");


       //1.获取subject
        Subject subject=SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
       //3.执行登陆逻辑
        try {
            subject.login(token);
            User user1=userMapper.getUserByName(user.getUsername());
            //将用户信息放入session，供给所有页面共享
            session.setAttribute("user",user1);
            modelMap.put("userinfo",user1);
            List<TypeInfo> typeLists=typeMapper.selectAll(null);
            request.setAttribute("tlists",typeLists);
            if(checkcode.equals(usercode.toUpperCase())) {
                return "redirect:/index.action";
            }else {
                map.put("err","您输入的验证码存在错误，请重试");
                return "login";
            }
        }catch (Exception e){
            map.put("err","您输入的账号或密码存在错误，请重试");
            return "login";
        }

    }



    @RequestMapping("/gotoBackhome")
    public String gotoBackhome( Map<String,Object> map,HttpServletRequest request,ModelMap modelMap){

        List<TypeInfo> typeLists=typeMapper.selectAll(null);
        request.setAttribute("tlists",typeLists);
        return "background";

    }








    //前往注册页逻辑
    @RequestMapping("/topageregis")
    public String topageregis(){
        return "regis";
    }

    //向目标邮箱发送验证码逻辑
    @ResponseBody
    @RequestMapping("/mailre")
    public Result mailreP(User employee, HttpSession session,ModelMap map){
        session.setAttribute("emp",employee);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("欢迎使用邮件注册");
        String a;
        a= String.valueOf((int)((Math.random()*(9999-1000+1))+1000));
        session.setAttribute("code",a);
        message.setText(a);
        if(employee.getEmail()!=null&&employee.getEmail()!="") {
            message.setTo(employee.getEmail());
            message.setFrom("282075823@qq.com");
            mailSender.send(message);
            map.put("msgemail","请前往邮箱查询收到的验证码并输入完成最后的确认~!");
            return Result.success();
        }else {
            return Result.error("请输入邮箱");
        }

       // employeeMapper.insertEmp(employee);

    }

   //邮箱中确认验证码，成功后MD5盐值加密存入数据库
    @RequestMapping("/icode")
    public String check(HttpServletRequest request,HttpSession session){
       String mycode=request.getParameter("mcode");
       if(mycode.equals(session.getAttribute("code"))){
             User employee= (User) session.getAttribute("emp");
             //在注册时对于密码进行MD5加密存储
           String newPassword=TestMD5.encryPassword(employee.getPassword(),employee.getUsername());
           SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
           String nowtime=simpleDateFormat.format(new Date());
           employee.setTime(nowtime);
           employee.setPassword(newPassword);
              userMapper.insertEmp(employee);
              //此上为注册成功了已经
           UserModel userModel=new UserModel();
           userModel.setUserid(employee.getId());

           return "login";
       }else {
           request.setAttribute("error","您的验证码存在错误");
           return "regis";}

    }




    //个人中心的模块实现逻辑
    @RequestMapping("/personal")
    public String showme(HttpSession session){
        User user= (User) session.getAttribute("user");
        User user1=userMapper.getEmpById(user.getId());
        session.setAttribute("user",user1);
        return "personal";
    }


    @RequestMapping("/saveimg")
    @ResponseBody
    public Result savehead(@RequestParam(required = false,value = "imgurl") String imgurl,
                           @RequestParam(value = "id") Integer id){
             userMapper.setHead(imgurl,id);
       return Result.success();
    }



    @RequestMapping("/updateuserinfo")
    public String updateuserinfo(User user){
      userMapper.updateUserInfo(user);
        return "redirect:/personal";
    }






    //管理员查看各人详情
    @RequestMapping("/personalList")
    public String showsomeone(@RequestParam("id") Integer id,
                              HttpSession session,
                              @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                              @RequestParam(value="pageSize", defaultValue="5") int pageSize,
                              ModelMap map){
        User user=userMapper.getEmpById(id);
        session.setAttribute("userchoose",user);
        Map<String, Object> param=new HashMap<>();
        param.put("status",1);
        param.put("owner",user.getId());
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleInfo> list= articleInfoService.list(param);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
        map.put("pageInfo", pageInfo);
        return "personalList";
    }













/*
     *
     * 数据图表专项=================================================================================
     *
     * */

    //数据分析图表1之      文章数和阅读量还要分类信息，先去构造一个用于存放的bean
    @RequestMapping("/dataAnalyse")
    public String todataAnalyse(ModelMap modelMap){

        return "dataAnalyse";
    }


@RequestMapping("/dataAnalyseT")
@ResponseBody
    public Result dataAnalyse(){
       //1.柱状图所要携带的数据
        List<TypeInfo> typeInfos=typeMapper.getTypeName();
        List<TableOne> tableOnes=new ArrayList<>();
        for(int i=0;i<typeInfos.size();i++) {
            Map<String,Integer> map= articleInfoService.getArticleNumberAndReadNumnerByTypeId(typeInfos.get(i).getId());
            TableOne tableOne=new TableOne();
            tableOne.setTypeName(typeInfos.get(i).getName());
            tableOne.setArticleNumber(Integer.parseInt(String.valueOf(map.get("artnum"))));
            tableOne.setReadNumber(Integer.parseInt(String.valueOf(map.get("viewnum"))));
            tableOne.setAuthorNumber(Integer.parseInt(String.valueOf(map.get("authornum"))));
            tableOnes.add(tableOne);
        }

        //2.饼状图需要的数据


        return Result.success().add("table01",tableOnes);

    }



    //退出方法
    @RequestMapping("/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
         subject.logout();
        return "redirect:/index.action";
    }


    //关于我的模块实现
    @RequestMapping("/aboutPage")
    public String about(){
        return "/stage/about";
    }



    //前往用户统计表的页面2,携带数据
    @RequestMapping("/dataAnalyse2")
    public String touserdarta(ModelMap map){
        String beg;
        String end;
        List<Integer> userNums=new ArrayList<>();
        for(int i=0;i<12;i++){
            beg="2019-"+(i+1)+"-0";
            end="2091-"+(i+2)+"-0";
            userNums.add(userMapper.numsformonths(beg,end));
           /* System.out.println((i+1)+"月: "+userNums.get(i)+"用户数目");*/
        }
        map.put("userNums",userNums);
        return "dataAnalyse2";
    }



    //保存用户标签
    @RequestMapping("/savetags")
    @ResponseBody
    public Result savetags(String[] ids,@RequestParam("id") Integer id) {
        StringBuffer str=new StringBuffer();
        for(int k=0;k<ids.length;k++){
            if(k<ids.length-1) {
                str.append(ids[k] + ",");
            }else {
                str.append(ids[k]);
            }
        }
        String nstr=str.toString();
        userMapper.addtags(nstr,id);
        return Result.success();
    }



    //用户阅读评论已读
    @ResponseBody
    @RequestMapping("/setrecall")
    public Result setrecall(@RequestParam("id") Integer id){
        commentService.setrecall(id);
        return Result.success();
    }


    //用户消息数目
    @ResponseBody
    @RequestMapping("/numofmessage")
    public Result numofmessage(@RequestParam("myname") String myname){
        int j=0;

       List<CommentInfo> commentInfoList=commentService.getCommentInfoss(myname);
        for(int k=0;k<commentInfoList.size();k++){
            if(commentInfoList.get(k).getRecall()==1){
                j++;
            }
        }
        return Result.success().add("num",j);
    }












}
