package de.funboyy.addon.hypixel.api.configuration;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface HypixelConfiguration extends ConfigAccessor {

  AutoMessageConfiguration autoMessageConfiguration();

  FastPlayConfiguration fastPlayConfiguration();

  TipConfiguration tipConfiguration();

  ConfigProperty<Boolean> glowing();

}
