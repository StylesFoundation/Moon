package com.moon.handlers;

import com.moon.Moon;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.DraggableHudElement;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.Tuple;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class HudDragHandler {
   private final List<DraggableHudElement> draggableHudElements;
   private final HUD hud;
   private Effects effects;
   private DraggableHudElement draggedElement = null;
   private float dragOffsetX;
   private float dragOffsetY;

   @Subscribe
   public void onOverlayRender(OverlayRenderEvent event) {
      this.ensureEffectsLoaded();
      if (event.getSequence() == OverlayRenderEvent.Sequence.PRE) {
         boolean inChat = Minecraft.getMinecraft().currentScreen instanceof GuiChat;
         if (Mouse.isButtonDown(0) && this.draggedElement == null && inChat) {
            Tuple<Integer, Integer> mousePos = this.getScaledMousePosition();
            int mouseX = mousePos.getFirst();
            int mouseY = mousePos.getSecond();

            for (DraggableHudElement element : this.draggableHudElements) {
               if (this.isMouseOverElement(mouseX, mouseY, element.getX(), element.getY(), element.getWidth(), element.getHeight())) {
                  this.dragOffsetX = element.getX() - mouseX;
                  this.dragOffsetY = element.getY() - mouseY;
                  this.draggedElement = element;
                  break;
               }
            }
         } else if (!Mouse.isButtonDown(0)) {
            this.draggedElement = null;
            this.dragOffsetX = this.dragOffsetY = 0.0F;
         }

         if (this.draggedElement != null) {
            if (inChat) {
               Tuple<Integer, Integer> mousePos = this.getScaledMousePosition();
               int mouseX = mousePos.getFirst();
               int mouseY = mousePos.getSecond();
               this.draggedElement.setX(mouseX + this.dragOffsetX);
               this.draggedElement.setY(mouseY + this.dragOffsetY);
            }

            this.hud.setRebuildRequested(true);
            this.effects.getKawaseRenderer().setNeedsRender(true);
            this.effects.getGaussianRenderer().requestRender();
         }
      }
   }

   private void ensureEffectsLoaded() {
      if (this.effects == null) {
         this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }
   }

   private Tuple<Integer, Integer> getScaledMousePosition() {
      int mouseX = Mouse.getX();
      int mouseY = Display.getHeight() - Mouse.getY();
      float scale = ScaleHandler.getScaledResolution().getScaleFactor() * this.hud.getScale().getValue();
      mouseX = (int)(mouseX / scale);
      mouseY = (int)(mouseY / scale);
      return new Tuple<>(mouseX, mouseY);
   }

   private boolean isMouseOverElement(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public List<DraggableHudElement> getDraggableHudElements() {
      return this.draggableHudElements;
   }

   public HUD getHud() {
      return this.hud;
   }

   public Effects getEffects() {
      return this.effects;
   }

   public DraggableHudElement getDraggedElement() {
      return this.draggedElement;
   }

   public float getDragOffsetX() {
      return this.dragOffsetX;
   }

   public float getDragOffsetY() {
      return this.dragOffsetY;
   }

   public HudDragHandler(List<DraggableHudElement> draggableHudElements, HUD hud) {
      this.draggableHudElements = draggableHudElements;
      this.hud = hud;
   }
}
