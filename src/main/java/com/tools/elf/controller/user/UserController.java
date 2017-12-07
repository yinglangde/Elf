package com.tools.elf.controller.user;

import com.tools.elf.bean.User;
import com.tools.elf.service.UserService;
import com.tools.elf.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @create 2017-11-20 17:31
 **/

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger= Logger.getLogger(UserController.class);

    @Autowired
    private UserService impl;

    /**
     * 添加新用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/userAdd" ,method = RequestMethod.POST)
    @ResponseBody
    public Object userAdd(@RequestBody User user){
        return impl.userAdd(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/userDel" ,method = RequestMethod.POST)
    @ResponseBody
    public Object userDel(@RequestParam("id") Long id){
        return impl.userDeleteById(id);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/userUpdate" ,method = RequestMethod.POST)
    @ResponseBody
    public Object userUpdate(@RequestBody User user){
        return impl.userUpdateById(user);
    }

    /**
     * 查找用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/userFindById" ,method = RequestMethod.POST)
    @ResponseBody
    public Object userFindById(@RequestParam("id") Long id){
        return impl.findById(id);
    }

    @RequestMapping(value = "/userFindByNameOrEmail" ,method = RequestMethod.POST)
    @ResponseBody
    public Object findByNameOrEmail(@RequestParam("keyWord") String keyWord){
        return impl.findByNameOrEmail(keyWord);
    }

}
