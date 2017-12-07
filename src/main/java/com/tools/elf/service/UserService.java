package com.tools.elf.service;

import com.tools.elf.bean.User;

/**
 * @author
 * @create 2017-11-20 17:33
 **/

public interface UserService {

    //添加 新用户返回新增数据
    //Object userAdd(String userName,String passWord,String email);
    Object userAdd(User user);

    //删除
    Object userDeleteById(Long id);

    //修改
    Object userUpdateById(User user);

    //查找
    Object findById(Long id);
    Object findByNameOrEmail(String keyWord);
}
