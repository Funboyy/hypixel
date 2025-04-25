package de.funboyy.addon.hypixel.api.location;

import org.jetbrains.annotations.Nullable;

public interface Mode {

  Type lobbyType();

  String displayName();

  boolean queueable();

  @Nullable
  static Mode of(final Type type, final String name) {
    if (type == null || name == null) {
      return null;
    }

    for (final GameMode mode : GameMode.values()) {
      if (!type.equals(mode.gameType())) {
        continue;
      }

      if (name.equalsIgnoreCase(mode.modeName())) {
        return mode;
      }
    }

    return new UnknownMode(type, name);
  }

}
