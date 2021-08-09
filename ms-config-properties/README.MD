# MICROSERVICE CONFIG PROPERTIES

## INSTALL IMAGE LOCAL

#### Generated Spring Cli Service Cross

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=cloud-config-server,devtools,lombok,actuator,prometheus,web -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-config-properties -d name=ms-config-properties -d applicationName=PropertiesApplication -o ms-config-properties.zip
```

:file_folder: root project

```bash
unzip ms-config-properties.zip
```

#### Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-config-properties:1.0.0
```

#### Run local docker image.

```bash
docker run -d --name ms-config-properties -e PORT=8088 -e GIT_USER=demo -e GIT_PWD=1234 -p 8088:8088 ms-config-properties:1.0.0 docker run -d --name ms-admin-server -e PORT=9000 -p 9000:9000 --network=microservice  ms-admin-server:1.0.0
```

#### Logs docker image.

```bash
docker logs -f ms-config-properties
```

#### Create a local tag, to publish to the container registry

```bash
docker tag ms-config-properties:1.0.0 gcr.io/[PROJECT ID]/ms-config-properties:1.0.0
```

#### Push container repository tag GCP

```bash
docker push gcr.io/${PROJECT_ID}/ms-config-properties:1.0.0
```

#### Create container docker compose

```bash
docker-compose -f ms-config-properties.yml up -d
```

#### Delete container docker compose

```bash
docker-compose -f ms-config-properties.yml down
```