#用户模块 UserController

##一.新增用户
###请求地址：http://localhost:8080/user/userAdd
```
parameter:
    {
        "userName": String  //用户名
        ,"passWord": String //用户密码,默认传 123456
        ,"email": String //用户邮箱
    }
response:
    {
      "data": {
        "id": int  //添加成功返回 用户id
      },
      "success": boolean,
      "info": String
    }
```

##二.删除用户
###请求地址：http://localhost:8080/user/userDel
```
parameter:
    "id": Long  //用户id
response:
    {
      "data": int,
      "success": boolean,
      "info": String
    }
```


##三.修改用户
###请求地址：http://localhost:8080/user/userUpdate
```
parameter:
    {
        "id": Long  //用户id
        ,"userName": String  //用户名
        ,"passWord": String //用户密码,默认传 123456
        ,"email": String //用户邮箱
    }
response:
    {
      "data": int,
      "success": boolean,
      "info": String
    }
```

##四.查询用户
###请求地址一，通过id查找：http://localhost:8080/user/userFindById
```
parameter:
    "id": Long  //用户id
response:
    {
      "data": [
        {
          "id": 2,
          "user_name": "bin.zhang",
          "pass_word": "123",
          "email": "bin.zhang@elf.com"
        }
      ],
      "success": true,
      "info": "查找成功！"
    }
```


###请求地址二，通过关键字查询(用户名和邮箱)：http://localhost:8080/user/userFindById
```
parameter:
    keyWord: String  //用户或者邮箱
response:
    {
      "data": [
        {
          "id": 2,
          "user_name": "bin.zhang",
          "pass_word": "123",
          "email": "bin.zhang@elf.com"
        }
      ],
      "success": true,
      "info": "查找成功！"
    }
```