package de.funboyy.addon.hypixel.api.location;

public class UnknownType implements Type {

  private final String name;

  public UnknownType(final String name) {
    this.name = name;
  }

  @Override
  public String displayName() {
    return this.name;
  }

  @Override
  public boolean fastPlay() {
    return false;
  }

  @Override
  public boolean isLimbo() {
    return false;
  }

}
