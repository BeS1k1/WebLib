create sequence hibernate_sequence start 1 increment 1;

create table book (
    id int8 not null,
    author varchar(255) not null,
    book_year int4 not null,
    quantity int4 not null,
    title varchar(255) not null,
    primary key (id)
);

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);

create table usr (
    id int8 not null,
    active boolean not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists user_role
    add constraint user_role_fk
    foreign key (user_id) references usr;