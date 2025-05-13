
create table if not exists wallet (
    id uuid default gen_random_uuid() unique not null ,
    money bigint check ( money >= 0 )
);

create table if not exists operation (
    id uuid default gen_random_uuid() unique not null ,
    wallet_id uuid references wallet(id) on delete set null on update cascade ,
    amount bigint check ( amount >= 0 ) not null ,
    operation_type varchar not null ,
    commited_at timestamp not null
);