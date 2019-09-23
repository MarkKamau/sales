FROM openjdk:8-jre-alpine
ADD target/data.transform.sales.jar data.transform.sales.jar
EXPOSE 34095
ENTRYPOINT ["java","-jar","data.transform.sales.jar"]