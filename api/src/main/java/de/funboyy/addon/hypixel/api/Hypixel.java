package de.funboyy.addon.hypixel.api;

import de.funboyy.addon.hypixel.api.chat.ChatRegistry;
import de.funboyy.addon.hypixel.api.configuration.HypixelConfiguration;
import de.funboyy.addon.hypixel.api.controller.AutoMessageController;
import de.funboyy.addon.hypixel.api.controller.FastPlayController;
import de.funboyy.addon.hypixel.api.controller.TipController;
import de.funboyy.addon.hypixel.api.location.LocationController;
import net.labymod.api.client.component.Component;

public interface Hypixel {

  HypixelConfiguration configuration();

  String namespace();

  void displayChatMessage(final Component message);

  boolean isEnabled();

  ChatRegistry chatRegistry();

  LocationController locationController();

  AutoMessageController autoMessageController();

  FastPlayController fastPlayController();

  TipController tipController();

}
