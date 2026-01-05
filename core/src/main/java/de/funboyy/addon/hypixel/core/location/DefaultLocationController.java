package de.funboyy.addon.hypixel.core.location;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.chat.filter.AdvancedChatFilter;
import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.Location;
import de.funboyy.addon.hypixel.api.location.LocationController;
import de.funboyy.addon.hypixel.api.location.Mode;
import de.funboyy.addon.hypixel.api.location.ServerType;
import de.funboyy.addon.hypixel.api.location.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.Laby;
import net.labymod.api.util.StringUtil;

public class DefaultLocationController implements LocationController {

  private static final Location LIMBO = new Location("limbo", null, ServerType.LIMBO, null, null);

  private static final Pattern LOCATION_PATTERN = Pattern.compile("^\\{"
      + "\"server\":\"(?<server>[^\"]+)\""
      + "(?:,\"gametype\":\"(?<type>[^\"]+)\")?"
      + "(?:,\"mode\":\"(?<mode>[^\"]+)\")?"
      + "(?:,\"map\":\"(?<map>[^\"]+)\")?"
      + "(?:,\"lobbyname\":\"(?<name>[^\"]+)\")?}$");

  private Location location = LIMBO;
  private Mode lastMode = null;
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

  @Override
  public Mode lastMode() {
    return this.lastMode;
  }

  @SuppressWarnings("SameReturnValue")
  private boolean handleLocation(final Matcher matcher) {
    final String server = StringUtil.parseEscapedUnicode(matcher.group("server"));
    final String name = StringUtil.parseEscapedUnicode(matcher.group("name"));
    final Type type = Type.of(StringUtil.parseEscapedUnicode(matcher.group("type")));
    final Mode mode = Mode.of(type, StringUtil.parseEscapedUnicode(matcher.group("mode")));
    final String map = StringUtil.parseEscapedUnicode(matcher.group("map"));

    this.location = new Location(server, name, type, mode, map);

    if (mode != null && mode.queueable()) {
      this.lastMode = mode;
    }

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
