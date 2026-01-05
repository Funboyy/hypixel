package de.funboyy.addon.hypixel.core.gson;

import com.google.gson.GsonBuilder;
import de.funboyy.addon.hypixel.api.gson.GameModeTypeAdapter;
import de.funboyy.addon.hypixel.api.location.GameMode;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.JsonConfigLoaderInitializeEvent;

public class JsonConfigLoaderInitializeListener {

  @Subscribe
  public void handleJsonConfigLoaderInitialize(final JsonConfigLoaderInitializeEvent event) {
    final GsonBuilder builder = event.getGsonBuilder();
    builder.registerTypeAdapter(GameMode.class, new GameModeTypeAdapter());
  }

}
