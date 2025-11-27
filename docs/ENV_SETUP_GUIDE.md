# 환경 변수 설정 가이드

이 문서는 Comeet 프로젝트에서 환경 변수(.env)를 사용하여 설정을 관리하는 방법을 설명합니다.

## 📋 목차

1. [왜 환경 변수를 사용하나요?](#왜-환경-변수를-사용하나요)
2. [.env 파일 설정](#env-파일-설정)
3. [사용 방법](#사용-방법)
4. [환경별 설정](#환경별-설정)
5. [보안 주의사항](#보안-주의사항)
6. [트러블슈팅](#트러블슈팅)

## 왜 환경 변수를 사용하나요?

### 장점

1. **보안 강화**
   - 민감한 정보(비밀번호, API 키 등)를 코드에서 분리
   - Git에 커밋되지 않도록 관리

2. **환경별 설정 관리**
   - 개발, 테스트, 운영 환경마다 다른 설정 사용
   - 코드 수정 없이 설정 변경 가능

3. **팀 협업 용이**
   - 각 개발자가 로컬 환경에 맞게 설정 가능
   - `.env.example`로 필요한 설정 항목 공유

4. **배포 자동화**
   - CI/CD 파이프라인에서 환경 변수 주입
   - 클라우드 환경 변수 서비스 활용 가능

## .env 파일 설정

### 1. .env 파일 생성

프로젝트 루트 디렉토리에서:

```bash
# .env.example을 복사하여 .env 파일 생성
cp .env.example .env
```

### 2. .env 파일 수정

`.env` 파일을 열어 실제 값으로 수정:

```properties
# 데이터베이스 설정
DB_DRIVER=com.mysql.cj.jdbc.Driver
DB_URL=jdbc:mysql://localhost:3306/comeet?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
DB_USERNAME=root
DB_PASSWORD=your_actual_password  # ← 실제 비밀번호로 변경

# 애플리케이션 설정
APP_NAME=Comeet
APP_VERSION=1.0.0
APP_PROFILE=dev

# 서버 설정
SERVER_PORT=8080

# JWT 설정
JWT_SECRET=your-super-secret-key-change-this-minimum-256-bits
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
```

### 3. Git 보안 확인

`.gitignore`에 `.env`가 포함되어 있는지 확인:

```bash
# .gitignore 확인
cat .gitignore | grep .env
```

출력 예시:
```
.env
.env.local
.env.*.local
```

## 사용 방법

### 1. application.yaml에서 참조

환경 변수는 `${변수명:기본값}` 형식으로 참조:

```yaml
spring:
  datasource:
    url: ${DB_URL:jdbc:mysql://localhost:3306/comeet}
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:password}
```

- `${DB_URL}`: .env 파일의 DB_URL 값 사용
- `:jdbc:mysql://...`: .env에 없을 경우 기본값 사용

### 2. Java 코드에서 사용

#### 방법 1: @Value 어노테이션

```java
@Component
public class DatabaseService {
    
    @Value("${DB_URL}")
    private String databaseUrl;
    
    @Value("${JWT_SECRET}")
    private String jwtSecret;
    
    public void printConfig() {
        System.out.println("Database URL: " + databaseUrl);
    }
}
```

#### 방법 2: AppProperties 클래스 (권장)

```java
@Service
public class UserService {
    
    private final AppProperties appProperties;
    
    public UserService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    
    public void someMethod() {
        String appName = appProperties.getName();
        String version = appProperties.getVersion();
    }
}
```

#### 방법 3: Environment 객체

```java
@Service
public class ConfigService {
    
    private final Environment environment;
    
    public ConfigService(Environment environment) {
        this.environment = environment;
    }
    
    public String getDatabaseUrl() {
        return environment.getProperty("DB_URL");
    }
}
```

## 환경별 설정

### 개발 환경 (.env)

```properties
APP_PROFILE=dev
DB_URL=jdbc:mysql://localhost:3306/comeet_dev
LOG_LEVEL_ROOT=DEBUG
```

### 테스트 환경 (.env.test)

```properties
APP_PROFILE=test
DB_URL=jdbc:mysql://localhost:3306/comeet_test
LOG_LEVEL_ROOT=INFO
```

### 운영 환경 (.env.prod)

```properties
APP_PROFILE=prod
DB_URL=jdbc:mysql://production-server:3306/comeet_prod
LOG_LEVEL_ROOT=WARN
```

### 환경별 실행

```bash
# 개발 환경 (기본)
./gradlew bootRun

# 테스트 환경
cp .env.test .env
./gradlew bootRun

# 운영 환경
cp .env.prod .env
./gradlew bootRun
```

또는 Spring Profile 사용:

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## 보안 주의사항

### ⚠️ 절대 하지 말아야 할 것

1. **`.env` 파일을 Git에 커밋하지 마세요**
   ```bash
   # 잘못된 예
   git add .env  # ❌ 절대 금지!
   ```

2. **민감한 정보를 코드에 하드코딩하지 마세요**
   ```java
   // 잘못된 예
   String password = "admin123";  // ❌
   
   // 올바른 예
   @Value("${DB_PASSWORD}")
   private String password;  // ✅
   ```

3. **`.env` 파일을 공개 저장소에 업로드하지 마세요**

### ✅ 권장 사항

1. **`.env.example` 사용**
   - 필요한 환경 변수 목록을 템플릿으로 제공
   - 실제 값은 제외하고 Git에 커밋

2. **강력한 비밀번호 사용**
   ```properties
   # 약한 비밀번호 ❌
   DB_PASSWORD=1234
   
   # 강한 비밀번호 ✅
   DB_PASSWORD=aB3$xY9#mK2@pL7!
   ```

3. **JWT Secret 생성**
   ```bash
   # 256비트 랜덤 키 생성
   openssl rand -base64 32
   ```

4. **정기적인 비밀번호 변경**
   - 특히 운영 환경의 비밀번호는 주기적으로 변경

## 환경 변수 목록

### 필수 환경 변수

| 변수명 | 설명 | 기본값 | 예시 |
|--------|------|--------|------|
| `DB_URL` | 데이터베이스 URL | - | `jdbc:mysql://localhost:3306/comeet` |
| `DB_USERNAME` | DB 사용자명 | root | `comeet_user` |
| `DB_PASSWORD` | DB 비밀번호 | - | `your_password` |

### 선택적 환경 변수

| 변수명 | 설명 | 기본값 |
|--------|------|--------|
| `SERVER_PORT` | 서버 포트 | 8080 |
| `APP_PROFILE` | 실행 프로필 | dev |
| `LOG_LEVEL_ROOT` | 로그 레벨 | INFO |
| `DB_POOL_MAX_SIZE` | 최대 커넥션 수 | 10 |

## 트러블슈팅

### 문제 1: 환경 변수를 읽지 못함

**증상:**
```
Could not resolve placeholder 'DB_URL'
```

**해결방법:**
1. `.env` 파일이 프로젝트 루트에 있는지 확인
2. 파일 이름이 정확히 `.env`인지 확인 (`.env.txt` ❌)
3. 환경 변수명 철자 확인
4. 애플리케이션 재시작

### 문제 2: .env 파일이 로드되지 않음

**해결방법:**

1. **spring.factories 확인**
   ```
   src/main/resources/META-INF/spring.factories
   ```
   파일이 존재하는지 확인

2. **의존성 확인**
   ```gradle
   implementation 'io.github.cdimascio:dotenv-java:3.0.0'
   ```
   build.gradle에 추가되어 있는지 확인

3. **의존성 다운로드**
   ```bash
   ./gradlew clean build
   ```

### 문제 3: Git에 .env가 커밋됨

**해결방법:**

```bash
# Git 캐시에서 제거
git rm --cached .env

# .gitignore 확인
echo ".env" >> .gitignore

# 변경사항 커밋
git add .gitignore
git commit -m "Remove .env from git and update .gitignore"
```

### 문제 4: 다른 개발자와 .env 공유

**해결방법:**

절대 .env 파일을 직접 공유하지 마세요! 대신:

1. `.env.example` 파일 공유 (Git에 커밋)
2. Slack/Email로 실제 값만 별도 전달
3. 비밀번호 관리 도구 사용 (1Password, LastPass 등)

## Docker 환경에서 사용

### docker-compose.yml

```yaml
version: '3.8'
services:
  backend:
    build: .
    ports:
      - "${SERVER_PORT:-8080}:8080"
    environment:
      - DB_URL=${DB_URL}
      - DB_USERNAME=${DB_USERNAME}
      - DB_PASSWORD=${DB_PASSWORD}
    env_file:
      - .env
```

### 실행

```bash
docker-compose up
```

## 클라우드 배포 시

### AWS Elastic Beanstalk

```bash
# 환경 변수 설정
eb setenv DB_URL=jdbc:mysql://... DB_USERNAME=admin DB_PASSWORD=...
```

### Heroku

```bash
# 환경 변수 설정
heroku config:set DB_URL=jdbc:mysql://...
heroku config:set DB_USERNAME=admin
```

### Kubernetes

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  DB_URL: "jdbc:mysql://..."
---
apiVersion: v1
kind: Secret
metadata:
  name: app-secrets
type: Opaque
stringData:
  DB_PASSWORD: "your_password"
```

## 참고 자료

- [dotenv-java GitHub](https://github.com/cdimascio/dotenv-java)
- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [12 Factor App - Config](https://12factor.net/config)

## 다음 단계

1. ✅ `.env` 파일 생성 및 설정
2. ✅ 데이터베이스 연결 테스트
3. ✅ 환경별 설정 파일 준비
4. ✅ 팀원들과 `.env.example` 공유
5. ✅ CI/CD 파이프라인에 환경 변수 설정
