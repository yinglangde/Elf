package com.tools.elf.controller.Role;

import com.tools.elf.bean.UserGroupRef;
import com.tools.elf.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bin.zhang
 * @create 2017-11-24 15:10
 **/

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService impl;

    @RequestMapping(value = "/getUserGroups",method = RequestMethod.POST)
    @ResponseBody
    public Object getUserGroups(@RequestParam("userId") Long userId){
        return impl.getUserGroups(userId);
    }

    @RequestMapping(value = "/editUserGroupRef",method = RequestMethod.POST)
    @ResponseBody
    public Object editUserGroupRef(@RequestBody List<UserGroupRef> userGroupRefList){
        return impl.editUserGroupRef(userGroupRefList);
    }

    /****************************分割线 以下 接口与用户无关******************************/

    /**
     * 获取权限组信息
     * @return
     */
    @RequestMapping(value = "/getGroupNameList",method = RequestMethod.GET)
    @ResponseBody
    public Object getGroupNameList(){
        return impl.getGroupNameList();
    }

    /**
     * 添加组访问hive库关系
     * @return
     */
    @RequestMapping(value = "/addGroupDbRef",method = RequestMethod.POST)
    @ResponseBody
    public Object addGroupDbRef(@RequestParam("groupId") Integer groupId,@RequestParam("dbName") String dbName){
        return impl.addGroupDbRef(groupId,dbName);
    }

}
