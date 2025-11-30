-- Test Data for Stores
-- 서울 내부의 다양한 위치에 분산된 카페/로스터리 더미 데이터

-- Roasteries 테이블에 테스트 데이터 추가
INSERT INTO roasteries (roastery_id, name, logo_url, website_url) VALUES
(1, '커핏 로스터즈', 'https://example.com/logos/comeet.jpg', 'https://comeet.com'),
(2, '블루보틀 코리아', 'https://example.com/logos/bluebottle.jpg', 'https://bluebottlecoffee.kr'),
(3, '프릳츠 커피', 'https://example.com/logos/fritz.jpg', 'https://fritzcoffee.co.kr'),
(4, '앤트러사이트', 'https://example.com/logos/anthracite.jpg', 'https://anthracitecoffee.com'),
(5, '테라로사', 'https://example.com/logos/terarosa.jpg', 'https://terarosa.com');

-- Stores 테이블에 테스트 데이터 추가 (서울 내 다양한 위치)

-- 1. 강남역 근처 (37.4979, 127.0276)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-gangnam-001', 1, NULL, '커핏 강남점', '강남역 5분 거리의 스페셜티 커피 전문점입니다. 다양한 원두와 핸드드립 커피를 제공합니다.', '서울특별시 강남구 강남대로 지하 396', 37.49780000, 127.02760000, '02-1234-5678', 'CAFE', 'https://example.com/stores/gangnam1.jpg', '08:00:00', '22:00:00', 4.50, 127, 523, false),
('store-gangnam-002', 2, NULL, '블루보틀 강남점', '블루보틀의 시그니처 메뉴를 만나보세요.', '서울특별시 강남구 테헤란로 152', 37.49950000, 127.02880000, '02-2345-6789', 'CAFE', 'https://example.com/stores/gangnam2.jpg', '07:30:00', '20:00:00', 4.80, 234, 891, false);

-- 2. 홍대입구역 근처 (37.5563, 126.9234)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-hongdae-001', 3, NULL, '프릳츠 홍대점', '홍대의 감성을 담은 로스터리 카페입니다.', '서울특별시 마포구 와우산로29길 8', 37.55630000, 126.92340000, '02-3456-7890', 'ROASTERY', 'https://example.com/stores/hongdae1.jpg', '09:00:00', '23:00:00', 4.30, 98, 412, false),
('store-hongdae-002', 1, NULL, '커핏 홍대점', '젊은 감성의 커피 문화를 선도합니다.', '서울특별시 마포구 양화로 160', 37.55800000, 126.92100000, '02-4567-8901', 'CAFE', 'https://example.com/stores/hongdae2.jpg', '10:00:00', '22:00:00', 4.20, 156, 678, true);

-- 3. 성수동 (37.5446, 127.0557)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-seongsu-001', 4, NULL, '앤트러사이트 성수점', '성수동의 핫플레이스! 넓은 공간과 좋은 커피', '서울특별시 성동구 연무장길 7', 37.54460000, 127.05570000, '02-5678-9012', 'CAFE', 'https://example.com/stores/seongsu1.jpg', '08:00:00', '21:00:00', 4.60, 189, 734, false),
('store-seongsu-002', 3, NULL, '프릳츠 성수점', '성수동 카페거리의 대표 로스터리', '서울특별시 성동구 아차산로 17길 49', 37.54520000, 127.05680000, '02-6789-0123', 'ROASTERY', 'https://example.com/stores/seongsu2.jpg', '09:00:00', '22:00:00', 4.70, 203, 845, false);

-- 4. 종로 (37.5701, 126.9911)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-jongno-001', 5, NULL, '테라로사 종로점', '전통과 현대가 만나는 스페셜티 커피', '서울특별시 종로구 삼일대로 428', 37.57010000, 126.99110000, '02-7890-1234', 'CAFE', 'https://example.com/stores/jongno1.jpg', '07:00:00', '21:00:00', 4.40, 145, 589, false);

-- 5. 이태원 (37.5347, 126.9946)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-itaewon-001', 2, NULL, '블루보틀 이태원점', '이태원의 글로벌한 분위기 속 프리미엄 커피', '서울특별시 용산구 이태원로 223', 37.53470000, 126.99460000, '02-8901-2345', 'CAFE', 'https://example.com/stores/itaewon1.jpg', '08:00:00', '20:00:00', 4.75, 267, 1023, false),
('store-itaewon-002', 1, NULL, '커핏 이태원점', '다국적 문화가 어우러진 특별한 커피 공간', '서울특별시 용산구 녹사평대로 164', 37.53350000, 126.99280000, '02-9012-3456', 'CAFE', 'https://example.com/stores/itaewon2.jpg', '09:00:00', '23:00:00', 4.10, 87, 321, true);

-- 6. 신촌 (37.5559, 126.9368)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-sinchon-001', 4, NULL, '앤트러사이트 신촌점', '대학가 감성의 넓고 쾌적한 카페', '서울특별시 서대문구 신촌로 83', 37.55590000, 126.93680000, '02-0123-4567', 'CAFE', 'https://example.com/stores/sinchon1.jpg', '08:00:00', '23:00:00', 4.35, 178, 692, false);

-- 7. 잠실 (37.5133, 127.1000)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-jamsil-001', 5, NULL, '테라로사 잠실점', '롯데월드타워 근처 프리미엄 커피 전문점', '서울특별시 송파구 올림픽로 300', 37.51330000, 127.10000000, '02-1234-0987', 'CAFE', 'https://example.com/stores/jamsil1.jpg', '10:00:00', '22:00:00', 4.55, 211, 867, false);

-- 8. 명동 (37.5636, 126.9825)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-myeongdong-001', 3, NULL, '프릳츠 명동점', '명동 쇼핑의 피로를 풀 수 있는 힐링 카페', '서울특별시 중구 명동길 74', 37.56360000, 126.98250000, '02-2345-0987', 'CAFE', 'https://example.com/stores/myeongdong1.jpg', '09:00:00', '21:00:00', 4.25, 134, 556, true);

-- 9. 여의도 (37.5219, 126.9245)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-yeouido-001', 2, NULL, '블루보틀 여의도점', '직장인들의 휴식처, IFC몰 근처', '서울특별시 영등포구 여의대로 108', 37.52190000, 126.92450000, '02-3456-0987', 'CAFE', 'https://example.com/stores/yeouido1.jpg', '07:00:00', '20:00:00', 4.65, 298, 1234, false),
('store-yeouido-002', 1, NULL, '커핏 여의도점', '바쁜 업무 중 찾는 프리미엄 커피', '서울특별시 영등포구 의사당대로 83', 37.52340000, 126.92560000, '02-4567-0987', 'CAFE', 'https://example.com/stores/yeouido2.jpg', '07:30:00', '21:00:00', 4.45, 187, 723, false);

-- 10. 건대입구 (37.5403, 127.0695)
INSERT INTO stores (store_id, roastery_id, owner_id, name, description, address, latitude, longitude, phone_number, category, thumbnail_url, open_time, close_time, average_rating, review_count, visit_count, is_closed) VALUES
('store-konkuk-001', 4, NULL, '앤트러사이트 건대점', '젊음의 거리 건대에서 만나는 감성 카페', '서울특별시 광진구 아차산로 243', 37.54030000, 127.06950000, '02-5678-0987', 'CAFE', 'https://example.com/stores/konkuk1.jpg', '09:00:00', '23:00:00', 4.20, 165, 634, false);
