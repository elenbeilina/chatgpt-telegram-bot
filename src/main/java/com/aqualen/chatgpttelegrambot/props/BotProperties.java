package com.aqualen.chatgpttelegrambot.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@RefreshScope
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("bot")
public class BotProperties {
  private String username;
  private String token;
  private String chatGptToken;
  private String chatGptUrl;
  private List<String> accessList;
}
