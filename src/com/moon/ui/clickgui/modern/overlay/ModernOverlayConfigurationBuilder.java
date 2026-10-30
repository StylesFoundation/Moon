package com.moon.ui.clickgui.modern.overlay;

import com.moon.ui.clickgui.modern.ModernClickGui;
import java.util.List;
import java.util.function.Supplier;

public class ModernOverlayConfigurationBuilder {
   private String title;
   private String description;
   private Supplier<Float> x;
   private Supplier<Float> y;
   private ModernClickGui clickGuiScreen;
   private ClassicOverlayContent content;
   private List<ClassicOverlayButton> buttons;

   ModernOverlayConfigurationBuilder() {
   }

   public ModernOverlayConfigurationBuilder setTitle(String title) {
      this.title = title;
      return this;
   }

   public ModernOverlayConfigurationBuilder setDescription(String description) {
      this.description = description;
      return this;
   }

   public ModernOverlayConfigurationBuilder setX(Supplier<Float> x) {
      this.x = x;
      return this;
   }

   public ModernOverlayConfigurationBuilder setY(Supplier<Float> y) {
      this.y = y;
      return this;
   }

   public ModernOverlayConfigurationBuilder setClickGuiScreen(ModernClickGui clickGuiScreen) {
      this.clickGuiScreen = clickGuiScreen;
      return this;
   }

   public ModernOverlayConfigurationBuilder setContent(ClassicOverlayContent content) {
      this.content = content;
      return this;
   }

   public ModernOverlayConfigurationBuilder setButtons(List<ClassicOverlayButton> buttons) {
      this.buttons = buttons;
      return this;
   }

   public ModernOverlayConfiguration build() {
      return new ModernOverlayConfiguration(this.title, this.description, this.x, this.y, this.clickGuiScreen, this.content, this.buttons);
   }

   @Override
   public String toString() {
      List buttons = this.buttons;
      ClassicOverlayContent content = this.content;
      ModernClickGui clickGuiScreen = this.clickGuiScreen;
      Supplier y = this.y;
      Supplier x = this.x;
      String description = this.description;
      String title = this.title;
      return "ModernOverlayConfiguration.ModernOverlayConfigurationBuilder(title="
         + title
         + ", description="
         + description
         + ", x="
         + x
         + ", y="
         + y
         + ", clickGuiScreen="
         + clickGuiScreen
         + ", content="
         + content
         + ", buttons="
         + buttons
         + ")";
   }
}
