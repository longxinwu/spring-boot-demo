##用户表
DROP TABLE IF EXISTS `prod_user`;
create table `prod_user` (
	id int(11) not null AUTO_INCREMENT,
	user_name varchar(50) not null COMMENT '用户名',
	user_pass varchar(255) not null COMMENT '密码',
	role varchar(50) default null ,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into `prod_user` values(1, 'admin1', '123456','admin');