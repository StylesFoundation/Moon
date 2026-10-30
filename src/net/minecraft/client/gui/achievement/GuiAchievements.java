package net.minecraft.client.gui.achievement;

import java.io.IOException;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiAchievements extends GuiScreen implements IProgressMeter {
   private static final int field_146572_y = AchievementList.a * 24 - 112;
   private static final int field_146571_z = AchievementList.b * 24 - 112;
   private static final int field_146559_A = AchievementList.c * 24 - 77;
   private static final int field_146560_B = AchievementList.d * 24 - 77;
   private static final ResourceLocation ACHIEVEMENT_BACKGROUND = new ResourceLocation("textures/gui/achievement/achievement_background.png");
   protected GuiScreen parentScreen;
   protected int field_146555_f = 256;
   protected int field_146557_g = 202;
   protected int field_146563_h;
   protected int field_146564_i;
   protected float field_146570_r = 1.0F;
   protected double field_146569_s;
   protected double field_146568_t;
   protected double field_146567_u;
   protected double field_146566_v;
   protected double field_146565_w;
   protected double field_146573_x;
   private int field_146554_D;
   private final StatFileWriter statFileWriter;
   private boolean loadingAchievements = true;

   public GuiAchievements(GuiScreen guiScreen, StatFileWriter statFileWriter) {
      this.parentScreen = guiScreen;
      this.statFileWriter = statFileWriter;
      short var5 = 141;
      short var6 = 141;
      this.field_146569_s = this.field_146567_u = this.field_146565_w = AchievementList.openInventory.displayColumn * 24 - 70 - 12;
      this.field_146568_t = this.field_146566_v = this.field_146573_x = AchievementList.openInventory.displayRow * 24 - 70;
   }

   @Override
   public void onInitGui() {
      this.mc.getNetHandler().Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
      this.m.clear();
      this.m.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (!this.loadingAchievements && guiButton.l == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
         this.mc.displayGuiScreen(null);
         this.mc.setIngameFocus();
      } else {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.loadingAchievements) {
         this.drawDefaultBackground();
         this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats"), this.width / 2, this.height / 2, 16777215);
         this.drawCenteredString(
            this.fontRendererObj,
            lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length)],
            this.width / 2,
            this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2,
            16777215
         );
      } else {
         if (Mouse.isButtonDown(0)) {
            int var6 = (this.width - this.field_146555_f) / 2;
            int var7 = (this.height - this.field_146557_g) / 2;
            int var8 = var6 + 8;
            int var9 = var7 + 17;
            if ((this.field_146554_D == 0 || this.field_146554_D == 1) && mouseX >= var8 && mouseX < var8 + 224 && mouseY >= var9 && mouseY < var9 + 155) {
               if (this.field_146554_D == 0) {
                  this.field_146554_D = 1;
               } else {
                  this.field_146567_u = this.field_146567_u - (mouseX - this.field_146563_h) * this.field_146570_r;
                  this.field_146566_v = this.field_146566_v - (mouseY - this.field_146564_i) * this.field_146570_r;
                  this.field_146565_w = this.field_146569_s = this.field_146567_u;
                  this.field_146573_x = this.field_146568_t = this.field_146566_v;
               }

               this.field_146563_h = mouseX;
               this.field_146564_i = mouseY;
            }
         } else {
            this.field_146554_D = 0;
         }

         int var13 = Mouse.getDWheel();
         float var14 = this.field_146570_r;
         if (var13 < 0) {
            this.field_146570_r += 0.25F;
         } else if (var13 > 0) {
            this.field_146570_r -= 0.25F;
         }

         this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
         if (this.field_146570_r != var14) {
            float var10000 = var14 - this.field_146570_r;
            float var15 = var14 * this.field_146555_f;
            float var10 = var14 * this.field_146557_g;
            float var11 = this.field_146570_r * this.field_146555_f;
            float var12 = this.field_146570_r * this.field_146557_g;
            this.field_146567_u -= (var11 - var15) * 0.5F;
            this.field_146566_v -= (var12 - var10) * 0.5F;
            this.field_146565_w = this.field_146569_s = this.field_146567_u;
            this.field_146573_x = this.field_146568_t = this.field_146566_v;
         }

         if (this.field_146565_w < field_146572_y) {
            this.field_146565_w = field_146572_y;
         }

         if (this.field_146573_x < field_146571_z) {
            this.field_146573_x = field_146571_z;
         }

         if (this.field_146565_w >= field_146559_A) {
            this.field_146565_w = field_146559_A - 1;
         }

         if (this.field_146573_x >= field_146560_B) {
            this.field_146573_x = field_146560_B - 1;
         }

         this.drawDefaultBackground();
         this.drawAchievementScreen(mouseX, mouseY, partialTicks);
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         this.drawTitle();
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
      }
   }

   @Override
   public void doneLoading() {
      if (this.loadingAchievements) {
         this.loadingAchievements = false;
      }
   }

   @Override
   public void onUpdateScreen() {
      if (!this.loadingAchievements) {
         this.field_146569_s = this.field_146567_u;
         this.field_146568_t = this.field_146566_v;
         double var1 = this.field_146565_w - this.field_146567_u;
         double var3 = this.field_146573_x - this.field_146566_v;
         if (var1 * var1 + var3 * var3 < 4.0) {
            this.field_146567_u += var1;
            this.field_146566_v += var3;
         } else {
            this.field_146567_u += var1 * 0.85;
            this.field_146566_v += var3 * 0.85;
         }
      }
   }

   protected void drawTitle() {
      int var3 = (this.width - this.field_146555_f) / 2;
      int var4 = (this.height - this.field_146557_g) / 2;
      this.fontRendererObj.drawString(I18n.format("gui.achievements"), var3 + 15, var4 + 5, 4210752);
   }

   protected void drawAchievementScreen(int integer1, int integer2, float float3) {
      int var6 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * float3);
      int var7 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * float3);
      if (var6 < field_146572_y) {
         var6 = field_146572_y;
      }

      if (var7 < field_146571_z) {
         var7 = field_146571_z;
      }

      if (var6 >= field_146559_A) {
         var6 = field_146559_A - 1;
      }

      if (var7 >= field_146560_B) {
         var7 = field_146560_B - 1;
      }

      int var8 = (this.width - this.field_146555_f) / 2;
      int var9 = (this.height - this.field_146557_g) / 2;
      int var10 = var8 + 16;
      int var11 = var9 + 17;
      this.zLevel = 0.0F;
      GlStateManager.depthFunc(518);
      GlStateManager.pushMatrix();
      GlStateManager.translate(var10, var11, -200.0F);
      GlStateManager.scale(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableColorMaterial();
      int var12 = var6 + 288 >> 4;
      int var13 = var7 + 288 >> 4;
      int var14 = (var6 + 288) % 16;
      int var15 = (var7 + 288) % 16;
      Random var21 = new Random();
      float var22 = 16.0F / this.field_146570_r;
      float var23 = 16.0F / this.field_146570_r;

      for (int var24 = 0; var24 * var22 - var15 < 155.0F; var24++) {
         float var25 = 0.6F - (var13 + var24) / 25.0F * 0.3F;
         GlStateManager.color(var25, var25, var25, 1.0F);

         for (int var26 = 0; var26 * var23 - var14 < 224.0F; var26++) {
            var21.setSeed(this.mc.getSession().getPlayerID().hashCode() + var12 + var26 + (var13 + var24) * 16L);
            int var27 = var21.nextInt(1 + var13 + var24) + (var13 + var24) / 2;
            TextureAtlasSprite var28 = this.func_175371_a(Blocks.sand);
            if (var27 > 37 || var13 + var24 == 35) {
               Block var29 = Blocks.bedrock;
               var28 = this.func_175371_a(var29);
            } else if (var27 == 22) {
               if (var21.nextInt(2) == 0) {
                  var28 = this.func_175371_a(Blocks.diamond_ore);
               } else {
                  var28 = this.func_175371_a(Blocks.redstone_ore);
               }
            } else if (var27 == 10) {
               var28 = this.func_175371_a(Blocks.iron_ore);
            } else if (var27 == 8) {
               var28 = this.func_175371_a(Blocks.coal_ore);
            } else if (var27 > 4) {
               var28 = this.func_175371_a(Blocks.stone);
            } else if (var27 > 0) {
               var28 = this.func_175371_a(Blocks.dirt);
            }

            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.drawTexturedModalRect(var26 * 16 - var14, var24 * 16 - var15, var28, 16, 16);
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);

      for (int var35 = 0; var35 < AchievementList.achievementList.size(); var35++) {
         Achievement var37 = AchievementList.achievementList.get(var35);
         if (var37.parentAchievement != null) {
            int var39 = var37.displayColumn * 24 - var6 + 11;
            int var41 = var37.displayRow * 24 - var7 + 11;
            int var44 = var37.parentAchievement.displayColumn * 24 - var6 + 11;
            int var47 = var37.parentAchievement.displayRow * 24 - var7 + 11;
            boolean var30 = this.statFileWriter.hasAchievementUnlocked(var37);
            boolean var31 = this.statFileWriter.canUnlockAchievement(var37);
            if (this.statFileWriter.func_150874_c(var37) <= 4) {
               int var33 = -16777216;
               if (var30) {
                  var33 = -6250336;
               } else if (var31) {
                  var33 = -16711936;
               }

               this.drawHorizontalLine(var39, var44, var41, var33);
               this.drawVerticalLine(var44, var41, var47, var33);
               if (var39 > var44) {
                  this.drawTexturedModalRect(var39 - 11 - 7, var41 - 5, 114, 234, 7, 11);
               } else if (var39 < var44) {
                  this.drawTexturedModalRect(var39 + 11, var41 - 5, 107, 234, 7, 11);
               } else if (var41 > var47) {
                  this.drawTexturedModalRect(var39 - 5, var41 - 11 - 7, 96, 234, 11, 7);
               } else if (var41 < var47) {
                  this.drawTexturedModalRect(var39 - 5, var41 + 11, 96, 241, 11, 7);
               }
            }
         }
      }

      Achievement var36 = null;
      float var38 = (integer1 - var10) * this.field_146570_r;
      float var40 = (integer2 - var11) * this.field_146570_r;
      RenderHelper.enableGUIStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableColorMaterial();

      for (int var42 = 0; var42 < AchievementList.achievementList.size(); var42++) {
         Achievement var45 = AchievementList.achievementList.get(var42);
         int var48 = var45.displayColumn * 24 - var6;
         int var50 = var45.displayRow * 24 - var7;
         if (var48 >= -24 && var50 >= -24 && var48 <= 224.0F * this.field_146570_r && var50 <= 155.0F * this.field_146570_r) {
            int var52 = this.statFileWriter.func_150874_c(var45);
            if (this.statFileWriter.hasAchievementUnlocked(var45)) {
               GlStateManager.color(0.75F, 0.75F, 0.75F, 1.0F);
            } else if (this.statFileWriter.canUnlockAchievement(var45)) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            } else if (var52 < 3) {
               GlStateManager.color(0.3F, 0.3F, 0.3F, 1.0F);
            } else if (var52 == 3) {
               GlStateManager.color(0.2F, 0.2F, 0.2F, 1.0F);
            } else {
               if (var52 != 4) {
                  continue;
               }

               GlStateManager.color(0.1F, 0.1F, 0.1F, 1.0F);
            }

            this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
            if (var45.getSpecial()) {
               this.drawTexturedModalRect(var48 - 2, var50 - 2, 26, 202, 26, 26);
            } else {
               this.drawTexturedModalRect(var48 - 2, var50 - 2, 0, 202, 26, 26);
            }

            if (!this.statFileWriter.canUnlockAchievement(var45)) {
               GlStateManager.color(0.1F, 0.1F, 0.1F, 1.0F);
               this.itemRender.isNotRenderingEffectsInGUI(false);
            }

            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            this.itemRender.renderItemAndEffectIntoGUI(var45.theItemStack, var48 + 3, var50 + 3);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.disableLighting();
            if (!this.statFileWriter.canUnlockAchievement(var45)) {
               this.itemRender.isNotRenderingEffectsInGUI(true);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (var38 >= var48 && var38 <= var48 + 22 && var40 >= var50 && var40 <= var50 + 22) {
               var36 = var45;
            }
         }
      }

      GlStateManager.disableDepth();
      GlStateManager.enableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
      this.drawTexturedModalRect(var8, var9, 0, 0, this.field_146555_f, this.field_146557_g);
      this.zLevel = 0.0F;
      GlStateManager.depthFunc(515);
      GlStateManager.disableDepth();
      GlStateManager.enableTexture2D();
      super.onDrawScreen(integer1, integer2, float3);
      if (var36 != null) {
         String var43 = var36.getStatName().getUnformattedText();
         String var46 = var36.getDescription();
         int var49 = integer1 + 12;
         int var51 = integer2 - 4;
         int var53 = this.statFileWriter.func_150874_c(var36);
         if (this.statFileWriter.canUnlockAchievement(var36)) {
            int var32 = Math.max(this.fontRendererObj.getStringWidth(var43), 120);
            int var56 = this.fontRendererObj.splitStringWidth(var46, var32);
            if (this.statFileWriter.hasAchievementUnlocked(var36)) {
               var56 += 12;
            }

            this.drawGradientRect(var49 - 3, var51 - 3, var49 + var32 + 3, var51 + var56 + 3 + 12, -1073741824, -1073741824);
            this.fontRendererObj.drawSplitString(var46, var49, var51 + 12, var32, -6250336);
            if (this.statFileWriter.hasAchievementUnlocked(var36)) {
               this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken"), var49, var51 + var56 + 4, -7302913);
            }
         } else if (var53 == 3) {
            var43 = I18n.format("achievement.unknown");
            int var54 = Math.max(this.fontRendererObj.getStringWidth(var43), 120);
            String var57 = new ChatComponentTranslation("achievement.requires", var36.parentAchievement.getStatName()).getUnformattedText();
            int var34 = this.fontRendererObj.splitStringWidth(var57, var54);
            this.drawGradientRect(var49 - 3, var51 - 3, var49 + var54 + 3, var51 + var34 + 12 + 3, -1073741824, -1073741824);
            this.fontRendererObj.drawSplitString(var57, var49, var51 + 12, var54, -9416624);
         } else if (var53 < 3) {
            int var55 = Math.max(this.fontRendererObj.getStringWidth(var43), 120);
            String var58 = new ChatComponentTranslation("achievement.requires", var36.parentAchievement.getStatName()).getUnformattedText();
            int var59 = this.fontRendererObj.splitStringWidth(var58, var55);
            this.drawGradientRect(var49 - 3, var51 - 3, var49 + var55 + 3, var51 + var59 + 12 + 3, -1073741824, -1073741824);
            this.fontRendererObj.drawSplitString(var58, var49, var51 + 12, var55, -9416624);
         } else {
            var43 = null;
         }

         if (var43 != null) {
            this.fontRendererObj
               .drawStringWithShadow(
                  var43,
                  var49,
                  var51,
                  this.statFileWriter.canUnlockAchievement(var36) ? (var36.getSpecial() ? -128 : -1) : (var36.getSpecial() ? -8355776 : -8355712)
               );
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.enableLighting();
      RenderHelper.disableStandardItemLighting();
   }

   private TextureAtlasSprite func_175371_a(Block block) {
      return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(block.getDefaultState());
   }

   @Override
   public boolean doesGuiPauseGame() {
      return !this.loadingAchievements;
   }
}
