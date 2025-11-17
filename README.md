<div align="center">

# ☕ Comeet
[![개발 상태][status-shield]][status-url]

### ✨ 커피와 사람을 연결하는 특별한 만남 ✨
**메뉴 인증으로 시작하는 나만의 커피 여행**
</div>

---

## 📋 목차

- [📖 프로젝트 소개](#-프로젝트-소개)
- [🎯 프로젝트 목표](#-프로젝트-목표)
- [🛠️ 기술 스택](#️-기술-스택)
- [📝 Commit Message Convention](#-commit-message-convention)
- [📚 상세 문서](#-상세-문서)
---

# 📖 프로젝트 소개

> [!IMPORTANT]
> **Comeet**은 스페셜티 커피를 사랑하는 20-30대를 위한 커피 탐험 플랫폼입니다.
> 지도 기반으로 주변 카페를 발견하고, **메뉴를 인증**하며, **커피 테이스팅 노트**를 작성하는 새로운 경험을 제공합니다.

**핵심 차별점**:
- 🎯 **메뉴 인증 시스템**: GPS 기반 100m 반경 내 메뉴 인증
- ☕ **커피 원두 정보**: 생산 국가, 농장, 품종, 가공 방식 등 상세 정보
- 📝 **테이스팅 노트**: 초심자/심화자 템플릿으로 체계적인 커피 기록
- 📊 **커피 여권**: 국가별, 로스터리별 통계 및 방문 기록

---

# 🎯 프로젝트 목표

### 핵심 가치
- **접근성**: 커피 초보자부터 심화자까지 수준별 기록 템플릿 제공
- **탐험**: 지도 기반으로 새로운 카페를 발견하고 메뉴 인증
- **성장**: 레벨 시스템과 뱃지를 통한 게임화 요소
- **기록**: 테이스팅 노트를 통한 체계적인 커피 기록 관리

### 타겟 사용자
- 스페셜티 커피에 관심 있는 20-30대
- 새로운 카페를 탐험하고 싶은 커피 애호가
- 체계적으로 커피를 기록하고 싶은 사용자
- 자신의 커피 취향을 찾아가고 싶은 초보자

---

# 🛠️ 기술 스택

## Backend (이 레포지토리)
```
- Language: Java 21
- Framework: Spring Boot 3.5.7
- ORM: MyBatis
- Architecture: REST API
- Database: MySQL 8.0+
- External API: Naver GeoLocation API
```

## Frontend (별도 레포지토리)
```
- Framework: Vue.js
- Map API: Naver Map API
```

## Infrastructure
```
- Deployment: AWS / Naver Cloud Platform
- Image Storage: AWS S3 / Object Storage
```


---

# 📝 Commit Message Convention

## 기본 형식 (Angular Commit Convention)
```
[gitmoji] type(#issue): subject

[optional body]

[optional footer]
```

## Type
- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `docs`: 문서 수정
- `style`: 코드 포맷팅, 세미콜론 누락 등 (코드 변경 없음)
- `refactor`: 코드 리팩토링
- `test`: 테스트 코드 추가/수정
- `chore`: 빌드, 패키지 매니저 설정 등
- `perf`: 성능 개선
- `ci`: CI/CD 설정 변경
- `build`: 빌드 시스템 변경

## Scope (필수)
- `#이슈번호` 형식으로 작성
- 예: `#1`, `#42`, `#123`

## Subject (필수)
- 50자 이내로 간결하게 작성
- 마침표 없이 명령문으로 작성
- 한글 또는 영문 사용 가능

## Gitmoji (선택)
- 커밋 타입을 시각적으로 표현
- **사용 여부는 선택 사항**

### 자주 사용하는 Gitmoji
| Emoji | Code | Type |
|-------|------|------|
| ✨ | `:sparkles:` | feat (새 기능) |
| 🐛 | `:bug:` | fix (버그 수정) |
| 📝 | `:memo:` | docs (문서) |
| ♻️ | `:recycle:` | refactor (리팩토링) |
| ✅ | `:white_check_mark:` | test (테스트) |
| 🔧 | `:wrench:` | chore (설정) |
| ⚡ | `:zap:` | perf (성능) |
| 💄 | `:lipstick:` | style (스타일) |
| 🔥 | `:fire:` | 코드/파일 삭제 |
| 🚀 | `:rocket:` | 배포 |

---

## 📚 상세 문서

프로젝트의 상세한 설계 및 구현 가이드는 다음 문서를 참고하세요:

- **[Comeet_기능명세서.xlsx](./Comeet_기능명세서.xlsx)** - 상세 기능 명세

---

<div align="center">

### ✨ **Comeet과 함께 나만의 커피 여행을 시작하세요!** ✨

*Made with ❤️ by Comeet Team*

</div>

[status-url]: #
[status-shield]: https://img.shields.io/badge/status-in%20development-yellow?style=for-the-badge
