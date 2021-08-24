FROM gradle:jdk11-openj9
WORKDIR /artifact
COPY . /artifact
RUN gradle clean build
RUN ls -la

FROM adoptopenjdk:11-jdk-openj9
ENV ARTIFACT_ID=ms-currency-transaction

RUN apt-get update \
	&& apt-get install -y ca-certificates \
	&& update-ca-certificates \
	&& apt-get install -y tzdata
	ENV TZ=America/Lima

WORKDIR /artifact
COPY --from=0 /artifact/build/libs/${ARTIFACT_ID}-*.jar app.jar
COPY --from=0 /artifact/src/main/resources/ .
RUN ls -lF
RUN mkdir -p /${ARTIFACT_ID}/resources
COPY src/main/resources/* /${ARTIFACT_ID}/resources/
CMD ["sh","-c","java -jar app.jar ${JAVA_OPTS}"]
RUN du -sh /var/cache/apt