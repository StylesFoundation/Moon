package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiMerchant extends GuiContainer {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
   private final IMerchant merchant;
   private GuiMerchant.MerchantButton nextButton;
   private GuiMerchant.MerchantButton previousButton;
   private int selectedMerchantRecipe;
   private final IChatComponent chatComponent;

   public GuiMerchant(InventoryPlayer inventoryPlayer, IMerchant iMerchant, World world) {
      super(new ContainerMerchant(inventoryPlayer, iMerchant, world));
      this.merchant = iMerchant;
      this.chatComponent = iMerchant.getDisplayName();
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      int var3 = (this.width - this.xSize) / 2;
      int var4 = (this.height - this.ySize) / 2;
      this.m.add(this.nextButton = new GuiMerchant.MerchantButton(1, var3 + 120 + 27, var4 + 24 - 1, true));
      this.m.add(this.previousButton = new GuiMerchant.MerchantButton(2, var3 + 36 - 19, var4 + 24 - 1, false));
      this.nextButton.enabled = false;
      this.previousButton.enabled = false;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      String var5 = this.chatComponent.getUnformattedText();
      this.fontRendererObj.drawString(var5, this.xSize / 2 - this.fontRendererObj.getStringWidth(var5) / 2, 6.0F, 4210752);
      this.fontRendererObj.drawString(I18n.format("container.inventory"), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      MerchantRecipeList var1 = this.merchant.getRecipes(this.mc.thePlayer);
      if (var1 != null) {
         this.nextButton.enabled = this.selectedMerchantRecipe < var1.size() - 1;
         this.previousButton.enabled = this.selectedMerchantRecipe > 0;
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      boolean var2 = false;
      if (guiButton == this.nextButton) {
         this.selectedMerchantRecipe++;
         MerchantRecipeList var3 = this.merchant.getRecipes(this.mc.thePlayer);
         if (var3 != null && this.selectedMerchantRecipe >= var3.size()) {
            this.selectedMerchantRecipe = var3.size() - 1;
         }

         var2 = true;
      } else if (guiButton == this.previousButton) {
         this.selectedMerchantRecipe--;
         if (this.selectedMerchantRecipe < 0) {
            this.selectedMerchantRecipe = 0;
         }

         var2 = true;
      }

      if (var2) {
         ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
         PacketBuffer var4 = new PacketBuffer(Unpooled.buffer());
         var4.writeInt(this.selectedMerchantRecipe);
         this.mc.getNetHandler().Method754(new C17PacketCustomPayload("MC|TrSel", var4));
      }
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      MerchantRecipeList var8 = this.merchant.getRecipes(this.mc.thePlayer);
      if (var8 != null && !var8.isEmpty()) {
         int var9 = this.selectedMerchantRecipe;
         if (var9 < 0 || var9 >= var8.size()) {
            return;
         }

         MerchantRecipe var10 = var8.get(var9);
         if (var10.isRecipeDisabled()) {
            this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
            this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      MerchantRecipeList var6 = this.merchant.getRecipes(this.mc.thePlayer);
      if (var6 != null && !var6.isEmpty()) {
         int var7 = (this.width - this.xSize) / 2;
         int var8 = (this.height - this.ySize) / 2;
         int var9 = this.selectedMerchantRecipe;
         MerchantRecipe var10 = var6.get(var9);
         ItemStack var11 = var10.getItemToBuy();
         ItemStack var12 = var10.getSecondItemToBuy();
         ItemStack var13 = var10.getItemToSell();
         GlStateManager.pushMatrix();
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableColorMaterial();
         GlStateManager.enableLighting();
         this.itemRender.zLevel = 100.0F;
         this.itemRender.renderItemAndEffectIntoGUI(var11, var7 + 36, var8 + 24);
         this.itemRender.renderItemOverlays(this.fontRendererObj, var11, var7 + 36, var8 + 24);
         if (var12 != null) {
            this.itemRender.renderItemAndEffectIntoGUI(var12, var7 + 62, var8 + 24);
            this.itemRender.renderItemOverlays(this.fontRendererObj, var12, var7 + 62, var8 + 24);
         }

         this.itemRender.renderItemAndEffectIntoGUI(var13, var7 + 120, var8 + 24);
         this.itemRender.renderItemOverlays(this.fontRendererObj, var13, var7 + 120, var8 + 24);
         this.itemRender.zLevel = 0.0F;
         GlStateManager.disableLighting();
         if (this.isPointInRegion(36, 24, 16, 16, mouseX, mouseY) && var11 != null) {
            this.renderToolTip(var11, mouseX, mouseY);
         } else if (var12 != null && this.isPointInRegion(62, 24, 16, 16, mouseX, mouseY) && var12 != null) {
            this.renderToolTip(var12, mouseX, mouseY);
         } else if (var13 != null && this.isPointInRegion(120, 24, 16, 16, mouseX, mouseY) && var13 != null) {
            this.renderToolTip(var13, mouseX, mouseY);
         } else if (var10.isRecipeDisabled() && (this.isPointInRegion(83, 21, 28, 21, mouseX, mouseY) || this.isPointInRegion(83, 51, 28, 21, mouseX, mouseY))) {
            this.drawCreativeTabHoveringText(I18n.format("merchant.deprecated"), mouseX, mouseY);
         }

         GlStateManager.popMatrix();
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
      }
   }

   public IMerchant getMerchant() {
      return this.merchant;
   }

   static class MerchantButton extends GuiButton {
      private final boolean field_146157_o;

      public MerchantButton(int integer1, int integer2, int integer3, boolean boolean4) {
         super(integer1, integer2, integer3, 12, 19, "");
         this.field_146157_o = boolean4;
      }

      @Override
      public void drawButton(Minecraft minecraft, int integer2, int integer3) {
         if (this.visible) {
            minecraft.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var6 = integer2 >= this.xPosition
               && integer3 >= this.yPosition
               && integer2 < this.xPosition + this.width
               && integer3 < this.yPosition + this.h;
            int var7 = 0;
            int var8 = 176;
            if (!this.enabled) {
               var8 += this.width * 2;
            } else if (var6) {
               var8 += this.width;
            }

            if (!this.field_146157_o) {
               var7 = 0 + this.h;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var8, var7, this.width, this.h);
         }
      }
   }
}
