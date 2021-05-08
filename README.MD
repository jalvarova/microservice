## ARQUITECTURA DE MICROSERVICIOS

>La arquitectura de microservicios (en inglés, Micro Services Architecture, MSA) es una aproximación para el desarrollo de software que consiste en construir una aplicación como un conjunto de pequeños servicios, los cuales se ejecutan en su propio proceso y se comunican con mecanismos ligeros (normalmente una API de recursos HTTP). Cada servicio se encarga de implementar una funcionalidad completa del negocio. Cada servicio es desplegado de forma independiente y puede estar programado en distintos lenguajes y usar diferentes tecnologías de almacenamiento de datos.1​
>
[Microservice Bible](https://microservices.io/patterns/index.html)

![alt text](/images/services-architecture.png "Archi")

# Microservice with Spring Boot


#### Generated Spring Cli

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=validation,devtools,lombok,actuator,prometheus,web,data-jpa,postgresql -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=pe.micro.walavo -d packageName=pe.micro.walavo -d artifactId=ms-clients -d name=ms-clients -d applicationName=Client -o ms-clients.zip
```

#### Generated Spring Cli auth login

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=security,validation,devtools,lombok,actuator,prometheus,web,data-jpa,postgresql -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-identity -d name=ms-identity -d applicationName=Identity -o ms-identity.zip
```

#### Generated Spring Cli Service Cross

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=cloud-config-server,devtools,lombok,actuator,prometheus,web -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-config-properties -d name=ms-config-properties -d applicationName=PropertiesApplication -o ms-config-properties.zip
```

```bash
curl -G https://start.spring.io/starter.zip -d dependencies=devtools,actuator,prometheus,cloud-eureka-server -d version=1.0.0 -d bootVersion=2.4.5.RELEASE -d javaVersion=11 -d language=java -d packaging=jar -d type=gradle-project -d groupId=org.hta -d packageName=org.hta -d artifactId=ms-registrY -d name=ms-registry -d applicationName=RegistryApplication -o ms-registry.zip
```


## Framework developer

![alt text](/images/framework.png "Framework")

## Cloud Native

![alt text](/images/stack-cloud.png "Cloud")

## Technological Stack 

![alt text](/images/stack-spring.png "Stack")


## Microservice Stack

![alt text](/images/stack-microservice.png "Microservice")

#### References

[Netflix Microservice](https://netflix.github.io/)

[Initializer Spring](https://start.spring.io/)

[Persistence ORM](https://github.com/vladmihalcea/hibernate-types)