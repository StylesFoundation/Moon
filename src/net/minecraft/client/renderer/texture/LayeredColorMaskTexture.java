package net.minecraft.client.renderer.texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture {
   private static final Logger LOG = LogManager.getLogger();
   private final ResourceLocation textureLocation;
   private final List<String> field_174949_h;
   private final List<EnumDyeColor> field_174950_i;

   public LayeredColorMaskTexture(ResourceLocation resourceLocation, List<String> list2, List<EnumDyeColor> list3) {
      this.textureLocation = resourceLocation;
      this.field_174949_h = list2;
      this.field_174950_i = list3;
   }

   @Override
   public void loadTexture(IResourceManager iResourceManager) throws IOException {
      this.deleteGlTexture();

      BufferedImage var4;
      try {
         BufferedImage var5 = TextureUtil.readBufferedImage(iResourceManager.getResource(this.textureLocation).getInputStream());
         int var6 = var5.getType();
         if (var6 == 0) {
            var6 = 6;
         }

         var4 = new BufferedImage(var5.getWidth(), var5.getHeight(), var6);
         Graphics var7 = var4.getGraphics();
         var7.drawImage(var5, 0, 0, null);

         for (int var8 = 0; var8 < 17 && var8 < this.field_174949_h.size() && var8 < this.field_174950_i.size(); var8++) {
            String var9 = this.field_174949_h.get(var8);
            MapColor var10 = this.field_174950_i.get(var8).getMapColor();
            if (var9 != null) {
               BufferedImage var12 = TextureUtil.readBufferedImage(iResourceManager.getResource(new ResourceLocation(var9)).getInputStream());
               if (var12.getWidth() == var4.getWidth() && var12.getHeight() == var4.getHeight() && var12.getType() == 6) {
                  for (int var13 = 0; var13 < var12.getHeight(); var13++) {
                     for (int var14 = 0; var14 < var12.getWidth(); var14++) {
                        int var15 = var12.getRGB(var14, var13);
                        if ((var15 & 0xFF000000) != 0) {
                           int var16 = (var15 & 0xFF0000) << 8 & 0xFF000000;
                           int var17 = var5.getRGB(var14, var13);
                           int var18 = MathHelper.func_180188_d(var17, var10.colorValue) & 16777215;
                           var12.setRGB(var14, var13, var16 | var18);
                        }
                     }
                  }

                  var4.getGraphics().drawImage(var12, 0, 0, null);
               }
            }
         }
      } catch (IOException var19) {
         LOG.error("Couldn't load layered image", var19);
         return;
      }

      if (Config.isShaders()) {
         ShadersTex.loadSimpleTexture(this.getGlTextureId(), var4, false, false, iResourceManager, this.textureLocation, this.getMultiTexID());
      } else {
         TextureUtil.uploadTextureImage(this.getGlTextureId(), var4);
      }
   }
}
