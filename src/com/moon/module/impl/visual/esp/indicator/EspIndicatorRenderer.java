package com.moon.module.impl.visual.esp.indicator;

import com.moon.Moon;
import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.esp.indicator.impl.BlockingIndicator;
import com.moon.module.impl.visual.esp.indicator.impl.LowHealthIndicator;
import com.moon.module.impl.visual.esp.indicator.impl.SneakingIndicator;
import com.moon.module.impl.visual.esp.renderer.EspEntityRenderer;
import com.moon.render.font.impl.MinecraftFontRenderer;
import java.util.List;
import net.minecraft.entity.Entity;
import org.lwjgl.util.vector.Vector4f;

public class EspIndicatorRenderer extends EspEntityRenderer {
   private final List<EspIndicator> indicators = List.of(new BlockingIndicator(this), new SneakingIndicator(this), new LowHealthIndicator(this));

   public EspIndicatorRenderer(ESP esp) {
      super(esp);
   }

   @Override
   public void render(Entity entity) {
      MinecraftFontRenderer fontRenderer = Moon.getInstance().getBitFontManager().getDefaultRenderer();
      Vector4f bounds = entity.Method940();
      float minX = bounds.x;
      float minY = bounds.y;
      float maxX = bounds.z;
      float maxY = bounds.w;
      float width = maxX - minX;
      float height = maxY - minY;
      float drawX = minX + width + this.padding * 2.0F;
      float drawY = minY + this.padding;
      float yOffset = 0.0F;

      for (EspIndicator indicator : this.indicators) {
         if (indicator.applies(entity)) {
            String text = indicator.getText(entity);
            fontRenderer.drawOutlinedString(this.textTask, text, drawX, drawY + yOffset, 1.0F, -1);
            yOffset += fontRenderer.getLineHeight() + 2.0F;
         }
      }
   }

   @Override
   public boolean isEnabled(Entity entity) {
      return this.getEsp().getIndicatorEnabled().getValue();
   }
}
