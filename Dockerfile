FROM openjdk:8
MAINTAINER Balamanova Assem <asema.balamanova777@gmail.com>
ADD build/libs/kindergarden-admin-0.0.1.jar kindergarden-admin-0.0.1.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "kindergarden-admin-0.0.1.jar"]
