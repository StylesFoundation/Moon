package com.moon.render;

import com.moon.render.command.RenderCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherRegistry;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.impl.MinecraftFontRenderer;
import com.moon.render.gl.GpuBuffer;
import com.moon.render.gl.VertexArray;
import com.moon.render.state.RenderState;
import com.moon.util.render.MatrixUtils;
import net.minecraft.client.renderer.ActiveRenderInfo;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class RenderTask {
   private static final TaggedLogger LOGGER = Logger.tag("engine/task");
   private final RenderTaskState state = new RenderTaskState();
   private RenderTaskDispatcher dispatcher;
   private boolean rendering;

   public void prepare() {
      if (this.dispatcher == null) {
         LOGGER.error("Illegal task setup call (dispatcher == NULL)");
      } else {
         if (this.dispatcher.getConfiguration().getDrawMode() != 0) {
            this.state.setDrawMode(this.dispatcher.getConfiguration().getDrawMode());
         }

         if (this.state.hasGpuBuffers()) {
            this.state.initialize(this.dispatcher);
         }
      }
   }

   public void addCommand(RenderCommand command) {
      this.state.addCommand(command);
   }

   public void addRenderState(RenderState state) {
      this.state.addRenderState(state);
   }

   public void render() {
      if (this.rendering) {
         LOGGER.error("Cannot render a task twice at once!");
      } else if (this.dispatcher == null) {
         LOGGER.error("Cannot render a task (dispatcher == NULL)");
      } else {
         this.rendering = true;
         this.dispatcher.render(this.state);
         this.rendering = false;
      }
   }

   public static RenderTask.Builder builder() {
      return new RenderTask.Builder();
   }

   public RenderTaskState getState() {
      return this.state;
   }

   public RenderTaskDispatcher getDispatcher() {
      return this.dispatcher;
   }

   public boolean isRendering() {
      return this.rendering;
   }

   public RenderTask setDispatcher(RenderTaskDispatcher dispatcher) {
      this.dispatcher = dispatcher;
      return this;
   }

   public RenderTask setRendering(boolean rendering) {
      this.rendering = rendering;
      return this;
   }

   public static class Builder {
      private final RenderTask task = new RenderTask();

      public RenderTask.Builder withRenderType(RenderType renderType) {
         this.task.setDispatcher(RenderTaskDispatcherRegistry.getDispatcher(renderType));
         return this;
      }

      public RenderTask.Builder withMinecraftFontRenderer(MinecraftFontRenderer minecraftFontRenderer) {
         this.task.getState().setMinecraftFontRenderer(minecraftFontRenderer);
         return this;
      }

      public RenderTask.Builder withFontRenderer(FontRenderer fontRenderer) {
         this.task.getState().setFontRenderer(fontRenderer);
         return this;
      }

      public RenderTask.Builder withGpuBuffers() {
         this.task.getState().setGpuBuffer(new GpuBuffer());
         this.task.getState().setVertexArray(new VertexArray());
         return this;
      }

      public RenderTask.Builder withActiveRenderMatrices() {
         this.task.getState().setModelViewMatrix(MatrixUtils.readMatrix(ActiveRenderInfo.MODELVIEW.duplicate()));
         this.task.getState().setProjectionMatrix(MatrixUtils.readMatrix(ActiveRenderInfo.PROJECTION.duplicate()));
         return this;
      }

      public RenderTask.Builder retainGpuBuffer() {
         this.task.getState().setRetainGpuBuffer(true);
         return this;
      }

      public RenderTask.Builder withDrawMode(int drawMode) {
         this.task.getState().setDrawMode(drawMode);
         return this;
      }

      public RenderTask build() {
         this.task.prepare();
         return this.task;
      }
   }
}
