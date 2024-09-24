package de.funboyy.addon.hypixel.core.chat;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.chat.ChatRegistry;
import de.funboyy.addon.hypixel.api.chat.filter.AdvancedChatFilter;
import de.funboyy.addon.hypixel.api.chat.filter.ChatFilter;
import de.funboyy.addon.hypixel.api.chat.filter.SimpleChatFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.KeyValue;

public class DefaultChatRegistry implements ChatRegistry {

  private final Hypixel hypixel;
  private final List<KeyValue<ChatFilter>> elements;

  public DefaultChatRegistry(final Hypixel hypixel) {
    this.hypixel = hypixel;
    this.elements = new ArrayList<>();
  }

  @Override
  public List<KeyValue<ChatFilter>> getElements() {
    return this.elements;
  }

  @Subscribe
  public void handleChatReceive(final ChatReceiveEvent event) {
    if (!this.hypixel.isEnabled()) {
      return;
    }

    final String text = event.chatMessage().getPlainText();
    final AtomicBoolean cancel = new AtomicBoolean(event.isCancelled());

    this.forEach(filter -> {
      if (filter instanceof SimpleChatFilter chatFilter) {
        if (!chatFilter.text().equals(text)) {
          return;
        }

        if (chatFilter.action().execute()) {
          cancel.set(true);
        }

        return;
      }

      if (filter instanceof AdvancedChatFilter chatFilter) {
        final Matcher matcher = chatFilter.pattern().matcher(text);

        if (!matcher.matches()) {
          return;
        }

        if (chatFilter.action().execute(matcher)) {
          cancel.set(true);
        }
      }
    });

    event.setCancelled(cancel.get());
  }

}
