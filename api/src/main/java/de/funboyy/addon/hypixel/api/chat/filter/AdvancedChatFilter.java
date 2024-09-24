package de.funboyy.addon.hypixel.api.chat.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record AdvancedChatFilter(Pattern pattern, ChatAction action) implements ChatFilter {

  @FunctionalInterface
  public interface ChatAction {

    boolean execute(final Matcher matcher);

  }

}
