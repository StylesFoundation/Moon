package com.moon.ui.clickgui.classic.component;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.impl.visual.Effects;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.gl.StencilUtil;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.clickgui.classic.setting.SettingComponent;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.math.MathUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class CategoryPanel extends Component {
   private static Effects effectsModule = null;
   private final List<ModuleButton> moduleButtons = new ArrayList<>();
   private final ModuleCategory category;
   private final List<Module> modules;
   private final AnimatedValue scrollAnimation = new AnimatedValue();
   private int scrollOffset;

   public CategoryPanel(ModuleCategory category, float x, float y, float width, float height) {
      this.category = category;
      this.modules = Moon.getInstance().getModuleManager().getModulesByCategory(category);
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void init() {
      this.ensureClickGuiReference();
      this.moduleButtons.clear();
      float buttonY = this.getY() + 30.0F;

      for (Module module : this.modules) {
         ModuleButton button = new ModuleButton(module, this, this.getX() + 5.0F, buttonY, this.width - 10.0F, 22.0F);
         button.setClickGui(this.clickGui);
         button.setColorScheme(ColorScheme.darkScheme(this.clickGui.getColor().getValue()));
         this.moduleButtons.add(button);
         button.setCompactDesign(this.clickGui.getModuleDesignType().getValue() == ClickGUI.ModuleDesignType.ROUNDED);
         button.init();
         buttonY += button.getHeight();
      }

      this.moduleButtons.sort(Comparator.comparingDouble(moduleButton -> moduleButton.getModule().getDisplayName().charAt(0)));
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.ensureClickGuiReference();
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      this.updateModuleButtonPositions();
      this.renderBackground(true);
      this.renderHeader();
      StencilUtil.beginStencil(false);
      this.renderBackground(false);
      StencilUtil.renderIntoStencil(true);
      this.renderModuleButtons(mouseX, mouseY, false);
      StencilUtil.endStencil();
      this.renderOutline();
      this.renderModuleButtons(mouseX, mouseY, true);
      if (this.isMouseOver(mouseX, mouseY, this.x, this.y + 25.0F, this.width, this.height - 50.0F)) {
         this.handleMouseScroll();
      }

      boolean canScroll = this.getContentHeight() + 25.0F > sr.Method238() - 20;
      this.scrollOffset = MathHelper.clamp_int(this.scrollOffset, Math.min((int)(-this.getContentHeight() + this.getHeight() - 25.0F), 0), 0);
      this.scrollAnimation.animateTo(canScroll ? this.scrollOffset : 0.0F, 0.4F);
      super.render(mouseX, mouseY);
   }

   @Override
   public void onEffectRender(EffectRenderEvent event, float partialTicks) {
      this.ensureClickGuiReference();
      RenderTask shadowTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      shadowTask.addCommand(
         RoundedRectangleCommand.of(
            this.x, this.y, this.width, this.height + 0.5F, 10.0F, new RenderColor(this.colorScheme.getShadow()).withAlphaFraction(0.75F).getRGB()
         )
      );
      shadowTask.render();
   }

   @Override
   public void update() {
      this.ensureClickGuiReference();
      boolean heightChanged = false;

      for (ModuleButton button : this.moduleButtons) {
         if (button.hasHeightChanged()) {
            heightChanged = true;
         }
      }

      if (effectsModule == null) {
         effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }

      if (heightChanged || this.isDragging()) {
         effectsModule.getGaussianRenderer().requestRender();
      }
   }

   @Override
   public void mouseClicked(int mouseX, int mouseY, int button) {
      for (ModuleButton moduleButton : this.moduleButtons) {
         moduleButton.onMouseClick(mouseX, mouseY, button);
      }

      if (this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, 25.0F)) {
         this.setDragging(true);
         this.setDragOffsetX(this.x - mouseX);
         this.setDragOffsetY(this.y - mouseY);
      }

      super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(int mouseX, int mouseY, int button) {
      for (ModuleButton moduleButton : this.moduleButtons) {
         moduleButton.onMouseRelease(mouseX, mouseY, button);
      }

      this.setDragging(false);
      super.mouseReleased(mouseX, mouseY, button);
   }

   @Override
   public void keyTyped(char charTyped, int keyCode) {
      Iterator iterator = this.moduleButtons.iterator();

      while (iterator.hasNext()) {
         ((ModuleButton)iterator.next()).onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public void updateColorScheme() {
      this.moduleButtons.forEach(SettingComponent::updateColorScheme);
      super.updateColorScheme();
   }

   private void renderBackground(boolean outline) {
      this.ensureClickGuiReference();
      RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      if (outline) {
         task.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height + 0.5F, 10.0F, this.colorScheme.getBackground()));
      }

      RoundedRectangleCommand bodyRect = RoundedRectangleCommand.of(
         this.x, this.y + 25.0F, this.width, this.height - 25.0F, 10.0F, this.colorScheme.getBackground()
      );
      bodyRect.setPixelAligned(false);
      bodyRect.setRoundTopLeft(false);
      bodyRect.setRoundTopRight(false);
      task.addCommand(bodyRect);
      task.render();
   }

   private void renderOutline() {
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      outlineTask.addCommand(
         OutlineRoundedRectangleCommand.of(this.x - 1.5F, this.y - 1.5F, this.width + 3.0F, this.height + 3.0F, 10.0F, 1.0F, this.colorScheme.getBackground())
      );
      outlineTask.render();
   }

   private void renderHeader() {
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      RenderTask titleTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      iconFont.draw(iconTask, this.category.getIcon(), 14.0F, this.x + this.width - iconFont.getTextWidth(this.category.getIcon(), 14.0F) - 7.0F, this.y + 8.0F, -1, false);
      this.fontRenderer.draw(titleTask, this.category.getName(), 12.0F, this.x + 8.0F, this.y + 7.0F, -1, false);
      titleTask.render();
      iconTask.render();
   }

   private void updateModuleButtonPositions() {
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      float buttonX = this.getX() + 3.0F;
      float buttonY = this.getY() + 25.0F + this.scrollAnimation.get();
      float totalHeight = 0.0F;
      boolean rounded = this.clickGui.getModuleDesignType().getValue() == ClickGUI.ModuleDesignType.ROUNDED;
      int spacing = rounded ? 4 : 0;
      if (!rounded) {
         buttonX -= 3.0F;
      }

      for (ModuleButton button : this.moduleButtons) {
         button.setX(buttonX);
         button.setY(buttonY);
         button.setWidth(rounded ? this.width - 6.0F : this.width);
         button.setCompactDesign(rounded);
         button.setInstantLayout(MathUtil.round(this.scrollAnimation.get(), 2) != this.scrollOffset);
         buttonY += button.getHeight() + spacing;
         totalHeight += button.getHeight() + spacing;
      }

      this.setHeight(Math.min(26.0F + totalHeight, sr.Method238() - 20));
   }

   private void renderModuleButtons(int mouseX, int mouseY, boolean overlay) {
      for (ModuleButton button : this.moduleButtons) {
         if (overlay) {
            button.renderOverlay(mouseX, mouseY);
         } else {
            button.render(mouseX, mouseY);
         }
      }

      GlStateManager.enableBlend();
   }

   private void handleMouseScroll() {
      int dWheel = Mouse.getDWheel();
      if (dWheel != 0) {
         boolean up = dWheel > 0;
         this.scrollOffset += up ? 12 : -12;
      }
   }

   private float getContentHeight() {
      this.ensureClickGuiReference();
      float height = 0.0F;
      boolean rounded = this.clickGui.getModuleDesignType().getValue() == ClickGUI.ModuleDesignType.ROUNDED;

      for (ModuleButton button : this.moduleButtons) {
         height += button.getHeight() + (rounded ? 5 : 0);
      }

      return height;
   }

   public List<Module> getModules() {
      return this.modules;
   }

   public AnimatedValue getScrollAnimation() {
      return this.scrollAnimation;
   }
}
