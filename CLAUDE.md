# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Comeet** is a coffee exploration platform backend for specialty coffee enthusiasts (20-30s). Users can discover cafes, authenticate menu visits via GPS, write tasting notes, and track their coffee journey with a gamified system.

### Core Features
- GPS-based menu authentication (100m radius)
- Detailed coffee bean information (origin, farm, variety, processing)
- Tasting notes with beginner/advanced templates
- Coffee passport with statistics by country and roastery

## Technology Stack

- **Java**: 21 (required)
- **Spring Boot**: 3.5.7
- **Build Tool**: Gradle
- **Database**: MySQL 8.0+ with MyBatis 3.0.5
- **Security**: Spring Security with OAuth2 (Naver Login) + JWT
- **Cache/Session**: Redis (for refresh tokens and blacklist)
- **API Documentation**: Swagger/OpenAPI (accessible at `/`)
- **External APIs**: Naver GeoLocation API, Naver Map API
- **Deployment**: AWS / Naver Cloud Platform (with S3/Object Storage for images)

## Architecture Pattern: CQRS-Inspired Layered Architecture

The codebase follows a **CQRS-inspired separation** between commands (write operations) and queries (read operations):

### Package Structure
```
com.backend/
├── common/                          # Shared infrastructure
│   ├── auth/                        # Authentication/Authorization
│   │   ├── jwt/                     # JWT token provider & properties
│   │   ├── redis/                   # RefreshToken & BlackList entities
│   │   ├── handler/                 # OAuth2LoginSuccessHandler
│   │   ├── service/                 # CustomOAuth2UserService
│   │   └── principal/               # AuthenticatedUser (custom UserDetails)
│   ├── config/                      # Spring configurations
│   │   ├── SecurityConfig.java      # Security filter chain & OAuth2
│   │   ├── CorsConfig.java          # CORS settings
│   │   ├── RedisConfig.java         # Redis connection
│   │   └── PasswordConfig.java      # BCryptPasswordEncoder bean
│   ├── filter/                      # Servlet filters
│   │   ├── JwtAuthenticationFilter  # JWT validation filter
│   │   └── ExceptionHandlerFilter   # Filter exception handling
│   ├── error/                       # Error handling (ErrorCode, BusinessException)
│   ├── mapper/                      # Base MyBatis mapper interfaces
│   │   ├── CommandMapper.java       # Generic insert interface
│   │   └── QueryMapper.java         # Generic findById interface
│   ├── response/                    # Standardized API responses
│   │   ├── BaseResponse.java        # Wrapper with success/error/timestamp
│   │   ├── ErrorResponse.java       # Error details with validation
│   │   └── PageResponse.java        # Pagination wrapper
│   └── util/                        # Utilities (ResponseUtils, LoggingUtil, CookieUtil)
│
└── domain/                          # Domain-driven modules
    └── {domain}/                    # e.g., user, auth
        ├── controller/
        │   ├── command/             # Write endpoints (POST/PUT/DELETE)
        │   └── query/               # Read endpoints (GET)
        ├── service/
        │   ├── command/             # Write business logic
        │   └── query/               # Read business logic
        ├── mapper/
        │   ├── command/             # MyBatis write operations
        │   └── query/               # MyBatis read operations
        ├── entity/                  # JPA/MyBatis entities
        ├── dto/
        │   ├── request/             # Request DTOs
        │   └── response/            # Response DTOs
        └── converter/               # Entity ↔ DTO mapping
```

### CQRS Pattern Guidelines

**Command Side (Write Operations):**
- Controllers in `controller/command/` handle `POST`, `PUT`, `PATCH`, `DELETE`
- Services in `service/command/` contain transaction logic with `@Transactional`
- Mappers in `mapper/command/` extend `CommandMapper<T>` for insert/update/delete
- Example: `UserCommandController` → `UserCommandServiceImpl` → `UserCommandMapper`

**Query Side (Read Operations):**
- Controllers in `controller/query/` handle `GET` requests
- Services in `service/query/` are read-only (no `@Transactional` or `readOnly=true`)
- Mappers in `mapper/query/` extend `QueryMapper<T>` for select operations
- Example: `UserQueryController` → `UserQueryServiceImpl` → `UserQueryMapper`

