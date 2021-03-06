CREATE TABLE IF NOT EXISTS hosts
(
    id         BIGSERIAL PRIMARY KEY,
    hostname   VARCHAR(255) UNIQUE NOT NULL,
    is_enabled BOOLEAN             NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP           NOT NULL,
    updated_at TIMESTAMP
);

