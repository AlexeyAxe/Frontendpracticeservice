--liquibase formatted sql
--changeset A.Aksenau:PUPPET-899.1 logicalFilePath:1.0.0/create-table-user-roles.sql

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id    UUID        NOT NULL,
    roles       VARCHAR(30) NOT NULL,

    CONSTRAINT pk_user_id_roles
        PRIMARY KEY (user_id,roles),

    CONSTRAINT fk_user_roles_user_id
        FOREIGN KEY (user_id)
            REFERENCES users(id),

    CONSTRAINT ch_user_roles_roles
        CHECK ( roles IN ('ADMIN','USER') )

);

--rollback drop table user-roles;