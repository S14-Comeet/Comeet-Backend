# 2025-12-15 #31
ALTER TABLE roasteries
    ADD COLUMN owner_id BIGINT NOT NULL;
ALTER TABLE roasteries
    ADD CONSTRAINT FOREIGN KEY (owner_id) REFERENCES users (id);

# 2025-12-17 #35
ALTER TABLE beans
    ADD COLUMN name VARCHAR(100) NOT NULL COMMENT '원두 이름' AFTER roastery_id
# 38
ALTER TABLE roasteries
    DROP FOREIGN KEY roasteries_ibfk_1;

ALTER TABLE roasteries
    DROP COLUMN owner_id;

#2025-12-19 #47
CREATE UNIQUE INDEX uniq_passport_user_year_month ON passports (user_id, year, month);

-- 2. visits 테이블에 컬럼 추가
ALTER TABLE visits ADD COLUMN store_id BIGINT;
ALTER TABLE visits ADD COLUMN passport_id BIGINT;

-- 3. visits 테이블 외래키 추가
ALTER TABLE visits ADD FOREIGN KEY (store_id) REFERENCES stores (id);
ALTER TABLE visits ADD FOREIGN KEY (passport_id) REFERENCES passports (id);

-- 4. visits 테이블 인덱스 추가
CREATE INDEX idx_visit_passport ON visits (passport_id);
CREATE INDEX idx_visit_store ON visits (store_id);
