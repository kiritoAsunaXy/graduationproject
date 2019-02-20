package com.xuyu.springboot.mapper;

import com.xuyu.springboot.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {


    public List<User> checkLogin(User employee);

    public User getEmpById(Integer id);

    public  void  insertEmp(User employee);

    public void updateEmp(User employee);

    public User getUserByName(String name);

    public void  setHead(@Param("imgurl") String imgurl,@Param("id") Integer id);

    void updateUserInfo(User user);

    List<User> selectAllUser();

    String selectHeadByName(String myattentionname);

    void addtags(@Param("str") String str, @Param("id") Integer id);

    Integer numsformonths(@Param("beg") String beg, @Param("end") String end);

    List<String> getAllUserName();
}
