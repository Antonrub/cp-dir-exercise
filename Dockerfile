FROM openjdk:11-nanoserver

LABEL maintainer="antonru@checkpoint.com"

COPY target/cp-dir.jar /cp-dir.jar
EXPOSE 8089

CMD ["java", "-jar", "cp-dir.jar"]