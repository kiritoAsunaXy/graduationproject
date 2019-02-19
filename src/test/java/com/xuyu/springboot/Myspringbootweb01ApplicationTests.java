package com.xuyu.springboot;

import com.xuyu.springboot.bean.*;
import com.xuyu.springboot.elasticsearchRepository.ArticleRepository;
import com.xuyu.springboot.mapper.TypeMapper;
import com.xuyu.springboot.mapper.UserMapper;
import com.xuyu.springboot.service.ArticleInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Myspringbootweb01ApplicationTests {

    @Autowired
   JavaMailSenderImpl mailSender;


    @Autowired
    UserMapper employeeMapper;


    @Autowired
    RedisTemplate<Object,User> empRedisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;//操作字符串

    @Autowired
    RedisTemplate redisTemplate;//k，v都是对象的

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleInfoService articleInfoService;
    @Autowired
    TypeMapper typeMapper;

    @Test
    public void contextLoads() {

    }



    /*
    * String字符串，list列表，set集合。hash散列，Zset有序集合
    *   stringRedisTemplate.opsForValue()来简化操作字符串
    *   stringRedisTemplate.opsForHash()操作Hsah以此类推
    * */
    @Test
    public void mytestredis01(){
     //   stringRedisTemplate.opsForValue().set("xuyu","123456");
       // String xuyu = stringRedisTemplate.opsForValue().get("xuyu");
        //System.out.println(xuyu);
        stringRedisTemplate.opsForList().leftPush("list01","1");
        stringRedisTemplate.opsForList().leftPush("list01","2");
    }



    @Test
    public void test02(){

        //1.柱状图所要携带的数据
//        List<TypeInfo> typeInfos=typeMapper.getTypeName();
//
//        List<TableOne> tableOnes=new ArrayList<>();
//                for(int i=0;i<typeInfos.size();i++) {
//                   Map<String,Integer> map= articleInfoService.getArticleNumberAndReadNumnerByTypeId(typeInfos.get(i).getId());
//                  // System.out.println(typeInfos.get(i).getName()+"  有："+map.get("artnum")+" "+map.get("viewnum"));
//                  // tableOne.setTypeName(typeInfos.get(i).getName());
//                  TableOne tableOne=new TableOne();
//                  tableOne.setTypeName(typeInfos.get(i).getName());
//                  tableOne.setArticleNumber(Integer.parseInt(String.valueOf(map.get("artnum"))));
//                  tableOne.setReadNumber(Integer.parseInt(String.valueOf(map.get("viewnum"))));
//                  tableOnes.add(tableOne);
//                }
//
//                for(TableOne aa : tableOnes){
//                    System.out.println(aa);
//                }

    }



    @Test
    public void test03() throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);

        helper.setSubject("程序园博客反馈");
        helper.setText("<img src='http://127.0.0.1:8082/images/sorry.jpg' style='height: 300px;width: 350px'/>",true);
       // helper.setText("<h1 style='color:red'>哈哈哈<h1>",true);
        helper.setTo("18251969058@163.com");
        helper.setFrom("282075823@qq.com");


         mailSender.send(mimeMessage);

    }


    @Test
    public void testElastic(){
            System.out.println("进来了");
            //List<ElasticArticle> lists=articleRepository.findByTitleLike("java");
        //List<ElasticArticle> lists= articleRepository.findAllByTitle("关于");

        List<ElasticArticle> lists=articleRepository.findAllByTitleContains("linux");
            System.out.println(lists.size());
           for(ElasticArticle elasticArticle:lists){
               System.out.println(elasticArticle.getTitle());
           }




    }





}
