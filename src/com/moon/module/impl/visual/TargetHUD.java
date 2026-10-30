package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.ScreenShaderEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.visual.targethud.TargetHUDDesign;
import com.moon.module.impl.visual.targethud.impl.BasicTargetHUD;
import com.moon.module.impl.visual.targethud.impl.MoonTargetHUD;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import java.awt.Color;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.Tuple;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class TargetHUD extends Module {
   private final EnumSetting<TargetHUD.Design> design = EnumSetting.builder("design", TargetHUD.Design.class).defaults("Design", TargetHUD.Design.MOON).build();
   private final BooleanSetting syncWithModuleList = BooleanSetting.builder("sync-with-module-list")
      .defaults("Sync with module-list", true)
      .visibleWhen(() -> this.design.getValue() == TargetHUD.Design.MOON)
      .build();
   private final ColorSetting moonColor = ColorSetting.builder("moon-color")
      .defaults("Color", Color.RED.getRGB())
      .visibleWhen(() -> this.design.getValue() == TargetHUD.Design.MOON && !this.syncWithModuleList.getValue())
      .build();
   private final FloatSetting x = FloatSetting.builder("x").defaults("X", 0.0F, 0.0F, 100000.0F, 1.0F).visibleWhen(() -> false).build();
   private final FloatSetting y = FloatSetting.builder("y").defaults("Y", 0.0F, 0.0F, 100000.0F, 1.0F).visibleWhen(() -> false).build();
   private Effects effects;
   private float dragOffsetX;
   private float dragOffsetY;
   private boolean hovered;
   private boolean dragging;

   public TargetHUD(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onOverlayRender(OverlayRenderEvent event) {
      if (event.getSequence() == OverlayRenderEvent.Sequence.POST) {
         this.resolveEffects();
         TargetHUDDesign currentDesign = this.getCurrentDesign();
         Tuple<Integer, Integer> mousePosition = this.getMousePosition();
         int mouseX = mousePosition.getFirst();
         int mouseY = mousePosition.getSecond();
         this.hovered = this.isInside(mouseX, mouseY, this.x.getValue(), this.y.getValue(), currentDesign.getX(), currentDesign.getY());
         if (!Mouse.isButtonDown(0)) {
            this.dragging = false;
         }

         if ((this.hovered || this.dragging) && Mouse.isButtonDown(0) && mc.currentScreen instanceof GuiChat) {
            if (!this.dragging) {
               this.dragOffsetX = this.x.getValue() - mouseX;
               this.dragOffsetY = this.y.getValue() - mouseY;
               this.dragging = true;
            }

            this.x.setValue(mouseX + this.dragOffsetX);
            this.y.setValue(mouseY + this.dragOffsetY);
            this.effects.getGaussianRenderer().requestRender();
         }

         currentDesign.onRender();
      }
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onEffectRender(EffectRenderEvent event) {
      this.getCurrentDesign().onShadowRender(event.isGaussian());
   }

   @Subscribe
   public void onScreenShader(ScreenShaderEvent event) {
      this.getCurrentDesign().onTick();
   }

   private TargetHUDDesign getCurrentDesign() {
      TargetHUDDesign renderer = this.design.getValue().getRenderer();
      renderer.setTargetHud(this);
      return renderer;
   }

   private boolean isInside(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   private void resolveEffects() {
      this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
   }

   private Tuple<Integer, Integer> getMousePosition() {
      int mouseX = Mouse.getX();
      int mouseY = Display.getHeight() - Mouse.getY();
      float scaleFactor = ScaleHandler.getScaledResolution().getScaleFactor();
      mouseX = (int)(mouseX / scaleFactor);
      mouseY = (int)(mouseY / scaleFactor);
      return new Tuple<>(mouseX, mouseY);
   }

   public EnumSetting<TargetHUD.Design> getDesign() {
      return this.design;
   }

   public BooleanSetting getSyncWithModuleList() {
      return this.syncWithModuleList;
   }

   public ColorSetting getMoonColor() {
      return this.moonColor;
   }

   public FloatSetting getXSetting() {
      return this.x;
   }

   public FloatSetting getYSetting() {
      return this.y;
   }

   public Effects getEffects() {
      return this.effects;
   }

   public float getDragOffsetX() {
      return this.dragOffsetX;
   }

   public float getDragOffsetY() {
      return this.dragOffsetY;
   }

   public boolean isHovered() {
      return this.hovered;
   }

   public boolean isDragging() {
      return this.dragging;
   }

   public enum Design implements LabelEnum {
      MOON("Moon", new MoonTargetHUD()),
      BASIC("Basic", new BasicTargetHUD());

      private final String label;
      private final TargetHUDDesign renderer;

      @Override
      public String label() {
         return this.label;
      }

      public TargetHUDDesign getRenderer() {
         return this.renderer;
      }

      Design(String label, TargetHUDDesign renderer) {
         this.label = label;
         this.renderer = renderer;
      }
   }
}
