package net.minecraft.client.shader;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
   private static final Logger logger = LogManager.getLogger();
   private static ShaderLinkHelper staticShaderLinkHelper;

   public static void setNewStaticShaderLinkHelper() {
      staticShaderLinkHelper = new ShaderLinkHelper();
   }

   public static ShaderLinkHelper getStaticShaderLinkHelper() {
      return staticShaderLinkHelper;
   }

   public void deleteShader(ShaderManager shaderManager) {
      shaderManager.Method358().deleteShader(shaderManager);
      shaderManager.Method357().deleteShader(shaderManager);
      OpenGlHelper.glDeleteProgram(shaderManager.getProgram());
   }

   public int createProgram() throws JsonException {
      int var1 = OpenGlHelper.glCreateProgram();
      if (var1 <= 0) {
         throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
      } else {
         return var1;
      }
   }

   public void linkProgram(ShaderManager shaderManager) throws IOException {
      shaderManager.Method358().attachShader(shaderManager);
      shaderManager.Method357().attachShader(shaderManager);
      OpenGlHelper.glLinkProgram(shaderManager.getProgram());
      int var4 = OpenGlHelper.glGetProgrami(shaderManager.getProgram(), OpenGlHelper.GL_LINK_STATUS);
      if (var4 == 0) {
         logger.warn(
            "Error encountered when linking program containing VS "
               + shaderManager.Method357().getShaderFilename()
               + " and FS "
               + shaderManager.Method358().getShaderFilename()
               + ". Log output:"
         );
         logger.warn(OpenGlHelper.glGetProgramInfoLog(shaderManager.getProgram(), 32768));
      }
   }
}
