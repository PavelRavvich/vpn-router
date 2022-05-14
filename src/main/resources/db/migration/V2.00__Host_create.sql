CREATE TABLE IF NOT EXISTS hosts
(
    id         BIGSERIAL PRIMARY KEY,
    host       VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL,
    deleted_at TIMESTAMP,
    domain_id  BIGINT REFERENCES domains (id)
);