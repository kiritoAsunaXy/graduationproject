package com.xuyu.springboot.service;

import com.xuyu.springboot.bean.UserModel;
import com.xuyu.springboot.mapper.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserModelService {


    @Autowired
    UserModelMapper userModelMapper;



    public void updateUserModel(UserModel userModel) {
       userModelMapper.updateUserModel(userModel);
    }

    public int checkUserIfExist(Integer id) {
        return userModelMapper.checkUserIfExist(id);
    }

    public void insertUserModel(UserModel userModel) {
         userModelMapper.insertUserModel(userModel);
    }

    public List<UserModel> getAllModels() {
        return userModelMapper.getAllModels();
    }

    public UserModel getModelByUserId(Integer id) {
        return userModelMapper.getModelByUserId(id);
    }

    public void updateUserModelsubmitname(String a,Integer id) {
        userModelMapper.updateUserModelsubmitname(a,id);
    }

    public void updateScore(Integer score, Integer id) {
        userModelMapper.updateScore(score,id);
    }
}
