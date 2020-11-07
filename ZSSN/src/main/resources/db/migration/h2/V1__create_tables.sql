create sequence survivor_id_seq start with 1 increment by 1;

create table survivors (
	id bigint DEFAULT survivor_id_seq.nextval,
	name varchar(255) not null,
	birthdate timestamp,
	last_latitude float(9),
	last_longitude float(9),
	gender varchar(1),
	count_infected numeric(1),
	infected boolean default false,
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

create sequence resouces_survivor_id_seq start with 1 increment by 1;

CREATE TABLE public.resouces_survivor (
	id bigint DEFAULT resouces_survivor_id_seq.nextval NOT NULL,
	quantity int4 NOT NULL,
	resource_id int8 NULL,
	survivor_id int8 NULL,
	CONSTRAINT resouces_survivor_pkey PRIMARY KEY (id),
	CONSTRAINT fksei1p2ifllkdt8avg4v12pm86 FOREIGN KEY (resource_id) REFERENCES resources(id),
	CONSTRAINT resouces_survivor_fk FOREIGN KEY (survivor_id) REFERENCES survivors(id)
);