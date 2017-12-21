#建表模块 TableRegisterController

##一.创建表
###请求地址：http://localhost:8080/table/tableRegister

```
parameter:
    {
    	"tableProperty":{
    		"tableName":"tbl_elf",
    		"tableComment":"test-elf",
    		"dbName":"fdm",
    		"expiredDays": 7,
    		"fileSeparatorCode":0,
    		"storageormatCode":3,
    		"tableOwner":"bin.zhang",
    		"devProduct":"0"
    	},
    	"columns":[
    		{"columnName":"cola","columnType":"string","columnComment":"111"},
    		{"columnName":"colb","columnType":"string","columnComment":"222"}
    	],
    	"partitions":[
    		{"partitionName":"dt","partitionType":"string","partitionComment":"111"},
    		{"partitionName":"subject","partitionType":"string","partitionComment":"222"}
    	]
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


##二.编辑表属性(属主，注释，有效时长等)
###请求地址：http://localhost:8080/table/editTableProperty
```
parameter:
    {
    	"id":1,
    	"tableName":"tbl_elf",
    	"tableComment":"test-elf",
    	"dbName":"adm",
    	"expiredDays": 14,
    	"fileSeparatorCode":0,
    	"storageormatCode":0,
    	"tableOwner":"admin",
    	"devProduct":"0",
        "disabled":true
    }
response:
    {
      "data": 1,
      "success": false,
      "info": "编辑成功 ！"
    }
```


##三.编辑列注释
###请求地址：http://localhost:8080/table/editColumnComment
```
parameter:
    {
    	"tableId":1
    	,"columnName":"cola"
    	,"columnType":"string"
    	,"columnComment":"1qwe"
    }

response:
    {
      "data": 1,
      "success": true,
      "info": "编辑成功 ！==> SQL:update meta_column set column_comment=? where table_id=? and column_name=?"
    }
```