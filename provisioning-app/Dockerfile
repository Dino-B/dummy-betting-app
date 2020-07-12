FROM openjdk:11 as builder

ARG MAVEN_VERSION=3.5.4
ARG USER_HOME_DIR="/root"
ARG SHA=22cac91b3557586bb1eba326f2f7727543ff15e3
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSLk -o /tmp/apache-maven.tar.gz "${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz" \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha1sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN mkdir -p "$USER_HOME_DIR/.m2"

RUN mkdir -p /usr/src/app/data-access-utils /usr/src/app/provisioning-app

WORKDIR /usr/src/app/data-access-utils

COPY data-access-utils/ .

RUN mvn clean install

WORKDIR /usr/src/app/provisioning-app

COPY provisioning-app/ .

RUN mvn clean package

FROM openjdk:11

RUN mkdir -p /usr/src/app/dependency

COPY --from=builder /usr/src/app/provisioning-app/target/dependency/*.jar /usr/src/app/dependency/
COPY --from=builder /usr/src/app/provisioning-app/target/*.jar /usr/src/app/

CMD "java"
