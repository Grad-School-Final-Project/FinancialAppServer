create table brokerage_transaction
(
    id                    integer          not null
        primary key,
    price_per_unit        double precision not null,
    units_purchased       double precision not null,
    accountdto_account_id integer
        constraint fkku3x06tq779f4a4wcoa7p26x7
            references account,
    stockdto_ticker       varchar(255)
        constraint fkinm3fcd5lqvif7yati7ujrgkq
            references stock
);


