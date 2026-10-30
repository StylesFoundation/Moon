package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood {
   private final boolean cooked;

   public ItemFishFood(boolean boolean1) {
      super(0, 0.0F, false);
      this.cooked = boolean1;
   }

   @Override
   public int getHealAmount(ItemStack itemStack) {
      ItemFishFood.FishType var5 = ItemFishFood.FishType.byItemStack(itemStack);
      return this.cooked && var5.canCook() ? var5.Method654() : var5.Method652();
   }

   @Override
   public float getSaturationModifier(ItemStack itemStack) {
      ItemFishFood.FishType var5 = ItemFishFood.FishType.byItemStack(itemStack);
      return this.cooked && var5.canCook() ? var5.Method655() : var5.Method653();
   }

   @Override
   public String getPotionEffect(ItemStack itemStack) {
      return ItemFishFood.FishType.byItemStack(itemStack) == ItemFishFood.FishType.PUFFERFISH ? "+0-1+2+3+13&4-4" : null;
   }

   @Override
   protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      ItemFishFood.FishType var7 = ItemFishFood.FishType.byItemStack(itemStack);
      if (var7 == ItemFishFood.FishType.PUFFERFISH) {
         entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
         entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
         entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
      }

      super.onFoodEaten(itemStack, world, entityPlayer);
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (ItemFishFood.FishType var10 : ItemFishFood.FishType.values()) {
         if (!this.cooked || var10.canCook()) {
            list.add(new ItemStack(this, 1, var10.getMetadata()));
         }
      }
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      ItemFishFood.FishType var5 = ItemFishFood.FishType.byItemStack(itemStack);
      return this.getUnlocalizedName() + "." + var5.getUnlocalizedName() + "." + (this.cooked && var5.canCook() ? "cooked" : "raw");
   }

   public enum FishType {
      COD(0, "cod", 2, 0.1F, 5, 0.6F),
      SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
      CLOWNFISH(2, "clownfish", 1, 0.1F),
      PUFFERFISH(3, "pufferfish", 1, 0.1F);

      private static final Map<Integer, ItemFishFood.FishType> META_LOOKUP = Maps.newHashMap();
      private final int meta;
      private final String unlocalizedName;
      private final int uncookedHealAmount;
      private final float uncookedSaturationModifier;
      private final int cookedHealAmount;
      private final float cookedSaturationModifier;
      private boolean cookable = false;

      FishType(int integer3, String string4, int integer5, float float6, int integer7, float float8) {
         this.meta = integer3;
         this.unlocalizedName = string4;
         this.uncookedHealAmount = integer5;
         this.uncookedSaturationModifier = float6;
         this.cookedHealAmount = integer7;
         this.cookedSaturationModifier = float8;
         this.cookable = true;
      }

      FishType(int integer3, String string4, int integer5, float float6) {
         this.meta = integer3;
         this.unlocalizedName = string4;
         this.uncookedHealAmount = integer5;
         this.uncookedSaturationModifier = float6;
         this.cookedHealAmount = 0;
         this.cookedSaturationModifier = 0.0F;
         this.cookable = false;
      }

      public int getMetadata() {
         return this.meta;
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      public int Method652() {
         return this.uncookedHealAmount;
      }

      public float Method653() {
         return this.uncookedSaturationModifier;
      }

      public int Method654() {
         return this.cookedHealAmount;
      }

      public float Method655() {
         return this.cookedSaturationModifier;
      }

      public boolean canCook() {
         return this.cookable;
      }

      public static ItemFishFood.FishType byMetadata(int integer) {
         ItemFishFood.FishType var4 = META_LOOKUP.get(integer);
         return var4 == null ? COD : var4;
      }

      public static ItemFishFood.FishType byItemStack(ItemStack itemStack) {
         return itemStack.getItem() instanceof ItemFishFood ? byMetadata(itemStack.getMetadata()) : COD;
      }

      private static ItemFishFood.FishType[] Method656() {
         return new ItemFishFood.FishType[]{COD, SALMON, CLOWNFISH, PUFFERFISH};
      }

      static {
         for (ItemFishFood.FishType var11 : values()) {
            META_LOOKUP.put(var11.getMetadata(), var11);
         }
      }
   }
}
