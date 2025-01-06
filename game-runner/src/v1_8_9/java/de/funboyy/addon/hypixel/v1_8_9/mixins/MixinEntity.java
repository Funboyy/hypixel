package de.funboyy.addon.hypixel.v1_8_9.mixins;

import de.funboyy.addon.hypixel.api.legacy.LegacyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Interface.Remap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
@Implements(
    @Interface(
        iface = LegacyEntity.class,
        prefix = "legacyEntity$",
        remap = Remap.NONE
    )
)
public abstract class MixinEntity implements LegacyEntity {

  @Shadow
  public World worldObj;

  @Shadow
  protected abstract boolean getFlag(final int index);

  @Shadow
  protected abstract void setFlag(final int index, final boolean value);

  @Unique
  private boolean hypixel$glowing;

  public boolean legacyEntity$isGlowing() {
    return this.hypixel$glowing || this.worldObj.isRemote && this.getFlag(6);
  }

  public void legacyEntity$setGlowing(final boolean glowing) {
    this.hypixel$glowing = glowing;

    if (!this.worldObj.isRemote) {
      this.setFlag(6, this.hypixel$glowing);
    }
  }

  @Inject(
      method = "readFromNBT",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/Entity;setSilent(Z)V"
      )
  )
  private void glow$readFromNBT(final NBTTagCompound compound, final CallbackInfo callbackInfo) {
    this.legacyEntity$setGlowing(compound.getBoolean("Glowing"));
  }

}
