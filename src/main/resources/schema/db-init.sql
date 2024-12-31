create database study_for_u;

use study_for_u;
create table users(
    id bigint primary key auto_increment,
    provider varchar(30),
    provider_id varchar(255),
    provider_nickname varchar(255),
    username varchar(100),
    role varchar(20)
);