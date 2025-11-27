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