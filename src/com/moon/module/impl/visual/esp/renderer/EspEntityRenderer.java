package com.moon.module.impl.visual.esp.renderer;

import com.moon.module.impl.visual.ESP;
import com.moon.render.RenderTask;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public abstract class EspEntityRenderer {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   private final ESP esp;
   protected RenderTask rectTask;
   protected RenderTask nameTask;
   protected RenderTask gradientTask;
   protected RenderTask textTask;
   protected float padding = 0.5F;

   public abstract void render(Entity entity);

   public abstract boolean isEnabled(Entity entity);

   public EspEntityRenderer setRectTask(RenderTask rectTask) {
      this.rectTask = rectTask;
      return this;
   }

   public EspEntityRenderer setNameTask(RenderTask nameTask) {
      this.nameTask = nameTask;
      return this;
   }

   public EspEntityRenderer setGradientTask(RenderTask gradientTask) {
      this.gradientTask = gradientTask;
      return this;
   }

   public EspEntityRenderer setTextTask(RenderTask textTask) {
      this.textTask = textTask;
      return this;
   }

   public EspEntityRenderer setPadding(float padding) {
      this.padding = padding;
      return this;
   }

   public ESP getEsp() {
      return this.esp;
   }

   public RenderTask getRectTask() {
      return this.rectTask;
   }

   public RenderTask getNameTask() {
      return this.nameTask;
   }

   public RenderTask getGradientTask() {
      return this.gradientTask;
   }

   public RenderTask getTextTask() {
      return this.textTask;
   }

   public float getPadding() {
      return this.padding;
   }

   public EspEntityRenderer(ESP esp) {
      this.esp = esp;
   }
}
