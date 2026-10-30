package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
   private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
   public ModelSign g = new ModelSign();
   private static double textRenderDistanceSq = 4096.0;

   public void renderTileEntityAt(TileEntitySign tileEntitySign, double double2, double double3, double double4, float float5, int integer) {
      Block var12 = tileEntitySign.getBlockType();
      GlStateManager.pushMatrix();
      if (var12 == Blocks.standing_sign) {
         GlStateManager.translate((float)double2 + 0.5F, (float)double3 + 0.5F, (float)double4 + 0.5F);
         GlStateManager.rotate(-(tileEntitySign.getBlockMetadata() * 360 / 16.0F), 0.0F, 1.0F, 0.0F);
         this.g.signStick.showModel = true;
      } else {
         int var14 = tileEntitySign.getBlockMetadata();
         float var15 = 0.0F;
         if (var14 == 2) {
            var15 = 180.0F;
         }

         if (var14 == 4) {
            var15 = 90.0F;
         }

         if (var14 == 5) {
            var15 = -90.0F;
         }

         GlStateManager.translate((float)double2 + 0.5F, (float)double3 + 0.5F, (float)double4 + 0.5F);
         GlStateManager.rotate(-var15, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
         this.g.signStick.showModel = false;
      }

      if (integer >= 0) {
         this.bindTexture(DESTROY_STAGES[integer]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 2.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(SIGN_TEXTURE);
      }

      GlStateManager.enableRescaleNormal();
      GlStateManager.pushMatrix();
      GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
      this.g.renderSign();
      GlStateManager.popMatrix();
      if (isRenderText(tileEntitySign)) {
         FontRenderer var21 = this.getFontRenderer();
         GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
         GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
         GL11.glNormal3f(0.0F, 0.0F, -0.010416667F);
         GlStateManager.depthMask(false);
         int var16 = 0;
         if (Config.isCustomColors()) {
            var16 = CustomColors.getSignTextColor(0);
         }

         if (integer < 0) {
            for (int var17 = 0; var17 < tileEntitySign.signText.length; var17++) {
               if (tileEntitySign.signText[var17] != null) {
                  List var19 = GuiUtilRenderComponents.Method379(tileEntitySign.signText[var17], 90, var21, false, true, false, null);
                  String var20 = var19 != null && var19.size() > 0 ? ((IChatComponent)var19.get(0)).getFormattedText() : "";
                  if (var17 == tileEntitySign.lineBeingEdited) {
                     var20 = "> " + var20 + " <";
                     var21.drawString(var20, -var21.getStringWidth(var20) / 2, var17 * 10 - tileEntitySign.signText.length * 5, var16);
                  } else {
                     var21.drawString(var20, -var21.getStringWidth(var20) / 2, var17 * 10 - tileEntitySign.signText.length * 5, var16);
                  }
               }
            }
         }
      }

      GlStateManager.depthMask(true);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      if (integer >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }

   private static boolean isRenderText(TileEntitySign tileEntitySign) {
      if (Shaders.isShadowPass) {
         return false;
      } else if (Config.getMinecraft().currentScreen instanceof GuiEditSign) {
         return true;
      } else if (!Config.zoomMode && tileEntitySign.lineBeingEdited < 0) {
         Entity var1 = Config.getMinecraft().getRenderViewEntity();
         double var2 = tileEntitySign.getDistanceSq(var1.posX, var1.posY, var1.posZ);
         return !(var2 > textRenderDistanceSq);
      } else {
         return true;
      }
   }

   public static void updateTextRenderDistance() {
      Minecraft var0 = Config.getMinecraft();
      double var1 = Config.Method256(var0.gameSettings.fovSetting, 1.0F, 120.0F);
      double var3 = Math.max(1.5 * var0.displayHeight / var1, 16.0);
      textRenderDistanceSq = var3 * var3;
   }
}
