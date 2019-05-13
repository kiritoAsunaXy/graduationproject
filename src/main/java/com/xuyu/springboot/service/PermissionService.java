package com.xuyu.springboot.service;

import com.xuyu.springboot.bean.Permission;
import com.xuyu.springboot.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    public void insertPermission(Permission permission) {
        permissionMapper.insertPermission(permission);
    }

    public List<Permission> queryAllDate() {
       return permissionMapper.queryAllDate();
    }

    public List<Permission> queryPermissionByUserId(Integer id) {

        return permissionMapper.queryPermissionByUserId(id);
    }

    public void delAllByUserId(String id) {
        permissionMapper.delAllByUserId(id);
    }

    public List<String> queryPermissionsByUserId(Integer id) {
        return permissionMapper.queryPermissionsByUserId(id);
    }
}
