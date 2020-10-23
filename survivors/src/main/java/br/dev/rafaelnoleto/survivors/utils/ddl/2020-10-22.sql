/**
 * Author:  rafaelnoleto
 * Created: 22/10/2020
 */

create table if not exists survivor (
	id serial not null,
	name varchar(100) not null,
	age int2,
	gender int2 default 0,
	latitude float8,
	longitude float8,
	constraint pk_survivor primary key (id)
);

create table if not exists survivor_notification (
	id serial not null,
	survivor_id int4 not null,
	survivor_notifier_id int4 not null,
	constraint pk_suvivor_notification primary key (id),
	constraint fk_survivor_id foreign key (survivor_id) references survivor(id),
	constraint fk_survivor_notifier_id foreign key (survivor_notifier_id) references survivor(id)
);

create table if not exists item (
	id serial not null,
	description varchar(100) not null,
	points int2,
	constraint pk_item primary key (id)
);

create table if not exists survivor_item (
	id serial not null,
	survivor_id int4 not null,
	item_id int4 not null,
	constraint pk_survivor_item primary key (id),
	constraint fk_survivor_id foreign key (survivor_id) references survivor(id),
	constraint fk_item_id foreign key (item_id) references item(id)
);
