package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class ItemEditableBook extends Item {
   public ItemEditableBook() {
      this.setMaxStackSize(1);
   }

   public static boolean validBookTagContents(NBTTagCompound nBTTagCompound) {
      if (!ItemWritableBook.isNBTValid(nBTTagCompound)) {
         return false;
      }

      if (!nBTTagCompound.hasKey("title", 8)) {
         return false;
      }

      String var4 = nBTTagCompound.getString("title");
      return var4 != null && var4.length() <= 32 && nBTTagCompound.hasKey("author", 8);
   }

   public static int getGeneration(ItemStack itemStack) {
      return itemStack.getTagCompound().getInteger("generation");
   }

   @Override
   public String getItemStackDisplayName(ItemStack itemStack) {
      if (itemStack.hasTagCompound()) {
         String var6 = itemStack.getTagCompound().getString("title");
         if (!StringUtils.isNullOrEmpty(var6)) {
            return var6;
         }
      }

      return super.getItemStackDisplayName(itemStack);
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      if (itemStack.hasTagCompound()) {
         NBTTagCompound var8 = itemStack.getTagCompound();
         String var9 = var8.getString("author");
         if (!StringUtils.isNullOrEmpty(var9)) {
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", var9));
         }

         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("book.generation." + var8.getInteger("generation")));
      }
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (!world.isRemote) {
         this.resolveContents(itemStack, entityPlayer);
      }

      entityPlayer.displayGUIBook(itemStack);
      entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStack;
   }

   private void resolveContents(ItemStack itemStack, EntityPlayer entityPlayer) {
      if (itemStack != null && itemStack.getTagCompound() != null) {
         NBTTagCompound var6 = itemStack.getTagCompound();
         if (!var6.getBoolean("resolved")) {
            var6.setBoolean("resolved", true);
            if (validBookTagContents(var6)) {
               NBTTagList var7 = var6.getTagList("pages", 8);

               for (int var8 = 0; var8 < var7.tagCount(); var8++) {
                  String var9 = var7.getStringTagAt(var8);

                  IChatComponent var10;
                  try {
                     var10 = IChatComponent.Serializer.jsonToComponent(var9);
                     var10 = ChatComponentProcessor.processComponent(entityPlayer, var10, entityPlayer);
                  } catch (Exception var11) {
                     var10 = new ChatComponentText(var9);
                  }

                  var7.set(var8, new NBTTagString(IChatComponent.Serializer.componentToJson(var10)));
               }

               var6.setTag("pages", var7);
               if (entityPlayer instanceof EntityPlayerMP && entityPlayer.getCurrentEquippedItem() == itemStack) {
                  Slot var12 = entityPlayer.openContainer.getSlotFromInventory(entityPlayer.inventory, entityPlayer.inventory.currentItem);
                  ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(0, var12.slotNumber, itemStack));
               }
            }
         }
      }
   }

   @Override
   public boolean hasEffect(ItemStack itemStack) {
      return true;
   }
}
