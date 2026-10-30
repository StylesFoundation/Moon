package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredTexture extends AbstractTexture {
   private static final Logger logger = LogManager.getLogger();
   public final List<String> layeredTextureNames;
   private ResourceLocation textureLocation;

   public LayeredTexture(String... arr) {
      this.layeredTextureNames = Lists.newArrayList(arr);
      if (arr.length > 0 && arr[0] != null) {
         this.textureLocation = new ResourceLocation(arr[0]);
      }
   }

   @Override
   public void loadTexture(IResourceManager iResourceManager) throws IOException {
      this.deleteGlTexture();
      BufferedImage var2 = null;

      try {
         for (String var4 : this.layeredTextureNames) {
            if (var4 != null) {
               BufferedImage var6 = TextureUtil.readBufferedImage(iResourceManager.getResource(new ResourceLocation(var4)).getInputStream());
               if (var2 == null) {
                  var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
               }

               var2.getGraphics().drawImage(var6, 0, 0, null);
            }
         }
      } catch (IOException var7) {
         logger.error("Couldn't load layered image", var7);
         return;
      }

      if (Config.isShaders()) {
         ShadersTex.loadSimpleTexture(this.getGlTextureId(), var2, false, false, iResourceManager, this.textureLocation, this.getMultiTexID());
      } else {
         TextureUtil.uploadTextureImage(this.getGlTextureId(), var2);
      }
   }
}
