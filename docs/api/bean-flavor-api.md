# 원두-플레이버 매핑 API 명세서

> 최종 업데이트: 2025-12-24

## 개요

원두(Bean)와 플레이버(Flavor)를 매핑하여 `bean_flavor_notes` 테이블을 관리하는 API입니다.

---

## 기본 정보

| 항목 | 값 |
|------|-----|
| Base URL | `{서버주소}/beans/{beanId}/flavors` |
| 인증 방식 | JWT Bearer Token |
| 권한 | `ROLE_MANAGER` 필요 |
| Content-Type | `application/json` |

---

## 공통 응답 형식

모든 API 응답은 `BaseResponse` 래퍼로 감싸집니다.

```json
{
  "success": true,
  "data": { ... },
  "error": null,
  "timestamp": "2025-12-24 15:30:45"
}
```

### 에러 응답

```json
{
  "success": false,
  "data": null,
  "error": {
    "status": "FORBIDDEN",
    "message": "접근이 거부되었습니다.",
    "method": "POST",
    "requestURI": "/beans/1/flavors",
    "errors": []
  },
  "timestamp": "2025-12-24 15:30:45"
}
```

---

## API 목록

| # | 엔드포인트 | 메서드 | 인증 | 설명 |
|---|-----------|--------|------|------|
| 1 | `/beans/{beanId}/flavors` | POST | O (MANAGER) | 플레이버 매핑 추가 |
| 2 | `/beans/{beanId}/flavors` | PUT | O (MANAGER) | 플레이버 매핑 전체 교체 |
| 3 | `/beans/{beanId}/flavors` | DELETE | O (MANAGER) | 플레이버 매핑 전체 삭제 |

---

## 1. 플레이버 매핑 추가

원두에 새로운 플레이버를 추가 매핑합니다. 기존 매핑은 유지됩니다.

### 요청

