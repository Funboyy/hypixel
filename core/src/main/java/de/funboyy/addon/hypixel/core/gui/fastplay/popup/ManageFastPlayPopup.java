package de.funboyy.addon.hypixel.core.gui.fastplay.popup;

import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration.FastPlay;
import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.ServerType;
import de.funboyy.addon.hypixel.core.gui.fastplay.activity.FastPlayActivity;
import de.funboyy.addon.hypixel.core.gui.fastplay.widget.FastPlayWidget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;

@Link("manage.lss")
public class ManageFastPlayPopup extends SimpleAdvancedPopup {

  private final SimplePopupButton doneButton;
  private Consumer<FastPlayWidget> saveListener;

  private boolean enabled;
  private ServerType type;
  private GameMode mode;
  private Key key;

  protected ManageFastPlayPopup(final Action action, final FastPlayWidget widget) {
    super.title = Component.translatable(FastPlayActivity.TRANSLATION_PREFIX + ".title." +
        switch (action) {
          case ADD -> "add";
          case EDIT -> "edit";
        });

    if (widget == null) {
      this.enabled = true;
      this.type = null;
      this.mode = null;
      this.key = Key.NONE;
    }

    else {
      final FastPlay fastPlay = widget.fastPlay();

      this.enabled = fastPlay.enabled();
      this.type = fastPlay.type();
      this.mode = fastPlay.mode();
      this.key = fastPlay.key();
    }

    this.doneButton = SimplePopupButton.create(Component
            .translatable(FastPlayActivity.TRANSLATION_PREFIX + ".button.done"),
        button -> this.save());

    super.buttons = new ArrayList<>();
    super.buttons.add(this.doneButton);
    super.buttons.add(SimplePopupButton.create(Component
        .translatable(FastPlayActivity.TRANSLATION_PREFIX + ".button.cancel")));
  }

  public ManageFastPlayPopup() {
    this(Action.ADD, null);
  }

  public ManageFastPlayPopup(final FastPlayWidget widget) {
    this(Action.EDIT, widget);
  }

  @Override
  protected void initializeCustomWidgets(final VerticalListWidget<Widget> container) {
    final DropdownWidget<String> modeWidget = new DropdownWidget<>();

    final HorizontalListWidget enabledList = new HorizontalListWidget();
    enabledList.addId("entry");

    final ComponentWidget enabledLabel = ComponentWidget.i18n(FastPlayActivity.TRANSLATION_PREFIX + ".label.enabled");
    enabledLabel.addId("label");
    enabledList.addEntry(enabledLabel);

    final SwitchWidget enabledWidget = SwitchWidget.create(enabled -> this.enabled = enabled);
    enabledWidget.addId("input");
    enabledWidget.setValue(this.enabled);
    enabledList.addEntry(enabledWidget);

    container.addChild(enabledList);

    final HorizontalListWidget typeList = new HorizontalListWidget();
    typeList.addId("entry");

    final ComponentWidget labelType = ComponentWidget.i18n(FastPlayActivity.TRANSLATION_PREFIX + ".label.type");
    labelType.addId("label");
    typeList.addEntry(labelType);

    final DropdownWidget<String> typeWidget = new DropdownWidget<>();
    typeWidget.addId("input");

    final List<String> types = new ArrayList<>();

    for (final ServerType type : ServerType.values()) {
      if (type.fastPlay()) {
        types.add(type.displayName());
      }
    }

    Collections.sort(types);

    typeWidget.addAll((Collection<String>) types);
    typeWidget.setSelected(this.type == null ? null : this.type.displayName());
    typeWidget.setChangeListener(typeName -> {
      final ServerType type = ServerType.fromName(typeName);

      if (this.type == type) {
        return;
      }

      this.type = type;
      this.mode = null;

      modeWidget.setSelected(null, false);
      modeWidget.clear();

      for (final GameMode mode : GameMode.values()) {
        if (!mode.queueable()) {
          continue;
        }

        if (mode.lobbyType() == type) {
          modeWidget.add(mode.displayName());
        }
      }

      this.doneButton.enabled(this.isValid());
    });
    typeList.addEntry(typeWidget);

    container.addChild(typeList);

    final HorizontalListWidget modeList = new HorizontalListWidget();
    modeList.addId("entry");

    final ComponentWidget labelMode = ComponentWidget.i18n(FastPlayActivity.TRANSLATION_PREFIX + ".label.mode");
    labelMode.addId("label");
    modeList.addEntry(labelMode);

    modeWidget.addId("input");

    if (this.type != null) {
      for (final GameMode mode : GameMode.values()) {
        if (mode.lobbyType() == this.type) {
          modeWidget.add(mode.displayName());
        }
      }
    }

    modeWidget.setSelected(this.mode == null ? null : this.mode.displayName());
    modeWidget.setChangeListener(modeName -> {
      final GameMode mode = GameMode.fromName(modeName);

      if (this.mode == mode) {
        return;
      }

      this.mode = mode;
      this.doneButton.enabled(this.isValid());
    });
    modeList.addEntry(modeWidget);

    container.addChild(modeList);

    final HorizontalListWidget keyList = new HorizontalListWidget();
    keyList.addId("entry");

    final ComponentWidget labelKey = ComponentWidget.i18n(FastPlayActivity.TRANSLATION_PREFIX + ".label.key");
    labelKey.addId("label");
    keyList.addEntry(labelKey);

    final KeybindWidget keyWidget = new KeybindWidget(key -> {
      this.key = key;
      this.doneButton.enabled(this.isValid());
    });
    keyWidget.addId("input");
    keyWidget.key(this.key);
    keyList.addEntry(keyWidget);

    container.addChild(keyList);

    // Can be enabled when initializeButtons is able to be removed
    //this.doneButton.enabled(this.isValid());
    this.doneButton.enabled(true);
  }

  // This only is required, because otherwise the text on the done button will always be gray
  // https://discord.com/channels/412724944112320513/1055415683103596564/1350107792840593558
  @Override
  protected void initializeButtons(final VerticalListWidget<Widget> container) {
    super.initializeButtons(container);

    this.doneButton.enabled(this.isValid());
  }

  private boolean isValid() {
    return this.type != null
        && this.mode != null
        && this.key != null
        && this.key != Key.NONE;
  }

  private void save() {
    if (this.saveListener == null) {
      return;
    }

    final FastPlayWidget widget = new FastPlayWidget(FastPlay.create(this.enabled, this.mode, this.key));

    this.saveListener.accept(widget);
  }

  public ManageFastPlayPopup onSave(final Consumer<FastPlayWidget> consumer) {
    this.saveListener = consumer;
    return this;
  }

  public enum Action {

    ADD,
    EDIT

  }

}
