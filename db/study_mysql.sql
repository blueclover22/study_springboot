# 코드그룹
CREATE TABLE code_group
(
    group_code VARCHAR(3) NOT NULL,
    group_name VARCHAR(30) NOT NULL ,
    reg_date DATETIME,
    upd_date DATETIME,
    use_yn VARCHAR(1),
    PRIMARY KEY (group_code)
);

ALTER TABLE code_group COMMENT '공통코드 그룹 테이블';
ALTER TABLE code_group MODIFY COLUMN group_code VARCHAR(3) NOT NULL COMMENT '그룹코드';
ALTER TABLE code_group MODIFY COLUMN group_name VARCHAR(30) NOT NULL COMMENT '그룹명';
ALTER TABLE code_group MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE code_group MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';
ALTER TABLE code_group MODIFY COLUMN use_yn VARCHAR(1) COMMENT '사용여부';

# 코드상세
CREATE TABLE code_detail
(
    group_code VARCHAR(3) NOT NULL ,
    code_value VARCHAR(3) NOT NULL ,
    code_name VARCHAR(30) NOT NULL ,
    sort_seq INTEGER NOT NULL ,
    reg_date DATETIME,
    upd_date DATETIME,
    use_yn VARCHAR(1),
    PRIMARY KEY (group_code, code_value)
);

ALTER TABLE code_detail COMMENT '공통코드 상세 테이블';
ALTER TABLE code_detail MODIFY COLUMN group_code VARCHAR(3) NOT NULL COMMENT '그룹코드';
ALTER TABLE code_detail MODIFY COLUMN code_value VARCHAR(3) NOT NULL COMMENT '코드값';
ALTER TABLE code_detail MODIFY COLUMN code_name VARCHAR(30) NOT NULL COMMENT '코드명';
ALTER TABLE code_detail MODIFY COLUMN sort_seq INTEGER NOT NULL COMMENT '정렬순서';
ALTER TABLE code_detail MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE code_detail MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';
ALTER TABLE code_detail MODIFY COLUMN use_yn VARCHAR(1) COMMENT '사용여부';

ALTER TABLE code_detail
    ADD CONSTRAINT fk_code_detail_group_code
        FOREIGN KEY (group_code) REFERENCES code_group(group_code);

# 회원
CREATE TABLE member
(
    user_no BIGINT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(50) NOT NULL ,
    user_pw VARCHAR(200) NOT NULL ,
    user_name VARCHAR(100) NOT NULL ,
    job VARCHAR(3) NOT NULL ,
    coin INTEGER NOT NULL ,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (user_no)
);

ALTER TABLE member COMMENT '회원 정보 테이블';
ALTER TABLE member MODIFY COLUMN user_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '사용자번호';
ALTER TABLE member MODIFY COLUMN user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID';
ALTER TABLE member MODIFY COLUMN user_pw VARCHAR(200) NOT NULL COMMENT '사용자 비밀번호';
ALTER TABLE member MODIFY COLUMN user_name VARCHAR(100) NOT NULL COMMENT '사용자명';
ALTER TABLE member MODIFY COLUMN job VARCHAR(3) NOT NULL COMMENT '직업';
ALTER TABLE member MODIFY COLUMN coin INTEGER NOT NULL COMMENT '코인';
ALTER TABLE member MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE member MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 회원권한
CREATE TABLE member_auth
(
    user_auth_no BIGINT NOT NULL AUTO_INCREMENT,
    user_no BIGINT,
    auth VARCHAR(50),
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (user_auth_no)
);

ALTER TABLE member_auth COMMENT '회원 권한 테이블';
ALTER TABLE member_auth MODIFY COLUMN user_auth_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '사용자권한번호';
ALTER TABLE member_auth MODIFY COLUMN user_no BIGINT COMMENT '사용자번호';
ALTER TABLE member_auth MODIFY COLUMN auth VARCHAR(50) COMMENT '권한';
ALTER TABLE member_auth MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE member_auth MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

ALTER TABLE member_auth
    ADD CONSTRAINT fk_member_auth_user_no
        FOREIGN KEY (user_no) REFERENCES member(user_no);

# 게시판
CREATE TABLE board
(
    board_no BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL ,
    writer VARCHAR(50) NOT NULL ,
    content LONGTEXT,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (board_no)
);

