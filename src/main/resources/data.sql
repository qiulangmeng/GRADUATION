insert  into role(id,`name`,des) values
(1,''ROLE_SUPERADMIN'',''超级管理员''),
(2,''ROLE_ADMIN'',''管理员''),
(3,''ROLE_USER'',''用户'');

insert  into user(id,password,user_name,phone,sex,default_address_id,create_time,update_time,email,active_code,is_active) values
(1,''$2a$10$6IWt0A.g8PDjPWUvtRwote0RIajENtfBVS1GJl7WOUcLkrfsmkiX6'',''superadmin'',''15295527902'',''男'',1,NOW(),NOW(),''2573393471@qq.com'',''1111'',1),
(2,''$2a$10$6IWt0A.g8PDjPWUvtRwote0RIajENtfBVS1GJl7WOUcLkrfsmkiX6'',''admin'',''15295527902'',''男'',1,NOW(),NOW(),''2573393471@qq.com'',''1111'',1),
(3,''$2a$10$6IWt0A.g8PDjPWUvtRwote0RIajENtfBVS1GJl7WOUcLkrfsmkiX6'',''qlm'',''15295527902'',''男'',1,NOW(),NOW(),''2573393471@qq.com'',''1111'',0);

insert into user_role(user_id,role_id) values (1,1),(2,2),(3,3);
--
insert into authority (id,`name`) VALUES
(1,''CREATE_ADMIN''),
(2,''DELETE_ADMIN''),
(3,''DELETE_USER'');
INSERT INTO role_authority(role_id,authority_id) VALUES (1,1),(1,2),(1,3),(2,3);