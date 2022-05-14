CREATE TABLE IF NOT EXISTS ips
(
    id         BIGSERIAL PRIMARY KEY,
    address    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    deleted_at TIMESTAMP,
    host_id    BIGINT REFERENCES hosts (id)
);