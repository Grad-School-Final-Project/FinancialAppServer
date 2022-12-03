create table users
(
    username   varchar(255) not null
        primary key,
    first_name varchar(255),
    last_name  varchar(255)
);

create sequence hibernate_sequence;

create table account
(
    account_id          integer      not null
        primary key,
    account_institution varchar(255),
    nickname            varchar(255),
    account_type        varchar(255),
    user_id             varchar(255) not null
        constraint fkra7xoi9wtlcq07tmoxxe5jrh4
            references users,
    constraint uk121hm86i1n0wwkr1oq2xmn5qi
        unique (user_id, nickname)
);

create table category
(
    category_id                 integer      not null
        primary key,
    category_name               varchar(255),
    parent_category_category_id integer
        constraint fkhyf0d7atqv1xg87nqp0hl6p4f
            references category,
    user_id                     varchar(255) not null
        constraint fk7ffrpnxaflomhdh0qfk2jcndo
            references users,
    constraint ukt8pi2cjtplbos9g0vvnmdfovn
        unique (user_id, category_name)
);

create table budget
(
    budget_id      integer          not null
        primary key,
    nickname       varchar(255),
    monthly_amount double precision not null,
    category_id    integer          not null
        constraint fkrme3v2iww4j9qkxv4f93vv4mt
            references category,
    user_id        varchar(255)     not null
        constraint fk5hquwcop4uwka537brlrxifl3
            references users,
    constraint uksn4fodtexgge53s9jpt02tufg
        unique (user_id, nickname)
);

create table transaction
(
    transaction_id                integer not null
        primary key,
    amount                        numeric(19, 2),
    currency                      varchar(255),
    date                          date,
    description                   varchar(255),
    notes                         varchar(255),
    associated_account_account_id integer
        constraint fkcfhelspeqh7tvpkgttuh6gatn
            references account,
    category_category_id          integer
        constraint fkk7l5xnhkvjivdg3hs8o7dark6
            references category,
    user_username                 varchar(255)
        constraint fk9eioirj30jfff3mypbgwkwesm
            references users
);

create table stock
(
    ticker       varchar(255) not null
        primary key,
    company_name varchar(255)
);

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