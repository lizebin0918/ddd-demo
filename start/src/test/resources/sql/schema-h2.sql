create table "order"
(
    order_id          bigint         not null,
    pay_money         numeric           not null,
    user_id           bigint            not null,
    status            integer           not null,
    create_date_time  timestamp   default CURRENT_TIMESTAMP      not null,
    update_date_time  timestamp    default CURRENT_TIMESTAMP     not null,
    "version"         integer default 1 not null,
    shipped_date_time timestamp
);