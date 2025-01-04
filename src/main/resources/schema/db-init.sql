create database study_for_u;

use study_for_u;
create table users(
    id bigint primary key auto_increment,
    provider varchar(30) not null,
    provider_id varchar(255) not null,
    provider_nickname varchar(255),
    username varchar(100),
    role varchar(20) not null,
    point integer default 0 not null,
    created_at datetime not null,
    updated_at datetime not null
);

create table posts(
    id bigint primary key auto_increment,
    user_id bigint not null,
    writer_name varchar(100),
    category_id integer,
    title varchar(255),
    body text,
    likes integer,
    status varchar(30),
    created_at datetime not null,
    updated_at datetime not null
);

create table categories(
    id bigint primary key auto_increment,
    parent_id bigint,
    level integer not null,
    name varchar(100),
    created_at datetime not null,
    updated_at datetime not null,

    foreign key (parent_id) references categories(id)
)