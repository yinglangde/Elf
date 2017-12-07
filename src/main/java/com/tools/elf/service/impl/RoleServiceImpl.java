package com.tools.elf.service.impl;

import com.tools.elf.bean.UserGroupRef;
import com.tools.elf.service.RoleService;
import com.tools.elf.util.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author bin.zhang
 * @create 2017-11-24 16:13
 **/
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object getUserGroups(Long userId) {
        try {
            List list=jdbcTemplate.queryForList("select group_id from user_group_ref where user_id =?",userId);
            return ResponseFactory.instance(true,list,"获取成功！");
        }catch (Exception e){
            logger.info("error:",e);
            return ResponseFactory.instance(false,null,"获取用户组异常 ！" + e.getMessage());
        }
    }


    @Override
    @Transactional
    public Object editUserGroupRef(final List<UserGroupRef> userGroupRefList) {
        int[] insertRow;
        if(userGroupRefList.size()==0){
            return ResponseFactory.instance(true,null,"编辑成功！无需操作数据！");
        }
        Long userId=userGroupRefList.get(0).getUserId();
        try {
            int delRow = jdbcTemplate.update("delete from user_group_ref where user_id=?",userId);

            String sql="insert into user_group_ref(user_id,group_id) values(?,?)";
            insertRow = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    UserGroupRef userGroupRef = userGroupRefList.get(i);
                    preparedStatement.setLong(1,userGroupRef.getUserId());
                    preparedStatement.setLong(2,userGroupRef.getGroupId());
                }

                @Override
                public int getBatchSize() {
                    return userGroupRefList.size();
                }
            });
            return ResponseFactory.instance(true,insertRow,"编辑成功！");
        }catch (Exception e){
            logger.info("error:",e);
            return ResponseFactory.instance(false,null,"编辑用户组关系异常 ！" + e.getMessage());
        }
    }

    /****************************分割线 以下 接口与用户无关******************************/
    @Override
    public Object getGroupNameList() {
        try {
            List list=jdbcTemplate.queryForList("select id,group_name,group_desc from role_group where disabled =0");
            return ResponseFactory.instance(true,list,"获取成功！");
        }catch (Exception e){
            logger.info("error:",e);
            return ResponseFactory.instance(false,null,"获取用户组异常 ！" + e.getMessage());
        }
    }

    @Override
    public Object addGroupDbRef(Integer groupId,String dbName) {
        try {
            int count=jdbcTemplate.update("insert INTO group_db_ref(group_id,db_name) values (?,?)",groupId,dbName);
            return ResponseFactory.instance(true,count,"添加成功！");
        }catch (Exception e){
            logger.info("error:",e);
            return ResponseFactory.instance(false,null,"添加失败 ！" + e.getMessage());
        }
    }
}
