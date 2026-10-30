package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer {
   private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
   private final IInventory playerInventory;
   private final IInventory horseInventory;
   private final EntityHorse horseEntity;
   private float mousePosx;
   private float mousePosY;

   public GuiScreenHorseInventory(IInventory iInventory1, IInventory iInventory2, EntityHorse entityHorse) {
      super(new ContainerHorseInventory(iInventory1, iInventory2, entityHorse, Minecraft.getMinecraft().thePlayer));
      this.playerInventory = iInventory1;
      this.horseInventory = iInventory2;
      this.horseEntity = entityHorse;
      this.allowUserInput = false;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8.0F, 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(horseGuiTextures);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      if (this.horseEntity.isChested()) {
         this.drawTexturedModalRect(var6 + 79, var7 + 17, 0, this.ySize, 90, 54);
      }

      if (this.horseEntity.canWearArmor()) {
         this.drawTexturedModalRect(var6 + 7, var7 + 35, 0, this.ySize + 54, 18, 18);
      }

      GuiInventory.drawEntityOnScreen(var6 + 51, var7 + 60, 17, var6 + 51 - this.mousePosx, var7 + 75 - 50 - this.mousePosY, this.horseEntity);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.mousePosx = mouseX;
      this.mousePosY = mouseY;
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
