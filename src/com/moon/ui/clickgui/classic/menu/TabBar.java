package com.moon.ui.clickgui.classic.menu;

import com.moon.Moon;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.theme.ColorScheme;

public class TabBar {
   private final AnimatedValue indicatorPosition = new AnimatedValue();
   private final AnimatedValue secondaryIndicator = new AnimatedValue();
   private final String[] tabs;
   private String selectedTab;
   private float x;
   private float y;

   public TabBar(String[] tabs, String selectedTab) {
      this.tabs = tabs;
      this.selectedTab = selectedTab;
      this.indicatorPosition.set(-1337.0F);
   }

   public void render(ColorScheme colorScheme, int mouseX, int mouseY) {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      float barHeight = fontRenderer.getLineHeight(8.25F) + 14.0F;
      float barWidth = this.getTotalTextWidth(fontRenderer, 8.25F) + 12.0F * this.tabs.length;
      RenderTask bgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask selectedTextTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      bgTask.addCommand(
         RoundedRectangleCommand.of(this.x - barWidth / 2.0F, this.y - (barHeight - 1.0F) / 2.0F, barWidth, barHeight, barHeight / 2.0F - 1.0F, colorScheme.getBackground())
      );
      float startX = this.x - barWidth / 2.0F;
      float offsetX = 0.0F;

      for (String tab : this.tabs) {
         boolean isSelected = tab.equals(this.selectedTab);
         if (isSelected) {
            float tabTextWidth = fontRenderer.getTextWidth(tab, 8.25F);
            float targetIndicatorX = startX + 6.0F + offsetX + tabTextWidth / 2.0F;
            if (this.indicatorPosition.get() < 0.0F) {
               this.indicatorPosition.set(targetIndicatorX);
            }

            this.indicatorPosition.animateTo(targetIndicatorX, 0.4F);
            float indicatorX = this.indicatorPosition.get();
            float indicatorBoxX = indicatorX - tabTextWidth / 2.0F - 3.0F;
            float indicatorBoxY = this.y - (barHeight - 1.0F) / 2.0F + 2.5F;
            float indicatorBoxWidth = tabTextWidth + 6.0F;
            float indicatorBoxHeight = barHeight - 5.0F;
            bgTask.addCommand(RoundedRectangleCommand.of(indicatorBoxX, indicatorBoxY, indicatorBoxWidth, indicatorBoxHeight, indicatorBoxHeight / 2.0F - 1.0F, colorScheme.getPrimary()));
            selectedTextTask.addRenderState(StencilClipState.of(indicatorBoxX, indicatorBoxY, indicatorBoxWidth, indicatorBoxHeight));
         }

         fontRenderer.draw(textTask, tab, 8.25F, startX + offsetX + 6.0F, this.y - (barHeight - 1.0F) / 2.0F + 7.0F, colorScheme.getOnBackground());
         fontRenderer.draw(selectedTextTask, tab, 8.25F, startX + offsetX + 6.0F, this.y - (barHeight - 1.0F) / 2.0F + 7.0F, colorScheme.getOnPrimary());
         offsetX += fontRenderer.getTextWidth(tab, 8.25F) + 12.0F;
      }

      bgTask.render();
      textTask.render();
      selectedTextTask.render();
   }

   public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      float barHeight = fontRenderer.getLineHeight(8.25F) + 14.0F;
      float barWidth = this.getTotalTextWidth(fontRenderer, 8.25F) + 12.0F * this.tabs.length;
      float startX = this.x - barWidth / 2.0F;
      float offsetX = 0.0F;

      for (String tab : this.tabs) {
         float tabTextWidth = fontRenderer.getTextWidth(tab, 8.25F);
         float indicatorCenterX = startX + 6.0F + offsetX + tabTextWidth / 2.0F;
         float tabX = indicatorCenterX - tabTextWidth / 2.0F - 3.0F;
         float tabY = this.y - (barHeight - 1.0F) / 2.0F + 2.5F;
         float tabWidth = tabTextWidth + 6.0F;
         float tabHeight = barHeight - 5.0F;
         if (this.isMouseOver(mouseX, mouseY, tabX, tabY, tabWidth, tabHeight) && mouseButton == 0) {
            this.selectedTab = tab;
            break;
         }

         offsetX += fontRenderer.getTextWidth(tab, 8.25F) + 12.0F;
      }
   }

   private float getTotalTextWidth(FontRenderer fontRenderer, float fontSize) {
      float totalWidth = 0.0F;

      for (String tab : this.tabs) {
         totalWidth += fontRenderer.getTextWidth(tab, fontSize);
      }

      return totalWidth;
   }

   private boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public AnimatedValue getIndicatorPosition() {
      return this.indicatorPosition;
   }

   public AnimatedValue getSecondaryIndicator() {
      return this.secondaryIndicator;
   }

   public String[] getTabs() {
      return this.tabs;
   }

   public String getSelectedTab() {
      return this.selectedTab;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public TabBar setX(float value) {
      this.x = value;
      return this;
   }

   public TabBar setY(float value) {
      this.y = value;
      return this;
   }
}
