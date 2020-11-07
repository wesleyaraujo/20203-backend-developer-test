create sequence user_id_seq start with 1 increment by 1;

create table users (
    id bigint default user_id_seq.nextval,
    email varchar(255) not null,
    name varchar(255) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    UNIQUE KEY user_email_unique (email)
);

create sequence survivor_id_seq start with 1 increment by 1;

create table survivors (
	id bigint DEFAULT survivor_id_seq.nextval,
	name varchar(255) not null,
	birthdate timestamp,
	last_latitude float(9),
	last_longitude float(9),
	gender varchar(1),
	count_infected numeric(1),
	infected numeric(1),
	created_at timestamp,
    updated_at timestamp,
	primary key (id)
);

create sequence resources_id_seq start with 1 increment by 1;

create table resources (
	id bigint DEFAULT resources_id_seq.nextval,
	name varchar(255) not null,
	point float(2) not null,
	primary key (id)
);