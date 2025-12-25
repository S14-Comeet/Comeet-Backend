# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build (skip tests)
./gradlew clean build -x test

# Run locally (default profile: local)
./gradlew bootRun

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Run all tests
./gradlew test

# Run single test class
./gradlew test --tests "com.backend.domain.bean.BeanServiceTest"

# Run single test method
./gradlew test --tests "com.backend.domain.bean.BeanServiceTest.shouldCreateBean"

# Compile only (fast syntax check)
./gradlew compileJava

# Docker build
docker build -t comeet-backend .
```

## Technology Stack

- **Java 21** with **Spring Boot 3.5.7**
- **MyBatis 3.0.5** (XML Mapper approach, not JPA)
- **MySQL 8.0+** for persistence
- **Redis Stack** for caching, sessions, and vector search (recommendation system)
- **Spring AI + OpenAI** (via GMS proxy) for embeddings and LLM reranking
- **Spring Security** with JWT + OAuth2 (Naver login)
- **AWS S3** for image storage

## Architecture Overview

### Package Structure

The project follows a **Domain-Driven Design (DDD)** inspired layered architecture:

```
src/main/java/com/backend/
├── common/          # Shared utilities, configs, auth, error handling
│   ├── ai/          # OpenAI/Spring AI integration for recommendations
│   ├── auth/        # JWT, OAuth2, security principal
│   ├── config/      # Security, Redis, Web configs
│   ├── error/       # ErrorCode enum and domain-specific exceptions
│   ├── redis/       # Redis vector service for embeddings
│   └── response/    # BaseResponse wrapper (all APIs use this)
│
├── domain/          # Business logic grouped by domain
│   ├── user/           # User management
│   ├── store/          # Cafe/store management (with geospatial search)
│   ├── menu/           # Menu items linked to beans
│   ├── bean/           # Coffee bean information
│   ├── roastery/       # Roastery (coffee roasting company)
│   ├── visit/          # GPS-based visit verification (100m radius)
│   ├── review/         # Reviews with flavor tags + cupping notes
│   ├── passport/       # Monthly coffee passport (travel log)
│   ├── bookmark/       # Folder-based cafe bookmarks
│   ├── preference/     # User coffee preferences
│   ├── beanscore/      # Bean attribute scores for recommendations
│   ├── recommendation/ # AI-powered bean/menu recommendations
│   ├── flavor/         # SCA Flavor Wheel tags
│   ├── image/          # S3 image upload
│   └── ai/             # Batch AI image generation (passport covers)
```

### Domain Layer Pattern

Each domain follows this consistent structure:

```
domain/{name}/
├── controller/
│   ├── command/     # POST, PUT, DELETE endpoints
│   └── query/       # GET endpoints
├── service/
│   ├── command/     # Write operations (interface + impl)
│   ├── query/       # Read operations (interface + impl)
│   └── facade/      # Complex operations spanning multiple services
├── mapper/
│   ├── command/     # MyBatis mapper interface for writes
│   └── query/       # MyBatis mapper interface for reads
├── entity/          # Domain entities
├── dto/
│   ├── request/     # *ReqDto
│   └── response/    # *ResDto
├── converter/       # Entity <-> DTO mapping
├── validator/       # Domain-specific validation
└── factory/         # Entity creation (optional)
```

### MyBatis XML Mappers

SQL queries are in XML files under `src/main/resources/mapper/{domain}/`:
- `*CommandMapper.xml` - INSERT, UPDATE, DELETE
- `*QueryMapper.xml` - SELECT

Common SQL fragments are in `mapper/common/CommonSql.xml`.

### Database Schema

- Schema definition: `src/main/resources/sql/schema/schema.sql`
- Schema changes: `src/main/resources/sql/schema/change.sql`
- Test data: `src/main/resources/sql/test/`
- Production data (flavors, etc.): `src/main/resources/sql/data/`

### Key Domain Relationships

```
User ─┬─> Visit ─> Review ─> CuppingNote
      │     │
      │     └─> Passport (monthly aggregation)
      │
      ├─> Preference (coffee taste preferences)
      │
      └─> BookmarkFolder ─> BookmarkItem ─> Store

