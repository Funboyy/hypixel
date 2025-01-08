package de.funboyy.addon.hypixel.v1_8_9.mixins;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.core.HypixelAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

  @Final
  @Shadow
  private Minecraft mc;

  @Shadow
  private Framebuffer entityOutlineFramebuffer;

  @Shadow
  private ShaderGroup entityOutlineShader;

  @Inject(
      method = "isRenderEntityOutlines",
      at = @At("HEAD"),
      cancellable = true
  )
  public void hypixel$byPassChecks(final CallbackInfoReturnable<Boolean> returnable) {
    if (!this.hypixel$isEnabled()) {
      return;
    }

    returnable.setReturnValue(this.entityOutlineFramebuffer != null
        && this.entityOutlineShader != null && this.mc.thePlayer != null);
    returnable.cancel();
  }

  @Unique
  private boolean hypixel$isEnabled() {
    final Hypixel hypixel = HypixelAddon.get();

    return hypixel.isEnabled() && hypixel.configuration().glowing().get();
  }

}
