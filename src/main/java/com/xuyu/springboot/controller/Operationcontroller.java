package com.xuyu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class Operationcontroller {



    @RequestMapping("/operation")
    public String tooperation(){
        return "operation";
    }





}
