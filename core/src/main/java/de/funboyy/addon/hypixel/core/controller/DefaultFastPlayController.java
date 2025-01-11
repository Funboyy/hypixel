package de.funboyy.addon.hypixel.core.controller;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration;
import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration.FastPlay;
import de.funboyy.addon.hypixel.api.controller.FastPlayController;
import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.LocationController;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.KeyEvent.State;

public class DefaultFastPlayController implements FastPlayController {

  private final Hypixel hypixel;
  private final Minecraft minecraft;
  private final FastPlayConfiguration configuration;
  private final LocationController locationController;
  private final ScheduledExecutorService scheduler;

  public DefaultFastPlayController(final Hypixel hypixel) {
    this.hypixel = hypixel;
    this.minecraft = Laby.labyAPI().minecraft();
    this.configuration = this.hypixel.configuration().fastPlayConfiguration();
    this.locationController = this.hypixel.locationController();
    this.scheduler = Executors.newScheduledThreadPool(1);
  }

  @Subscribe
  public void handleKey(final KeyEvent event) {
    if (event.state() != State.UNPRESSED) {
      return;
    }

    if (!this.configuration.enabled().get()) {
      return;
    }

    if (this.minecraft.minecraftWindow().isScreenOpened()) {
      return;
    }

    if (!this.minecraft.isIngame()) {
      return;
    }

    if (event.key() == this.configuration.playAgain().get()) {
      final GameMode mode = this.locationController.lastMode();

      if (mode == null) {
        this.hypixel.displayChatMessage(Component.translatable(this.hypixel.namespace()
            + ".message.playAgain.missing", NamedTextColor.RED));
        return;
      }

      if (mode == GameMode.UNKNOWN) {
        this.hypixel.displayChatMessage(Component.translatable(this.hypixel.namespace()
            + ".message.playAgain.unknown", NamedTextColor.RED));
        return;
      }

      if (!mode.queueable()) {
        this.hypixel.displayChatMessage(Component.translatable(this.hypixel.namespace()
            + ".message.playAgain.disabled", NamedTextColor.RED));
        return;
      }

      this.locationController.join(mode);
      return;
    }

    for (final FastPlay fastPlay : this.configuration.elements()) {
      if (!fastPlay.enabled()) {
        continue;
      }

      if (fastPlay.key() != event.key()) {
        continue;
      }

      this.locationController.join(fastPlay.mode());
      break;
    }
  }

  @Override
  public void handleGameEnd() {
    if (!this.configuration.enabled().get() || !this.configuration.auto().get()) {
      return;
    }

    final int delay = this.configuration.delay().get();
    final GameMode mode = this.locationController.location().mode();

    if (mode == null || mode == GameMode.UNKNOWN || !mode.queueable()) {
      return;
    }

    if (delay == 0) {
      this.locationController.join(mode);
      return;
    }

    this.scheduler.schedule(() -> this.locationController.join(mode), delay, TimeUnit.SECONDS);
  }

}
