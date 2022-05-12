FROM maven:3-openjdk-8 AS build
RUN mkdir /projects
COPY .. /projects
WORKDIR /projects
RUN mvn clean package -pl back-service

# Note: 如果有使用spring-cloud-gcp相關Library，base image不建議使用alpine，因spring-cloud-gcp相關Library
# 會使用glibc這個函式庫，而alpine支援msul而不支援glibc，因此啟動container時會產生fatal error而導致失敗
FROM openjdk:8-jre
RUN adduser myuser && mkdir /app
COPY --from=build /projects/back-service/target/*.jar /app/application.jar
WORKDIR /app
USER myuser
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "application.jar"]