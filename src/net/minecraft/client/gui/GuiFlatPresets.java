package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockTallGrass;
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
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

public class GuiFlatPresets extends GuiScreen {
   private static final List<GuiFlatPresets.LayerItem> FLAT_WORLD_PRESETS = Lists.newArrayList();
   private final GuiCreateFlatWorld parentScreen;
   private String presetsTitle;
   private String presetsShare;
   private String field_146436_r;
   private GuiFlatPresets.ListSlot field_146435_s;
   private GuiButton field_146434_t;
   private GuiTextField field_146433_u;

   public GuiFlatPresets(GuiCreateFlatWorld guiCreateFlatWorld) {
      this.parentScreen = guiCreateFlatWorld;
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      Keyboard.enableRepeatEvents(true);
      this.presetsTitle = I18n.format("createWorld.customize.presets.title");
      this.presetsShare = I18n.format("createWorld.customize.presets.share");
      this.field_146436_r = I18n.format("createWorld.customize.presets.list");
      this.field_146433_u = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
      this.field_146435_s = new GuiFlatPresets.ListSlot();
      this.field_146433_u.setMaxStringLength(1230);
      this.field_146433_u.setText(this.parentScreen.func_146384_e());
      this.m.add(this.field_146434_t = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select")));
      this.m.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
      this.func_146426_g();
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146435_s.handleMouseInput();
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      this.field_146433_u.mouseClicked(mouseX, mouseY, button);
      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (!this.field_146433_u.textboxKeyTyped(charTyped, keyCode)) {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 0 && this.func_146430_p()) {
         this.parentScreen.func_146383_a(this.field_146433_u.getText());
         this.mc.displayGuiScreen(this.parentScreen);
      } else if (guiButton.l == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.field_146435_s.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.presetsTitle, this.width / 2, 8, 16777215);
      this.drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
      this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
      this.field_146433_u.drawTextBox();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   public void onUpdateScreen() {
      this.field_146433_u.updateCursorCounter();
      super.onUpdateScreen();
   }

   public void func_146426_g() {
      this.field_146434_t.enabled = this.func_146430_p();
   }

   private boolean func_146430_p() {
      return this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < FLAT_WORLD_PRESETS.size()
         || this.field_146433_u.getText().length() > 1;
   }

   private static void func_146425_a(String string, Item item, BiomeGenBase biomeGenBase, FlatLayerInfo... arr) {
      func_175354_a(string, item, 0, biomeGenBase, null, arr);
   }

   private static void func_146421_a(String string, Item item, BiomeGenBase biomeGenBase, List<String> list, FlatLayerInfo... arr) {
      func_175354_a(string, item, 0, biomeGenBase, list, arr);
   }

   private static void func_175354_a(String string, Item item, int integer, BiomeGenBase biomeGenBase, List<String> list, FlatLayerInfo... arr) {
      FlatGeneratorInfo var6 = new FlatGeneratorInfo();

      for (int var7 = arr.length - 1; var7 >= 0; var7--) {
         var6.getFlatLayers().add(arr[var7]);
      }

      var6.setBiome(biomeGenBase.biomeID);
      var6.func_82645_d();
      if (list != null) {
         for (String var8 : list) {
            var6.getWorldFeatures().put(var8, Maps.newHashMap());
         }
      }

      FLAT_WORLD_PRESETS.add(new GuiFlatPresets.LayerItem(item, integer, string, var6.toString()));
   }

   static {
      func_146421_a(
         "Classic Flat",
         Item.getItemFromBlock(Blocks.grass),
         BiomeGenBase.plains,
         List.of("village"),
         new FlatLayerInfo(1, Blocks.grass),
         new FlatLayerInfo(2, Blocks.dirt),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
      func_146421_a(
         "Tunnelers' Dream",
         Item.getItemFromBlock(Blocks.stone),
         BiomeGenBase.extremeHills,
         Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"),
         new FlatLayerInfo(1, Blocks.grass),
         new FlatLayerInfo(5, Blocks.dirt),
         new FlatLayerInfo(230, Blocks.stone),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
      func_146421_a(
         "Water World",
         Items.water_bucket,
         BiomeGenBase.deepOcean,
         Arrays.asList("biome_1", "oceanmonument"),
         new FlatLayerInfo(90, Blocks.water),
         new FlatLayerInfo(5, Blocks.sand),
         new FlatLayerInfo(5, Blocks.dirt),
         new FlatLayerInfo(5, Blocks.stone),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
      func_175354_a(
         "Overworld",
         Item.getItemFromBlock(Blocks.tallgrass),
         BlockTallGrass.EnumType.GRASS.getMeta(),
         BiomeGenBase.plains,
         Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"),
         new FlatLayerInfo(1, Blocks.grass),
         new FlatLayerInfo(3, Blocks.dirt),
         new FlatLayerInfo(59, Blocks.stone),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
      func_146421_a(
         "Snowy Kingdom",
         Item.getItemFromBlock(Blocks.snow_layer),
         BiomeGenBase.icePlains,
         Arrays.asList("village", "biome_1"),
         new FlatLayerInfo(1, Blocks.snow_layer),
         new FlatLayerInfo(1, Blocks.grass),
         new FlatLayerInfo(3, Blocks.dirt),
         new FlatLayerInfo(59, Blocks.stone),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
      func_146421_a(
         "Bottomless Pit",
         Items.feather,
         BiomeGenBase.plains,
         Arrays.asList("village", "biome_1"),
         new FlatLayerInfo(1, Blocks.grass),
         new FlatLayerInfo(3, Blocks.dirt),
         new FlatLayerInfo(2, Blocks.cobblestone)
      );
      func_146421_a(
         "Desert",
         Item.getItemFromBlock(Blocks.sand),
         BiomeGenBase.desert,
         Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"),
         new FlatLayerInfo(8, Blocks.sand),
         new FlatLayerInfo(52, Blocks.sandstone),
         new FlatLayerInfo(3, Blocks.stone),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
      func_146425_a(
         "Redstone Ready",
         Items.redstone,
         BiomeGenBase.desert,
         new FlatLayerInfo(52, Blocks.sandstone),
         new FlatLayerInfo(3, Blocks.stone),
         new FlatLayerInfo(1, Blocks.bedrock)
      );
   }

   static class LayerItem {
      public Item field_148234_a;
      public int field_179037_b;
      public String field_148232_b;
      public String field_148233_c;

      public LayerItem(Item item, int integer, String string3, String string4) {
         this.field_148234_a = item;
         this.field_179037_b = integer;
         this.field_148232_b = string3;
         this.field_148233_c = string4;
      }
   }

   class ListSlot extends GuiSlot {
      public int field_148175_k = -1;

      public ListSlot() {
         super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80, GuiFlatPresets.this.height - 37, 24);
      }

      private void func_178054_a(int integer1, int integer2, Item item, int integer4) {
         this.func_148173_e(integer1 + 1, integer2 + 1);
         GlStateManager.enableRescaleNormal();
         RenderHelper.enableGUIStandardItemLighting();
         GuiFlatPresets.this.itemRender.Method679(new ItemStack(item, 1, integer4), integer1 + 2, integer2 + 2);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
      }

      private void func_148173_e(int integer1, int integer2) {
         this.func_148171_c(integer1, integer2, 0, 0);
      }

      private void func_148171_c(int integer1, int integer2, int integer3, int integer4) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(Gui.statIcons);
         Tessellator var11 = Tessellator.getInstance();
         WorldRenderer var12 = var11.getWorldRenderer();
         var12.begin(7, DefaultVertexFormats.POSITION_TEX);
         var12.pos(integer1, integer2 + 18, GuiFlatPresets.this.zLevel).tex(integer3 * 0.0078125F, (integer4 + 18) * 0.0078125F).endVertex();
         var12.pos(integer1 + 18, integer2 + 18, GuiFlatPresets.this.zLevel).tex((integer3 + 18) * 0.0078125F, (integer4 + 18) * 0.0078125F).endVertex();
         var12.pos(integer1 + 18, integer2, GuiFlatPresets.this.zLevel).tex((integer3 + 18) * 0.0078125F, integer4 * 0.0078125F).endVertex();
         var12.pos(integer1, integer2, GuiFlatPresets.this.zLevel).tex(integer3 * 0.0078125F, integer4 * 0.0078125F).endVertex();
         var11.draw();
      }

      @Override
      protected int getSize() {
         return GuiFlatPresets.FLAT_WORLD_PRESETS.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
         this.field_148175_k = index;
         GuiFlatPresets.this.func_146426_g();
         GuiFlatPresets.this.field_146433_u.setText(GuiFlatPresets.FLAT_WORLD_PRESETS.get(GuiFlatPresets.this.field_146435_s.field_148175_k).field_148233_c);
      }

      @Override
      protected boolean isSelected(int index) {
         return index == this.field_148175_k;
      }

      @Override
      protected void drawBackground() {
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         GuiFlatPresets.LayerItem var9 = GuiFlatPresets.FLAT_WORLD_PRESETS.get(index);
         this.func_178054_a(left, top, var9.field_148234_a, var9.field_179037_b);
         GuiFlatPresets.this.fontRendererObj.drawString(var9.field_148232_b, left + 18 + 5, top + 6, 16777215);
      }
   }
}
