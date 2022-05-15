CREATE TABLE IF NOT EXISTS routes
(
    id         BIGSERIAL PRIMARY KEY,
    address    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    domain_id  BIGINT REFERENCES domains (id)
);