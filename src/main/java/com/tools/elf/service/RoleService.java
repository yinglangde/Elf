package com.tools.elf.service;

import com.tools.elf.bean.UserGroupRef;

import java.util.List;

/**
 * @author
 * @create 2017-11-24 15:44
 **/
public interface RoleService {
    Object getUserGroups(Long userId);

    /**
     * 修改用户组
     * @param userGroupRefList
     * @return
     */
    Object editUserGroupRef(final List<UserGroupRef> userGroupRefList);

    /**************************** 分割线 以下接口 与用户无关 ******************************/
    /**
     * 获取 角色组列表详情
     * @return
     */
    Object getGroupNameList();

    Object addGroupDbRef(Integer groupId,String dbName);
}
