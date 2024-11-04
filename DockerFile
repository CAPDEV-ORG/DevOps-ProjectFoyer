FROM openjdk:17-jdk
EXPOSE 8089
ADD target/*.jar tp-foyer-1.0.jar
ENTRYPOINT ["java", "-jar", "/tp-foyer-1.0.jar"]