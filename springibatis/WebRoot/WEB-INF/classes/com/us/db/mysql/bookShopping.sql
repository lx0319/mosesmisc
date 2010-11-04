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
drop table if exists book;
create table if not exists book
(
    id varchar(32) primary key  not null,
    type_code varchar(18), 
    title varchar(50),
    author varchar(20),
    publisher varchar(50),
    total int,
    price float,
    price_rebate float,
    publish_date date,
    pagecount int,
    simple_content varchar(2000),
    add_time date,
    book_image varchar(200),
    isbn varchar(20)    
);
drop table if exists booktype;
create table if not exists booktype
(
	id varchar(32) primary key,
	type_code varchar(18),
	type_name varchar(20)
);