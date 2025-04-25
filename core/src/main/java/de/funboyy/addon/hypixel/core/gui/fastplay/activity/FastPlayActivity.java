package de.funboyy.addon.hypixel.core.gui.fastplay.activity;

import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration;
import de.funboyy.addon.hypixel.api.configuration.FastPlayConfiguration.FastPlay;
import de.funboyy.addon.hypixel.core.HypixelAddon;
import de.funboyy.addon.hypixel.core.gui.fastplay.popup.DeleteFastPlayPopup;
import de.funboyy.addon.hypixel.core.gui.fastplay.popup.ManageFastPlayPopup;
import de.funboyy.addon.hypixel.core.gui.fastplay.widget.FastPlayWidget;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoActivity
@Link("overview.lss")
public class FastPlayActivity extends Activity {

  public static final String TRANSLATION_PREFIX = HypixelAddon.get().namespace() + ".gui.fastPlay";

  private final FastPlayConfiguration configuration;

  private final VerticalListWidget<FastPlayWidget> fastPlayList;
  private final List<FastPlayWidget> fastPlayWidgets;

  private FastPlayWidget selectedFastPlay;

  private ButtonWidget removeButton;
  private ButtonWidget editButton;

  public FastPlayActivity() {
    this.configuration = HypixelAddon.get().configuration().fastPlayConfiguration();

    this.fastPlayWidgets = new ArrayList<>();

    for (final FastPlay fastPlay : this.configuration.elements()) {
      this.fastPlayWidgets.add(new FastPlayWidget(fastPlay));
    }

    this.fastPlayList = new VerticalListWidget<>();
    this.fastPlayList.addId("fast-play-list");
    this.fastPlayList.setSelectCallback(fastPlayWidget -> {
      final FastPlayWidget selectedFastPlay = this.fastPlayList.listSession().getSelectedEntry();

      if (selectedFastPlay == null || selectedFastPlay.fastPlay() != fastPlayWidget.fastPlay()) {
        this.editButton.setEnabled(true);
        this.removeButton.setEnabled(true);
      }
    });

    this.fastPlayList.setDoubleClickCallback(fastPlayWidget -> this.action(Action.EDIT));
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
    menu.addId("button-menu");

    menu.addEntry(ButtonWidget.i18n(TRANSLATION_PREFIX + ".button.add", () -> this.action(Action.ADD)));

    this.editButton = ButtonWidget.i18n(TRANSLATION_PREFIX + ".button.edit", () -> this.action(Action.EDIT));
    this.editButton.setEnabled(this.selectedFastPlay != null);
    menu.addEntry(this.editButton);

    this.removeButton = ButtonWidget.i18n(TRANSLATION_PREFIX + ".button.remove", () -> this.action(Action.REMOVE));
    this.removeButton.setEnabled(this.selectedFastPlay != null);
    menu.addEntry(this.removeButton);

    container.addContent(menu);
    super.document().addChild(container);
  }

  @Override
  public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
    try {
      return super.mouseClicked(mouse, mouseButton);
    } finally {
      this.selectedFastPlay = this.fastPlayList.listSession().getSelectedEntry();
      this.removeButton.setEnabled(this.selectedFastPlay != null);
      this.editButton.setEnabled(this.selectedFastPlay != null);
    }
  }

  private void action(final Action action) {
    switch (action) {
      case REMOVE:
        new DeleteFastPlayPopup(this.selectedFastPlay)
            .onDelete(this::deleteFastPlay)
            .displayInOverlay();
        break;
      case EDIT:
        new ManageFastPlayPopup(this.selectedFastPlay)
            .onSave(this::updateFastPlay)
            .displayInOverlay();
        break;
      case ADD:
      default:
        new ManageFastPlayPopup()
            .onSave(this::createFastPlay)
            .displayInOverlay();
        break;
    }
  }

  private void createFastPlay(final FastPlayWidget widget) {
    this.configuration.elements().add(widget.fastPlay());
    this.fastPlayWidgets.add(widget);
    this.selectedFastPlay = widget;
    this.fastPlayList.listSession().setSelectedEntry(this.selectedFastPlay);

    super.reload();
  }

  private void updateFastPlay(final FastPlayWidget widget) {
    if (widget.fastPlay().equals(this.selectedFastPlay.fastPlay())) {
      return;
    }

    this.configuration.elements().remove(this.selectedFastPlay.fastPlay());
    this.fastPlayWidgets.remove(this.selectedFastPlay);

    this.createFastPlay(widget);
  }

  private void deleteFastPlay(final FastPlayWidget widget) {
    this.configuration.elements().remove(widget.fastPlay());
    this.fastPlayWidgets.remove(widget);

    this.selectedFastPlay = null;
    this.fastPlayList.listSession().setSelectedEntry(null);

    super.reload();
  }

  private enum Action {

    ADD,
    EDIT,
    REMOVE

  }

}