**When creating new domains:**
1. Follow the command/query split for controllers, services, and mappers
2. Extend `CommandMapper<T>` or `QueryMapper<T>` base interfaces
3. Use `UserConverter`-style classes for entity ↔ DTO conversions
4. Place MyBatis XML in `src/main/resources/mapper/{domain}/` (e.g., `mapper/user/UserCommandMapper.xml`)

## Authentication & Security Flow

### OAuth2 + JWT Architecture

1. **OAuth2 Login (Naver)**:
   - User initiates OAuth2 login via Naver
   - `CustomOAuth2UserService` loads user info from Naver API
   - `OAuth2LoginSuccessHandler` generates JWT access/refresh tokens
   - Tokens stored in HTTP-only cookies

2. **JWT Authentication**:
   - `JwtAuthenticationFilter` validates JWT on each request
   - Refresh tokens stored in Redis (`RefreshToken` entity)
   - Revoked tokens stored in Redis blacklist (`BlackList` entity)
   - `JwtTokenProvider` handles token creation/validation

3. **Security Configuration**:
   - Whitelist defined in `SecurityConfig.WHITELIST` (includes Swagger, OAuth2 endpoints)
   - Session policy: STATELESS
   - CORS configured via `CorsConfig`

### Key Security Classes
- `AuthenticatedUser`: Custom `UserDetails` implementation with user ID and role
- `CustomOAuth2User`: OAuth2 user info wrapper
- `OAuth2Attribute`: Maps OAuth2 provider attributes to internal user model
- `Role`: Enum for user roles (ROLE_USER, etc.)

## Response Structure

All API responses use `BaseResponse<T>`:
```java
{
  "success": true/false,
  "data": T,                    // null on error
  "error": ErrorResponse,       // null on success
  "timestamp": "2025-11-18 14:30:00"
}
```

Use helper methods in `ResponseUtils`:
- `ResponseUtils.ok(data)` → HTTP 200
- `ResponseUtils.created(data)` → HTTP 201
- `ResponseUtils.noContent()` → HTTP 204

Error handling is centralized in `GlobalExceptionHandler`:
- `BusinessException` → custom error codes with proper HTTP status
- `UserException` → user-specific exceptions
- `MethodArgumentNotValidException` → validation errors with field details
- Generic `Exception` → 500 Internal Server Error

## Build and Development Commands

### Build and Run
```bash
# Windows
gradlew.bat build
gradlew.bat bootRun

# Unix/Linux/Mac
./gradlew build
./gradlew bootRun

# Clean build
./gradlew clean build
```

### Testing
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.backend.domain.user.UserCommandServiceTest"

# Run with detailed output
./gradlew test --info

# Coverage report (if configured)
./gradlew test jacocoTestReport
```

### Development
```bash
# Compile only
./gradlew compileJava

# Check dependencies
./gradlew dependencies
```

## Environment Configuration

The application uses **environment variables** for configuration. See `docs/ENV_SETUP_GUIDE.md` for detailed setup instructions.

### Required Variables
```bash
# Database
DB_URL=jdbc:mysql://localhost:3306/comeet
DB_USERNAME=your_username
DB_PASSWORD=your_password

# OAuth2 (Naver)
CLIENT_ID_NAVER=your_naver_client_id
CLIENT_SECRET_NAVER=your_naver_client_secret
REDIRECT_URL=http://localhost:8080/login/oauth2/code/naver

