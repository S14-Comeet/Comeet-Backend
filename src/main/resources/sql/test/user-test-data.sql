-- 1. 관리자 (MANAGER) 추가
INSERT INTO users (id, name, email, password, nick_name, role, social_id)
VALUES (2,
        '관리자',
        'manager@example.com',
        '$2a$10$DUMMYPASSWORDHASHVALUE...',
        '강남점장',
        'MANAGER',
        'naver-test1');

-- 2. 관리자 (MANAGER) 추가
INSERT INTO users (id, name, email, password, nick_name, role, social_id)
VALUES (3,
        '관리자',
        'manager2@example.com',
        '$2a$10$DUMMYPASSWORDHASHVALUE...',
        '수석로스터',
        'MANAGER',
        'naver-test2');

-- 3. 관리자 테스트
UPDATE users
set role = 'MANAGER'
where id = 1;
