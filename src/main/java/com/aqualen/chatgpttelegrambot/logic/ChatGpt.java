package com.aqualen.chatgpttelegrambot.logic;

import com.aqualen.chatgpttelegrambot.props.BotProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatGpt {

  private final BotProperties botProperties;
  private final OkHttpClient client;

  @SneakyThrows
  public String sendToChatGPT(String chatGptRequest) {
    log.info("Request: " + chatGptRequest);
    HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl
            .parse(botProperties.getChatGptUrl()))
        .newBuilder();

    JSONObject data = new JSONObject()
        .put("model", "text-davinci-003")
        .put("prompt", chatGptRequest)
        .put("max_tokens", 4000)
        .put("temperature", 1.0);
    Request request = new Request.Builder()
        .header("Authorization",
            "Bearer " + botProperties.getChatGptToken())
        .url(httpBuilder.build())
        .post(RequestBody.Companion.create(
            data.toString(), MediaType.get(APPLICATION_JSON.getMimeType()))).build();

    String responseBody = client.newCall(request).execute().body().string();
    JSONObject response = new JSONObject(responseBody);
    JSONArray choices = (JSONArray) response.get("choices");
    JSONObject choice = (JSONObject) choices.get(0);

    return choice.get("text").toString();
  }
}
