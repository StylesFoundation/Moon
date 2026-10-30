package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public class RenderEntityModelEvent extends EventCancellable {
   private final EntityLivingBase entity;
   private final ModelBase mainModel;
   private final float limbSwing;
   private final float limbSwingAmount;
   private final float ageInTicks;
   private final float netHeadYaw;
   private final float headPitch;
   private final float scale;
   public boolean pre;

   public EntityLivingBase getEntity() {
      return this.entity;
   }

   public ModelBase getMainModel() {
      return this.mainModel;
   }

   public float getLimbSwing() {
      return this.limbSwing;
   }

   public float getLimbSwingAmount() {
      return this.limbSwingAmount;
   }

   public float getAgeInTicks() {
      return this.ageInTicks;
   }

   public float getNetHeadYaw() {
      return this.netHeadYaw;
   }

   public float getHeadPitch() {
      return this.headPitch;
   }

   public float getScale() {
      return this.scale;
   }

   public boolean isPre() {
      return this.pre;
   }

   public RenderEntityModelEvent(
      EntityLivingBase entity,
      ModelBase mainModel,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float scale,
      boolean pre
   ) {
      this.entity = entity;
      this.mainModel = mainModel;
      this.limbSwing = limbSwing;
      this.limbSwingAmount = limbSwingAmount;
      this.ageInTicks = ageInTicks;
      this.netHeadYaw = netHeadYaw;
      this.headPitch = headPitch;
      this.scale = scale;
      this.pre = pre;
   }
}
