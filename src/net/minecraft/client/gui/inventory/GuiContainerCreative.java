package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiContainerCreative extends InventoryEffectRenderer {
   private static final ResourceLocation creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   private static final InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
   private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
   private float currentScroll;
   private boolean isScrolling;
   private boolean wasClicking;
   private GuiTextField searchField;
   private List<Slot> field_147063_B;
   private Slot field_147064_C;
   private boolean field_147057_D;
   private CreativeCrafting field_147059_E;

   public GuiContainerCreative(EntityPlayer entityPlayer) {
      super(new GuiContainerCreative.ContainerCreative(entityPlayer));
      entityPlayer.openContainer = this.inventorySlots;
      this.allowUserInput = true;
      this.ySize = 136;
      this.xSize = 195;
   }

   @Override
   public void onUpdateScreen() {
      if (!this.mc.playerController.isInCreativeMode()) {
         this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
      }

      this.updateActivePotionEffects();
   }

   @Override
   protected void handleMouseClick(Slot slot, int integer2, int integer3, int integer4) {
      this.field_147057_D = true;
      boolean var7 = integer4 == 1;
      integer4 = integer2 == -999 && integer4 == 0 ? 4 : integer4;
      if (slot == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && integer4 != 5) {
         InventoryPlayer var17 = this.mc.thePlayer.inventory;
         if (var17.getItemStack() != null) {
            if (integer3 == 0) {
               this.mc.thePlayer.dropPlayerItemWithRandomChoice(var17.getItemStack(), true);
               this.mc.playerController.sendPacketDropItem(var17.getItemStack());
               var17.setItemStack(null);
            }

            if (integer3 == 1) {
               ItemStack var19 = var17.getItemStack().splitStack(1);
               this.mc.thePlayer.dropPlayerItemWithRandomChoice(var19, true);
               this.mc.playerController.sendPacketDropItem(var19);
               if (var17.getItemStack().stackSize == 0) {
                  var17.setItemStack(null);
               }
            }
         }
      } else if (slot == this.field_147064_C && var7) {
         for (int var16 = 0; var16 < this.mc.thePlayer.inventoryContainer.getInventory().size(); var16++) {
            this.mc.playerController.sendSlotPacket(null, var16);
         }
      } else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
         if (slot == this.field_147064_C) {
            this.mc.thePlayer.inventory.setItemStack(null);
         } else if (integer4 == 4 && slot != null && slot.getHasStack()) {
            ItemStack var8 = slot.decrStackSize(integer3 == 0 ? 1 : slot.getStack().getMaxStackSize());
            this.mc.thePlayer.dropPlayerItemWithRandomChoice(var8, true);
            this.mc.playerController.sendPacketDropItem(var8);
         } else if (integer4 == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
            this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
            this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
            this.mc.thePlayer.inventory.setItemStack(null);
         } else {
            this.mc
               .thePlayer
               .inventoryContainer
               .slotClick(slot == null ? integer2 : ((GuiContainerCreative.CreativeSlot)slot).slot.slotNumber, integer3, integer4, this.mc.thePlayer);
            this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
         }
      } else if (integer4 != 5 && slot.inventory == field_147060_v) {
         InventoryPlayer var15 = this.mc.thePlayer.inventory;
         ItemStack var9 = var15.getItemStack();
         ItemStack var10 = slot.getStack();
         if (integer4 == 2) {
            if (var10 != null && integer3 >= 0 && integer3 < 9) {
               ItemStack var21 = var10.copy();
               var21.stackSize = var21.getMaxStackSize();
               this.mc.thePlayer.inventory.setInventorySlotContents(integer3, var21);
               this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
            }

            return;
         }

         if (integer4 == 3) {
            if (var15.getItemStack() == null && slot.getHasStack()) {
               ItemStack var20 = slot.getStack().copy();
               var20.stackSize = var20.getMaxStackSize();
               var15.setItemStack(var20);
            }

            return;
         }

         if (integer4 == 4) {
            if (var10 != null) {
               ItemStack var11 = var10.copy();
               var11.stackSize = integer3 == 0 ? 1 : var11.getMaxStackSize();
               this.mc.thePlayer.dropPlayerItemWithRandomChoice(var11, true);
               this.mc.playerController.sendPacketDropItem(var11);
            }

            return;
         }

         if (var9 != null && var9.isItemEqual(var10)) {
            if (integer3 == 0) {
               if (var7) {
                  var9.stackSize = var9.getMaxStackSize();
               } else if (var9.stackSize < var9.getMaxStackSize()) {
                  var9.stackSize++;
               }
            } else if (var9.stackSize <= 1) {
               var15.setItemStack(null);
            } else {
               var9.stackSize--;
            }
         } else if (var10 != null && var9 == null) {
            var15.setItemStack(ItemStack.copyItemStack(var10));
            var9 = var15.getItemStack();
            if (var7) {
               var9.stackSize = var9.getMaxStackSize();
            }
         } else {
            var15.setItemStack(null);
         }
      } else {
         this.inventorySlots.slotClick(slot == null ? integer2 : slot.slotNumber, integer3, integer4, this.mc.thePlayer);
         if (Container.getDragEvent(integer3) == 2) {
            for (int var13 = 0; var13 < 9; var13++) {
               this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + var13).getStack(), 36 + var13);
            }
         } else if (slot != null) {
            ItemStack var14 = this.inventorySlots.getSlot(slot.slotNumber).getStack();
            this.mc.playerController.sendSlotPacket(var14, slot.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
         }
      }
   }

   @Override
   protected void updateActivePotionEffects() {
      int var3 = this.guiLeft;
      super.updateActivePotionEffects();
      if (this.searchField != null && this.guiLeft != var3) {
         this.searchField.xPosition = this.guiLeft + 82;
      }
   }

   @Override
   public void onInitGui() {
      if (this.mc.playerController.isInCreativeMode()) {
         super.onInitGui();
         this.m.clear();
         Keyboard.enableRepeatEvents(true);
         this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
         this.searchField.setMaxStringLength(15);
         this.searchField.setEnableBackgroundDrawing(false);
         this.searchField.setVisible(false);
         this.searchField.setTextColor(16777215);
         int var3 = selectedTabIndex;
         selectedTabIndex = -1;
         this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[var3]);
         this.field_147059_E = new CreativeCrafting(this.mc);
         this.mc.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
      } else {
         this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
      }
   }

   @Override
   public void onGuiClosed() {
      super.onGuiClosed();
      if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
         this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
      }

      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
         if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
            this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
         } else {
            super.onKeyTyped(charTyped, keyCode);
         }
      } else {
         if (this.field_147057_D) {
            this.field_147057_D = false;
            this.searchField.setText("");
         }

         if (!this.checkHotbarKeys(keyCode)) {
            if (this.searchField.textboxKeyTyped(charTyped, keyCode)) {
               this.updateCreativeSearch();
            } else {
               super.onKeyTyped(charTyped, keyCode);
            }
         }
      }
   }

   private void updateCreativeSearch() {
      GuiContainerCreative.ContainerCreative var1 = (GuiContainerCreative.ContainerCreative)this.inventorySlots;
      var1.itemList.clear();

      for (Item var3 : Item.itemRegistry) {
         if (var3 != null && var3.getCreativeTab() != null) {
            var3.getSubItems(var3, null, var1.itemList);
         }
      }

      for (Enchantment var5 : Enchantment.enchantmentsBookList) {
         if (var5 != null && var5.type != null) {
            Items.enchanted_book.getAll(var5, var1.itemList);
         }
      }

      Iterator var8 = var1.itemList.iterator();
      String var10 = this.searchField.getText().toLowerCase();

      while (var8.hasNext()) {
         ItemStack var11 = (ItemStack)var8.next();
         boolean var12 = false;
         Iterator var6 = var11.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();

         while (var6.hasNext()) {
            if (EnumChatFormatting.getTextWithoutFormattingCodes((String)var6.next()).toLowerCase().contains(var10)) {
               var12 = true;
               break;
            }
         }

         if (!var12) {
            var8.remove();
         }
      }

      this.currentScroll = 0.0F;
      var1.scrollTo(0.0F);
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      CreativeTabs var5 = CreativeTabs.creativeTabArray[selectedTabIndex];
      if (var5.drawInForegroundOfTab()) {
         GlStateManager.disableBlend();
         this.fontRendererObj.drawString(I18n.format(var5.Method576()), 8.0F, 6.0F, 4210752);
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (button == 0) {
         int var4 = mouseX - this.guiLeft;
         int var5 = mouseY - this.guiTop;

         for (CreativeTabs var9 : CreativeTabs.creativeTabArray) {
            if (this.func_147049_a(var9, var4, var5)) {
               return;
            }
         }
      }

      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      if (button == 0) {
         int var4 = mouseX - this.guiLeft;
         int var5 = mouseY - this.guiTop;

         for (CreativeTabs var9 : CreativeTabs.creativeTabArray) {
            if (this.func_147049_a(var9, var4, var5)) {
               this.setCurrentCreativeTab(var9);
               return;
            }
         }
      }

      super.onMouseReleased(mouseX, mouseY, button);
   }

   private boolean needsScrollBars() {
      return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex()
         && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory()
         && ((GuiContainerCreative.ContainerCreative)this.inventorySlots).func_148328_e();
   }

   private void setCurrentCreativeTab(CreativeTabs creativeTabs) {
      int var4 = selectedTabIndex;
      selectedTabIndex = creativeTabs.getTabIndex();
      GuiContainerCreative.ContainerCreative var5 = (GuiContainerCreative.ContainerCreative)this.inventorySlots;
      this.dragSplittingSlots.clear();
      var5.itemList.clear();
      creativeTabs.displayAllReleventItems(var5.itemList);
      if (creativeTabs == CreativeTabs.tabInventory) {
         Container var6 = this.mc.thePlayer.inventoryContainer;
         if (this.field_147063_B == null) {
            this.field_147063_B = var5.inventorySlots;
         }

         var5.inventorySlots = Lists.newArrayList();

         for (int var7 = 0; var7 < var6.inventorySlots.size(); var7++) {
            GuiContainerCreative.CreativeSlot var8 = new GuiContainerCreative.CreativeSlot(var6.inventorySlots.get(var7), var7);
            var5.inventorySlots.add(var8);
            if (var7 >= 5 && var7 < 9) {
               int var12 = var7 - 5;
               int var13 = var12 / 2;
               int var14 = var12 % 2;
               var8.xDisplayPosition = 9 + var13 * 54;
               var8.yDisplayPosition = 6 + var14 * 27;
            } else if (var7 >= 0 && var7 < 5) {
               var8.yDisplayPosition = -2000;
               var8.xDisplayPosition = -2000;
            } else if (var7 < var6.inventorySlots.size()) {
               int var9 = var7 - 9;
               int var10 = var9 % 9;
               int var11 = var9 / 9;
               var8.xDisplayPosition = 9 + var10 * 18;
               if (var7 >= 36) {
                  var8.yDisplayPosition = 112;
               } else {
                  var8.yDisplayPosition = 54 + var11 * 18;
               }
            }
         }

         this.field_147064_C = new Slot(field_147060_v, 0, 173, 112);
         var5.inventorySlots.add(this.field_147064_C);
      } else if (var4 == CreativeTabs.tabInventory.getTabIndex()) {
         var5.inventorySlots = this.field_147063_B;
         this.field_147063_B = null;
      }

      if (this.searchField != null) {
         if (creativeTabs == CreativeTabs.tabAllSearch) {
            this.searchField.setVisible(true);
            this.searchField.setCanLoseFocus(false);
            this.searchField.setFocused(true);
            this.searchField.setText("");
            this.updateCreativeSearch();
         } else {
            this.searchField.setVisible(false);
            this.searchField.setCanLoseFocus(true);
            this.searchField.setFocused(false);
         }
      }

      this.currentScroll = 0.0F;
      var5.scrollTo(0.0F);
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var3 = Mouse.getEventDWheel();
      if (var3 != 0 && this.needsScrollBars()) {
         int var4 = ((GuiContainerCreative.ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5;
         if (var3 > 0) {
            var3 = 1;
         }

         if (var3 < 0) {
            var3 = -1;
         }

         this.currentScroll = (float)(this.currentScroll - (double)var3 / var4);
         this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      boolean var6 = Mouse.isButtonDown(0);
      int var7 = this.guiLeft;
      int var8 = this.guiTop;
      int var9 = var7 + 175;
      int var10 = var8 + 18;
      int var11 = var9 + 14;
      int var12 = var10 + 112;
      if (!this.wasClicking && var6 && mouseX >= var9 && mouseY >= var10 && mouseX < var11 && mouseY < var12) {
         this.isScrolling = this.needsScrollBars();
      }

      if (!var6) {
         this.isScrolling = false;
      }

      this.wasClicking = var6;
      if (this.isScrolling) {
         this.currentScroll = (mouseY - var10 - 7.5F) / (var12 - var10 - 15.0F);
         this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);

      for (CreativeTabs var16 : CreativeTabs.creativeTabArray) {
         if (this.renderCreativeInventoryHoveringText(var16, mouseX, mouseY)) {
            break;
         }
      }

      if (this.field_147064_C != null
         && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()
         && this.isPointInRegion(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, mouseX, mouseY)) {
         this.drawCreativeTabHoveringText(I18n.format("inventory.binSlot"), mouseX, mouseY);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableLighting();
   }

   @Override
   protected void renderToolTip(ItemStack itemStack, int integer2, int integer3) {
      if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
         List var4 = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
         CreativeTabs var5 = itemStack.getItem().getCreativeTab();
         if (var5 == null && itemStack.getItem() == Items.enchanted_book) {
            Map var6 = EnchantmentHelper.getEnchantments(itemStack);
            if (var6.size() == 1) {
               Enchantment var7 = Enchantment.getEnchantmentById((Integer)var6.keySet().iterator().next());

               for (CreativeTabs var11 : CreativeTabs.creativeTabArray) {
                  if (var11.hasRelevantEnchantmentType(var7.type)) {
                     var5 = var11;
                     break;
                  }
               }
            }
         }

         if (var5 != null) {
            var4.add(1, "" + EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.format(var5.Method576()));
         }

         for (int var12 = 0; var12 < var4.size(); var12++) {
            if (var12 == 0) {
               var4.set(var12, itemStack.getRarity().rarityColor + (String)var4.get(var12));
            } else {
               var4.set(var12, EnumChatFormatting.GRAY + (String)var4.get(var12));
            }
         }

         this.drawHoveringText(var4, integer2, integer3);
      } else {
         super.renderToolTip(itemStack, integer2, integer3);
      }
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.enableGUIStandardItemLighting();
      CreativeTabs var6 = CreativeTabs.creativeTabArray[selectedTabIndex];

      for (CreativeTabs var10 : CreativeTabs.creativeTabArray) {
         this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
         if (var10.getTabIndex() != selectedTabIndex) {
            this.func_147051_a(var10);
         }
      }

      this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var6.getBackgroundImageName()));
      this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      this.searchField.drawTextBox();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int var11 = this.guiLeft + 175;
      int var12 = this.guiTop + 18;
      int var13 = var12 + 112;
      this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
      if (var6.shouldHidePlayerInventory()) {
         this.drawTexturedModalRect(var11, var12 + (int)((var13 - var12 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
      }

      this.func_147051_a(var6);
      if (var6 == CreativeTabs.tabInventory) {
         GuiInventory.drawEntityOnScreen(
            this.guiLeft + 43, this.guiTop + 45, 20, this.guiLeft + 43 - integer2, this.guiTop + 45 - 30 - integer3, this.mc.thePlayer
         );
      }
   }

   protected boolean func_147049_a(CreativeTabs creativeTabs, int integer2, int integer3) {
      int var6 = creativeTabs.getTabColumn();
      int var7 = 28 * var6;
      if (var6 == 5) {
         var7 = this.xSize - 28 + 2;
      } else if (var6 > 0) {
         var7 += var6;
      }

      int var8;
      if (creativeTabs.isTabInFirstRow()) {
         var8 = -32;
      } else {
         var8 = 0 + this.ySize;
      }

      return integer2 >= var7 && integer2 <= var7 + 28 && integer3 >= var8 && integer3 <= var8 + 32;
   }

   protected boolean renderCreativeInventoryHoveringText(CreativeTabs creativeTabs, int integer2, int integer3) {
      int var6 = creativeTabs.getTabColumn();
      int var7 = 28 * var6;
      if (var6 == 5) {
         var7 = this.xSize - 28 + 2;
      } else if (var6 > 0) {
         var7 += var6;
      }

      int var8;
      if (creativeTabs.isTabInFirstRow()) {
         var8 = -32;
      } else {
         var8 = 0 + this.ySize;
      }

      if (this.isPointInRegion(var7 + 3, var8 + 3, 23, 27, integer2, integer3)) {
         this.drawCreativeTabHoveringText(I18n.format(creativeTabs.Method576()), integer2, integer3);
         return true;
      } else {
         return false;
      }
   }

   protected void func_147051_a(CreativeTabs creativeTabs) {
      boolean var4 = creativeTabs.getTabIndex() == selectedTabIndex;
      boolean var5 = creativeTabs.isTabInFirstRow();
      int var6 = creativeTabs.getTabColumn();
      int var7 = var6 * 28;
      byte var8 = 0;
      int var9 = this.guiLeft + 28 * var6;
      int var10 = this.guiTop;
      byte var11 = 32;
      if (var4) {
         var8 += 32;
      }

      if (var6 == 5) {
         var9 = this.guiLeft + this.xSize - 28;
      } else if (var6 > 0) {
         var9 += var6;
      }

      if (var5) {
         var10 -= 28;
      } else {
         var8 += 64;
         var10 += this.ySize - 4;
      }

      GlStateManager.disableLighting();
      this.drawTexturedModalRect(var9, var10, var7, var8, 28, var11);
      this.zLevel = 100.0F;
      this.itemRender.zLevel = 100.0F;
      var9 += 6;
      var10 = var10 + 8 + (var5 ? 1 : -1);
      GlStateManager.enableLighting();
      GlStateManager.enableRescaleNormal();
      ItemStack var12 = creativeTabs.getIconItemStack();
      this.itemRender.renderItemAndEffectIntoGUI(var12, var9, var10);
      this.itemRender.renderItemOverlays(this.fontRendererObj, var12, var9, var10);
      GlStateManager.disableLighting();
      this.itemRender.zLevel = 0.0F;
      this.zLevel = 0.0F;
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 0) {
         this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
      }

      if (guiButton.l == 1) {
         this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
      }
   }

   public int getSelectedTabIndex() {
      return selectedTabIndex;
   }

   static class ContainerCreative extends Container {
      public List<ItemStack> itemList = Lists.newArrayList();

      public ContainerCreative(EntityPlayer entityPlayer) {
         InventoryPlayer var4 = entityPlayer.inventory;

         for (int var5 = 0; var5 < 5; var5++) {
            for (int var6 = 0; var6 < 9; var6++) {
               this.addSlotToContainer(new Slot(GuiContainerCreative.field_147060_v, var5 * 9 + var6, 9 + var6 * 18, 18 + var5 * 18));
            }
         }

         for (int var7 = 0; var7 < 9; var7++) {
            this.addSlotToContainer(new Slot(var4, var7, 9 + var7 * 18, 112));
         }

         this.scrollTo(0.0F);
      }

      @Override
      public boolean canInteractWith(EntityPlayer entityPlayer) {
         return true;
      }

      public void scrollTo(float float1) {
         int var4 = (this.itemList.size() + 9 - 1) / 9 - 5;
         int var5 = (int)(float1 * var4 + 0.5);
         if (var5 < 0) {
            var5 = 0;
         }

         for (int var6 = 0; var6 < 5; var6++) {
            for (int var7 = 0; var7 < 9; var7++) {
               int var8 = var7 + (var6 + var5) * 9;
               if (var8 >= 0 && var8 < this.itemList.size()) {
                  GuiContainerCreative.field_147060_v.setInventorySlotContents(var7 + var6 * 9, this.itemList.get(var8));
               } else {
                  GuiContainerCreative.field_147060_v.setInventorySlotContents(var7 + var6 * 9, null);
               }
            }
         }
      }

      public boolean func_148328_e() {
         return this.itemList.size() > 45;
      }

      @Override
      protected void retrySlotClick(int integer1, int integer2, boolean boolean3, EntityPlayer entityPlayer) {
      }

      @Override
      public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
         if (integer >= this.inventorySlots.size() - 9 && integer < this.inventorySlots.size()) {
            Slot var5 = this.inventorySlots.get(integer);
            if (var5 != null && var5.getHasStack()) {
               var5.putStack(null);
            }
         }

         return null;
      }

      @Override
      public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
         return slot.yDisplayPosition > 90;
      }

      @Override
      public boolean canDragIntoSlot(Slot slot) {
         return slot.inventory instanceof InventoryPlayer || slot.yDisplayPosition > 90 && slot.xDisplayPosition <= 162;
      }
   }

   static class CreativeSlot extends Slot {
      private final Slot slot;

      public CreativeSlot(Slot slot, int integer) {
         super(slot.inventory, integer, 0, 0);
         this.slot = slot;
      }

      @Override
      public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
         this.slot.onPickupFromSlot(entityPlayer, itemStack);
      }

      @Override
      public boolean isItemValid(ItemStack itemStack) {
         return this.slot.isItemValid(itemStack);
      }

      @Override
      public ItemStack getStack() {
         return this.slot.getStack();
      }

      @Override
      public boolean getHasStack() {
         return this.slot.getHasStack();
      }

      @Override
      public void putStack(ItemStack itemStack) {
         this.slot.putStack(itemStack);
      }

      @Override
      public void onSlotChanged() {
         this.slot.onSlotChanged();
      }

      @Override
      public int getSlotStackLimit() {
         return this.slot.getSlotStackLimit();
      }

      @Override
      public int getItemStackLimit(ItemStack itemStack) {
         return this.slot.getItemStackLimit(itemStack);
      }

      @Override
      public String getSlotTexture() {
         return this.slot.getSlotTexture();
      }

      @Override
      public ItemStack decrStackSize(int integer) {
         return this.slot.decrStackSize(integer);
      }

      @Override
      public boolean isHere(IInventory iInventory, int integer) {
         return this.slot.isHere(iInventory, integer);
      }
   }
}
