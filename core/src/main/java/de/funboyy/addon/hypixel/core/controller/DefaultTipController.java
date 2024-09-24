package de.funboyy.addon.hypixel.core.controller;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.chat.ChatRegistry;
import de.funboyy.addon.hypixel.api.chat.filter.AdvancedChatFilter;
import de.funboyy.addon.hypixel.api.chat.filter.SimpleChatFilter;
import de.funboyy.addon.hypixel.api.configuration.TipConfiguration;
import de.funboyy.addon.hypixel.api.controller.TipController;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import net.labymod.api.Laby;

public class DefaultTipController implements TipController {

  private static final Pattern TIP_PATTERN = Pattern.compile(
      "^You tipped \\d+ players?(?: in \\d+ (?:different )?games?)?!$");

  private static final String ANTI_SPAM_MESSAGE = "Slow down! You can only use /tip every few seconds.";
  private static final String NO_ACTIVE_BOOSTER_MESSAGE = "No one has a network booster active right now! Try again later.";
  private static final String ALREADY_TIPPED_MESSAGE = "You already tipped everyone that has boosters active, so there isn't anybody to be tipped right now!";

  private final TipConfiguration configuration;
  private ScheduledExecutorService scheduler;

  public DefaultTipController(final Hypixel hypixel) {
    this.configuration = hypixel.configuration().tipConfiguration();
    final ChatRegistry chatRegistry = hypixel.chatRegistry();

    this.configuration.enabled().addChangeListener(enabled -> {
      if (enabled) {
        this.start();
        return;
      }

      this.stop();
    });

    chatRegistry.register("tip_success", new AdvancedChatFilter(TIP_PATTERN,
        matcher -> this.configuration.enabled().get() && !this.configuration.showMessage().get()));
    chatRegistry.register("tip_fail_spam", new SimpleChatFilter(ANTI_SPAM_MESSAGE,
        () -> this.configuration.enabled().get() && !this.configuration.showMessage().get()));
    chatRegistry.register("tip_fail_no_active", new SimpleChatFilter(NO_ACTIVE_BOOSTER_MESSAGE,
        () -> this.configuration.enabled().get() && !this.configuration.showErrorMessage().get()));
    chatRegistry.register("tip_fail_already", new SimpleChatFilter(ALREADY_TIPPED_MESSAGE,
        () -> this.configuration.enabled().get() && !this.configuration.showErrorMessage().get()));
  }

  @Override
  public void start() {
    if (this.scheduler != null) {
      return;
    }

    this.scheduler = Executors.newScheduledThreadPool(1);
    this.scheduler.scheduleWithFixedDelay(() -> Laby.references().chatExecutor().chat("/tip all", false),
        10, this.configuration.delay().get() * 60, TimeUnit.SECONDS);
  }

  @Override
  public void stop() {
    if (this.scheduler == null) {
      return;
    }

    this.scheduler.shutdown();
    this.scheduler = null;
  }

}
