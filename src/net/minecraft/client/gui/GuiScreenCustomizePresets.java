package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkProviderSettings;
import org.lwjgl.input.Keyboard;

public class GuiScreenCustomizePresets extends GuiScreen {
   private static final List<GuiScreenCustomizePresets.Info> field_175310_f = Lists.newArrayList();
   private GuiScreenCustomizePresets.ListPreset field_175311_g;
   private GuiButton field_175316_h;
   private GuiTextField field_175317_i;
   private final GuiCustomizeWorldScreen field_175314_r;
   protected String field_175315_a = "Customize World Presets";
   private String field_175313_s;
   private String field_175312_t;

   public GuiScreenCustomizePresets(GuiCustomizeWorldScreen guiCustomizeWorldScreen) {
      this.field_175314_r = guiCustomizeWorldScreen;
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      Keyboard.enableRepeatEvents(true);
      this.field_175315_a = I18n.format("createWorld.customize.custom.presets.title");
      this.field_175313_s = I18n.format("createWorld.customize.presets.share");
      this.field_175312_t = I18n.format("createWorld.customize.presets.list");
      this.field_175317_i = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
      this.field_175311_g = new GuiScreenCustomizePresets.ListPreset();
      this.field_175317_i.setMaxStringLength(2000);
      this.field_175317_i.setText(this.field_175314_r.func_175323_a());
      this.m.add(this.field_175316_h = new GuiButton(0, this.width / 2 - 102, this.height - 27, 100, 20, I18n.format("createWorld.customize.presets.select")));
      this.m.add(new GuiButton(1, this.width / 2 + 3, this.height - 27, 100, 20, I18n.format("gui.cancel")));
      this.func_175304_a();
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_175311_g.handleMouseInput();
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      this.field_175317_i.mouseClicked(mouseX, mouseY, button);
      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (!this.field_175317_i.textboxKeyTyped(charTyped, keyCode)) {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      switch (guiButton.l) {
         case 0:
            this.field_175314_r.func_175324_a(this.field_175317_i.getText());
            this.mc.displayGuiScreen(this.field_175314_r);
            break;
         case 1:
            this.mc.displayGuiScreen(this.field_175314_r);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.field_175311_g.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.field_175315_a, this.width / 2, 8, 16777215);
      this.drawString(this.fontRendererObj, this.field_175313_s, 50, 30, 10526880);
      this.drawString(this.fontRendererObj, this.field_175312_t, 50, 70, 10526880);
      this.field_175317_i.drawTextBox();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   public void onUpdateScreen() {
      this.field_175317_i.updateCursorCounter();
      super.onUpdateScreen();
   }

   public void func_175304_a() {
      this.field_175316_h.enabled = this.func_175305_g();
   }

   private boolean func_175305_g() {
      return this.field_175311_g.field_178053_u > -1 && this.field_175311_g.field_178053_u < field_175310_f.size()
         || this.field_175317_i.getText().length() > 1;
   }

   static {
      ChunkProviderSettings.Factory var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }"
      );
      ResourceLocation var14 = new ResourceLocation("textures/gui/presets/water.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.waterWorld"), var14, var13));
      var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }"
      );
      var14 = new ResourceLocation("textures/gui/presets/isles.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.isleLand"), var14, var13));
      var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }"
      );
      var14 = new ResourceLocation("textures/gui/presets/delight.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.caveDelight"), var14, var13));
      var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }"
      );
      var14 = new ResourceLocation("textures/gui/presets/madness.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.mountains"), var14, var13));
      var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }"
      );
      var14 = new ResourceLocation("textures/gui/presets/drought.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.drought"), var14, var13));
      var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }"
      );
      var14 = new ResourceLocation("textures/gui/presets/chaos.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.caveChaos"), var14, var13));
      var13 = ChunkProviderSettings.Factory.jsonToFactory(
         "{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }"
      );
      var14 = new ResourceLocation("textures/gui/presets/luck.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.format("createWorld.customize.custom.preset.goodLuck"), var14, var13));
   }

   static class Info {
      public String field_178955_a;
      public ResourceLocation field_178953_b;
      public ChunkProviderSettings.Factory field_178954_c;

      public Info(String string, ResourceLocation resourceLocation, ChunkProviderSettings.Factory factory) {
         this.field_178955_a = string;
         this.field_178953_b = resourceLocation;
         this.field_178954_c = factory;
      }
   }

   class ListPreset extends GuiSlot {
      public int field_178053_u = -1;

      public ListPreset() {
         super(
            GuiScreenCustomizePresets.this.mc,
            GuiScreenCustomizePresets.this.width,
            GuiScreenCustomizePresets.this.height,
            80,
            GuiScreenCustomizePresets.this.height - 32,
            38
         );
      }

      @Override
      protected int getSize() {
         return GuiScreenCustomizePresets.field_175310_f.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
         this.field_178053_u = index;
         GuiScreenCustomizePresets.this.func_175304_a();
         GuiScreenCustomizePresets.this.field_175317_i
            .setText(GuiScreenCustomizePresets.field_175310_f.get(GuiScreenCustomizePresets.this.field_175311_g.field_178053_u).field_178954_c.toString());
      }

      @Override
      protected boolean isSelected(int index) {
         return index == this.field_178053_u;
      }

      @Override
      protected void drawBackground() {
      }

      private void func_178051_a(int integer1, int integer2, ResourceLocation resourceLocation) {
         int var6 = integer1 + 5;
         GuiScreenCustomizePresets.this.drawHorizontalLine(var6 - 1, var6 + 32, integer2 - 1, -2039584);
         GuiScreenCustomizePresets.this.drawHorizontalLine(var6 - 1, var6 + 32, integer2 + 32, -6250336);
         GuiScreenCustomizePresets.this.drawVerticalLine(var6 - 1, integer2 - 1, integer2 + 32, -2039584);
         GuiScreenCustomizePresets.this.drawVerticalLine(var6 + 32, integer2 - 1, integer2 + 32, -6250336);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(resourceLocation);
         Tessellator var9 = Tessellator.getInstance();
         WorldRenderer var10 = var9.getWorldRenderer();
         var10.begin(7, DefaultVertexFormats.POSITION_TEX);
         var10.pos(var6, integer2 + 32, 0.0).tex(0.0, 1.0).endVertex();
         var10.pos(var6 + 32, integer2 + 32, 0.0).tex(1.0, 1.0).endVertex();
         var10.pos(var6 + 32, integer2, 0.0).tex(1.0, 0.0).endVertex();
         var10.pos(var6, integer2, 0.0).tex(0.0, 0.0).endVertex();
         var9.draw();
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         GuiScreenCustomizePresets.Info var9 = GuiScreenCustomizePresets.field_175310_f.get(index);
         this.func_178051_a(left, top, var9.field_178953_b);
         GuiScreenCustomizePresets.this.fontRendererObj.drawString(var9.field_178955_a, left + 32 + 10, top + 14, 16777215);
      }
   }
}
