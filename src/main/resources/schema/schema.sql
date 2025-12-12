-- User Table
CREATE TABLE IF NOT EXISTS users
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL UNIQUE,
    password          VARCHAR(255),
    nick_name         VARCHAR(255),
    profile_image_url TEXT,
    social_id         VARCHAR(255),
    role              VARCHAR(50)  NOT NULL DEFAULT 'GUEST',
    created_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roasteries
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    logo_url    VARCHAR(500),
    website_url VARCHAR(500),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stores
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    FOREIGN KEY (roastery_id) REFERENCES roasteries (id),
    FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS visits
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    menu_id     BIGINT         NOT NULL,
    latitude    DECIMAL(10, 8) NOT NULL,
    longitude   DECIMAL(11, 8) NOT NULL,
    is_verified BOOLEAN        NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    visit_id   BIGINT    NOT NULL UNIQUE,
    user_id    BIGINT    NOT NULL,
    store_id   BIGINT    NOT NULL,
    menu_id    BIGINT    NOT NULL,
    content    TEXT,
    is_public  BOOLEAN   NOT NULL,
    image_url  TEXT,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (visit_id) REFERENCES visits (id),
    FOREIGN KEY (store_id) REFERENCES stores (id)
    -- FOREIGN KEY (menu_id) REFERENCES menus (id),
);

CREATE TABLE IF NOT EXISTS flavors
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    code        VARCHAR(50)  NOT NULL COMMENT '고유 코드 (FRUITY, BERRY 등)',
    parent_id   BIGINT COMMENT '상위 카테고리 ID',
    level       TINYINT      NOT NULL COMMENT '1:대분류, 2:중분류, 3:소분류',
    path        VARCHAR(255) NOT NULL COMMENT '계층 경로 (fruity/berry/blackberry)',
    name        VARCHAR(100) NOT NULL COMMENT '이름',
    description VARCHAR(500) COMMENT '설명',
    color_hex   VARCHAR(7) COMMENT 'SCA Wheel 색상',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS tasting_notes
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id  BIGINT NOT NULL,
    flavor_id  BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES reviews (id),
    FOREIGN KEY (flavor_id) REFERENCES flavors (id)
);

CREATE TABLE `cupping_notes`
(
    `id`                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    `review_id`                BIGINT                           NOT NULL,
    `roast_level`              ENUM ('LIGHT', 'MEDIUM', 'DARK') NULL,

    `fragrance_score`          DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',
    `aroma_score`              DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',
    `flavor_score`             DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',
    `aftertaste_score`         DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',
    `acidity_score`            DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',
    `sweetness_score`          DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',
    `mouthfeel_score`          DECIMAL(4, 2)                    NULL COMMENT '0.00-15.00',

    `total_score`              DECIMAL(5, 2) GENERATED ALWAYS AS (
        COALESCE(fragrance_score, 0) + COALESCE(aroma_score, 0) +
        COALESCE(flavor_score, 0) + COALESCE(aftertaste_score, 0) +
        COALESCE(acidity_score, 0) + COALESCE(sweetness_score, 0) +
        COALESCE(mouthfeel_score, 0)
        ) STORED COMMENT '총점: 0-105',

    `fragrance_aroma_detail`   TEXT                             NULL,
    `flavor_aftertaste_detail` TEXT                             NULL,
    `acidity_notes`            TEXT                             NULL,
    `sweetness_notes`          TEXT                             NULL,
    `mouthfeel_notes`          TEXT                             NULL,
    `overall_notes`            TEXT                             NULL,

    `created_at`               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`               TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`) ON DELETE CASCADE
)