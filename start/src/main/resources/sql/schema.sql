create table if not exists "order"
(
    order_id         bigint                              not null
        constraint "PRIMARY_order"
            primary key,
    order_status     integer                             not null,
    currency         text,
    exchange_rate    numeric,
    total_should_pay numeric,
    total_actual_pay numeric,
    email            text,
    phone_number     text,
    first_name       text,
    last_name        text,
    address_line1    text,
    address_line2    text,
    country          text,
    extension        jsonb,
    version          int                                 not null,
    add_time         timestamp default CURRENT_TIMESTAMP not null,
    update_time      timestamp default CURRENT_TIMESTAMP not null
);


create table if not exists order_detail
(
    id           bigint
        constraint "PRIMARY_order_detail"
            primary key,
    order_id     bigint                              not null,
    sku_id       bigint,
    order_status integer                             not null,
    price        numeric,
    locked       boolean null,
    add_time     timestamp default CURRENT_TIMESTAMP not null,
    update_time  timestamp default CURRENT_TIMESTAMP not null
);

create sequence if not exists order_detail_id_seq;

alter table order_detail
    alter column id set default nextval('order_detail_id_seq'::regclass);

create table if not exists domain_event
(
    id          bigint                              not null
        primary key,
    topic       text                                not null,
    tag         text                                not null,
    biz_id      text                                not null,
    key         text                                not null,
    content     jsonb                               not null,
    sent        boolean   default false             not null,
    msg_id      text null,
    add_time    timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null,
    constraint uk_topic_tag_biz_id
        unique (topic, tag, biz_id)
);

create index if not exists domain_event_sharding_key_index on domain_event (key);

create sequence if not exists domain_event_id_seq;

alter table domain_event
    alter column id set default nextval('domain_event_id_seq'::regclass);

create table if not exists operation_log
(
    id          bigint                              not null
    primary key,
    biz_id         bigint,
    log_table_name text,
    biz_type       text,
    old_status     text,
    new_status     text,
    remark         text,
    add_time       timestamp(0) default now(),
    diff           text,
    uid            bigint,
    source         text
    );

create sequence if not exists operation_log_id_seq;

alter table operation_log
    alter column id set default nextval('operation_log_id_seq'::regclass);

create table if not exists cart
(
    user_id   bigint not null primary key,
    version int not null,
    add_time         timestamp not null,
    update_time      timestamp not null
);

create table if not exists cart_detail
(
    id               bigserial
        constraint cart_detail_pk
            primary key,
    user_id   bigint                   not null,
    sku_id         bigint                     not null,
    count            integer                  not null,
    delete_timestamp bigint  default 0        not null,
    delete_flag      boolean default false    not null,
    add_time         timestamp not null,
    update_time      timestamp not null,
    constraint cart_detail_uk
        unique (user_id, sku_id, delete_flag, delete_timestamp)
);