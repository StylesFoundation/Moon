package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;

public class LayerCustomHead implements LayerRenderer<EntityLivingBase> {
   private final ModelRenderer field_177209_a;

   public LayerCustomHead(ModelRenderer modelRenderer) {
      this.field_177209_a = modelRenderer;
   }

   @Override
   public void doRenderLayer(
      EntityLivingBase entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8
   ) {
      ItemStack var11 = entityLivingBase.getCurrentArmor(3);
      if (var11 != null && var11.getItem() != null) {
         Item var12 = var11.getItem();
         Minecraft var13 = Minecraft.getMinecraft();
         GlStateManager.pushMatrix();
         if (entityLivingBase.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         boolean var14 = entityLivingBase instanceof EntityVillager
            || entityLivingBase instanceof EntityZombie && ((EntityZombie)entityLivingBase).isVillager();
         if (!var14 && entityLivingBase.isChild()) {
            GlStateManager.scale(0.7F, 0.7F, 0.7F);
            GlStateManager.translate(0.0F, 16.0F * float8, 0.0F);
         }

         this.field_177209_a.postRender(0.0625F);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if (var12 instanceof ItemBlock) {
            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            if (var14) {
               GlStateManager.translate(0.0F, 0.1875F, 0.0F);
            }

            var13.getItemRenderer().Method807(entityLivingBase, var11, ItemCameraTransforms.TransformType.HEAD);
         } else if (var12 == Items.skull) {
            GlStateManager.scale(1.1875F, -1.1875F, -1.1875F);
            if (var14) {
               GlStateManager.translate(0.0F, 0.0625F, 0.0F);
            }

            GameProfile var16 = null;
            if (var11.hasTagCompound()) {
               NBTTagCompound var17 = var11.getTagCompound();
               if (var17.hasKey("SkullOwner", 10)) {
                  var16 = NBTUtil.readGameProfileFromNBT(var17.getCompoundTag("SkullOwner"));
               } else if (var17.hasKey("SkullOwner", 8)) {
                  String var18 = var17.getString("SkullOwner");
                  if (!StringUtils.isNullOrEmpty(var18)) {
                     var16 = TileEntitySkull.updateGameprofile(new GameProfile(null, var18));
                     var17.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), var16));
                  }
               }
            }

            TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, var11.getMetadata(), var16, -1);
         }

         GlStateManager.popMatrix();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return true;
   }
}
