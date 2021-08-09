# MICROSERVICE ADMIN SPRING BOOT

#### Generated Spring Cli Service Cross

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=devtools,actuator,prometheus,cloud-eureka-server,cloud-config-client -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-registry -d name=ms-registry -d applicationName=RegistryApplication -o ms-registry.zip
```

:file_folder: root project

```bash
unzip ms-registry.zip
```

## INSTALL IMAGE LOCAL

#### Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-registry:1.0.0
```

#### Run local docker image.

```bash
docker run -d --name ms-registry -e PORT=8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 8761:8761 --network=microservice ms-registry:1.0.0
```

#### Logs docker image.

```bash
docker logs -f ms-registry
```

#### Create a local tag, to publish to the container registry

```bash
docker tag ms-registry:1.0.0 gcr.io/[PROJECT ID]/ms-registry:1.0.0
```

#### Push container repository tag GCP

```bash
docker push gcr.io/${PROJECT_ID}/ms-registry:1.0.0
```

#### Create container docker compose

```bash
docker-compose -f ms-registry.yml up -d
```

#### Delete container docker compose

```bash
docker-compose -f ms-registry.yml down
```