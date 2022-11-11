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


