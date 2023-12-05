- start mysql server
- create database
- create /src/main/resources/application.properties with your data:

spring.datasource.url = jdbc:mysql://localhost:[default=3306]/[dbname]?useSSL=false&serverTimezone=UTC
  
spring.datasource.username = [db username]
  
spring.datasource.password = [your password here]

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect

spring.jpa.hibernate.ddl-auto = update

logging.level.org.springframework.security=DEBUG
# to run $: mvn spring-boot:run

credit:
https://www.javaguides.net/2021/10/login-and-registration-rest-api-using-spring-boot-spring-security-hibernate-mysql-database.html

this program has models of requests and entities of sign-in, sign-up, user, and event.

endpoint-1: POST /api/event/add<br />
endpoint-2: GET /api/event/all<br />
endpoint-3: DELETE /api/event/del/{eventId}<br />
endpoint-4: POST /api/auth/signin<br />
endpoint-5: POST /api/auth/signup<br />

work should be done concerning security of data and the program.
