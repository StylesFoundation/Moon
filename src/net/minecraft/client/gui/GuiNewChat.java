package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.moon.Moon;
import com.moon.module.impl.visual.CustomMinecraft;
import com.moon.render.font.manager.FontType;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui {
   private static final Logger logger = LogManager.getLogger();
   private final Minecraft mc;
   private final List<String> sentMessages = Lists.newArrayList();
   private final List<ChatLine> chatLines = Lists.newArrayList();
   private final List<ChatLine> drawnChatLines = Lists.newArrayList();
   private int scrollPos;
   private boolean isScrolled;
   private CustomMinecraft m;

   public GuiNewChat(Minecraft minecraft) {
      this.mc = minecraft;
   }

   public void drawChat(int integer) {
      if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
         int var4 = this.getLineCount();
         boolean var5 = false;
         int var6 = 0;
         int var7 = this.drawnChatLines.size();
         float var8 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
         if (var7 > 0) {
            if (this.getChatOpen()) {
               var5 = true;
            }

            float var9 = this.getChatScale();
            int var10 = MathHelper.ceiling_float_int(this.getChatWidth() / var9);
            GlStateManager.pushMatrix();
            GlStateManager.translate(2.0F, 20.0F, 0.0F);
            GlStateManager.scale(var9, var9, 1.0F);

            for (int var11 = 0; var11 + this.scrollPos < this.drawnChatLines.size() && var11 < var4; var11++) {
               ChatLine var12 = this.drawnChatLines.get(var11 + this.scrollPos);
               if (var12 != null) {
                  int var13 = integer - var12.getUpdatedCounter();
                  if (var13 < 200 || var5) {
                     double var14 = var13 / 200.0;
                     var14 = MathHelper.clamp_double((1.0 - var14) * 10.0, 0.0, 1.0);
                     var14 *= var14;
                     int var16 = (int)(255.0 * var14);
                     if (var5) {
                        var16 = 255;
                     }

                     var16 = (int)(var16 * var8);
                     var6++;
                     if (var16 > 3) {
                        int var18 = -var11 * 9;
                        drawRect(0, var18 - 9, 0 + var10 + 4, var18, var16 / 2 << 24);
                        String var19 = var12.getChatComponent().getFormattedText();
                        GlStateManager.enableBlend();
                        this.mc.fontRendererObj.drawStringWithShadow(var19, 0.0F, var18 - 8, 16777215 + (var16 << 24));
                        GlStateManager.disableAlpha();
                        GlStateManager.disableBlend();
                     }
                  }
               }
            }

            if (var5) {
               int var20 = this.mc.fontRendererObj.FONT_HEIGHT;
               GlStateManager.translate(-3.0F, 0.0F, 0.0F);
               int var21 = var7 * var20 + var7;
               int var22 = var6 * var20 + var6;
               int var25 = this.scrollPos * var22 / var7;
               int var15 = var22 * var22 / var21;
               if (var21 != var22) {
                  int var27 = var25 > 0 ? 170 : 96;
                  int var17 = this.isScrolled ? 13382451 : 3355562;
                  drawRect(0, -var25, 2, -var25 - var15, var17 + (var27 << 24));
                  drawRect(2, -var25, 1, -var25 - var15, 13421772 + (var27 << 24));
               }
            }

            GlStateManager.popMatrix();
         }
      }
   }

   public void clearChatMessages() {
      this.drawnChatLines.clear();
      this.chatLines.clear();
      this.sentMessages.clear();
   }

   public void printChatMessage(IChatComponent iChatComponent) {
      this.printChatMessageWithOptionalDeletion(iChatComponent, 0);
   }

   public void printChatMessageWithOptionalDeletion(IChatComponent iChatComponent, int integer) {
      this.setChatLine(iChatComponent, integer, this.mc.ingameGUI.getUpdateCounter(), false);
      logger.info("[CHAT] " + iChatComponent.getUnformattedText());
   }

   private void setChatLine(IChatComponent iChatComponent, int integer2, int integer3, boolean boolean4) {
      if (this.m == null) {
         this.m = Moon.getInstance().getModuleManager().getModule(CustomMinecraft.class);
      }

      if (integer2 != 0) {
         this.deleteChatLine(integer2);
      }

      int var7 = MathHelper.floor_float(this.getChatWidth() / this.getChatScale());
      List<IChatComponent> var8 = GuiUtilRenderComponents.Method379(
         iChatComponent,
         var7,
         this.mc.fontRendererObj,
         false,
         false,
         this.m.isEnabled() && this.m.chat.getValue(),
         Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM)
      );
      boolean var9 = this.getChatOpen();

      for (IChatComponent var11 : var8) {
         if (var9 && this.scrollPos > 0) {
            this.isScrolled = true;
            this.scroll(1);
         }

         this.drawnChatLines.add(0, new ChatLine(integer3, var11, integer2));
      }

      while (this.drawnChatLines.size() > 100) {
         this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
      }

      if (!boolean4) {
         this.chatLines.add(0, new ChatLine(integer3, iChatComponent, integer2));

         while (this.chatLines.size() > 100) {
            this.chatLines.remove(this.chatLines.size() - 1);
         }
      }
   }

   public void refreshChat() {
      this.drawnChatLines.clear();
      this.resetScroll();

      for (int var1 = this.chatLines.size() - 1; var1 >= 0; var1--) {
         ChatLine var2 = this.chatLines.get(var1);
         this.setChatLine(var2.getChatComponent(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
      }
   }

   public List<String> getSentMessages() {
      return this.sentMessages;
   }

   public void addToSentMessages(String string) {
      if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(string)) {
         this.sentMessages.add(string);
      }
   }

   public void resetScroll() {
      this.scrollPos = 0;
      this.isScrolled = false;
   }

   public void scroll(int integer) {
      this.scrollPos += integer;
      int var2 = this.drawnChatLines.size();
      if (this.scrollPos > var2 - this.getLineCount()) {
         this.scrollPos = var2 - this.getLineCount();
      }

      if (this.scrollPos <= 0) {
         this.scrollPos = 0;
         this.isScrolled = false;
      }
   }

   public IChatComponent getChatComponent(int integer1, int integer2) {
      if (!this.getChatOpen()) {
         return null;
      }

      if (this.m == null) {
         this.m = Moon.getInstance().getModuleManager().getModule(CustomMinecraft.class);
      }

      byte var5 = 9;
      boolean var6 = this.m.chat.getValue() && this.m.isEnabled();
      com.moon.render.font.FontRenderer var7 = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      int var9 = new ScaledResolution(this.mc).getScaleFactor();
      float var10 = this.getChatScale();
      int var11 = integer1 / var9 - 3;
      int var12 = integer2 / var9 - 27;
      var11 = MathHelper.floor_float(var11 / var10);
      var12 = MathHelper.floor_float(var12 / var10);
      if (var11 >= 0 && var12 >= 0) {
         int var13 = Math.min(this.getLineCount(), this.drawnChatLines.size());
         float var14 = var6 ? Math.round(var7.getLineHeight(9.0F)) : this.mc.fontRendererObj.FONT_HEIGHT;
         if (var11 <= MathHelper.floor_float(this.getChatWidth() / this.getChatScale()) && var12 < var14 * var13 + var13) {
            int var15 = (int)(var12 / var14 + this.scrollPos);
            if (var15 >= 0 && var15 < this.drawnChatLines.size()) {
               ChatLine var16 = this.drawnChatLines.get(var15);
               int var17 = 0;

               for (IChatComponent var19 : var16.getChatComponent()) {
                  if (var19 instanceof ChatComponentText) {
                     var17 = (int)(
                        var17
                           + (
                              var6
                                 ? var7.getTextWidth(
                                    GuiUtilRenderComponents.func_178909_a(((ChatComponentText)var19).getChatComponentText_TextValue(), false), 9.0F
                                 )
                                 : this.mc
                                    .fontRendererObj
                                    .getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)var19).getChatComponentText_TextValue(), false))
                           )
                     );
                     if (var17 > var11) {
                        return var19;
                     }
                  }
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public boolean getChatOpen() {
      return this.mc.currentScreen instanceof GuiChat;
   }

   public void deleteChatLine(int integer) {
      Iterator var2 = this.drawnChatLines.iterator();

      while (var2.hasNext()) {
         ChatLine var3 = (ChatLine)var2.next();
         if (var3.getChatLineID() == integer) {
            var2.remove();
         }
      }

      var2 = this.chatLines.iterator();

      while (var2.hasNext()) {
         ChatLine var5 = (ChatLine)var2.next();
         if (var5.getChatLineID() == integer) {
            var2.remove();
            break;
         }
      }
   }

   public int getChatWidth() {
      return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
   }

   public int getChatHeight() {
      return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
   }

   public float getChatScale() {
      return this.mc.gameSettings.chatScale;
   }

   public static int calculateChatboxWidth(float float1) {
      short var3 = 320;
      byte var4 = 40;
      return MathHelper.floor_float(float1 * 280.0F + 40.0F);
   }

   public static int calculateChatboxHeight(float float1) {
      short var3 = 180;
      byte var4 = 20;
      return MathHelper.floor_float(float1 * 160.0F + 20.0F);
   }

   public int getLineCount() {
      return this.getChatHeight() / 9;
   }

   public Minecraft Method493() {
      return this.mc;
   }

   public List<String> Method494() {
      return this.sentMessages;
   }

   public List<ChatLine> Method495() {
      return this.chatLines;
   }

   public List<ChatLine> Method496() {
      return this.drawnChatLines;
   }

   public int Method497() {
      return this.scrollPos;
   }

   public boolean Method498() {
      return this.isScrolled;
   }

   public CustomMinecraft Method499() {
      return this.m;
   }
}
