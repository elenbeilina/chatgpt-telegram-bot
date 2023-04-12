### ChatGPT Telegram bot.
This is java spring-boot app that is using OpenAPI through Telegram bot.

#### Configuration
It is configured using spring properties.
> Important! \
> In order to use this bot this properties needs to be changed:
> - bot.username
> - bot.token
> - bot.chat-gpt-token

#### Local test scenario: 

1. Build project:
    ```
    mvn clean package
    ```
2. Download key.json for accessing GCP locally. \
      IAM & Admin -> Service Accounts -> App Engine default service account(or create your own and use it for app engine) -> KEYS -> ADD KEY -> Create new key \
      Provide path to the key.json in application.properties.
3. Uncomment lines from the application.properties.
4. Run the application via spring-boot run
5. Communicate with Telegram bot under the name, that was configured via **bot.username** property.

#### GCP test scenario:
1. Initialize the Cloud SDK:
   ```
   gcloud init
   ```
2. Create an App Engine application:
   ```
   gcloud app create
   ```
3. Authorize the Cloud SDK to use GCP APIs in your local environment:
   ```
   gcloud auth application-default login
   ```
4. Enable the [Secret Manager API](https://console.cloud.google.com/flows/enableapi?apiid=secretmanager.googleapis.com&redirect=https://console.cloud.google.com&_ga=2.72503123.1749283848.1589680102-1322801348.1576371208&_gac=1.225110888.1587192241.CjwKCAjwp-X0BRAFEiwAheRui4GkVAiJEcD-d_dhMaMnTeAmRAMMUBXLV45atuLUiiLinEjPGLLbuhoCzD8QAvD_BwE).
5. Add Secret Manager Admin role to the App Engine service account.
6. Create new secrets for bot configuration file. Example:
   ```
   echo -n "my super secret data" | gcloud secrets create chatgpt-telegram-bot-telegram-token \
    --replication-policy="automatic" \
    --data-file=-
   ```
   
   ```
   echo -n "my super secret data" | gcloud secrets create chatgpt-telegram-bot-open-ai-token \
    --replication-policy="automatic" \
    --data-file=-
   ```
7. Follow this [instruction](https://cloud.google.com/build/docs/automating-builds/github/connect-repo-github) for building repo from Github.
8. Add 3 roles to cloudbuild.gserviceaccount.com on the IAM page:
   — App Engine Admin
   — Secret Manager Admin
9. [Enable](https://console.developers.google.com/apis/library/appengine.googleapis.com) App Engine Admin API
10. Create build trigger via this [instruction](https://cloud.google.com/build/docs/automating-builds/create-manage-triggers)
---
![logo.png](src/main/resources/logo.png)