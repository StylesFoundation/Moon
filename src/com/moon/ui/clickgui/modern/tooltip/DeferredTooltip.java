package com.moon.ui.clickgui.modern.tooltip;

public class DeferredTooltip {
   private final Tooltip tooltip;
   private final int x;
   private final int y;

   public void render() {
      this.tooltip.render(this.x, this.y);
   }

   public DeferredTooltip(Tooltip tooltip, int x, int y) {
      this.tooltip = tooltip;
      this.x = x;
      this.y = y;
   }
}
