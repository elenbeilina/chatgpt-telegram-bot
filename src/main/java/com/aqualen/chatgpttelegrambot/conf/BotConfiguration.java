package com.aqualen.chatgpttelegrambot.conf;

import com.aqualen.chatgpttelegrambot.logic.GptAssisterBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BotConfiguration {
  private final GptAssisterBot gptAssisterBot;

  @EventListener({ContextRefreshedEvent.class})
  public void init() throws TelegramApiException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    try {
      telegramBotsApi.registerBot(gptAssisterBot);
    } catch (TelegramApiException e) {
      log.warn(e.getMessage(), e);
    }
  }
}
