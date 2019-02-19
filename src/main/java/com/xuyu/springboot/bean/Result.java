package com.xuyu.springboot.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;


//用来处理json返回

public class Result {
    //code状态码,成功000000，失败999999
    private String code;
    //返回的信息
    private String message;
    //返回的数据(链式结构)
    private Map<String, Object> data=new HashMap<>();


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }



    //正确的方法
    public static Result success() {
        // TODO Auto-generated method stub
        Result result=new Result();
        result.setCode("000000");
        result.setMessage("操作成功");
        return result;
    }


    //错误的方法
    public static Result error(String string) {
        // TODO Auto-generated method stub
        Result result=new Result();
        result.setCode("999999");
        if(StringUtils.isEmpty(string)) {
            result.setMessage("操作失败");
        }else {
            result.setMessage(string);
        }
        return result;
    }



    public Result add(String key,Object value) {
        //Result result=new Result();
        this.getData().put(key, value);
        return this;
    }


}
