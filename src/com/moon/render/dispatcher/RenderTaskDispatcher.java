package com.moon.render.dispatcher;

import com.moon.render.RenderTaskState;
import com.moon.render.RenderTransformState;
import com.moon.render.gl.GpuBuffer;
import com.moon.render.gl.VertexArray;
import com.moon.render.shader.ShaderProgram;
import com.moon.render.shader.ShaderUniform;
import com.moon.render.vertex.VertexAttributeLayout;
import com.moon.util.render.MatrixUtils;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public abstract class RenderTaskDispatcher {
   private static final TaggedLogger LOGGER = Logger.tag("engine/task/dispatcher");
   private final FloatBuffer modelViewBuffer = BufferUtils.createFloatBuffer(16);
   private final FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
   private VertexArray vertexArray;
   private GpuBuffer gpuBuffer;
   private VertexAttributeLayout attributeLayout;
   protected ShaderProgram shaderProgram;
   private final ShaderUniform modelViewUniform = ShaderUniform.builder().withName("matrix.modelview").withProgramSupplier(() -> this.shaderProgram).build();
   private final ShaderUniform projectionUniform = ShaderUniform.builder().withName("matrix.projection").withProgramSupplier(() -> this.shaderProgram).build();

   public abstract RenderTaskDispatcherConfiguration getConfiguration();

   public void initialize() {
      RenderTaskDispatcherConfiguration configuration = this.getConfiguration();
      this.shaderProgram = new ShaderProgram(configuration.getVertexShader() + ".vert", configuration.getFragmentShader() + ".frag", configuration.usesOptionalColorBuffer());
      this.createGpuResources();
      this.shaderProgram.bind();
      this.attributeLayout = VertexAttributeLayout.builder().withAttributeData(configuration.getAttributeLayout()).withShaderProgram(this.shaderProgram).build();
      this.linkUniforms();
      this.attributeLayout.loadAttributes();
      this.attributeLayout.disableAttributes();
      this.projectionUniform.link();
      this.modelViewUniform.link();
      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
      GL30C.glBindVertexArray(0);
      GL20.glUseProgram(0);
      LOGGER.info("Setup task dispatcher successful for {}", new Object[]{this.getClass()});
   }

   protected abstract void linkUniforms();

   protected abstract void configureShader();

   protected void createGpuResources() {
      this.vertexArray = new VertexArray();
      this.gpuBuffer = new GpuBuffer();
      this.vertexArray.bind();
      this.gpuBuffer.bind(34962);
      this.gpuBuffer.allocate(34962, 2048L, 35048);
   }

   public void render(RenderTaskState state) {
      GlStateManager.alphaFunc(516, 0.0F);
      this.shaderProgram.bind();
      this.vertexArray.bind();
      if (!state.isVertexDataBuilt()) {
         state.buildVertexData();
      }

      if (state.hasGpuBuffers()) {
         if (!state.isCompiled()) {
            state.uploadVertexData();
         } else {
            state.getGpuBuffer().bind(34962);
         }
      } else {
         this.gpuBuffer.bind(34962);
         this.gpuBuffer.allocate(34962, state.getVertexData().getCapacity() * 4L, 35048);
         this.gpuBuffer.upload(34962, 0L, state.getVertexData());
      }

      if (state.hasMinecraftFontRenderer()) {
         state.applyMinecraftFontTransform();
      }

      if (state.hasFontRenderer()) {
         state.bindFontRenderer();
      }

      float scaleX = RenderTransformState.getScaleX();
      float scaleY = RenderTransformState.getScaleY();
      float scaleZ = RenderTransformState.getScaleZ();
      float translateX = RenderTransformState.getTranslateX();
      float translateY = RenderTransformState.getTranslateY();
      float translateZ = RenderTransformState.getTranslateZ();
      float rotationAngle = RenderTransformState.getRotationAngle();
      float rotationAxisX = RenderTransformState.getRotationAxisX();
      float rotationAxisY = RenderTransformState.getRotationAxisY();
      float rotationAxisZ = RenderTransformState.getRotationAxisZ();
      if (RenderTransformState.hasScale()) {
         state.getModelMatrix().scale(new Vector3f(scaleX, scaleY, scaleZ));
      }

      if (RenderTransformState.hasTranslation()) {
         state.getModelMatrix().translate(new Vector3f(translateX, translateY, translateZ));
      }

      if (RenderTransformState.hasRotation()) {
         state.getModelMatrix().rotate(rotationAngle, new Vector3f(rotationAxisX, rotationAxisY, rotationAxisZ));
      }

      if (state.hasRenderStates()) {
         state.attachRenderStates(this.shaderProgram);
      }

      this.attributeLayout.disableAttributes();
      this.configureShader();
      this.modelViewBuffer.clear();
      this.projectionBuffer.clear();
      MatrixUtils.writeMatrix(state.hasRenderMatrices() ? state.getModelViewMatrix() : state.getModelMatrix(), this.modelViewBuffer);
      this.modelViewUniform.setMatrix4(false, this.modelViewBuffer);
      Matrix4f orthoMatrix = MatrixUtils.createOrthographicMatrix(0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
      MatrixUtils.writeMatrix(state.hasRenderMatrices() ? state.getProjectionMatrix() : orthoMatrix, this.projectionBuffer);
      this.projectionUniform.setMatrix4(false, this.projectionBuffer);
      GL15.glDrawArrays(state.getDrawMode(), 0, state.getVertexCount());
      if (!state.retainsGpuBuffer()) {
         state.releaseGpuResources();
      }

      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
      GL30C.glBindVertexArray(0);
      GL20.glUseProgram(0);
      if (state.hasMinecraftFontRenderer()) {
         state.restoreMinecraftFontTransform();
      }

      if (state.hasFontRenderer()) {
         GlStateManager.bindTexture(0);
      }

      if (RenderTransformState.hasScale()) {
         state.getModelMatrix().scale(new Vector3f(1.0F / scaleX, 1.0F / scaleY, 1.0F / scaleZ));
      }

      if (RenderTransformState.hasTranslation()) {
         state.getModelMatrix().translate(new Vector3f(-translateX, -translateY, -translateZ));
      }

      if (RenderTransformState.hasRotation()) {
         state.getModelMatrix().rotate(-rotationAngle, new Vector3f(rotationAxisX, rotationAxisY, rotationAxisZ));
      }

      if (state.hasRenderStates()) {
         state.detachRenderStates();
      }

      GlStateManager.alphaFunc(516, 0.1F);
   }

   public FloatBuffer getModelViewBuffer() {
      return this.modelViewBuffer;
   }

   public FloatBuffer getProjectionBuffer() {
      return this.projectionBuffer;
   }

   public VertexArray getVertexArray() {
      return this.vertexArray;
   }

   public GpuBuffer getGpuBuffer() {
      return this.gpuBuffer;
   }

   public VertexAttributeLayout getAttributeLayout() {
      return this.attributeLayout;
   }

   public ShaderProgram getShaderProgram() {
      return this.shaderProgram;
   }

   public ShaderUniform getModelViewUniform() {
      return this.modelViewUniform;
   }

   public ShaderUniform getProjectionUniform() {
      return this.projectionUniform;
   }
}
