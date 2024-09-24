package de.funboyy.addon.hypixel.api.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Location(
    @NotNull String server,
    @Nullable String name,
    @NotNull ServerType type,
    @Nullable GameMode mode,
    @Nullable String map) {

}
