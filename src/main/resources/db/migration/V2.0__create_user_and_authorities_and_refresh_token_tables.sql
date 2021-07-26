CREATE TABLE users (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) UNIQUE NOT NULL,
    password     VARCHAR(255) NOT NULL,
    created_at   DATETIME NOT NULL,
    updated_at   DATETIME NOT NULL
);

CREATE TABLE authorities (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE user_discord_map (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNIQUE NOT NULL,
    discord_id BIGINT UNIQUE NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_authority_map (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    created_at   DATETIME NOT NULL,
    updated_at   DATETIME NOT NULL,

    FOREIGN KEY (user_Id) REFERENCES users (id),
    FOREIGN KEY (authority_id) REFERENCES authorities (id)
);

CREATE TABLE refresh_tokens (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNIQUE NOT NULL,
    token      BINARY(16) UNIQUE NOT NULL,
    expires_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id)
)