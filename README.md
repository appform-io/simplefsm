# Simple FSM

[![Build](https://github.com/appform-io/simplefsm/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/appform-io/simplefsm/actions/workflows/build.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=appform-io_simplefsm&metric=coverage)](https://sonarcloud.io/dashboard?id=appform-io_simplefsm)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=appform-io_simplefsm&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=appform-io_simplefsm)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=appform-io_simplefsm&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=appform-io_simplefsm)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=appform-io_simplefsm&metric=security_rating)](https://sonarcloud.io/dashboard?id=appform-io_simplefsm)
[![javadoc](https://javadoc.io/badge2/io.appform.simplefsm/simplefsm/javadoc.svg)](https://javadoc.io/doc/io.appform.simplefsm/simplefsm)

A library to build event driven state machines in your code.

## Why?

State machines are a core construct for many systems that monitor system states across many objects and manage them in
memory.

## Usage
In order to use the library the following needs to be done.

### Use the dependency
Use the following maven dependency
```xml
<dependency>
    <groupId>io.appform.simplefsm</groupId>
    <artifactId>simplefsm</artifactId>
    <version>1.0</version>
</dependency>
```

Check StateMachineTest class for basic usage.

## License
Apache 2