package com.xuyu.springboot.service;


import com.xuyu.springboot.bean.User;
import com.xuyu.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    UserMapper employeeMapper;

 /*
 * 将方法的运行结果进行缓存，以后要相同的数据直接去缓存里面找
 *
 * CacheMahager管理多个cache组件的，每一个缓存组件有着自己唯一的名字
 * cacheNames指定组件名字
 * key value
 * key = "#id"==root.args[0]
 *
 * keyGenerator与key二选一
 * condition符合条件才缓存
 * sync是否使用异步
 * */


    @Cacheable(cacheNames = "emp",keyGenerator = "myKeyGenerator",condition = "#id>0")
    public User getEmp(Integer id){
        System.out.println("查询"+id+"员工号");
        User employee=employeeMapper.getEmpById(id);
        return employee;
    }


    @CachePut(value = "emp")
    public User updateEmp(User employee){
        employeeMapper.updateEmp(employee);
        return employee;
    }

}
