package com.aqualen.chatgpttelegrambot.logic;

import com.aqualen.chatgpttelegrambot.props.BotProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class GptAssisterBot extends TelegramLongPollingBot {

  private final BotProperties botProperties;
  private final ChatGpt chatGpt;

  public GptAssisterBot(BotProperties botProperties,
                        @Value("${bot.token}") String botToken,
                        ChatGpt chatGpt) {
    super(botToken);
    this.botProperties = botProperties;
    this.chatGpt = chatGpt;
  }

  @Override
  public String getBotUsername() {
    return botProperties.getUsername();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      var msg = update.getMessage();
      var chatId = String.valueOf(msg.getChatId());

      if (msg.getText().equals("/start")) {
        sendNotification(chatId, "Enter request for ChatGpt:");
      } else {
        var reply = chatGpt.sendToChatGPT(msg.getText());
        sendNotification(chatId, reply);
      }
    }
  }

  @SneakyThrows
  private void sendNotification(String chatId, String msg) {
    var response = new SendMessage(chatId, msg);
    execute(response);
  }
}
