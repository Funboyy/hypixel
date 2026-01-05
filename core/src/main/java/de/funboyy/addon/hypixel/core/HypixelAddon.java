package de.funboyy.addon.hypixel.core;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.HypixelServer;
import de.funboyy.addon.hypixel.api.chat.ChatRegistry;
import de.funboyy.addon.hypixel.api.controller.AutoMessageController;
import de.funboyy.addon.hypixel.api.controller.FastPlayController;
import de.funboyy.addon.hypixel.api.controller.TipController;
import de.funboyy.addon.hypixel.core.gson.JsonConfigLoaderInitializeListener;
import de.funboyy.addon.hypixel.api.location.LocationController;
import de.funboyy.addon.hypixel.api.util.GradientBuilder;
import de.funboyy.addon.hypixel.core.chat.DefaultChatRegistry;
import de.funboyy.addon.hypixel.core.command.LocationCommand;
import de.funboyy.addon.hypixel.core.configuration.DefaultHypixelConfiguration;
import de.funboyy.addon.hypixel.core.controller.DefaultAutoMessageController;
import de.funboyy.addon.hypixel.core.controller.DefaultFastPlayController;
import de.funboyy.addon.hypixel.core.controller.DefaultTipController;
import de.funboyy.addon.hypixel.core.location.DefaultLocationController;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class HypixelAddon extends LabyAddon<DefaultHypixelConfiguration> implements Hypixel {

  private static final Component PREFIX = Component.text("[", NamedTextColor.DARK_GRAY)
      .append(GradientBuilder.builder().text("Hypixel").color(NamedTextColor.GOLD, NamedTextColor.YELLOW).build())
      .append(Component.text("]", NamedTextColor.DARK_GRAY)).decorate(TextDecoration.BOLD);

  private static HypixelAddon instance;

  public static HypixelAddon get() {
    return instance;
  }

  private final ChatRegistry chatRegistry;
  private final LocationController locationController;
  private final AutoMessageController autoMessageController;
  private final FastPlayController fastPlayController;
  private final TipController tipController;
  private final HypixelServer server;

  public HypixelAddon() {
    instance = this;

    this.chatRegistry = new DefaultChatRegistry(this);
    this.locationController = new DefaultLocationController(this);
    this.fastPlayController = new DefaultFastPlayController(this);
    this.autoMessageController = new DefaultAutoMessageController(this);
    this.tipController = new DefaultTipController(this);
    this.server = new HypixelServer(this);
  }

  @Override
  protected void preConfigurationLoad() {
    super.registerListener(new JsonConfigLoaderInitializeListener());
  }

  @Override
  protected void enable() {
    super.registerSettingCategory();

    super.registerListener(this.chatRegistry);
    super.registerListener(this.fastPlayController);

    super.registerCommand(new LocationCommand(this));

    super.labyAPI().serverController().registerServer(this.server);
  }

  @Override
  protected Class<DefaultHypixelConfiguration> configurationClass() {
    return DefaultHypixelConfiguration.class;
  }

  @Override
  public String namespace() {
    return super.addonInfo().getNamespace();
  }

  @Override
  public void displayChatMessage(final Component message) {
    super.displayMessage(Component.empty().append(PREFIX).append(Component.space()).append(message));
  }

  @Override
  public boolean isEnabled() {
    return super.configuration().enabled().get() && this.server.isOnline();
  }

  @Override
  public ChatRegistry chatRegistry() {
    return this.chatRegistry;
  }

  @Override
  public LocationController locationController() {
    return this.locationController;
  }

  @Override
  public AutoMessageController autoMessageController() {
    return this.autoMessageController;
  }

  @Override
  public FastPlayController fastPlayController() {
    return this.fastPlayController;
  }

  @Override
  public TipController tipController() {
    return this.tipController;
  }

}
