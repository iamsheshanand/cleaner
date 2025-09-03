create table customer
(
    customer_number int not null primary key,
    name varchar(255) null,
    windows int null
);

create table customer_booking
(
    booking_number  int  not null primary key,
    customer_number int  null,
    date            date null,
    constraint FK3x68mb6e8243l82akm42v4p3j
        foreign key (customer_number) references customer (customer_number)
);