ALTER TABLE board COMMENT '게시판 테이블';
ALTER TABLE board MODIFY COLUMN board_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '게시글번호';
ALTER TABLE board MODIFY COLUMN title VARCHAR(200) NOT NULL COMMENT '제목';
ALTER TABLE board MODIFY COLUMN writer VARCHAR(50) NOT NULL COMMENT '작성자';
ALTER TABLE board MODIFY COLUMN content LONGTEXT COMMENT '내용';
ALTER TABLE board MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE board MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 공지사항
CREATE TABLE notice
(
    notice_no BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL ,
    content LONGTEXT,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (notice_no)
);

ALTER TABLE notice COMMENT '공지사항 테이블';
ALTER TABLE notice MODIFY COLUMN notice_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '공지번호';
ALTER TABLE notice MODIFY COLUMN title VARCHAR(200) NOT NULL COMMENT '제목';
ALTER TABLE notice MODIFY COLUMN content LONGTEXT COMMENT '내용';
ALTER TABLE notice MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE notice MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 상품
CREATE TABLE item
(
    item_id BIGINT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(50) NOT NULL ,
    price INTEGER,
    description VARCHAR(250),
    picture_url VARCHAR(200),
    preview_url VARCHAR(200),
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (item_id)
);

ALTER TABLE item COMMENT '상품 정보 테이블';
ALTER TABLE item MODIFY COLUMN item_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품ID';
ALTER TABLE item MODIFY COLUMN item_name VARCHAR(50) NOT NULL COMMENT '상품명';
ALTER TABLE item MODIFY COLUMN price INTEGER COMMENT '가격 (코인)';
ALTER TABLE item MODIFY COLUMN description VARCHAR(250) COMMENT '상품설명';
ALTER TABLE item MODIFY COLUMN picture_url VARCHAR(200) COMMENT '이미지 URL';
ALTER TABLE item MODIFY COLUMN preview_url VARCHAR(200) COMMENT '미리보기 URL';
ALTER TABLE item MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE item MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 사용자 상품
CREATE TABLE user_item
(
    user_item_no BIGINT NOT NULL AUTO_INCREMENT,
    user_no BIGINT,
    item_id BIGINT,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (user_item_no)
);

ALTER TABLE user_item COMMENT '사용자 상품 테이블';
ALTER TABLE user_item MODIFY COLUMN user_item_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '사용자상품번호';
ALTER TABLE user_item MODIFY COLUMN user_no BIGINT COMMENT '사용자번호';
ALTER TABLE user_item MODIFY COLUMN item_id BIGINT COMMENT '상품ID';
ALTER TABLE user_item MODIFY COLUMN reg_date DATETIME COMMENT '구매일시';
ALTER TABLE user_item MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 자료실
CREATE TABLE pds
(
    item_id BIGINT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255),
    view_cnt INTEGER,
    description VARCHAR(255),
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (item_id)
);

ALTER TABLE pds COMMENT '자료실 테이블';
ALTER TABLE pds MODIFY COLUMN item_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '자료ID';
ALTER TABLE pds MODIFY COLUMN item_name VARCHAR(255) COMMENT '자료명';
ALTER TABLE pds MODIFY COLUMN view_cnt INTEGER COMMENT '조회수';
ALTER TABLE pds MODIFY COLUMN description VARCHAR(255) COMMENT '자료설명';
ALTER TABLE pds MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE pds MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 자료실 첨부 파일
CREATE TABLE pds_file
(
    pds_file_id BIGINT NOT NULL AUTO_INCREMENT,
    item_id BIGINT,
    full_name VARCHAR(200),
    down_cnt INTEGER,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (pds_file_id)
);

ALTER TABLE pds_file COMMENT '자료실 첨부파일 테이블';
ALTER TABLE pds_file MODIFY COLUMN pds_file_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '파일ID';
ALTER TABLE pds_file MODIFY COLUMN item_id BIGINT COMMENT '자료ID';
ALTER TABLE pds_file MODIFY COLUMN full_name VARCHAR(200) COMMENT '파일 전체명';
ALTER TABLE pds_file MODIFY COLUMN down_cnt INTEGER COMMENT '다운로드 횟수';
ALTER TABLE pds_file MODIFY COLUMN reg_date DATETIME COMMENT '등록일시';
ALTER TABLE pds_file MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

ALTER TABLE pds_file
    ADD CONSTRAINT fk_pds_file_item_id
        FOREIGN KEY (item_id) REFERENCES pds (item_id);

