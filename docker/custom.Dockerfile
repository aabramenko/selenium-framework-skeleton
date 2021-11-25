FROM ubuntu:18.04 AS base

ENV DEBIAN_FRONTEND=noninteractive

# fix https://github.com/SeleniumHQ/docker-selenium/issues/87
ENV DBUS_SESSION_BUS_ADDRESS=/dev/null

ENV TESTS_DIR /selenium-tests
WORKDIR $TESTS_DIR

ADD . $TESTS_DIR

RUN apt-get -qqy update && apt-get -qqy --no-install-recommends install \
    curl \
	maven \
	openjdk-8-jdk \
	wget
	
# install Firefox
RUN FIREFOX_URL="https://download.mozilla.org/?product=firefox-latest-ssl&os=linux64&lang=en-US" \
  && ACTUAL_URL=$(curl -Ls -o /dev/null -w %{url_effective} $FIREFOX_URL) \
  && curl --silent --show-error --location --fail --retry 3 --output /tmp/firefox.tar.bz2 $ACTUAL_URL \
  && tar -xvjf /tmp/firefox.tar.bz2 -C /opt \
  && ln -s /opt/firefox/firefox /usr/local/bin/firefox \
  && apt-get install -y libgtk3.0-cil-dev libasound2 libasound2 libdbus-glib-1-2 libdbus-1-3 \
  && rm -rf /tmp/firefox.* \
  && firefox --version

# download dependencies
RUN cd $TESTS_DIR && mvn install -DskipTests

ENTRYPOINT sh $TESTS_DIR/docker/custom-test-execution.sh