package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.LabelEnum;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ModernMultipleChoiceSettingComponent extends ModernSettingComponent {
   private final Animation expandAnimation = new Animation(150L, Easing.EASE_IN_OUT_CUBIC);
   private final Map<Integer, Animation> valueAnimations = new HashMap<>();
   private final Map<Integer, Boolean> checkedStates = new HashMap<>();
   private final AnimatedValue selectionPosition = new AnimatedValue();
   private final AnimatedValue selectionAlpha = new AnimatedValue();
   private final MultipleChoiceSetting<?> setting;
   private int hoveredIndex = -1;
   private int selectedIndex = -1;
   private boolean expanded;

   public ModernMultipleChoiceSettingComponent(
      VisibilitySupplier visibilitySupplier, MultipleChoiceSetting<?> setting, float x, float y, float width, float height
   ) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;

      for (int i = 0; i < setting.getValues().length; i++) {
         this.valueAnimations.put(i, new Animation(200L, Easing.EASE_IN_OUT_CUBIC));
         this.checkedStates.put(i, false);
      }

      this.expandAnimation.finish();
   }

   @Override
   public void update(int mouseX, int mouseY) {
      super.update(mouseX, mouseY);
   }

   @Override
   public void render(int mouseX, int mouseY) {
      float x = this.getX() + 8.0F;
      float y = this.getY() + 4.5F;
      float width = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      float rotationAngle = -90.0F * this.expandAnimation.getValue();
      Vector2f center = new Vector2f(x + width - 8.5F, y + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F);
      Vector2f p1 = new Vector2f(center.x + 2.5F, center.y + 2.5F);
      Vector2f p2 = new Vector2f(center.x - 2.5F, center.y + 2.5F);
      Vector2f p3 = new Vector2f(center.x, center.y - 2.5F);
      Vector2f rp1 = this.rotatePoint(center, p3, 180.0F + rotationAngle);
      Vector2f rp2 = this.rotatePoint(center, p2, 180.0F + rotationAngle);
      Vector2f rp3 = this.rotatePoint(center, p1, 180.0F + rotationAngle);
      triangleTask.addCommand(TriangleCommand.of(rp1.x, rp1.y, rp2.x, rp2.y, rp3.x, rp3.y, Color.BLACK.getRGB()));
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      super.render(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float x = this.getX() + 8.0F;
      float y = this.getY() + 4.5F;
      float width = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      rectTask.addCommand(RoundedRectangleCommand.of(x - 0.5F, y + 1.0F + fontRenderer.getLineHeight(7.0F), width + 1.0F, 17.0F, 3.0F, 1342177280));
      this.renderTooltipIcon(x + width - 17.0F - 11.0F, y + 5.5F, mouseX, mouseY);
      RoundedRectangleCommand arrowBg = RoundedRectangleCommand.of(
         x + width - 17.0F, y + 1.0F + fontRenderer.getLineHeight(7.0F), 17.0F, 17.0F, 3.0F, this.getClickGui().getColor().getValue()
      );
      arrowBg.setRoundTopLeft(false);
      arrowBg.setRoundBottomRight(false);
      arrowBg.setPixelAligned(false);
      rectTask.addCommand(arrowBg);
      float rotationAngle = -90.0F * this.expandAnimation.getValue();
      Vector2f center = new Vector2f(x + width - 8.5F, y + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F);
      Vector2f p1 = new Vector2f(center.x + 2.5F, center.y + 2.5F);
      Vector2f p2 = new Vector2f(center.x - 2.5F, center.y + 2.5F);
      Vector2f p3 = new Vector2f(center.x, center.y - 2.5F);
      Vector2f rp1 = this.rotatePoint(center, p3, 180.0F + rotationAngle);
      Vector2f rp2 = this.rotatePoint(center, p2, 180.0F + rotationAngle);
      Vector2f rp3 = this.rotatePoint(center, p1, 180.0F + rotationAngle);
      triangleTask.addCommand(TriangleCommand.of(rp1.x, rp1.y, rp2.x, rp2.y, rp3.x, rp3.y, -1));
      fontRenderer.draw(textTask, this.setting.getDisplayName(), 7.0F, x, y, -1);
      LabelEnum[] values = (LabelEnum[])this.setting.getValues();
      ArrayList<String> selectedLabels = new ArrayList<>();

      for (LabelEnum val : values) {
         if (((MultipleChoiceSetting)this.setting).hasChecked(this.uncheckedCast(val))) {
            selectedLabels.add(val.label());
         }
      }

      String joined = String.join(", ", selectedLabels);
      String trimmed = fontRenderer.trimToWidth(joined, Math.round(width - 17.0F - 6.0F - fontRenderer.getTextWidth("...", 7.0F)), 7.0F, false);
      if (!trimmed.equals(joined)) {
         trimmed = trimmed + "...";
      }

      if (trimmed.isEmpty()) {
         trimmed = "None selected";
      }

      fontRenderer.draw(textTask, trimmed, 7.0F, x + 4.0F, y + 1.5F + fontRenderer.getLineHeight(7.0F) + 8.5F - fontRenderer.getTextHeight(trimmed, 7.0F) / 2.0F, -1);
      rectTask.render();
      textTask.render();
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      this.renderValueList(mouseX, mouseY);
      this.expandAnimation.animate(0.0F, 1.0F, this.expanded);
      float contentHeight = 2.5F + this.setting.getValues().length * 17.0F;
      this.baseHeight = 31.5F + contentHeight * this.expandAnimation.getValue();
      super.renderForeground(mouseX, mouseY);
   }

   private void renderValueList(int mouseX, int mouseY) {
      float x = this.getX() + 8.0F;
      float y = this.getY() + 4.5F;
      float width = this.getWidth() - 16.0F;
      float listTotalHeight = 3.0F + this.setting.getValues().length * 17.0F;
      float listCurrentHeight = listTotalHeight * this.expandAnimation.getValue();
      FontRenderer textFontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      FontRenderer iconFontRenderer = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      StencilClipState clipState = StencilClipState.of(x, y, width, this.baseHeight);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(textFontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFontRenderer).build();
      rectTask.addCommand(RoundedRectangleCommand.of(x - 0.5F, y + 29.0F, width + 1.0F, listCurrentHeight - 3.0F, 3.0F, 1342177280));
      float listY = y + 29.0F;
      boolean anyHovered = false;
      if (this.expanded || this.expandAnimation.getValue() > 0.0F) {
         for (int i = 0; i < this.setting.getValues().length; i++) {
            Animation anim = this.valueAnimations.get(i);
            String label = ((LabelEnum)this.setting.getValues()[i]).label();
            textFontRenderer.draw(textTask, label, 7.0F, x + 4.0F, listY + i * 17.0F + 8.5F - textFontRenderer.getTextHeight(label, 7.0F) / 2.0F + 0.5F, -1);
            iconFontRenderer.draw(
               iconTask,
               "\ue5ca",
               7.0F,
               x + width - 8.5F - 4.0F,
               listY + i * 17.0F + 8.5F - iconFontRenderer.getLineHeight(7.0F) / 2.0F + 1.0F,
               new RenderColor(this.getClickGui().getColor().getValue()).withAlphaFraction(anim.getValue()).getRGB()
            );
            boolean checked = this.checkedStates.get(i);
            Enum val = this.setting.getValues()[i];
            boolean actuallyChecked = ((MultipleChoiceSetting)this.setting).hasChecked(this.uncheckedCast(val));
            if (checked != actuallyChecked) {
               anim.setValue(checked ? 1.0F : 0.0F);
               this.checkedStates.put(i, actuallyChecked);
            }

            if (this.isMouseOver(mouseX, mouseY, x, listY + i * 17.0F - 0.5F, width, 18.0F)) {
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
         rectTask.addCommand(
            RoundedRectangleCommand.of(
               x - 0.5F,
               listY + this.selectionPosition.get() * 17.0F,
               width + 1.0F,
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
      rectTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      iconTask.addRenderState(clipState);
      rectTask.render();
      textTask.render();
      iconTask.render();

      for (Integer index : this.valueAnimations.keySet()) {
         Enum value = this.setting.getValues()[index];
         this.valueAnimations.get(index).animate(0.0F, 1.0F, ((MultipleChoiceSetting)this.setting).hasChecked(this.uncheckedCast(value)));
      }
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float x = this.getX() + 8.0F;
      float y = this.getY() + 4.5F;
      float width = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      if (this.isMouseOver(mouseX, mouseY, x - 0.5F, y + 3.0F + fontRenderer.getLineHeight(7.0F), width + 1.0F, 17.0F)) {
         this.expandAnimation.setValue(this.expanded ? 1.0F : 0.0F);
         this.expanded = !this.expanded;
      }

      if (this.expanded) {
         float listY = y + 29.0F;

         for (int i = 0; i < this.setting.getValues().length; i++) {
            float itemY = listY + i * 17.0F;
            if (this.isMouseOver(mouseX, mouseY, x - 0.5F, itemY, width + 1.0F, 17.0F) && button == 0) {
               ((MultipleChoiceSetting)this.setting).toggle(this.uncheckedCast(this.setting.getValues()[i]));
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
      double rad = Math.toRadians(angle);
      float cos = (float)Math.cos(rad);
      float sin = (float)Math.sin(rad);
      return new Vector2f(
         center.x + (point.x - center.x) * cos - (point.y - center.y) * sin, center.y + (point.x - center.x) * sin + (point.y - center.y) * cos
      );
   }
}
