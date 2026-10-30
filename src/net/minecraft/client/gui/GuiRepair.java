package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class GuiRepair extends GuiContainer implements ICrafting {
   private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
   private final ContainerRepair anvil;
   private GuiTextField nameField;
   private final InventoryPlayer playerInventory;

   public GuiRepair(InventoryPlayer inventoryPlayer, World world) {
      super(new ContainerRepair(inventoryPlayer, world, Minecraft.getMinecraft().thePlayer));
      this.playerInventory = inventoryPlayer;
      this.anvil = (ContainerRepair)this.inventorySlots;
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      Keyboard.enableRepeatEvents(true);
      int var3 = (this.width - this.xSize) / 2;
      int var4 = (this.height - this.ySize) / 2;
      this.nameField = new GuiTextField(0, this.fontRendererObj, var3 + 62, var4 + 24, 103, 12);
      this.nameField.setTextColor(-1);
      this.nameField.setDisabledTextColour(-1);
      this.nameField.setEnableBackgroundDrawing(false);
      this.nameField.setMaxStringLength(30);
      this.inventorySlots.removeCraftingFromCrafters(this);
      this.inventorySlots.onCraftGuiOpened(this);
   }

   @Override
   public void onGuiClosed() {
      super.onGuiClosed();
      Keyboard.enableRepeatEvents(false);
      this.inventorySlots.removeCraftingFromCrafters(this);
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      this.fontRendererObj.drawString(I18n.format("container.repair"), 60.0F, 6.0F, 4210752);
      if (this.anvil.maximumCost > 0) {
         int var5 = 8453920;
         boolean var6 = true;
         String var7 = I18n.format("container.repair.cost", this.anvil.maximumCost);
         if (this.anvil.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
            var7 = I18n.format("container.repair.expensive");
            var5 = 16736352;
         } else if (!this.anvil.getSlot(2).getHasStack()) {
            var6 = false;
         } else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
            var5 = 16736352;
         }

         if (var6) {
            int var8 = 0xFF000000 | (var5 & 16579836) >> 2 | var5 & 0xFF000000;
            int var9 = this.xSize - 8 - this.fontRendererObj.getStringWidth(var7);
            byte var10 = 67;
            if (this.fontRendererObj.getUnicodeFlag()) {
               drawRect(var9 - 3, 65, this.xSize - 7, 77, -16777216);
               drawRect(var9 - 2, 66, this.xSize - 8, 76, -12895429);
            } else {
               this.fontRendererObj.drawString(var7, var9, 68.0F, var8);
               this.fontRendererObj.drawString(var7, var9 + 1, 67.0F, var8);
               this.fontRendererObj.drawString(var7, var9 + 1, 68.0F, var8);
            }

            this.fontRendererObj.drawString(var7, var9, 67.0F, var5);
         }
      }

      GlStateManager.enableLighting();
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (this.nameField.textboxKeyTyped(charTyped, keyCode)) {
         this.renameItem();
      } else {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   private void renameItem() {
      String var1 = this.nameField.getText();
      Slot var2 = this.anvil.getSlot(0);
      if (var2 != null && var2.getHasStack() && !var2.getStack().hasDisplayName() && var1.equals(var2.getStack().getDisplayName())) {
         var1 = "";
      }

      this.anvil.updateItemName(var1);
      this.mc.thePlayer.sendQueue.Method754(new C17PacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).writeString(var1)));
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.nameField.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      this.nameField.drawTextBox();
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(anvilResource);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      this.drawTexturedModalRect(var6 + 59, var7 + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
      if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
         this.drawTexturedModalRect(var6 + 99, var7 + 45, this.xSize, 0, 28, 21);
      }
   }

   @Override
   public void updateCraftingInventory(Container container, List<ItemStack> list) {
      this.sendSlotContents(container, 0, container.getSlot(0).getStack());
   }

   @Override
   public void sendSlotContents(Container container, int integer, ItemStack itemStack) {
      if (integer == 0) {
         this.nameField.setText(itemStack == null ? "" : itemStack.getDisplayName());
         this.nameField.setEnabled(itemStack != null);
         if (itemStack != null) {
            this.renameItem();
         }
      }
   }

   @Override
   public void sendProgressBarUpdate(Container container, int integer2, int integer3) {
   }

   @Override
   public void sendAllWindowProperties(Container container, IInventory iInventory) {
   }
}
