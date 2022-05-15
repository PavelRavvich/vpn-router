CREATE TABLE IF NOT EXISTS routes
(
    id         BIGSERIAL PRIMARY KEY,
    address    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    host_id    BIGINT REFERENCES hosts (id)
);