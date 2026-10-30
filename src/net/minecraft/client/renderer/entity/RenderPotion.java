package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSnowball<EntityPotion> {
   public RenderPotion(RenderManager renderManager, RenderItem renderItem) {
      super(renderManager, Items.potionitem, renderItem);
   }

   public ItemStack func_177082_d(EntityPotion entityPotion) {
      return new ItemStack(this.field_177084_a, 1, entityPotion.getPotionDamage());
   }
}
