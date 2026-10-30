package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

public class GuiCreateFlatWorld extends GuiScreen {
   private final GuiCreateWorld createWorldGui;
   private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
   private String flatWorldTitle;
   private String field_146394_i;
   private String field_146391_r;
   private GuiCreateFlatWorld.Details createFlatWorldListSlotGui;
   private GuiButton field_146389_t;
   private GuiButton field_146388_u;
   private GuiButton field_146386_v;

   public GuiCreateFlatWorld(GuiCreateWorld guiCreateWorld, String string) {
      this.createWorldGui = guiCreateWorld;
      this.func_146383_a(string);
   }

   public String func_146384_e() {
      return this.theFlatGeneratorInfo.toString();
   }

   public void func_146383_a(String string) {
      this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(string);
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      this.flatWorldTitle = I18n.format("createWorld.customize.flat.title");
      this.field_146394_i = I18n.format("createWorld.customize.flat.tile");
      this.field_146391_r = I18n.format("createWorld.customize.flat.height");
      this.createFlatWorldListSlotGui = new GuiCreateFlatWorld.Details();
      this.m
         .add(
            this.field_146389_t = new GuiButton(
               2, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("createWorld.customize.flat.addLayer") + " (NYI)"
            )
         );
      this.m
         .add(
            this.field_146388_u = new GuiButton(
               3, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("createWorld.customize.flat.editLayer") + " (NYI)"
            )
         );
      this.m
         .add(this.field_146386_v = new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer")));
      this.m.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("gui.done")));
      this.m.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.format("createWorld.customize.presets")));
      this.m.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
      this.field_146389_t.visible = this.field_146388_u.visible = false;
      this.theFlatGeneratorInfo.func_82645_d();
      this.func_146375_g();
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.createFlatWorldListSlotGui.handleMouseInput();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      int var2 = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_148228_k - 1;
      if (guiButton.l == 1) {
         this.mc.displayGuiScreen(this.createWorldGui);
      } else if (guiButton.l == 0) {
         this.createWorldGui.chunkProviderSettingsJson = this.func_146384_e();
         this.mc.displayGuiScreen(this.createWorldGui);
      } else if (guiButton.l == 5) {
         this.mc.displayGuiScreen(new GuiFlatPresets(this));
      } else if (guiButton.l == 4 && this.func_146382_i()) {
         this.theFlatGeneratorInfo.getFlatLayers().remove(var2);
         this.createFlatWorldListSlotGui.field_148228_k = Math.min(
            this.createFlatWorldListSlotGui.field_148228_k, this.theFlatGeneratorInfo.getFlatLayers().size() - 1
         );
      }

      this.theFlatGeneratorInfo.func_82645_d();
      this.func_146375_g();
   }

   public void func_146375_g() {
      boolean var1 = this.func_146382_i();
      this.field_146386_v.enabled = var1;
      this.field_146388_u.enabled = var1;
      this.field_146388_u.enabled = false;
      this.field_146389_t.enabled = false;
   }

   private boolean func_146382_i() {
      return this.createFlatWorldListSlotGui.field_148228_k > -1
         && this.createFlatWorldListSlotGui.field_148228_k < this.theFlatGeneratorInfo.getFlatLayers().size();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.createFlatWorldListSlotGui.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.flatWorldTitle, this.width / 2, 8, 16777215);
      int var6 = this.width / 2 - 92 - 16;
      this.drawString(this.fontRendererObj, this.field_146394_i, var6, 32, 16777215);
      this.drawString(this.fontRendererObj, this.field_146391_r, var6 + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   class Details extends GuiSlot {
      public int field_148228_k = -1;

      public Details() {
         super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43, GuiCreateFlatWorld.this.height - 60, 24);
      }

      private void func_148225_a(int integer1, int integer2, ItemStack itemStack) {
         this.func_148226_e(integer1 + 1, integer2 + 1);
         GlStateManager.enableRescaleNormal();
         if (itemStack != null && itemStack.getItem() != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GuiCreateFlatWorld.this.itemRender.Method679(itemStack, integer1 + 2, integer2 + 2);
            RenderHelper.disableStandardItemLighting();
         }

         GlStateManager.disableRescaleNormal();
      }

      private void func_148226_e(int integer1, int integer2) {
         this.func_148224_c(integer1, integer2, 0, 0);
      }

      private void func_148224_c(int integer1, int integer2, int integer3, int integer4) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(Gui.statIcons);
         Tessellator var11 = Tessellator.getInstance();
         WorldRenderer var12 = var11.getWorldRenderer();
         var12.begin(7, DefaultVertexFormats.POSITION_TEX);
         var12.pos(integer1, integer2 + 18, GuiCreateFlatWorld.this.zLevel).tex(integer3 * 0.0078125F, (integer4 + 18) * 0.0078125F).endVertex();
         var12.pos(integer1 + 18, integer2 + 18, GuiCreateFlatWorld.this.zLevel).tex((integer3 + 18) * 0.0078125F, (integer4 + 18) * 0.0078125F).endVertex();
         var12.pos(integer1 + 18, integer2, GuiCreateFlatWorld.this.zLevel).tex((integer3 + 18) * 0.0078125F, integer4 * 0.0078125F).endVertex();
         var12.pos(integer1, integer2, GuiCreateFlatWorld.this.zLevel).tex(integer3 * 0.0078125F, integer4 * 0.0078125F).endVertex();
         var11.draw();
      }

      @Override
      protected int getSize() {
         return GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
         this.field_148228_k = index;
         GuiCreateFlatWorld.this.func_146375_g();
      }

      @Override
      protected boolean isSelected(int index) {
         return index == this.field_148228_k;
      }

      @Override
      protected void drawBackground() {
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         FlatLayerInfo var9 = GuiCreateFlatWorld.this.theFlatGeneratorInfo
            .getFlatLayers()
            .get(GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - index - 1);
         IBlockState var10 = var9.getLayerMaterial();
         Block var11 = var10.getBlock();
         Item var12 = Item.getItemFromBlock(var11);
         ItemStack var13 = var11 != Blocks.air && var12 != null ? new ItemStack(var12, 1, var11.getMetaFromState(var10)) : null;
         String var14 = var13 == null ? "Air" : var12.getItemStackDisplayName(var13);
         if (var12 == null) {
            if (var11 == Blocks.water || var11 == Blocks.flowing_water) {
               var12 = Items.water_bucket;
            } else if (var11 == Blocks.lava || var11 == Blocks.flowing_lava) {
               var12 = Items.lava_bucket;
            }

            if (var12 != null) {
               var13 = new ItemStack(var12, 1, var11.getMetaFromState(var10));
               var14 = var11.getLocalizedName();
            }
         }

         this.func_148225_a(left, top, var13);
         GuiCreateFlatWorld.this.fontRendererObj.drawString(var14, left + 18 + 5, top + 3, 16777215);
         String var15;
         if (index == 0) {
            var15 = I18n.format("createWorld.customize.flat.layer.top", var9.getLayerCount());
         } else if (index == GuiCreateFlatWorld.this.theFlatGeneratorInfo.getFlatLayers().size() - 1) {
            var15 = I18n.format("createWorld.customize.flat.layer.bottom", var9.getLayerCount());
         } else {
            var15 = I18n.format("createWorld.customize.flat.layer", var9.getLayerCount());
         }

         GuiCreateFlatWorld.this.fontRendererObj
            .drawString(var15, left + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(var15), top + 3, 16777215);
      }

      @Override
      protected int getScrollBarX() {
         return this.width - 70;
      }
   }
}
