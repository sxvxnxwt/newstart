FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Gradle Wrapper 및 소스 복사
COPY gradlew ./
COPY gradle gradle
COPY build.gradle ./
COPY src src

# 실행권한 부여(필요시)
RUN chmod +x gradlew

# 종속성 캐싱(선택)
# RUN ./gradlew dependencies --no-daemon || return 0

# 빌드
RUN ./gradlew clean bootJar --no-daemon

# 런타임 이미지
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]