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


