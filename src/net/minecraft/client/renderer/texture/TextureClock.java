package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class TextureClock extends TextureAtlasSprite {
   private double currentAngle;
   private double angleDelta;

   public TextureClock(String string) {
      super(string);
   }

   @Override
   public void updateAnimation() {
      if (!this.framesTextureData.isEmpty()) {
         Minecraft var1 = Minecraft.getMinecraft();
         double var2 = 0.0;
         if (var1.theWorld != null && var1.thePlayer != null) {
            var2 = var1.theWorld.getCelestialAngle(1.0F);
            if (!var1.theWorld.provider.isSurfaceWorld()) {
               var2 = Math.random();
            }
         }

         double var4 = var2 - this.currentAngle;

         while (var4 < -0.5) {
            var4++;
         }

         while (var4 >= 0.5) {
            var4--;
         }

         var4 = MathHelper.clamp_double(var4, -1.0, 1.0);
         this.angleDelta += var4 * 0.1;
         this.angleDelta *= 0.8;
         this.currentAngle = this.currentAngle + this.angleDelta;
         int var6 = (int)((this.currentAngle + 1.0) * this.framesTextureData.size()) % this.framesTextureData.size();

         while (var6 < 0) {
            var6 = (var6 + this.framesTextureData.size()) % this.framesTextureData.size();
         }

         if (var6 != this.frameCounter) {
            this.frameCounter = var6;
            TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
         }
      }
   }
}
