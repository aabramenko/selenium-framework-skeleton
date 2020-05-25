How to launch without dockerizing:

1. Install openjdk (tested with openjdk8 and openjdk11)
2. Install Maven
3. mvn test -DSUITE=test-suite.xml -DHEADLESS=false -DBROWSER=chrome -DGRID=false

Reports will be stored into the "/selenium-framework-skeleton/target" folder


How to launch using Selenium/Hub and Selenium/Node:

1. Install docker
2. Install docker-compose
3. BROWSER=firefox HEADLESS=true docker-compose -f docker/basic.docker-compose.yaml up --scale firefox=6 --scale chrome=0
   or
   BROWSER=chrome HEADLESS=true docker-compose -f docker/basic.docker-compose.yaml up --scale chrome=6 --scale firefox=0

Reports will be stored into the "/selenium-framework-skeleton/test-result" folder


How to launch using Zalenium

1. docker pull elgalu/selenium
2. docker pull dosel/zalenium
3. BROWSER=chrome HEADLESS=true docker-compose -f docker/zalenium.docker-compose.yaml up
   or
   BROWSER=firefox HEADLESS=true docker-compose -f docker/zalenium.docker-compose.yaml up

The realization is in progress yet, but looks like this approach does not have any advantages, so don't spend much time for that.  


How to launch using Selenoid

1. docker pull selenoid/firefox:76.0
2. docker pull selenoid/chrome:83.0
3. docker pull selenoid/video-recorder:latest-release // if you would like to record video
4. docker-compose -f docker/selenoid.docker-compose.yaml up -d
5. docker build -t aabramenko/selenium-test-environment -f docker/Dockerfile .
6. docker run -e HUB_HOST=172.17.0.1 -e BROWSER=chrome -e HEADLESS=false -e VIDEO=true aabramenko/selenium-test-environment

where HUB_HOST is IP address of the selenoid hub

Reports will be stored into the "/selenium-framework-skeleton/test-result" folder


Reports

1. Allure report is available in the "/selenium-framework-skeleton/test-result/allure-results/" folder
2. ReportNG report is available in the "/selenium-framework-skeleton/test-result/surefire-reports/html/" folder


How to run Jenkins locally

docker run -p 8080:8080 -p 50000:50000 -v "$PWD:/var/jenkins_home" jenkins/jenkins:lts
