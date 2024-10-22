--liquibase formatted sql
--changeset A.Aksenau:PUPPET-899.1 logicalFilePath:1.0.0/create-table-users.sql

CREATE TABLE IF NOT EXISTS users
(
    id              UUID DEFAULT uuid_generate_v4(),
    first_name      VARCHAR(30) NOT NULL,
    last_name       VARCHAR(30) NOT NULL,
    birthday        DATE        NOT NULL,
    inn             VARCHAR(12) NOT NULL,
    snils           VARCHAR(11) NOT NULL,
    login           VARCHAR(30) NOT NULL,
    password        VARCHAR(30) NOT NULL,
    passport_number VARCHAR(10) NOT NULL,

    CONSTRAINT pk_users_id
        PRIMARY KEY (id),
    CONSTRAINT uq_users_login
        UNIQUE (login)

);

--rollback drop table users;

--changeset A.Aksenau:PUPPET-900.1 logicalFilePath:1.0.0/create-table-users.sql

ALTER TABLE users
    DROP COLUMN inn;

ALTER TABLE users
    ADD COLUMN requisites_id UUID;

ALTER TABLE users
    ADD CONSTRAINT fk_requisites_requisites_id
FOREIGN KEY (requisites_id)
REFERENCES requisites (id);

--rollback alter table users add column inn varchar(12) not null;
--rollback alter table users drop constraint fk_requisites_requisites_id;
--rollback alter table users drop requisites_id;