package net.optifine.shaders.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.EnumShaderOption;

public class GuiButtonEnumShaderOption extends GuiButton {
   private EnumShaderOption enumShaderOption = null;

   public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int integer2, int integer3, int integer4, int integer5) {
      super(enumShaderOption.ordinal(), integer2, integer3, integer4, integer5, getButtonText(enumShaderOption));
      this.enumShaderOption = enumShaderOption;
   }

   public EnumShaderOption getEnumShaderOption() {
      return this.enumShaderOption;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static String getButtonText(EnumShaderOption enumShaderOption) {
      String var3 = I18n.format(enumShaderOption.getResourceKey()) + ": ";
      switch (enumShaderOption) {
         case ANTIALIASING :
            return var3 + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
         case NORMAL_MAP :
            return var3 + GuiShaders.toStringOnOff(Shaders.configNormalMap);
         case SPECULAR_MAP :
            return var3 + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
         case RENDER_RES_MUL :
            return var3 + GuiShaders.toStringQuality(Shaders.configRenderResMul);
         case SHADOW_RES_MUL :
            return var3 + GuiShaders.toStringQuality(Shaders.configShadowResMul);
         case HAND_DEPTH_MUL :
            return var3 + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
         case CLOUD_SHADOW :
            return var3 + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
         case OLD_HAND_LIGHT :
            return var3 + Shaders.configOldHandLight.getUserValue();
         case OLD_LIGHTING :
            return var3 + Shaders.configOldLighting.getUserValue();
         case SHADOW_CLIP_FRUSTRUM :
            return var3 + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
         case TWEAK_BLOCK_DAMAGE :
            return var3 + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
         default:
            return var3 + Shaders.getEnumShaderOption(enumShaderOption);
      }
   }

   public void updateButtonText() {
      this.displayString = getButtonText(this.enumShaderOption);
   }
}
