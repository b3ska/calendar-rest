- start mysql server
- create tables user & event
- create /src/main/resources/application.properties with your data:

spring.datasource.url = jdbc:mysql://localhost:[default=3306]/[dbname]?useSSL=false&serverTimezone=UTC
  
spring.datasource.username = [db username]
  
spring.datasource.password = [your password here]

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect

spring.jpa.hibernate.ddl-auto = update

logging.level.org.springframework.security=DEBUG
# to run $: mvn spring-boot:run


