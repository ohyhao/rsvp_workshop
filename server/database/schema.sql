-- create a database
drop schema if exists rsvp;
create database rsvp;

use rsvp;

create table rsvp (
    rsvp_id varchar(8) not null,
    name varchar(128) not null,
    email varchar(128) unique not null,
    phone varchar(16) not null,
    c_date date not null,
    comments text,
    primary key (rsvp_id)
);