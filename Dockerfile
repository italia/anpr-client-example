FROM openjdk:8
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN apt-get update && apt-get -y install maven
CMD ["mvn", "clean", "install"]