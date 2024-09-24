package de.funboyy.addon.hypixel.api.configuration;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface TipConfiguration extends ConfigAccessor {

  ConfigProperty<Boolean> enabled();

  ConfigProperty<Integer> delay();

  ConfigProperty<Boolean> showMessage();

  ConfigProperty<Boolean> showErrorMessage();

}
