CREATE TABLE IF NOT EXISTS domains
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL,
    deleted_at TIMESTAMP
);

