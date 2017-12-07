package com.tools.elf.service;


/**
 * @author
 * @create 2017-11-08 17:46
 **/
public interface UserLoginService {
    Object verifyUserExists(String userName);

    Object verifyUser(String userName,String passWord);
}
