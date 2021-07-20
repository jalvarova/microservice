# MICROSERVICE Currency Exchange Transaction

#### Generated Spring Cli Service Cross

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=devtools,actuator,prometheus,cloud-gateway,cloud-config-client -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-gateway -d name=ms-gateway -d applicationName=GatewayApplication -o ms-gateway.zip
```

:file_folder: root project

```bash
unzip ms-gateway.zip
```

## INSTALL IMAGE LOCAL

#### Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-gateway:1.0.0
```

#### Run local docker image.

```bash
docker run -d --name ms-gateway -e PORT=9000 -e EUREKA_URI=http://ms-registry:8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 9000:9000 --network=microservice ms-gateway:1.0.0
```
#### Logs docker image.

```bash
docker logs -f ms-gateway
```

#### Create a local tag, to publish to the container registry

```bash
docker tag ms-gateway:1.0.0 gcr.io/[PROJECT ID]/ms-gateway:1.0.0
```