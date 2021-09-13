CREATE TABLE devices (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial     BINARY(16) UNIQUE NOT NULL,
    user_id    BIGINT NOT NULL,
    name       VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE settings (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id    BIGINT UNIQUE NOT NULL,
    water_amount INT,
    created_at   DATETIME NOT NULL,
    updated_at   DATETIME NOT NULL,

    FOREIGN KEY (device_id) REFERENCES devices (id)
);

CREATE TABLE automation_settings (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id  BIGINT UNIQUE NOT NULL,
    enabled    BOOL NOT NULL,
    `interval` INT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    FOREIGN KEY (device_id) REFERENCES devices (id)
);
