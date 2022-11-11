create table stock_data
(
    id           integer          not null
        primary key,
    instant      timestamp,
    price        double precision not null,
    stock_ticker varchar(255)     not null
        constraint fkm7tt2xlq9bek0k2sgeetp72j
            references stock,
    constraint uktpi7uy8u9owx9srnskgwc6b9p
        unique (stock_ticker, instant)
);


