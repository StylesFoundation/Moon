package com.moon.ui.clickgui.classic;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.handlers.ScaleHandler;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.impl.visual.Effects;
import com.moon.render.RenderTransformState;
import com.moon.ui.clickgui.classic.animation.AlphaAnimationMap;
import com.moon.ui.clickgui.classic.animation.ScaleAnimationMap;
import com.moon.ui.clickgui.classic.component.Button;
import com.moon.ui.clickgui.classic.component.CategoryPanel;
import com.moon.ui.clickgui.classic.component.Component;
import com.moon.ui.clickgui.classic.overlay.Overlay;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class ClassicClickGui extends GuiScreen {
   private final ClickGUI clickGui;
   private final List<Component> panels = new ArrayList<>();
   private final Map<Easing, Animation> alphaAnimations = new AlphaAnimationMap(this);
   private final Map<Easing, Animation> scaleAnimations = new ScaleAnimationMap(this);
   private Overlay overlay = null;
   private boolean closing = false;
   private Effects effectsModule = null;
   private CategoryPanel scriptsPanel;
   private int frameCount = 0;
   private Button menuButton;

   @Override
   public void onInitGui() {
      super.onInitGui();
      this.closing = false;
      this.frameCount = 0;
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      this.menuButton = new Button(4.5F, sr.Method238() - 27, 100.0F, 22.0F, "Client Menu", false, this.clickGui.getAccentColor());
   }

   public void setupPanels() {
      float panelX = 10.0F;

      for (ModuleCategory category : ModuleCategory.values()) {
         CategoryPanel panel = new CategoryPanel(category, panelX, 10.0F, 153.0F, 350.0F);
         panel.setClickGui(this.clickGui);
         panel.setColorScheme(ColorScheme.darkScheme(this.clickGui.getColor().getValue()));
         this.panels.add(panel);
         panel.init();
         panelX += 157.0F;
         if (category == ModuleCategory.SCRIPTS) {
            this.scriptsPanel = panel;
         }
      }
   }

   public void refreshColors() {
      this.panels.forEach(Component::updateColorScheme);
   }

   public void setOverlay(Overlay overlay) {
      this.overlay = overlay;
      if (overlay != null) {
         overlay.init();
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.frameCount++ == 1) {
         if (this.clickGui.getOpenAnimation().getValue()) {
            this.setAnimations(this.scaleAnimations.values(), 0.5F, false);
            this.setAnimations(this.alphaAnimations.values(), 0.0F, false);
         } else {
            this.setAnimations(this.scaleAnimations.values(), 1.0F, true);
            this.setAnimations(this.alphaAnimations.values(), 1.0F, true);
         }
      }

      if (this.effectsModule == null) {
         this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }

      if (!this.effectsModule.getShadow().getValue() || !this.effectsModule.isEnabled()) {
         this.animateAll(this.scaleAnimations.values(), 0.0F, 1.0F, !this.closing);
         this.animateAll(this.alphaAnimations.values(), 0.0F, 1.0F, !this.closing);
      }

      GlStateManager.enableBlend();
      this.menuButton.setColorScheme(this.clickGui.getAccentColor());
      this.menuButton.render(mouseX, mouseY);
      float scale = this.getAnimation(false).getValue();
      float alpha = this.getAnimation(true).getValue();
      float invScale = 1.0F - scale;
      float transX = invScale / 2.0F;
      float transY = invScale / 2.0F;
      RenderTransformState.setScale(scale, scale, 1.0F);
      RenderTransformState.setTranslation(transX, transY, 0.0F);
      RenderTransformState.setAlphaMultiplier(alpha);

      for (Component panel : this.panels) {
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         panel.render(mouseX, mouseY);
      }

      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetScale();
      RenderTransformState.resetTranslation();
      if (this.getAnimation(false).getValue() < 0.5 && this.closing) {
         this.mc.displayGuiScreen(null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }

      if (this.overlay != null) {
         this.overlay.render(mouseX, mouseY);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (this.overlay != null) {
         this.overlay.mouseClicked(mouseX, mouseY, button);
      } else {
         for (Component panel : this.panels) {
            panel.mouseClicked(mouseX, mouseY, button);
         }

         if (this.menuButton.isClicked(mouseX, mouseY, button)) {
            this.mc.displayGuiScreen(new ClientMenuScreen());
         }

         super.onMouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      if (this.overlay != null) {
         this.overlay.mouseReleased(mouseX, mouseY, button);
      } else {
         for (Component panel : this.panels) {
            panel.mouseReleased(mouseX, mouseY, button);
         }

         super.onMouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == 1) {
         if (this.overlay != null) {
            this.setOverlay(null);
         } else {
            if (this.clickGui.getCloseAnimation().getValue()) {
               if (!this.closing) {
                  this.closing = true;
                  this.setAnimations(this.alphaAnimations.values(), 1.0F, false);
                  this.setAnimations(this.scaleAnimations.values(), 1.0F, false);
                  Keyboard.enableRepeatEvents(false);
               }
            } else {
               this.mc.displayGuiScreen(null);
               if (this.mc.currentScreen == null) {
                  this.mc.setIngameFocus();
               }

               Keyboard.enableRepeatEvents(false);
            }
         }
      } else if (this.overlay != null) {
         this.overlay.charTyped(charTyped, keyCode);
      } else {
         for (Component panel : this.panels) {
            panel.keyTyped(charTyped, keyCode);
         }

         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }

   public void renderWithEffects(EffectRenderEvent event) {
      if (event.isGaussian()) {
         this.animateAll(this.scaleAnimations.values(), 0.0F, 1.0F, !this.closing);
         this.animateAll(this.alphaAnimations.values(), 0.0F, 1.0F, !this.closing);

         for (Component panel : this.panels) {
            float scale = this.getAnimation(false).getValue();
            float alpha = this.getAnimation(true).getValue();
            if (alpha > 0.5F) {
               float invScale = 1.0F - scale;
               float transX = invScale / 2.0F;
               float transY = invScale / 2.0F;
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.setTranslation(transX, transY, 0.0F);
               RenderTransformState.setAlphaMultiplier(alpha);
               panel.onEffectRender(event, this.getAnimation(false).getValue());
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.resetTranslation();
            }
         }
      }
   }

   public void updateBlur() {
      if (!this.getAnimation(true).isDone() || !this.getAnimation(false).isDone()) {
         if (this.effectsModule == null) {
            this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
         }

         this.effectsModule.getGaussianRenderer().requestRender();
      }

      Iterator iterator = this.panels.iterator();

      while (iterator.hasNext()) {
         ((Component)iterator.next()).update();
      }
   }

   public Animation getAnimation(boolean alpha) {
      if (this.closing) {
         return alpha
            ? this.alphaAnimations.get(this.clickGui.getCloseAnimationType().getValue().getEasing())
            : this.scaleAnimations.get(this.clickGui.getCloseAnimationType().getValue().getEasing());
      } else {
         return alpha
            ? this.alphaAnimations.get(this.clickGui.getOpenAnimationType().getValue().getEasing())
            : this.scaleAnimations.get(this.clickGui.getOpenAnimationType().getValue().getEasing());
      }
   }

   private void animateAll(Collection<Animation> animations, float start, float end, boolean forward) {
      Iterator iterator = animations.iterator();

      while (iterator.hasNext()) {
         ((Animation)iterator.next()).animate(start, end, forward);
      }
   }

   private void setAnimations(Collection<Animation> animations, float value, boolean finish) {
      for (Animation animation : animations) {
         if (finish) {
            animation.forceValue(value);
            animation.finish();
         } else {
            animation.setValue(value);
         }
      }
   }

   public ClickGUI getClickGui() {
      return this.clickGui;
   }

   public List<Component> getPanels() {
      return this.panels;
   }

   public Map<Easing, Animation> getAlphaAnimations() {
      return this.alphaAnimations;
   }

   public Map<Easing, Animation> getScaleAnimations() {
      return this.scaleAnimations;
   }

   public Overlay getOverlay() {
      return this.overlay;
   }

   public boolean isClosing() {
      return this.closing;
   }

   public Effects getEffectsModule() {
      return this.effectsModule;
   }

   public CategoryPanel getScriptsPanel() {
      return this.scriptsPanel;
   }

   public int getFrameCount() {
      return this.frameCount;
   }

   public Button getMenuButton() {
      return this.menuButton;
   }

   public ClassicClickGui(ClickGUI clickGui) {
      this.clickGui = clickGui;
   }
}
