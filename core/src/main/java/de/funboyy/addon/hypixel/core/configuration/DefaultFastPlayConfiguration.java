package de.funboyy.addon.hypixel.core.configuration;

import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration;
import de.funboyy.addon.hypixel.core.gui.fastplay.FastPlayActivity;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget.ActivitySetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.MethodOrder;

public class DefaultFastPlayConfiguration extends Config implements FastPlayConfiguration {

  @SwitchSetting
  @ShowSettingInParent
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @Exclude
  private final List<FastPlay> elements = new ArrayList<>();

  @KeyBindSetting(acceptMouseButtons = true)
  private final ConfigProperty<Key> playAgain = new ConfigProperty<>(Key.NONE);

  @SwitchSetting
  private final ConfigProperty<Boolean> auto = new ConfigProperty<>(false);

  @SliderSetting(min = 0f, max = 10f)
  private final ConfigProperty<Integer> delay = new ConfigProperty<>(5);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  @Override
  public List<FastPlay> elements() {
    return this.elements;
  }

  @Override
  @ActivitySetting
  @MethodOrder(after = "enabled")
  public Activity manageElements() {
    return new FastPlayActivity();
  }

  @Override
  public ConfigProperty<Key> playAgain() {
    return this.playAgain;
  }

  @Override
  public ConfigProperty<Boolean> auto() {
    return this.auto;
  }

  @Override
  public ConfigProperty<Integer> delay() {
    return this.delay;
  }

}