# 충전 내역
CREATE TABLE charge_coin_history
(
    history_no BIGINT NOT NULL AUTO_INCREMENT,
    user_no BIGINT,
    amount INTEGER NOT NULL ,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (history_no)
);

ALTER TABLE charge_coin_history COMMENT '코인 충전 내역 테이블';
ALTER TABLE charge_coin_history MODIFY COLUMN history_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '이력번호';
ALTER TABLE charge_coin_history MODIFY COLUMN user_no BIGINT COMMENT '사용자번호';
ALTER TABLE charge_coin_history MODIFY COLUMN amount INTEGER NOT NULL COMMENT '충전금액';
ALTER TABLE charge_coin_history MODIFY COLUMN reg_date DATETIME COMMENT '충전일시';
ALTER TABLE charge_coin_history MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 지급 내역
CREATE TABLE pay_coin_history
(
    history_no BIGINT NOT NULL AUTO_INCREMENT,
    user_no BIGINT,
    item_id BIGINT,
    item_name VARCHAR(255),
    amount INTEGER NOT NULL ,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (history_no)
);

ALTER TABLE pay_coin_history COMMENT '코인 지급 내역 테이블';
ALTER TABLE pay_coin_history MODIFY COLUMN history_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '이력번호';
ALTER TABLE pay_coin_history MODIFY COLUMN user_no BIGINT COMMENT '사용자번호';
ALTER TABLE pay_coin_history MODIFY COLUMN item_id BIGINT COMMENT '상품ID';
ALTER TABLE pay_coin_history MODIFY COLUMN item_name VARCHAR(255) COMMENT '상품명';
ALTER TABLE pay_coin_history MODIFY COLUMN amount INTEGER NOT NULL COMMENT '사용금액';
ALTER TABLE pay_coin_history MODIFY COLUMN reg_date DATETIME COMMENT '사용일시';
ALTER TABLE pay_coin_history MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 접근 로그
CREATE TABLE access_log
(
    log_no BIGINT NOT NULL AUTO_INCREMENT,
    request_url VARCHAR(200) NOT NULL ,
    class_name VARCHAR(100) NOT NULL ,
    class_simple_name VARCHAR(50) NOT NULL ,
    method_name VARCHAR(100) NOT NULL ,
    remote_addr VARCHAR(50) NOT NULL ,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (log_no)
);

ALTER TABLE access_log COMMENT '시스템 접근 로그 테이블';
ALTER TABLE access_log MODIFY COLUMN log_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '로그번호';
ALTER TABLE access_log MODIFY COLUMN request_url VARCHAR(200) NOT NULL COMMENT '요청 URL';
ALTER TABLE access_log MODIFY COLUMN class_name VARCHAR(100) NOT NULL COMMENT '컨트롤러 클래스명';
ALTER TABLE access_log MODIFY COLUMN class_simple_name VARCHAR(50) NOT NULL COMMENT '컨트롤러 클래스명 (요약)';
ALTER TABLE access_log MODIFY COLUMN method_name VARCHAR(100) NOT NULL COMMENT '메서드명';
ALTER TABLE access_log MODIFY COLUMN remote_addr VARCHAR(50) NOT NULL COMMENT '클라이언트 IP주소';
ALTER TABLE access_log MODIFY COLUMN reg_date DATETIME COMMENT '접근일시';
ALTER TABLE access_log MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';

# 서비스 성능 로그
CREATE TABLE performance_log
(
    log_no BIGINT NOT NULL AUTO_INCREMENT,
    signature_name VARCHAR(50) NOT NULL ,
    signature_type_name VARCHAR(100) NOT NULL ,
    duration_time BIGINT,
    reg_date DATETIME,
    upd_date DATETIME,
    PRIMARY KEY (log_no)
);

ALTER TABLE performance_log COMMENT '서비스 성능 로그 테이블';
ALTER TABLE performance_log MODIFY COLUMN log_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '로그번호';
ALTER TABLE performance_log MODIFY COLUMN signature_name VARCHAR(50) NOT NULL COMMENT '시그니처명';
ALTER TABLE performance_log MODIFY COLUMN signature_type_name VARCHAR(100) NOT NULL COMMENT '시그니처 타입명';
ALTER TABLE performance_log MODIFY COLUMN duration_time BIGINT COMMENT '실행시간';
ALTER TABLE performance_log MODIFY COLUMN reg_date DATETIME COMMENT '실행일시';
ALTER TABLE performance_log MODIFY COLUMN upd_date DATETIME COMMENT '수정일시';
