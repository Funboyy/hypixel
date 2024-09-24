package de.funboyy.addon.hypixel.core.configuration;

import de.funboyy.addon.hypixel.api.configuration.TipConfiguration;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultTipConfiguration extends Config implements TipConfiguration {

  @SwitchSetting
  @ShowSettingInParent
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SliderSetting(min = 5f, max = 30f)
  private final ConfigProperty<Integer> delay = new ConfigProperty<>(10);

  @SwitchSetting
  private final ConfigProperty<Boolean> showMessage = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> showErrorMessage = new ConfigProperty<>(false);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  @Override
  public ConfigProperty<Integer> delay() {
    return this.delay;
  }

  @Override
  public ConfigProperty<Boolean> showMessage() {
    return this.showMessage;
  }

  @Override
  public ConfigProperty<Boolean> showErrorMessage() {
    return this.showErrorMessage;
  }

}
