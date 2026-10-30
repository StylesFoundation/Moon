package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import org.lwjgl.util.glu.Project;

public class GuiEnchantment extends GuiContainer {
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private static final ModelBook MODEL_BOOK = new ModelBook();
   private final InventoryPlayer playerInventory;
   private final Random random = new Random();
   private final ContainerEnchantment container;
   public int field_147073_u;
   public float field_147071_v;
   public float field_147069_w;
   public float field_147082_x;
   public float field_147081_y;
   public float field_147080_z;
   public float field_147076_A;
   ItemStack field_147077_B;
   public final IWorldNameable field_175380_I;

   public GuiEnchantment(InventoryPlayer inventoryPlayer, World world, IWorldNameable iWorldNameable) {
      super(new ContainerEnchantment(inventoryPlayer, world));
      this.playerInventory = inventoryPlayer;
      this.container = (ContainerEnchantment)this.inventorySlots;
      this.field_175380_I = iWorldNameable;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12.0F, 5.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      this.func_147068_g();
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;

      for (int var8 = 0; var8 < 3; var8++) {
         int var9 = mouseX - (var6 + 60);
         int var10 = mouseY - (var7 + 14 + 19 * var8);
         if (var9 >= 0 && var10 >= 0 && var9 < 108 && var10 < 19 && this.container.enchantItem(this.mc.thePlayer, var8)) {
            this.mc.playerController.sendEnchantPacket(this.container.windowId, var8);
         }
      }
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      GlStateManager.pushMatrix();
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      ScaledResolution var8 = new ScaledResolution(this.mc);
      GlStateManager.viewport(
         (var8.Method237() - 320) / 2 * var8.getScaleFactor(),
         (var8.Method238() - 240) / 2 * var8.getScaleFactor(),
         320 * var8.getScaleFactor(),
         240 * var8.getScaleFactor()
      );
      GlStateManager.translate(-0.34F, 0.23F, 0.0F);
      Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.translate(0.0F, 3.3F, -16.0F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.scale(5.0F, 5.0F, 5.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
      GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
      float var11 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * float1;
      GlStateManager.translate((1.0F - var11) * 0.2F, (1.0F - var11) * 0.1F, (1.0F - var11) * 0.25F);
      GlStateManager.rotate(-(1.0F - var11) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      float var12 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * float1 + 0.25F;
      float var13 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * float1 + 0.75F;
      var12 = (var12 - MathHelper.truncateDoubleToInt(var12)) * 1.6F - 0.3F;
      var13 = (var13 - MathHelper.truncateDoubleToInt(var13)) * 1.6F - 0.3F;
      if (var12 < 0.0F) {
         var12 = 0.0F;
      }

      if (var13 < 0.0F) {
         var13 = 0.0F;
      }

      if (var12 > 1.0F) {
         var12 = 1.0F;
      }

      if (var13 > 1.0F) {
         var13 = 1.0F;
      }

      GlStateManager.enableRescaleNormal();
      MODEL_BOOK.render(null, 0.0F, var12, var13, var11, 0.0F, 0.0625F);
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.matrixMode(5889);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
      int var14 = this.container.getLapisAmount();

      for (int var15 = 0; var15 < 3; var15++) {
         int var16 = var6 + 60;
         int var17 = var16 + 20;
         byte var18 = 86;
         String var19 = EnchantmentNameParts.getInstance().generateNewRandomName();
         this.zLevel = 0.0F;
         this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
         int var20 = this.container.enchantLevels[var15];
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if (var20 == 0) {
            this.drawTexturedModalRect(var16, var7 + 14 + 19 * var15, 0, 185, 108, 19);
         } else {
            String var21 = var20 + "";
            FontRenderer var22 = this.mc.standardGalacticFontRenderer;
            int var23 = 6839882;
            if ((var14 < var15 + 1 || this.mc.thePlayer.experienceLevel < var20) && !this.mc.thePlayer.capabilities.isCreativeMode) {
               this.drawTexturedModalRect(var16, var7 + 14 + 19 * var15, 0, 185, 108, 19);
               this.drawTexturedModalRect(var16 + 1, var7 + 15 + 19 * var15, 16 * var15, 239, 16, 16);
               var22.drawSplitString(var19, var17, var7 + 16 + 19 * var15, var18, 3419941);
               var23 = 4226832;
            } else {
               int var24 = integer2 - (var6 + 60);
               int var25 = integer3 - (var7 + 14 + 19 * var15);
               if (var24 >= 0 && var25 >= 0 && var24 < 108 && var25 < 19) {
                  this.drawTexturedModalRect(var16, var7 + 14 + 19 * var15, 0, 204, 108, 19);
                  var23 = 16777088;
               } else {
                  this.drawTexturedModalRect(var16, var7 + 14 + 19 * var15, 0, 166, 108, 19);
               }

               this.drawTexturedModalRect(var16 + 1, var7 + 15 + 19 * var15, 16 * var15, 223, 16, 16);
               var22.drawSplitString(var19, var17, var7 + 16 + 19 * var15, var18, var23);
               var23 = 8453920;
            }

            var22 = this.mc.fontRendererObj;
            var22.drawStringWithShadow(var21, var17 + 86 - var22.getStringWidth(var21), var7 + 16 + 19 * var15 + 7, var23);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      boolean var6 = this.mc.thePlayer.capabilities.isCreativeMode;
      int var7 = this.container.getLapisAmount();

      for (int var8 = 0; var8 < 3; var8++) {
         int var9 = this.container.enchantLevels[var8];
         int var10 = this.container.enchantmentIds[var8];
         int var11 = var8 + 1;
         if (this.isPointInRegion(60, 14 + 19 * var8, 108, 17, mouseX, mouseY) && var9 > 0 && var10 >= 0) {
            ArrayList var12 = Lists.newArrayList();
            if (var10 >= 0 && Enchantment.getEnchantmentById(var10 & 0xFF) != null) {
               String var13 = Enchantment.getEnchantmentById(var10 & 0xFF).Method532((var10 & 0xFF00) >> 8);
               var12.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC + I18n.format("container.enchant.clue", var13));
            }

            if (!var6) {
               if (var10 >= 0) {
                  var12.add("");
               }

               if (this.mc.thePlayer.experienceLevel < var9) {
                  var12.add(EnumChatFormatting.RED + "Level Requirement: " + this.container.enchantLevels[var8]);
               } else {
                  String var14;
                  if (var11 == 1) {
                     var14 = I18n.format("container.enchant.lapis.one");
                  } else {
                     var14 = I18n.format("container.enchant.lapis.many", var11);
                  }

                  if (var7 >= var11) {
                     var12.add(EnumChatFormatting.GRAY + var14);
                  } else {
                     var12.add(EnumChatFormatting.RED + var14);
                  }

                  if (var11 == 1) {
                     var14 = I18n.format("container.enchant.level.one");
                  } else {
                     var14 = I18n.format("container.enchant.level.many", var11);
                  }

                  var12.add(EnumChatFormatting.GRAY + var14);
               }
            }

            this.drawHoveringText(var12, mouseX, mouseY);
            break;
         }
      }
   }

   public void func_147068_g() {
      ItemStack var1 = this.inventorySlots.getSlot(0).getStack();
      if (!ItemStack.areItemStacksEqual(var1, this.field_147077_B)) {
         this.field_147077_B = var1;

         do {
            this.field_147082_x = this.field_147082_x + (this.random.nextInt(4) - this.random.nextInt(4));
         } while (!(this.field_147071_v > this.field_147082_x + 1.0F) && !(this.field_147071_v < this.field_147082_x - 1.0F));
      }

      this.field_147073_u++;
      this.field_147069_w = this.field_147071_v;
      this.field_147076_A = this.field_147080_z;
      boolean var2 = false;

      for (int var3 = 0; var3 < 3; var3++) {
         if (this.container.enchantLevels[var3] != 0) {
            var2 = true;
            break;
         }
      }

      if (var2) {
         this.field_147080_z += 0.2F;
      } else {
         this.field_147080_z -= 0.2F;
      }

      this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0F, 1.0F);
      float var4 = (this.field_147082_x - this.field_147071_v) * 0.4F;
      var4 = MathHelper.clamp_float(var4, -0.2F, 0.2F);
      this.field_147081_y = this.field_147081_y + (var4 - this.field_147081_y) * 0.9F;
      this.field_147071_v = this.field_147071_v + this.field_147081_y;
   }
}
