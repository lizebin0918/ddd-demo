create sequence t_order_order_id_seq;

create table if not exists "order"
(
    order_id         bigint default nextval('ddd.t_order_order_id_seq'::regclass) not null
        constraint t_order_pk
            primary key,
    pay_money        numeric                                                      not null,
    user_id          bigint                                                       not null,
    status           integer                                                      not null,
    create_date_time timestamp                                                    not null,
    update_date_time timestamp                                                    not null
);

comment on column "order".order_id is '订单号';

comment on column "order".pay_money is '支付金额';

comment on column "order".user_id is '下单用户';

comment on column "order".status is '订单状态';

comment on column "order".create_date_time is '创建时间';

comment on column "order".update_date_time is '更新时间';

alter sequence t_order_order_id_seq owned by "order".order_id;

create table if not exists order_detail
(
    id               serial
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

comment on column order_detail.count is '数量';

comment on column order_detail.product_id is '商品id';

comment on column order_detail.status is '订单明细状态';

comment on column order_detail.product_code is '商品编码';

create table if not exists product
(
    id   bigserial
        constraint product_pk
            primary key,
    code text not null
);

comment on column product.code is '商品编码';

create table domain_event
(
    id     serial
        constraint domain_event_pk
            primary key,
    topic  varchar(32)       not null,
    tag    varchar(32),
    key    varchar(32),
    body   text,
    status integer default 0 not null
);