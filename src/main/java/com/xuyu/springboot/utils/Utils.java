package com.xuyu.springboot.utils;


import com.xuyu.springboot.bean.ArticleInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {


    //排序
    public String SortTopFour(List<Integer> list) {
        Set uniqueSet = new HashSet(list);
        List<String> typeid = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (Object o : uniqueSet) {
            // System.out.println("id"+o+"出现了:"+Collections.frequency(arid,o));
            typeid.add(o.toString());
            count.add(Collections.frequency(list, o));
        }
        Integer  index = Maxindex.maxindex2(count);
        String ftypeid=typeid.get(index);

        return ftypeid;
    }


    //取得两个list中相同的元素个数,这个比较最常看的Top3文章类型
    public Integer getsamenum(List<Integer> list1,List<Integer> list2){
        Integer a=list1.size();
        list1.removeAll(list2);
        Integer num=a-list1.size();
        return num;
    }

  //这个比较关注的作者重复
    public Integer getnum(List<String> list1,List<String> list2){
        Integer a=list1.size();
        list1.removeAll(list2);
        Integer num=a-list1.size();
        return num;
    }



    //此方法用于数组去重复
    public List<Integer>  removeDuplicate(List<Integer> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    //用来将数据库存入的toString类型的List还原为list
    public static List<String> backList(String str){


        str=str.substring(1,str.length()-1);
        str=str.replace(" ","");
        String a[]=str.split(",");
        List<String> b=new ArrayList<>();
        for(int i=0;i<a.length;i++){
            b.add(a[i]);
        }
        return b;
    }


    //这个方法用于随机产生新的list下标顺序
    public static List<Integer> randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            System.out.println("in");
            return null;
        }
        List<Integer> result = new ArrayList<>();
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < result.size(); j++) {
                if(num == result.get(j)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result.add(num);
                count++;
            }
        }
        return result;
    }



    //用来实现重新排序
    public static List<Integer> re(List<Integer> a,List<Integer> b){
        //a为原始数组，b为随机排序下标
        List<Integer> c=new ArrayList<>();
        for(int i=0;i<a.size();i++){
            c.add(a.get(b.get(i)));
        }
        return c;

    }

    //实现一个数组按一个数组序列重排序
    public static List<ArticleInfo> rechange(List<ArticleInfo> article,List<String> sort){
        List<Integer> list=new ArrayList<>();
        sort.forEach(item -> {
            list.add(Integer.parseInt(item));
        });

        return null;
    }


    //写一个方法用于首页的游客推荐
    public static List<Integer>  homeforguides(List<ArticleInfo> alllist,List<ArticleInfo> hotlist){
        if(hotlist.size()<5){
            //不作处理
            return null;
        }else {
            //1.产生5个0~9的随机数代表从热点文章中随机出的文章
            List<Integer> indexList = new ArrayList<>();
            while (indexList.size()<5){
                Integer index = new Random().nextInt(10);
                if( !indexList.stream().anyMatch( e -> e==index)){
                    indexList.add(index);
                }
            }
             //2.根据随机产生的下标拿到hotlist里面5个具体的文章
            List<ArticleInfo> newHotList = indexList.stream()
                    .map(index -> hotlist.get(index)).collect(Collectors.toList());
             //3.构建最终返回结果list
            List<ArticleInfo> result = new ArrayList<>();
            while (true){
                //生成在前十个插入位置的随机数,直接使用上面的indexList
                //先插入所有的
                result.addAll(alllist);
                result.addAll(hotlist);
                int count=0;
                Stream.iterate(0, i -> i + 1).limit(result.size()).forEach(i -> {
                    if(indexList.contains(i)){
                        result.set(i,newHotList.get(0));
                        newHotList.remove(0);
                    }
                });
                if(newHotList.size()==0)break;
            }
            List<Integer> ids=new ArrayList<>();
            result.forEach(item ->ids.add(Integer.parseInt(item.getId())));
            return ids.stream().distinct().collect(Collectors.toList());
        }


    }



}