```
POST /beans/{beanId}/flavors
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**경로 파라미터:**

| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| beanId | number | O | 원두 ID |

**요청 바디:**

```json
{
  "flavorIds": [1, 5, 10]
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| flavorIds | number[] | O | 추가할 플레이버 ID 목록 (최소 1개) |

### 응답

**성공 (201 Created):**

```json
{
  "success": true,
  "data": {
    "beanId": 1,
    "flavors": [
      {
        "flavorId": 1,
        "code": "Fruity",
        "colorHex": "#FF5733"
      },
      {
        "flavorId": 5,
        "code": "Floral",
        "colorHex": "#E91E63"
      },
      {
        "flavorId": 10,
        "code": "Nutty",
        "colorHex": "#8B4513"
      }
    ]
  },
  "error": null,
  "timestamp": "2025-12-24 15:30:45"
}
```

### 에러 코드

| HTTP 상태 | 코드 | 설명 |
|-----------|------|------|
| 201 | - | 성공 |
| 400 | C-001 | flavorIds가 비어있음 |
| 401 | A-007 | 인증 실패 |
| 403 | C-006 | 권한 없음 (MANAGER 권한 필요) |
| 404 | B-001 | 원두를 찾을 수 없음 |
| 409 | D-002 | 중복된 플레이버 매핑 (이미 존재) |
| 500 | B-008 | 플레이버 매핑 저장 실패 |

---

## 2. 플레이버 매핑 전체 교체

원두의 기존 플레이버 매핑을 모두 삭제하고 새로운 매핑으로 교체합니다.

### 요청

```
PUT /beans/{beanId}/flavors
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**경로 파라미터:**

| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| beanId | number | O | 원두 ID |

**요청 바디:**

```json
{
  "flavorIds": [2, 7, 15]
}
```

| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| flavorIds | number[] | O | 새로 매핑할 플레이버 ID 목록 (최소 1개) |

### 응답

**성공 (200 OK):**

```json
{
  "success": true,
  "data": {
    "beanId": 1,
    "flavors": [
      {
        "flavorId": 2,
        "code": "Citrus",
        "colorHex": "#FFA500"
      },
      {
        "flavorId": 7,
        "code": "Chocolate",
        "colorHex": "#7B3F00"
      },
      {
        "flavorId": 15,
        "code": "Caramel",
        "colorHex": "#FFD700"
      }
    ]
  },
  "error": null,
  "timestamp": "2025-12-24 15:30:45"
}
```

### 에러 코드

| HTTP 상태 | 코드 | 설명 |
|-----------|------|------|
| 200 | - | 성공 |
| 400 | C-001 | flavorIds가 비어있음 |
| 401 | A-007 | 인증 실패 |
| 403 | C-006 | 권한 없음 (MANAGER 권한 필요) |
| 404 | B-001 | 원두를 찾을 수 없음 |
| 500 | B-008 | 플레이버 매핑 저장 실패 |

---

## 3. 플레이버 매핑 전체 삭제

원두에 매핑된 모든 플레이버를 삭제합니다.

### 요청

```
DELETE /beans/{beanId}/flavors
Authorization: Bearer {accessToken}
```

**경로 파라미터:**

| 파라미터 | 타입 | 필수 | 설명 |
|---------|------|------|------|
| beanId | number | O | 원두 ID |

### 응답

**성공 (204 No Content):**

```json
{
  "success": true,
  "data": null,
  "error": null,
  "timestamp": "2025-12-24 15:30:45"
}
```

### 에러 코드

| HTTP 상태 | 코드 | 설명 |
|-----------|------|------|
| 204 | - | 성공 (매핑이 없어도 성공) |
| 401 | A-007 | 인증 실패 |
| 403 | C-006 | 권한 없음 (MANAGER 권한 필요) |
| 404 | B-001 | 원두를 찾을 수 없음 |

---

## 데이터 타입 참조

### BeanFlavorResDto (응답 DTO)

```json
{
  "beanId": 1,
  "flavors": [
    {
      "flavorId": 1,
      "code": "Fruity",
      "colorHex": "#FF5733"
    }
  ]
}
```

| 필드 | 타입 | 설명 |
|------|------|------|
| beanId | number | 원두 ID |
| flavors | FlavorBadgeDto[] | 매핑된 플레이버 목록 |

### FlavorBadgeDto (플레이버 뱃지)

```json
{
  "flavorId": 10,
  "code": "Fruity",
  "colorHex": "#FF5733"
}
```

| 필드 | 타입 | 설명 |
|------|------|------|
| flavorId | number | 플레이버 ID |
| code | string | 플레이버 코드 (영문) |
| colorHex | string | 플레이버 색상 (Hex) |

---

## 프론트엔드 사용 예시

### JavaScript/TypeScript

```typescript
// API 클라이언트 설정
const api = axios.create({
  baseURL: 'https://api.example.com',
  headers: {
    'Content-Type': 'application/json',
  },
});

// 인증 헤더 설정
api.interceptors.request.use((config) => {
  const token = getAccessToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 1. 플레이버 매핑 추가
async function addBeanFlavors(beanId: number, flavorIds: number[]) {
  const response = await api.post(`/beans/${beanId}/flavors`, { flavorIds });
  return response.data.data;
}

// 2. 플레이버 매핑 전체 교체
async function updateBeanFlavors(beanId: number, flavorIds: number[]) {
  const response = await api.put(`/beans/${beanId}/flavors`, { flavorIds });
  return response.data.data;
}

// 3. 플레이버 매핑 전체 삭제
async function deleteBeanFlavors(beanId: number) {
  await api.delete(`/beans/${beanId}/flavors`);
}
```

### 사용 예시

```typescript
// 원두 ID 1에 플레이버 [1, 5, 10] 추가
const result = await addBeanFlavors(1, [1, 5, 10]);
console.log(result.flavors); // 매핑된 플레이버 목록

// 원두 ID 1의 플레이버를 [2, 7]로 전체 교체
const updated = await updateBeanFlavors(1, [2, 7]);
console.log(updated.flavors); // 새로운 플레이버 목록

// 원두 ID 1의 모든 플레이버 매핑 삭제
await deleteBeanFlavors(1);
```

---

## 참고 사항

1. **권한**: 모든 API는 `MANAGER` 권한이 필요합니다.
2. **중복 매핑**: POST 요청 시 이미 매핑된 플레이버 ID가 포함되어 있으면 409 에러가 발생합니다.
3. **PUT vs POST**:
   - `POST`는 기존 매핑을 유지하면서 추가
   - `PUT`은 기존 매핑을 삭제하고 새로 교체
4. **빈 배열**: 매핑된 플레이버가 없으면 `flavors`는 빈 배열 `[]`입니다.
5. **트랜잭션**: PUT 요청은 삭제와 삽입이 하나의 트랜잭션으로 처리됩니다.
