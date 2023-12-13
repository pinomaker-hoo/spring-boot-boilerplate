-- payhere.tb_log definition

CREATE TABLE `tb_log`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) NOT NULL COMMENT '생성 시간',
    `log`          varchar(50) NOT NULL COMMENT '로그 내용',
    `log_data`     text COMMENT '로그 데이터',
    `log_type`     tinyint(4) NOT NULL COMMENT '로그 종류',
    `user_id`      int(11) NOT NULL COMMENT '유저 ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


-- payhere.tb_user definition

CREATE TABLE `tb_user`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `created_date`  datetime(6) NOT NULL COMMENT '생성 시간',
    `deleted_date`  datetime(6) DEFAULT NULL COMMENT '삭제 시간',
    `modified_date` datetime(6) NOT NULL COMMENT '수정 시간',
    `password`      varchar(150) NOT NULL COMMENT '비밀번호',
    `phone_number`  varchar(30)  NOT NULL COMMENT '전화번호',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_qi5yr54j76lu2meatpwefocym` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


-- payhere.tb_product_category definition

CREATE TABLE `tb_product_category`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `created_date`  datetime(6) NOT NULL COMMENT '생성 시간',
    `deleted_date`  datetime(6) DEFAULT NULL COMMENT '삭제 시간',
    `modified_date` datetime(6) NOT NULL COMMENT '수정 시간',
    `created_id`    int(11) NOT NULL COMMENT '생성자 ID',
    `del_yn`        char(1)     NOT NULL COMMENT '삭제 여부',
    `deleted_id`    int(11) DEFAULT NULL COMMENT '삭제자 ID',
    `expose_yn`     char(1)     NOT NULL COMMENT '노출 여부',
    `modified_id`   int(11) NOT NULL COMMENT '수정자 ID',
    `name`          varchar(30) NOT NULL COMMENT '카테고리 이름',
    `user_id`       int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY             `FKspf3j318ds5ba6ar7spnoo7q1` (`user_id`),
    CONSTRAINT `FKspf3j318ds5ba6ar7spnoo7q1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- payhere.tb_product definition

CREATE TABLE `tb_product`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date`    datetime(6) NOT NULL COMMENT '생성 시간',
    `deleted_date`    datetime(6) DEFAULT NULL COMMENT '삭제 시간',
    `modified_date`   datetime(6) NOT NULL COMMENT '수정 시간',
    `code`            varchar(30) NOT NULL COMMENT '상품 코드(바코드)',
    `cost`            int(11) NOT NULL COMMENT '원가',
    `created_id`      int(11) NOT NULL COMMENT '생성자 ID',
    `del_yn`          char(1)     NOT NULL COMMENT '삭제 여부',
    `deleted_id`      int(11) DEFAULT NULL COMMENT '삭제자 ID',
    `expiration_date` datetime(6) NOT NULL COMMENT '유통 기한',
    `expose_yn`       char(1)     NOT NULL COMMENT '노출 여부',
    `modified_id`     int(11) NOT NULL COMMENT '수정자 ID',
    `name`            varchar(20) NOT NULL COMMENT '상품 이름',
    `name_consonant`  varchar(20) NOT NULL COMMENT '상품 이름 초성',
    `price`           int(11) NOT NULL COMMENT '가격',
    `size`            tinyint(4) NOT NULL COMMENT '사이즈',
    `sold_out_yn`     char(1)     NOT NULL COMMENT '매진 여부',
    `category_id`     int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY               `FKc2sdxynfmfpo2ne9phpyew0bn` (`category_id`),
    CONSTRAINT `FKc2sdxynfmfpo2ne9phpyew0bn` FOREIGN KEY (`category_id`) REFERENCES `tb_product_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;