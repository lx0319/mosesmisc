use ibatisstudy;
drop table if exists sbook;
create table if not exists sbook
(
	id int primary key auto_increment,
	title varchar(50),
	author varchar(20),
	total int,
	price float,
	isbn varchar(20),
	publisher varchar(50)
);
