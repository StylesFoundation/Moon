package com.moon.ui.account;

import com.moon.Moon;
import com.moon.account.Account;
import com.moon.handlers.ScaleHandler;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.texture.RoundedTextureCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.theme.ColorScheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class AccountListWidget extends GuiSlot {
   public int selectedIndex = 0;
   private AccountManagerScreen parentScreen;
   private AnimatedValue scrollAnimation = new AnimatedValue();

   public AccountListWidget(AccountManagerScreen parentScreen) {
      super(Minecraft.getMinecraft(), parentScreen.width, parentScreen.height, 36, parentScreen.height - 56, 41);
      this.parentScreen = parentScreen;
   }

   @Override
   protected int getSize() {
      return Moon.getInstance().getAccountManager().getAccounts().size();
   }

   @Override
   protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
      this.selectedIndex = index;
      if (doubleClick) {
         this.getAccount(index).login();
      }
   }

   @Override
   protected boolean isSelected(int index) {
      return index == this.selectedIndex;
   }

   @Override
   protected void drawBackground() {
   }

   @Override
   protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
   }

   public void drawEntries() {
      float availableWidth = ScaleHandler.getScaledResolution().Method237() - 200;
      int columnCount = (int)Math.floor(availableWidth / (this.getListWidth() + 3));
      ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
      RenderTask backgroundTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).build();
      backgroundTask.addCommand(RectangleCommand.of(0.0F, 0.0F, this.mc.displayWidth, this.top - 1, themeColor.getShadow()));
      backgroundTask.addCommand(RectangleCommand.of(0.0F, this.bottom, this.mc.displayWidth, this.height, themeColor.getShadow()));
      backgroundTask.render();
      StencilClipState clipState = StencilClipState.of(0.0F, this.top - 1, this.width, this.bottom - this.top + 1);
      int baseX = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
      float baseY = this.top + 4 - this.scrollAnimation.get();
      int size = this.getSize();

      for (int i = 0; i < size; i++) {
         float slotY = baseY + i / columnCount * this.slotHeight + this.headerPadding;
         int slotH = this.slotHeight - 4;
         if (slotY > this.bottom || slotY + slotH < this.top) {
            this.func_178040_a(i, baseX, MathHelper.ceiling_float_int(slotY));
         }

         if (slotY >= this.top - this.slotHeight && slotY <= this.bottom) {
            RenderTask slotTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
            int columnSpacing = this.getListWidth() + 3;
            float xOffset = columnSpacing * (i % columnCount) - columnSpacing * columnCount / 2.0F;
            float textOffsetX = 3.0F + xOffset;
            int slotWidth = this.getListWidth();
            float slotX = this.left + this.width / 2.0F + xOffset;
            float rectY = slotY - 1.0F;
            int rectHeight = this.slotHeight - 3;
            slotTask.addRenderState(clipState);
            slotTask.addCommand(RoundedRectangleCommand.of(slotX, rectY, slotWidth, rectHeight, 8.0F, themeColor.getShadow()));
            slotTask.render();
         }
      }
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.field_178041_q) {
         ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
         this.mouseX = mouseX;
         this.mouseY = mouseY;
         int scrollBarX = this.getScrollBarX();
         int scrollBarRight = scrollBarX + 6;
         this.bindAmountScrolled();
         GlStateManager.disableLighting();
         GlStateManager.disableFog();
         Tessellator.getInstance();
         int slotX = this.left + this.width / 2 + 2;
         float slotY = this.top + 4 - this.scrollAnimation.get();
         this.drawAccountSlots(slotX, slotY, mouseX, mouseY);
         RenderTask overlayTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         overlayTask.addCommand(RoundedRectangleCommand.of(-1.0F, -1.0F, this.mc.displayWidth + 1, this.top, 0.0F, themeColor.getSecondaryContainer()));
         overlayTask.addCommand(RoundedRectangleCommand.of(-1.0F, this.bottom, this.mc.displayWidth + 1, this.height, 0.0F, themeColor.getSecondaryContainer()));
         overlayTask.render();
         GlStateManager.disableDepth();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
         GlStateManager.disableAlpha();
         GlStateManager.shadeModel(7425);
         GlStateManager.disableTexture2D();
         int maxScroll = this.func_148135_f();
         if (maxScroll > 0) {
            int barHeight = MathHelper.clamp_int(
               (this.bottom - this.top) * (this.bottom - this.top) / Math.max(this.getContentHeight(), 1), 32, this.bottom - this.top - 8
            );
            this.scrollAnimation.animateTo(this.amountScrolled, 0.3F);
            if (this.initialClickY > 0) {
               this.scrollAnimation.set(this.amountScrolled);
            }

            float barTop = this.scrollAnimation.get() * (this.bottom - this.top - barHeight) / maxScroll + this.top;
            if (barTop < this.top) {
               barTop = this.top;
            }

            RenderTask scrollBarTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
            scrollBarTask.addCommand(RoundedRectangleCommand.of(scrollBarX - 1, this.top, scrollBarRight - scrollBarX, this.bottom - this.top - 1.0F, 1.5F, themeColor.getSurfaceVariant()));
            scrollBarTask.addCommand(RoundedRectangleCommand.of(scrollBarX - 1, barTop, scrollBarRight - scrollBarX, barHeight - 1, 1.5F, themeColor.getPrimary()));
            scrollBarTask.render();
         }

         this.func_148142_b(mouseX, mouseY);
         GlStateManager.enableTexture2D();
         GlStateManager.shadeModel(7424);
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
      }
   }

   protected void drawAccountSlots(int x, float y, int mouseX, int mouseY) {
      int size = this.getSize();
      Tessellator.getInstance().getWorldRenderer();
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      int columnCount = (int)Math.floor((float)(scaledResolution.Method237() - 200) / (this.getListWidth() + 3));

      for (int i = 0; i < size; i++) {
         float slotY = y + i / columnCount * this.slotHeight + this.headerPadding;
         int slotH = this.slotHeight - 4;
         if (slotY > this.bottom || slotY + slotH < this.top) {
            this.func_178040_a(i, x, MathHelper.ceiling_float_int(slotY));
         }

         if (slotY >= this.top - this.slotHeight && slotY <= this.bottom) {
            this.drawAccountEntry(i, x, slotY, slotH, mouseX, mouseY, i % columnCount, columnCount);
         }
      }
   }

   protected void drawAccountEntry(int index, int x, float y, int slotHeight, int mouseX, int mouseY, int columnIndex, int columnCount) {
      ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
      Account account = this.getAccount(index);
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      boolean isSelected = this.isSelected(index);
      int renderX = x + 2;
      float renderY = y;
      if (!(renderY >= scaledResolution.Method238()) && !(renderY < 0.0F)) {
         int columnSpacing = this.getListWidth() + 3;
         float columnOffset = columnSpacing * columnIndex - columnSpacing * columnCount / 2.0F;
         float textOffsetX = 3.0F + columnOffset;
         GlStateManager.enableBlend();
         RenderTask slotTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         int slotWidth = this.getListWidth();
         float boxX = this.left + this.width / 2.0F + columnOffset;
         float boxY = renderY - 1.0F;
         int boxHeight = this.slotHeight - 3;
         boolean isHovered = mouseX > boxX && mouseX < boxX + slotWidth && mouseY > boxY && mouseY < boxY + boxHeight && mouseY > this.top && mouseY < this.bottom;
         slotTask.addCommand(
            RoundedRectangleCommand.of(
               boxX,
               boxY,
               slotWidth,
               boxHeight,
               8.0F,
               isSelected ? themeColor.getPrimary() : new RenderColor(themeColor.getSurfaceVariant()).withAlphaFraction(isHovered ? 0.65F : 1.0F).getRGB()
            )
         );
         slotTask.render();
         GlStateManager.disableBlend();
         GL11.glTranslated(renderX, renderY, 0.0);
         this.drawPlayerHead(account.getSession().getName(), boxX + 6.5F, boxY + 6.5F, 24, 24);
         GL11.glTranslated(-renderX, -renderY, 0.0);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
         RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
         fontRenderer.drawInternal(
            textTask,
            account.getSession().getName(),
            8.0F,
            renderX + textOffsetX + 30.0F,
            renderY + 6.0F + 12.5F - fontRenderer.getLineHeight(8.0F),
            isSelected ? themeColor.getOnPrimary() : themeColor.getOnSecondaryContainer(),
            false,
            false,
            0.02F
         );
         fontRenderer.draw(
            textTask,
            StringUtils.capitalize(account.getType().name().toLowerCase()),
            8.0F,
            renderX + textOffsetX + 30.0F,
            renderY + 6.0F + 12.5F,
            isSelected ? themeColor.getOnPrimary() : themeColor.getOnSecondaryContainer()
         );
         textTask.render();
         GlStateManager.disableBlend();
      }
   }

   private void drawPlayerHead(String name, float x, float y, int width, int height) {
      try {
         AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name).loadTexture(Minecraft.getMinecraft().getResourceManager());
         Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
         GL11.glEnable(GL11.GL_BLEND);
         RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_ROUND).build();
         renderTask.addCommand(RoundedTextureCommand.of(x - 1.0F, y - 1.0F, x + width + 2.0F, y + height + 2.0F, 0.125F, 0.125F, 0.25F, 0.25F, 4.0F, -1));
         renderTask.addCommand(RoundedTextureCommand.of(x - 1.0F, y - 1.0F, x + width + 2.0F, y + height + 2.0F, 0.625F, 0.125F, 0.75F, 0.25F, 4.0F, -1));
         renderTask.render();
         GL11.glDisable(GL11.GL_BLEND);
      } catch (Exception e) {
      }
   }

   @Override
   public void handleMouseInput() {
      int columnCount = (int)Math.floor((float)(ScaleHandler.getScaledResolution().Method237() - 200) / (this.getListWidth() + 3));
      int size = this.getSize();
      int slotBaseX = this.left + this.width / 2 + 2;
      float slotBaseY = this.top + 4 - this.scrollAnimation.get();
      if (this.isMouseYWithinSlotBounds(this.mouseY)) {
         if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
            for (int i = 0; i < size; i++) {
               float slotY = slotBaseY + i / columnCount * this.slotHeight + this.headerPadding;
               int slotH = this.slotHeight - 4;
               if (slotY > this.bottom || slotY + slotH < this.top) {
                  this.func_178040_a(i, slotBaseX, MathHelper.ceiling_float_int(slotY));
               }

               if (slotY >= this.top - this.slotHeight && slotY <= this.bottom) {
                  int columnSpacing = this.getListWidth() + 3;
                  float xOffset = columnSpacing * (i % columnCount) - columnSpacing * columnCount / 2.0F;
                  int slotWidth = this.getListWidth();
                  float slotX = this.left + this.width / 2.0F + xOffset;
                  float boxY = slotY - 1.0F;
                  int boxHeight = this.slotHeight - 3;
                  if (this.isMouseOver(this.mouseX, this.mouseY, slotX, boxY, slotWidth, boxHeight)) {
                     this.elementClicked(i, false, this.mouseX, this.mouseY);
                     this.selectedElement = i;
                  }
               }
            }
         }

         if (!Mouse.isButtonDown(0) || !this.getEnabled()) {
            this.initialClickY = -1;
         } else if (this.initialClickY == -1) {
            boolean allowScrollDrag = true;
            if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
               boolean clickedEntry = false;

               for (int i = 0; i < size; i++) {
                  float slotY = slotBaseY + i / columnCount * this.slotHeight + this.headerPadding;
                  int slotH = this.slotHeight - 4;
                  if (slotY > this.bottom || slotY + slotH < this.top) {
                     this.func_178040_a(i, slotBaseX, MathHelper.ceiling_float_int(slotY));
                  }

                  if (slotY >= this.top - this.slotHeight && slotY <= this.bottom) {
                     int columnSpacing = this.getListWidth() + 3;
                     float xOffset = columnSpacing * (i % columnCount) - columnSpacing * columnCount / 2.0F;
                     GlStateManager.enableBlend();
                     int slotWidth = this.getListWidth();
                     float slotX = this.left + this.width / 2.0F + xOffset;
                     float boxY = slotY - 1.0F;
                     int boxHeight = this.slotHeight - 3;
                     if (this.isMouseOver(this.mouseX, this.mouseY, slotX, boxY, slotWidth, boxHeight)) {
                        boolean isDoubleClick = i == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                        this.elementClicked(i, isDoubleClick, this.mouseX, this.mouseY);
                        this.selectedElement = i;
                        this.lastClicked = Minecraft.getSystemTime();
                        clickedEntry = true;
                     }
                  }
               }

               if (clickedEntry) {
                  allowScrollDrag = false;
               }

               int scrollBarX = this.getScrollBarX();
               int scrollBarRight = scrollBarX + 6;
               if (this.mouseX >= scrollBarX && this.mouseX <= scrollBarRight) {
                  this.scrollMultiplier = -1.0F;
                  int maxScroll = this.func_148135_f();
                  if (maxScroll < 1) {
                     maxScroll = 1;
                  }

                  int barHeight = MathHelper.clamp_int(
                     (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / this.getContentHeight()), 32, this.bottom - this.top - 8
                  );
                  this.scrollMultiplier = this.scrollMultiplier / ((float)(this.bottom - this.top - barHeight) / maxScroll);
               } else {
                  this.scrollMultiplier = 1.0F;
               }

               if (allowScrollDrag) {
                  this.initialClickY = this.mouseY;
               } else {
                  this.initialClickY = -2;
               }
            } else {
               this.initialClickY = -2;
            }
         } else if (this.initialClickY >= 0) {
            this.amountScrolled = this.amountScrolled - (this.mouseY - this.initialClickY) * this.scrollMultiplier;
            this.initialClickY = this.mouseY;
         }

         int wheelDelta = Mouse.getEventDWheel();
         if (wheelDelta != 0) {
            if (wheelDelta > 0) {
               wheelDelta = -1;
            } else if (wheelDelta < 0) {
               wheelDelta = 1;
            }

            this.amountScrolled = this.amountScrolled + wheelDelta * this.slotHeight / 2;
         }
      }
   }

   @Override
   protected int getContentHeight() {
      int columnCount = (int)Math.floor((float)(ScaleHandler.getScaledResolution().Method237() - 200) / (this.getListWidth() + 3));
      return this.getSize() / Math.max(columnCount, 1) * this.slotHeight + this.headerPadding;
   }

   @Override
   protected int getScrollBarX() {
      int columnCount = (int)Math.floor((float)(ScaleHandler.getScaledResolution().Method237() - 200) / (this.getListWidth() + 3));
      return this.width / 2 + columnCount * (this.getListWidth() + 3) / 2 + 4;
   }

   private boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public Account getAccount(int index) {
      return Moon.getInstance().getAccountManager().getAccounts().get(index);
   }
}
