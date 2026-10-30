package com.moon.handlers.entitytrack;

import com.moon.module.impl.visual.ESP;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public abstract class EntityRenderPassBase {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   private final ESP esp;

   public abstract void draw();

   public abstract void rebuild();

   public abstract void renderEntities();

   public abstract boolean isEnabled();

   public boolean isValidTarget(Entity entity) {
      MultipleChoiceSetting targets = this.getEsp().getTargets();
      if (entity == mc.thePlayer) {
         return mc.gameSettings.thirdPersonView != 0;
      } else if (entity.Method940() == null) {
         return false;
      } else if (entity.getEntityId() == -84 || entity.getEntityId() == -42) {
         return false;
      } else if (entity.isInvisible() && !targets.hasChecked(ESP.Targets.INVISIBLE)) {
         return false;
      } else if (entity instanceof EntityMob && targets.hasChecked(ESP.Targets.MOB)) {
         return true;
      } else {
         return (entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntitySquid) && targets.hasChecked(ESP.Targets.ANIMAL)
            ? true
            : entity instanceof EntityPlayer && targets.hasChecked(ESP.Targets.PLAYER);
      }
   }

   public ESP getEsp() {
      return this.esp;
   }

   public EntityRenderPassBase(ESP esp) {
      this.esp = esp;
   }
}
