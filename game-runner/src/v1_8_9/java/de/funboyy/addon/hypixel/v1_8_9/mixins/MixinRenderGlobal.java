package de.funboyy.addon.hypixel.v1_8_9.mixins;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.legacy.LegacyEntity;
import de.funboyy.addon.hypixel.core.HypixelAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

  @Final
  @Shadow
  private Minecraft mc;

  @Redirect(
      method = "isRenderEntityOutlines",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"
      )
  )
  public boolean glow$byPassSpectatorCheck(final EntityPlayerSP player) {
    if (!this.hypixel$isEnabled()) {
      return player.isSpectator();
    }

    return true;
  }

  @Redirect(
      method = "isRenderEntityOutlines",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"
      )
  )
  public boolean glow$byPassKeyCheck(final KeyBinding keyBinding) {
    if (!this.hypixel$isEnabled()) {
      return keyBinding.isKeyDown();
    }

    return true;
  }

  @Redirect(
      method = "renderEntities",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/Entity;isInRangeToRender3d(DDD)Z",
          ordinal = 1
      )
  )
  private boolean glow$forceRenderRange(final Entity entity, final double x, final double y,
      final double z, final Entity renderViewEntity, final ICamera camera, final float partialTicks) {

    if (!entity.isInRangeToRender3d(x, y, z)) {
      return false;
    }

    if (!this.hypixel$isEnabled()) {
      return true;
    }

    final boolean sleeping = renderViewEntity instanceof EntityLivingBase entityLiving
        && entityLiving.isPlayerSleeping();

    if (entity == renderViewEntity && this.mc.gameSettings.thirdPersonView == 0 && !sleeping) {
      return false;
    }

    if (entity instanceof LegacyEntity legacyEntity && legacyEntity.isGlowing()) {
      return true;
    }

    return this.mc.thePlayer.isSpectator()
        && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown()
        && entity instanceof EntityPlayer
        && (entity.ignoreFrustumCheck
        || camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox())
        || entity.isRiding());
  }

  @Unique
  private boolean hypixel$isEnabled() {
    final Hypixel hypixel = HypixelAddon.get();

    return hypixel.isEnabled() && hypixel.configuration().glowing().get();
  }

}
