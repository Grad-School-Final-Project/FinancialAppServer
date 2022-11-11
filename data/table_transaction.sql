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


