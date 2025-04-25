package de.funboyy.addon.hypixel.core.gui.fastplay.widget;

import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration.FastPlay;
import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.ServerType;
import de.funboyy.addon.hypixel.core.HypixelAddon;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.I18n;

@AutoWidget
public class FastPlayWidget extends SimpleWidget {

  private final FastPlay fastPlay;

  public FastPlayWidget(final FastPlay fastPlay) {
    this.fastPlay = fastPlay;
  }

  @Override
  public void initialize(final Parent parent) {
    super.initialize(parent);

    if (this.fastPlay.enabled()) {
      this.removeId("disabled");
    } else {
      this.addId("disabled");
    }

    final IconWidget iconWidget = new IconWidget(this.iconWidget());
    iconWidget.addId("icon");
    this.addChild(iconWidget);

    final ComponentWidget modeWidget = ComponentWidget.text(this.fastPlay.displayName());
    modeWidget.addId("mode");
    this.addChild(modeWidget);

    final ComponentWidget keyWidget = ComponentWidget.text(this.keyDisplayName(this.fastPlay.key()));
    keyWidget.addId("key");
    this.addChild(keyWidget);
  }

  private Icon iconWidget() {
    final String namespace = HypixelAddon.get().namespace();
    final ServerType type = this.fastPlay.type();

    if (type == null) {
      return Icon.texture(Textures.EMPTY);
    }

    if (type == ServerType.LEGACY) {
      final GameMode mode = this.fastPlay.mode();

      if (mode == null) {
        return Icon.texture(Textures.EMPTY);
      }

      if (mode == GameMode.QUAKE_SOLO || mode == GameMode.QUAKE_TEAMS) {
        return Icon.texture(ResourceLocation.create(namespace, "textures/mode/quake.png"));
      }

      if (mode == GameMode.ARENA_1V1 || mode == GameMode.ARENA_2V2 || mode == GameMode.ARENA_4V4) {
        return Icon.texture(ResourceLocation.create(namespace, "textures/mode/arena.png"));
      }

      return Icon.texture(ResourceLocation.create(namespace,
          String.format("textures/mode/%s.png", mode.name().toLowerCase())));
    }

    if (type == ServerType.PROTOTYPE) {
      if (this.fastPlay.mode() == GameMode.SKYBLOCK) {
        return Icon.texture(ResourceLocation.create(namespace, "textures/mode/skyblock.png"));
      }
    }

    return Icon.texture(ResourceLocation.create(namespace,
        String.format("textures/lobby/%s.png", type.name().toLowerCase())));
  }

  private String keyDisplayName(final Key key) {
    if (key == null || key == Key.NONE) {
      return I18n.translate("labymod.ui.keybind.none");
    }

    if (key instanceof MouseButton) {
      return I18n.translate("labymod.ui.keybind.mouse", key.getName());
    }

    return I18n.translate("labymod.ui.keybind.keyboard", key.getName());
  }

  public FastPlay fastPlay() {
    return this.fastPlay;
  }

}
