# MICROSERVICE Customer

## INSTALL IMAGE LOCAL

#### Generated Spring Cli

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=validation,devtools,lombok,actuator,prometheus,web,data-jpa,postgresql -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=pe.micro.walavo -d packageName=pe.micro.walavo -d artifactId=ms-clients -d name=ms-clients -d applicationName=Client -o ms-clients.zip
```
:file_folder: root project

```bash
unzip ms-clients.zip
```

#### Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-clients:1.0.0
```

#### Run local docker image.

```bash
docker run -d --name ms-clients -e PORT=9000 -e EUREKA_URI=http://ms-registry:8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 9000:9000 --network=microservice ms-clients:1.0.0
```

#### Logs docker image.

```bash
docker logs -f ms-clients
```

#### Create a local tag, to publish to the container registry

```bash
docker tag ms-clients:1.0.0 gcr.io/[PROJECT ID]/ms-clients:1.0.0
```

#### Push container repository tag GCP

```bash
docker push gcr.io/${PROJECT_ID}/ms-clients:1.0.0
```

#### Create container docker compose

```bash
docker-compose -f ms-clients.yml up -d
```

#### Delete container docker compose

```bash
docker-compose -f ms-clients.yml down
```