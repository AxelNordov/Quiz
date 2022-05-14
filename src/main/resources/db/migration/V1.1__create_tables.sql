create table answer
(
    id           int8 generated by default as identity,
    body         varchar(255) not null,
    order_number int2,
    quiz_id      int8         not null,
    primary key (id)
);

create table author
(
    id    int8 generated by default as identity,
    title varchar(255) not null,
    link  varchar(255),
    primary key (id)
);

create table category
(
    id    int8 generated by default as identity,
    title varchar(255) not null,
    primary key (id)
);

create table quiz
(
    id              int8 generated by default as identity,
    question        varchar(255) not null,
    right_answer    int2,
    one_answer      boolean,
    category_id     int8         not null,
    author_id       int8,
    topic_id        int8,
    quiz_details_id int8,
    primary key (id)
);

create table quiz_details
(
    id            int8 generated by default as identity,
    description   text,
    link_1        varchar(255),
    link_2        varchar(255),
    origin_number int4,
    primary key (id)
);

create table topic
(
    id    int8 generated by default as identity,
    title varchar(255) not null,
    primary key (id)
);

create table users
(
    id          int8 generated by default as identity,
    state_name  varchar(255),
    category_id int8,
    primary key (id)
);

alter table if exists answer
    add constraint FK_answer_quiz foreign key (quiz_id) references quiz;

alter table if exists quiz
    add constraint FK_quiz_author foreign key (author_id) references author;

alter table if exists quiz
    add constraint FK_quiz_category foreign key (category_id) references category;

alter table if exists quiz
    add constraint FK_quiz_quiz_details foreign key (quiz_details_id) references quiz_details;

alter table if exists quiz
    add constraint FK_quiz_topic foreign key (topic_id) references topic;

alter table if exists users
    add constraint FK_users_category foreign key (category_id) references category;