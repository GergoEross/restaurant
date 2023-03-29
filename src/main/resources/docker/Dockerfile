FROM openjdk:17

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

RUN mkdir -p /opt
ADD maven/opt/waiter.jar /opt/waiter.jar


EXPOSE 8080

ENTRYPOINT java -jar /opt/waiter.jar -Djava.net.preferIPv4Stack=true