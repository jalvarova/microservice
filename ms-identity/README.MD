# MICROSERVICE ADMIN SPRING BOOT

## INSTALL IMAGE LOCAL

> Build a local Docker image with the Dockerfile already created.

```bash
docker build --build-arg ARTIFACT_ID . -t ms-identity:1.0.0
```

> Run local docker image.

```bash
docker run -d --name ms-identity -e PORT=9000 -e EUREKA_URI=http://ms-registry:8761 -e MS_CONFIG_SERVER=http://ms-config-properties:8088 -p 9000:9000 --network=microservice ms-identity:1.0.0
```

> Logs docker image.

```bash
docker logs -f ms-identity
```

> Create a local tag, to publish to the container registry

```bash
docker tag ms-identity:1.0.0 gcr.io/[PROJECT ID]/ms-identity:1.0.0
```