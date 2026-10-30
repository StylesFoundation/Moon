package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;

public class TileEntityItemStackRenderer {
   public static TileEntityItemStackRenderer instance = new TileEntityItemStackRenderer();
   private final TileEntityChest field_147717_b = new TileEntityChest(0);
   private final TileEntityChest field_147718_c = new TileEntityChest(1);
   private final TileEntityEnderChest enderChest = new TileEntityEnderChest();
   private final TileEntityBanner banner = new TileEntityBanner();
   private final TileEntitySkull skull = new TileEntitySkull();

   public void renderByItem(ItemStack itemStack) {
      if (itemStack.getItem() == Items.banner) {
         this.banner.setItemValues(itemStack);
         TileEntityRendererDispatcher.instance.renderTileEntityAt(this.banner, 0.0, 0.0, 0.0, 0.0F);
      } else if (itemStack.getItem() == Items.skull) {
         GameProfile var4 = null;
         if (itemStack.hasTagCompound()) {
            NBTTagCompound var5 = itemStack.getTagCompound();
            if (var5.hasKey("SkullOwner", 10)) {
               var4 = NBTUtil.readGameProfileFromNBT(var5.getCompoundTag("SkullOwner"));
            } else if (var5.hasKey("SkullOwner", 8) && var5.getString("SkullOwner").length() > 0) {
               var4 = TileEntitySkull.updateGameprofile(new GameProfile(null, var5.getString("SkullOwner")));
               var5.removeTag("SkullOwner");
               var5.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), var4));
            }
         }

         if (TileEntitySkullRenderer.instance != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, 0.0F, -0.5F);
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.disableCull();
            TileEntitySkullRenderer.instance.renderSkull(0.0F, 0.0F, 0.0F, EnumFacing.UP, 0.0F, itemStack.getMetadata(), var4, -1);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      } else {
         Block var6 = Block.getBlockFromItem(itemStack.getItem());
         if (var6 == Blocks.ender_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.enderChest, 0.0, 0.0, 0.0, 0.0F);
         } else if (var6 == Blocks.trapped_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147718_c, 0.0, 0.0, 0.0, 0.0F);
         } else {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.field_147717_b, 0.0, 0.0, 0.0, 0.0F);
         }
      }
   }
}
