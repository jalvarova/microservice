FROM adoptopenjdk/openjdk11

#ENV ARTIFACT_ID=$ARTIFACT
#ENV ARTIFACT_VERSION=$VERSION
#ENV APPLICATION_PORT=$PORT
#ENV HOST_DATABASE=$DATABASE

ENV ARTIFACT_ID=ms-identity
ENV ARTIFACT_VERSION=1.0.0
ENV APPLICATION_PORT=8080
ENV HOST_DATABASE=jdbc:postgresql://db:5432/rbacdb
ENV HOST_CACHE=redis

# Update Alpine
RUN apt-get update \
    && apt-get install -y iputils-ping \
	&& apt-get install -y ca-certificates \
	&& apt-get install postgresql-client \
	&& update-ca-certificates \
	&& apt-get install -y tzdata
	ENV TZ=America/Lima

RUN env
RUN mkdir -p /${ARTIFACT_ID}/api/build/libs && mkdir -p /${ARTIFACT_ID}/resources
# Copy *.jar and resources files
COPY build/libs/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar /${ARTIFACT_ID}/build/libs/
COPY build/resources/* /${ARTIFACT_ID}/resources/
# Run service java
RUN ls -la /${ARTIFACT_ID}/build/libs/
CMD ["sh","-c","java -jar /${ARTIFACT_ID}/build/libs/${ARTIFACT_ID}-${ARTIFACT_VERSION}.jar -Duser.timezone=America/Lima ${JAVA_OPTS}"]
# ENTRYPOINT ["java","-Duser.timezone=America/Lima","-jar","/ARTIFACT_ID/api/build/libs/ARTIFACT_ID-ARTIFACT_VERSION-all.jar"]
# Clean APK cache
RUN du -sh /var/cache/apt