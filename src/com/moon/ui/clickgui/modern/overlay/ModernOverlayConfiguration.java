package com.moon.ui.clickgui.modern.overlay;

import com.moon.ui.clickgui.modern.ModernClickGui;
import java.util.List;
import java.util.function.Supplier;

public class ModernOverlayConfiguration {
   private final String title;
   private final String description;
   private final Supplier<Float> x;
   private final Supplier<Float> y;
   private final ModernClickGui clickGuiScreen;
   private final ClassicOverlayContent content;
   private final List<ClassicOverlayButton> buttons;

   ModernOverlayConfiguration(
      String title,
      String description,
      Supplier<Float> x,
      Supplier<Float> y,
      ModernClickGui clickGuiScreen,
      ClassicOverlayContent content,
      List<ClassicOverlayButton> buttons
   ) {
      this.title = title;
      this.description = description;
      this.x = x;
      this.y = y;
      this.clickGuiScreen = clickGuiScreen;
      this.content = content;
      this.buttons = buttons;
   }

   public static ModernOverlayConfigurationBuilder builder() {
      return new ModernOverlayConfigurationBuilder();
   }

   public String getTitle() {
      return this.title;
   }

   public String getDescription() {
      return this.description;
   }

   public Supplier<Float> getX() {
      return this.x;
   }

   public Supplier<Float> getY() {
      return this.y;
   }

   public ModernClickGui getClickGuiScreen() {
      return this.clickGuiScreen;
   }

   public ClassicOverlayContent getContent() {
      return this.content;
   }

   public List<ClassicOverlayButton> getButtons() {
      return this.buttons;
   }
}
