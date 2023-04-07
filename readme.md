### ChatGPT Telegram bot.
This is java spring-boot app that is using OpenAPI through Telegram bot.

#### Configuration
It is configured using spring properties.
> Important! \
> In order to use this bot this properties needs to be changed:
> - bot.username
> - bot.token
> - bot.chat-gpt-token

#### Test scenario: 

1. Build project:
    ```
    mvn clean package
    ```
2. Run the application via spring-boot run
3. Communicate with Telegram bot under the name, that was configured via **bot.username** property.
---
![logo.png](src/main/resources/logo.png)