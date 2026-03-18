-- =====================
-- 코드그룹
-- =====================
CREATE TABLE code_group
(
    group_code VARCHAR(3)  NOT NULL,
    group_name VARCHAR(30) NOT NULL,
    reg_date   TIMESTAMP,
    upd_date   TIMESTAMP,
    use_yn     VARCHAR(1),
    PRIMARY KEY (group_code)
);

COMMENT ON TABLE code_group IS '공통코드 그룹 테이블';
COMMENT ON COLUMN code_group.group_code IS '그룹코드';
COMMENT ON COLUMN code_group.group_name IS '그룹명';
COMMENT ON COLUMN code_group.reg_date IS '등록일시';
COMMENT ON COLUMN code_group.upd_date IS '수정일시';
COMMENT ON COLUMN code_group.use_yn IS '사용여부';


-- =====================
-- 코드상세
-- =====================
CREATE TABLE code_detail
(
    group_code VARCHAR(3)  NOT NULL,
    code_value VARCHAR(3)  NOT NULL,
    code_name  VARCHAR(30) NOT NULL,
    sort_seq   INTEGER     NOT NULL,
    reg_date   TIMESTAMP,
    upd_date   TIMESTAMP,
    use_yn     VARCHAR(1),
    PRIMARY KEY (group_code, code_value)
);

COMMENT ON TABLE code_detail IS '공통코드 상세 테이블';
COMMENT ON COLUMN code_detail.group_code IS '그룹코드';
COMMENT ON COLUMN code_detail.code_value IS '코드값';
COMMENT ON COLUMN code_detail.code_name IS '코드명';
COMMENT ON COLUMN code_detail.sort_seq IS '정렬순서';
COMMENT ON COLUMN code_detail.reg_date IS '등록일시';
COMMENT ON COLUMN code_detail.upd_date IS '수정일시';
COMMENT ON COLUMN code_detail.use_yn IS '사용여부';

ALTER TABLE code_detail
    ADD CONSTRAINT fk_code_detail_group_code
        FOREIGN KEY (group_code) REFERENCES code_group (group_code);


-- =====================
-- 회원
-- =====================
CREATE TABLE member
(
    user_no   BIGSERIAL    NOT NULL,
    user_id   VARCHAR(50)  NOT NULL,
    user_pw   VARCHAR(200) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    job       VARCHAR(3)   NOT NULL,
    coin      INTEGER      NOT NULL,
    reg_date  TIMESTAMP,
    upd_date  TIMESTAMP,
    PRIMARY KEY (user_no)
);

COMMENT ON TABLE member IS '회원 정보 테이블';
COMMENT ON COLUMN member.user_no IS '사용자번호';
COMMENT ON COLUMN member.user_id IS '사용자 ID';
COMMENT ON COLUMN member.user_pw IS '사용자 비밀번호';
COMMENT ON COLUMN member.user_name IS '사용자명';
COMMENT ON COLUMN member.job IS '직업';
COMMENT ON COLUMN member.coin IS '코인';
COMMENT ON COLUMN member.reg_date IS '등록일시';
COMMENT ON COLUMN member.upd_date IS '수정일시';


-- =====================
-- 회원권한
-- =====================
CREATE TABLE member_auth
(
    user_auth_no BIGSERIAL   NOT NULL,
    user_no      BIGINT,
    auth         VARCHAR(50),
    reg_date     TIMESTAMP,
    upd_date     TIMESTAMP,
    PRIMARY KEY (user_auth_no)
);

COMMENT ON TABLE member_auth IS '회원 권한 테이블';
COMMENT ON COLUMN member_auth.user_auth_no IS '사용자권한번호';
COMMENT ON COLUMN member_auth.user_no IS '사용자번호';
COMMENT ON COLUMN member_auth.auth IS '권한';
COMMENT ON COLUMN member_auth.reg_date IS '등록일시';
COMMENT ON COLUMN member_auth.upd_date IS '수정일시';

ALTER TABLE member_auth
    ADD CONSTRAINT fk_member_auth_user_no
        FOREIGN KEY (user_no) REFERENCES member (user_no);


-- =====================
-- 게시판
-- =====================
CREATE TABLE board
(
    board_no BIGSERIAL    NOT NULL,
    title    VARCHAR(200) NOT NULL,
    writer   VARCHAR(50)  NOT NULL,
    content  TEXT,
    reg_date TIMESTAMP,
    upd_date TIMESTAMP,
    PRIMARY KEY (board_no)
);

