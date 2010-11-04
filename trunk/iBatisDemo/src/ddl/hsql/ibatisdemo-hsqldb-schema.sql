drop table t_user;

create table t_user (
    id int not null IDENTITY,
    name varchar(80) null,
    date date null,
    primary key (id)
);
