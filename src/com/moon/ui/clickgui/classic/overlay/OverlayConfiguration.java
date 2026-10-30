package com.moon.ui.clickgui.classic.overlay;

import com.moon.ui.clickgui.classic.ClassicClickGui;
import java.util.List;
import java.util.function.Supplier;

public class OverlayConfiguration {
   private final String title;
   private final String description;
   private final Supplier<Float> x;
   private final Supplier<Float> y;
   private final OverlayContent content;
   private final ClassicClickGui clickGuiScreen;
   private final List<OverlayButton> buttons;

   OverlayConfiguration(
      String title,
      String description,
      Supplier<Float> x,
      Supplier<Float> y,
      OverlayContent content,
      ClassicClickGui clickGuiScreen,
      List<OverlayButton> buttons
   ) {
      this.title = title;
      this.description = description;
      this.x = x;
      this.y = y;
      this.content = content;
      this.clickGuiScreen = clickGuiScreen;
      this.buttons = buttons;
   }

   public static OverlayConfigurationBuilder builder() {
      return new OverlayConfigurationBuilder();
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

   public OverlayContent getContent() {
      return this.content;
   }

   public ClassicClickGui getClickGuiScreen() {
      return this.clickGuiScreen;
   }

   public List<OverlayButton> getButtons() {
      return this.buttons;
   }
}
