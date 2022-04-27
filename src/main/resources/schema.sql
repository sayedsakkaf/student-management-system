drop table if exists course CASCADE;

drop table if exists student CASCADE;

drop table if exists student_course CASCADE;

create table course (id integer not null auto_increment, course_description varchar(255) not null, course_title varchar(32) not null, primary key (id));

create table student (id integer not null auto_increment, first_name varchar(64), surname varchar(64), primary key (id));

create table student_course (student_id integer not null, course_id integer not null);

alter table student_course add constraint FKejrkh4gv8iqgmspsanaji90ws foreign key (course_id) references course;

alter table student_course add constraint FKq7yw2wg9wlt2cnj480hcdn6dq foreign key (student_id) references student;


