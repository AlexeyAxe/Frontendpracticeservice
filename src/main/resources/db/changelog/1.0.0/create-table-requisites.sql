--liquibase formatted sql
--changeset A.Aksenau:PUPPET-900.1 logicalFilePath:1.0.0/create-table-requisites.sql

CREATE TABLE IF NOT EXISTS requisites
(
    id                    UUID DEFAULT uuid_generate_v4(),
    current_account       VARCHAR(20) NOT NULL,
    bic                   VARCHAR(9)  NOT NULL,
    correspondent_account VARCHAR(20) NOT NULL,
    inn                   VARCHAR(12) NOT NULL,
    kpp                   VARCHAR(9) NOT NULL,
    kbk                   VARCHAR(20) NOT NULL,

    CONSTRAINT pk_requisites_id
        PRIMARY KEY (id)

);

--rollback drop table requisites;