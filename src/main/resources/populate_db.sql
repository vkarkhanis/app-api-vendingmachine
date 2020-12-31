drop table if exists product;

create table product (
    id int auto_increment not null,
    price bigint not null,
    name varchar(50) not null,
    primary key(id)
);

insert into product (price, name) values (30, 'Coca-Cola');
insert into product (price, name) values (30, 'Fanta');
insert into product (price, name) values (30, 'Sprite');
insert into product (price, name) values (20, 'Cadbury');
insert into product (price, name) values (15, 'Nestle');
insert into product (price, name) values (15, 'Kit-Kat');
insert into product (price, name) values (10, 'Bourbon');
insert into product (price, name) values (10, 'Oreo');
insert into product (price, name) values (10, 'Cream Treat');
insert into product (price, name) values (10, 'Lays');
insert into product (price, name) values (10, 'Balaji');

drop table if exists product_inventory;

create table product_inventory (
    product_id int not null,
    count int not null,
    primary key(product_id)
);

insert into product_inventory (product_id, count) values (1, 100);
insert into product_inventory (product_id, count) values (2, 100);
insert into product_inventory (product_id, count) values (3, 100);
insert into product_inventory (product_id, count) values (4, 100);
insert into product_inventory (product_id, count) values (5, 100);
insert into product_inventory (product_id, count) values (6, 100);
insert into product_inventory (product_id, count) values (7, 100);
insert into product_inventory (product_id, count) values (8, 100);
insert into product_inventory (product_id, count) values (9, 100);
insert into product_inventory (product_id, count) values (10, 100);
insert into product_inventory (product_id, count) values (11, 100);