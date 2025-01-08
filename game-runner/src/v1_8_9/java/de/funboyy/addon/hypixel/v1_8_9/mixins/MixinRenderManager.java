package de.funboyy.addon.hypixel.v1_8_9.mixins;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.legacy.LegacyEntity;
import de.funboyy.addon.hypixel.core.HypixelAddon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public class MixinRenderManager {

  @Unique
  private boolean hypixel$hitBox;

  @Shadow
  private boolean renderOutlines;

  @Shadow
  private boolean debugBoundingBox;

  @Inject(
      method = "doRenderEntity",
      at = @At("HEAD")
  )
  private void hypixel$disableHitBox(final CallbackInfoReturnable<Boolean> returnable) {
    if (this.renderOutlines) {
      this.hypixel$hitBox = this.debugBoundingBox;
      this.debugBoundingBox = false;
    }
  }

  @Inject(
      method = "doRenderEntity",
      at = @At("RETURN")
  )
  private void hypixel$restoreHitBox(final CallbackInfoReturnable<Boolean> returnable) {
    if (this.renderOutlines) {
      this.debugBoundingBox = this.hypixel$hitBox;
    }
  }

  @Inject(
      method = "renderEntitySimple",
      at = @At("HEAD"),
      cancellable = true
  )
  private void murder$checkHitBox(final Entity entity, final float partialTicks,
      final CallbackInfoReturnable<Boolean> returnable) {

    if (!this.renderOutlines) {
      return;
    }

    if (!hypixel$isEnabled()) {
      returnable.cancel();
      return;
    }

    if (!(entity instanceof LegacyEntity legacyEntity)) {
      returnable.cancel();
      return;
    }

    if (!legacyEntity.isGlowing()) {
      returnable.cancel();
    }
  }

  @Unique
  private boolean hypixel$isEnabled() {
    final Hypixel hypixel = HypixelAddon.get();

    return hypixel.isEnabled() && hypixel.configuration().glowing().get();
  }

}
