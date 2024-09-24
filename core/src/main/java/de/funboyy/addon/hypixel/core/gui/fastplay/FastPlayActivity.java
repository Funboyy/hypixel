package de.funboyy.addon.hypixel.core.gui.fastplay;

import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration;
import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration.FastPlay;
import de.funboyy.addon.hypixel.api.location.GameMode;
import de.funboyy.addon.hypixel.api.location.ServerType;
import de.funboyy.addon.hypixel.core.HypixelAddon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoActivity
@Link("manage.lss")
@Link("overview.lss")
public class FastPlayActivity extends Activity {

  private final FastPlayConfiguration configuration;
  private final String translationPrefix;

  private final VerticalListWidget<FastPlayWidget> fastPlayList;
  private final List<FastPlayWidget> fastPlayWidgets;

  private FastPlayWidget selectedFastPlay;

  private ButtonWidget removeButton;
  private ButtonWidget editButton;

  private FlexibleContentWidget inputWidget;

  private Action action;
  private ServerType lastType;
  private GameMode lastMode;
  private Key lastKey;

  public FastPlayActivity() {
    final HypixelAddon addon = HypixelAddon.get();

    this.configuration = addon.configuration().fastPlayConfiguration();
    this.translationPrefix = addon.addonInfo().getNamespace() + ".gui.fastPlay.";

    this.fastPlayWidgets = new ArrayList<>();

    for (final FastPlay fastPlay : this.configuration.elements()) {
      this.fastPlayWidgets.add(new FastPlayWidget(fastPlay));
    }

    this.fastPlayList = new VerticalListWidget<>();
    this.fastPlayList.addId("fast-play-list");
    this.fastPlayList.setSelectCallback(fastPlayWidget -> {
      final FastPlayWidget selectedNameTag = this.fastPlayList.listSession().getSelectedEntry();

      if (selectedNameTag == null || selectedNameTag.fastPlay() != fastPlayWidget.fastPlay()) {
        this.editButton.setEnabled(true);
        this.removeButton.setEnabled(true);
      }
    });

    this.fastPlayList.setDoubleClickCallback(nameTagWidget -> this.action(Action.EDIT));
  }

  @Override
  public void initialize(final Parent parent) {
    super.initialize(parent);

    final FlexibleContentWidget container = new FlexibleContentWidget();
    container.addId("fast-play-container");

    for (final FastPlayWidget fastPlayWidget : this.fastPlayWidgets) {
      this.fastPlayList.addChild(fastPlayWidget);
    }

    container.addFlexibleContent(new ScrollWidget(this.fastPlayList));

    this.selectedFastPlay = this.fastPlayList.listSession().getSelectedEntry();

    final HorizontalListWidget menu = new HorizontalListWidget();
    menu.addId("overview-button-menu");

    menu.addEntry(ButtonWidget.i18n("labymod.ui.button.add", () -> this.action(Action.ADD)));

    this.editButton = ButtonWidget.i18n("labymod.ui.button.edit", () -> this.action(Action.EDIT));
    this.editButton.setEnabled(this.selectedFastPlay != null);
    menu.addEntry(this.editButton);

    this.removeButton = ButtonWidget.i18n("labymod.ui.button.remove", () -> this.action(Action.REMOVE));
    this.removeButton.setEnabled(this.selectedFastPlay != null);
    menu.addEntry(this.removeButton);

    container.addContent(menu);
    this.document().addChild(container);

    if (this.action == null) {
      return;
    }

    final DivWidget manageContainer = new DivWidget();
    manageContainer.addId("manage-container");

    final Widget overlayWidget;

    switch (this.action) {
      case ADD -> {
        final FastPlayWidget newFastPlayWidget = new FastPlayWidget(FastPlay.createDefault());
        overlayWidget = this.initializeManageContainer(newFastPlayWidget);
      }
      case EDIT -> overlayWidget = this.initializeManageContainer(this.selectedFastPlay);
      case REMOVE -> overlayWidget = this.initializeRemoveContainer(this.selectedFastPlay);
      default -> overlayWidget = null;
    }

    if (overlayWidget == null) {
      return;
    }

    manageContainer.addChild(overlayWidget);
    this.document().addChild(manageContainer);
  }

