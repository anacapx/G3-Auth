FROM debrum/ubuntu-jdk-mvn
RUN mkdir g3-auth
COPY . g3-auth
RUN cd g3-auth && mvn clean package -Dmaven.test.skip
RUN cp g3-auth/target/*jar /app-auth.jar
RUN cp g3-auth/newrelic/*jar /newrelic-auth.jar
RUN cp g3-auth/newrelic/*yml /newrelic.yml
ENV NEW_RELIC_APP_NAME=${NEW_RELIC_APP_NAME}
ENV NEW_RELIC_LICENSE_KEY=${NEW_RELIC_LICENSE_KEY}
ENV NEW_RELIC_LOG_FILE_NAME=${NEW_RELIC_LOG_FILE_NAME}
EXPOSE 8081
ENTRYPOINT ["java","-javaagent:/newrelic-auth.jar","-jar","/app-auth.jar"]
