package net.minecraft.item;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.realms.Tezzelator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors$ArmoredMob;
import net.minecraft.world.World;

public class ItemArmor extends Item {
   private static final int[] n = new int[]{11, 16, 15, 13};
   public static final String[] EMPTY_SLOT_NAMES = new String[]{
      "minecraft:items/empty_armor_slot_helmet",
      "minecraft:items/empty_armor_slot_chestplate",
      "minecraft:items/empty_armor_slot_leggings",
      "minecraft:items/empty_armor_slot_boots"
   };
   private static final IBehaviorDispenseItem dispenserBehavior = new BehaviorDefaultDispenseItem() {
      @Override
      protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
         BlockPos var6 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
         int var7 = var6.getX();
         int var8 = var6.getY();
         int var9 = var6.getZ();
         AxisAlignedBB var10 = new AxisAlignedBB(var7, var8, var9, var7 + 1, var8 + 1, var9 + 1);
         List var11 = iBlockSource.getWorld()
            .getEntitiesWithinAABB(EntityLivingBase.class, var10, Predicates.and(Tezzelator.d, new EntitySelectors$ArmoredMob(itemStack)));
         if (var11.size() > 0) {
            EntityLivingBase var12 = (EntityLivingBase)var11.get(0);
            int var13 = var12 instanceof EntityPlayer ? 1 : 0;
            int var14 = EntityLiving.getArmorPosition(itemStack);
            ItemStack var15 = itemStack.copy();
            var15.stackSize = 1;
            var12.setCurrentItemOrArmor(var14 - var13, var15);
            if (var12 instanceof EntityLiving) {
               ((EntityLiving)var12).setEquipmentDropChance(var14, 2.0F);
            }

            itemStack.stackSize--;
            return itemStack;
         } else {
            return super.dispenseStack(iBlockSource, itemStack);
         }
      }
   };
   public final int armorType;
   public final int damageReduceAmount;
   public final int renderIndex;
   private final ItemArmor.ArmorMaterial material;

   public ItemArmor(ItemArmor.ArmorMaterial armorMaterial, int integer2, int integer3) {
      this.material = armorMaterial;
      this.armorType = integer3;
      this.renderIndex = integer2;
      this.damageReduceAmount = armorMaterial.getDamageReductionAmount(integer3);
      this.setMaxDamage(armorMaterial.getDurability(integer3));
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabCombat);
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      if (integer > 0) {
         return 16777215;
      }

      int var6 = this.getColor(itemStack);
      if (var6 < 0) {
         var6 = 16777215;
      }

      return var6;
   }

   @Override
   public int getItemEnchantability() {
      return this.material.getEnchantability();
   }

   public ItemArmor.ArmorMaterial getArmorMaterial() {
      return this.material;
   }

   public boolean hasColor(ItemStack itemStack) {
      return this.material == ItemArmor.ArmorMaterial.LEATHER
         && itemStack.hasTagCompound()
         && itemStack.getTagCompound().hasKey("display", 10)
         && itemStack.getTagCompound().getCompoundTag("display").hasKey("color", 3);
   }

   public int getColor(ItemStack itemStack) {
      if (this.material != ItemArmor.ArmorMaterial.LEATHER) {
         return -1;
      }

      NBTTagCompound var5 = itemStack.getTagCompound();
      if (var5 != null) {
         NBTTagCompound var6 = var5.getCompoundTag("display");
         if (var6 != null && var6.hasKey("color", 3)) {
            return var6.getInteger("color");
         }
      }

      return 10511680;
   }

   public void removeColor(ItemStack itemStack) {
      if (this.material == ItemArmor.ArmorMaterial.LEATHER) {
         NBTTagCompound var5 = itemStack.getTagCompound();
         if (var5 != null) {
            NBTTagCompound var6 = var5.getCompoundTag("display");
            if (var6.hasKey("color")) {
               var6.removeTag("color");
            }
         }
      }
   }

   public void setColor(ItemStack itemStack, int integer) {
      if (this.material != ItemArmor.ArmorMaterial.LEATHER) {
         throw new UnsupportedOperationException("Can't dye non-leather!");
      }

      NBTTagCompound var6 = itemStack.getTagCompound();
      if (var6 == null) {
         var6 = new NBTTagCompound();
         itemStack.setTagCompound(var6);
      }

      NBTTagCompound var7 = var6.getCompoundTag("display");
      if (!var6.hasKey("display", 10)) {
         var6.setTag("display", var7);
      }

      var7.setInteger("color", integer);
   }

   @Override
   public boolean getIsRepairable(ItemStack itemStack1, ItemStack itemStack2) {
      return this.material.getRepairItem() == itemStack2.getItem() || super.getIsRepairable(itemStack1, itemStack2);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      int var7 = EntityLiving.getArmorPosition(itemStack) - 1;
      ItemStack var8 = entityPlayer.getCurrentArmor(var7);
      if (var8 == null) {
         entityPlayer.setCurrentItemOrArmor(var7, itemStack.copy());
         itemStack.stackSize = 0;
      }

      return itemStack;
   }

   public enum ArmorMaterial {
      LEATHER("leather", 5, new int[]{1, 3, 2, 1}, 15),
      CHAIN("chainmail", 15, new int[]{2, 5, 4, 1}, 12),
      IRON("iron", 15, new int[]{2, 6, 5, 2}, 9),
      GOLD("gold", 7, new int[]{2, 5, 3, 1}, 25),
      DIAMOND("diamond", 33, new int[]{3, 8, 6, 3}, 10);

      private final String name;
      private final int maxDamageFactor;
      private final int[] damageReductionAmountArray;
      private final int enchantability;

      ArmorMaterial(String string3, int integer4, int[] arr, int integer6) {
         this.name = string3;
         this.maxDamageFactor = integer4;
         this.damageReductionAmountArray = arr;
         this.enchantability = integer6;
      }

      public int getDurability(int integer) {
         return ItemArmor.n[integer] * this.maxDamageFactor;
      }

      public int getDamageReductionAmount(int integer) {
         return this.damageReductionAmountArray[integer];
      }

      public int getEnchantability() {
         return this.enchantability;
      }

      public Item getRepairItem() {
         return this == LEATHER
            ? Items.leather
            : (
               this == CHAIN
                  ? Items.iron_ingot
                  : (this == GOLD ? Items.gold_ingot : (this == IRON ? Items.iron_ingot : (this == DIAMOND ? Items.diamond : null)))
            );
      }

      public String getName() {
         return this.name;
      }

      private static ItemArmor.ArmorMaterial[] Method907() {
         return new ItemArmor.ArmorMaterial[]{LEATHER, CHAIN, IRON, GOLD, DIAMOND};
      }
   }
}
