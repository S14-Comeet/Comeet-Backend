-- 1. 매장 관리자 (STORE_MANAGER) 추가
INSERT INTO users (id, name, email, password, nick_name, role, social_id)
VALUES (2,
        '매장관리자',
        'store_admin@example.com',
        '$2a$10$DUMMYPASSWORDHASHVALUE...',
        '강남점장',
        'STORE_MANAGER',
        'naver-test1');

-- 2. 로스터리 관리자 (ROASTERY_MANAGER) 추가
INSERT INTO users (id, name, email, password, nick_name, role, social_id)
VALUES (3,
        '로스터리관리자',
        'roastery_admin@example.com',
        '$2a$10$DUMMYPASSWORDHASHVALUE...',
        '수석로스터',
        'ROASTERY_MANAGER',
        'naver-test2');

-- 3. 가맹점 관리자 테스트
UPDATE users
set role = 'STORE_MANAGER'
where id = 1;

-- 4. 로스터리 관리자 테스트
UPDATE users
set role = 'ROASTERY_MANAGER'
where id = 1;
