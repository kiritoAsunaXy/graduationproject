package com.xuyu.springboot.mapper;

import com.xuyu.springboot.bean.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    void insertPermission(Permission permission);

    List<Permission> queryAllDate();

    List<Permission> queryPermissionByUserId(Integer id);

    void delAllByUserId(String id);

    List<String> queryPermissionsByUserId(Integer id);
}
