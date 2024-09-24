package de.funboyy.addon.hypixel.api;

import net.labymod.api.client.network.server.AbstractServer;
import net.labymod.api.event.Phase;

public class HypixelServer extends AbstractServer {

  private final Hypixel hypixel;

  private boolean online;

  public HypixelServer(final Hypixel hypixel) {
    super("hypixel");

    this.hypixel = hypixel;
  }

  @Override
  public void loginOrSwitch(final LoginPhase phase) {
    this.online = true;

    if (phase == LoginPhase.LOGIN) {
      this.hypixel.tipController().start();
    }

    this.hypixel.locationController().requestLocation();
  }

  @Override
  public void disconnect(final Phase phase) {
    this.online = false;

    if (phase == Phase.PRE) {
      this.hypixel.tipController().stop();
    }
  }

  public boolean isOnline() {
    return this.online;
  }

}
