# Занятие от 02.11.2024

SQL DDL:

```create table if not exists public.car
(
    id    integer generated always as identity
        primary key,
    title varchar(255)
);

create table if not exists public.penalty
(
    id      serial
        primary key,
    car_id  integer not null
        references public.car,
    amount  integer,
    daytime date
);
```