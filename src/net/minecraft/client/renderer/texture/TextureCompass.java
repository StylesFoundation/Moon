package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TextureCompass extends TextureAtlasSprite {
   public double currentAngle;
   public double angleDelta;
   public static String locationSprite;

   public TextureCompass(String string) {
      super(string);
      locationSprite = string;
   }

   @Override
   public void updateAnimation() {
      Minecraft var1 = Minecraft.getMinecraft();
      if (var1.theWorld != null && var1.thePlayer != null) {
         this.updateCompass(var1.theWorld, var1.thePlayer.posX, var1.thePlayer.posZ, var1.thePlayer.rotationYaw, false, false);
      } else {
         this.updateCompass(null, 0.0, 0.0, 0.0, true, false);
      }
   }

   public void updateCompass(World world, double double2, double double3, double double4, boolean boolean5, boolean boolean6) {
      if (!this.framesTextureData.isEmpty()) {
         double var10 = 0.0;
         if (world != null && !boolean5) {
            BlockPos var12 = world.getSpawnPoint();
            double var13 = var12.getX() - double2;
            double var15 = var12.getZ() - double3;
            var10 = -((double4 % 360.0 - 90.0) * Math.PI / 180.0 - Math.atan2(var15, var13));
            if (!world.provider.isSurfaceWorld()) {
               var10 = Math.random() * Math.PI * 2.0;
            }
         }

         if (boolean6) {
            this.currentAngle = var10;
         } else {
            double var17 = var10 - this.currentAngle;

            while (var17 < -Math.PI) {
               var17 += Math.PI * 2;
            }

            while (var17 >= Math.PI) {
               var17 -= Math.PI * 2;
            }

            var17 = MathHelper.clamp_double(var17, -1.0, 1.0);
            this.angleDelta += var17 * 0.1;
            this.angleDelta *= 0.8;
            this.currentAngle = this.currentAngle + this.angleDelta;
         }

         int var19 = (int)((this.currentAngle / (Math.PI * 2) + 1.0) * this.framesTextureData.size()) % this.framesTextureData.size();

         while (var19 < 0) {
            var19 = (var19 + this.framesTextureData.size()) % this.framesTextureData.size();
         }

         if (var19 != this.frameCounter) {
            this.frameCounter = var19;
            TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
         }
      }
   }
}