  private FlexibleContentWidget initializeRemoveContainer(final FastPlayWidget fastPlayWidget) {
    this.inputWidget = new FlexibleContentWidget();
    this.inputWidget.addId("remove-container");

    final ComponentWidget confirmationWidget = ComponentWidget.i18n(this.translationPrefix + "title.remove");
    confirmationWidget.addId("remove-confirmation");
    this.inputWidget.addContent(confirmationWidget);

    final FastPlayWidget previewWidget = new FastPlayWidget(fastPlayWidget.fastPlay());
    previewWidget.addId("remove-preview");
    this.inputWidget.addContent(previewWidget);

    final HorizontalListWidget menu = new HorizontalListWidget();
    menu.addId("remove-button-menu");

    menu.addEntry(ButtonWidget.i18n("labymod.ui.button.remove", () -> {
      this.configuration.elements().remove(fastPlayWidget.fastPlay());
      this.fastPlayWidgets.remove(fastPlayWidget);
      this.fastPlayList.listSession().setSelectedEntry(null);
      this.action(null);
    }));

    menu.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.action(null)));
    this.inputWidget.addContent(menu);

    return this.inputWidget;
  }

  private DivWidget initializeManageContainer(final FastPlayWidget fastPlayWidget) {
    this.lastType = fastPlayWidget.fastPlay().type();
    this.lastMode = fastPlayWidget.fastPlay().mode();
    this.lastKey = fastPlayWidget.fastPlay().key();

    final DropdownWidget<String> modeWidget = new DropdownWidget<>();
    final ButtonWidget doneButton = ButtonWidget.i18n("labymod.ui.button.done");

    final DivWidget inputContainer = new DivWidget();
    inputContainer.addId("input-container");

    this.inputWidget = new FlexibleContentWidget();
    this.inputWidget.addId("input-list");

    final ComponentWidget titleLabel = ComponentWidget.i18n(this.translationPrefix + "title.manage");
    titleLabel.addId("title");

    this.inputWidget.addContent(titleLabel);

    final HorizontalListWidget enabledList = new HorizontalListWidget();
    enabledList.addId("entry");

    final ComponentWidget enabledLabel = ComponentWidget.i18n(this.translationPrefix + "enabled");
    enabledLabel.addId("label");
    enabledList.addEntry(enabledLabel);

    final SwitchWidget enabledWidget = SwitchWidget.create(null);
    enabledWidget.addId("input");
    enabledWidget.setValue(fastPlayWidget.fastPlay().enabled());
    enabledList.addEntry(enabledWidget);

    this.inputWidget.addContent(enabledList);

    final HorizontalListWidget typeList = new HorizontalListWidget();
    typeList.addId("entry");

    final ComponentWidget labelType = ComponentWidget.i18n(this.translationPrefix + "type");
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
    typeWidget.setSelected(this.lastType == null ? null : this.lastType.displayName());
    typeWidget.setChangeListener(typeName -> {
      final ServerType newType = ServerType.fromName(typeName);

      if (newType == this.lastType) {
        return;
      }

      this.lastType = newType;

      modeWidget.setSelected(null, false);
      modeWidget.clear();

      for (final GameMode mode : GameMode.values()) {
        if (mode.lobbyType() == newType) {
          modeWidget.add(mode.displayName());
        }
      }

      doneButton.setEnabled(this.lastType != null && this.lastMode != null
          && this.lastKey != null && this.lastKey != Key.NONE);
    });
    typeList.addEntry(typeWidget);

    this.inputWidget.addContent(typeList);

    final HorizontalListWidget modeList = new HorizontalListWidget();
    modeList.addId("entry");

    final ComponentWidget labelMode = ComponentWidget.i18n(this.translationPrefix + "mode");
    labelMode.addId("label");
    modeList.addEntry(labelMode);

    modeWidget.addId("input");

    if (this.lastType != null) {
      for (final GameMode mode : GameMode.values()) {
        if (mode.lobbyType() == this.lastType) {
          modeWidget.add(mode.displayName());
        }
      }
    }

    modeWidget.setSelected(this.lastMode == null ? null : this.lastMode.displayName());
    modeWidget.setChangeListener(modeName -> {
      final GameMode newMode = GameMode.fromName(modeName);

      if (newMode == this.lastMode) {
        return;
      }

      this.lastMode = GameMode.fromName(modeName);

      doneButton.setEnabled(this.lastType != null && this.lastMode != null
          && this.lastKey != null && this.lastKey != Key.NONE);
    });
    modeList.addEntry(modeWidget);

    this.inputWidget.addContent(modeList);

    final HorizontalListWidget keyList = new HorizontalListWidget();
    keyList.addId("entry");

    final ComponentWidget labelKey = ComponentWidget.i18n(this.translationPrefix + "key");
    labelKey.addId("label");
    keyList.addEntry(labelKey);

    final KeybindWidget keyWidget = new KeybindWidget(key -> {
      this.lastKey = key;

      doneButton.setEnabled(this.lastType != null && this.lastMode != null
          && this.lastKey != null && this.lastKey != Key.NONE);
    });
    keyWidget.addId("input");
    keyWidget.key(this.lastKey);
    keyList.addEntry(keyWidget);

    this.inputWidget.addContent(keyList);

    final HorizontalListWidget buttonList = new HorizontalListWidget();
    buttonList.addId("edit-button-menu");

    doneButton.setEnabled(this.lastType != null && this.lastMode != null
        && this.lastKey != null && this.lastKey != Key.NONE);
    doneButton.setPressable(() -> {
      final FastPlay fastPlay = fastPlayWidget.fastPlay();
      fastPlay.enabled(enabledWidget.getValue());
      fastPlay.type(ServerType.fromName(typeWidget.getSelected()));
      fastPlay.mode(GameMode.fromName(modeWidget.getSelected()));
      fastPlay.key(keyWidget.key());

      if (this.action == Action.ADD) {
        this.configuration.elements().add(fastPlay);
        this.fastPlayWidgets.add(fastPlayWidget);
      }

      this.action(null);
    });

    buttonList.addEntry(doneButton);
    buttonList.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.action(null)));
    this.inputWidget.addContent(buttonList);

    inputContainer.addChild(this.inputWidget);

    return inputContainer;
  }

  @Override
  public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
    try {
      if (this.action != null) {
        return this.inputWidget.mouseClicked(mouse, mouseButton);
      }

      return super.mouseClicked(mouse, mouseButton);
    } finally {
      this.selectedFastPlay = this.fastPlayList.listSession().getSelectedEntry();
      this.removeButton.setEnabled(this.selectedFastPlay != null);
      this.editButton.setEnabled(this.selectedFastPlay != null);
    }
  }

  @Override
  public boolean keyPressed(final Key key, final InputType type) {
    if (key == Key.ESCAPE && this.action != null) {
      this.action(null);
      return true;
    }

    return super.keyPressed(key, type);
  }

  private void action(final Action action) {
    this.action = action;
    this.reload();
  }

  private enum Action {

    ADD,
    EDIT,
    REMOVE

  }

}