COMMENT ON TABLE board IS '게시판 테이블';
COMMENT ON COLUMN board.board_no IS '게시글번호';
COMMENT ON COLUMN board.title IS '제목';
COMMENT ON COLUMN board.writer IS '작성자';
COMMENT ON COLUMN board.content IS '내용';
COMMENT ON COLUMN board.reg_date IS '등록일시';
COMMENT ON COLUMN board.upd_date IS '수정일시';


-- =====================
-- 공지사항
-- =====================
CREATE TABLE notice
(
    notice_no BIGSERIAL    NOT NULL,
    title     VARCHAR(200) NOT NULL,
    content   TEXT,
    reg_date  TIMESTAMP,
    upd_date  TIMESTAMP,
    PRIMARY KEY (notice_no)
);

COMMENT ON TABLE notice IS '공지사항 테이블';
COMMENT ON COLUMN notice.notice_no IS '공지번호';
COMMENT ON COLUMN notice.title IS '제목';
COMMENT ON COLUMN notice.content IS '내용';
COMMENT ON COLUMN notice.reg_date IS '등록일시';
COMMENT ON COLUMN notice.upd_date IS '수정일시';


-- =====================
-- 상품
-- =====================
CREATE TABLE item
(
    item_id     BIGSERIAL    NOT NULL,
    item_name   VARCHAR(50)  NOT NULL,
    price       INTEGER,
    description VARCHAR(250),
    picture_url VARCHAR(200),
    preview_url VARCHAR(200),
    reg_date    TIMESTAMP,
    upd_date    TIMESTAMP,
    PRIMARY KEY (item_id)
);

COMMENT ON TABLE item IS '상품 정보 테이블';
COMMENT ON COLUMN item.item_id IS '상품ID';
COMMENT ON COLUMN item.item_name IS '상품명';
COMMENT ON COLUMN item.price IS '가격 (코인)';
COMMENT ON COLUMN item.description IS '상품설명';
COMMENT ON COLUMN item.picture_url IS '이미지 URL';
COMMENT ON COLUMN item.preview_url IS '미리보기 URL';
COMMENT ON COLUMN item.reg_date IS '등록일시';
COMMENT ON COLUMN item.upd_date IS '수정일시';


-- =====================
-- 사용자 상품
-- =====================
CREATE TABLE user_item
(
    user_item_no BIGSERIAL NOT NULL,
    user_no      BIGINT,
    item_id      BIGINT,
    reg_date     TIMESTAMP,
    upd_date     TIMESTAMP,
    PRIMARY KEY (user_item_no)
);

COMMENT ON TABLE user_item IS '사용자 상품 테이블';
COMMENT ON COLUMN user_item.user_item_no IS '사용자상품번호';
COMMENT ON COLUMN user_item.user_no IS '사용자번호';
COMMENT ON COLUMN user_item.item_id IS '상품ID';
COMMENT ON COLUMN user_item.reg_date IS '구매일시';
COMMENT ON COLUMN user_item.upd_date IS '수정일시';


-- =====================
-- 자료실
-- =====================
CREATE TABLE pds
(
    item_id     BIGSERIAL    NOT NULL,
    item_name   VARCHAR(255),
    view_cnt    INTEGER,
    description VARCHAR(255),
    reg_date    TIMESTAMP,
    upd_date    TIMESTAMP,
    PRIMARY KEY (item_id)
);

COMMENT ON TABLE pds IS '자료실 테이블';
COMMENT ON COLUMN pds.item_id IS '자료ID';
COMMENT ON COLUMN pds.item_name IS '자료명';
COMMENT ON COLUMN pds.view_cnt IS '조회수';
COMMENT ON COLUMN pds.description IS '자료설명';
COMMENT ON COLUMN pds.reg_date IS '등록일시';
COMMENT ON COLUMN pds.upd_date IS '수정일시';


-- =====================
-- 자료실 첨부파일
-- =====================
CREATE TABLE pds_file
(
    pds_file_id BIGSERIAL    NOT NULL,
    item_id     BIGINT,
    full_name   VARCHAR(200),
    down_cnt    INTEGER,
    reg_date    TIMESTAMP,
    upd_date    TIMESTAMP,
    PRIMARY KEY (pds_file_id)
);

COMMENT ON TABLE pds_file IS '자료실 첨부파일 테이블';
COMMENT ON COLUMN pds_file.pds_file_id IS '파일ID';
COMMENT ON COLUMN pds_file.item_id IS '자료ID';
COMMENT ON COLUMN pds_file.full_name IS '파일 전체명';
COMMENT ON COLUMN pds_file.down_cnt IS '다운로드 횟수';
COMMENT ON COLUMN pds_file.reg_date IS '등록일시';
COMMENT ON COLUMN pds_file.upd_date IS '수정일시';

