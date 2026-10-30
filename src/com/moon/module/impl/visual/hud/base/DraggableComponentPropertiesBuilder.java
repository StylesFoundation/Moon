package com.moon.module.impl.visual.hud.base;

public class DraggableComponentPropertiesBuilder {
   private boolean disableBackground;
   private boolean disableTitle;
   private String title;

   DraggableComponentPropertiesBuilder() {
   }

   public DraggableComponentPropertiesBuilder disableBackground(boolean disableBackground) {
      this.disableBackground = disableBackground;
      return this;
   }

   public DraggableComponentPropertiesBuilder disableTitle(boolean disableTitle) {
      this.disableTitle = disableTitle;
      return this;
   }

   public DraggableComponentPropertiesBuilder title(String title) {
      this.title = title;
      return this;
   }

   public DraggableComponentProperties build() {
      return new DraggableComponentProperties(this.disableBackground, this.disableTitle, this.title);
   }

   @Override
   public String toString() {
      String title = this.title;
      boolean disableTitle = this.disableTitle;
      boolean disableBackground = this.disableBackground;
      return "DraggableComponentProperties.DraggableComponentPropertiesBuilder(disableBackground=" + disableBackground + ", disableTitle=" + disableTitle + ", title=" + title + ")";
   }
}
