FROM openjdk:17-jdk-alpine
RUN mkdir /App
WORKDIR /App
COPY target/*.jar /App/app.jar
CMD ["java","-jar","/App/app.jar"]