drop table if exists meta_table;
create table meta_table (
  id int(20) not null auto_increment comment '表id'
 ,db_name varchar(100) comment '数据库名称'
 ,table_name varchar(100) comment '表名'
 ,table_comment varchar(200) comment '表注释'
 /*,expired_days_count_down int(20) comment '剩余过期天数'*/
 ,expired_days int(20) comment '申请有效天数'
 ,valid_date timestamp not null default '0000-00-00 00:00:00' comment '截止有效日期 = 当前期 + 申请有效天数'
 ,file_separator_code int(20) not null default 0 comment '分隔符: 0表示制表符 , 1表示逗号'
 ,storage_format_code int(20) not null default 0 comment '数据文件存储格式(0:text, 1:RCFILE, 2:ORCFILE, 3:LZO)'
 ,table_owner varchar(100) comment '创建表的用户'
 ,create_sql varchar(4000) comment '执行的建表语句'
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 ,dev_product  int(20) NOT NULL default 0 comment '是否开发生产永久表，0否，1是'
 ,unique key(id)
 ,unique key(db_name,table_name)
) ENGINE=INNODB
CHARACTER SET utf8
comment '表-元数据'
;

drop table if exists meta_column;
create table meta_column (
  id int(20) not null auto_increment comment '列id'
 ,table_id int(20) not null comment '表id'
 ,column_name varchar(100) not null comment '列名称'
 ,column_type varchar(100) not null comment '列类型'
 ,column_comment varchar(200) comment '列描述'
 /*
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 */
 ,unique key(id)
 ,unique key(table_id,column_name)
) ENGINE=INNODB
CHARACTER SET utf8
comment '列-元数据'
;

drop table if exists meta_partition;
create table meta_partition (
  id int(20) not null auto_increment comment '分区id'
 ,table_id int(20) not null comment '表id'
 ,partition_name varchar(100) not null comment '分区名称'
 ,partition_type varchar(100) not null comment '分区类型'
 ,partition_comment varchar(200) comment '分区描述'
 /*
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 */
 ,unique key(id)
 ,unique key(table_id,partition_name)
) ENGINE=INNODB
CHARACTER SET utf8
comment '分区-元数据'
;

drop table if exists hive_table_datafile;
create table hive_table_datafile (
  id int(20) not null auto_increment comment '数据文件id'
 ,table_id int(20) not null comment '表id'
 ,server_location varchar(100) comment '数据文件服务器位置-绝对路径'
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 ,unique key(id)
) ENGINE=INNODB
CHARACTER SET utf8
comment '上传数据文件存储'
;

drop table if exists group_db_ref;
create table group_db_ref (
  id int(20) not null auto_increment comment 'id'
 ,group_id int(20) not null comment '组id'
 ,db_name varchar(100) comment '数据库名称'
 ,unique key(id)
 ,unique key(group_id,db_name)
) ENGINE=INNODB
CHARACTER SET utf8
comment '组对应数据库权限关系'
;
insert INTO group_db_ref(group_id,db_name) values(1,"report");
insert INTO group_db_ref(group_id,db_name) values(1,"vbawork_base");
insert INTO group_db_ref(group_id,db_name) values(1,"vbawork_report");
insert INTO group_db_ref(group_id,db_name) values(1,"vbawork_business");
insert INTO group_db_ref(group_id,db_name) values(1,"vbawork_business_klx");
insert INTO group_db_ref(group_id,db_name) values(1,"tmp");
insert INTO group_db_ref(group_id,db_name) values(1,"model");

insert INTO group_db_ref(group_id,db_name) values(5,"report");
insert INTO group_db_ref(group_id,db_name) values(5,"vbawork_base");
insert INTO group_db_ref(group_id,db_name) values(5,"vbawork_report");
insert INTO group_db_ref(group_id,db_name) values(5,"vbawork_business");
insert INTO group_db_ref(group_id,db_name) values(5,"vbawork_business_klx");
insert INTO group_db_ref(group_id,db_name) values(5,"tmp");

insert INTO group_db_ref(group_id,db_name) values(6,"tmp");
insert INTO group_db_ref(group_id,db_name) values(6,"model");
