create sequence survivor_id_seq start with 1 increment by 1;	

create table survivors (
	id bigint DEFAULT nextval('survivor_id_seq') not null,
	name varchar(255) not null,
	birthdate timestamp,
	last_latitude numeric(8,4),
	last_longitude numeric(8,4),
	gender varchar(1),
	count_infected numeric(1) default 0,
	infected boolean default false,
	created_at timestamp,
    updated_at timestamp,
	primary key (id)
);

create sequence resources_id_seq start with 1 increment by 1;

create table resources (
	id bigint DEFAULT nextval('resources_id_seq') not null,
	name varchar(255) not null,
	point numeric(4,2) not null,
	primary key (id)
);

create sequence resouces_survivor_id_seq start with 1 increment by 1;

CREATE TABLE public.resouces_survivor (
	id bigint DEFAULT nextval('resouces_survivor_id_seq') NOT NULL,
	quantity int4 NOT NULL,
	resource_id int8 NULL,
	survivor_id int8 NULL,
	CONSTRAINT resouces_survivor_pkey PRIMARY KEY (id),
	CONSTRAINT fksei1p2ifllkdt8avg4v12pm86 FOREIGN KEY (resource_id) REFERENCES resources(id),
	CONSTRAINT resouces_survivor_fk FOREIGN KEY (survivor_id) REFERENCES survivors(id)
);

