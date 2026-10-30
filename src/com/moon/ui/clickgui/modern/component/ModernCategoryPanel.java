package com.moon.ui.clickgui.modern.component;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.gl.StencilUtil;
import com.moon.ui.clickgui.AnimatedValue;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class ModernCategoryPanel extends ClassicComponent {
   private final AnimatedValue scrollAnimation = new AnimatedValue();
   private final List<ModernModuleButton> buttons = new ArrayList<>();
   private final ModuleCategory category;
   private int scrollOffset;

   public ModernCategoryPanel(ModuleCategory category, float x, float y, float width, float height) {
      super(x, y, width, height);
      this.category = category;
      float buttonY = this.getY() + 28.0F;

      for (Module module : Moon.getInstance().getModuleManager().getModulesByCategory(category)) {
         ModernModuleButton button = new ModernModuleButton(this, module, this.getX(), buttonY, this.getWidth(), 23.0F);
         button.init();
         this.buttons.add(button);
         buttonY += 23.0F;
      }

      this.scrollAnimation.set(0.0F);
      this.scrollOffset = 0;
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      this.setHeight(Math.min(scaledResolution.Method238() - 125, Math.min(550.0F, this.getTotalHeight() + 28.0F)));
   }

   public void rebuild() {
      this.buttons.clear();
      float buttonY = this.getY() + 28.0F;

      for (Module module : Moon.getInstance().getModuleManager().getModulesByCategory(this.category)) {
         ModernModuleButton button = new ModernModuleButton(this, module, this.getX(), buttonY, this.getWidth(), 23.0F);
         button.init();
         this.buttons.add(button);
         buttonY += 23.0F;
      }

      this.scrollAnimation.set(0.0F);
      this.scrollOffset = 0;
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      this.setHeight(Math.min(scaledResolution.Method238() - 125, Math.min(550.0F, this.getTotalHeight() + 28.0F)));
   }

   @Override
   public void renderBackground(boolean dark) {
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      renderTask.addCommand(RoundedRectangleCommand.of(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 13.0F, dark ? -872415232 : -1));
      renderTask.render();
   }

   @Override
   public void tick() {
      if (this.getLastX() != this.getX()
         || this.getLastY() != this.getY()
         || this.getLastWidth() != this.getWidth()
         || this.getLastHeight() != this.getHeight()) {
         this.getEffects().getKawaseRenderer().setNeedsRender(true);
         this.getEffects().getGaussianRenderer().requestRender();
         this.setLastX(this.getX());
         this.setLastY(this.getY());
         this.setLastWidth(this.getWidth());
         this.setLastHeight(this.getHeight());
      }

      super.tick();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      StencilUtil.beginStencil(false);
      RenderTask stencilTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RoundedRectangleCommand stencilCommand = RoundedRectangleCommand.of(
         this.getX() + 0.5F, this.getY() + 28.0F, this.getWidth() - 1.0F, this.getHeight() - 28.5F, 13.0F, -1
      );
      stencilCommand.setRoundTopLeft(false);
      stencilCommand.setRoundTopRight(false);
      stencilCommand.setPixelAligned(false);
      stencilTask.addCommand(stencilCommand);
      stencilTask.render();
      StencilUtil.renderIntoStencil(true);

      for (ModernModuleButton button : this.buttons) {
         button.render(mouseX, mouseY);
      }

      StencilUtil.endStencil();
      super.render(mouseX, mouseY);
   }

   @Override
   public void update(int mouseX, int mouseY) {
      StencilUtil.beginStencil(false);
      RenderTask stencilTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RoundedRectangleCommand stencilCommand = RoundedRectangleCommand.of(
         this.getX() + 0.5F, this.getY() + 28.0F, this.getWidth() - 1.0F, this.getHeight() - 28.5F, 13.0F, -1
      );
      stencilCommand.setRoundTopLeft(false);
      stencilCommand.setRoundTopRight(false);
      stencilCommand.setPixelAligned(false);
      stencilTask.addCommand(stencilCommand);
      stencilTask.render();
      StencilUtil.renderIntoStencil(true);

      for (ModernModuleButton button : this.buttons) {
         button.update(mouseX, mouseY);
      }

      StencilUtil.endStencil();
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      this.setHeight(Math.min(Math.max(scaledResolution.Method238() - 125, 200), Math.min(550.0F, this.getTotalHeight() + 28.0F)));
      super.update(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      int headerColor = -870704614;


      FontRenderer font = Moon.getInstance().getFontManager().getFont(FontType.INTER_SEMI_BOLD);
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      RenderTask headerTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(font).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      headerTask.addCommand(RoundedRectangleCommand.of(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 13.0F, headerColor));
      iconFont.draw(iconTask, this.category.getIcon(), 12.0F, this.getX() + 10.0F, this.getY() + 10.5F, -1140850689);
      font.draw(
         textTask, this.category.getName(), 12.0F, this.getX() + 10.0F + iconFont.getTextWidth(this.category.getIcon(), 12.0F) + 6.0F, this.getY() + 8.5F, -1140850689
      );
      headerTask.render();
      iconTask.render();
      textTask.render();
      StencilUtil.beginStencil(false);
      RenderTask stencilTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RoundedRectangleCommand stencilCommand = RoundedRectangleCommand.of(
         this.getX() + 0.5F, this.getY() + 28.0F, this.getWidth() - 1.0F, this.getHeight() - 28.5F, 13.0F, -1
      );
      stencilCommand.setRoundTopLeft(false);
      stencilCommand.setRoundTopRight(false);
      stencilCommand.setPixelAligned(false);
      stencilTask.addCommand(stencilCommand);
      stencilTask.render();
      StencilUtil.renderIntoStencil(true);
      this.positionButtons();

      for (ModernModuleButton button : this.buttons) {
         button.renderForeground(mouseX, mouseY);
      }

      StencilUtil.endStencil();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      outlineTask.addCommand(
         OutlineRoundedRectangleCommand.of(this.getX() - 0.5F, this.getY() - 0.5F, this.getWidth() + 1.0F, this.getHeight() + 1.0F, 13.0F, 0.25F, -10790053)
      );
      outlineTask.render();
      float totalHeight = this.getTotalHeight();
      boolean canScroll = totalHeight > this.getHeight() - 28.0F;
      if (this.isMouseOver(mouseX, mouseY, this.getX(), this.getY() + 28.0F, this.getWidth(), this.getHeight() - 28.0F) && canScroll) {
         this.handleMouseScroll();
      }

      if (canScroll) {
         this.scrollOffset = Math.max(this.scrollOffset, 0);
         this.scrollOffset = Math.min(this.scrollOffset, (int)(totalHeight - this.getHeight() + 28.0F));
      }

      this.scrollAnimation.animateTo(canScroll ? this.scrollOffset : 0.0F, 0.4F);
      if (this.scrollAnimation.get() > this.scrollOffset) {
         this.scrollAnimation.set(Math.min(this.scrollAnimation.get(), totalHeight - this.getHeight() + 28.0F));
      }

      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      for (ModernModuleButton moduleButton : this.buttons) {
         moduleButton.onMouseClick(mouseX, mouseY, button);
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      for (ModernModuleButton moduleButton : this.buttons) {
         moduleButton.onMouseRelease(mouseX, mouseY, button);
      }

      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      for (ModernModuleButton button : this.buttons) {
         button.onKeyTyped(charTyped, keyCode);
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   private float getTotalHeight() {
      float totalHeight = this.buttons.size() > 0 ? 1.0F : 0.0F;

      for (ModernModuleButton button : this.buttons) {
         totalHeight += button.getHeight();
      }

      return totalHeight;
   }

   private void handleMouseScroll() {
      int dWheel = Mouse.getDWheel();
      if (dWheel != 0) {
         ClickGUI clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
         if (clickGui.getModernScreen().getOverlay() == null) {
            boolean scrollingUp = dWheel > 0;
            this.scrollOffset += scrollingUp ? -22 : 22;
         }
      }
   }

   private void positionButtons() {
      float buttonY = this.getY() + 28.0F - this.scrollAnimation.get();

      for (ModernModuleButton button : this.buttons) {
         button.setX(this.getX());
         button.setY(buttonY);
         buttonY += button.getHeight();
      }
   }

   public AnimatedValue getScrollAnimation() {
      return this.scrollAnimation;
   }

   public List<ModernModuleButton> getButtons() {
      return this.buttons;
   }

   public ModuleCategory getCategory() {
      return this.category;
   }

   public int getScrollOffset() {
      return this.scrollOffset;
   }
}
