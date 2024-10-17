--liquibase formatted sql
--changeset A.Aksenau:PUPPET-899.1 logicalFilePath:1.0.0/create-extension-uuid-ossp.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--rollback drop extension uuid-ossp;