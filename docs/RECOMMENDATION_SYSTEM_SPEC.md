# 메뉴/원두 추천 시스템 기술 명세서

> **Version**: 1.0.0
> **Last Updated**: 2025-12-22
> **Author**: Comeet Backend Team

---

## 1. 시스템 개요

### 1.1 목적
사용자의 커피 취향(user_preferences)과 원두 특성 점수(bean_scores)를 기반으로 개인화된 메뉴 및 원두를 추천하는 AI 기반 추천 시스템

### 1.2 추천 유형
| 유형 | 설명 | 사용 사례 |
|------|------|----------|
| **근거리 추천 (LOCAL)** | 사용자 위치 기준 반경 N km 내 카페의 메뉴/원두만 추천 | "지금 갈 수 있는 카페에서 내 취향에 맞는 메뉴 추천" |
| **전역 추천 (GLOBAL)** | 거리 상관없이 전체 메뉴/원두에서 추천 | "내 취향에 맞는 원두 찾기" |

### 1.3 기술 스택
| 구분 | 기술 | 용도 |
|------|------|------|
| Vector DB | Redis Stack (Vector Search) | 임베딩 저장 및 유사도 검색 |
| LLM | OpenAI GPT-4o | 최종 리랭킹 및 추천 이유 생성 |
| Embedding | OpenAI text-embedding-3-small | 플레이버 태그 벡터화 |
| 위치 계산 | GeoUtils (Haversine) | 거리 계산 및 BoundingBox 필터링 |

---

## 2. 추천 프로세스

### 2.1 전체 흐름

```
사용자 요청
    │
    ▼
┌───────────────────────────────────────┐
│ Step 1: 위치 필터링 (LOCAL 모드만)     │
│ - BoundingBox로 반경 내 카페 필터      │
└───────────────────────────────────────┘
    │
    ▼
┌───────────────────────────────────────┐
│ Step 2: 하드 필터링 (SQL)              │
│ - disliked_tags 제외                   │
│ - preferred_roast_levels 필터          │
└───────────────────────────────────────┘
    │
    ▼
┌───────────────────────────────────────┐
│ Step 3: 벡터 유사도 검색 (Redis)       │
│ - liked_tags 임베딩                    │
│ - Cosine Similarity 계산               │
│ - 상위 20개 추출                       │
└───────────────────────────────────────┘
    │
    ▼
┌───────────────────────────────────────┐
│ Step 4: LLM 리랭킹 (GPT-4o)           │
│ - 사용자 취향 + 후보 20개 전달         │
│ - 최종 3개 선정 + 추천 이유 생성       │
└───────────────────────────────────────┘
    │
    ▼
   응답 반환
```

### 2.2 단계별 상세 설명

#### Step 1: 위치 필터링 (LOCAL 모드)
- **입력**: 사용자 위도/경도, 반경(km)
- **처리**: `GeoUtils.calculateBoundingBox()`로 BoundingBox 계산
- **출력**: 반경 내 카페 ID 목록

```java
BoundingBox box = GeoUtils.calculateBoundingBox(lat, lon, radiusKm);
// SQL WHERE: latitude BETWEEN minLat AND maxLat
//        AND longitude BETWEEN minLon AND maxLon
```

#### Step 2: 하드 필터링
- **입력**: 사용자 취향 (disliked_tags, preferred_roast_levels)
- **처리**: SQL WHERE절로 절대 제외 조건 적용
- **출력**: 필터링된 원두/메뉴 목록

```sql
WHERE NOT JSON_OVERLAPS(bs.flavor_tags, #{dislikedTagsJson})
  AND bs.roast_level IN (#{preferredRoastLevels})
```

#### Step 3: 벡터 유사도 검색
- **입력**: 사용자 liked_tags 임베딩, 후보 원두 목록
- **처리**: Redis Vector Search로 Cosine Similarity 계산
- **출력**: 유사도 상위 20개

```java
Query query = new Query("*=>[KNN 20 @embedding $vec AS score]")
    .addParam("vec", userEmbedding)
    .setSortBy("score", true);
```

#### Step 4: LLM 리랭킹
- **입력**: 사용자 취향 JSON, 후보 20개 정보
- **처리**: GPT-4o로 최종 순위 결정
- **출력**: Top 3 + 추천 이유

```
프롬프트: "당신은 커피 소믈리에입니다. 사용자 취향과 후보 원두를 분석하여
          가장 적합한 3개를 선정하고 추천 이유를 설명해주세요."
```

---

## 3. 데이터 모델

### 3.1 user_preferences 테이블

