# Zoopick Server

- Redis 서비스가 로컬에서 실행 중이어야 합니다.
-

### 환경변수

```bash .env
# Optional
# default: jdbc:postgresql://mir.lalaalal.com:5432/zoopick
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/zoopick

# Mandatory
SPRING_DATASOURCE_PASSWORD=password
SPRING_DATASOURCE_USERNAME=username
FIREBASE_ACCOUNT_KEY_PATH=/path/to/firebase-adminsdk.json
SPRING_MAIL_USERNAME=example@example.com
SPRING_MAIL_PASSWORD=password
```

### 빌드

```bash
./mvnw clean package
```

### 실행

```bash
# source .env
cd target
java -jar zoopick-server-x.x.x.jar
```