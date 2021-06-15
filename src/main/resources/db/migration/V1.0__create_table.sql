CREATE TABLE devices (
    id         BINARY(16) UNIQUE PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE member_device_maps (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    device_id  BINARY(16) UNIQUE NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    FOREIGN KEY (device_id) REFERENCES devices (id)
);

CREATE TABLE watering_settings (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id     BINARY(16) UNIQUE NOT NULL,
    auto_watering BOOL NOT NULL,
    `interval`    INT,
    created_at    DATETIME NOT NULL,
    updated_at    DATETIME NOT NULL,

    FOREIGN KEY (device_id) REFERENCES devices (id)
);
