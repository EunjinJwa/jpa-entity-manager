# jpa-entity-manager

### 🚀 2단계 - 엔터티 초기화 (EntityLoader)
- [x] 요구사항 1 - RowMapper 리팩터링
  - EntityLoader는 데이터베이스 쿼리를 실행하여 엔티티 객체를 로드하는 역할을 수행합니다.
- [x] 요구사항 2 - EntityManager 의 책임 줄여주기
    - EntityManager 의 구현체에서 find 에 대한 책임을 EntityLoader 로 옮겨주자
