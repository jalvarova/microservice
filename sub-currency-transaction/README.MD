# MICROSERVICE Currency Exchange Transaction Consumer

#### Generated Spring subscriber

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=cloud-starter,cloud-stream,cloud-gcp-pubsub,validation,devtools,lombok,actuator,prometheus,data-jpa,postgresql -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=sub-currency-transaction -d name=sub-currency-transaction -d applicationName=CunsumerTransactionApplication -o sub-currency-transaction.zip
```

:file_folder: root project

```bash
unzip sub-currency-transaction.zip
```

## INSTALL IMAGE LOCAL

#### Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t sub-currency-transaction:1.0.0
```

#### Run local docker image.

```bash
docker run -d --name sub-currency-transaction -e PORT=9000 -e EUREKA_URI=http://ms-registry:8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 9000:9000 --network=microservice sub-currency-transaction:1.0.0
```

#### Logs docker image.

```bash
docker logs -f sub-currency-transaction
```

#### Create a local tag, to publish to the container registry

```bash
docker tag sub-currency-transaction:1.0.0 gcr.io/[PROJECT ID]/sub-currency-transaction:1.0.0
```

#### Push container repository tag GCP

```bash
docker push gcr.io/${PROJECT_ID}/sub-currency-transaction:1.0.0
```

#### Create container docker compose

```bash
docker-compose -f sub-currency-transaction.yml up -d
```

#### Delete container docker compose

```bash
docker-compose -f sub-currency-transaction.yml down
```