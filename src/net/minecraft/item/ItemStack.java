package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public final class ItemStack {
   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
   public int stackSize;
   public int animationsToGo;
   private Item item;
   private NBTTagCompound stackTagCompound;
   private int itemDamage;
   private EntityItemFrame itemFrame;
   private Block canDestroyCacheBlock = null;
   private boolean canDestroyCacheResult = false;
   private Block canPlaceOnCacheBlock = null;
   private boolean canPlaceOnCacheResult = false;

   public ItemStack(Block block) {
      this(block, 1);
   }

   public ItemStack(Block block, int integer) {
      this(block, integer, 0);
   }

   public ItemStack(Block block, int integer2, int integer3) {
      this(Item.getItemFromBlock(block), integer2, integer3);
   }

   public ItemStack(Item item) {
      this(item, 1);
   }

   public ItemStack(Item item, int integer) {
      this(item, integer, 0);
   }

   public ItemStack(Item item, int integer2, int integer3) {
      this.item = item;
      this.stackSize = integer2;
      this.itemDamage = integer3;
      if (this.itemDamage < 0) {
         this.itemDamage = 0;
      }
   }

   public static ItemStack loadItemStackFromNBT(NBTTagCompound nBTTagCompound) {
      ItemStack var4 = new ItemStack();
      var4.readFromNBT(nBTTagCompound);
      return var4.getItem() != null ? var4 : null;
   }

   private ItemStack() {
   }

   public ItemStack splitStack(int integer) {
      ItemStack var5 = new ItemStack(this.item, integer, this.itemDamage);
      if (this.stackTagCompound != null) {
         var5.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      this.stackSize -= integer;
      return var5;
   }

   public Item getItem() {
      return this.item;
   }

   public boolean onItemUse(EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float5, float float6, float float7) {
      boolean var11 = this.getItem().onItemUse(this, entityPlayer, world, blockPos, enumFacing, float5, float6, float7);
      if (var11) {
         entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
      }

      return var11;
   }

   public float getStrVsBlock(Block block) {
      return this.getItem().getStrVsBlock(this, block);
   }

   public ItemStack useItemRightClick(World world, EntityPlayer entityPlayer) {
      return this.getItem().onItemRightClick(this, world, entityPlayer);
   }

   public ItemStack onItemUseFinish(World world, EntityPlayer entityPlayer) {
      return this.getItem().onItemUseFinish(this, world, entityPlayer);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nBTTagCompound) {
      ResourceLocation var5 = Item.itemRegistry.getNameForObject(this.item);
      nBTTagCompound.setString("id", var5 == null ? "minecraft:air" : var5.toString());
      nBTTagCompound.setByte("Count", (byte)this.stackSize);
      nBTTagCompound.setShort("Damage", (short)this.itemDamage);
      if (this.stackTagCompound != null) {
         nBTTagCompound.setTag("tag", this.stackTagCompound);
      }

      return nBTTagCompound;
   }

   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.hasKey("id", 8)) {
         this.item = Item.getByNameOrId(nBTTagCompound.getString("id"));
      } else {
         this.item = Item.getItemById(nBTTagCompound.getShort("id"));
      }

      this.stackSize = nBTTagCompound.getByte("Count");
      this.itemDamage = nBTTagCompound.getShort("Damage");
      if (this.itemDamage < 0) {
         this.itemDamage = 0;
      }

      if (nBTTagCompound.hasKey("tag", 10)) {
         this.stackTagCompound = nBTTagCompound.getCompoundTag("tag");
         if (this.item != null) {
            this.item.updateItemStackNBT(this.stackTagCompound);
         }
      }
   }

   public int getMaxStackSize() {
      return this.getItem().getItemStackLimit();
   }

   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
   }

   public boolean isItemStackDamageable() {
      return this.item != null && this.item.getMaxDamage() > 0 && (!this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable"));
   }

   public boolean getHasSubtypes() {
      return this.item.getHasSubtypes();
   }

   public boolean isItemDamaged() {
      return this.isItemStackDamageable() && this.itemDamage > 0;
   }

   public int getItemDamage() {
      return this.itemDamage;
   }

   public int getMetadata() {
      return this.itemDamage;
   }

   public void setItemDamage(int integer) {
      this.itemDamage = integer;
      if (this.itemDamage < 0) {
         this.itemDamage = 0;
      }
   }

   public int getMaxDamage() {
      return this.item.getMaxDamage();
   }

   public boolean attemptDamageItem(int integer, Random random) {
      if (!this.isItemStackDamageable()) {
         return false;
      }

      if (integer > 0) {
         int var6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
         int var7 = 0;

         for (int var8 = 0; var6 > 0 && var8 < integer; var8++) {
            if (EnchantmentDurability.negateDamage(this, var6, random)) {
               var7++;
            }
         }

         integer -= var7;
         if (integer <= 0) {
            return false;
         }
      }

      this.itemDamage += integer;
      return this.itemDamage > this.getMaxDamage();
   }

   public void damageItem(int integer, EntityLivingBase entityLivingBase) {
      if ((!(entityLivingBase instanceof EntityPlayer) || !((EntityPlayer)entityLivingBase).capabilities.isCreativeMode)
         && this.isItemStackDamageable()
         && this.attemptDamageItem(integer, entityLivingBase.getRNG())) {
         entityLivingBase.renderBrokenItemStack(this);
         this.stackSize--;
         if (entityLivingBase instanceof EntityPlayer var6) {
            var6.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
            if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
               var6.destroyCurrentEquippedItem();
            }
         }

         if (this.stackSize < 0) {
            this.stackSize = 0;
         }

         this.itemDamage = 0;
      }
   }

   public void hitEntity(EntityLivingBase entityLivingBase, EntityPlayer entityPlayer) {
      boolean var5 = this.item.hitEntity(this, entityLivingBase, entityPlayer);
      if (var5) {
         entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
      }
   }

   public void onBlockDestroyed(World world, Block block, BlockPos blockPos, EntityPlayer entityPlayer) {
      boolean var7 = this.item.onBlockDestroyed(this, world, block, blockPos, entityPlayer);
      if (var7) {
         entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
      }
   }

   public boolean canHarvestBlock(Block block) {
      return this.item.canHarvestBlock(block);
   }

   public boolean interactWithEntity(EntityPlayer entityPlayer, EntityLivingBase entityLivingBase) {
      return this.item.itemInteractionForEntity(this, entityPlayer, entityLivingBase);
   }

   public ItemStack copy() {
      ItemStack var3 = new ItemStack(this.item, this.stackSize, this.itemDamage);
      if (this.stackTagCompound != null) {
         var3.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      return var3;
   }

   public static boolean areItemStackTagsEqual(ItemStack itemStack1, ItemStack itemStack2) {
      return itemStack1 == null && itemStack2 == null
         || itemStack1 != null
            && itemStack2 != null
            && (itemStack1.stackTagCompound != null || itemStack2.stackTagCompound == null)
            && (itemStack1.stackTagCompound == null || itemStack1.stackTagCompound.equals(itemStack2.stackTagCompound));
   }

   public static boolean areItemStacksEqual(ItemStack itemStack1, ItemStack itemStack2) {
      return itemStack1 == null && itemStack2 == null || itemStack1 != null && itemStack2 != null && itemStack1.isItemStackEqual(itemStack2);
   }

   private boolean isItemStackEqual(ItemStack itemStack) {
      return this.stackSize == itemStack.stackSize
         && this.item == itemStack.item
         && this.itemDamage == itemStack.itemDamage
         && (this.stackTagCompound != null || itemStack.stackTagCompound == null)
         && (this.stackTagCompound == null || this.stackTagCompound.equals(itemStack.stackTagCompound));
   }

   public static boolean areItemsEqual(ItemStack itemStack1, ItemStack itemStack2) {
      return itemStack1 == null && itemStack2 == null || itemStack1 != null && itemStack1.isItemEqual(itemStack2);
   }

   public boolean isItemEqual(ItemStack itemStack) {
      return itemStack != null && this.item == itemStack.item && this.itemDamage == itemStack.itemDamage;
   }

   public String getUnlocalizedName() {
      return this.item.getUnlocalizedName(this);
   }

   public static ItemStack copyItemStack(ItemStack itemStack) {
      return itemStack == null ? null : itemStack.copy();
   }

   @Override
   public String toString() {
      return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
   }

   public void updateAnimation(World world, Entity entity, int integer, boolean boolean4) {
      if (this.animationsToGo > 0) {
         this.animationsToGo--;
      }

      this.item.onUpdate(this, world, entity, integer, boolean4);
   }

   public void onCrafting(World world, EntityPlayer entityPlayer, int integer) {
      entityPlayer.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], integer);
      this.item.onCreated(this, world, entityPlayer);
   }

   public boolean getIsItemStackEqual(ItemStack itemStack) {
      return this.isItemStackEqual(itemStack);
   }

   public int getMaxItemUseDuration() {
      return this.getItem().getMaxItemUseDuration(this);
   }

   public EnumAction getItemUseAction() {
      return this.getItem().getItemUseAction(this);
   }

   public void onPlayerStoppedUsing(World world, EntityPlayer entityPlayer, int integer) {
      this.getItem().onPlayerStoppedUsing(this, world, entityPlayer, integer);
   }

   public boolean hasTagCompound() {
      return this.stackTagCompound != null;
   }

   public NBTTagCompound getTagCompound() {
      return this.stackTagCompound;
   }

   public NBTTagCompound getSubCompound(String string, boolean boolean2) {
      if (this.stackTagCompound != null && this.stackTagCompound.hasKey(string, 10)) {
         return this.stackTagCompound.getCompoundTag(string);
      } else if (boolean2) {
         NBTTagCompound var6 = new NBTTagCompound();
         this.setTagInfo(string, var6);
         return var6;
      } else {
         return null;
      }
   }

   public NBTTagList getEnchantmentTagList() {
      return this.stackTagCompound == null ? null : this.stackTagCompound.getTagList("ench", 10);
   }

   public void setTagCompound(NBTTagCompound nBTTagCompound) {
      this.stackTagCompound = nBTTagCompound;
   }

   public String getDisplayName() {
      String var4 = this.getItem().getItemStackDisplayName(this);
      if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
         NBTTagCompound var5 = this.stackTagCompound.getCompoundTag("display");
         if (var5.hasKey("Name", 8)) {
            var4 = var5.getString("Name");
         }
      }

      return var4;
   }

   public ItemStack setStackDisplayName(String string) {
      if (this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      if (!this.stackTagCompound.hasKey("display", 10)) {
         this.stackTagCompound.setTag("display", new NBTTagCompound());
      }

      this.stackTagCompound.getCompoundTag("display").setString("Name", string);
      return this;
   }

   public void clearCustomName() {
      if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
         NBTTagCompound var4 = this.stackTagCompound.getCompoundTag("display");
         var4.removeTag("Name");
         if (var4.hasNoTags()) {
            this.stackTagCompound.removeTag("display");
            if (this.stackTagCompound.hasNoTags()) {
               this.setTagCompound(null);
            }
         }
      }
   }

   public boolean hasDisplayName() {
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10) && this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8);
   }

   public List<String> getTooltip(EntityPlayer entityPlayer, boolean boolean2) {
      ArrayList var6 = Lists.newArrayList();
      String var7 = this.getDisplayName();
      if (this.hasDisplayName()) {
         var7 = EnumChatFormatting.ITALIC + var7;
      }

      var7 = var7 + EnumChatFormatting.RESET;
      if (boolean2) {
         String var8 = "";
         if (var7.length() > 0) {
            var7 = var7 + " (";
            var8 = ")";
         }

         int var9 = Item.getIdFromItem(this.item);
         if (this.getHasSubtypes()) {
            var7 = var7 + String.format("#%04d/%d%s", var9, this.itemDamage, var8);
         } else {
            var7 = var7 + String.format("#%04d%s", var9, var8);
         }
      } else if (!this.hasDisplayName() && this.item == Items.filled_map) {
         var7 = var7 + " #" + this.itemDamage;
      }

      var6.add(var7);
      int var18 = 0;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
         var18 = this.stackTagCompound.getInteger("HideFlags");
      }

      if ((var18 & 32) == 0) {
         this.item.addInformation(this, entityPlayer, var6, boolean2);
      }

      if (this.hasTagCompound()) {
         if ((var18 & 1) == 0) {
            NBTTagList var19 = this.getEnchantmentTagList();
            if (var19 != null) {
               for (int var10 = 0; var10 < var19.tagCount(); var10++) {
                  short var11 = var19.getCompoundTagAt(var10).getShort("id");
                  short var12 = var19.getCompoundTagAt(var10).getShort("lvl");
                  if (Enchantment.getEnchantmentById(var11) != null) {
                     var6.add(Enchantment.getEnchantmentById(var11).Method532(var12));
                  }
               }
            }
         }

         if (this.stackTagCompound.hasKey("display", 10)) {
            NBTTagCompound var20 = this.stackTagCompound.getCompoundTag("display");
            if (var20.hasKey("color", 3)) {
               if (boolean2) {
                  var6.add("Color: #" + Integer.toHexString(var20.getInteger("color")).toUpperCase());
               } else {
                  var6.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
               }
            }

            if (var20.getTagId("Lore") == 9) {
               NBTTagList var22 = var20.getTagList("Lore", 8);
               if (var22.tagCount() > 0) {
                  for (int var26 = 0; var26 < var22.tagCount(); var26++) {
                     var6.add("" + EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.ITALIC + var22.getStringTagAt(var26));
                  }
               }
            }
         }
      }

      Multimap<String, AttributeModifier> var21 = this.getAttributeModifiers();
      if (!var21.isEmpty() && (var18 & 2) == 0) {
         var6.add("");

         for (Entry<String, AttributeModifier> var27 : var21.entries()) {
            AttributeModifier var30 = (AttributeModifier)var27.getValue();
            double var13 = var30.getAmount();
            if (var30.getID() == Item.itemModifierUUID) {
               var13 += EnchantmentHelper.getModifierForCreature(this, EnumCreatureAttribute.UNDEFINED);
            }

            double var15;
            if (var30.getOperation() != 1 && var30.getOperation() != 2) {
               var15 = var13;
            } else {
               var15 = var13 * 100.0;
            }

            if (var13 > 0.0) {
               var6.add(
                  EnumChatFormatting.BLUE
                     + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.plus." + var30.getOperation(),
                        DECIMALFORMAT.format(var15),
                        StatCollector.translateToLocal("attribute.name." + (String)var27.getKey())
                     )
               );
            } else if (var13 < 0.0) {
               var15 *= -1.0;
               var6.add(
                  EnumChatFormatting.RED
                     + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.take." + var30.getOperation(),
                        DECIMALFORMAT.format(var15),
                        StatCollector.translateToLocal("attribute.name." + (String)var27.getKey())
                     )
               );
            }
         }
      }

      if (this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable") && (var18 & 4) == 0) {
         var6.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
      }

      if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (var18 & 8) == 0) {
         NBTTagList var24 = this.stackTagCompound.getTagList("CanDestroy", 8);
         if (var24.tagCount() > 0) {
            var6.add("");
            var6.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));

            for (int var28 = 0; var28 < var24.tagCount(); var28++) {
               Block var31 = Block.getBlockFromName(var24.getStringTagAt(var28));
               if (var31 != null) {
                  var6.add(EnumChatFormatting.DARK_GRAY + var31.getLocalizedName());
               } else {
                  var6.add(EnumChatFormatting.DARK_GRAY + "missingno");
               }
            }
         }
      }

      if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (var18 & 16) == 0) {
         NBTTagList var25 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
         if (var25.tagCount() > 0) {
            var6.add("");
            var6.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));

            for (int var29 = 0; var29 < var25.tagCount(); var29++) {
               Block var32 = Block.getBlockFromName(var25.getStringTagAt(var29));
               if (var32 != null) {
                  var6.add(EnumChatFormatting.DARK_GRAY + var32.getLocalizedName());
               } else {
                  var6.add(EnumChatFormatting.DARK_GRAY + "missingno");
               }
            }
         }
      }

      if (boolean2) {
         if (this.isItemDamaged()) {
            var6.add("Durability: " + (this.getMaxDamage() - this.getItemDamage()) + " / " + this.getMaxDamage());
         }

         var6.add(EnumChatFormatting.DARK_GRAY + Item.itemRegistry.getNameForObject(this.item).toString());
         if (this.hasTagCompound()) {
            var6.add(EnumChatFormatting.DARK_GRAY + "NBT: " + this.getTagCompound().getKeySet().size() + " tag(s)");
         }
      }

      return var6;
   }

   public boolean hasEffect() {
      return this.getItem().hasEffect(this);
   }

   public EnumRarity getRarity() {
      return this.getItem().getRarity(this);
   }

   public boolean isItemEnchantable() {
      return this.getItem().isItemTool(this) && !this.isItemEnchanted();
   }

   public void addEnchantment(Enchantment enchantment, int integer) {
      if (this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      if (!this.stackTagCompound.hasKey("ench", 9)) {
         this.stackTagCompound.setTag("ench", new NBTTagList());
      }

      NBTTagList var6 = this.stackTagCompound.getTagList("ench", 10);
      NBTTagCompound var7 = new NBTTagCompound();
      var7.setShort("id", (short)enchantment.effectId);
      var7.setShort("lvl", (byte)integer);
      var6.appendTag(var7);
   }

   public boolean isItemEnchanted() {
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9);
   }

   public void setTagInfo(String string, NBTBase nBTBase) {
      if (this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      this.stackTagCompound.setTag(string, nBTBase);
   }

   public boolean canEditBlocks() {
      return this.getItem().canItemEditBlocks();
   }

   public boolean isOnItemFrame() {
      return this.itemFrame != null;
   }

   public void setItemFrame(EntityItemFrame entityItemFrame) {
      this.itemFrame = entityItemFrame;
   }

   public EntityItemFrame getItemFrame() {
      return this.itemFrame;
   }

   public int getRepairCost() {
      return this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3) ? this.stackTagCompound.getInteger("RepairCost") : 0;
   }

   public void setRepairCost(int integer) {
      if (!this.hasTagCompound()) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("RepairCost", integer);
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers() {
      Multimap<String, AttributeModifier> var4;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
         var4 = HashMultimap.create();
         NBTTagList var5 = this.stackTagCompound.getTagList("AttributeModifiers", 10);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            NBTTagCompound var7 = var5.getCompoundTagAt(var6);
            AttributeModifier var8 = SharedMonsterAttributes.readAttributeModifierFromNBT(var7);
            if (var8 != null && var8.getID().getLeastSignificantBits() != 0L && var8.getID().getMostSignificantBits() != 0L) {
               var4.put(var7.getString("AttributeName"), var8);
            }
         }
      } else {
         var4 = this.getItem().getItemAttributeModifiers();
      }

      return var4;
   }

   public void setItem(Item item) {
      this.item = item;
   }

   public IChatComponent getChatComponent() {
      ChatComponentText var3 = new ChatComponentText(this.getDisplayName());
      if (this.hasDisplayName()) {
         var3.getChatStyle().setItalic(Boolean.TRUE);
      }

      IChatComponent var4 = new ChatComponentText("[").appendSibling(var3).appendText("]");
      if (this.item != null) {
         NBTTagCompound var5 = new NBTTagCompound();
         this.writeToNBT(var5);
         var4.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(var5.toString())));
         var4.getChatStyle().setColor(this.getRarity().rarityColor);
      }

      return var4;
   }

   public boolean canDestroy(Block block) {
      if (block == this.canDestroyCacheBlock) {
         return this.canDestroyCacheResult;
      }

      this.canDestroyCacheBlock = block;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
         NBTTagList var5 = this.stackTagCompound.getTagList("CanDestroy", 8);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            Block var7 = Block.getBlockFromName(var5.getStringTagAt(var6));
            if (var7 == block) {
               this.canDestroyCacheResult = true;
               return true;
            }
         }
      }

      this.canDestroyCacheResult = false;
      return false;
   }

   public boolean canPlaceOn(Block block) {
      if (block == this.canPlaceOnCacheBlock) {
         return this.canPlaceOnCacheResult;
      }

      this.canPlaceOnCacheBlock = block;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
         NBTTagList var5 = this.stackTagCompound.getTagList("CanPlaceOn", 8);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            Block var7 = Block.getBlockFromName(var5.getStringTagAt(var6));
            if (var7 == block) {
               this.canPlaceOnCacheResult = true;
               return true;
            }
         }
      }

      this.canPlaceOnCacheResult = false;
      return false;
   }
}
