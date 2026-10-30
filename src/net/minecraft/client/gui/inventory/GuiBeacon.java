package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiBeacon extends GuiContainer {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
   public IInventory tileBeacon;
   private GuiBeacon.ConfirmButton beaconConfirmButton;
   private boolean buttonsNotDrawn;

   public GuiBeacon(InventoryPlayer inventoryPlayer, IInventory iInventory) {
      super(new ContainerBeacon(inventoryPlayer, iInventory));
      this.tileBeacon = iInventory;
      this.xSize = 230;
      this.ySize = 219;
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      this.m.add(this.beaconConfirmButton = new GuiBeacon.ConfirmButton(-1, this.guiLeft + 164, this.guiTop + 107));
      this.m.add(new GuiBeacon.CancelButton(-2, this.guiLeft + 190, this.guiTop + 107));
      this.buttonsNotDrawn = true;
      this.beaconConfirmButton.enabled = false;
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      int var3 = this.tileBeacon.getField(0);
      int var4 = this.tileBeacon.getField(1);
      int var5 = this.tileBeacon.getField(2);
      if (this.buttonsNotDrawn && var3 >= 0) {
         this.buttonsNotDrawn = false;

         for (int var6 = 0; var6 <= 2; var6++) {
            int var7 = TileEntityBeacon.effectsList[var6].length;
            int var8 = var7 * 22 + (var7 - 1) * 2;

            for (int var9 = 0; var9 < var7; var9++) {
               int var10 = TileEntityBeacon.effectsList[var6][var9].id;
               GuiBeacon.PowerButton var11 = new GuiBeacon.PowerButton(
                  var6 << 8 | var10, this.guiLeft + 76 + var9 * 24 - var8 / 2, this.guiTop + 22 + var6 * 25, var10, var6
               );
               this.m.add(var11);
               if (var6 >= var3) {
                  var11.enabled = false;
               } else if (var10 == var4) {
                  var11.func_146140_b(true);
               }
            }
         }

         int var12 = TileEntityBeacon.effectsList[3].length + 1;
         int var13 = var12 * 22 + (var12 - 1) * 2;

         for (int var14 = 0; var14 < var12 - 1; var14++) {
            int var16 = TileEntityBeacon.effectsList[3][var14].id;
            GuiBeacon.PowerButton var17 = new GuiBeacon.PowerButton(768 | var16, this.guiLeft + 167 + var14 * 24 - var13 / 2, this.guiTop + 47, var16, 3);
            this.m.add(var17);
            if (3 >= var3) {
               var17.enabled = false;
            } else if (var16 == var5) {
               var17.func_146140_b(true);
            }
         }

         if (var4 > 0) {
            GuiBeacon.PowerButton var15 = new GuiBeacon.PowerButton(768 | var4, this.guiLeft + 167 + (var12 - 1) * 24 - var13 / 2, this.guiTop + 47, var4, 3);
            this.m.add(var15);
            if (3 >= var3) {
               var15.enabled = false;
            } else if (var4 == var5) {
               var15.func_146140_b(true);
            }
         }
      }

      this.beaconConfirmButton.enabled = this.tileBeacon.getStackInSlot(0) != null && var4 > 0;
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == -2) {
         this.mc.displayGuiScreen(null);
      } else if (guiButton.l == -1) {
         PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
         var5.writeInt(this.tileBeacon.getField(1));
         var5.writeInt(this.tileBeacon.getField(2));
         this.mc.getNetHandler().Method754(new C17PacketCustomPayload("MC|Beacon", var5));
         this.mc.displayGuiScreen(null);
      } else if (guiButton instanceof GuiBeacon.PowerButton) {
         if (((GuiBeacon.PowerButton)guiButton).func_146141_c()) {
            return;
         }

         int var4 = guiButton.l;
         int var7 = var4 & 0xFF;
         int var6 = var4 >> 8;
         if (var6 < 3) {
            this.tileBeacon.setField(1, var7);
         } else {
            this.tileBeacon.setField(2, var7);
         }

         this.m.clear();
         this.onInitGui();
         this.onUpdateScreen();
      }
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      RenderHelper.disableStandardItemLighting();
      this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary"), 62, 10, 14737632);
      this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary"), 169, 10, 14737632);

      for (GuiButton var6 : this.m) {
         if (var6.isMouseOver()) {
            var6.drawButtonForegroundLayer(integer1 - this.guiLeft, integer2 - this.guiTop);
            break;
         }
      }

      RenderHelper.enableGUIStandardItemLighting();
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(beaconGuiTextures);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      this.itemRender.zLevel = 100.0F;
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), var6 + 42, var7 + 109);
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), var6 + 42 + 22, var7 + 109);
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), var6 + 42 + 44, var7 + 109);
      this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), var6 + 42 + 66, var7 + 109);
      this.itemRender.zLevel = 0.0F;
   }

   static void access$100(GuiBeacon guiBeacon, String string, int integer3, int integer4) {
      guiBeacon.drawCreativeTabHoveringText(string, integer3, integer4);
   }

   static void access$200(GuiBeacon guiBeacon, String string, int integer3, int integer4) {
      guiBeacon.drawCreativeTabHoveringText(string, integer3, integer4);
   }

   static void access$300(GuiBeacon guiBeacon, String string, int integer3, int integer4) {
      guiBeacon.drawCreativeTabHoveringText(string, integer3, integer4);
   }

   static class Button extends GuiButton {
      private final ResourceLocation field_146145_o;
      private final int field_146144_p;
      private final int field_146143_q;
      private boolean field_146142_r;

      protected Button(int integer1, int integer2, int integer3, ResourceLocation resourceLocation, int integer5, int integer6) {
         super(integer1, integer2, integer3, 22, 22, "");
         this.field_146145_o = resourceLocation;
         this.field_146144_p = integer5;
         this.field_146143_q = integer6;
      }

      @Override
      public void drawButton(Minecraft minecraft, int integer2, int integer3) {
         if (this.visible) {
            minecraft.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = integer2 >= this.xPosition
               && integer3 >= this.yPosition
               && integer2 < this.xPosition + this.width
               && integer3 < this.yPosition + this.h;
            short var6 = 219;
            int var7 = 0;
            if (!this.enabled) {
               var7 = 0 + this.width * 2;
            } else if (this.field_146142_r) {
               var7 = 0 + this.width;
            } else if (this.hovered) {
               var7 = 0 + this.width * 3;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var7, var6, this.width, this.h);
            if (!GuiBeacon.beaconGuiTextures.equals(this.field_146145_o)) {
               minecraft.getTextureManager().bindTexture(this.field_146145_o);
            }

            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
         }
      }

      public boolean func_146141_c() {
         return this.field_146142_r;
      }

      public void func_146140_b(boolean boolean1) {
         this.field_146142_r = boolean1;
      }
   }

   class CancelButton extends GuiBeacon.Button {
      public CancelButton(int integer2, int integer3, int integer4) {
         super(integer2, integer3, integer4, GuiBeacon.beaconGuiTextures, 112, 220);
      }

      @Override
      public void drawButtonForegroundLayer(int integer1, int integer2) {
         GuiBeacon.access$100(GuiBeacon.this, I18n.format("gui.cancel"), integer1, integer2);
      }
   }

   class ConfirmButton extends GuiBeacon.Button {
      public ConfirmButton(int integer2, int integer3, int integer4) {
         super(integer2, integer3, integer4, GuiBeacon.beaconGuiTextures, 90, 220);
      }

      @Override
      public void drawButtonForegroundLayer(int integer1, int integer2) {
         GuiBeacon.access$200(GuiBeacon.this, I18n.format("gui.done"), integer1, integer2);
      }
   }

   class PowerButton extends GuiBeacon.Button {
      private final int field_146149_p;
      private final int field_146148_q;

      public PowerButton(int integer2, int integer3, int integer4, int integer5, int integer6) {
         super(
            integer2,
            integer3,
            integer4,
            GuiContainer.inventoryBackground,
            Potion.potionTypes[integer5].getStatusIconIndex() % 8 * 18,
            198 + Potion.potionTypes[integer5].getStatusIconIndex() / 8 * 18
         );
         this.field_146149_p = integer5;
         this.field_146148_q = integer6;
      }

      @Override
      public void drawButtonForegroundLayer(int integer1, int integer2) {
         String var3 = I18n.format(Potion.potionTypes[this.field_146149_p].getName());
         if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
            var3 = var3 + " II";
         }

         GuiBeacon.access$300(GuiBeacon.this, var3, integer1, integer2);
      }
   }
}
