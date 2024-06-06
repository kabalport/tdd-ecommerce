# 요구사항
```sql
-- User 테이블에 데이터 삽입
INSERT INTO `user` (user_id, created_at, email, name, updated_at) VALUES(1, NOW(), 'customer@example.com', 'choi', NOW());

-- UserPoint 테이블에 데이터 삽입
INSERT INTO user_point (user_point_id, point_balance, user_id) VALUES(1, 100000.00, 1);

-- Product 테이블에 데이터 삽입
INSERT INTO product (id, del_flag, description, discount_policy, name, price) VALUES(1, FALSE, 'Description 1', 'NONE', 'Product 1', 100.00),(2, FALSE, 'Description 2', 'NONE', 'Product 2', 200.00);

-- ProductStock 테이블에 데이터 삽입
INSERT INTO product_stock (product_stock_id, last_updated, product_id, product_quantity) VALUES(1, NOW(), 1, 1000),(2, NOW(), 2, 1000);
```


## 포인트
- 포인트충전-회원이 포인트충전요청을 하면 회원의포인트를 충전시킵니다.
- 포인트충전실패-회원이 아닌경우는 포인트를 충전할수 없습니다.
- 포인트사용-회원이 포인트사용요청을 하면 회원의포인트를 사용합니다.
- 포인트사용실패-잔액이 부족한경우는 포인트사용을 할 수 없습니다.
- 포인트갱신손실-동시에 포인트를 갱신하는 경우 손실이 발생할 수 있습니다. 그러면 보상트랜잭션이 필요합니다.


### 동시성 이슈 파악 - 포인트 갱신손실
문제정의
조회후 그값을 기반으로 업데이트 하는거다.

문제 해결 방법론
가능한 동시성 제어 방식들을 도입해보고 각각의 장단점을 파악한 내용을 정리 제출
다양한 동시성 제어 방식에서 해당 비즈니스 로직에서 적합하다고 판단하여
차용한 동시성 제어 방식을 구현하여 비즈니스 로직에 적용하고, 통합테스트 등으로 검증하는 코드 작성 및 제출
1.낙관적락
2.비관적락
3.분산락









## 주문
상품주문-사용자가 상품주문요청을 하면 상품주문을 합니다.



# 유즈케이스
## 포인트충전
1. 회원조회