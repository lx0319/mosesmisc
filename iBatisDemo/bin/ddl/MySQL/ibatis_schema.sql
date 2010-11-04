create database if not exists `ibatis_schema`;

USE `ibatis_schema`;

drop table if exists `t_user`;

CREATE TABLE `t_user` (
  `id` int(12) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `date` date default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=GBK;

insert into `t_user`(name,date) values ('liulu','2007-03-15'),('liulu2','2007-03-15'),('liulu3','2007-03-15');
