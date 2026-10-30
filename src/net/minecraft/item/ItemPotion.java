package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPotion extends Item {
   private final Map<Integer, List<PotionEffect>> effectCache = Maps.newHashMap();
   private static final Map<List<PotionEffect>, Integer> SUB_ITEMS_CACHE = Maps.newLinkedHashMap();

   public ItemPotion() {
      this.setMaxStackSize(1);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(CreativeTabs.tabBrewing);
   }

   public List<PotionEffect> getEffects(ItemStack itemStack) {
      if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
         ArrayList var10 = Lists.newArrayList();
         NBTTagList var6 = itemStack.getTagCompound().getTagList("CustomPotionEffects", 10);

         for (int var7 = 0; var7 < var6.tagCount(); var7++) {
            PotionEffect var9 = PotionEffect.readCustomPotionEffectFromNBT(var6.getCompoundTagAt(var7));
            if (var9 != null) {
               var10.add(var9);
            }
         }

         return var10;
      } else {
         List var5 = this.effectCache.get(itemStack.getMetadata());
         if (var5 == null) {
            var5 = PotionHelper.getPotionEffects(itemStack.getMetadata(), false);
            this.effectCache.put(itemStack.getMetadata(), var5);
         }

         return var5;
      }
   }

   public List<PotionEffect> getEffects(int integer) {
      List var5 = this.effectCache.get(integer);
      if (var5 == null) {
         var5 = PotionHelper.getPotionEffects(integer, false);
         this.effectCache.put(integer, var5);
      }

      return var5;
   }

   @Override
   public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (!entityPlayer.capabilities.isCreativeMode) {
         itemStack.stackSize--;
      }

      if (!world.isRemote) {
         List<PotionEffect> var7 = this.getEffects(itemStack);
         if (var7 != null) {
            for (PotionEffect var9 : var7) {
               entityPlayer.addPotionEffect(new PotionEffect(var9));
            }
         }
      }

      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      if (!entityPlayer.capabilities.isCreativeMode) {
         if (itemStack.stackSize <= 0) {
            return new ItemStack(Items.glass_bottle);
         }

         entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
      }

      return itemStack;
   }

   @Override
   public int getMaxItemUseDuration(ItemStack itemStack) {
      return 32;
   }

   @Override
   public EnumAction getItemUseAction(ItemStack itemStack) {
      return EnumAction.DRINK;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (isSplash(itemStack.getMetadata())) {
         if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize--;
         }

         world.playSoundAtEntity(entityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
         if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityPotion(world, entityPlayer, itemStack));
         }

         entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      } else {
         entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
      }

      return itemStack;
   }

   public static boolean isSplash(int integer) {
      return (integer & 16384) != 0;
   }

   public int getColorFromDamage(int integer) {
      return PotionHelper.getLiquidColor(integer, false);
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      return integer > 0 ? 16777215 : this.getColorFromDamage(itemStack.getMetadata());
   }

   public boolean isEffectInstant(int integer) {
      List<PotionEffect> var5 = this.getEffects(integer);
      if (var5 != null && !var5.isEmpty()) {
         for (PotionEffect var7 : var5) {
            if (Potion.potionTypes[var7.getPotionID()].isInstant()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public String getItemStackDisplayName(ItemStack itemStack) {
      if (itemStack.getMetadata() == 0) {
         return StatCollector.translateToLocal("item.emptyPotion.name").trim();
      }

      String var5 = "";
      if (isSplash(itemStack.getMetadata())) {
         var5 = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
      }

      List<PotionEffect> var6 = Items.potionitem.getEffects(itemStack);
      if (var6 != null && !var6.isEmpty()) {
         String var7 = ((PotionEffect)var6.get(0)).getEffectName() + ".postfix";
         return var5 + StatCollector.translateToLocal(var7).trim();
      } else {
         return StatCollector.translateToLocal(PotionHelper.getPotionPrefix(itemStack.getMetadata())).trim() + " " + super.getItemStackDisplayName(itemStack);
      }
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      if (itemStack.getMetadata() != 0) {
         List<PotionEffect> var8 = Items.potionitem.getEffects(itemStack);
         HashMultimap<String, AttributeModifier> var9 = HashMultimap.create();
         if (var8 != null && !var8.isEmpty()) {
            for (PotionEffect var11 : var8) {
               String var12 = StatCollector.translateToLocal(var11.getEffectName()).trim();
               Potion var13 = Potion.potionTypes[var11.getPotionID()];
               Map<IAttribute, AttributeModifier> var14 = var13.getAttributeModifierMap();
               if (var14 != null && var14.size() > 0) {
                  for (Entry<IAttribute, AttributeModifier> var16 : var14.entrySet()) {
                     AttributeModifier var17 = var16.getValue();
                     AttributeModifier var18 = new AttributeModifier(
                        var17.getName(), var13.getAttributeModifierAmount(var11.getAmplifier(), var17), var17.getOperation()
                     );
                     var9.put(var16.getKey().getAttributeUnlocalizedName(), var18);
                  }
               }

               if (var11.getAmplifier() > 0) {
                  var12 = var12 + " " + StatCollector.translateToLocal("potion.potency." + var11.getAmplifier()).trim();
               }

               if (var11.getDuration() > 20) {
                  var12 = var12 + " (" + Potion.getDurationString(var11) + ")";
               }

               if (var13.isBadEffect()) {
                  list.add(EnumChatFormatting.RED + var12);
               } else {
                  list.add(EnumChatFormatting.GRAY + var12);
               }
            }
         } else {
            String var10 = StatCollector.translateToLocal("potion.empty").trim();
            list.add(EnumChatFormatting.GRAY + var10);
         }

         if (!var9.isEmpty()) {
            list.add("");
            list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));

            for (Entry<String, AttributeModifier> var21 : var9.entries()) {
               AttributeModifier var22 = var21.getValue();
               double var23 = var22.getAmount();
               double var24;
               if (var22.getOperation() != 1 && var22.getOperation() != 2) {
                  var24 = var22.getAmount();
               } else {
                  var24 = var22.getAmount() * 100.0;
               }

               if (var23 > 0.0) {
                  list.add(
                     EnumChatFormatting.BLUE
                        + StatCollector.translateToLocalFormatted(
                           "attribute.modifier.plus." + var22.getOperation(),
                           ItemStack.DECIMALFORMAT.format(var24),
                           StatCollector.translateToLocal("attribute.name." + (String)var21.getKey())
                        )
                  );
               } else if (var23 < 0.0) {
                  var24 *= -1.0;
                  list.add(
                     EnumChatFormatting.RED
                        + StatCollector.translateToLocalFormatted(
                           "attribute.modifier.take." + var22.getOperation(),
                           ItemStack.DECIMALFORMAT.format(var24),
                           StatCollector.translateToLocal("attribute.name." + (String)var21.getKey())
                        )
                  );
               }
            }
         }
      }
   }

   @Override
   public boolean hasEffect(ItemStack itemStack) {
      List<PotionEffect> var5 = this.getEffects(itemStack);
      return var5 != null && !var5.isEmpty();
   }

   @Override
   public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      super.getSubItems(item, creativeTabs, list);
      if (SUB_ITEMS_CACHE.isEmpty()) {
         for (int var7 = 0; var7 <= 15; var7++) {
            for (int var8 = 0; var8 <= 1; var8++) {
               int var9;
               if (var8 == 0) {
                  var9 = var7 | 8192;
               } else {
                  var9 = var7 | 16384;
               }

               for (int var10 = 0; var10 <= 2; var10++) {
                  int var11 = var9;
                  if (var10 != 0) {
                     if (var10 == 1) {
                        var11 = var9 | 32;
                     } else if (var10 == 2) {
                        var11 = var9 | 64;
                     }
                  }

                  List var12 = PotionHelper.getPotionEffects(var11, false);
                  if (var12 != null && !var12.isEmpty()) {
                     SUB_ITEMS_CACHE.put(var12, var11);
                  }
               }
            }
         }
      }

      for (int var14 : SUB_ITEMS_CACHE.values()) {
         list.add(new ItemStack(item, 1, var14));
      }
   }
}
