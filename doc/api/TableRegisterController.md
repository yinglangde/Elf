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