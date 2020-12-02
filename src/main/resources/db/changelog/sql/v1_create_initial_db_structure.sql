--liquibase formatted sql

--changeset eric-rudat:v1.0
--comment: creates car table
create table if not exists car
(
    id          serial primary key not null,
    vin         varchar not null,
    make        varchar not null,
    model       varchar not null,
    color       varchar not null
);
--rollback drop table if exists car;