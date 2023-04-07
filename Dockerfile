FROM openjdk:17-jdk-alpine
COPY /target/*.jar chatgpt-telegram-bot-*.jar
ENTRYPOINT ["java","-jar","/chatgpt-telegram-bot-*.jar"]