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


