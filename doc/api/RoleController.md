#权限模块 UserLoginController

##一.获取用户组列表
###请求地址：http://localhost:8080/role/getUserGroups
```
parameter:
    userId Long
response:
    {
      "data": [
        {
          "group_id": 2
        },
        {
          "group_id": 3
        }
      ],
      "success": true,
      "info": "获取用户组成功！"
    }
```

##二.获取角色组列表
###请求地址：http://localhost:8080/role/getGroupNameList
```
get
response:
    {
      "data": [
        {
          "id": 1,
          "group_name": "admin",
          "group_desc": "管理员组"
        },
        {
          "id": 2,
          "group_name": "dev_warehouse",
          "group_desc": "开发仓库组"
        }
      ],
      "success": true,
      "info": "获取成功！"
    }
```


##三.编辑用户 和 权限组 关系
###请求地址：http://localhost:8080/role/editUserGroupRef
```
parameter:
    [
        {
            "userId":2,
            "groupId":3
        },
        {
            "userId":2,
            "groupId":4
        }
    ]
response:
    {
      "data": [
        1,
        1
      ],
      "success": true,
      "info": "编辑成功！"
    }
```
