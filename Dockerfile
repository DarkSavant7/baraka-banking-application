FROM openjdk:11.0.14.1-jre-buster as production
LABEL maintainer="Alex Grigorev"
VOLUME /tmp
WORKDIR /app
#
COPY /banking-application-src/target/*.jar /app/app.jar
RUN mkdir /app/log
RUN mkdir /app/config
ENV JAVA_TOOL_OPTIONS=""

ENTRYPOINT ["sh", "-c", "java ${JAVA_TOOL_OPTIONS} -jar app.jar"]
