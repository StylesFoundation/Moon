package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.primitives.Floats;
import java.io.IOException;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {
   private final GuiCreateWorld field_175343_i;
   protected String field_175341_a = "Customize World Settings";
   protected String field_175333_f = "Page 1 of 3";
   protected String field_175335_g = "Basic Settings";
   protected String[] field_175342_h = new String[4];
   private GuiPageButtonList field_175349_r;
   private GuiButton field_175348_s;
   private GuiButton field_175347_t;
   private GuiButton field_175346_u;
   private GuiButton field_175345_v;
   private GuiButton field_175344_w;
   private GuiButton field_175352_x;
   private GuiButton field_175351_y;
   private GuiButton field_175350_z;
   private boolean field_175338_A = false;
   private int field_175339_B = 0;
   private boolean field_175340_C = false;
   private final Predicate<String> N = string -> {
      Float var1 = Floats.tryParse(string);
      return string.length() == 0 || var1 != null && Floats.isFinite(var1) && var1 >= 0.0F;
   };
   private final ChunkProviderSettings.Factory field_175334_E = new ChunkProviderSettings.Factory();
   private ChunkProviderSettings.Factory field_175336_F;
   private final Random random = new Random();

   public GuiCustomizeWorldScreen(GuiScreen guiScreen, String string) {
      this.field_175343_i = (GuiCreateWorld)guiScreen;
      this.func_175324_a(string);
   }

   @Override
   public void onInitGui() {
      int var3 = 0;
      int var4 = 0;
      if (this.field_175349_r != null) {
         var3 = this.field_175349_r.func_178059_e();
         var4 = this.field_175349_r.getAmountScrolled();
      }

      this.field_175341_a = I18n.format("options.customizeTitle");
      this.m.clear();
      this.m.add(this.field_175345_v = new GuiButton(302, 20, 5, 80, 20, I18n.format("createWorld.customize.custom.prev")));
      this.m.add(this.field_175344_w = new GuiButton(303, this.width - 100, 5, 80, 20, I18n.format("createWorld.customize.custom.next")));
      this.m
         .add(this.field_175346_u = new GuiButton(304, this.width / 2 - 187, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.defaults")));
      this.m
         .add(this.field_175347_t = new GuiButton(301, this.width / 2 - 92, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.randomize")));
      this.m.add(this.field_175350_z = new GuiButton(305, this.width / 2 + 3, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.presets")));
      this.m.add(this.field_175348_s = new GuiButton(300, this.width / 2 + 98, this.height - 27, 90, 20, I18n.format("gui.done")));
      this.field_175346_u.enabled = this.field_175338_A;
      this.field_175352_x = new GuiButton(306, this.width / 2 - 55, 160, 50, 20, I18n.format("gui.yes"));
      this.field_175352_x.visible = false;
      this.m.add(this.field_175352_x);
      this.field_175351_y = new GuiButton(307, this.width / 2 + 5, 160, 50, 20, I18n.format("gui.no"));
      this.field_175351_y.visible = false;
      this.m.add(this.field_175351_y);
      if (this.field_175339_B != 0) {
         this.field_175352_x.visible = true;
         this.field_175351_y.visible = true;
      }

      this.func_175325_f();
      if (var3 != 0) {
         this.field_175349_r.func_181156_c(var3);
         this.field_175349_r.scrollBy(var4);
         this.func_175328_i();
      }
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_175349_r.handleMouseInput();
   }

   private void func_175325_f() {
      GuiPageButtonList.GuiListEntry[] var3 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiSlideEntry(160, I18n.format("createWorld.customize.custom.seaLevel"), true, this, 1.0F, 255.0F, this.field_175336_F.seaLevel),
         new GuiPageButtonList.GuiButtonEntry(148, I18n.format("createWorld.customize.custom.useCaves"), true, this.field_175336_F.useCaves),
         new GuiPageButtonList.GuiButtonEntry(150, I18n.format("createWorld.customize.custom.useStrongholds"), true, this.field_175336_F.useStrongholds),
         new GuiPageButtonList.GuiButtonEntry(151, I18n.format("createWorld.customize.custom.useVillages"), true, this.field_175336_F.useVillages),
         new GuiPageButtonList.GuiButtonEntry(152, I18n.format("createWorld.customize.custom.useMineShafts"), true, this.field_175336_F.useMineShafts),
         new GuiPageButtonList.GuiButtonEntry(153, I18n.format("createWorld.customize.custom.useTemples"), true, this.field_175336_F.useTemples),
         new GuiPageButtonList.GuiButtonEntry(210, I18n.format("createWorld.customize.custom.useMonuments"), true, this.field_175336_F.useMonuments),
         new GuiPageButtonList.GuiButtonEntry(154, I18n.format("createWorld.customize.custom.useRavines"), true, this.field_175336_F.useRavines),
         new GuiPageButtonList.GuiButtonEntry(149, I18n.format("createWorld.customize.custom.useDungeons"), true, this.field_175336_F.useDungeons),
         new GuiPageButtonList.GuiSlideEntry(
            157, I18n.format("createWorld.customize.custom.dungeonChance"), true, this, 1.0F, 100.0F, this.field_175336_F.dungeonChance
         ),
         new GuiPageButtonList.GuiButtonEntry(155, I18n.format("createWorld.customize.custom.useWaterLakes"), true, this.field_175336_F.useWaterLakes),
         new GuiPageButtonList.GuiSlideEntry(
            158, I18n.format("createWorld.customize.custom.waterLakeChance"), true, this, 1.0F, 100.0F, this.field_175336_F.waterLakeChance
         ),
         new GuiPageButtonList.GuiButtonEntry(156, I18n.format("createWorld.customize.custom.useLavaLakes"), true, this.field_175336_F.useLavaLakes),
         new GuiPageButtonList.GuiSlideEntry(
            159, I18n.format("createWorld.customize.custom.lavaLakeChance"), true, this, 10.0F, 100.0F, this.field_175336_F.lavaLakeChance
         ),
         new GuiPageButtonList.GuiButtonEntry(161, I18n.format("createWorld.customize.custom.useLavaOceans"), true, this.field_175336_F.useLavaOceans),
         new GuiPageButtonList.GuiSlideEntry(
            162, I18n.format("createWorld.customize.custom.fixedBiome"), true, this, -1.0F, 37.0F, this.field_175336_F.fixedBiome
         ),
         new GuiPageButtonList.GuiSlideEntry(163, I18n.format("createWorld.customize.custom.biomeSize"), true, this, 1.0F, 8.0F, this.field_175336_F.biomeSize),
         new GuiPageButtonList.GuiSlideEntry(164, I18n.format("createWorld.customize.custom.riverSize"), true, this, 1.0F, 5.0F, this.field_175336_F.riverSize)
      };
      GuiPageButtonList.GuiListEntry[] var4 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiLabelEntry(416, I18n.format("tile.dirt.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(165, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.dirtSize),
         new GuiPageButtonList.GuiSlideEntry(166, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.dirtCount),
         new GuiPageButtonList.GuiSlideEntry(
            167, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.dirtMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            168, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.dirtMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(417, I18n.format("tile.gravel.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(169, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.gravelSize),
         new GuiPageButtonList.GuiSlideEntry(170, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.gravelCount),
         new GuiPageButtonList.GuiSlideEntry(
            171, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.gravelMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            172, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.gravelMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(418, I18n.format("tile.stone.granite.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(173, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.graniteSize),
         new GuiPageButtonList.GuiSlideEntry(174, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.graniteCount),
         new GuiPageButtonList.GuiSlideEntry(
            175, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.graniteMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            176, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.graniteMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(419, I18n.format("tile.stone.diorite.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(177, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.dioriteSize),
         new GuiPageButtonList.GuiSlideEntry(178, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.dioriteCount),
         new GuiPageButtonList.GuiSlideEntry(
            179, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.dioriteMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            180, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.dioriteMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(420, I18n.format("tile.stone.andesite.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(181, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.andesiteSize),
         new GuiPageButtonList.GuiSlideEntry(
            182, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.andesiteCount
         ),
         new GuiPageButtonList.GuiSlideEntry(
            183, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.andesiteMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            184, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.andesiteMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(421, I18n.format("tile.oreCoal.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(185, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.coalSize),
         new GuiPageButtonList.GuiSlideEntry(186, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.coalCount),
         new GuiPageButtonList.GuiSlideEntry(
            187, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.coalMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            189, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.coalMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(422, I18n.format("tile.oreIron.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(190, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.ironSize),
         new GuiPageButtonList.GuiSlideEntry(191, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.ironCount),
         new GuiPageButtonList.GuiSlideEntry(
            192, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.ironMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            193, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.ironMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(423, I18n.format("tile.oreGold.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(194, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.goldSize),
         new GuiPageButtonList.GuiSlideEntry(195, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.goldCount),
         new GuiPageButtonList.GuiSlideEntry(
            196, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.goldMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            197, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.goldMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(424, I18n.format("tile.oreRedstone.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(198, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.redstoneSize),
         new GuiPageButtonList.GuiSlideEntry(
            199, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.redstoneCount
         ),
         new GuiPageButtonList.GuiSlideEntry(
            200, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.redstoneMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            201, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.redstoneMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(425, I18n.format("tile.oreDiamond.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(202, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.diamondSize),
         new GuiPageButtonList.GuiSlideEntry(203, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.diamondCount),
         new GuiPageButtonList.GuiSlideEntry(
            204, I18n.format("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.diamondMinHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            205, I18n.format("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, this.field_175336_F.diamondMaxHeight
         ),
         new GuiPageButtonList.GuiLabelEntry(426, I18n.format("tile.oreLapis.name"), false),
         null,
         new GuiPageButtonList.GuiSlideEntry(206, I18n.format("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, this.field_175336_F.lapisSize),
         new GuiPageButtonList.GuiSlideEntry(207, I18n.format("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, this.field_175336_F.lapisCount),
         new GuiPageButtonList.GuiSlideEntry(
            208, I18n.format("createWorld.customize.custom.center"), false, this, 0.0F, 255.0F, this.field_175336_F.lapisCenterHeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            209, I18n.format("createWorld.customize.custom.spread"), false, this, 0.0F, 255.0F, this.field_175336_F.lapisSpread
         )
      };
      GuiPageButtonList.GuiListEntry[] var5 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiSlideEntry(
            100, I18n.format("createWorld.customize.custom.mainNoiseScaleX"), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleX
         ),
         new GuiPageButtonList.GuiSlideEntry(
            101, I18n.format("createWorld.customize.custom.mainNoiseScaleY"), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleY
         ),
         new GuiPageButtonList.GuiSlideEntry(
            102, I18n.format("createWorld.customize.custom.mainNoiseScaleZ"), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleZ
         ),
         new GuiPageButtonList.GuiSlideEntry(
            103, I18n.format("createWorld.customize.custom.depthNoiseScaleX"), false, this, 1.0F, 2000.0F, this.field_175336_F.depthNoiseScaleX
         ),
         new GuiPageButtonList.GuiSlideEntry(
            104, I18n.format("createWorld.customize.custom.depthNoiseScaleZ"), false, this, 1.0F, 2000.0F, this.field_175336_F.depthNoiseScaleZ
         ),
         new GuiPageButtonList.GuiSlideEntry(
            105, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent"), false, this, 0.01F, 20.0F, this.field_175336_F.depthNoiseScaleExponent
         ),
         new GuiPageButtonList.GuiSlideEntry(106, I18n.format("createWorld.customize.custom.baseSize"), false, this, 1.0F, 25.0F, this.field_175336_F.baseSize),
         new GuiPageButtonList.GuiSlideEntry(
            107, I18n.format("createWorld.customize.custom.coordinateScale"), false, this, 1.0F, 6000.0F, this.field_175336_F.coordinateScale
         ),
         new GuiPageButtonList.GuiSlideEntry(
            108, I18n.format("createWorld.customize.custom.heightScale"), false, this, 1.0F, 6000.0F, this.field_175336_F.heightScale
         ),
         new GuiPageButtonList.GuiSlideEntry(109, I18n.format("createWorld.customize.custom.stretchY"), false, this, 0.01F, 50.0F, this.field_175336_F.stretchY),
         new GuiPageButtonList.GuiSlideEntry(
            110, I18n.format("createWorld.customize.custom.upperLimitScale"), false, this, 1.0F, 5000.0F, this.field_175336_F.upperLimitScale
         ),
         new GuiPageButtonList.GuiSlideEntry(
            111, I18n.format("createWorld.customize.custom.lowerLimitScale"), false, this, 1.0F, 5000.0F, this.field_175336_F.lowerLimitScale
         ),
         new GuiPageButtonList.GuiSlideEntry(
            112, I18n.format("createWorld.customize.custom.biomeDepthWeight"), false, this, 1.0F, 20.0F, this.field_175336_F.biomeDepthWeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            113, I18n.format("createWorld.customize.custom.biomeDepthOffset"), false, this, 0.0F, 20.0F, this.field_175336_F.biomeDepthOffset
         ),
         new GuiPageButtonList.GuiSlideEntry(
            114, I18n.format("createWorld.customize.custom.biomeScaleWeight"), false, this, 1.0F, 20.0F, this.field_175336_F.biomeScaleWeight
         ),
         new GuiPageButtonList.GuiSlideEntry(
            115, I18n.format("createWorld.customize.custom.biomeScaleOffset"), false, this, 0.0F, 20.0F, this.field_175336_F.biomeScaleOffset
         )
      };
      GuiPageButtonList.GuiListEntry[] var6 = new GuiPageButtonList.GuiListEntry[]{
         new GuiPageButtonList.GuiLabelEntry(400, I18n.format("createWorld.customize.custom.mainNoiseScaleX") + ":", false),
         new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", this.field_175336_F.mainNoiseScaleX), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(401, I18n.format("createWorld.customize.custom.mainNoiseScaleY") + ":", false),
         new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", this.field_175336_F.mainNoiseScaleY), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(402, I18n.format("createWorld.customize.custom.mainNoiseScaleZ") + ":", false),
         new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", this.field_175336_F.mainNoiseScaleZ), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(403, I18n.format("createWorld.customize.custom.depthNoiseScaleX") + ":", false),
         new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", this.field_175336_F.depthNoiseScaleX), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(404, I18n.format("createWorld.customize.custom.depthNoiseScaleZ") + ":", false),
         new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", this.field_175336_F.depthNoiseScaleZ), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(405, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent") + ":", false),
         new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", this.field_175336_F.depthNoiseScaleExponent), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(406, I18n.format("createWorld.customize.custom.baseSize") + ":", false),
         new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", this.field_175336_F.baseSize), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(407, I18n.format("createWorld.customize.custom.coordinateScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", this.field_175336_F.coordinateScale), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(408, I18n.format("createWorld.customize.custom.heightScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", this.field_175336_F.heightScale), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(409, I18n.format("createWorld.customize.custom.stretchY") + ":", false),
         new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", this.field_175336_F.stretchY), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(410, I18n.format("createWorld.customize.custom.upperLimitScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", this.field_175336_F.upperLimitScale), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(411, I18n.format("createWorld.customize.custom.lowerLimitScale") + ":", false),
         new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", this.field_175336_F.lowerLimitScale), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(412, I18n.format("createWorld.customize.custom.biomeDepthWeight") + ":", false),
         new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", this.field_175336_F.biomeDepthWeight), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(413, I18n.format("createWorld.customize.custom.biomeDepthOffset") + ":", false),
         new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", this.field_175336_F.biomeDepthOffset), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(414, I18n.format("createWorld.customize.custom.biomeScaleWeight") + ":", false),
         new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", this.field_175336_F.biomeScaleWeight), false, this.N),
         new GuiPageButtonList.GuiLabelEntry(415, I18n.format("createWorld.customize.custom.biomeScaleOffset") + ":", false),
         new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", this.field_175336_F.biomeScaleOffset), false, this.N)
      };
      this.field_175349_r = new GuiPageButtonList(this.mc, this.width, this.height, 32, this.height - 32, 25, this, var3, var4, var5, var6);

      for (int var7 = 0; var7 < 4; var7++) {
         this.field_175342_h[var7] = I18n.format("createWorld.customize.custom.page" + var7);
      }

      this.func_175328_i();
   }

   public String func_175323_a() {
      return this.field_175336_F.toString().replace("\n", "");
   }

   public void func_175324_a(String string) {
      if (string != null && string.length() != 0) {
         this.field_175336_F = ChunkProviderSettings.Factory.jsonToFactory(string);
      } else {
         this.field_175336_F = new ChunkProviderSettings.Factory();
      }
   }

   @Override
   public void func_175319_a(int integer, String string) {
      float var5 = 0.0F;

      try {
         var5 = Float.parseFloat(string);
      } catch (NumberFormatException var7) {
      }

      float var6 = 0.0F;
      switch (integer) {
         case 132:
            var6 = this.field_175336_F.mainNoiseScaleX = MathHelper.clamp_float(var5, 1.0F, 5000.0F);
            break;
         case 133:
            var6 = this.field_175336_F.mainNoiseScaleY = MathHelper.clamp_float(var5, 1.0F, 5000.0F);
            break;
         case 134:
            var6 = this.field_175336_F.mainNoiseScaleZ = MathHelper.clamp_float(var5, 1.0F, 5000.0F);
            break;
         case 135:
            var6 = this.field_175336_F.depthNoiseScaleX = MathHelper.clamp_float(var5, 1.0F, 2000.0F);
            break;
         case 136:
            var6 = this.field_175336_F.depthNoiseScaleZ = MathHelper.clamp_float(var5, 1.0F, 2000.0F);
            break;
         case 137:
            var6 = this.field_175336_F.depthNoiseScaleExponent = MathHelper.clamp_float(var5, 0.01F, 20.0F);
            break;
         case 138:
            var6 = this.field_175336_F.baseSize = MathHelper.clamp_float(var5, 1.0F, 25.0F);
            break;
         case 139:
            var6 = this.field_175336_F.coordinateScale = MathHelper.clamp_float(var5, 1.0F, 6000.0F);
            break;
         case 140:
            var6 = this.field_175336_F.heightScale = MathHelper.clamp_float(var5, 1.0F, 6000.0F);
            break;
         case 141:
            var6 = this.field_175336_F.stretchY = MathHelper.clamp_float(var5, 0.01F, 50.0F);
            break;
         case 142:
            var6 = this.field_175336_F.upperLimitScale = MathHelper.clamp_float(var5, 1.0F, 5000.0F);
            break;
         case 143:
            var6 = this.field_175336_F.lowerLimitScale = MathHelper.clamp_float(var5, 1.0F, 5000.0F);
            break;
         case 144:
            var6 = this.field_175336_F.biomeDepthWeight = MathHelper.clamp_float(var5, 1.0F, 20.0F);
            break;
         case 145:
            var6 = this.field_175336_F.biomeDepthOffset = MathHelper.clamp_float(var5, 0.0F, 20.0F);
            break;
         case 146:
            var6 = this.field_175336_F.biomeScaleWeight = MathHelper.clamp_float(var5, 1.0F, 20.0F);
            break;
         case 147:
            var6 = this.field_175336_F.biomeScaleOffset = MathHelper.clamp_float(var5, 0.0F, 20.0F);
      }

      if (var6 != var5 && var5 != 0.0F) {
         ((GuiTextField)this.field_175349_r.func_178061_c(integer)).setText(this.func_175330_b(integer, var6));
      }

      ((GuiSlider)this.field_175349_r.func_178061_c(integer - 132 + 100)).func_175218_a(var6, false);
      if (!this.field_175336_F.equals(this.field_175334_E)) {
         this.func_181031_a(true);
      }
   }

   private void func_181031_a(boolean boolean1) {
      this.field_175338_A = boolean1;
      this.field_175346_u.enabled = boolean1;
   }

   @Override
   public String getText(int integer, String string, float float3) {
      return string + ": " + this.func_175330_b(integer, float3);
   }

   private String func_175330_b(int integer, float float2) {
      switch (integer) {
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 107:
         case 108:
         case 110:
         case 111:
         case 132:
         case 133:
         case 134:
         case 135:
         case 136:
         case 139:
         case 140:
         case 142:
         case 143:
            return String.format("%5.3f", float2);
         case 105:
         case 106:
         case 109:
         case 112:
         case 113:
         case 114:
         case 115:
         case 137:
         case 138:
         case 141:
         case 144:
         case 145:
         case 146:
         case 147:
            return String.format("%2.3f", float2);
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         default:
            return String.format("%d", (int)float2);
         case 162:
            if (float2 < 0.0F) {
               return I18n.format("gui.all");
            } else if ((int)float2 >= BiomeGenBase.hell.biomeID) {
               BiomeGenBase var4 = BiomeGenBase.getBiomeGenArray()[(int)float2 + 2];
               return var4 != null ? var4.biomeName : "?";
            } else {
               BiomeGenBase var3 = BiomeGenBase.getBiomeGenArray()[(int)float2];
               return var3 != null ? var3.biomeName : "?";
            }
      }
   }

   @Override
   public void func_175321_a(int integer, boolean boolean2) {
      switch (integer) {
         case 148:
            this.field_175336_F.useCaves = boolean2;
            break;
         case 149:
            this.field_175336_F.useDungeons = boolean2;
            break;
         case 150:
            this.field_175336_F.useStrongholds = boolean2;
            break;
         case 151:
            this.field_175336_F.useVillages = boolean2;
            break;
         case 152:
            this.field_175336_F.useMineShafts = boolean2;
            break;
         case 153:
            this.field_175336_F.useTemples = boolean2;
            break;
         case 154:
            this.field_175336_F.useRavines = boolean2;
            break;
         case 155:
            this.field_175336_F.useWaterLakes = boolean2;
            break;
         case 156:
            this.field_175336_F.useLavaLakes = boolean2;
            break;
         case 161:
            this.field_175336_F.useLavaOceans = boolean2;
            break;
         case 210:
            this.field_175336_F.useMonuments = boolean2;
      }

      if (!this.field_175336_F.equals(this.field_175334_E)) {
         this.func_181031_a(true);
      }
   }

   @Override
   public void onTick(int integer, float float2) {
      switch (integer) {
         case 100:
            this.field_175336_F.mainNoiseScaleX = float2;
            break;
         case 101:
            this.field_175336_F.mainNoiseScaleY = float2;
            break;
         case 102:
            this.field_175336_F.mainNoiseScaleZ = float2;
            break;
         case 103:
            this.field_175336_F.depthNoiseScaleX = float2;
            break;
         case 104:
            this.field_175336_F.depthNoiseScaleZ = float2;
            break;
         case 105:
            this.field_175336_F.depthNoiseScaleExponent = float2;
            break;
         case 106:
            this.field_175336_F.baseSize = float2;
            break;
         case 107:
            this.field_175336_F.coordinateScale = float2;
            break;
         case 108:
            this.field_175336_F.heightScale = float2;
            break;
         case 109:
            this.field_175336_F.stretchY = float2;
            break;
         case 110:
            this.field_175336_F.upperLimitScale = float2;
            break;
         case 111:
            this.field_175336_F.lowerLimitScale = float2;
            break;
         case 112:
            this.field_175336_F.biomeDepthWeight = float2;
            break;
         case 113:
            this.field_175336_F.biomeDepthOffset = float2;
            break;
         case 114:
            this.field_175336_F.biomeScaleWeight = float2;
            break;
         case 115:
            this.field_175336_F.biomeScaleOffset = float2;
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 132:
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 153:
         case 154:
         case 155:
         case 156:
         case 161:
         case 188:
         default:
            break;
         case 157:
            this.field_175336_F.dungeonChance = (int)float2;
            break;
         case 158:
            this.field_175336_F.waterLakeChance = (int)float2;
            break;
         case 159:
            this.field_175336_F.lavaLakeChance = (int)float2;
            break;
         case 160:
            this.field_175336_F.seaLevel = (int)float2;
            break;
         case 162:
            this.field_175336_F.fixedBiome = (int)float2;
            break;
         case 163:
            this.field_175336_F.biomeSize = (int)float2;
            break;
         case 164:
            this.field_175336_F.riverSize = (int)float2;
            break;
         case 165:
            this.field_175336_F.dirtSize = (int)float2;
            break;
         case 166:
            this.field_175336_F.dirtCount = (int)float2;
            break;
         case 167:
            this.field_175336_F.dirtMinHeight = (int)float2;
            break;
         case 168:
            this.field_175336_F.dirtMaxHeight = (int)float2;
            break;
         case 169:
            this.field_175336_F.gravelSize = (int)float2;
            break;
         case 170:
            this.field_175336_F.gravelCount = (int)float2;
            break;
         case 171:
            this.field_175336_F.gravelMinHeight = (int)float2;
            break;
         case 172:
            this.field_175336_F.gravelMaxHeight = (int)float2;
            break;
         case 173:
            this.field_175336_F.graniteSize = (int)float2;
            break;
         case 174:
            this.field_175336_F.graniteCount = (int)float2;
            break;
         case 175:
            this.field_175336_F.graniteMinHeight = (int)float2;
            break;
         case 176:
            this.field_175336_F.graniteMaxHeight = (int)float2;
            break;
         case 177:
            this.field_175336_F.dioriteSize = (int)float2;
            break;
         case 178:
            this.field_175336_F.dioriteCount = (int)float2;
            break;
         case 179:
            this.field_175336_F.dioriteMinHeight = (int)float2;
            break;
         case 180:
            this.field_175336_F.dioriteMaxHeight = (int)float2;
            break;
         case 181:
            this.field_175336_F.andesiteSize = (int)float2;
            break;
         case 182:
            this.field_175336_F.andesiteCount = (int)float2;
            break;
         case 183:
            this.field_175336_F.andesiteMinHeight = (int)float2;
            break;
         case 184:
            this.field_175336_F.andesiteMaxHeight = (int)float2;
            break;
         case 185:
            this.field_175336_F.coalSize = (int)float2;
            break;
         case 186:
            this.field_175336_F.coalCount = (int)float2;
            break;
         case 187:
            this.field_175336_F.coalMinHeight = (int)float2;
            break;
         case 189:
            this.field_175336_F.coalMaxHeight = (int)float2;
            break;
         case 190:
            this.field_175336_F.ironSize = (int)float2;
            break;
         case 191:
            this.field_175336_F.ironCount = (int)float2;
            break;
         case 192:
            this.field_175336_F.ironMinHeight = (int)float2;
            break;
         case 193:
            this.field_175336_F.ironMaxHeight = (int)float2;
            break;
         case 194:
            this.field_175336_F.goldSize = (int)float2;
            break;
         case 195:
            this.field_175336_F.goldCount = (int)float2;
            break;
         case 196:
            this.field_175336_F.goldMinHeight = (int)float2;
            break;
         case 197:
            this.field_175336_F.goldMaxHeight = (int)float2;
            break;
         case 198:
            this.field_175336_F.redstoneSize = (int)float2;
            break;
         case 199:
            this.field_175336_F.redstoneCount = (int)float2;
            break;
         case 200:
            this.field_175336_F.redstoneMinHeight = (int)float2;
            break;
         case 201:
            this.field_175336_F.redstoneMaxHeight = (int)float2;
            break;
         case 202:
            this.field_175336_F.diamondSize = (int)float2;
            break;
         case 203:
            this.field_175336_F.diamondCount = (int)float2;
            break;
         case 204:
            this.field_175336_F.diamondMinHeight = (int)float2;
            break;
         case 205:
            this.field_175336_F.diamondMaxHeight = (int)float2;
            break;
         case 206:
            this.field_175336_F.lapisSize = (int)float2;
            break;
         case 207:
            this.field_175336_F.lapisCount = (int)float2;
            break;
         case 208:
            this.field_175336_F.lapisCenterHeight = (int)float2;
            break;
         case 209:
            this.field_175336_F.lapisSpread = (int)float2;
      }

      if (integer >= 100 && integer < 116) {
         Gui var5 = this.field_175349_r.func_178061_c(integer - 100 + 132);
         if (var5 != null) {
            ((GuiTextField)var5).setText(this.func_175330_b(integer, float2));
         }
      }

      if (!this.field_175336_F.equals(this.field_175334_E)) {
         this.func_181031_a(true);
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         switch (guiButton.l) {
            case 300:
               this.field_175343_i.chunkProviderSettingsJson = this.field_175336_F.toString();
               this.mc.displayGuiScreen(this.field_175343_i);
               break;
            case 301:
               for (int var4 = 0; var4 < this.field_175349_r.getSize(); var4++) {
                  GuiPageButtonList.GuiEntry var5 = this.field_175349_r.getListEntry(var4);
                  if (var5.func_178022_a() instanceof GuiButton var7) {
                     if (var7 instanceof GuiSlider) {
                        float var8 = ((GuiSlider)var7).func_175217_d() * (0.75F + this.random.nextFloat() * 0.5F) + (this.random.nextFloat() * 0.1F - 0.05F);
                        ((GuiSlider)var7).func_175219_a(MathHelper.clamp_float(var8, 0.0F, 1.0F));
                     } else if (var7 instanceof GuiListButton) {
                        ((GuiListButton)var7).func_175212_b(this.random.nextBoolean());
                     }
                  }

                  if (var5.func_178021_b() instanceof GuiButton var11) {
                     if (var11 instanceof GuiSlider) {
                        float var9 = ((GuiSlider)var11).func_175217_d() * (0.75F + this.random.nextFloat() * 0.5F) + (this.random.nextFloat() * 0.1F - 0.05F);
                        ((GuiSlider)var11).func_175219_a(MathHelper.clamp_float(var9, 0.0F, 1.0F));
                     } else if (var11 instanceof GuiListButton) {
                        ((GuiListButton)var11).func_175212_b(this.random.nextBoolean());
                     }
                  }
               }

               return;
            case 302:
               this.field_175349_r.func_178071_h();
               this.func_175328_i();
               break;
            case 303:
               this.field_175349_r.func_178064_i();
               this.func_175328_i();
               break;
            case 304:
               if (this.field_175338_A) {
                  this.func_175322_b(304);
               }
               break;
            case 305:
               this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
               break;
            case 306:
               this.func_175331_h();
               break;
            case 307:
               this.field_175339_B = 0;
               this.func_175331_h();
         }
      }
   }

   private void func_175326_g() {
      this.field_175336_F.func_177863_a();
      this.func_175325_f();
      this.func_181031_a(false);
   }

   private void func_175322_b(int integer) {
      this.field_175339_B = integer;
      this.func_175329_a(true);
   }

   private void func_175331_h() throws IOException {
      switch (this.field_175339_B) {
         case 300:
            this.actionPerformed((GuiListButton)this.field_175349_r.func_178061_c(300));
            break;
         case 304:
            this.func_175326_g();
      }

      this.field_175339_B = 0;
      this.field_175340_C = true;
      this.func_175329_a(false);
   }

   private void func_175329_a(boolean boolean1) {
      this.field_175352_x.visible = boolean1;
      this.field_175351_y.visible = boolean1;
      this.field_175347_t.enabled = !boolean1;
      this.field_175348_s.enabled = !boolean1;
      this.field_175345_v.enabled = !boolean1;
      this.field_175344_w.enabled = !boolean1;
      this.field_175346_u.enabled = this.field_175338_A && !boolean1;
      this.field_175350_z.enabled = !boolean1;
      this.field_175349_r.func_181155_a(!boolean1);
   }

   private void func_175328_i() {
      this.field_175345_v.enabled = this.field_175349_r.func_178059_e() != 0;
      this.field_175344_w.enabled = this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1;
      this.field_175333_f = I18n.format("book.pageIndicator", this.field_175349_r.func_178059_e() + 1, this.field_175349_r.func_178057_f());
      this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
      this.field_175347_t.enabled = this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1;
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      super.onKeyTyped(charTyped, keyCode);
      if (this.field_175339_B == 0) {
         switch (keyCode) {
            case 200:
               this.func_175327_a(1.0F);
               break;
            case 208:
               this.func_175327_a(-1.0F);
               break;
            default:
               this.field_175349_r.func_178062_a(charTyped, keyCode);
         }
      }
   }

   private void func_175327_a(float float1) {
      if (this.field_175349_r.func_178056_g() instanceof GuiTextField var3) {
         float var4 = float1;
         if (GuiScreen.isShiftKeyDown()) {
            var4 = float1 * 0.1F;
            if (GuiScreen.isCtrlKeyDown()) {
               var4 *= 0.1F;
            }
         } else if (GuiScreen.isCtrlKeyDown()) {
            var4 = float1 * 10.0F;
            if (GuiScreen.isAltKeyDown()) {
               var4 *= 10.0F;
            }
         }

         Float var5 = Floats.tryParse(var3.getText());
         if (var5 != null) {
            var5 = var5 + var4;
            int var6 = var3.getId();
            String var7 = this.func_175330_b(var3.getId(), var5);
            var3.setText(var7);
            this.func_175319_a(var6, var7);
         }
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      if (this.field_175339_B == 0 && !this.field_175340_C) {
         this.field_175349_r.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      super.onMouseReleased(mouseX, mouseY, button);
      if (this.field_175340_C) {
         this.field_175340_C = false;
      } else if (this.field_175339_B == 0) {
         this.field_175349_r.mouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.field_175349_r.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.field_175341_a, this.width / 2, 2, 16777215);
      this.drawCenteredString(this.fontRendererObj, this.field_175333_f, this.width / 2, 12, 16777215);
      this.drawCenteredString(this.fontRendererObj, this.field_175335_g, this.width / 2, 22, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      if (this.field_175339_B != 0) {
         drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
         this.drawHorizontalLine(this.width / 2 - 91, this.width / 2 + 90, 99, -2039584);
         this.drawHorizontalLine(this.width / 2 - 91, this.width / 2 + 90, 185, -6250336);
         this.drawVerticalLine(this.width / 2 - 91, 99, 185, -2039584);
         this.drawVerticalLine(this.width / 2 + 90, 99, 185, -6250336);
         GlStateManager.disableLighting();
         GlStateManager.disableFog();
         Tessellator var8 = Tessellator.getInstance();
         WorldRenderer var9 = var8.getWorldRenderer();
         this.mc.getTextureManager().bindTexture(optionsBackground);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         var9.pos(this.width / 2 - 90, 185.0, 0.0).tex(0.0, 2.65625).color(64, 64, 64, 64).endVertex();
         var9.pos(this.width / 2 + 90, 185.0, 0.0).tex(5.625, 2.65625).color(64, 64, 64, 64).endVertex();
         var9.pos(this.width / 2 + 90, 100.0, 0.0).tex(5.625, 0.0).color(64, 64, 64, 64).endVertex();
         var9.pos(this.width / 2 - 90, 100.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 64).endVertex();
         var8.draw();
         this.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirmTitle"), this.width / 2, 105, 16777215);
         this.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm1"), this.width / 2, 125, 16777215);
         this.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm2"), this.width / 2, 135, 16777215);
         this.field_175352_x.drawButton(this.mc, mouseX, mouseY);
         this.field_175351_y.drawButton(this.mc, mouseX, mouseY);
      }
   }
}
