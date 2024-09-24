package de.funboyy.addon.hypixel.api.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ServerType {

  LIMBO("Limbo", -1),

  MAIN("Main Lobby"),
  TOURNAMENT("Tournament Hall"),

  QUAKECRAFT("Quakecraft", 2),
  WALLS("Walls", 3),
  PAINTBALL("Paintball", 4),
  SURVIVAL_GAMES("Blitz Survival Games", 5, true),
  TNTGAMES("The TNT Games", 6, true),
  VAMPIREZ("VampireZ", 7),
  WALLS3("Mega Walls", 13, true),
  ARCADE("Arcade", 14, true),
  ARENA("Arena Brawl", 17),
  MCGO("Cops and Crims", 21, true),
  UHC("UHC Champions", 20, true),
  BATTLEGROUND("Warlords", 23, true),
  SUPER_SMASH("Smash Heroes", 24, true),
  GINGERBREAD("Turbo Kart Racers", 25),
  HOUSING("Housing", 26),
  SKYWARS("SkyWars", 51, true),
  TRUE_COMBAT("Crazy Walls", 52),
  SPEED_UHC("Speed UHC", 54),
  SKYCLASH("SkyClash", 55),
  LEGACY("Classic Games", 56, true),
  PROTOTYPE("Prototype", 57, true),
  BEDWARS("Bed Wars", 58, true),
  MURDER_MYSTERY("Murder Mystery", 59, true),
  BUILD_BATTLE("Build Battle", 60, true),
  DUELS("Duels", 61, true),
  SKYBLOCK("SkyBlock", 63),
  PIT("Pit", 64, true),
  REPLAY("Replay", 65),
  SMP("SMP", 67),
  WOOL_GAMES("Wool Games", 68, true);

  private final String name;
  private final int id;
  private final boolean fastPlay;

  ServerType(final String name) {
    this.name = name;
    this.id = 0;
    this.fastPlay = false;
  }

  ServerType(final String name, final int id) {
    this.name = name;
    this.id = id;
    this.fastPlay = false;
  }

  ServerType(final String name, final int id, final boolean fastPlay) {
    this.name = name;
    this.id = id;
    this.fastPlay = fastPlay;
  }

  public String displayName() {
    return this.name;
  }

  public int id() {
    return this.id;
  }

  public boolean fastPlay() {
    return this.fastPlay;
  }

  public boolean isLimbo() {
    return this.id == -1;
  }

  public boolean isSpecial() {
    return this.id == 0 || this.isLimbo();
  }

  @NotNull
  public static ServerType of(final String name) {
    if (name == null) {
      return LIMBO;
    }

    for (final ServerType type : values()) {
      if (type.name().equals(name)) {
        return type;
      }
    }

    return LIMBO;
  }

  @Nullable
  public static ServerType fromName(final String name) {
    if (name == null) {
      return null;
    }

    for (final ServerType type : values()) {
      if (type.displayName().equals(name)) {
        return type;
      }
    }

    return null;
  }

}
