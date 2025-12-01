-- User Table
CREATE TABLE IF NOT EXISTS user
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL UNIQUE,
    password          VARCHAR(255),
    nick_name         VARCHAR(255),
    profile_image_url TEXT,
    social_id         VARCHAR(255),
    role              VARCHAR(50)  NOT NULL DEFAULT 'USER',
    created_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roasteries
(
    roastery_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    logo_url        VARCHAR(500),
    website_url     VARCHAR(500),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stores
(
    store_id       VARCHAR(255) PRIMARY KEY,
    roastery_id    BIGINT         NOT NULL,
    owner_id       BIGINT,
    name           VARCHAR(100)   NOT NULL,
    description    TEXT,
    address        VARCHAR(255)   NOT NULL,
    latitude       DECIMAL(10, 8) NOT NULL,
    longitude      DECIMAL(11, 8) NOT NULL,
    phone_number   VARCHAR(20),
    category       VARCHAR(50),
    thumbnail_url  VARCHAR(500),
    open_time      TIME,
    close_time     TIME,
    average_rating DECIMAL(3, 2)  NOT NULL DEFAULT 0,
    review_count   INT            NOT NULL DEFAULT 0,
    visit_count    INT            NOT NULL DEFAULT 0,
    is_closed      BOOLEAN        NOT NULL DEFAULT FALSE,
    deleted_at     TIMESTAMP,
    created_at     TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (roastery_id) REFERENCES roasteries (roastery_id),
    FOREIGN KEY (owner_id) REFERENCES user (id)
);