FROM adoptopenjdk/openjdk11:alpine-slim

ENV APP_DIR=/var/opt/webapp

COPY target/*standalone*.jar $APP_DIR/standalone.jar

WORKDIR $APP_DIR
USER daemon
ENTRYPOINT ["java","-server","-jar","/var/opt/webapp/standalone.jar"]