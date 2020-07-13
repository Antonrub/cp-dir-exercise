FROM openjdk

LABEL maintainer="antonru@checkpoint.com"

COPY /cp-dir-0.0.1-SNAPSHOT.jar /cp-dir.jar
EXPOSE 8089

CMD ["java", "-jar", "cp-dir.jar"]