package com.tools.elf.service.impl;

import com.tools.elf.bean.User;
import com.tools.elf.service.UserLoginService;
import com.tools.elf.util.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2017-11-08 17:47
 **/
@Service
public class UserLoginServiceImpl implements UserLoginService {

    private Logger logger = LoggerFactory.getLogger(UserLoginService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object verifyUser(String userName,String passWord) {
        try {
            List list = jdbcTemplate.queryForList("select id,user_name,pass_word,email from user where user_name=? and pass_word=? limit 1",userName,passWord);
            for(Object obj:list){
                Map map=(Map)obj;
                String name =(String)map.get("user_name");
                String pw =(String)map.get("pass_word");
                if(name.equals(userName) && pw.equals(passWord)){
                    User user = new User();
                    user.setId( Long.parseLong(map.get("id").toString()));
                    user.setUserName(userName);
                    user.setEmail((String)map.get("email"));
                    return ResponseFactory.instance(true,user,"用户已验证！");
                }
            }
            return ResponseFactory.instance(false,null,"用户或密码错误！");
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null," 验证异常 ！"+e.getMessage());
        }
    }

    @Override
    public Object verifyUserExists(String userName) {
        return null;
    }
}
