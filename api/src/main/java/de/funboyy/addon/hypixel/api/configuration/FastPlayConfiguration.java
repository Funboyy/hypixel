package de.funboyy.addon.hypixel.api.configuration;

import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.ServerType;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface FastPlayConfiguration extends ConfigAccessor {

  ConfigProperty<Boolean> enabled();

  List<FastPlay> elements();

  Activity manageElements();

  ConfigProperty<Key> playAgain();

  ConfigProperty<Boolean> auto();

  ConfigProperty<Integer> delay();

  class FastPlay {

    private boolean enabled;
    private GameMode mode;
    private Key key;

    private FastPlay(final boolean enabled, final GameMode mode, final Key key) {
      this.enabled = enabled;
      this.mode = mode;
      this.key = key;
    }

    public static FastPlay create(final boolean enabled, final GameMode mode, final Key key) {
      return new FastPlay(enabled, mode, key);
    }

    public boolean enabled() {
      return this.enabled;
    }

    public void enabled(final boolean enabled) {
      this.enabled = enabled;
    }

    public ServerType type() {
      return this.mode.lobbyType();
    }

    public GameMode mode() {
      return this.mode;
    }

    public void mode(final GameMode mode) {
      this.mode = mode;
    }

    public Key key() {
      return this.key;
    }

    public void key(final Key key) {
      this.key = key;
    }

    public String displayName() {
      return this.mode.lobbyType().displayName() + " - " + this.mode.displayName();
    }

  }

}
