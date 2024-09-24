package de.funboyy.addon.hypixel.api.util;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;

public class GradientBuilder {

  public static GradientBuilder builder() {
    return new GradientBuilder();
  }

  private String text;
  private TextColor start;
  private TextColor end;

  private GradientBuilder() {
  }

  public GradientBuilder text(final String text) {
    this.text = text;

    return this;
  }

  public GradientBuilder color(final TextColor start, final TextColor end) {
    this.start = start;
    this.end = end;

    return this;
  }

  public Component build() {
    if (this.text == null) {
      throw new IllegalStateException("Text cannot be null");
    }

    if (this.text.length() < 2) {
      throw new IllegalStateException("Text must be at least 2 characters long");
    }

    if (this.start == null) {
      throw new IllegalStateException("Start Color cannot be null");
    }

    if (this.end == null) {
      throw new IllegalStateException("End Color cannot be null");
    }

    final Component component = Component.empty();
    final int length = this.text.length();

    for (int i = 0; i < length; i++) {
      component.append(Component.text(this.text.charAt(i),
          this.interpolate((float) i / (length - 1))));
    }

    return component;
  }

  private TextColor interpolate(final float ratio) {
    final Color start = this.start.color();
    final Color end = this.end.color();

    return TextColor.color(
        (int) (start.getRed() + (end.getRed() - start.getRed()) * ratio),
        (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * ratio),
        (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * ratio)
    );
  }

}
