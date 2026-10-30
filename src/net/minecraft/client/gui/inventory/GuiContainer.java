package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;
import com.moon.Moon;
import com.moon.module.Module;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.player.ChestStealer;
import com.moon.module.impl.player.InventoryManager;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public abstract class GuiContainer extends GuiScreen {
   protected static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
   protected int xSize = 176;
   protected int ySize = 166;
   public Container inventorySlots;
   protected int guiLeft;
   protected int guiTop;
   private Slot theSlot;
   private Slot clickedSlot;
   private boolean isRightMouseClick;
   private ItemStack draggedStack;
   private int touchUpX;
   private int touchUpY;
   private Slot returningStackDestSlot;
   private long returningStackTime;
   private ItemStack returningStack;
   private Slot currentDragTargetSlot;
   private long dragItemDropDelay;
   protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
   protected boolean dragSplitting;
   private int dragSplittingLimit;
   private int dragSplittingButton;
   private boolean ignoreMouseUp;
   private int dragSplittingRemnant;
   private long lastClickTime;
   private Slot lastClickSlot;
   private int lastClickButton;
   private boolean doubleClick;
   private ItemStack shiftClickedSlot;

   public GuiContainer(Container container) {
      this.inventorySlots = container;
      this.ignoreMouseUp = true;
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      this.mc.thePlayer.openContainer = this.inventorySlots;
      this.guiLeft = (this.width - this.xSize) / 2;
      this.guiTop = (this.height - this.ySize) / 2;
      this.m.add(new GuiButton(2, 2, 2, 180, 20, "Disable Inventory Manager", true));
      this.m.add(new GuiButton(3, 2, 24, 180, 20, "Disable Chest Stealer", true));
      this.m.add(new GuiButton(4, 2, 46, 180, 20, "Disable Kill Aura", true));
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      int var6 = this.guiLeft;
      int var7 = this.guiTop;
      this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      RenderHelper.enableGUIStandardItemLighting();
      GlStateManager.pushMatrix();
      GlStateManager.translate(var6, var7, 0.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableRescaleNormal();
      this.theSlot = null;
      short var8 = 240;
      short var9 = 240;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      for (int var10 = 0; var10 < this.inventorySlots.inventorySlots.size(); var10++) {
         Slot var11 = this.inventorySlots.inventorySlots.get(var10);
         this.drawSlot(var11);
         if (this.isMouseOverSlot(var11, mouseX, mouseY) && var11.canBeHovered()) {
            this.theSlot = var11;
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int var12 = var11.xDisplayPosition;
            int var13 = var11.yDisplayPosition;
            GlStateManager.colorMask(true, true, true, false);
            this.drawGradientRect(var12, var13, var12 + 16, var13 + 16, -2130706433, -2130706433);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
         }
      }

      RenderHelper.disableStandardItemLighting();
      this.drawGuiContainerForegroundLayer(mouseX, mouseY);
      RenderHelper.enableGUIStandardItemLighting();
      InventoryPlayer var17 = this.mc.thePlayer.inventory;
      ItemStack var18 = this.draggedStack == null ? var17.getItemStack() : this.draggedStack;
      if (var18 != null) {
         byte var19 = 8;
         int var22 = this.draggedStack == null ? 8 : 16;
         String var14 = null;
         if (this.draggedStack != null && this.isRightMouseClick) {
            var18 = var18.copy();
            var18.stackSize = MathHelper.ceiling_float_int(var18.stackSize / 2.0F);
         } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
            var18 = var18.copy();
            var18.stackSize = this.dragSplittingRemnant;
            if (var18.stackSize == 0) {
               var14 = EnumChatFormatting.YELLOW + "0";
            }
         }

         this.drawItemStack(var18, mouseX - var6 - var19, mouseY - var7 - var22, var14);
      }

      if (this.returningStack != null) {
         float var20 = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
         if (var20 >= 1.0F) {
            var20 = 1.0F;
            this.returningStack = null;
         }

         int var23 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
         int var24 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
         int var15 = this.touchUpX + (int)(var23 * var20);
         int var16 = this.touchUpY + (int)(var24 * var20);
         this.drawItemStack(this.returningStack, var15, var16, null);
      }

      GlStateManager.popMatrix();
      if (var17.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
         ItemStack var21 = this.theSlot.getStack();
         this.renderToolTip(var21, mouseX, mouseY);
      }

      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
   }

   private void drawItemStack(ItemStack itemStack, int integer2, int integer3, String string) {
      GlStateManager.translate(0.0F, 0.0F, 32.0F);
      this.zLevel = 200.0F;
      this.itemRender.zLevel = 200.0F;
      this.itemRender.renderItemAndEffectIntoGUI(itemStack, integer2, integer3);
      this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemStack, integer2, integer3 - (this.draggedStack == null ? 0 : 8), string);
      this.zLevel = 0.0F;
      this.itemRender.zLevel = 0.0F;
   }

   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
   }

   protected abstract void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3);

   private void drawSlot(Slot slot) {
      int var4 = slot.xDisplayPosition;
      int var5 = slot.yDisplayPosition;
      ItemStack var6 = slot.getStack();
      boolean var7 = false;
      boolean var8 = slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
      ItemStack var9 = this.mc.thePlayer.inventory.getItemStack();
      String var10 = null;
      if (slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && var6 != null) {
         var6 = var6.copy();
         var6.stackSize /= 2;
      } else if (this.dragSplitting && this.dragSplittingSlots.contains(slot) && var9 != null) {
         if (this.dragSplittingSlots.size() == 1) {
            return;
         }

         if (Container.canAddItemToSlot(slot, var9, true) && this.inventorySlots.canDragIntoSlot(slot)) {
            var6 = var9.copy();
            var7 = true;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var6, slot.getStack() == null ? 0 : slot.getStack().stackSize);
            if (var6.stackSize > var6.getMaxStackSize()) {
               var10 = "" + EnumChatFormatting.YELLOW + var6.getMaxStackSize();
               var6.stackSize = var6.getMaxStackSize();
            }

            if (var6.stackSize > slot.getItemStackLimit(var6)) {
               var10 = "" + EnumChatFormatting.YELLOW + slot.getItemStackLimit(var6);
               var6.stackSize = slot.getItemStackLimit(var6);
            }
         } else {
            this.dragSplittingSlots.remove(slot);
            this.updateDragSplitting();
         }
      }

      this.zLevel = 100.0F;
      this.itemRender.zLevel = 100.0F;
      if (var6 == null) {
         String var11 = slot.getSlotTexture();
         if (var11 != null) {
            TextureAtlasSprite var12 = this.mc.getTextureMapBlocks().getAtlasSprite(var11);
            GlStateManager.disableLighting();
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.drawTexturedModalRect(var4, var5, var12, 16, 16);
            GlStateManager.enableLighting();
            var8 = true;
         }
      }

      if (!var8) {
         if (var7) {
            drawRect(var4, var5, var4 + 16, var5 + 16, -2130706433);
         }

         GlStateManager.enableDepth();
         this.itemRender.renderItemAndEffectIntoGUI(var6, var4, var5);
         this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, var6, var4, var5, var10);
      }

      this.itemRender.zLevel = 0.0F;
      this.zLevel = 0.0F;
   }

   private void updateDragSplitting() {
      ItemStack var1 = this.mc.thePlayer.inventory.getItemStack();
      if (var1 != null && this.dragSplitting) {
         this.dragSplittingRemnant = var1.stackSize;

         for (Slot var3 : this.dragSplittingSlots) {
            ItemStack var4 = var1.copy();
            int var5 = var3.getStack() == null ? 0 : var3.getStack().stackSize;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var4, var5);
            if (var4.stackSize > var4.getMaxStackSize()) {
               var4.stackSize = var4.getMaxStackSize();
            }

            if (var4.stackSize > var3.getItemStackLimit(var4)) {
               var4.stackSize = var3.getItemStackLimit(var4);
            }

            this.dragSplittingRemnant = this.dragSplittingRemnant - (var4.stackSize - var5);
         }
      }
   }

   private Slot getSlotAtPosition(int integer1, int integer2) {
      for (int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); var3++) {
         Slot var4 = this.inventorySlots.inventorySlots.get(var3);
         if (this.isMouseOverSlot(var4, integer1, integer2)) {
            return var4;
         }
      }

      return null;
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      boolean var6 = button == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
      Slot var7 = this.getSlotAtPosition(mouseX, mouseY);
      long var8 = Minecraft.getSystemTime();
      this.doubleClick = this.lastClickSlot == var7 && var8 - this.lastClickTime < 250L && this.lastClickButton == button;
      this.ignoreMouseUp = false;
      if (!this.checkHotbarKeys(button - 100) || var6) {
         if (button == 0 || button == 1 || var6) {
            int var10 = this.guiLeft;
            int var11 = this.guiTop;
            boolean var12 = mouseX < var10 || mouseY < var11 || mouseX >= var10 + this.xSize || mouseY >= var11 + this.ySize;
            int var13 = -1;
            if (var7 != null) {
               var13 = var7.slotNumber;
            }

            if (var12) {
               var13 = -999;
            }

            if (this.mc.gameSettings.touchscreen && var12 && this.mc.thePlayer.inventory.getItemStack() == null) {
               this.mc.displayGuiScreen(null);
               return;
            }

            if (var13 != -1) {
               if (this.mc.gameSettings.touchscreen) {
                  if (var7 != null && var7.getHasStack()) {
                     this.clickedSlot = var7;
                     this.draggedStack = null;
                     this.isRightMouseClick = button == 1;
                  } else {
                     this.clickedSlot = null;
                  }
               } else if (!this.dragSplitting) {
                  if (this.mc.thePlayer.inventory.getItemStack() == null) {
                     if (button == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.handleMouseClick(var7, var13, button, 3);
                     } else {
                        boolean var14 = var13 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                        byte var15 = 0;
                        if (var14) {
                           this.shiftClickedSlot = var7 != null && var7.getHasStack() ? var7.getStack() : null;
                           var15 = 1;
                        } else if (var13 == -999) {
                           var15 = 4;
                        }

                        this.handleMouseClick(var7, var13, button, var15);
                     }

                     this.ignoreMouseUp = true;
                  } else {
                     this.dragSplitting = true;
                     this.dragSplittingButton = button;
                     this.dragSplittingSlots.clear();
                     if (button == 0) {
                        this.dragSplittingLimit = 0;
                     } else if (button == 1) {
                        this.dragSplittingLimit = 1;
                     } else if (button == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.dragSplittingLimit = 2;
                     }
                  }
               }
            }
         }

         this.lastClickSlot = var7;
         this.lastClickTime = var8;
         this.lastClickButton = button;
      }
   }

   @Override
   protected void mouseClickMove(int integer1, int integer2, int integer3, long long4) {
      Slot var8 = this.getSlotAtPosition(integer1, integer2);
      ItemStack var9 = this.mc.thePlayer.inventory.getItemStack();
      if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
         if (integer3 == 0 || integer3 == 1) {
            if (this.draggedStack == null) {
               if (var8 != this.clickedSlot && this.clickedSlot.getStack() != null) {
                  this.draggedStack = this.clickedSlot.getStack().copy();
               }
            } else if (this.draggedStack.stackSize > 1 && var8 != null && Container.canAddItemToSlot(var8, this.draggedStack, false)) {
               long var10 = Minecraft.getSystemTime();
               if (this.currentDragTargetSlot == var8) {
                  if (var10 - this.dragItemDropDelay > 500L) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                     this.handleMouseClick(var8, var8.slotNumber, 1, 0);
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                     this.dragItemDropDelay = var10 + 750L;
                     this.draggedStack.stackSize--;
                  }
               } else {
                  this.currentDragTargetSlot = var8;
                  this.dragItemDropDelay = var10;
               }
            }
         }
      } else if (this.dragSplitting
         && var8 != null
         && var9 != null
         && var9.stackSize > this.dragSplittingSlots.size()
         && Container.canAddItemToSlot(var8, var9, true)
         && var8.isItemValid(var9)
         && this.inventorySlots.canDragIntoSlot(var8)) {
         this.dragSplittingSlots.add(var8);
         this.updateDragSplitting();
      }
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      Slot var6 = this.getSlotAtPosition(mouseX, mouseY);
      int var7 = this.guiLeft;
      int var8 = this.guiTop;
      boolean var9 = mouseX < var7 || mouseY < var8 || mouseX >= var7 + this.xSize || mouseY >= var8 + this.ySize;
      int var10 = -1;
      if (var6 != null) {
         var10 = var6.slotNumber;
      }

      if (var9) {
         var10 = -999;
      }

      if (this.doubleClick && var6 != null && button == 0 && this.inventorySlots.canMergeSlot(null, var6)) {
         if (isShiftKeyDown()) {
            if (var6 != null && var6.inventory != null && this.shiftClickedSlot != null) {
               for (Slot var16 : this.inventorySlots.inventorySlots) {
                  if (var16 != null
                     && var16.canTakeStack(this.mc.thePlayer)
                     && var16.getHasStack()
                     && var16.inventory == var6.inventory
                     && Container.canAddItemToSlot(var16, this.shiftClickedSlot, true)) {
                     this.handleMouseClick(var16, var16.slotNumber, button, 1);
                  }
               }
            }
         } else {
            this.handleMouseClick(var6, var10, button, 6);
         }

         this.doubleClick = false;
         this.lastClickTime = 0L;
      } else {
         if (this.dragSplitting && this.dragSplittingButton != button) {
            this.dragSplitting = false;
            this.dragSplittingSlots.clear();
            this.ignoreMouseUp = true;
            return;
         }

         if (this.ignoreMouseUp) {
            this.ignoreMouseUp = false;
            return;
         }

         if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (button == 0 || button == 1) {
               if (this.draggedStack == null && var6 != this.clickedSlot) {
                  this.draggedStack = this.clickedSlot.getStack();
               }

               boolean var14 = Container.canAddItemToSlot(var6, this.draggedStack, false);
               if (var10 != -1 && this.draggedStack != null && var14) {
                  this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, button, 0);
                  this.handleMouseClick(var6, var10, 0, 0);
                  if (this.mc.thePlayer.inventory.getItemStack() != null) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, button, 0);
                     this.touchUpX = mouseX - var7;
                     this.touchUpY = mouseY - var8;
                     this.returningStackDestSlot = this.clickedSlot;
                     this.returningStack = this.draggedStack;
                     this.returningStackTime = Minecraft.getSystemTime();
                  } else {
                     this.returningStack = null;
                  }
               } else if (this.draggedStack != null) {
                  this.touchUpX = mouseX - var7;
                  this.touchUpY = mouseY - var8;
                  this.returningStackDestSlot = this.clickedSlot;
                  this.returningStack = this.draggedStack;
                  this.returningStackTime = Minecraft.getSystemTime();
               }

               this.draggedStack = null;
               this.clickedSlot = null;
            }
         } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
            this.handleMouseClick(null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);

            for (Slot var12 : this.dragSplittingSlots) {
               this.handleMouseClick(var12, var12.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
            }

            this.handleMouseClick(null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
         } else if (this.mc.thePlayer.inventory.getItemStack() != null) {
            if (button == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
               this.handleMouseClick(var6, var10, button, 3);
            } else {
               boolean var11 = var10 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
               if (var11) {
                  this.shiftClickedSlot = var6 != null && var6.getHasStack() ? var6.getStack() : null;
               }

               this.handleMouseClick(var6, var10, button, var11 ? 1 : 0);
            }
         }
      }

      if (this.mc.thePlayer.inventory.getItemStack() == null) {
         this.lastClickTime = 0L;
      }

      this.dragSplitting = false;
   }

   private boolean isMouseOverSlot(Slot slot, int integer2, int integer3) {
      return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, integer2, integer3);
   }

   protected boolean isPointInRegion(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      int var7 = this.guiLeft;
      int var8 = this.guiTop;
      integer5 -= var7;
      integer6 -= var8;
      return integer5 >= integer1 - 1 && integer5 < integer1 + integer3 + 1 && integer6 >= integer2 - 1 && integer6 < integer2 + integer4 + 1;
   }

   protected void handleMouseClick(Slot slot, int integer2, int integer3, int integer4) {
      if (slot != null) {
         integer2 = slot.slotNumber;
      }

      this.mc.playerController.windowClick(this.inventorySlots.windowId, integer2, integer3, integer4, this.mc.thePlayer);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
         this.mc.thePlayer.closeScreen();
      }

      this.checkHotbarKeys(keyCode);
      if (this.theSlot != null && this.theSlot.getHasStack()) {
         if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
         } else if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
         }
      }
   }

   protected boolean checkHotbarKeys(int integer) {
      if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
         for (int var4 = 0; var4 < 9; var4++) {
            if (integer == this.mc.gameSettings.keyBindsHotbar[var4].getKeyCode()) {
               this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var4, 2);
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void onGuiClosed() {
      if (this.mc.thePlayer != null) {
         this.inventorySlots.onContainerClosed(this.mc.thePlayer);
      }
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
         this.mc.thePlayer.closeScreen();
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l >= 2) {
         switch (guiButton.l) {
            case 2:
               Moon.getInstance().getModuleManager().<Module>getModule(InventoryManager.class).setEnabled(false);
               break;
            case 3:
               Moon.getInstance().getModuleManager().<Module>getModule(ChestStealer.class).setEnabled(false);
               break;
            case 4:
               Moon.getInstance().getModuleManager().<Module>getModule(KillAura.class).setEnabled(false);
         }
      }

      super.actionPerformed(guiButton);
   }
}
