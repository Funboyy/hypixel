package de.funboyy.addon.hypixel.api.location;

public class UnknownMode implements Mode {

  private final Type type;
  private final String name;

  public UnknownMode(final Type type, final String name) {
    this.type = type;
    this.name = name;
  }

  @Override
  public Type lobbyType() {
    return this.type;
  }

  @Override
  public String displayName() {
    return this.name;
  }

  @Override
  public boolean queueable() {
    return false;
  }

}
