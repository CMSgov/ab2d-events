FROM openjdk:17
WORKDIR /usr/src/ab2d-events
ADD build/libs/ab2d-event.jar /usr/src/ab2d-events/ab2d-event.jar

CMD java \
    -XX:+UseContainerSupport \
    -jar ab2d-event.jar

EXPOSE 8080