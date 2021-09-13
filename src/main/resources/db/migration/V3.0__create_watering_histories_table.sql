CREATE TABLE watering_histories(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id  BIGINT NOT NULL,
    amount     INT NOT NULL,
    timestamp  DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    FOREIGN KEY (device_id) REFERENCES devices (id)
);
