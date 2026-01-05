package de.funboyy.addon.hypixel.api.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import de.funboyy.addon.hypixel.api.location.GameMode;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.util.gson.LabyGsonTypeAdapter;

public class GameModeTypeAdapter extends LabyGsonTypeAdapter<GameMode> {

  private static final Map<String, GameMode> LEGACY_MAPPING = Map.of(
      "PROTOTYPE_DISASTERS", GameMode.ARCADE_DISASTERS
  );

  @Override
  public JsonElement serialize(final GameMode mode, final Type type, final JsonSerializationContext context) {
    return new JsonPrimitive(mode.name());
  }

  @Override
  public GameMode deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
    if (!json.isJsonPrimitive()) {
      throw new JsonParseException("Cannot deserialize GameMode (non-primitive element)");
    }

    final String name = json.getAsString().trim().toUpperCase(Locale.ROOT);

    for (final GameMode mode : GameMode.values()) {
      if (!mode.name().equals(name)) {
        continue;
      }

      if (!mode.queueable()) {
        throw new JsonParseException("Cannot deserialize GameMode (non-queueable mode)");
      }

      return mode;
    }

    if (LEGACY_MAPPING.containsKey(name)) {
      return LEGACY_MAPPING.get(name);
    }

    throw new JsonParseException("Cannot deserialize GameMode (unknown name)");
  }

}
