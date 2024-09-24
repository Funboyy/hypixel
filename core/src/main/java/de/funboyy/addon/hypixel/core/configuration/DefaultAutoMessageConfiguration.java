package de.funboyy.addon.hypixel.core.configuration;

import de.funboyy.addon.hypixel.api.configuration.AutoMessageConfiguration;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultAutoMessageConfiguration extends Config implements AutoMessageConfiguration {

  @SwitchSetting
  @ShowSettingInParent
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @TextFieldSetting
  private final ConfigProperty<String> text = new ConfigProperty<>("gg");

  @TextFieldSetting
  private final ConfigProperty<String> repeatText = new ConfigProperty<>("good game");

  @SliderSetting(min = 0f, max = 5f)
  private final ConfigProperty<Integer> delay = new ConfigProperty<>(1);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  @Override
  public ConfigProperty<String> text() {
    return this.text;
  }

  @Override
  public ConfigProperty<String> repeatText() {
    return this.repeatText;
  }

  @Override
  public ConfigProperty<Integer> delay() {
    return this.delay;
  }

}
