package com.moon.module.impl.visual.hud.base;

public class DraggableComponentProperties {
   private final boolean disableBackground;
   private boolean disableTitle;
   private final String title;

   DraggableComponentProperties(boolean disableBackground, boolean disableTitle, String title) {
      this.disableBackground = disableBackground;
      this.disableTitle = disableTitle;
      this.title = title;
   }

   public static DraggableComponentPropertiesBuilder builder() {
      return new DraggableComponentPropertiesBuilder();
   }

   public boolean isBackgroundDisabled() {
      return this.disableBackground;
   }

   public boolean isTitleDisabled() {
      return this.disableTitle;
   }

   public String getTitle() {
      return this.title;
   }

   public DraggableComponentProperties setTitleDisabled(boolean disabled) {
      this.disableTitle = disabled;
      return this;
   }
}
