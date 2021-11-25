FROM openjdk:8-jdk-alpine

RUN apk add maven curl wget jq

ENV TESTS_DIR /usr/share/selenium-tests
WORKDIR $TESTS_DIR

ADD . $TESTS_DIR
RUN cd $TESTS_DIR && mvn install -DskipTests

ENTRYPOINT sh docker/hub-test-execution.sh