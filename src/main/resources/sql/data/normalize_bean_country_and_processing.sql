-- 1. 르완다 (Rwanda): 루완다, 루안다 -> 르완다
UPDATE beans
SET country = '르완다'
WHERE country IN ('루완다', '루안다');

-- 2. 케냐 (Kenya): 켄야 -> 케냐
UPDATE beans
SET country = '케냐'
WHERE country = '켄야';

-- 3. 에콰도르 (Ecuador): 에쿠아도르 -> 에콰도르
UPDATE beans
SET country = '에콰도르'
WHERE country = '에쿠아도르';

-- 4. 엘살바도르 (El Salvador): 띄어쓰기 및 오타 수정
UPDATE beans
SET country = '엘살바도르'
WHERE country IN ('엘 사바도르', '엘사바도르');

-- 5. 페루 (Peru): 퍼루 -> 페루
UPDATE beans
SET country = '페루'
WHERE country = '퍼루';

-- 6. 아이티 (Haiti): 하이티 -> 아이티
UPDATE beans
SET country = '아이티'
WHERE country = '하이티';

-- 7. 파푸아뉴기니 (Papua New Guinea): 띄어쓰기 제거로 통일
UPDATE beans
SET country = '파푸아뉴기니'
WHERE country = '파푸아 뉴기니';

-- 8. 대만 (Taiwan): 타이완 -> 대만 (한국에서 더 자주 쓰이는 표기로 통일)
UPDATE beans
SET country = '대만'
WHERE country = '타이완';

-- 9. 콩고 민주 공화국 (DRC): DR 콩고 -> 콩고 민주 공화국
UPDATE beans
SET country = '콩고 민주 공화국'
WHERE country = 'DR 콩고';

-- 1. Wet (습식) -> Washed (워시드)로 통일
UPDATE beans
SET processing_method = 'Washed'
WHERE processing_method = 'Wet';

-- 2. Dry Process (건식) -> Natural (내추럴)로 통일
UPDATE beans
SET processing_method = 'Natural'
WHERE processing_method = 'Dry Process';

-- 3. Dry Fermented -> Natural (문맥상 내추럴의 변형이므로 통일하거나 유지. 여기서는 Natural로 분류)
-- (만약 Dry Fermented를 별도 카테고리로 유지하고 싶으시면 이 쿼리는 생략하세요)
UPDATE beans
SET processing_method = 'Natural'
WHERE processing_method = 'Dry Fermented';

-- 4. Natural Anaerobic -> Anaerobic Natural (어순 통일)
UPDATE beans
SET processing_method = 'Anaerobic Natural'
WHERE processing_method = 'Natural Anaerobic';

-- 5. Washed Anaerobic (일관성을 위해 Anaerobic Washed로 변경 추천, 데이터엔 둘 다 존재 가능성)
UPDATE beans
SET processing_method = 'Anaerobic Washed'
WHERE processing_method = 'Washed Anaerobic';

-- 6. Double-Anaerobic -> Double Anaerobic (하이픈 제거)
UPDATE beans
SET processing_method = 'Double Anaerobic'
WHERE processing_method = 'Double-Anaerobic';

-- 7. Natural Water Process -> Water Process (디카페인 공정 명칭 단순화, 필요시 적용)
-- 참고: 'Water Process'는 보통 디카페인을 의미합니다.
UPDATE beans
SET processing_method = 'Water Process'
WHERE processing_method = 'Natural Water Process';
