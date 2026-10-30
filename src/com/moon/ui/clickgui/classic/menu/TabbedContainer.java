package com.moon.ui.clickgui.classic.menu;

import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.theme.ColorScheme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabbedContainer {
   private final List<Tab> tabs = new ArrayList<>();
   private final AnimatedValue scrollOffset = new AnimatedValue();
   private final float x;
   private final float y;
   private final float width;
   private final float height;
   private Tab selectedTab;
   private int selectedIndex;

   public TabbedContainer(float x, float y, float width, float height, String selectedTabName, Tab... tabs) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.selectedTab = Arrays.stream(tabs).filter(tab -> tab.getName().equalsIgnoreCase(selectedTabName)).findFirst().orElse(tabs[0]);
      this.selectedIndex = this.selectedTab.getIndex();
      this.tabs.addAll(Arrays.asList(tabs));
      this.scrollOffset.set(this.selectedIndex * width);
   }

   public void render(ColorScheme colorScheme, int mouseX, int mouseY) {
      float targetOffset = -this.selectedIndex * this.width;
      this.scrollOffset.animateTo(targetOffset, 0.3F);
      float tabX = this.x + this.scrollOffset.get();

      for (Tab tab : this.tabs) {
         tab.setX(tabX);
         tab.setY(this.y);
         tab.setWidth(this.width);
         tab.setHeight(this.height);
         tab.setClipX(this.x);
         tab.setClipY(this.y);
         tab.setClipWidth(this.width);
         tab.setClipHeight(this.height);
         tabX += this.width;
      }

      for (Tab tab : this.tabs) {
         if (!(tab.getX() + tab.getWidth() - 1.0F < this.x) && !(tab.getX() + 1.0F > this.x + this.width)) {
            tab.render(colorScheme, mouseX, mouseY);
         }
      }
   }

   public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
      this.selectedTab.onMouseClick(mouseX, mouseY, mouseButton);
   }

   public void setSelectedTab(String tabName) {
      if (!this.selectedTab.getName().equals(tabName)) {
         Tab matchingTab = this.tabs.stream().filter(tab -> tab.getName().equalsIgnoreCase(tabName)).findFirst().orElse(null);
         if (matchingTab != null) {
            this.selectedTab = matchingTab;
            this.selectedIndex = this.selectedTab.getIndex();
         }
      }
   }

   public List<Tab> getTabs() {
      return this.tabs;
   }

   public AnimatedValue getScrollOffset() {
      return this.scrollOffset;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getHeight() {
      return this.width;
   }

   public float getWidth() {
      return this.height;
   }

   public Tab getSelectedTab() {
      return this.selectedTab;
   }

   public int getSelectedIndex() {
      return this.selectedIndex;
   }
}
