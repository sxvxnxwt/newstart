# 📰 newstart 백엔드

> 본 레포는 팀 프로젝트에서 **직접 구현한 백엔드 기능만 정리한 포트폴리오용**입니다.

## 📁 프로젝트 개요

- **프로젝트명**: newstart (1분 요약 뉴스레터 서비스)
- **설명**: 네이버 뉴스 API를 활용해 최신 헤드라인을 수집하고, AI 요약 모델을 통해 기사 내용을 간결하게 제공하는 웹 서비스
- **진행기간**: 2024.11.25 ~ 2024.12.18
- **참여인원**: 4명 (프론트 2, 백엔드 1, AI 1)
- **개발방식**: 협업 (GitHub + Notion)
- **역할**: 백엔드 (하단 구현 기능 요약 참고)

## 🛠 사용 기술 스택

- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **Build Tool**: Gradle
- **Cloud**: GCP (Google Cloud Platform)
- **Auth & Security**: OAuth2.0, Spring Security
- **Version Control**: Git & GitHub
- **Others**: REST API 설계, FastAPI 연동

## ☑️ 구현 기능 요약

| 기능 구분      | 상세 내용 |
|----------------|-----------|
| 사용자 인증 | 회원가입, 로그인/로그아웃, OAuth2.0 기반 소셜 로그인, 회원 탈퇴 |
| 기사 요약 제공 | FastAPI로 구현된 AI 모델에 요청을 보내 기사 요약 결과를 수신, 가공 후 전달 |
| 기사 검색   | 키워드 기반 기사 검색 시 FastAPI 호출 → 결과 가공 후 응답 |
| 북마크 기능 | 기사 북마크 추가/삭제 기능 사용자별 북마크 목록 조회 API 제공 |
