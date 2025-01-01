package de.funboyy.addon.hypixel.api.legacy;

import net.labymod.api.client.entity.player.Player;

public interface LegacyEntity extends Player {

  boolean isGlowing();

  void setGlowing(final boolean glowing);

}
