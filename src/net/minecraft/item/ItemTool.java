package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item {
   private final Set<Block> effectiveBlocks;
   protected float efficiencyOnProperMaterial = 4.0F;
   private final float damageVsEntity;
   protected Item.ToolMaterial toolMaterial;

   protected ItemTool(float float1, Item.ToolMaterial toolMaterial, Set<Block> set) {
      this.toolMaterial = toolMaterial;
      this.effectiveBlocks = set;
      this.maxStackSize = 1;
      this.setMaxDamage(toolMaterial.getMaxUses());
      this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
      this.damageVsEntity = float1 + toolMaterial.getDamageVsEntity();
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public float getStrVsBlock(ItemStack itemStack, Block block) {
      return this.effectiveBlocks.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
   }

   @Override
   public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase2, EntityLivingBase entityLivingBase3) {
      itemStack.damageItem(2, entityLivingBase3);
      return true;
   }

   @Override
   public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, BlockPos blockPos, EntityLivingBase entityLivingBase) {
      if (block.getBlockHardness(world, blockPos) != 0.0) {
         itemStack.damageItem(1, entityLivingBase);
      }

      return true;
   }

   @Override
   public boolean isFull3D() {
      return true;
   }

   public Item.ToolMaterial getToolMaterial() {
      return this.toolMaterial;
   }

   @Override
   public int getItemEnchantability() {
      return this.toolMaterial.getEnchantability();
   }

   public String getToolMaterialName() {
      return this.toolMaterial.toString();
   }

   @Override
   public boolean getIsRepairable(ItemStack itemStack1, ItemStack itemStack2) {
      return this.toolMaterial.getRepairItem() == itemStack2.getItem() || super.getIsRepairable(itemStack1, itemStack2);
   }

   @Override
   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
      Multimap var1 = super.getItemAttributeModifiers();
      var1.put(
         SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", this.damageVsEntity, 0)
      );
      return var1;
   }
}
