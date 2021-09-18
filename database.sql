DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS account;

create table account(
	account_id serial not null,
	transaction_id serial primary key,
	tranction_date Date not null default current_date,
	amount_change Numeric(12,2) not null default 0
);

create table users(
	user_id serial primary key,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	pass_word varchar(50) not null,
	email varchar(100) unique not null,
	amount_change Numeric(12,2) not null default 0
);
