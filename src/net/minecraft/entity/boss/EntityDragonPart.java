package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class EntityDragonPart extends Entity {
   public final IEntityMultiPart entityDragonObj;
   public final String partName;

   public EntityDragonPart(IEntityMultiPart iEntityMultiPart, String string, float float3, float float4) {
      super(iEntityMultiPart.getWorld());
      this.setSize(float3, float4);
      this.entityDragonObj = iEntityMultiPart;
      this.partName = string;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      return !this.isEntityInvulnerable(damageSource) && this.entityDragonObj.attackEntityFromPart(this, damageSource, float2);
   }

   @Override
   public boolean isEntityEqual(Entity entity) {
      return this == entity || this.entityDragonObj == entity;
   }
}
