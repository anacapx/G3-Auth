FROM debrum/ubuntu-jdk-mvn
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}
ENV JWT_SECRET=${JWT_SECRET}
RUN mkdir g3-consumer
COPY . g3-consumer
RUN cd g3-consumer && mvn clean package
RUN cp g3-consumer/target/*jar /app-consumer.jar
RUN rm -rf g3-consumer
ENTRYPOINT [ "java", "-jar", "/app-consumer.jar" ]