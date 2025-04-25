package de.funboyy.addon.hypixel.core.gui.fastplay.popup;

import de.funboyy.addon.hypixel.core.gui.fastplay.activity.FastPlayActivity;
import de.funboyy.addon.hypixel.core.gui.fastplay.widget.FastPlayWidget;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;

@Link("delete.lss")
public class DeleteFastPlayPopup extends SimpleAdvancedPopup {

  private final FastPlayWidget widget;
  private Consumer<FastPlayWidget> deleteListener;

  public DeleteFastPlayPopup(final FastPlayWidget widget) {
    super.title = Component.translatable(FastPlayActivity.TRANSLATION_PREFIX + ".title.remove");

    this.widget = widget;

    super.buttons = new ArrayList<>();
    super.buttons.add(SimplePopupButton.create(Component
        .translatable(FastPlayActivity.TRANSLATION_PREFIX + ".button.confirm"),
        button -> this.delete()));
    super.buttons.add(SimplePopupButton.create(Component
        .translatable(FastPlayActivity.TRANSLATION_PREFIX + ".button.cancel")));
  }

  @Override
  protected void initializeCustomWidgets(final VerticalListWidget<Widget> container) {
    container.addChild(new FastPlayWidget(this.widget.fastPlay()));
  }

  private void delete() {
    if (this.deleteListener == null) {
      return;
    }

    this.deleteListener.accept(this.widget);
  }

  public DeleteFastPlayPopup onDelete(final Consumer<FastPlayWidget> consumer) {
    this.deleteListener = consumer;
    return this;
  }

}
