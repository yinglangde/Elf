/*用户表*/
drop table if exists user;
create table user (
  id int(20) not null auto_increment comment 'id'
 ,user_name varchar(100) not null comment '用户名'
 ,pass_word varchar(100) not null  comment '用户密码'
 ,email varchar(100) not null comment '用户密码'
 ,create_time timestamp not null default '0000-00-00 00:00:00' comment '创建时间'
 ,update_time timestamp not null default current_timestamp comment '更新时间'
 ,disabled boolean not null default 0 comment '有效性标识'
 ,UNIQUE key(id)
 ,UNIQUE key(user_name)
 ,UNIQUE key(email)
) ENGINE=INNODB
CHARACTER SET utf8
comment '用户'
;
/*
insert into user(user_name,pass_word) VALUES('admin',PASSWORD('admin'));
select * from user where pass_word=PASSWORD('admin');
*/
insert into user(id,user_name,pass_word,email,create_time) VALUES(1,'admin','admin','admin@elf.com',NOW());
insert into user(id,user_name,pass_word,email,create_time) VALUES(2,'bin.zhang','123456','bin.zhang@elf.com',NOW());
select * from user;

