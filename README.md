# README #

### What is this repository for? ###

Backend developer task.
This repository contains the source code for the tui-homework microservice.
It is a RESTful service that allows users to get general information about his repositories and branches from an external providers (e.g GitHub).

### How to run locally? ###

Command below runs the application with default profile

```
$ mvn spring-boot:run
```
### How to run in Docker? ###

You'll need Docker installed. Optimized Dockerfile can be found in a root folder.
Command below builds the image from a Dockerfile with a `latest` tag.

```
$ docker build -t tui-homework .
```

To run image in container

```
$ docker run --name tui-homework-container -p 8080:8080 tui-homework
```

### How to check API documentation? ###

To view API docs run the service and browse to `host:port/swagger-ui.html`

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### [1.0.0]

#### Added

#### Changed

#### Fixed
 
