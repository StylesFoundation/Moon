package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFood extends Item {
   public final int itemUseDuration = 32;
   private final int healAmount;
   private final float saturationModifier;
   private final boolean isWolfsFavoriteMeat;
   private boolean alwaysEdible;
   private int potionId;
   private int potionDuration;
   private int potionAmplifier;
   private float potionEffectProbability;

   public ItemFood(int integer, float float2, boolean boolean3) {
      this.healAmount = integer;
      this.isWolfsFavoriteMeat = boolean3;
      this.saturationModifier = float2;
      this.setCreativeTab(CreativeTabs.tabFood);
   }

   public ItemFood(int integer, boolean boolean2) {
      this(integer, 0.6F, boolean2);
   }

   @Override
   public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      itemStack.stackSize--;
      entityPlayer.getFoodStats().addStats(this, itemStack);
      world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
      this.onFoodEaten(itemStack, world, entityPlayer);
      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStack;
   }

   protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (!world.isRemote && this.potionId > 0 && world.rand.nextFloat() < this.potionEffectProbability) {
         entityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
      }
   }

   @Override
   public int getMaxItemUseDuration(ItemStack itemStack) {
      return 32;
   }

   @Override
   public EnumAction getItemUseAction(ItemStack itemStack) {
      return EnumAction.EAT;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (entityPlayer.canEat(this.alwaysEdible)) {
         entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
      }

      return itemStack;
   }

   public int getHealAmount(ItemStack itemStack) {
      return this.healAmount;
   }

   public float getSaturationModifier(ItemStack itemStack) {
      return this.saturationModifier;
   }

   public boolean isWolfsFavoriteMeat() {
      return this.isWolfsFavoriteMeat;
   }

   public ItemFood setPotionEffect(int integer1, int integer2, int integer3, float float4) {
      this.potionId = integer1;
      this.potionDuration = integer2;
      this.potionAmplifier = integer3;
      this.potionEffectProbability = float4;
      return this;
   }

   public ItemFood setAlwaysEdible() {
      this.alwaysEdible = true;
      return this;
   }
}
