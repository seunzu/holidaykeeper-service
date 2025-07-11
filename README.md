# holidaykeeper-service

## BE 기술 스택
- Java 21
- SpringBoot 3.x
  - Spring MVC, Spring Data JPA, QueryDSL, Spring Scheduler
- H2 Database
- Prometheus + Grafana
- Docker, Docker Hub, GitHub Actions


## 외부 API
| 용도 | 엔드포인트                                                                                                                                                                                      | 응답 |
| --- |--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| 국가 목록 | `GET` https://date.nager.at/api/v3/AvailableCountries                                                                                                                                      | 국가배열 |
| 특정 연도 공휴일 | `GET` [https://date.nager.at/api/v3/PublicHolidays/{year}/](https://date.nager.at/api/v3/PublicHolidays/%7Byear%7D/){countryCode} e.g. https://date.nager.at/api/v3/PublicHolidays/2025/KR | 공휴일 |


## 주요 기능
- 국가 데이터 적재
    - 외부 API에서 국가 목록 불러와 DB 저장 (최초 1회만 적재)
- 공휴일 데이터 적재 및 동기화
  - 외부 API에서 공휴일 데이터 불러와 `Upsert` 저장
- 공휴일 검색 API 제공
  - 날짜, 국가코드, 공휴일 타입으로 공휴일 검색 가능
- 스케줄링 기반 자동 동기화
  - `Spring Scheduler`를 활용한 정기 `Holiday` 동기화 배치 실행
- 모니터링
  - Prometheus + Grafana로 메트릭 수집 및 시각화
- CI/CD 파이프라인
  -  GitHub Actions 기반 자동 빌드 & Docker Hub 이미지 푸시

## 실행 방법 (로컬 개발 환경)
**반드시 아래 2개의 파일 필요**
- `docker-compose.yml` ([컨테이너 조합 정의](https://github.com/seunzu/holidaykeeper-service/blob/main/docker-compose.yml))
- `Docker/prometheus.yml` ([Promethes 설정](https://github.com/seunzu/holidaykeeper-service/blob/main/Docker/prometheus.yml))

```
# 전체 서비스 실행
docker-compose up -d
```

> (참고) Docker Hub에서 holidaykeeper-service 이미지만 따로 실행 (단독 컨테이너 실행)
```
docker pull seungjusuh/holidaykeeper-service
docker run -d -p 8080:8080 seungjusuh/holidaykeeper-service
```

- Swagger API
  - http://localhost:8080/swagger-ui/index.html

- Grafana
  - http://localhost:3000
  - User Name: `admin`
  - Password: `1234`

- H2 Database
  - http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:holiday`
  - User Name: `test`
  - Password: `1234`

![스크린샷 2025-07-08 오후 3 18 11](https://github.com/user-attachments/assets/26f8564c-3fcc-40f7-9f18-9bbdef0ac0e5)

