# MICROSERVICE CONFIG PROPERTIES

## INSTALL IMAGE LOCAL

> Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-config-properties:1.0.0
```

> Run local docker image.

```bash
docker run -d --name ms-config-properties -e PORT=8088 -e GIT_USER=demo -e GIT_PWD=1234 -p 8088:8088 ms-config-properties:1.0.0 docker run -d --name ms-admin-server -e PORT=9000 -p 9000:9000 --network=microservice  ms-admin-server:1.0.0
```

> Logs docker image.

```bash
docker logs -f ms-config-properties
```

> Create a local tag, to publish to the container registry

```bash
docker tag ms-config-properties:1.0.0 gcr.io/[PROJECT ID]/ms-config-properties:1.0.0
```