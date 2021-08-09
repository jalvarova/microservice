# MICROSERVICE Event Transaction

#### Generated Spring Cli Mediator

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=cloud-eureka,cloud-gcp-pubsub,amqp,cloud-config-client,devtools,actuator,prometheus -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-mediator-event -d name=ms-mediator-event -d applicationName=MediatorEventApplication -o ms-mediator-event.zip
```

:file_folder: root project

```bash
unzip ms-mediator-event.zip
```

## INSTALL IMAGE LOCAL

#### Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-mediator-event:1.0.0
```

#### Run local docker image.

```bash
docker run -d --name ms-mediator-event -e PORT=9000 -e EUREKA_URI=http://ms-registry:8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 9000:9000 --network=microservice ms-mediator-event:1.0.0
```

#### Logs docker image.

```bash
docker logs -f ms-mediator-event
```

#### Create a local tag, to publish to the container registry

```bash
docker tag ms-mediator-event:1.0.0 gcr.io/[PROJECT ID]/ms-mediator-event:1.0.0
```

#### Push container repository tag GCP

```bash
docker push gcr.io/${PROJECT_ID}/ms-mediator-event:1.0.0
```

#### Create container docker compose

```bash
docker-compose -f ms-mediator-event.yml up -d
```

#### Delete container docker compose

```bash
docker-compose -f ms-mediator-event.yml down
```