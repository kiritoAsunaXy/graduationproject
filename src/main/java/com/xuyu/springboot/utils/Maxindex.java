package com.xuyu.springboot.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maxindex {

    public static List<Integer> maxindex(List<Integer> list){
        int max=Collections.max(list);
        List<Integer> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i)==max){
                list1.add(i);
            }
        }
        return list1;
    }

    public static Integer maxindex2(List<Integer> list){
        int max=Collections.max(list);
        Integer a=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i)==max){
               a=i;
            }
        }
        return a;
    }

}
