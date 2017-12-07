package com.tools.elf.service.impl;

import com.mysql.jdbc.Statement;
import com.tools.elf.bean.User;
import com.tools.elf.service.UserService;
import com.tools.elf.util.ResponseFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2017-11-20 17:34
 **/
@Service
public class UserServiceImpl implements UserService{

    private Logger logger= Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Override
//    public Object userAdd(String userName,String passWord,String email) {
//        try {
//            int count= jdbcTemplate.update("insert into user(user_name,pass_word,email) values(?,?,?)",userName,passWord,email);
//            return ResponseFactory.instance(true,count,"添加新用户成功！");
//        }catch (Exception e){
//            logger.info("error:{}",e);
//            return ResponseFactory.instance(false,null,e.getMessage());
//        }
//    }

    @Override
    public Object userAdd(User user) {
        final String sql="insert into user(user_name,pass_word,email,create_time) values(?,?,?,NOW())";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String userName=user.getUserName();
        final String passWord=user.getPassWord();
        final String email=user.getEmail();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1,userName);
                    ps.setString(2,passWord);
                    ps.setString(3,email);
                    return ps;
                }
            },keyHolder);
            Map<String,Integer> map =new HashMap<String,Integer>();
            map.put("id",keyHolder.getKey().intValue());
            return ResponseFactory.instance(true,map,"添加新用户成功！");
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null,"添加失败！ " + e.getMessage());
        }
    }

    @Override
    public Object userDeleteById(Long id) {
        try {
            int count=jdbcTemplate.update("update user set disabled = 1 where id=? and disabled=0",id);
            if(count==0){
                return ResponseFactory.instance(true,count,"未找要删除的记录！");
            }else {
                return ResponseFactory.instance(true,count,"删除成功！");
            }
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(true,null,"删除失败！" +e.getMessage());
        }
    }

    @Override
    public Object userUpdateById(User user) {
        try {
            int count=jdbcTemplate.update("update user set pass_word=?,email=? where id=?",user.getPassWord(),user.getEmail(),user.getId());
            return ResponseFactory.instance(true,count,"更新成功！");
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null,"更新失败" +e.getMessage());
        }
    }


    @Override
    public Object findById(Long id) {
        try {
            List list =jdbcTemplate.queryForList("select id,user_name,pass_word,email from user where id=?" ,id);
            return ResponseFactory.instance(true,list,"查找成功！");
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null,"查找失败！" +e.getMessage());
        }
    }

    @Override
    public Object findByNameOrEmail(String keyWord) {
        String str = "%"+keyWord+"%";
        try {
            List list =jdbcTemplate.queryForList("select id,user_name,pass_word,email from user where user_name like ? or email like ?" ,str,str);
            return ResponseFactory.instance(true,list,"查找成功！");
        }catch (Exception e){
            logger.info("error:{}",e);
            return ResponseFactory.instance(false,null,"查找失败！" +e.getMessage());
        }
    }

}