Store ─> Roastery
   │
   └─> Menu ─> MenuBean ─> Bean ─> BeanFlavor ─> Flavor
                             │
                             └─> BeanScore (for AI recommendations)
```

### API Response Format

All endpoints return `BaseResponse<T>`:

```json
{
  "success": true,
  "data": { ... },
  "error": null,
  "timestamp": "2025-12-19T..."
}
```

Use `ResponseUtils.ok(data)`, `ResponseUtils.created(data)`, or `ResponseUtils.noContent()`.

### Error Handling

- All error codes are centralized in `ErrorCode.java` enum
- Each domain has its own exception class (e.g., `UserException`, `StoreException`, `BeanException`)
- Exceptions are caught by `GlobalExceptionHandler`

### Authentication & Authorization

- JWT tokens in `Authorization: Bearer {token}` header
- Refresh tokens stored in HttpOnly cookies
- `@CurrentUser AuthenticatedUser` annotation for injecting current user in controllers
- Roles: `GUEST` (initial signup), `USER`, `OWNER` (store owner), `MANAGER` (admin)
- Use `@PreAuthorize("hasRole('ROLE_MANAGER')")` for role-based access control

## Coding Conventions

### DTO Pattern

Request DTOs use Java records with validation annotations:
```java
@Schema(description = "원두 생성 요청 DTO")
public record BeanCreateReqDto(
    @Schema(description = "이름", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "이름은 필수 입력값입니다.")
    String name
) {}
```

### Logging Convention

Services follow this logging pattern:
```java
log.info("[Domain] 작업명 - contextInfo={}", value);
// Examples:
log.info("[Bean] 원두 생성 - roasteryId={}", bean.getRoasteryId());
log.info("[BeanFlavor] 원두-플레이버 매핑 생성 - beanId={}, flavorIds={}", beanId, flavorIds);
```

### Converter Pattern

Use `@UtilityClass` for entity-to-DTO conversions:
```java
@UtilityClass
public class BeanConverter {
    public BeanResDto toBeanResDto(final Bean bean, final List<FlavorBadgeDto> flavors) {
        return BeanResDto.builder()...build();
    }
}
```

## Recommendation System

The project includes an AI-powered recommendation system for coffee beans and menus:

1. **Vector Embeddings**: Bean flavor tags are embedded using OpenAI's `text-embedding-3-small`
2. **Redis Vector Search**: Stored in Redis with cosine similarity index (`bean_embeddings`, dimension: 1536)
3. **LLM Reranking**: GPT-4o selects top 5 from vector search candidates with personalized reasons

Key components:
- `EmbeddingService` - Creates embeddings via OpenAI (through GMS proxy)
- `RedisVectorService` - Manages vector index and similarity search
- `LlmService` - Handles GPT-4o reranking
- `RecommendationFacadeService` - Orchestrates the recommendation pipeline
- `BeanEmbeddingBatchService` - Batch processing for embedding all beans

Admin endpoints for embedding management:
- `POST /admin/bean-scores/embed-all` - Embed all beans
- `POST /admin/bean-scores/embed-missing` - Embed beans without embeddings
- `POST /admin/bean-scores/drop-and-embed` - Delete and recreate all embeddings

## Configuration

Profiles: `local`, `dev` (set via `APP_PROFILE` env var)

Required environment variables:
- `JWT_SECRET`, `JWT_EXPIRATION`, `JWT_REFRESH_EXPIRATION` - JWT settings
- `CLIENT_ID_NAVER`, `CLIENT_SECRET_NAVER`, `REDIRECT_URL` - Naver OAuth2
- `GMS_KEY` - OpenAI API key (via GMS proxy)
- `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_S3_BUCKET` - S3 storage
- Database: configured per profile in `application-{profile}.yml`

## Git Conventions

- **Branch naming**: `feature/#issue` (e.g., `feature/54`)
- **Commit format**: `[gitmoji] type(#issue): subject`
  - Example: `✨ feat(#54): Add menu recommendation API`
- **Types**: feat, fix, docs, style, refactor, test, chore, perf
- **Gitmoji**: ✨(feat), 🐛(fix), 📝(docs), ♻️(refactor), ✅(test), 🔧(chore), ⚡(perf), 🔊(logging)
