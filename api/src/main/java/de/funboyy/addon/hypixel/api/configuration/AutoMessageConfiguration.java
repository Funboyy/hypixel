package de.funboyy.addon.hypixel.api.configuration;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface AutoMessageConfiguration extends ConfigAccessor {

  ConfigProperty<Boolean> enabled();

  ConfigProperty<String> text();

  ConfigProperty<String> repeatText();

  ConfigProperty<Integer> delay();

}
