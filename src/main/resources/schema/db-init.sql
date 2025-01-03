create database study_for_u;

use study_for_u;
create table users(
    id bigint primary key auto_increment,
    provider varchar(30),
    provider_id varchar(255),
    provider_nickname varchar(255),
    username varchar(100),
    role varchar(20),
    point integer,
    created_at datetime,
    updated_at datetime
);

create table posts(
    id bigint primary key auto_increment,
    user_id bigint,
    writer_name varchar(100),
    category_id integer,
    title varchar(255),
    body text,
    likes integer,
    status varchar(30),
    created_at datetime,
    updated_at datetime
);

create table category(

)