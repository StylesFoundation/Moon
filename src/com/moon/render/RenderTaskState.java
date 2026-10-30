package com.moon.render;

import com.moon.handlers.ScaleHandler;
import com.moon.render.command.RenderCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.impl.MinecraftFontRenderer;
import com.moon.render.gl.GpuBuffer;
import com.moon.render.gl.VertexArray;
import com.moon.render.shader.ShaderProgram;
import com.moon.render.state.RenderState;
import com.moon.render.vertex.VertexDataBuffer;
import com.moon.util.render.MatrixUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.util.vector.Matrix4f;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class RenderTaskState {
   private static final TaggedLogger LOGGER = Logger.tag("engine/task/state");
   private final VertexDataBuffer vertexData = new VertexDataBuffer(2048);
   private final Matrix4f modelMatrix = MatrixUtils.createScaleMatrix(1.0F, 1.0F, 1.0F);
   private final List<RenderCommand> commands = new ArrayList<>();
   private final List<RenderState> renderStates = new ArrayList<>();
   private MinecraftFontRenderer minecraftFontRenderer;
   private FontRenderer fontRenderer;
   private GpuBuffer gpuBuffer;
   private VertexArray vertexArray;
   private Matrix4f modelViewMatrix;
   private Matrix4f projectionMatrix;
   private int drawOffset;
   private int vertexCount;
   private float viewportWidth;
   private float viewportHeight;
   private boolean compiled;
   private boolean vertexDataBuilt;
   private boolean retainGpuBuffer;
   private int drawMode = 7;

   public RenderTaskState() {
      this.viewportWidth = (float)ScaleHandler.getScaledResolution().getScaledWidth_double();
      this.viewportHeight = (float)ScaleHandler.getScaledResolution().getScaledHeight_double();
   }

   public void addCommand(RenderCommand command) {
      this.commands.add(command);
   }

   public void addVertex(float vertex) {
      this.vertexData.appendFloat(vertex);
      this.vertexCount++;
   }

   public void addRenderState(RenderState renderState) {
      this.renderStates.add(renderState);
   }

   public void attachRenderStates(ShaderProgram shaderProgram) {
      for (RenderState renderState : this.renderStates) {
         renderState.withShaderProgram(shaderProgram);
         renderState.apply(this.viewportWidth, this.viewportHeight);
      }
   }

   public void detachRenderStates() {
      Iterator iterator = this.renderStates.iterator();

      while (iterator.hasNext()) {
         ((RenderState)iterator.next()).disable();
      }
   }

   public void initialize(RenderTaskDispatcher dispatcher) {
   }

   public void applyMinecraftFontTransform() {
      this.minecraftFontRenderer.begin(this.modelMatrix);
   }

   public void bindFontRenderer() {
      this.fontRenderer.bind();
   }

   public void restoreMinecraftFontTransform() {
      this.minecraftFontRenderer.end(this.modelMatrix);
   }

   public void buildVertexData() {
      net.minecraft.client.gui.ScaledResolution sr = ScaleHandler.getScaledResolution();
      this.viewportWidth = (float)sr.getScaledWidth_double();
      this.viewportHeight = (float)sr.getScaledHeight_double();
      int totalStride = 0;

      for (RenderCommand command : this.commands) {
         totalStride += command.getVertexStride();
      }

      this.vertexData.ensureCapacity(totalStride);

      for (RenderCommand command : this.commands) {
         this.vertexCount = this.vertexCount + command.appendVertices(this.vertexData, this.viewportWidth, this.viewportHeight);
      }

      this.vertexDataBuilt = true;
   }

   public void uploadVertexData() {
      if (!this.hasGpuBuffers()) {
         LOGGER.error("Illegal task compilation call, no buffers are being overridden");
      } else if (this.compiled) {
         LOGGER.error("Illegal task compilation call, task is already compiled");
      } else {
         this.gpuBuffer.bind(OpenGlHelper.GL_ARRAY_BUFFER);
         this.gpuBuffer.allocate(OpenGlHelper.GL_ARRAY_BUFFER, this.vertexData.getCapacity() * 4L, 35048);
         this.gpuBuffer.upload(OpenGlHelper.GL_ARRAY_BUFFER, 0L, this.vertexData);
         this.compiled = true;
      }
   }

   public void releaseGpuResources() {
      if (this.hasGpuBuffers()) {
         this.gpuBuffer.delete();
         this.vertexArray.delete();
      }

      this.vertexData.free();
   }

   public boolean hasGpuBuffers() {
      return this.vertexArray != null && this.gpuBuffer != null;
   }

   public boolean hasRenderMatrices() {
      return this.modelViewMatrix != null && this.projectionMatrix != null;
   }

   public boolean hasMinecraftFontRenderer() {
      return this.minecraftFontRenderer != null;
   }

   public boolean hasFontRenderer() {
      return this.fontRenderer != null;
   }

   public boolean hasRenderStates() {
      return !this.renderStates.isEmpty();
   }

   public VertexDataBuffer getVertexData() {
      return this.vertexData;
   }

   public Matrix4f getModelMatrix() {
      return this.modelMatrix;
   }

   public List<RenderCommand> getCommands() {
      return this.commands;
   }

   public List<RenderState> getRenderStates() {
      return this.renderStates;
   }

   public MinecraftFontRenderer getMinecraftFontRenderer() {
      return this.minecraftFontRenderer;
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public GpuBuffer getGpuBuffer() {
      return this.gpuBuffer;
   }

   public VertexArray getVertexArray() {
      return this.vertexArray;
   }

   public Matrix4f getModelViewMatrix() {
      return this.modelViewMatrix;
   }

   public Matrix4f getProjectionMatrix() {
      return this.projectionMatrix;
   }

   public int getDrawOffset() {
      return this.drawOffset;
   }

   public int getVertexCount() {
      return this.vertexCount;
   }

   public float getViewportWidth() {
      return this.viewportWidth;
   }

   public float getViewportHeight() {
      return this.viewportHeight;
   }

   public boolean isCompiled() {
      return this.compiled;
   }

   public boolean isVertexDataBuilt() {
      return this.vertexDataBuilt;
   }

   public boolean retainsGpuBuffer() {
      return this.retainGpuBuffer;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public RenderTaskState setMinecraftFontRenderer(MinecraftFontRenderer minecraftFontRenderer) {
      this.minecraftFontRenderer = minecraftFontRenderer;
      return this;
   }

   public RenderTaskState setFontRenderer(FontRenderer fontRenderer) {
      this.fontRenderer = fontRenderer;
      return this;
   }

   public RenderTaskState setGpuBuffer(GpuBuffer gpuBuffer) {
      this.gpuBuffer = gpuBuffer;
      return this;
   }

   public RenderTaskState setVertexArray(VertexArray vertexArray) {
      this.vertexArray = vertexArray;
      return this;
   }

   public RenderTaskState setModelViewMatrix(Matrix4f modelViewMatrix) {
      this.modelViewMatrix = modelViewMatrix;
      return this;
   }

   public RenderTaskState setProjectionMatrix(Matrix4f projectionMatrix) {
      this.projectionMatrix = projectionMatrix;
      return this;
   }

   public RenderTaskState setDrawOffset(int drawOffset) {
      this.drawOffset = drawOffset;
      return this;
   }

   public RenderTaskState setVertexCount(int vertexCount) {
      this.vertexCount = vertexCount;
      return this;
   }

   public RenderTaskState setViewportWidth(float viewportWidth) {
      this.viewportWidth = viewportWidth;
      return this;
   }

   public RenderTaskState setViewportHeight(float viewportHeight) {
      this.viewportHeight = viewportHeight;
      return this;
   }

   public RenderTaskState setCompiled(boolean compiled) {
      this.compiled = compiled;
      return this;
   }

   public RenderTaskState setVertexDataBuilt(boolean vertexDataBuilt) {
      this.vertexDataBuilt = vertexDataBuilt;
      return this;
   }

   public RenderTaskState setRetainGpuBuffer(boolean retainGpuBuffer) {
      this.retainGpuBuffer = retainGpuBuffer;
      return this;
   }

   public RenderTaskState setDrawMode(int drawMode) {
      this.drawMode = drawMode;
      return this;
   }
}
