package de.funboyy.addon.hypixel.api.location;

import org.jetbrains.annotations.NotNull;

public interface Type {

  String displayName();

  boolean fastPlay();

  boolean isLimbo();

  @NotNull
  static Type of(final String name) {
    if (name == null) {
      return ServerType.LIMBO;
    }

    for (final ServerType type : ServerType.values()) {
      if (type.name().equals(name)) {
        return type;
      }
    }

    return new UnknownType(name);
  }

}
