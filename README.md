# WeBond Service Module

這是 WeBond 服務媒合平台中的服務模組，主要負責服務類型、服務項目、服務時段的管理與查詢。

目前 `main` 分支是 Spring Boot 版本，提供 REST API 給前端或 API 測試頁使用。這版是從原本的 JSP / Servlet / Spring MVC 專案逐步改過來的，目前資料存取層還保留 Hibernate DAO，之後會再整理成 Spring Data JPA。

## 使用技術

* Java 17
* Spring Boot 3.4.1
* Spring Web
* Hibernate
* HikariCP
* MySQL
* REST API
* DTO
* Maven

## 主要功能

* 查詢服務類型
* 查詢服務列表
* 依服務類型查詢服務
* 查詢單一服務
* 查詢服務可預約時段

## API 範例

```http
GET /CKA101_C_myMaven1/api/services
GET /CKA101_C_myMaven1/api/services/{serviceId}
GET /CKA101_C_myMaven1/api/services/type/{serviceTypeId}
GET /CKA101_C_myMaven1/api/service-types
GET /CKA101_C_myMaven1/api/service-slots
```
## 分支紀錄

這個專案有保留不同階段的版本，方便對照學習過程：

| 分支                           | 說明                           |
| ---------------------------- | ---------------------------- |
| main                         | 目前展示版，Spring Boot REST API   |
| dev                          | 後續開發分支                       |
| work/01-servlet-jsp-dao      | JSP / Servlet / DAO 版本       |
| work/02-spring-mvc-jsp       | Spring MVC + JSP + Hibernate |
| work/03-spring-mvc-rest-vue  | Spring MVC REST API + Vue    |
| work/04-spring-boot-rest-vue | Spring Boot 過渡版本             |

## 目前狀態

目前已完成 Spring Boot 啟動、REST API、HikariCP 連線池與 Hibernate DAO 串接。

後續預計整理：

* 將 Hibernate DAO 改成 Spring Data JPA
* 整理 package 結構
* 補上 Vue axios 串接
* 補上 Docker 部署
* 補上 API 驗證與錯誤處理
