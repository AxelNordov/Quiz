alter table quiz
    drop column quiz_details_id;

delete
from quiz_details
where quiz_details.description is null
  and quiz_details.url_1 is null
  and quiz_details.url_2 is null
  and quiz_details.origin_number is null;

alter table quiz_details
    add quiz_id int8;

update quiz_details
set quiz_id = id
where true;

alter table quiz_details
    drop column id;

alter table quiz_details
    add primary key (quiz_id);

alter table quiz_details
    alter column quiz_id set not null;
