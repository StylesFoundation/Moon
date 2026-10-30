package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.BezierSetting;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ButtonSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.setting.impl.ModularListSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.module.setting.impl.StringSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.modern.render.ClickGuiRenderContext;
import com.moon.ui.clickgui.modern.tooltip.SettingTooltip;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ModernGroupSettingComponent extends ModernSettingComponent implements VisibilitySupplier {
   private final Animation expandAnimation = new Animation(150L, Easing.EASE_IN_OUT_CUBIC);
   private final List<ModernSettingComponent> components = new ArrayList<>();
   private final GroupSetting setting;
   private boolean expanded;

   public ModernGroupSettingComponent(VisibilitySupplier visibilitySupplier, GroupSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;
   }

   @Override
   public void init() {
      float currentY = this.getY() + 4.5F + 17.0F;

      for (Setting subSetting : this.setting.getValue()) {
         if (!subSetting.isGrouped() || subSetting.getGroup() == this.setting) {
            ModernSettingComponent component = null;
            switch (subSetting.getType()) {
               case BOOLEAN:
                  component = new ModernBooleanSettingComponent(this, (BooleanSetting)subSetting, this.getX(), currentY, this.getWidth(), 18.5F);
                  break;
               case INTEGER:
                  component = new ModernIntegerSettingComponent(this, (IntegerSetting)subSetting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case FLOAT:
                  component = new ModernFloatSettingComponent(this, (FloatSetting)subSetting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case MODE:
                  component = new ModernModeSettingComponent(this, (ModeSetting<?>)subSetting, this.getX(), currentY, this.getWidth(), 38.0F);
                  break;
               case ENUM:
                  component = new ModernEnumSettingComponent(this, (EnumSetting<?>)subSetting, this.getX(), currentY, this.getWidth(), 38.0F);
                  break;
               case GROUP:
                  component = new ModernGroupSettingComponent(this, (GroupSetting)subSetting, this.getX(), currentY, this.getWidth(), 23.5F);
                  break;
               case MULTIPLE_CHOICE:
                  component = new ModernMultipleChoiceSettingComponent(this, (MultipleChoiceSetting<?>)subSetting, this.getX(), currentY, this.getWidth(), 25.0F);
                  break;
               case COLOR:
                  component = new ModernColorSettingComponent(this, (ColorSetting)subSetting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case STRING:
                  component = new ModernStringSettingComponent(this, (StringSetting)subSetting, this.getX(), currentY, this.getWidth(), 31.5F);
                  break;
               case BUTTON:
                  component = new ModernButtonSettingComponent(this, (ButtonSetting)subSetting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case BEZIER:
                  component = new ModernBezierSettingComponent(this, (BezierSetting)subSetting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case MODULAR_LIST:
                  component = new ModernModularListSettingComponent(this, (ModularListSetting)subSetting, this.getX(), currentY, this.getWidth(), 38.0F);
            }

            if (component != null) {
               if (subSetting.getDescription() != null) {
                  component.setTooltip(new SettingTooltip(component));
               }

               component.init();
               component.setX(this.getX() + 4.0F);
               component.setWidth(this.getWidth() - 8.0F);
               this.components.add(component);
               currentY += component.getBaseHeight();
            }
         }
      }

      super.init();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      float scaledHeight = scaledResolution.Method238();

      for (ModernSettingComponent component : this.components) {
         if (component.getVisibilityProgress() > 0.0F || component.isSettingVisible() && component.getVisibilityProgress() > 0.0F) {
            float scale = ClickGuiRenderContext.scale;
            float offsetY = component.getY() / scaledHeight * (scale - component.getVisibilityProgress() * scale);
            if (!this.isAnimating()) {
               RenderTransformState.setAlphaMultiplier(component.getVisibilityProgress());
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, component.getVisibilityProgress() * scale, 1.0F);
               RenderTransformState.setTranslation(0.0F, offsetY, 0.0F);
            }

            component.render(mouseX, mouseY);
            if (!this.isAnimating()) {
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.resetTranslation();
            }
         }
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public void update(int mouseX, int mouseY) {
      float scaledHeight = ScaleHandler.getScaledResolution().Method238();
      this.updateComponentPositions();

      for (ModernSettingComponent component : this.components) {
         if (component.getVisibilityProgress() > 0.0F || component.isSettingVisible() && component.getVisibilityProgress() > 0.0F) {
            float scale = ClickGuiRenderContext.scale;
            float offsetY = component.getY() / scaledHeight * (scale - component.getVisibilityProgress() * scale);
            if (!this.isAnimating()) {
               RenderTransformState.setAlphaMultiplier(component.getVisibilityProgress());
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, component.getVisibilityProgress() * scale, 1.0F);
               RenderTransformState.setTranslation(0.0F, offsetY, 0.0F);
            }

            component.update(mouseX, mouseY);
            if (!this.isAnimating()) {
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.resetTranslation();
            }
         }

         component.updateVisibility();
      }

      this.baseHeight = 21.0F + this.getComponentsHeight();
      super.update(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      rectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY, renderWidth + 1.0F, 17.0F, 3.0F, 1342177280));
      this.renderTooltipIcon(renderX + renderWidth - 17.0F - 11.0F, renderY + 5.5F, mouseX, mouseY);
      RoundedRectangleCommand expandRectCommand = RoundedRectangleCommand.of(renderX + renderWidth - 17.0F, renderY, 17.0F, 17.0F, 3.0F, this.getClickGui().getColor().getValue());
      expandRectCommand.setRoundTopLeft(false);
      expandRectCommand.setRoundBottomRight(false);
      expandRectCommand.setPixelAligned(false);
      rectTask.addCommand(expandRectCommand);
      float arrowAngle = -90.0F * this.expandAnimation.getValue();
      Vector2f center = new Vector2f(renderX + renderWidth - 8.5F, renderY + 8.5F);
      Vector2f point1 = new Vector2f(center.x + 2.5F, center.y + 2.5F);
      Vector2f point2 = new Vector2f(center.x - 2.5F, center.y + 2.5F);
      Vector2f point3 = new Vector2f(center.x, center.y - 2.5F);
      Vector2f rotatedPoint3 = this.rotatePoint(center, point3, 180.0F + arrowAngle);
      Vector2f rotatedPoint2 = this.rotatePoint(center, point2, 180.0F + arrowAngle);
      Vector2f rotatedPoint1 = this.rotatePoint(center, point1, 180.0F + arrowAngle);
      triangleTask.addCommand(TriangleCommand.of(rotatedPoint3.x, rotatedPoint3.y, rotatedPoint2.x, rotatedPoint2.y, rotatedPoint1.x, rotatedPoint1.y, -1));
      fontRenderer.draw(
         textTask, this.setting.getDisplayName(), 7.0F, renderX + 4.0F, renderY + 0.5F + 8.5F - fontRenderer.getTextHeight(this.setting.getDisplayName(), 7.0F) / 2.0F, -1
      );
      rectTask.render();
      textTask.render();
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      float scaledHeight = ScaleHandler.getScaledResolution().Method238();
      this.updateComponentPositions();

      for (ModernSettingComponent component : this.components) {
         if (component.getVisibilityProgress() > 0.0F || component.isSettingVisible() && component.getVisibilityProgress() > 0.0F) {
            float scale = ClickGuiRenderContext.scale;
            float offsetY = component.getY() / scaledHeight * (scale - component.getVisibilityProgress() * scale);
            if (!this.isAnimating()) {
               RenderTransformState.setAlphaMultiplier(component.getVisibilityProgress());
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, component.getVisibilityProgress() * scale, 1.0F);
               RenderTransformState.setTranslation(0.0F, offsetY, 0.0F);
            }

            component.renderForeground(mouseX, mouseY);
            if (!this.isAnimating()) {
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.resetTranslation();
            }
         }
      }

      this.expandAnimation.animate(0.0F, 1.0F, this.expanded);
      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      if (this.isMouseOver(mouseX, mouseY, renderX - 0.5F, renderY + 1.5F, renderWidth + 1.0F, 17.0F)) {
         this.expandAnimation.setValue(this.expanded ? 1.0F : 0.0F);
         this.expanded = !this.expanded;
      }

      if (this.expanded) {
         for (ModernSettingComponent component : this.components) {
            if (component.isSettingVisible()) {
               component.onMouseClick(mouseX, mouseY, button);
            }
         }
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      if (this.expanded) {
         for (ModernSettingComponent component : this.components) {
            if (component.isSettingVisible()) {
               component.onMouseRelease(mouseX, mouseY, button);
            }
         }
      }

      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (this.expanded) {
         for (ModernSettingComponent component : this.components) {
            if (component.isSettingVisible()) {
               component.onKeyTyped(charTyped, keyCode);
            }
         }
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   @Override
   public boolean isVisible() {
      return this.expanded && this.isSupplierVisible();
   }

   @Override
   public boolean isSettingVisible() {
      return this.setting.isVisible();
   }

   @Override
   public Setting getSetting() {
      return this.setting;
   }

   private float getComponentsHeight() {
      float totalHeight = 0.0F;

      for (ModernSettingComponent component : this.components) {
         totalHeight += component.getHeight();
      }

      return Math.max(totalHeight, 0.0F);
   }

   private void updateComponentPositions() {
      float currentY = this.getY() + 4.5F + 17.0F;

      for (ModernSettingComponent component : this.components) {
         component.setX(this.getX() + 4.0F);
         component.setWidth(this.getWidth() - 8.0F);
         component.setY(currentY);
         currentY += component.getHeight();
      }
   }

   private Vector2f rotatePoint(Vector2f center, Vector2f point, float angle) {
      double radians = Math.toRadians(angle);
      float cos = (float)Math.cos(radians);
      float sin = (float)Math.sin(radians);
      return new Vector2f(
         center.x + (point.x - center.x) * cos - (point.y - center.y) * sin, center.y + (point.x - center.x) * sin + (point.y - center.y) * cos
      );
   }
}