| 컬럼 | 타입 | 설명 | 범위/예시 |
|------|------|------|----------|
| id | BIGINT | PK | - |
| user_id | BIGINT | 사용자 FK (UNIQUE) | - |
| pref_acidity | TINYINT | 선호 산미 | 1-10 (1=부드러움, 10=강렬함) |
| pref_body | TINYINT | 선호 바디감 | 1-10 (1=가벼움, 10=묵직함) |
| pref_sweetness | TINYINT | 선호 단맛 | 1-10 (1=드라이, 10=달콤함) |
| pref_bitterness | TINYINT | 선호 쓴맛 | 1-10 (1=거의없음, 10=강함) |
| preferred_roast_levels | JSON | 선호 배전도 | `["LIGHT", "MEDIUM"]` |
| liked_tags | JSON | 선호 플레이버 태그 | `["fruity", "floral", "citrus"]` |
| disliked_tags | JSON | 비선호 태그 (하드 필터링) | `["earthy", "smoky"]` |

### 3.2 bean_scores 테이블

| 컬럼 | 타입 | 설명 | 범위/예시 |
|------|------|------|----------|
| id | BIGINT | PK | - |
| bean_id | BIGINT | 원두 FK (UNIQUE) | - |
| acidity | TINYINT | 산미 | 1-10 |
| body | TINYINT | 바디감 | 1-10 |
| sweetness | TINYINT | 단맛 | 1-10 |
| bitterness | TINYINT | 쓴맛 | 1-10 |
| aroma | TINYINT | 향 | 1-10 |
| flavor | TINYINT | 풍미 | 1-10 |
| aftertaste | TINYINT | 여운 | 1-10 |
| total_score | TINYINT | 총점 | 0-100 |
| roast_level | ENUM | 배전도 | LIGHT, MEDIUM, HEAVY |
| flavor_tags | JSON | 플레이버 태그 (임베딩용) | `["Fruity > Citrus > Lemon", "Floral > Jasmine"]` |

### 3.3 관계도

```
User (1) ──── (1) UserPreference
                    │
                    │ 추천 매칭
                    ▼
Bean (1) ──── (1) BeanScore
  │
  │ menu_bean_mappings (N:M)
  ▼
Menu ──── Store (위치 정보)
```

---

## 4. API 명세

### 4.1 사용자 취향 API

#### GET /api/preferences
현재 로그인 사용자의 취향 조회

**Response**
```json
{
  "success": true,
  "data": {
    "prefAcidity": 7,
    "prefBody": 5,
    "prefSweetness": 8,
    "prefBitterness": 3,
    "preferredRoastLevels": ["LIGHT", "MEDIUM"],
    "likedTags": ["fruity", "floral", "citrus"],
    "dislikedTags": ["earthy", "smoky"]
  }
}
```

#### PUT /api/preferences
사용자 취향 업데이트

**Request**
```json
{
  "prefAcidity": 7,
  "prefBody": 5,
  "prefSweetness": 8,
  "prefBitterness": 3,
  "preferredRoastLevels": ["LIGHT", "MEDIUM"],
  "likedTags": ["fruity", "floral"],
  "dislikedTags": ["earthy"]
}
```

### 4.2 추천 API

#### GET /api/recommendations/beans
전역 원두 추천 (Top 3)

**Response**
```json
{
  "success": true,
  "data": [
    {
      "beanId": 1,
      "beanName": "Ethiopia Yirgacheffe",
      "roasteryName": "Onion Coffee",
      "country": "Ethiopia",
      "roastLevel": "LIGHT",
      "flavorTags": ["Fruity > Citrus > Lemon", "Floral > Jasmine"],
      "rank": 1,
      "reason": "밝은 산미와 플로럴한 향을 선호하시는 취향에 완벽하게 맞는 원두입니다.",
      "similarityScore": 0.92
    }
  ]
}
```

#### GET /api/recommendations/menus/nearby
근거리 메뉴 추천

**Query Parameters**
| 파라미터 | 타입 | 필수 | 설명 |
|----------|------|------|------|
| lat | BigDecimal | O | 사용자 위도 |
| lon | BigDecimal | O | 사용자 경도 |
| radius | Integer | X | 반경 km (기본값: 5) |

**Response**
```json
{
  "success": true,
  "data": [
    {
      "menuId": 42,
      "menuName": "Ethiopia Hand Drip",
      "price": 6000,
      "imageUrl": "https://...",
      "storeId": 10,
      "storeName": "Onion Coffee 성수점",
      "storeAddress": "서울시 성동구 성수동 123-45",
      "distanceKm": 0.8,
      "bean": {
        "id": 1,
        "name": "Ethiopia Yirgacheffe"
      },
      "rank": 1,
      "reason": "가까운 거리에서 취향에 맞는 에티오피아 원두를 즐길 수 있습니다.",
      "similarityScore": 0.89
    }
  ]
}
```

