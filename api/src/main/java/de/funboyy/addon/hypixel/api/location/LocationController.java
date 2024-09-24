package de.funboyy.addon.hypixel.api.location;

public interface LocationController {

  void join(final GameMode mode);

  void rejoin();

  void requestLocation();

  Location location();

}
