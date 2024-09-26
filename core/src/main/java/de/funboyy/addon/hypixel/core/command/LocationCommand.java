package de.funboyy.addon.hypixel.core.command;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.location.Location;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;

public class LocationCommand extends Command {

  private static final TextColor INFO_COLOR = TextColor.color(213, 213, 128);

  private final Hypixel hypixel;

  public LocationCommand(final Hypixel hypixel) {
    super("location", "loc");

    this.hypixel = hypixel;
  }

  @Override
  public boolean execute(final String prefix, final String[] arguments) {
    if (!this.hypixel.isEnabled()) {
      return false;
    }

    final Location location = this.hypixel.locationController().location();
    final Component message = Component.text("Server: ", NamedTextColor.GRAY)
        .append(Component.text(location.server(), INFO_COLOR));

    if (!location.type().isLimbo()) {
      message.append(Component.text(", ServerType: ", NamedTextColor.GRAY))
          .append(Component.text(location.type().displayName(), INFO_COLOR));
    }

    if (location.mode() != null) {
      message.append(Component.text(", GameMode: ", NamedTextColor.GRAY))
          .append(Component.text(location.mode().displayName(), INFO_COLOR));
    }

    if (location.map() != null) {
      message.append(Component.text(", Map: ", NamedTextColor.GRAY))
          .append(Component.text(location.map(), INFO_COLOR));
    }

    if (location.name() != null) {
      message.append(Component.text(", Name: ", NamedTextColor.GRAY))
          .append(Component.text(location.name(), INFO_COLOR));
    }

    this.hypixel.displayChatMessage(message);
    return true;
  }
}
