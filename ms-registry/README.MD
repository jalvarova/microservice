# MICROSERVICE ADMIN SPRING BOOT

## INSTALL IMAGE LOCAL

> Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-registry:1.0.0
```

> Run local docker image.

```bash
docker run -d --name ms-registry -e PORT=8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 8761:8761 --network=microservice ms-registry:1.0.0
```

> Logs docker image.

```bash
docker logs -f ms-registry
```

> Create a local tag, to publish to the container registry

```bash
docker tag ms-registry:1.0.0 gcr.io/[PROJECT ID]/ms-registry:1.0.0
```