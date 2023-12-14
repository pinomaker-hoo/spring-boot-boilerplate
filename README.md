# Payhere 백엔드 개발자 과제 전형 서버

### 구현 목표

```agsl
사장님은 카페를 운영하는 사장님입니다.
사장님은 상품을 등록해서 가게를 운영하고 싶습니다.
아래의 요구사항을 만족하는 DB 테이블과 REST API를 만들어주세요.
```

### 이런 부분을 신경 썼어요!
```agsl
1. 운영에 있어서 추적을 쉽게 하기 위해서 로그 테이블에 데이터를 쌓고, 필요한 데이터를 logging 했습니다.
2. 가독성이 좋은 코드를 작성하기 위해 노력했습니다.
3. 보안을 고려하여 전화번호를 암호화 하여 저장했습니다.
4. Swagger를 이용하여 프론트와의 협업을 신경 썼습니다.
```


### 사용한 기술

```agsl
Spring boot 3, JPA, MySQL 5.7, JDK 17
```

### 실행 방법

```agsl
1. docker-compose up -d -> docker compose를 이용한 DB container 실행(서버도 같이 실행되자만 종료됨)
2. sql/payhere-ddl.sql를 이용하여 테이블 생성
3. docker-compose up -d
```

### ERD
![payhere (1)](https://github.com/pinomaker-hoo/payhere-backend/assets/56928532/30d54d5a-04bf-4df8-acb3-0d0eb04d1199)
<br />

### API LIST
```agsl
# User

1. 회원가입, POST : /api/v1/user
2. 로그인, POST : /api/v1/user/login
3. 회원가입, POST : /api/v1/user/reissue

# Product Category

1. 카테고리 생성, POST : /api/v1/product/category
2. 카테고리 수정, PUT : /api/v1/product/category
3. 카테고리 삭제, DELETE : /api/v1/product/category
4. 카테고리 리스트 조회, GET : /api/v1/product/category
5. 카테고리 단일 조회, GET : /api/v1/product/category/{id}

# Product

1. 제품 생성, POST : /api/v1/product
2. 제품 수정, PUT : /api/v1/product
3. 제품 삭제, DELETE : /api/v1/product
4. 제품 리스트 조회, GET : /api/v1/produc
5. 제품 단일 조회, GET : /api/v1/product/{id}

# Product

1. 로그 조회, GET : /api/v1/log
```

### 폴더 구조
```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂payhere
 ┃ ┃ ┃ ┗ 📂cafeproduct
 ┃ ┃ ┃ ┃ ┣ 📂api -> Domain 기준의 폴더 구조
 ┃ ┃ ┃ ┃ ┃ ┣ 📂product
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain -> Entity 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂event
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto -> dto 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂vo -> vo 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository -> repository 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service -> service 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂ui -> controller 파일 모음
 ┃ ┃ ┃ ┃ ┃ ┣ 📂productCategory
 ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┣ 📂log
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
 ┃ ┃ ┃ ┃ ┗ 📜CafeProductApplication.java
 ┃ ┗ 📂resources
 ┗ 📂test
```
