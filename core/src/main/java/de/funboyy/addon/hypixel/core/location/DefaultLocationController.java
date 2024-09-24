package de.funboyy.addon.hypixel.core.location;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.chat.filter.AdvancedChatFilter;
import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.ServerType;
import de.funboyy.addon.hypixel.api.location.Location;
import de.funboyy.addon.hypixel.api.location.LocationController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.Laby;

public class DefaultLocationController implements LocationController {

  private static final Location LIMBO = new Location("limbo", null, ServerType.LIMBO, null, null);

  private static final Pattern LOCATION_PATTERN = Pattern.compile("^\\{"
      + "\"server\":\"(?<server>[^\"]+)\""
      + "(?:,\"gametype\":\"(?<type>[^\"]+)\")?"
      + "(?:,\"mode\":\"(?<mode>[^\"]+)\")?"
      + "(?:,\"map\":\"(?<map>[^\"]+)\")?"
      + "(?:,\"lobbyname\":\"(?<name>[^\"]+)\")?}$");

  private Location location = LIMBO;
  private boolean retry = true;

  public DefaultLocationController(final Hypixel hypixel) {
    hypixel.chatRegistry().register("location_detection",
        new AdvancedChatFilter(LOCATION_PATTERN, this::handleLocation));
  }

  @Override
  public void join(final GameMode mode) {
    Laby.references().chatExecutor().chat("/play " + mode.name(), false);
  }

  @Override
  public void requestLocation() {
    Laby.references().chatExecutor().chat("/locraw", false);
  }

  @Override
  public Location location() {
    return this.location;
  }

  private boolean handleLocation(final Matcher matcher) {
    final String server = matcher.group("server");
    final String name = matcher.group("name");
    final ServerType type = ServerType.of(matcher.group("type"));
    final GameMode mode = GameMode.of(type, matcher.group("mode"));
    final String map = matcher.group("map");

    this.location = new Location(server, name,  type, mode, map);

    if (!server.equals("limbo")) {
      this.retry = true;

      return true;
    }

    if (this.retry) {
      this.retry = false;

      this.requestLocation();
    }

    return true;
  }

}
