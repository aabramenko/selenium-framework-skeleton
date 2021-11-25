## How to run tests without docker:

1. Install openjdk (tested with openjdk8 and openjdk11)
2. Install Maven
3. Install Google Chrome / Firefox
4. `mvn test -DSUITE=test-suite.xml -DHEADLESS=false -DBROWSER=chrome -DGRID=false`

Reports will be stored to the "target" folder


## How to run tests using Selenium/Hub and Selenium/Node:

1. Install docker
2. Install docker-compose
3. `BROWSER=chrome HEADLESS=true SUITE=test-suite.xml docker-compose -f docker/hub.docker-compose.yaml up --build --scale chrome=4`

Reports will be stored to the "reports" folder


## How to run tests inside one docker container:
1. Install docker
2. Install docker-compose
3. `BROWSER=firefox HEADLESS=true SUITE=test-suite.xml docker-compose -f docker/custom.docker-compose.yml up`

Reports will be stored to the "reports" folder

## How to run tests using Zalenium

1. docker pull elgalu/selenium
2. docker pull dosel/zalenium
3. `BROWSER=chrome HEADLESS=true docker-compose -f docker/zalenium.docker-compose.yaml up`
   or
   `BROWSER=firefox HEADLESS=true docker-compose -f docker/zalenium.docker-compose.yaml up`

(!) The implementation is in progress yet, but looks like this approach does not have any advantages, so don't spend much time for that.  


## How to run tests using Selenoid

Note: if your docker requires 'sudo' please take a look at this article: https://docs.docker.com/engine/install/linux-postinstall/
All next commands should be executed without 'sudo'.

1. `docker pull selenoid/firefox:94.0`
2. `docker pull selenoid/chrome:96.0`
3. `docker pull selenoid/video-recorder:latest-release // if you would like to record video`
4. `docker-compose -f docker/selenoid.docker-compose.yaml up -d`
5. `docker build -t aabramenko/selenium-test-environment -f docker/hub.Dockerfile .`
6. ```
   docker run -e HUB_HOST=172.17.0.1 \
            -e BROWSER=chrome \
            -e HEADLESS=true \
            -e VIDEO=false \
            -e SUITE=test-suite.xml \
            -v $PWD/reports/selenoid:/usr/share/selenium-tests/target \
            aabramenko/selenium-test-environment
   ```

where HUB_HOST is IP address of the selenoid hub

Reports will be stored to the "reports" folder


## Run tests on the Github Actions

### using the Hub docker
https://github.com/aabramenko/selenium-framework-skeleton/actions/workflows/hub.yml

### without docker
https://github.com/aabramenko/selenium-framework-skeleton/actions/workflows/no-docker.yml

### using a custom docker
https://github.com/aabramenko/selenium-framework-skeleton/actions/workflows/custom.yml


## Reports

1. Allure report is available in the appropriate folder
2. ReportNG report is available in the appropriate folder


## How to run Jenkins locally

`docker run -p 8888:8080 -p 50000:50000 -v "$PWD:/var/jenkins_home" jenkins/jenkins:lts`

// the port is "8888" because the port "8080" may be used by the Selenoid hub
