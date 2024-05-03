create table if not exists customer (
    id           bigserial primary key,
    created      bigint  not null default EXTRACT(EPOCH FROM now()) * 1000000,
    updated      bigint  not null default EXTRACT(EPOCH FROM now()) * 1000000,
    full_name    text    not null,
    email        text    not null,
    phone_number text,
    is_active    boolean not null default true
);
