create table users
(
    id         bigserial primary key,
    username   varchar(50) not null unique,
    password   varchar(80) not null,
    status     varchar(10) not null default 'CREATED',
    created_at timestamp            default current_timestamp,
    updated_at timestamp            default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

create table currencies
(
    id         bigserial primary key,
    name       varchar(50)    not null unique,
    rate       numeric(10, 2) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table accounts
(
    id          bigserial primary key,
    amount      numeric(10, 2) not null,
    user_id     bigint         not null references users (id),
    status      varchar(10)    not null default 'ACTIVE',
    currency_id bigint         not null references currencies (id),
    created_at  timestamp               default current_timestamp,
    updated_at  timestamp               default current_timestamp
);

create table operations
(
    id                  bigserial primary key,
    amount              numeric(10, 2) not null,
    account_id          bigint         not null references accounts (id),
    transfer_account_id bigint references accounts (id),
    type                varchar(10)    not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);
