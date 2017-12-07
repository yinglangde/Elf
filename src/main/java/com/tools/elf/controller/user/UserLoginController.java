package com.tools.elf.controller.user;

import com.tools.elf.service.UserLoginService;
import com.tools.elf.service.impl.UserLoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author
 * @create 2017-11-08 9:33
 **/

@Controller
@RequestMapping(value = "/login")
public class UserLoginController {
    private Logger logger= LoggerFactory.getLogger(UserLoginController.class);

    //private UserLoginServiceImpl impl = new UserLoginServiceImpl(); 这种方法是错误的，会使得jdbcTemplate的db配置无法注入，导致impl调用jdbcTemplate报NPE的错误
    @Autowired
    private UserLoginService impl;

    @RequestMapping(value = "/verify" ,method = RequestMethod.POST)
    @ResponseBody
    public Object verifyUser(@RequestParam("userName") String userName , @RequestParam("passWord") String passWord){
        return impl.verifyUser(userName,passWord);
    }

}
