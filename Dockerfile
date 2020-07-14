#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY /src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install -DskipTests

#
# Package stage
#

FROM openjdk AS final

COPY --from=build /home/app/target/cp-dir-0.0.1-SNAPSHOT.jar /cp-dir.jar

LABEL maintainer="antonru@checkpoint.com"

EXPOSE 8089

CMD ["java", "-jar", "cp-dir.jar"]
