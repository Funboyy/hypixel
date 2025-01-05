package de.funboyy.addon.hypixel.api.legacy;

import net.labymod.api.client.entity.Entity;

public interface LegacyEntity extends Entity {

  boolean isGlowing();

  void setGlowing(final boolean glowing);

}
