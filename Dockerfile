FROM openjdk:8-jdk-lpine 

EXPOSE 8082

ARG JAR_FILE=target/*.jar}

VOLUME /tmp

COPY ${JAVA_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]