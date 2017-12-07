/*角色(权限)组表*/
drop table if exists role_group;
create table role_group (
  id int(20) not null auto_increment comment 'id'
 ,group_name varchar(100) not null comment '组名称'
 ,group_desc varchar(200) comment '组描述'
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 ,UNIQUE key(id)
 ,UNIQUE key(group_name)
) ENGINE=INNODB
CHARACTER SET utf8
comment '权限组表'
;
insert into role_group(id,group_name,group_desc,create_time) values (1,'admin','管理员组',NOW());
insert into role_group(id,group_name,group_desc,create_time) values (2,'dev_warehouse','开发仓库组',NOW());
insert into role_group(id,group_name,group_desc,create_time) values (3,'dev_report','开发产品组',NOW());
insert into role_group(id,group_name,group_desc,create_time) values (4,'dev_model','开发模型组',NOW());
insert into role_group(id,group_name,group_desc,create_time) values (5,'product','产品组',NOW());
insert into role_group(id,group_name,group_desc,create_time) values (6,'other','其他用户',NOW());


/*用户权限组关系
drop table if exists user_group_ref;
create table user_group_ref (
  id int(20) not null auto_increment comment 'id'
 ,user_id int(20) not null comment '用户id'
 ,group_id int(20) not null comment '组id'
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 ,UNIQUE key(id)
 ,UNIQUE key(user_id,group_id)
) ENGINE=INNODB
CHARACTER SET utf8
comment '用户权限组关系'
;
insert into user_group_ref (user_id,group_id,create_time) VALUES (1,1,NOW());
insert into user_group_ref (user_id,group_id,create_time) VALUES (2,2,NOW());
insert into user_group_ref (user_id,group_id,create_time) VALUES (2,3,NOW());
*/

/*用户权限组关系*/
drop table if exists user_group_ref;
create table user_group_ref (
  user_id int(20) not null comment '用户id'
 ,group_id int(20) not null comment '组id'
 ,UNIQUE key(user_id,group_id)
) ENGINE=INNODB
CHARACTER SET utf8
comment '用户权限组关系'
;
insert into user_group_ref (user_id,group_id) VALUES (1,1);
insert into user_group_ref (user_id,group_id) VALUES (2,2);
insert into user_group_ref (user_id,group_id) VALUES (2,3);


/*组对应数据库权限关系*/
drop table if exists group_db_ref;
create table group_db_ref (
 ,group_id int(20) not null comment '组id'
 ,db_name varchar(100) comment '数据库名称'
 ,unique key(group_id,db_name)
) ENGINE=INNODB
CHARACTER SET utf8
comment '组对应数据库权限关系'
;
/*1,'admin','管理员组'*/
insert INTO group_db_ref(group_id,db_name) values(1,"storage_model");
insert INTO group_db_ref(group_id,db_name) values(1,"storage_report");
insert INTO group_db_ref(group_id,db_name) values(1,"storage_product");
insert INTO group_db_ref(group_id,db_name) values(1,"storage_fdm");
insert INTO group_db_ref(group_id,db_name) values(1,"storage_gdm");
insert INTO group_db_ref(group_id,db_name) values(1,"storage_adm");
insert INTO group_db_ref(group_id,db_name) values(1,"tmp");
/*仓库*/
insert INTO group_db_ref(group_id,db_name) values(2,"storage_fdm");
insert INTO group_db_ref(group_id,db_name) values(2,"storage_gdm");
insert INTO group_db_ref(group_id,db_name) values(2,"storage_adm");
insert INTO group_db_ref(group_id,db_name) values(2,"tmp");
/*报表*/
insert INTO group_db_ref(group_id,db_name) values(3,"storage_report");
insert INTO group_db_ref(group_id,db_name) values(3,"tmp");
/*模型*/
insert INTO group_db_ref(group_id,db_name) values(4,"storage_model");
insert INTO group_db_ref(group_id,db_name) values(4,"tmp");
/*产品用户*/
insert INTO group_db_ref(group_id,db_name) values(5,"storage_product");
insert INTO group_db_ref(group_id,db_name) values(5,"tmp");
/*其他用户*/
insert INTO group_db_ref(group_id,db_name) values(6,"tmp");