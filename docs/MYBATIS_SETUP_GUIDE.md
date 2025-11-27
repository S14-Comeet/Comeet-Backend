# MyBatis 설정 가이드

이 문서는 Comeet 프로젝트에서 MyBatis를 설정하고 사용하는 방법을 설명합니다.

## 📋 목차

1. [설정 파일 개요](#설정-파일-개요)
2. [데이터베이스 설정](#데이터베이스-설정)
3. [프로젝트 구조](#프로젝트-구조)
4. [사용 방법](#사용-방법)
5. [Best Practices](#best-practices)
6. [트러블슈팅](#트러블슈팅)

## 설정 파일 개요

### 1. application.yaml

Spring Boot의 메인 설정 파일로, 데이터베이스 연결과 MyBatis 기본 설정을 관리합니다.

**주요 설정:**
- `spring.datasource`: 데이터베이스 연결 정보
- `mybatis.mapper-locations`: Mapper XML 파일 위치
- `mybatis.type-aliases-package`: Entity 클래스 패키지
- `mybatis.configuration`: MyBatis 동작 설정

### 2. mybatis-config.xml (선택적)

고급 MyBatis 설정이 필요한 경우 사용합니다.
- Custom Type Handler
- Plugin 설정
- 특수한 전역 설정

> ⚠️ **주의**: application.yaml과 설정이 중복되지 않도록 주의하세요.

## 데이터베이스 설정

### 1. MySQL 설치 및 데이터베이스 생성

```bash
# MySQL 접속
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE comeet DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 사용자 생성 (선택적)
CREATE USER 'comeet_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON comeet.* TO 'comeet_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. 스키마 적용

```bash
# schema.sql 실행
mysql -u root -p comeet < src/main/resources/schema.sql
```

### 3. application.yaml 수정

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/comeet?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: your_username  # 본인의 MySQL 사용자명으로 변경
    password: your_password  # 본인의 MySQL 비밀번호로 변경
```

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/backend/
│   │   ├── domain/          # Entity/Domain 클래스
│   │   │   └── User.java
│   │   ├── mapper/          # MyBatis Mapper 인터페이스
│   │   │   └── UserMapper.java
│   │   └── service/         # 비즈니스 로직 (직접 생성)
│   └── resources/
│       ├── mapper/          # MyBatis Mapper XML
│       │   └── UserMapper.xml
│       ├── application.yaml
│       ├── mybatis-config.xml
│       └── schema.sql
└── test/
    └── java/com/backend/
        └── mapper/          # Mapper 테스트
```

## 사용 방법

### 1. Service 계층에서 Mapper 사용

```java
package com.backend.service;

import com.backend.domain.User;
import com.backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userMapper.findById(userId);
    }
    
    @Transactional
    public User createUser(User user) {
        userMapper.insert(user);
        // insert 후 user.getUserId()로 생성된 ID 접근 가능
        return user;
    }
    
    @Transactional
    public void updateUser(User user) {
        userMapper.update(user);
    }
    
    @Transactional
    public void deleteUser(Long userId) {
        userMapper.delete(userId);
    }
}
```

### 2. Controller에서 Service 사용

```java
package com.backend.controller;

import com.backend.domain.User;
import com.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }
}
```

## Best Practices

### 1. Mapper 작성 규칙

✅ **DO:**
- XML 파일의 namespace는 Mapper 인터페이스의 전체 경로와 일치
- 메서드명(id)은 명확하고 일관된 네이밍 사용
- 재사용 가능한 SQL은 `<sql>` 태그로 분리
- 동적 SQL은 `<if>`, `<choose>`, `<foreach>` 활용
- 파라미터가 2개 이상일 때 `@Param` 어노테이션 사용

❌ **DON'T:**
- SELECT * 사용 (필요한 컬럼만 명시)
- 하드코딩된 값 사용 (파라미터로 전달)
- 복잡한 비즈니스 로직을 SQL에 포함

### 2. 트랜잭션 관리

```java
@Transactional(readOnly = true)  // 조회 메서드
public User getUser(Long id) { ... }

@Transactional  // CUD 메서드
public void updateUser(User user) { ... }
```

### 3. 페이징 처리

```xml
<!-- Mapper XML -->
<select id="findUsersWithPaging" parameterType="map" resultType="User">
    SELECT * FROM users
    WHERE active = true
    ORDER BY created_at DESC
    LIMIT #{limit} OFFSET #{offset}
</select>
```

```java
// Mapper Interface
List<User> findUsersWithPaging(@Param("limit") int limit, @Param("offset") int offset);
```

### 4. 동적 SQL 활용

```xml
<select id="searchUsers" parameterType="map" resultType="User">
    SELECT * FROM users
    <where>
        <if test="username != null and username != ''">
            AND username LIKE CONCAT('%', #{username}, '%')
        </if>
        <if test="email != null and email != ''">
            AND email = #{email}
        </if>
    </where>
</select>
```

## 트러블슈팅

### 문제 1: Mapper를 찾을 수 없음

```
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)
```

**해결방법:**
1. Mapper XML의 namespace가 인터페이스 경로와 일치하는지 확인
2. XML의 id가 메서드명과 일치하는지 확인
3. `mapper-locations` 설정 확인
4. 빌드 후 target/classes/mapper 폴더에 XML이 있는지 확인

### 문제 2: 데이터베이스 연결 실패

```
com.mysql.cj.jdbc.exceptions.CommunicationsException
```

**해결방법:**
1. MySQL 서비스 실행 확인
2. application.yaml의 연결 정보 확인
3. 방화벽 설정 확인
4. MySQL 드라이버 의존성 확인

### 문제 3: 한글 깨짐

**해결방법:**
1. DB 문자셋 확인: `utf8mb4`
2. JDBC URL에 `characterEncoding=UTF-8` 추가
3. application.yaml에서 인코딩 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/comeet?characterEncoding=UTF-8
```

### 문제 4: SQL 로그가 보이지 않음

**해결방법:**
application.yaml에서 로깅 레벨 조정:

```yaml
logging:
  level:
    com.backend.mapper: DEBUG
```

## 추가 참고사항

### MySQL 의존성 확인

build.gradle에 MySQL 드라이버가 없다면 추가:

```gradle
dependencies {
    implementation 'com.mysql:mysql-connector-j:8.2.0'
}
```

### 개발/운영 환경 분리

```yaml
# application-dev.yaml (개발)
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/comeet_dev

# application-prod.yaml (운영)
spring:
  datasource:
    url: jdbc:mysql://production-server:3306/comeet_prod
```

실행 시: `--spring.profiles.active=dev` 또는 `prod`

## 다음 단계

1. ✅ Service 계층 작성
2. ✅ Controller 작성
3. ✅ 테스트 코드 작성
4. ✅ 예외 처리 추가
5. ✅ 로깅 설정
6. ✅ API 문서화 (Swagger 등)

## 참고 문서

- [MyBatis 공식 문서](https://mybatis.org/mybatis-3/)
- [Spring Boot MyBatis](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
- [MyBatis Dynamic SQL](https://mybatis.org/mybatis-3/dynamic-sql.html)
