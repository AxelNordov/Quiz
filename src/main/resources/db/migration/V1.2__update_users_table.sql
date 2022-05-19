alter table users
    add author_id int8;

alter table if exists users
    add constraint fk_users_author foreign key (author_id) references author;


alter table author
    add category_id int8;

alter table if exists author
    add constraint fk_author_category foreign key (category_id) references category;

update author
set category_id = s.category_id
from (select distinct category_id, author_id from quiz q) as s
where author.id = s.author_id;

alter table quiz
    drop constraint fk_quiz_category;

alter table quiz
    drop column category_id;


alter table quiz
    rename column one_answer to is_one_answer;

alter table author
    rename column link to url;

alter table quiz_details
    rename column link_1 to url_1;

alter table quiz_details
    rename column link_2 to url_2;
