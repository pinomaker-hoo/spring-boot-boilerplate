# Spring boot Boilerplate

### 사용한 기술

```agsl
Spring boot 3, JPA, Maria DB, JDK 17
```

### 실행 방법

```agsl
docker-compose up -d
```

### API LIST
```agsl
# User

1. 회원가입, POST : /api/v1/user
2. 로그인, POST : /api/v1/user/login
3. 토큰 재발급, POST : /api/v1/user/reissue

```

### 폴더 구조
```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂payhere
 ┃ ┃ ┃ ┗ 📂cafeproduct
 ┃ ┃ ┃ ┃ ┣ 📂api -> Domain 기준의 폴더 구조
 ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain -> Entity 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂event
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto -> dto 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo -> vo 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository -> repository 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service -> service 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂ui -> controller 파일 모음
 ┃ ┃ ┃ ┃ ┣ 📂global
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config -> 전역 설정 관련 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂domain -> 공통 Entity 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto -> 공통 dto 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂enums -> 공통 enum 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception -> exception 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂handler -> 전역 이벤트 핸들러 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂interceptor -> 인터셉터 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂jwt -> JWT 관련 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┗ 📂utils -> Utils 함수
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂encoder -> 암호화
 ┃ ┃ ┃ ┃ ┗ 📜MarsApplication.java
 ┃ ┗ 📂resources
 ┗ 📂test
```
