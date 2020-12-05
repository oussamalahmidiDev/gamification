# Gamification

Gamification is a simple web application based on Spring Boot and Spring Cloud project.

This is a simple extendable app for learning purposes.

### Technologies

Gamification uses a number of open source projects to work properly:

- Springboot - Java framework for backend (Rest APIs, Embedded server)
- PostgreSQL - Production-ready SQL database
- RabbitMQ - Message broker for asynchronous communication
- Docker - Containerization system for services deployment
- Consul - Discovery and config server
- Sleuth - Distributed tracing

### Installation

##### Requirements

Please install these requirements before setting up the porject on your local machine :

| Requirement | DESCRIPTION                                                                         |
| ----------- | ----------------------------------------------------------------------------------- |
| JDK 8       | At least JDK 8 is required to compile the services                                  |
| Maven       | Java application packaging system                                                   |
| Docker      | Containerization system to deploy services                                          |
| RabbitMQ\*  | Message broker (optional for local tests without Docker)                            |
| Consul\*    | Discovery and config server for servicesr (optional for local tests without Docker) |

##### Setup

Setup environment variables `DB_PASSWORD` :

```sh
$ git clone blabla
$ cd blabla
$ echo DB_PASSWORD=mysecret > .env
$ ./deploy.sh -b
```

Run deployment script :

```sh
$ ./deploy.sh -b
```

You can skip to deployment phase in case you've already done that:

```sh
$ ./deploy.sh
```

You can build and deploy a specific service (By default it skips tests phase):

```sh
$ ./[service-name]/deploy.sh -d
```

Add `-t` to run skip tests phase before deploying the service :

```sh
$ ./[service-name]/deploy.sh -t -d
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
127.0.0.1:8000
```

You can check running services and their stats by navigating to Consul panel :

```sh
127.0.0.1:8500
```

You can scale up the service as you wish by running :

```sh
docker-compose scale ${service-name}=${number-of-instances}
```

### Todos

- Write MORE Tests
- Add security and authorization layers

## Contact

- Feel free to contact me : <oussama.lahmidi@icloud.com>
- LinkedIn page : [Oussama Lahmidi](https://linkedin.com/in/oussama-lahmidi)
