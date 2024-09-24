package de.funboyy.addon.hypixel.api.chat.filter;

public record SimpleChatFilter(String text, ChatAction action) implements ChatFilter {

  @FunctionalInterface
  public interface ChatAction {

    boolean execute();

  }

}
