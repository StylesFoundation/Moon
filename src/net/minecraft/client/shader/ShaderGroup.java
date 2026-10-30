package net.minecraft.client.shader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderGroup {
   private final Framebuffer mainFramebuffer;
   private final IResourceManager resourceManager;
   private final String shaderGroupName;
   private final List<Shader> listShaders = Lists.newArrayList();
   private final Map<String, Framebuffer> mapFramebuffers = Maps.newHashMap();
   private final List<Framebuffer> listFramebuffers = Lists.newArrayList();
   private Matrix4f projectionMatrix;
   private int mainFramebufferWidth;
   private int mainFramebufferHeight;
   private float field_148036_j;
   private float field_148037_k;

   public ShaderGroup(TextureManager textureManager, IResourceManager iResourceManager, Framebuffer framebuffer, ResourceLocation resourceLocation) throws IOException, JsonSyntaxException {
      this.resourceManager = iResourceManager;
      this.mainFramebuffer = framebuffer;
      this.field_148036_j = 0.0F;
      this.field_148037_k = 0.0F;
      this.mainFramebufferWidth = framebuffer.framebufferWidth;
      this.mainFramebufferHeight = framebuffer.framebufferHeight;
      this.shaderGroupName = resourceLocation.toString();
      this.resetProjectionMatrix();
      this.parseGroup(textureManager, resourceLocation);
   }

   public void parseGroup(TextureManager textureManager, ResourceLocation resourceLocation) throws IOException, JsonSyntaxException {
      InputStream var3 = null;

      try {
         var3 = this.resourceManager.getResource(resourceLocation).getInputStream();
         JsonObject var21 = JsonParser.parseString(IOUtils.toString(var3, Charsets.UTF_8)).getAsJsonObject();
         if (JsonUtils.isJsonArray(var21, "targets")) {
            JsonArray var6 = var21.getAsJsonArray("targets");
            int var7 = 0;

            for (JsonElement var9 : var6) {
               try {
                  this.initTarget(var9);
               } catch (Exception var18) {
                  JsonException var11 = JsonException.func_151379_a(var18);
                  var11.func_151380_a("targets[" + var7 + "]");
                  throw var11;
               }

               var7++;
            }
         }

         if (JsonUtils.isJsonArray(var21, "passes")) {
            JsonArray var22 = var21.getAsJsonArray("passes");
            int var23 = 0;

            for (JsonElement var25 : var22) {
               try {
                  this.parsePass(textureManager, var25);
               } catch (Exception var17) {
                  JsonException var26 = JsonException.func_151379_a(var17);
                  var26.func_151380_a("passes[" + var23 + "]");
                  throw var26;
               }

               var23++;
            }
         }
      } catch (Exception var19) {
         JsonException var5 = JsonException.func_151379_a(var19);
         var5.func_151381_b(resourceLocation.getResourcePath());
         throw var5;
      } finally {
         IOUtils.closeQuietly(var3);
      }
   }

   private void initTarget(JsonElement jsonElement) throws JsonException {
      if (JsonUtils.isString(jsonElement)) {
         this.addFramebuffer(jsonElement.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
      } else {
         JsonObject var2 = JsonUtils.Method746(jsonElement, "target");
         String var3 = JsonUtils.Method741(var2, "name");
         int var4 = JsonUtils.getInt(var2, "width", this.mainFramebufferWidth);
         int var5 = JsonUtils.getInt(var2, "height", this.mainFramebufferHeight);
         if (this.mapFramebuffers.containsKey(var3)) {
            throw new JsonException(var3 + " is already defined");
         }

         this.addFramebuffer(var3, var4, var5);
      }
   }

   private void parsePass(TextureManager textureManager, JsonElement jsonElement) throws IOException {
      JsonObject var5 = JsonUtils.Method746(jsonElement, "pass");
      String var6 = JsonUtils.Method741(var5, "name");
      String var7 = JsonUtils.Method741(var5, "intarget");
      String var8 = JsonUtils.Method741(var5, "outtarget");
      Framebuffer var9 = this.getFramebuffer(var7);
      Framebuffer var10 = this.getFramebuffer(var8);
      if (var9 == null) {
         throw new JsonException("Input target '" + var7 + "' does not exist");
      }

      if (var10 == null) {
         throw new JsonException("Output target '" + var8 + "' does not exist");
      }

      Shader var11 = this.addShader(var6, var9, var10);
      JsonArray var12 = JsonUtils.getJsonArray(var5, "auxtargets", null);
      if (var12 != null) {
         int var13 = 0;

         for (JsonElement var15 : var12) {
            try {
               JsonObject var16 = JsonUtils.Method746(var15, "auxtarget");
               String var32 = JsonUtils.Method741(var16, "name");
               String var18 = JsonUtils.Method741(var16, "id");
               Framebuffer var19 = this.getFramebuffer(var18);
               if (var19 == null) {
                  ResourceLocation var20 = new ResourceLocation("textures/effect/" + var18 + ".png");

                  try {
                     this.resourceManager.getResource(var20);
                  } catch (FileNotFoundException var26) {
                     throw new JsonException("Render target or texture '" + var18 + "' does not exist");
                  }

                  textureManager.bindTexture(var20);
                  ITextureObject var21 = textureManager.getTexture(var20);
                  int var22 = JsonUtils.Method745(var16, "width");
                  int var23 = JsonUtils.Method745(var16, "height");
                  boolean var24 = JsonUtils.Method743(var16, "bilinear");
                  if (var24) {
                     GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                     GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                  } else {
                     GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                     GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                  }

                  var11.addAuxFramebuffer(var32, var21.getGlTextureId(), var22, var23);
               } else {
                  var11.addAuxFramebuffer(var32, var19, var19.framebufferTextureWidth, var19.framebufferTextureHeight);
               }
            } catch (Exception var27) {
               JsonException var17 = JsonException.func_151379_a(var27);
               var17.func_151380_a("auxtargets[" + var13 + "]");
               throw var17;
            }

            var13++;
         }
      }

      JsonArray var28 = JsonUtils.getJsonArray(var5, "uniforms", null);
      if (var28 != null) {
         int var29 = 0;

         for (JsonElement var31 : var28) {
            try {
               this.initUniform(var31);
            } catch (Exception var25) {
               JsonException var33 = JsonException.func_151379_a(var25);
               var33.func_151380_a("uniforms[" + var29 + "]");
               throw var33;
            }

            var29++;
         }
      }
   }

   private void initUniform(JsonElement jsonElement) throws JsonException {
      JsonObject var2 = JsonUtils.Method746(jsonElement, "uniform");
      String var3 = JsonUtils.Method741(var2, "name");
      ShaderUniform var4 = this.listShaders.get(this.listShaders.size() - 1).getShaderManager().getShaderUniform(var3);
      if (var4 == null) {
         throw new JsonException("Uniform '" + var3 + "' does not exist");
      }

      float[] var5 = new float[4];
      int var6 = 0;

      for (JsonElement var8 : JsonUtils.Method749(var2, "values")) {
         try {
            var5[var6] = JsonUtils.getFloat(var8, "value");
         } catch (Exception var11) {
            JsonException var10 = JsonException.func_151379_a(var11);
            var10.func_151380_a("values[" + var6 + "]");
            throw var10;
         }

         var6++;
      }

      switch (var6) {
         case 0:
         default:
            break;
         case 1:
            var4.set(var5[0]);
            break;
         case 2:
            var4.set(var5[0], var5[1]);
            break;
         case 3:
            var4.set(var5[0], var5[1], var5[2]);
            break;
         case 4:
            var4.set(var5[0], var5[1], var5[2], var5[3]);
      }
   }

   public Framebuffer getFramebufferRaw(String string) {
      return this.mapFramebuffers.get(string);
   }

   public void addFramebuffer(String string, int integer2, int integer3) {
      Framebuffer var4 = new Framebuffer(integer2, integer3, true);
      var4.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.mapFramebuffers.put(string, var4);
      if (integer2 == this.mainFramebufferWidth && integer3 == this.mainFramebufferHeight) {
         this.listFramebuffers.add(var4);
      }
   }

   public void deleteShaderGroup() {
      Iterator var1 = this.mapFramebuffers.values().iterator();

      while (var1.hasNext()) {
         ((Framebuffer)var1.next()).deleteFramebuffer();
      }

      var1 = this.listShaders.iterator();

      while (var1.hasNext()) {
         ((Shader)var1.next()).deleteShader();
      }

      this.listShaders.clear();
   }

   public Shader addShader(String string, Framebuffer framebuffer2, Framebuffer framebuffer3) throws IOException {
      Shader var4 = new Shader(this.resourceManager, string, framebuffer2, framebuffer3);
      this.listShaders.add(this.listShaders.size(), var4);
      return var4;
   }

   private void resetProjectionMatrix() {
      this.projectionMatrix = new Matrix4f();
      this.projectionMatrix.setIdentity();
      this.projectionMatrix.m00 = 2.0F / this.mainFramebuffer.framebufferTextureWidth;
      this.projectionMatrix.m11 = 2.0F / -this.mainFramebuffer.framebufferTextureHeight;
      this.projectionMatrix.m22 = -0.0020001999F;
      this.projectionMatrix.m33 = 1.0F;
      this.projectionMatrix.m03 = -1.0F;
      this.projectionMatrix.m13 = 1.0F;
      this.projectionMatrix.m23 = -1.0001999F;
   }

   public void createBindFramebuffers(int integer1, int integer2) {
      this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
      this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
      this.resetProjectionMatrix();
      Iterator var3 = this.listShaders.iterator();

      while (var3.hasNext()) {
         ((Shader)var3.next()).setProjectionMatrix(this.projectionMatrix);
      }

      var3 = this.listFramebuffers.iterator();

      while (var3.hasNext()) {
         ((Framebuffer)var3.next()).createBindFramebuffer(integer1, integer2);
      }
   }

   public void loadShaderGroup(float float1) {
      if (float1 < this.field_148037_k) {
         this.field_148036_j = this.field_148036_j + (1.0F - this.field_148037_k);
         this.field_148036_j += float1;
      } else {
         this.field_148036_j = this.field_148036_j + (float1 - this.field_148037_k);
      }

      this.field_148037_k = float1;

      while (this.field_148036_j > 20.0F) {
         this.field_148036_j -= 20.0F;
      }

      Iterator var2 = this.listShaders.iterator();

      while (var2.hasNext()) {
         ((Shader)var2.next()).loadShader(this.field_148036_j / 20.0F);
      }
   }

   public final String getShaderGroupName() {
      return this.shaderGroupName;
   }

   private Framebuffer getFramebuffer(String string) {
      return string == null ? null : (string.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(string));
   }
}
