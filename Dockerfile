FROM openjdk:24-ea-17-jdk-bookworm

COPY . /tmp
RUN mkdir /opt/app
WORKDIR /tmp
RUN ./mvnw package && rm -rf ~/.m2  && mv target/speaker_web_gate-RELEASE.jar /opt/app/speaker_web_gate.jar \
 && cd / && rm -rf /tmp/*

CMD ["java", "-jar", "/opt/app/speaker_web_gate.jar"]