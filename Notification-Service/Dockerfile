
# ---------- build jar ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -Dmaven.test.skip=true clean package
RUN cp target/*.jar app.jar

# ---------- jlink on Alpine JDK (musl) ----------
FROM eclipse-temurin:21-jdk-alpine AS jrebuilder
WORKDIR /opt
COPY --from=builder /app/app.jar /opt/app.jar

# in the Alpine JDK stage
RUN set -eux; \
  MODS="$(jdeps --ignore-missing-deps --print-module-deps /opt/app.jar)"; \
  jlink \
    --add-modules "$MODS,\
jdk.localedata,java.desktop,jdk.unsupported,java.naming,java.sql,\
java.management,jdk.management,java.rmi,java.security.jgss,java.security.sasl,jdk.security.auth,\
java.instrument,java.xml" \
    --no-man-pages --no-header-files --strip-debug --compress=2 \
    --output /opt/jre


# ---------- final Alpine runtime ----------
FROM alpine:3.20
ENV JAVA_HOME=/opt/jre PATH="/opt/jre/bin:${PATH}"
WORKDIR /app
COPY --from=jrebuilder /opt/jre /opt/jre
COPY --from=builder    /app/app.jar /app/app.jar
USER 1000:1000
EXPOSE 7000
ENTRYPOINT ["java","-jar","/app/app.jar"]

