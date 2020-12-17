---ESD project sql DDL--


create table placement
(
	id int auto_increment,
	organization varchar(20) not null,
	profile varchar(20) not null,
	description varchar(50) null,
	intake int not null,
	minimum_grade float null,
	constraint placement_pk
		primary key (id)
);

insert into placement values(20,'Google','Software Engineer','',3,3.4);
insert into placement values(default,'Microsoft','SE 2','',10,2.8);
insert into placement values(default,'Flipkart','UI Developer','',5,2.5);
insert into placement values(default,'Samsung','Hardware Engineer','',15,2.7);
insert into placement values(default,'Rubric','Software Developer','',2,3.6);
insert into placement values(default,'Amazon','SE','',15,3.0);


create table placement_filter
(
	id int auto_increment,
	placement_id int not null,
	specialization varchar(20),
	domain varchar(20),
	constraint placement_filter_pk
		primary key (id),
	constraint placement_filter_placement_id_fk
		foreign key (placement_id) references placement (id)
			on update cascade on delete cascade
);

insert into placement_filter values(default,20,'AI/ML','CSE');
insert into placement_filter values(default,21,'','CSE');
insert into placement_filter values(default,22,'NC','CSE');
insert into placement_filter values(default,23,'VLSI','ECE');
insert into placement_filter values(default,25,'','CSE');
insert into placement_filter values(default,24,'','');


create table students
(
	student_id int auto_increment,
	roll_number varchar(10) unique not null,
	first_name varchar(10) not null,
	last_name varchar(10) null,
	email varchar(20) unique not null,
	photograph_path varchar(100) null,
	cgpa float default 0.0 not null,
	total_credits int not null,
	graduation_year int null,
	domain varchar(20),
	specialization varchar(20),
	placement_id int null,
	constraint students_pk
		primary key (student_id),
	constraint students_placement_id_fk
		foreign key (placement_id) references placement (id)
);

insert into students values(default,'MT123123','Sokka','Watertribe','abcd@gmail.com','',3.0,15,2020,'CSE','TCSD',NULL);
insert into students values(default,'MT123456','Kakashi','Hatake','kaka@gmail.com','',3.6,12,2021,'ECE','',24);
insert into students values(default,'DS123123','Itachi','Uchiha','xyz@gmail.com','',2.9,16,2020,'CSE','AI/ML',NULL);

create table placement_student
(
	id int auto_increment,
	placement_id int not null,
	student_id int not null,
	cv_application blob null,
	about varchar(50) null,
	acceptance varchar(20) default 'PENDING' null,
	comments varchar(100) null,
	date date null,
	constraint placement_student_pk
		primary key (id),
	constraint placement_student_placement_id_fk
		foreign key (placement_id) references placement (id),
	constraint placement_student_students_student_id_fk
		foreign key (student_id) references students (student_id)
);


insert into placement_student values(default,22 , 2, default, "somethin", default, "wip", "2020-08-08");
insert into placement_student values(default,24 , 2, default, "", "REJECTED", "not good", "2019-05-05");
insert into placement_student values(default,21 , 1, default, "offshore", default, "average", "2020-10-08");