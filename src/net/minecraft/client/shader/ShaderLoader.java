package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

public class ShaderLoader {
   private final ShaderLoader.ShaderType shaderType;
   private final String shaderFilename;
   private final int shader;
   private int shaderAttachCount = 0;

   private ShaderLoader(ShaderLoader.ShaderType shaderType, int integer, String string) {
      this.shaderType = shaderType;
      this.shader = integer;
      this.shaderFilename = string;
   }

   public void attachShader(ShaderManager shaderManager) {
      this.shaderAttachCount++;
      OpenGlHelper.glAttachShader(shaderManager.getProgram(), this.shader);
   }

   public void deleteShader(ShaderManager shaderManager) {
      this.shaderAttachCount--;
      if (this.shaderAttachCount <= 0) {
         OpenGlHelper.glDeleteShader(this.shader);
         this.shaderType.getLoadedShaders().remove(this.shaderFilename);
      }
   }

   public String getShaderFilename() {
      return this.shaderFilename;
   }

   public static ShaderLoader loadShader(IResourceManager iResourceManager, ShaderLoader.ShaderType shaderType, String string) throws IOException {
      ShaderLoader var5 = shaderType.getLoadedShaders().get(string);
      if (var5 == null) {
         ResourceLocation var6 = new ResourceLocation("shaders/program/" + string + shaderType.getShaderExtension());
         byte[] var8 = toByteArray(new BufferedInputStream(iResourceManager.getResource(var6).getInputStream()));
         ByteBuffer var9 = BufferUtils.createByteBuffer(var8.length);
         var9.put(var8);
         var9.position(0);
         int var10 = OpenGlHelper.glCreateShader(shaderType.getShaderMode());
         OpenGlHelper.glShaderSource(var10, var9);
         OpenGlHelper.glCompileShader(var10);
         if (OpenGlHelper.glGetShaderi(var10, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
            String var11 = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(var10, 32768));
            JsonException var12 = new JsonException("Couldn't compile " + shaderType.getShaderName() + " program: " + var11);
            var12.func_151381_b(var6.getResourcePath());
            throw var12;
         }

         var5 = new ShaderLoader(shaderType, var10, string);
         shaderType.getLoadedShaders().put(string, var5);
      }

      return var5;
   }

   protected static byte[] toByteArray(BufferedInputStream bufferedInputStream) throws IOException {
      try (bufferedInputStream) {
         return IOUtils.toByteArray(bufferedInputStream);
      }
   }

   public enum ShaderType {
      VERTEX("vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
      FRAGMENT("fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);

      private final String shaderName;
      private final String shaderExtension;
      private final int shaderMode;
      private final Map<String, ShaderLoader> loadedShaders = Maps.newHashMap();

      ShaderType(String string3, String string4, int integer5) {
         this.shaderName = string3;
         this.shaderExtension = string4;
         this.shaderMode = integer5;
      }

      public String getShaderName() {
         return this.shaderName;
      }

      private String getShaderExtension() {
         return this.shaderExtension;
      }

      private int getShaderMode() {
         return this.shaderMode;
      }

      private Map<String, ShaderLoader> getLoadedShaders() {
         return this.loadedShaders;
      }

      private static ShaderLoader.ShaderType[] Method722() {
         return new ShaderLoader.ShaderType[]{VERTEX, FRAGMENT};
      }
   }
}
