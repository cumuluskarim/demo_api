create table book
(
   id integer auto_increment not null,
   type varchar(255) not null,
   price float not null,
   quantity_placed integer not null,
   quantity_filled integer default 0,
   status varchar(255) not null,
   order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

   primary key(id)
);