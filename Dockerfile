# ===== Build 階段：用 Maven 打包 WAR =====
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


# ===== Run 階段：用 Tomcat 11 跑 WAR =====
FROM tomcat:11.0-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/CKA101_C_myMaven1.war

EXPOSE 8080

CMD ["catalina.sh", "run"]