package de.funboyy.addon.hypixel.api.controller;

import org.jetbrains.annotations.ApiStatus.Internal;

public interface AutoMessageController {

  @Internal
  boolean handleGameEnd();

  @Internal
  boolean handleRepeat();

}
