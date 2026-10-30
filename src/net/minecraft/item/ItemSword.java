package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemSword extends Item {
   private final float attackDamage;
   private final Item.ToolMaterial material;

   public ItemSword(Item.ToolMaterial toolMaterial) {
      this.material = toolMaterial;
      this.maxStackSize = 1;
      this.setMaxDamage(toolMaterial.getMaxUses());
      this.setCreativeTab(CreativeTabs.tabCombat);
      this.attackDamage = 4.0F + toolMaterial.getDamageVsEntity();
   }

   public float getDamageVsEntity() {
      return this.material.getDamageVsEntity();
   }

   @Override
   public float getStrVsBlock(ItemStack itemStack, Block block) {
      if (block == Blocks.web) {
         return 15.0F;
      }

      Material var6 = block.getMaterial();
      return var6 != Material.plants && var6 != Material.vine && var6 != Material.coral && var6 != Material.leaves && var6 != Material.gourd ? 1.0F : 1.5F;
   }

   @Override
   public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase2, EntityLivingBase entityLivingBase3) {
      itemStack.damageItem(1, entityLivingBase3);
      return true;
   }

   @Override
   public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, BlockPos blockPos, EntityLivingBase entityLivingBase) {
      if (block.getBlockHardness(world, blockPos) != 0.0) {
         itemStack.damageItem(2, entityLivingBase);
      }

      return true;
   }

   @Override
   public boolean isFull3D() {
      return true;
   }

   @Override
   public EnumAction getItemUseAction(ItemStack itemStack) {
      return EnumAction.BLOCK;
   }

   @Override
   public int getMaxItemUseDuration(ItemStack itemStack) {
      return 72000;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
      return itemStack;
   }

   @Override
   public boolean canHarvestBlock(Block block) {
      return block == Blocks.web;
   }

   @Override
   public int getItemEnchantability() {
      return this.material.getEnchantability();
   }

   public String getToolMaterialName() {
      return this.material.toString();
   }

   @Override
   public boolean getIsRepairable(ItemStack itemStack1, ItemStack itemStack2) {
      return this.material.getRepairItem() == itemStack2.getItem() || super.getIsRepairable(itemStack1, itemStack2);
   }

   @Override
   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
      Multimap var1 = super.getItemAttributeModifiers();
      var1.put(
         SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", this.attackDamage, 0)
      );
      return var1;
   }
}
