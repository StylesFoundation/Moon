package net.minecraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityFlowerPot extends TileEntity {
   private Item flowerPotItem;
   private int flowerPotData;

   public TileEntityFlowerPot() {
   }

   public TileEntityFlowerPot(Item item, int integer) {
      this.flowerPotItem = item;
      this.flowerPotData = integer;
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      ResourceLocation var5 = Item.itemRegistry.getNameForObject(this.flowerPotItem);
      nBTTagCompound.setString("Item", var5 == null ? "" : var5.toString());
      nBTTagCompound.setInteger("Data", this.flowerPotData);
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("Item", 8)) {
         this.flowerPotItem = Item.getByNameOrId(nBTTagCompound.getString("Item"));
      } else {
         this.flowerPotItem = Item.getItemById(nBTTagCompound.getInteger("Item"));
      }

      this.flowerPotData = nBTTagCompound.getInteger("Data");
   }

   @Override
   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      var1.removeTag("Item");
      var1.setInteger("Item", Item.getIdFromItem(this.flowerPotItem));
      return new S35PacketUpdateTileEntity(this.pos, 5, var1);
   }

   public void setFlowerPotData(Item item, int integer) {
      this.flowerPotItem = item;
      this.flowerPotData = integer;
   }

   public Item getFlowerPotItem() {
      return this.flowerPotItem;
   }

   public int getFlowerPotData() {
      return this.flowerPotData;
   }
}
