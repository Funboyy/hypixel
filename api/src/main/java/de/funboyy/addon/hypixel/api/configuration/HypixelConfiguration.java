package de.funboyy.addon.hypixel.api.configuration;

import net.labymod.api.configuration.loader.ConfigAccessor;

public interface HypixelConfiguration extends ConfigAccessor {

  AutoMessageConfiguration autoMessageConfiguration();

  FastPlayConfiguration fastPlayConfiguration();

  TipConfiguration tipConfiguration();

}
