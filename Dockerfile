FROM adoptopenjdk/openjdk11

LABEL maintainer=m.gauthier.antoine@gmail.com

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/zapping-maker-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} zapping-maker.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zapping-maker.jar"]
