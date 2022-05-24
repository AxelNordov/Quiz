create table user_author
(
    user_id   int8,
    author_id int8,
    primary key (user_id, author_id)
);

alter table if exists user_author
    add constraint user_author_author_fk foreign key (author_id) references author;

alter table if exists user_author
    add constraint user_author_user_fk foreign key (user_id) references users;

insert into user_author (user_id, author_id)
select id, author_id
from users;

alter table users
    drop column author_id;
