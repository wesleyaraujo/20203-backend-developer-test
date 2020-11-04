create sequence user_id_seq start with 10 increment by 50;

create table users (
    id bigint DEFAULT nextval('user_id_seq') not null,
    email varchar(255) not null CONSTRAINT user_email_unique UNIQUE,
    name varchar(255) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);

create sequence survivor_id_seq start with 1 increment by 1;

create table survivors(
	id bigint DEFAULT nextval('survivor_id_seq') not null,
	name varchar(255) not null,
	birthdate timestamp,
	last_latitude float(9,6),
	last_longitude float(9,6),
	gender varchar(1),
	created_at timestamp,
    updated_at timestamp,
	primary key (id)
);