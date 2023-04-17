package com.aqualen.chatgpttelegrambot.logic;

import com.aqualen.chatgpttelegrambot.props.BotProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

  @SneakyThrows
  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      var msg = update.getMessage();
      var chatId = String.valueOf(msg.getChatId());
      if(!botProperties.getAccessList().contains(msg.getChat().getUserName())){
        if (msg.getText().equals("/start")) {
          sendNotification(chatId, "Not allowed to use this bot!");
        }
        throw new TelegramApiException("Not allowed to use this bot!");
      }

      if (msg.getText().equals("/start")) {
        String greeting = """
            Hello %s!
            Enter your request for ChatGpt:
            """;
        sendNotification(chatId, String.format(greeting, msg.getChat().getFirstName()));
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