# JWT
JWT_SECRET=your-256-bit-secret-key
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
```

### Optional Variables
```bash
APP_NAME=Comeet
APP_PROFILE=dev           # dev/prod
APP_VERSION=1.0.0
LOG_LEVEL_ROOT=INFO
LOG_LEVEL_APP=DEBUG
LOG_LEVEL_MYBATIS=DEBUG
DB_POOL_MAX_SIZE=10
DB_POOL_MIN_IDLE=5
```

**Setup Steps:**
1. Copy `.env.example` to `.env`: `cp .env.example .env`
2. Fill in actual values in `.env` file
3. Never commit `.env` file (already in `.gitignore`)

Profiles are in `application-{profile}.yml` (e.g., `application-dev.yml`)

## MyBatis Configuration

See `docs/MYBATIS_SETUP_GUIDE.md` for detailed MyBatis setup and best practices.

### Current Setup
- Mapper XMLs: `classpath:mapper/**/*.xml`
- Type aliases: `com.backend.domain.*.entity`
- Auto-mapping: `map-underscore-to-camel-case: true` (DB snake_case → Java camelCase)
- Query logging: Slf4j via MyBatis (`org.apache.ibatis.logging.slf4j.Slf4jImpl`)

### When Creating MyBatis Mappers
1. Create XML in `src/main/resources/mapper/{domain}/` (e.g., `mapper/user/UserCommandMapper.xml`)
2. Namespace matches fully qualified interface name
3. Use `#{paramName}` for parameters, rely on auto-mapping for result sets
4. For insert operations, use `useGeneratedKeys="true" keyProperty="id"` to retrieve auto-generated IDs
5. Example structure:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.backend.domain.user.mapper.command.UserCommandMapper">
    <insert id="insert" parameterType="User" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO user (name, email, password_hash)
        VALUES (#{name}, #{email}, #{passwordHash})
    </insert>
</mapper>
```

## Redis Configuration

Redis is used for:
- **Refresh tokens**: `RefreshToken` entity stored with TTL
- **Token blacklist**: `BlackList` entity for revoked/logged-out tokens

Configuration in `RedisConfig.java`:
- Host: `localhost` (default)
- Port: `6379` (default)
- Timeout: `60000ms`

Repository interfaces:
- `RefreshTokenRepository` (extends `CrudRepository`)
- `BlackListRepository` (extends `CrudRepository`)

## API Documentation (Swagger)

Swagger UI is accessible at the root path:
- **URL**: `http://localhost:8080/`
- Configuration in `application.yml`:
  - `springdoc.swagger-ui.path: /`
  - Operations sorted alphabetically
  - Request duration displayed
  - "Try it out" enabled

## Database Schema

Schema initialization:
- Location: `src/main/resources/schema/schema.sql`
- Auto-applied on startup: `spring.sql.init.mode: always`
- **Important**: Set to `never` in production to avoid data loss

## Security Whitelist

The following endpoints are publicly accessible (defined in `SecurityConfig.WHITELIST`):
- `/` (Swagger UI)
- `/swagger-ui/**`
- `/v3/api-docs/**`
- `/login/oauth2/**`
- `/oauth2/**`
- `/api/auth/**`

All other endpoints require JWT authentication.

## Commit Message Convention

Follow **Angular Convention with Gitmoji**:
```
[gitmoji] type(#issue): subject

[optional body]
```

**Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `perf`, `ci`, `build`

**Scope:** Always include `#issue_number` (e.g., `#42`)

**Examples:**
```
✨ feat(#12): 카페 메뉴 인증 API 추가
🐛 fix(#34): 사용자 조회 시 NPE 수정
📝 docs(#56): README에 API 엔드포인트 문서 추가
🔧 config(#3): SecurityConfig에 JWT 필터 추가
```

**Common Gitmojis:**
- ✨ `:sparkles:` - feat
- 🐛 `:bug:` - fix
- 📝 `:memo:` - docs
- ♻️ `:recycle:` - refactor
- ✅ `:white_check_mark:` - test
- 🔧 `:wrench:` - chore/config
- 🔥 `:fire:` - remove code/files

## Project Context

- **Frontend:** Separate Vue.js repository using Naver Map API
- **Database:** MySQL 8.0+
- **Documentation:**
  - `Comeet_기능명세서.xlsx` - detailed feature specifications
  - `docs/ENV_SETUP_GUIDE.md` - environment variable setup
  - `docs/MYBATIS_SETUP_GUIDE.md` - MyBatis configuration and best practices
- **Status:** In active development (feature branches follow `feature/#issue` naming)

## Additional Resources

- **Swagger UI**: `http://localhost:8080/`
- **Actuator Endpoints**: `/actuator/health`, `/actuator/info`, `/actuator/metrics`
- **Database Schema**: `src/main/resources/schema/schema.sql`
