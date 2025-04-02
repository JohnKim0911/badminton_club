# 배드민턴 클럽 플랫폼

- 개인 토이 프로젝트입니다.
- 개발기간: 2025.03.25 (화) ~ 현재

## 개발 계기

동호회 총무님께서 회비를 엑셀로 관리하는게 너무 불편해 보여서 시작하게 되었다.

- 문제점
  - 회비 관리
    - 회원마다 회비를 내는 시기, 기간 및 가격이 다르다.
      - 한번에 한달, 여러달, 1년치 한번에 내기도 함.
      - 할인가 적용
        - 2인이상 가족이 같이 할 경우 10% 할인
        - 1년치 선결제시 할인
    - 회비를 내는 사람도 자기가 언제부터 언제까지 기간의 회비를 냈는지 기억하지 못한다.
      - 각자 알아서 기록하고 내야한다.
      - 총무님께서 일일히 회비를 안 낸 사람을 파악하고, 독촉하기도 쉽지 않다.
    - 조직의 예산(회비)은 투명하게 관리되어야 한다.
      - 실수로 누락되거나, 새어나가는 예산이 없도록 확인할 수 있어야 한다.

이 문제를 해결하고, 기능을 확장해보자.

## 개발 환경

- SpringBoot 3.4.2
- Java 17
- Spring Data JPA
- Querydsl
- H2 database
- Thymeleaf
- IDE: IntelliJ

## 기능 목록

- 회원
  - 회원 등록, 조회, 수정, 삭제
  - 회원 등록 신청관리 (수락, 거부)
  - 출석 히스토리
- 클럽
  - 클럽 등록, 조회, 수정, 삭제
- 게시판
  - 게시글 등록, 조회, 수정, 삭제
  - 페이징 처리
  - 다중 첨부파일
  - 댓글, 대댓글
  - 좋아요 기능
- 결재
  - 회비 일정 조회
  - 회비 결제
  - 회비 결제일 알람
- 예산
  - 예산 조회, 수정
- 일정
  - 일정 조회, 등록, 수정, 삭제
- 게임
  - 조 매칭 (싱글/복식)
  - 전적 관리
- 채팅
- 레슨
  - 레슨 일정 조회
  - 레슨 신청

## 프로젝트 설정

### H2 데이터베이스

- JDBC URL:
  - 파일 생성 (최초 한번만): `jdbc:h2:~/club`
  - 이후 접속: `jdbc:h2:tcp://localhost/~/club`
