FROM debrum/ubuntu-jdk-mvn
RUN mkdir g3-auth
COPY . g3-auth
RUN cd g3-auth && mvn clean package -Dmaven.test.skip
RUN cp g3-auth/target/*jar /app-auth.jar
RUN rm -rf g3-auth
ENTRYPOINT [ "java", "-jar", "/app-auth.jar" ]