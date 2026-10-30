package net.minecraft.client.shader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderManager {
   private static final Logger logger = LogManager.getLogger();
   private static final ShaderDefault defaultShaderUniform = new ShaderDefault();
   private static ShaderManager staticShaderManager = null;
   private static int currentProgram = -1;
   private static boolean field_148000_e = true;
   private final Map<String, Object> shaderSamplers = Maps.newHashMap();
   private final List<String> samplerNames = Lists.newArrayList();
   private final List<Integer> shaderSamplerLocations = Lists.newArrayList();
   private final List<ShaderUniform> shaderUniforms = Lists.newArrayList();
   private final List<Integer> shaderUniformLocations = Lists.newArrayList();
   private final Map<String, ShaderUniform> mappedShaderUniforms = Maps.newHashMap();
   private final int program;
   private final String programFilename;
   private final boolean useFaceCulling;
   private boolean isDirty;
   private final JsonBlendingMode p;
   private final List<Integer> q;
   private final List<String> r;
   private final ShaderLoader s;
   private final ShaderLoader t;

   public ShaderManager(IResourceManager iResourceManager, String string) throws IOException {
      ResourceLocation var3 = new ResourceLocation("shaders/program/" + string + ".json");
      this.programFilename = string;
      InputStream var4 = null;

      try {
         var4 = iResourceManager.getResource(var3).getInputStream();
         JsonObject var5 = JsonParser.parseString(IOUtils.toString(var4, Charsets.UTF_8)).getAsJsonObject();
         String var27 = JsonUtils.Method741(var5, "vertex");
         String var7 = JsonUtils.Method741(var5, "fragment");
         JsonArray var8 = JsonUtils.getJsonArray(var5, "samplers", null);
         if (var8 != null) {
            int var9 = 0;

            for (JsonElement var11 : var8) {
               try {
                  this.parseSampler(var11);
               } catch (Exception var24) {
                  JsonException var13 = JsonException.func_151379_a(var24);
                  var13.func_151380_a("samplers[" + var9 + "]");
                  throw var13;
               }

               var9++;
            }
         }

         JsonArray var28 = JsonUtils.getJsonArray(var5, "attributes", null);
         if (var28 != null) {
            int var29 = 0;
            this.q = Lists.newArrayListWithCapacity(var28.size());
            this.r = Lists.newArrayListWithCapacity(var28.size());

            for (JsonElement var12 : var28) {
               try {
                  this.r.add(JsonUtils.Method740(var12, "attribute"));
               } catch (Exception var23) {
                  JsonException var14 = JsonException.func_151379_a(var23);
                  var14.func_151380_a("attributes[" + var29 + "]");
                  throw var14;
               }

               var29++;
            }
         } else {
            this.q = null;
            this.r = null;
         }

         JsonArray var30 = JsonUtils.getJsonArray(var5, "uniforms", null);
         if (var30 != null) {
            int var32 = 0;

            for (JsonElement var36 : var30) {
               try {
                  this.parseUniform(var36);
               } catch (Exception var22) {
                  JsonException var15 = JsonException.func_151379_a(var22);
                  var15.func_151380_a("uniforms[" + var32 + "]");
                  throw var15;
               }

               var32++;
            }
         }

         this.p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObject(var5, "blend", null));
         this.useFaceCulling = JsonUtils.getBoolean(var5, "cull", true);
         this.s = ShaderLoader.loadShader(iResourceManager, ShaderLoader.ShaderType.VERTEX, var27);
         this.t = ShaderLoader.loadShader(iResourceManager, ShaderLoader.ShaderType.FRAGMENT, var7);
         this.program = ShaderLinkHelper.getStaticShaderLinkHelper().createProgram();
         ShaderLinkHelper.getStaticShaderLinkHelper().linkProgram(this);
         this.setupUniforms();
         if (this.r != null) {
            for (String var35 : this.r) {
               int var37 = OpenGlHelper.glGetAttribLocation(this.program, var35);
               this.q.add(var37);
            }
         }
      } catch (Exception var25) {
         JsonException var6 = JsonException.func_151379_a(var25);
         var6.func_151381_b(var3.getResourcePath());
         throw var6;
      } finally {
         IOUtils.closeQuietly(var4);
      }

      this.markDirty();
   }

   public void deleteShader() {
      ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
   }

   public void endShader() {
      OpenGlHelper.glUseProgram(0);
      currentProgram = -1;
      staticShaderManager = null;
      field_148000_e = true;

      for (int var1 = 0; var1 < this.shaderSamplerLocations.size(); var1++) {
         if (this.shaderSamplers.get(this.samplerNames.get(var1)) != null) {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var1);
            GlStateManager.bindTexture(0);
         }
      }
   }

   public void useShader() {
      this.isDirty = false;
      staticShaderManager = this;
      this.p.func_148109_a();
      if (this.program != currentProgram) {
         OpenGlHelper.glUseProgram(this.program);
         currentProgram = this.program;
      }

      if (this.useFaceCulling) {
         GlStateManager.enableCull();
      } else {
         GlStateManager.disableCull();
      }

      for (int var1 = 0; var1 < this.shaderSamplerLocations.size(); var1++) {
         if (this.shaderSamplers.get(this.samplerNames.get(var1)) != null) {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var1);
            GlStateManager.enableTexture2D();
            Object var2 = this.shaderSamplers.get(this.samplerNames.get(var1));
            int var3 = -1;
            if (var2 instanceof Framebuffer) {
               var3 = ((Framebuffer)var2).framebufferTexture;
            } else if (var2 instanceof ITextureObject) {
               var3 = ((ITextureObject)var2).getGlTextureId();
            } else if (var2 instanceof Integer) {
               var3 = (Integer)var2;
            }

            if (var3 != -1) {
               GlStateManager.bindTexture(var3);
               OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(this.program, this.samplerNames.get(var1)), var1);
            }
         }
      }

      Iterator var4 = this.shaderUniforms.iterator();

      while (var4.hasNext()) {
         ((ShaderUniform)var4.next()).Method380();
      }
   }

   public void markDirty() {
      this.isDirty = true;
   }

   public ShaderUniform getShaderUniform(String string) {
      return this.mappedShaderUniforms.getOrDefault(string, null);
   }

   public ShaderUniform getShaderUniformOrDefault(String string) {
      return this.mappedShaderUniforms.getOrDefault(string, defaultShaderUniform);
   }

   private void setupUniforms() {
      int var1 = 0;

      for (int var2 = 0; var1 < this.samplerNames.size(); var2++) {
         String var3 = this.samplerNames.get(var1);
         int var4 = OpenGlHelper.glGetUniformLocation(this.program, var3);
         if (var4 == -1) {
            logger.warn("Shader " + this.programFilename + "could not find sampler named " + var3 + " in the specified shader program.");
            this.shaderSamplers.remove(var3);
            this.samplerNames.remove(var2);
            var2--;
         } else {
            this.shaderSamplerLocations.add(var4);
         }

         var1++;
      }

      for (ShaderUniform var7 : this.shaderUniforms) {
         String var8 = var7.getShaderName();
         int var5 = OpenGlHelper.glGetUniformLocation(this.program, var8);
         if (var5 == -1) {
            logger.warn("Could not find uniform named " + var8 + " in the specified shader program.");
         } else {
            this.shaderUniformLocations.add(var5);
            var7.setUniformLocation(var5);
            this.mappedShaderUniforms.put(var8, var7);
         }
      }
   }

   private void parseSampler(JsonElement jsonElement) throws JsonException {
      JsonObject var2 = JsonUtils.Method746(jsonElement, "sampler");
      String var3 = JsonUtils.Method741(var2, "name");
      if (!JsonUtils.isString(var2, "file")) {
         this.shaderSamplers.put(var3, null);
         this.samplerNames.add(var3);
      } else {
         this.samplerNames.add(var3);
      }
   }

   public void addSamplerTexture(String string, Object object) {
      this.shaderSamplers.remove(string);
      this.shaderSamplers.put(string, object);
      this.markDirty();
   }

   private void parseUniform(JsonElement jsonElement) throws JsonException {
      JsonObject var4 = JsonUtils.Method746(jsonElement, "uniform");
      String var5 = JsonUtils.Method741(var4, "name");
      int var6 = ShaderUniform.parseType(JsonUtils.Method741(var4, "type"));
      int var7 = JsonUtils.Method745(var4, "count");
      float[] var8 = new float[Math.max(var7, 16)];
      JsonArray var9 = JsonUtils.Method749(var4, "values");
      if (var9.size() != var7 && var9.size() > 1) {
         throw new JsonException("Invalid amount of values specified (expected " + var7 + ", found " + var9.size() + ")");
      }

      int var10 = 0;

      for (JsonElement var12 : var9) {
         try {
            var8[var10] = JsonUtils.getFloat(var12, "value");
         } catch (Exception var15) {
            JsonException var14 = JsonException.func_151379_a(var15);
            var14.func_151380_a("values[" + var10 + "]");
            throw var14;
         }

         var10++;
      }

      if (var7 > 1 && var9.size() == 1) {
         while (var10 < var7) {
            var8[var10] = var8[0];
            var10++;
         }
      }

      int var16 = var7 > 1 && var7 <= 4 && var6 < 8 ? var7 - 1 : 0;
      ShaderUniform var17 = new ShaderUniform(var5, var6 + var16, var7, this);
      if (var6 <= 3) {
         var17.set((int)var8[0], (int)var8[1], (int)var8[2], (int)var8[3]);
      } else if (var6 <= 7) {
         var17.func_148092_b(var8[0], var8[1], var8[2], var8[3]);
      } else {
         var17.set(var8);
      }

      this.shaderUniforms.add(var17);
   }

   public ShaderLoader Method357() {
      return this.s;
   }

   public ShaderLoader Method358() {
      return this.t;
   }

   public int getProgram() {
      return this.program;
   }
}
