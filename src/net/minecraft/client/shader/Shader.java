package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {
   private final ShaderManager manager;
   public final Framebuffer framebufferIn;
   public final Framebuffer framebufferOut;
   private final List<Object> listAuxFramebuffers = Lists.newArrayList();
   private final List<String> listAuxNames = Lists.newArrayList();
   private final List<Integer> listAuxWidths = Lists.newArrayList();
   private final List<Integer> listAuxHeights = Lists.newArrayList();
   private Matrix4f projectionMatrix;

   public Shader(IResourceManager iResourceManager, String string, Framebuffer framebuffer3, Framebuffer framebuffer4) throws IOException {
      this.manager = new ShaderManager(iResourceManager, string);
      this.framebufferIn = framebuffer3;
      this.framebufferOut = framebuffer4;
   }

   public void deleteShader() {
      this.manager.deleteShader();
   }

   public void addAuxFramebuffer(String string, Object object, int integer3, int integer4) {
      this.listAuxNames.add(this.listAuxNames.size(), string);
      this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), object);
      this.listAuxWidths.add(this.listAuxWidths.size(), integer3);
      this.listAuxHeights.add(this.listAuxHeights.size(), integer4);
   }

   private void preLoadShader() {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.disableDepth();
      GlStateManager.disableAlpha();
      GlStateManager.disableFog();
      GlStateManager.disableLighting();
      GlStateManager.disableColorMaterial();
      GlStateManager.enableTexture2D();
      GlStateManager.bindTexture(0);
   }

   public void setProjectionMatrix(Matrix4f matrix4f) {
      this.projectionMatrix = matrix4f;
   }

   public void loadShader(float float1) {
      this.preLoadShader();
      this.framebufferIn.unbindFramebuffer();
      float var4 = this.framebufferOut.framebufferTextureWidth;
      float var5 = this.framebufferOut.framebufferTextureHeight;
      GlStateManager.viewport(0, 0, (int)var4, (int)var5);
      this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);

      for (int var6 = 0; var6 < this.listAuxFramebuffers.size(); var6++) {
         this.manager.addSamplerTexture(this.listAuxNames.get(var6), this.listAuxFramebuffers.get(var6));
         this.manager.getShaderUniformOrDefault("AuxSize" + var6).set(this.listAuxWidths.get(var6).intValue(), this.listAuxHeights.get(var6).intValue());
      }

      this.manager.getShaderUniformOrDefault("ProjMat").set(this.projectionMatrix);
      this.manager.getShaderUniformOrDefault("InSize").set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
      this.manager.getShaderUniformOrDefault("OutSize").set(var4, var5);
      this.manager.getShaderUniformOrDefault("Time").set(float1);
      Minecraft var11 = Minecraft.getMinecraft();
      this.manager.getShaderUniformOrDefault("ScreenSize").set(var11.displayWidth, var11.displayHeight);
      this.manager.useShader();
      this.framebufferOut.framebufferClear();
      this.framebufferOut.bindFramebuffer(false);
      GlStateManager.depthMask(false);
      GlStateManager.colorMask(true, true, true, true);
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      var8.begin(7, DefaultVertexFormats.POSITION_COLOR);
      var8.pos(0.0, var5, 500.0).color(255, 255, 255, 255).endVertex();
      var8.pos(var4, var5, 500.0).color(255, 255, 255, 255).endVertex();
      var8.pos(var4, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
      var8.pos(0.0, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
      var7.draw();
      GlStateManager.depthMask(true);
      GlStateManager.colorMask(true, true, true, true);
      this.manager.endShader();
      this.framebufferOut.unbindFramebuffer();
      this.framebufferIn.unbindFramebufferTexture();

      for (Object var10 : this.listAuxFramebuffers) {
         if (var10 instanceof Framebuffer) {
            ((Framebuffer)var10).unbindFramebufferTexture();
         }
      }
   }

   public ShaderManager getShaderManager() {
      return this.manager;
   }
}
