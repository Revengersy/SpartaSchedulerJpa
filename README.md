# Sparta Scheduler JPA 📅

Spring Boot 기반의 스케줄 관리 및 사용자 인증 기능을 제공하는 애플리케이션입니다. JPA를 활용하여 데이터를 관리하며, 다양한 컴포넌트로 구성되어 있습니다.

## 목차
### 설정
### 컨트롤러
### DTO
### 엔티티
### 필터
### 레포지토리
### 서비스

## 설정 ⚙️
### PasswordEncoder
sparta.sparta_scheduler_jpa.config.PasswordEncoder에 위치하며, BCrypt를 사용해 비밀번호를 암호화하고 검증하는 컴포넌트입니다.

```java
public String encode(String rawPassword) { /* 암호화 로직 */ }
public boolean matches(String rawPassword, String encodedPassword) { /* 검증 로직 */ }
```

### WebConfig

sparta.sparta_scheduler_jpa.config.WebConfig에 위치하며, 커스텀 필터를 등록하는 설정입니다.
CustomFilter: 모든 URL 패턴에 1순위로 적용됩니다.
LoginFilter: 모든 URL 패턴에 2순위로 적용됩니다.

```java
@Bean
public FilterRegistrationBean customFilter() { /*...*/ }
@Bean
public FilterRegistrationBean loginFilter() { /*...*/ }
```

## 컨트롤러 🎮
ScheduleController
sparta.sparta_scheduler_jpa.controller.ScheduleController에 위치하며, 스케줄 관련 REST API를 제공합니다.

엔드포인트:
POST /schedules: 새로운 스케줄 생성
GET /schedules: 모든 스케줄 조회
GET /schedules/{id}: ID로 스케줄 조회
GET /schedules/writer/{writerName}: 작성자명으로 스케줄 조회
GET /schedules/date/{date}: 날짜로 스케줄 조회
PUT /schedules/{id}: 스케줄 업데이트
PATCH /schedules/{id}: 스케줄 작성자명 업데이트
DELETE /schedules/{id}: 스케줄 삭제


### SessionHomeController
세션 관리를 담당하며, 홈 화면 및 로그인 리다이렉션을 처리합니다.

엔드포인트:
GET /: session-home으로 리다이렉트
GET /session-home: 세션 홈 페이지 표시


### SessionLoginController
사용자 로그인 및 로그아웃 세션을 관리합니다.

엔드포인트:
POST /session-login: 사용자 로그인
POST /session-logout: 사용자 로그아웃


### UserController
사용자 관리 작업을 처리합니다.

엔드포인트:
POST /users/signup: 새로운 사용자 등록
GET /users/{id}: ID로 사용자 조회
GET /users: 모든 사용자 조회
PUT /users/{id}: 사용자 정보 업데이트
DELETE /users/{id}: 사용자 삭제


## DTO 📋
### LoginRequestDto
로그인 요청 DTO, username과 password를 포함
### ScheduleRequestDto
스케줄 요청 DTO, task, username, password 포함
### ScheduleResponseDto
스케줄 응답 DTO, 스케줄 세부 정보를 캡슐화
### SignupRequestDto
회원가입 요청 DTO, username, password, age, email 포함
### SignupResponseDto
회원가입 응답 DTO
### UserResponseDto
사용자 정보 응답 DTO

## 엔티티 🏢
### BaseEntity
엔티티의 추상화된 기반 클래스, 생성일 및 수정일 포함

### Schedule
스케줄을 나타내며, User 엔티티와 연결

### User
사용자 엔티티, 사용자명, 비밀번호, 나이, 이메일 속성 포함

## 필터 🚦
### CustomFilter
요청 URI를 로깅하는 필터
### LoginFilter
인증된 세션을 확인하고, 인증되지 않은 요청을 리다이렉트

## 레포지토리 📂
### ScheduleRepository
Schedule 엔티티 관리, 사용자 또는 날짜 범위별 스케줄 검색 메소드 제공
### UserRepository
User 엔티티 관리, 사용자 조회 메소드 제공

## 서비스 💼
### ScheduleService
스케줄 관련 비즈니스 로직 제공, 생성, 검색, 업데이트, 삭제 기능 포함
### UserService
사용자 관련 작업 관리, 회원가입 및 인증 기능 포함
