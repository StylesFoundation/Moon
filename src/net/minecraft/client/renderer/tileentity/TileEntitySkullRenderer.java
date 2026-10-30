package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer<TileEntitySkull> {
   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
   public static TileEntitySkullRenderer instance;
   private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
   public ModelSkeletonHead l = new ModelHumanoidHead();

   public void renderTileEntityAt(TileEntitySkull tileEntitySkull, double double2, double double3, double double4, float float5, int integer) {
      EnumFacing var12 = EnumFacing.getFront(tileEntitySkull.getBlockMetadata() & 7);
      this.renderSkull(
         (float)double2,
         (float)double3,
         (float)double4,
         var12,
         tileEntitySkull.getSkullRotation() * 360 / 16.0F,
         tileEntitySkull.getSkullType(),
         tileEntitySkull.getPlayerProfile(),
         integer
      );
   }

   @Override
   public void setRendererDispatcher(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
      super.setRendererDispatcher(tileEntityRendererDispatcher);
      instance = this;
   }

   public void renderSkull(float float1, float float2, float float3, EnumFacing enumFacing, float float5, int integer6, GameProfile gameProfile, int integer8) {
      ModelSkeletonHead var11 = this.skeletonHead;
      if (integer8 >= 0) {
         this.bindTexture(DESTROY_STAGES[integer8]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 2.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         switch (integer6) {
            case 0:
            default:
               this.bindTexture(SKELETON_TEXTURES);
               break;
            case 1:
               this.bindTexture(WITHER_SKELETON_TEXTURES);
               break;
            case 2:
               this.bindTexture(ZOMBIE_TEXTURES);
               var11 = this.l;
               break;
            case 3:
               var11 = this.l;
               ResourceLocation var12 = DefaultPlayerSkin.getDefaultSkinLegacy();
               if (gameProfile != null) {
                  Minecraft var13 = Minecraft.getMinecraft();
                  Map var14 = var13.getSkinManager().loadSkinFromCache(gameProfile);
                  if (var14.containsKey(Type.SKIN)) {
                     var12 = var13.getSkinManager().loadSkin((MinecraftProfileTexture)var14.get(Type.SKIN), Type.SKIN);
                  } else {
                     var12 = DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getUUID(gameProfile));
                  }
               }

               this.bindTexture(var12);
               break;
            case 4:
               this.bindTexture(CREEPER_TEXTURES);
         }
      }

      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      if (enumFacing != EnumFacing.UP) {
         switch (enumFacing) {
            case NORTH:
               GlStateManager.translate(float1 + 0.5F, float2 + 0.25F, float3 + 0.74F);
               break;
            case SOUTH:
               GlStateManager.translate(float1 + 0.5F, float2 + 0.25F, float3 + 0.26F);
               float5 = 180.0F;
               break;
            case WEST:
               GlStateManager.translate(float1 + 0.74F, float2 + 0.25F, float3 + 0.5F);
               float5 = 270.0F;
               break;
            case EAST:
            default:
               GlStateManager.translate(float1 + 0.26F, float2 + 0.25F, float3 + 0.5F);
               float5 = 90.0F;
         }
      } else {
         GlStateManager.translate(float1 + 0.5F, float2, float3 + 0.5F);
      }

      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      var11.render(null, 0.0F, 0.0F, 0.0F, float5, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      if (integer8 >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }
}
