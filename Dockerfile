# Build stage
FROM gradle:jdk11 as builder
RUN mkdir -p /app
WORKDIR /app
COPY . /app
RUN gradle build

# Run stage
FROM adoptopenjdk:11-jre-hotspot
ENV APP_HOME /app
RUN mkdir $APP_HOME

WORKDIR $APP_HOME
# Copy executable jar file from the builder image
COPY --from=builder /app/build/libs/*.jar urlShortener.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "urlShortener.jar"]