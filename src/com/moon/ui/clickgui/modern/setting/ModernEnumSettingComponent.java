package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.LabelEnum;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ModernEnumSettingComponent extends ModernSettingComponent {
   private final Animation expandAnimation = new Animation(150L, Easing.EASE_IN_OUT_CUBIC);
   private final Map<Integer, Animation> valueAnimations = new HashMap<>();
   private final AnimatedValue selectionPosition = new AnimatedValue();
   private final AnimatedValue selectionAlpha = new AnimatedValue();
   private final EnumSetting<?> setting;
   private int hoveredIndex = -1;
   private int selectedIndex = -1;
   private boolean expanded;

   public ModernEnumSettingComponent(VisibilitySupplier visibilitySupplier, EnumSetting<?> setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;

      for (int i = 0; i < setting.getValues().length; i++) {
         this.valueAnimations.put(i, new Animation(200L, Easing.EASE_IN_OUT_CUBIC));
      }

      this.expandAnimation.finish();
   }

   @Override
   public void update(int mouseX, int mouseY) {
      super.update(mouseX, mouseY);
   }

   @Override
   public void render(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      float arrowAngle = -90.0F * this.expandAnimation.getValue();
      Vector2f arrowCenter = new Vector2f(renderX + renderWidth - 8.5F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F);
      Vector2f rightPoint = new Vector2f(arrowCenter.x + 2.5F, arrowCenter.y + 2.5F);
      Vector2f leftPoint = new Vector2f(arrowCenter.x - 2.5F, arrowCenter.y + 2.5F);
      Vector2f topPoint = new Vector2f(arrowCenter.x, arrowCenter.y - 2.5F);
      Vector2f rotatedTop = this.rotatePoint(arrowCenter, topPoint, 180.0F + arrowAngle);
      Vector2f rotatedLeft = this.rotatePoint(arrowCenter, leftPoint, 180.0F + arrowAngle);
      Vector2f rotatedRight = this.rotatePoint(arrowCenter, rightPoint, 180.0F + arrowAngle);
      triangleTask.addCommand(TriangleCommand.of(rotatedTop.x, rotatedTop.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, Color.BLACK.getRGB()));
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      super.render(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      RenderTask msdfTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F), renderWidth + 1.0F, 17.0F, 3.0F, 1342177280));
      this.renderTooltipIcon(renderX + renderWidth - 17.0F - 11.0F, renderY + 15.0F, mouseX, mouseY);
      RoundedRectangleCommand arrowBoxCommand = RoundedRectangleCommand.of(
         renderX + renderWidth - 17.0F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F), 17.0F, 17.0F, 3.0F, this.getClickGui().getColor().getValue()
      );
      arrowBoxCommand.setRoundTopLeft(false);
      arrowBoxCommand.setRoundBottomRight(false);
      arrowBoxCommand.setPixelAligned(false);
      roundedRectTask.addCommand(arrowBoxCommand);
      float arrowAngle = -90.0F * this.expandAnimation.getValue();
      Vector2f arrowCenter = new Vector2f(renderX + renderWidth - 8.5F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F);
      Vector2f rightPoint = new Vector2f(arrowCenter.x + 2.5F, arrowCenter.y + 2.5F);
      Vector2f leftPoint = new Vector2f(arrowCenter.x - 2.5F, arrowCenter.y + 2.5F);
      Vector2f topPoint = new Vector2f(arrowCenter.x, arrowCenter.y - 2.5F);
      Vector2f rotatedTop = this.rotatePoint(arrowCenter, topPoint, 180.0F + arrowAngle);
      Vector2f rotatedLeft = this.rotatePoint(arrowCenter, leftPoint, 180.0F + arrowAngle);
      Vector2f rotatedRight = this.rotatePoint(arrowCenter, rightPoint, 180.0F + arrowAngle);
      triangleTask.addCommand(TriangleCommand.of(rotatedTop.x, rotatedTop.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, -1));
      fontRenderer.draw(msdfTask, this.setting.getDisplayName(), 7.0F, renderX, renderY, -1);
      fontRenderer.draw(
         msdfTask,
         ((LabelEnum)((Enum)this.setting.getValue())).label(),
         7.0F,
         renderX + 4.0F,
         renderY + 1.5F + fontRenderer.getLineHeight(7.0F) + 8.5F - fontRenderer.getTextHeight(((LabelEnum)((Enum)this.setting.getValue())).label(), 7.0F) / 2.0F,
         -1
      );
      roundedRectTask.render();
      msdfTask.render();
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      this.renderValueList(mouseX, mouseY);
      float expandedHeight = 2.5F + this.setting.getValues().length * 17.0F;
      this.baseHeight = 31.5F + expandedHeight * this.expandAnimation.getValue();
      this.expandAnimation.animate(0.0F, 1.0F, this.expanded);
      super.renderForeground(mouseX, mouseY);
   }

   private void renderValueList(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float totalListHeight = 3.0F + this.setting.getValues().length * 17.0F;
      float animatedListHeight = totalListHeight * this.expandAnimation.getValue();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      FontRenderer iconRenderer = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      StencilClipState clipState = StencilClipState.of(renderX, renderY, renderWidth, this.baseHeight);
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconRenderer).build();
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY + 29.0F, renderWidth + 1.0F, animatedListHeight - 3.0F, 3.0F, 1342177280));
      float listY = renderY + 29.0F;
      boolean anyHovered = false;
      if (this.expanded || this.expandAnimation.getValue() > 0.0F) {
         for (int i = 0; i < this.setting.getValues().length; i++) {
            Animation animation = this.valueAnimations.get(i);
            LabelEnum labelEnum = (LabelEnum)this.setting.getValues()[i];
            String label = labelEnum.label();
            fontRenderer.draw(textTask, label, 7.0F, renderX + 4.0F, listY + i * 17.0F + 8.5F - fontRenderer.getTextHeight(label, 7.0F) / 2.0F + 0.5F, -1);
            iconRenderer.draw(
               iconTask,
               "\ue5ca",
               7.0F,
               renderX + renderWidth - 8.5F - 4.0F,
               listY + i * 17.0F + 8.5F - iconRenderer.getLineHeight(7.0F) / 2.0F + 1.0F,
               new RenderColor(this.getClickGui().getColor().getValue()).withAlphaFraction(animation.getValue()).getRGB()
            );
            if (labelEnum == this.setting.getValue() && this.hoveredIndex != i) {
               if (this.hoveredIndex != -1) {
                  this.valueAnimations.get(this.hoveredIndex).setValue(1.0F);
               }

               this.hoveredIndex = i;
               this.valueAnimations.get(i).setValue(0.0F);
            }

            if (animation.isDone() && animation.getValue() == 1.0F && this.hoveredIndex != i) {
               animation.setValue(1.0F);
            }

            if (this.isMouseOver(mouseX, mouseY, renderX, listY + i * 17.0F - 0.5F, renderWidth, 18.0F)) {
               if (this.selectedIndex == -1) {
                  this.selectionPosition.set(i);
               }

               this.selectedIndex = i;
               anyHovered = true;
            }
         }
      }

      if (!anyHovered) {
         this.selectedIndex = -1;
      }

      if (this.expanded) {
         roundedRectTask.addCommand(
            RoundedRectangleCommand.of(
               renderX - 0.5F,
               listY + this.selectionPosition.get() * 17.0F,
               renderWidth + 1.0F,
               17.0F,
               3.0F,
               new RenderColor(-12500671).withAlphaFraction(this.selectionAlpha.get() * 0.25F).getRGB()
            )
         );
      }

      if (this.selectedIndex != -1) {
         this.selectionPosition.animateTo(this.selectedIndex, 0.4F);
      }

      this.selectionAlpha.animateTo(this.selectedIndex != -1 ? 1.0F : 0.0F, 0.4F);
      roundedRectTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      iconTask.addRenderState(clipState);
      roundedRectTask.render();
      textTask.render();
      iconTask.render();

      for (Integer index : this.valueAnimations.keySet()) {
         Animation animation = this.valueAnimations.get(index);
         animation.animate(0.0F, 1.0F, this.setting.getValue().ordinal() == index);
      }
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      if (this.isMouseOver(mouseX, mouseY, renderX - 0.5F, renderY + 3.0F + fontRenderer.getLineHeight(7.0F), renderWidth + 1.0F, 17.0F)) {
         this.expandAnimation.setValue(this.expanded ? 1.0F : 0.0F);
         this.expanded = !this.expanded;
      }

      if (this.expanded) {
         float listY = renderY + 29.0F;

         for (int i = 0; i < this.setting.getValues().length; i++) {
            float itemY = listY + i * 17.0F;
            if (this.isMouseOver(mouseX, mouseY, renderX - 0.5F, itemY, renderWidth + 1.0F, 17.0F) && button == 0) {
               ((EnumSetting)this.setting).setValue((Enum)this.uncheckedCast(this.setting.getValues()[i]));
               break;
            }
         }
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public boolean isSettingVisible() {
      return this.setting.isVisible();
   }

   @Override
   public Setting getSetting() {
      return this.setting;
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