#### GET /api/recommendations/beans/{beanId}/menus
특정 원두를 사용하는 메뉴 조회

**Path Parameters**
| 파라미터 | 타입 | 설명 |
|----------|------|------|
| beanId | Long | 원두 ID |

---

## 5. 임베딩 스키마

### 5.1 Redis Vector Index

```
Index Name: bean_embeddings
Key Pattern: bean:{beanId}
Vector Field: embedding
Dimension: 1536 (text-embedding-3-small)
Distance Metric: COSINE
```

### 5.2 저장 구조

```json
{
  "key": "bean:1",
  "fields": {
    "beanId": 1,
    "embedding": [0.023, -0.045, ...],  // 1536 dimensions
    "flavorTags": "[\"Fruity > Citrus\", \"Floral\"]"
  }
}
```

### 5.3 임베딩 생성 규칙

1. `flavor_tags` JSON Array를 쉼표로 연결한 문자열 생성
2. OpenAI text-embedding-3-small 모델로 임베딩
3. Redis에 저장

```java
String embeddingText = String.join(", ", flavorTags);
// "Fruity > Citrus > Lemon, Floral > Jasmine"
float[] embedding = openAiClient.createEmbedding(embeddingText);
```

---

## 6. LLM 프롬프트 설계

### 6.1 리랭킹 프롬프트

```
당신은 전문 커피 소믈리에입니다.

## 사용자 취향
{userPreferenceJson}

## 후보 원두 목록 (20개)
{candidatesJson}

## 지시사항
1. 사용자 취향과 가장 잘 맞는 원두 3개를 선정하세요
2. 각 원두에 대해 1문장의 추천 이유를 작성하세요
3. 취향 점수와 플레이버 태그의 유사도를 모두 고려하세요

## 응답 형식 (JSON)
{
  "recommendations": [
    {
      "beanId": 1,
      "rank": 1,
      "reason": "추천 이유..."
    }
  ]
}
```

### 6.2 프롬프트 파라미터
| 파라미터 | 설명 |
|----------|------|
| temperature | 0.3 (일관된 추천을 위해 낮게 설정) |
| max_tokens | 500 |
| response_format | JSON |

---

## 7. 에러 코드

| 코드 | HTTP Status | 설명 |
|------|-------------|------|
| PR-001 | 404 | 사용자 취향 정보를 찾을 수 없습니다 |
| PR-002 | 409 | 이미 취향 정보가 존재합니다 |
| BS-001 | 404 | 원두 점수를 찾을 수 없습니다 |
| BS-002 | 409 | 이미 원두 점수가 존재합니다 |
| RC-001 | 500 | 추천 생성 실패 |
| RC-002 | 500 | 임베딩 생성 실패 |
| RC-003 | 503 | LLM 서비스 연결 실패 |
| RC-004 | 400 | 추천 데이터 부족 |

---

## 8. 성능 고려사항

### 8.1 캐싱 전략
| 대상 | TTL | 무효화 조건 |
|------|-----|-------------|
| 원두 임베딩 | 영구 | 원두 정보 변경 시 |
| 사용자 임베딩 | 1시간 | 취향 변경 시 |
| 추천 결과 | 10분 | 취향 변경 시 |

### 8.2 비용 최적화
- **임베딩**: 배치 처리로 API 호출 최소화
- **LLM**: 캐싱으로 동일 요청 재사용
- **Redis**: 커넥션 풀링

### 8.3 예상 응답 시간
| 단계 | 예상 시간 |
|------|----------|
| 하드 필터링 | ~50ms |
| 벡터 검색 | ~100ms |
| LLM 리랭킹 | ~1-2s |
| **총합** | **~1.5-2.5s** |

---

## 9. 배포 요구사항

### 9.1 인프라
- Redis Stack 7.2+ (Vector Search 지원)
- OpenAI API Key

### 9.2 환경 변수
```yaml
OPENAI_API_KEY: ${OPENAI_API_KEY}
REDIS_HOST: ${REDIS_HOST}
REDIS_PORT: ${REDIS_PORT}
```

### 9.3 의존성
```groovy
implementation 'org.springframework.ai:spring-ai-openai-spring-boot-starter:1.0.0-M4'
implementation 'redis.clients:jedis:5.1.0'
implementation 'com.google.code.gson:gson:2.10.1'
```
