package com.xuyu.springboot.mapper;


import com.xuyu.springboot.bean.CommentInfo;
import com.xuyu.springboot.bean.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserModelMapper {

    void updateUserModel(UserModel userModel);

    int checkUserIfExist(Integer id);

    void insertUserModel(UserModel userModel);

    List<UserModel> getAllModels();

    UserModel getModelByUserId(Integer id);

    void updateUserModelsubmitname(@Param("lists") String a,@Param("id") Integer id);

    void updateScore(@Param("score") Integer score, @Param("id") Integer id);
}
