#用户登录模块 UserLoginController

##一.验证用户
###请求地址：http://localhost:8080/login/verify
```
parameter:
    {
        "userName": String  //用户名
        ,"passWord": String //用户密码
    }
response:
    {
      "data": {
        "id": 2,
        "userName": "bin.zhang",
        "passWord": null,
        "email": "bin.zhang@elf.com"
      },
      "success": true,
      "info": "用户已验证！"
    }
```