# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build (skip tests)
./gradlew clean build -x test

# Run locally
./gradlew bootRun

# Run tests
./gradlew test

# Docker build
docker build -t comeet-backend .
```

## Technology Stack

- **Java 21** with **Spring Boot 3.5.7**
- **MyBatis 3.0.5** (XML Mapper approach, not JPA)
- **MySQL 8.0+** for persistence
- **Redis Stack** for caching, sessions, and vector search (recommendation system)
- **Spring AI + OpenAI** for embeddings and LLM reranking
- **Spring Security** with JWT + OAuth2 (Naver login)

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
│   ├── user/
│   ├── store/
│   ├── menu/
│   ├── bean/
│   ├── visit/
│   ├── review/
│   ├── preference/     # User coffee preferences
│   ├── beanscore/      # Bean attribute scores
│   └── recommendation/ # AI-powered recommendations
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

Use `BaseResponse.ok(data)`, `BaseResponse.created(data)`, or `BaseResponse.fail(error)`.

### Error Handling

- All error codes are centralized in `ErrorCode.java` enum
- Each domain has its own exception class (e.g., `UserException`, `StoreException`)
- Exceptions are caught by `GlobalExceptionHandler`

### Authentication

- JWT tokens in `Authorization: Bearer {token}` header
- Refresh tokens stored in HttpOnly cookies
- `@CurrentUser AuthenticatedUser` annotation for injecting current user in controllers
- Roles: `GUEST` (initial signup), `USER`, `OWNER` (store owner)

## Recommendation System

The project includes an AI-powered recommendation system for coffee beans and menus:

1. **Vector Embeddings**: Bean flavor tags are embedded using OpenAI's `text-embedding-3-small`
2. **Redis Vector Search**: Stored in Redis with cosine similarity index (`bean_embeddings`)
3. **LLM Reranking**: GPT-4o selects top 3 from vector search candidates with personalized reasons

Key components:
- `EmbeddingService` - Creates embeddings via OpenAI
- `RedisVectorService` - Manages vector index and similarity search
- `LlmService` - Handles GPT-4o reranking
- `RecommendationFacadeService` - Orchestrates the recommendation pipeline

## Git Conventions

- **Branch naming**: `feature/#issue` (e.g., `feature/54`)
- **Commit format**: `[gitmoji] type(#issue): subject`
  - Example: `✨ feat(#54): Add menu recommendation API`
- **Types**: feat, fix, docs, style, refactor, test, chore, perf
