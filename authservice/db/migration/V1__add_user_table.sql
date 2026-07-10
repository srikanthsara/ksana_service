create table users(

user_id serial primary key,

username varchar(50),

password varchar(100),

email varchar(100),

role varchar(30)

);

insert into users
(
username,
password,
email,
role
)
values
(
'srikanth',
'password',
'srikanth@gmail.com',
'CUSTOMER'
);