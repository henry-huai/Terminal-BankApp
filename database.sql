DROP TABLE IF EXISTS transactions cascade;
DROP TABLE IF EXISTS accounts cascade;
DROP TABLE IF EXISTS users cascade;


create table users(
	user_id serial primary key,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	pass_word varchar(50) not null,
	email varchar(100) unique not null
);


create table accounts(
	account_id serial primary key,
	user_id serial not null references users,
	balance Numeric(12,2) not null default 0
);

create table transactions(
	account_id serial references accounts,
	transaction_id serial primary key,
	transaction_date Date not null default current_date,
	amount_change Numeric(12,2) not null default 0

);
