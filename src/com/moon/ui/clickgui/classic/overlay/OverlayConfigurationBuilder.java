package com.moon.ui.clickgui.classic.overlay;

import com.moon.ui.clickgui.classic.ClassicClickGui;
import java.util.List;
import java.util.function.Supplier;

public class OverlayConfigurationBuilder {
   private String title;
   private String description;
   private Supplier<Float> x;
   private Supplier<Float> y;
   private OverlayContent content;
   private ClassicClickGui clickGuiScreen;
   private List<OverlayButton> buttons;

   OverlayConfigurationBuilder() {
   }

   public OverlayConfigurationBuilder setTitle(String title) {
      this.title = title;
      return this;
   }

   public OverlayConfigurationBuilder setDescription(String description) {
      this.description = description;
      return this;
   }

   public OverlayConfigurationBuilder setX(Supplier<Float> x) {
      this.x = x;
      return this;
   }

   public OverlayConfigurationBuilder setY(Supplier<Float> y) {
      this.y = y;
      return this;
   }

   public OverlayConfigurationBuilder setContent(OverlayContent content) {
      this.content = content;
      return this;
   }

   public OverlayConfigurationBuilder setClickGuiScreen(ClassicClickGui clickGuiScreen) {
      this.clickGuiScreen = clickGuiScreen;
      return this;
   }

   public OverlayConfigurationBuilder setButtons(List<OverlayButton> buttons) {
      this.buttons = buttons;
      return this;
   }

   public OverlayConfiguration build() {
      return new OverlayConfiguration(this.title, this.description, this.x, this.y, this.content, this.clickGuiScreen, this.buttons);
   }

   @Override
   public String toString() {
      List buttons = this.buttons;
      ClassicClickGui clickGuiScreen = this.clickGuiScreen;
      OverlayContent content = this.content;
      Supplier y = this.y;
      Supplier x = this.x;
      String description = this.description;
      String title = this.title;
      return "OverlayConfiguration.OverlayConfigurationBuilder(title="
         + title
         + ", description="
         + description
         + ", x="
         + x
         + ", y="
         + y
         + ", content="
         + content
         + ", clickGuiScreen="
         + clickGuiScreen
         + ", buttons="
         + buttons
         + ")";
   }
}
