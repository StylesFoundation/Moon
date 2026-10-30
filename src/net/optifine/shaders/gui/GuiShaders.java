package net.optifine.shaders.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.config.EnumShaderOption;
import org.lwjgl.Sys;

public class GuiShaders extends GuiScreenOF {
   protected GuiScreen parentGui;
   protected String screenTitle = "Shaders";
   private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderEnumShaderOptions());
   private int updateTimer = -1;
   private GuiSlotShaders shaderList;
   private boolean saved = false;
   private static final float[] QUALITY_MULTIPLIERS = new float[]{
      0.5F, 0.6F, 0.6666667F, 0.75F, 0.8333333F, 0.9F, 1.0F, 1.1666666F, 1.3333334F, 1.5F, 1.6666666F, 1.8F, 2.0F
   };
   private static final String[] QUALITY_MULTIPLIER_NAMES = new String[]{
      "0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x"
   };
   private static final float[] HAND_DEPTH_VALUES = new float[]{0.0625F, 0.125F, 0.25F};
   private static final String[] HAND_DEPTH_NAMES = new String[]{"0.5x", "1x", "2x"};

   public GuiShaders(GuiScreen guiScreen, GameSettings gameSettings) {
      this.parentGui = guiScreen;
   }

   @Override
   public void onInitGui() {
      this.screenTitle = I18n.format("of.options.shadersTitle");
      if (Shaders.shadersConfig == null) {
         Shaders.loadConfig();
      }

      byte var4 = 120;
      byte var5 = 20;
      int var6 = this.width - var4 - 10;
      byte var7 = 30;
      byte var8 = 20;
      int var9 = this.width - var4 - 20;
      this.shaderList = new GuiSlotShaders(this, var9, this.height, var7, this.height - 50, 16);
      this.shaderList.registerScrollButtons(7, 8);
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, var6, var7, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, var6, 50, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, var6, 70, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, var6, 90, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, var6, 110, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, var6, 130, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, var6, 150, var4, var5));
      this.m.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, var6, 170, var4, var5));
      int var10 = Math.min(150, var9 / 2 - 10);
      int var11 = var9 / 4 - var10 / 2;
      int var12 = this.height - 25;
      this.m.add(new GuiButton(201, var11, var12, var10 - 22 + 1, var5, Lang.get("of.options.shaders.shadersFolder")));
      this.m.add(new GuiButtonDownloadShaders(210, var11 + var10 - 22 - 1, var12));
      this.m.add(new GuiButton(202, var9 / 4 * 3 - var10 / 2, this.height - 25, var10, var5, I18n.format("gui.done")));
      this.m.add(new GuiButton(203, var6, this.height - 25, var4, var5, Lang.get("of.options.shaders.shaderOptions")));
      this.updateButtons();
   }

   public void updateButtons() {
      boolean var4 = Config.isShaders();

      for (GuiButton var6 : this.m) {
         if (var6.l != 201 && var6.l != 202 && var6.l != 210 && var6.l != EnumShaderOption.ANTIALIASING.ordinal()) {
            var6.enabled = var4;
         }
      }
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.shaderList.handleMouseInput();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) {
      this.actionPerformed(guiButton, false);
   }

   @Override
   protected void actionPerformedRightClick(GuiButton guiButton) {
      this.actionPerformed(guiButton, true);
   }

   private void actionPerformed(GuiButton guiButton, boolean boolean2) {
      if (guiButton.enabled) {
         if (guiButton instanceof GuiButtonEnumShaderOption var6) {
            switch (var6.getEnumShaderOption()) {
               case ANTIALIASING:
                  Shaders.nextAntialiasingLevel(!boolean2);
                  if (this.hasShiftDown()) {
                     Shaders.configAntialiasingLevel = 0;
                  }

                  Shaders.uninit();
                  break;
               case NORMAL_MAP:
                  Shaders.configNormalMap = !Shaders.configNormalMap;
                  if (this.hasShiftDown()) {
                     Shaders.configNormalMap = true;
                  }

                  Shaders.uninit();
                  this.mc.scheduleResourcesRefresh();
                  break;
               case SPECULAR_MAP:
                  Shaders.configSpecularMap = !Shaders.configSpecularMap;
                  if (this.hasShiftDown()) {
                     Shaders.configSpecularMap = true;
                  }

                  Shaders.uninit();
                  this.mc.scheduleResourcesRefresh();
                  break;
               case RENDER_RES_MUL:
                  Shaders.configRenderResMul = this.getNextValue(Shaders.configRenderResMul, QUALITY_MULTIPLIERS, 1.0F, !boolean2, this.hasShiftDown());
                  Shaders.uninit();
                  Shaders.scheduleResize();
                  break;
               case SHADOW_RES_MUL:
                  Shaders.configShadowResMul = this.getNextValue(Shaders.configShadowResMul, QUALITY_MULTIPLIERS, 1.0F, !boolean2, this.hasShiftDown());
                  Shaders.uninit();
                  Shaders.scheduleResizeShadow();
                  break;
               case HAND_DEPTH_MUL:
                  Shaders.configHandDepthMul = this.getNextValue(Shaders.configHandDepthMul, HAND_DEPTH_VALUES, 0.125F, !boolean2, this.hasShiftDown());
                  Shaders.uninit();
                  break;
               case OLD_HAND_LIGHT:
                  Shaders.configOldHandLight.nextValue(!boolean2);
                  if (this.hasShiftDown()) {
                     Shaders.configOldHandLight.resetValue();
                  }

                  Shaders.uninit();
                  break;
               case OLD_LIGHTING:
                  Shaders.configOldLighting.nextValue(!boolean2);
                  if (this.hasShiftDown()) {
                     Shaders.configOldLighting.resetValue();
                  }

                  Shaders.updateBlockLightLevel();
                  Shaders.uninit();
                  this.mc.scheduleResourcesRefresh();
                  break;
               case TWEAK_BLOCK_DAMAGE:
                  Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                  break;
               case CLOUD_SHADOW:
                  Shaders.configCloudShadow = !Shaders.configCloudShadow;
                  break;
               case TEX_MIN_FIL_B:
                  Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                  Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
                  guiButton.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
                  ShadersTex.updateTextureMinMagFilter();
                  break;
               case TEX_MAG_FIL_N:
                  Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                  guiButton.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
                  ShadersTex.updateTextureMinMagFilter();
                  break;
               case TEX_MAG_FIL_S:
                  Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                  guiButton.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
                  ShadersTex.updateTextureMinMagFilter();
                  break;
               case SHADOW_CLIP_FRUSTRUM:
                  Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                  guiButton.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
                  ShadersTex.updateTextureMinMagFilter();
            }

            var6.updateButtonText();
         } else if (!boolean2) {
            switch (guiButton.l) {
               case 201:
                  switch (getOSType()) {
                     case 1:
                        String var7 = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderPacksDir.getAbsolutePath());

                        try {
                           Runtime.getRuntime().exec(var7);
                           return;
                        } catch (IOException var14) {
                           var14.printStackTrace();
                           break;
                        }
                     case 2:
                        try {
                           Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath()});
                           return;
                        } catch (IOException var13) {
                           var13.printStackTrace();
                        }
                  }

                  boolean var15 = false;

                  try {
                     Class var16 = Class.forName("java.awt.Desktop");
                     Object var17 = var16.getMethod("getDesktop").invoke(null);
                     var16.getMethod("browse", URI.class).invoke(var17, new File(this.mc.mcDataDir, "shaderpacks").toURI());
                  } catch (Throwable var12) {
                     var12.printStackTrace();
                     var15 = true;
                  }

                  if (var15) {
                     Config.Method251("Opening via system class!");
                     Sys.openURL("file://" + Shaders.shaderPacksDir.getAbsolutePath());
                  }
                  break;
               case 202:
                  Shaders.storeConfig();
                  this.saved = true;
                  this.mc.displayGuiScreen(this.parentGui);
                  break;
               case 203:
                  GuiShaderOptions var8 = new GuiShaderOptions(this, Config.getGameSettings());
                  Config.getMinecraft().displayGuiScreen(var8);
                  break;
               case 210:
                  try {
                     Class var9 = Class.forName("java.awt.Desktop");
                     Object var10 = var9.getMethod("getDesktop").invoke(null);
                     var9.getMethod("browse", URI.class).invoke(var10, new URI("http://optifine.net/shaderPacks"));
                  } catch (Throwable var11) {
                     var11.printStackTrace();
                  }
               case 204:
               case 205:
               case 206:
               case 207:
               case 208:
               case 209:
               default:
                  this.shaderList.actionPerformed(guiButton);
            }
         }
      }
   }

   @Override
   public void onGuiClosed() {
      super.onGuiClosed();
      if (!this.saved) {
         Shaders.storeConfig();
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.shaderList.drawScreen(mouseX, mouseY, partialTicks);
      if (this.updateTimer <= 0) {
         this.shaderList.updateList();
         this.updateTimer += 20;
      }

      this.drawCenteredString(this.fontRendererObj, this.screenTitle + " ", this.width / 2, 15, 16777215);
      String var9 = Shaders.glRendererString;
      String var10 = Shaders.glVendorString;
      String var11 = Shaders.glVersionString;
      String var7 = "OpenGL: " + var11 + ", " + var10 + ", " + var9;
      int var8 = this.fontRendererObj.getStringWidth(var7);
      if (var8 < this.width - 5) {
         this.drawCenteredString(this.fontRendererObj, var7, this.width / 2, this.height - 40, 8421504);
      } else {
         this.drawString(this.fontRendererObj, var7, 5, this.height - 40, 8421504);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(mouseX, mouseY, this.m);
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      this.updateTimer--;
   }

   public Minecraft getMc() {
      return this.mc;
   }

   public void drawCenteredString(String string, int integer2, int integer3, int integer4) {
      this.drawCenteredString(this.fontRendererObj, string, integer2, integer3, integer4);
   }

   public static String toStringOnOff(boolean boolean1) {
      String var3 = Lang.getOn();
      String var4 = Lang.getOff();
      return boolean1 ? var3 : var4;
   }

   public static String toStringAa(int integer) {
      return integer == 2 ? "FXAA 2x" : (integer == 4 ? "FXAA 4x" : Lang.getOff());
   }

   public static String toStringValue(float float1, float[] values, String[] names) {
      int var3 = getValueIndex(float1, values);
      return names[var3];
   }

   private float getNextValue(float float1, float[] arr, float float3, boolean boolean4, boolean boolean5) {
      if (boolean5) {
         return float3;
      }

      int var9 = getValueIndex(float1, arr);
      if (boolean4) {
         if (++var9 >= arr.length) {
            var9 = 0;
         }
      } else if (--var9 < 0) {
         var9 = arr.length - 1;
      }

      return arr[var9];
   }

   public static int getValueIndex(float float1, float[] arr) {
      for (int var5 = 0; var5 < arr.length; var5++) {
         float var6 = arr[var5];
         if (var6 >= float1) {
            return var5;
         }
      }

      return arr.length - 1;
   }

   public static String toStringQuality(float float1) {
      return toStringValue(float1, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
   }

   public static String toStringHandDepth(float float1) {
      return toStringValue(float1, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
   }

   public static int getOSType() {
      String var3 = System.getProperty("os.name").toLowerCase();
      return var3.contains("win")
         ? 1
         : (
            var3.contains("mac")
               ? 2
               : (var3.contains("solaris") ? 3 : (var3.contains("sunos") ? 3 : (var3.contains("linux") ? 4 : (var3.contains("unix") ? 4 : 0))))
         );
   }

   public boolean hasShiftDown() {
      return isShiftKeyDown();
   }
}
