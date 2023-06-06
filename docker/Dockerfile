FROM maven:3.8-jdk-11

RUN mkdir -p /usr/src/project/
COPY  . /usr/src/project/
WORKDIR /usr/src/project/
RUN mvn clean install

EXPOSE 8080

CMD ["java", "-jar", "target/number-reducer-0.0.1-SNAPSHOT.jar"]