package com.xuyu.springboot.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class timer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args)  {
        System.out.println("今日最热重置方法开始执行！！！！");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23); // 控制时
        calendar.set(Calendar.MINUTE, 59);       // 控制分
        calendar.set(Calendar.SECOND, 0);       // 控制秒
        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                    try {
                        //System.out.println("今日最热重置方法执行成功！！！！");
                    Jedis jedis=new Jedis("192.168.181.131",6379);
                    jedis.select(1);
                    jedis.flushDB();
                    jedis.close();
                }catch (Exception e){
                    System.out.println("还未启动redis");
                }
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
}
