create table if not exists "order"
(
    order_id          bigserial         not null
        constraint t_order_pk
            primary key,
    pay_money         numeric           not null,
    user_id           bigint            not null,
    status            integer           not null,
    create_date_time  timestamp         not null,
    update_date_time  timestamp         not null,
    "version"         integer default 1 not null,
    shipped_date_time timestamp with time zone
);
create table if not exists order_detail
(
    id               bigserial
        constraint order_detail_pk
            primary key,
    order_id         integer not null,
    count            integer not null,
    product_id       bigint,
    create_date_time timestamp,
    update_date_time timestamp,
    status           integer,
    product_code     text
);

create table if not exists product
(
    id   bigserial
        constraint product_pk
            primary key,
    code text not null
);

create table if not exists domain_event
(
    id     serial
        constraint domain_event_pk
            primary key,
    topic  varchar(32)       not null,
    "tag"  varchar(32),
    "key"  varchar(32),
    body   text,
    status integer default 0 not null
);