ALTER TABLE pds_file
    ADD CONSTRAINT fk_pds_file_item_id
        FOREIGN KEY (item_id) REFERENCES pds (item_id);


-- =====================
-- 충전 내역
-- =====================
CREATE TABLE charge_coin_history
(
    history_no BIGSERIAL NOT NULL,
    user_no    BIGINT,
    amount     INTEGER   NOT NULL,
    reg_date   TIMESTAMP,
    upd_date   TIMESTAMP,
    PRIMARY KEY (history_no)
);

COMMENT ON TABLE charge_coin_history IS '코인 충전 내역 테이블';
COMMENT ON COLUMN charge_coin_history.history_no IS '이력번호';
COMMENT ON COLUMN charge_coin_history.user_no IS '사용자번호';
COMMENT ON COLUMN charge_coin_history.amount IS '충전금액';
COMMENT ON COLUMN charge_coin_history.reg_date IS '충전일시';
COMMENT ON COLUMN charge_coin_history.upd_date IS '수정일시';


-- =====================
-- 지급 내역
-- =====================
CREATE TABLE pay_coin_history
(
    history_no BIGSERIAL    NOT NULL,
    user_no    BIGINT,
    item_id    BIGINT,
    item_name  VARCHAR(255),
    amount     INTEGER      NOT NULL,
    reg_date   TIMESTAMP,
    upd_date   TIMESTAMP,
    PRIMARY KEY (history_no)
);

COMMENT ON TABLE pay_coin_history IS '코인 지급 내역 테이블';
COMMENT ON COLUMN pay_coin_history.history_no IS '이력번호';
COMMENT ON COLUMN pay_coin_history.user_no IS '사용자번호';
COMMENT ON COLUMN pay_coin_history.item_id IS '상품ID';
COMMENT ON COLUMN pay_coin_history.item_name IS '상품명';
COMMENT ON COLUMN pay_coin_history.amount IS '사용금액';
COMMENT ON COLUMN pay_coin_history.reg_date IS '사용일시';
COMMENT ON COLUMN pay_coin_history.upd_date IS '수정일시';


-- =====================
-- 접근 로그
-- =====================
CREATE TABLE access_log
(
    log_no              BIGSERIAL    NOT NULL,
    request_uri         VARCHAR(200) NOT NULL,
    class_name          VARCHAR(100) NOT NULL,
    class_simple_name   VARCHAR(50)  NOT NULL,
    method_name         VARCHAR(100) NOT NULL,
    remote_addr         VARCHAR(50)  NOT NULL,
    reg_date            TIMESTAMP,
    upd_date            TIMESTAMP,
    PRIMARY KEY (log_no)
);

COMMENT ON TABLE access_log IS '시스템 접근 로그 테이블';
COMMENT ON COLUMN access_log.log_no IS '로그번호';
COMMENT ON COLUMN access_log.request_uri IS '요청 URI';
COMMENT ON COLUMN access_log.class_name IS '컨트롤러 클래스명';
COMMENT ON COLUMN access_log.class_simple_name IS '컨트롤러 클래스명 (요약)';
COMMENT ON COLUMN access_log.method_name IS '메서드명';
COMMENT ON COLUMN access_log.remote_addr IS '클라이언트 IP주소';
COMMENT ON COLUMN access_log.reg_date IS '접근일시';
COMMENT ON COLUMN access_log.upd_date IS '수정일시';


-- =====================
-- 서비스 성능 로그
-- =====================
CREATE TABLE performance_log
(
    log_no               BIGSERIAL    NOT NULL,
    signature_name       VARCHAR(50)  NOT NULL,
    signature_type_name  VARCHAR(100) NOT NULL,
    duration_time        BIGINT,
    reg_date             TIMESTAMP,
    upd_date             TIMESTAMP,
    PRIMARY KEY (log_no)
);

COMMENT ON TABLE performance_log IS '서비스 성능 로그 테이블';
COMMENT ON COLUMN performance_log.log_no IS '로그번호';
COMMENT ON COLUMN performance_log.signature_name IS '시그니처명';
COMMENT ON COLUMN performance_log.signature_type_name IS '시그니처 타입명';
COMMENT ON COLUMN performance_log.duration_time IS '실행시간';
COMMENT ON COLUMN performance_log.reg_date IS '실행일시';
COMMENT ON COLUMN performance_log.upd_date IS '수정일시';
