# Tech Stacks
- Back-End
  - Java
  - Spring Boot
  - Spring Security
  - OAuth 2.0
  - WebSocket
- Front-End
  - jQuery
  - Thymeleaf
  - Ajax
- Database
  - MySql
# Project Structure
```
📦letsparty
 ┣ 📂config
 ┣ 📂dto
 ┣ 📂exception  // 커스텀 예외 및 에러 클래스
 ┣ 📂info       // pagination
 ┣ 📂mapper
 ┣ 📂security   // oauth 및 인증/인가
 ┃ ┣ 📂oauth
 ┃ ┃ ┣ 📂exception
 ┃ ┃ ┗ 📂info
 ┃ ┣ 📂service
 ┃ ┣ 📂user
 ┣ 📂service
 ┣ 📂util
 ┣ 📂vo
 ┣ 📂web
 ┃ ┣ 📂advice
 ┃ ┣ 📂controller
 ┃ ┣ 📂form
 ┃ ┣ 📂interceptor
 ┃ ┣ 📂model
 ┃ ┗ 📂websocket  // 채팅관련 websocket 설정
 ┃ ┃ ┣ 📂controller
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┣ 📂interceptor
 ┃ ┃ ┣ 📂service
 ┃ ┃ ┣ 📂util
```
# ERD
# API
# Workflow 및 구현 패턴
