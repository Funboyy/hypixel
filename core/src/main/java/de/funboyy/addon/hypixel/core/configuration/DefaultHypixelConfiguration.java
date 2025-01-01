package de.funboyy.addon.hypixel.core.configuration;

import de.funboyy.addon.hypixel.api.configuration.AutoMessageConfiguration;
import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration;
import de.funboyy.addon.hypixel.api.configuration.HypixelConfiguration;
import de.funboyy.addon.hypixel.api.configuration.TipConfiguration;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class DefaultHypixelConfiguration extends AddonConfig implements HypixelConfiguration {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  private final DefaultAutoMessageConfiguration autoMessageConfiguration = new DefaultAutoMessageConfiguration();

  private final DefaultFastPlayConfiguration fastPlayConfiguration = new DefaultFastPlayConfiguration();

  private final DefaultTipConfiguration tipConfiguration = new DefaultTipConfiguration();

  @SwitchSetting
  @VersionCompatibility("1.8.9")
  private final ConfigProperty<Boolean> glowing = new ConfigProperty<>(true);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  @Override
  public AutoMessageConfiguration autoMessageConfiguration() {
    return this.autoMessageConfiguration;
  }

  @Override
  public FastPlayConfiguration fastPlayConfiguration() {
    return this.fastPlayConfiguration;
  }

  @Override
  public TipConfiguration tipConfiguration() {
    return this.tipConfiguration;
  }

  @Override
  public ConfigProperty<Boolean> glowing() {
    return this.glowing;
  }
}
