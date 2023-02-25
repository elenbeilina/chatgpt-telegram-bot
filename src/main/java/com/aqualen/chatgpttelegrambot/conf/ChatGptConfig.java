package com.aqualen.chatgpttelegrambot.conf;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ChatGptConfig {

  private static final int TIMEOUT = 30000;

  @Bean
  OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS).build();
  }
}
