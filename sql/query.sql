-- H2 DB 모든 테이블 지우기
-- drop all objects;

-- 모든 테이블 조회
SELECT * FROM ADDRESS;
SELECT * FROM ADDRESS_LV1;
SELECT * FROM ADDRESS_LV2;
SELECT * FROM ADDRESS_LV3;
SELECT * FROM ATTACHMENT;
SELECT * FROM BUDGET;
SELECT * FROM CLUB;
SELECT * FROM CLUB_MEMBER;
SELECT * FROM COMMENTS;
SELECT * FROM MEMBER;
SELECT * FROM POST;
SELECT * FROM SCHEDULE;
SELECT * FROM TRANSACTIONS;

-- 주소 테이블 전체조회
SELECT
    a.ADDRESS_ID,
    a.ADDRESS_LV1_ID as lv1_id,
    lv1.NAME as lv1_name,
    a.ADDRESS_LV2_ID as lv2_id,
    lv2.NAME as lv2_name,
    a.ADDRESS_LV3_ID as lv3_id,
    lv3.NAME as lv3_name
FROM ADDRESS a
INNER JOIN address_lv1 lv1 ON a.ADDRESS_LV1_ID = lv1.ADDRESS_LV1_ID
INNER JOIN address_lv2 lv2 ON a.ADDRESS_LV2_ID = lv2.ADDRESS_LV2_ID
LEFT JOIN address_lv3 lv3 ON a.ADDRESS_LV3_ID = lv3.ADDRESS_LV3_ID;
