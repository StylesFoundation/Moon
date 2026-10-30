package com.moon.module.impl.visual.esp.renderer;

import com.moon.Moon;
import com.moon.module.impl.visual.ESP;
import com.moon.render.font.impl.MinecraftFontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.util.vector.Vector4f;

public class EspItemNameRenderer extends EspEntityRenderer {
   public EspItemNameRenderer(ESP esp) {
      super(esp);
   }

   @Override
   public void render(Entity entity) {
      MinecraftFontRenderer fontRenderer = Moon.getInstance().getBitFontManager().getDefaultRenderer();
      Vector4f bounds = entity.Method940();
      if (bounds != null) {
         float x = bounds.x;
         float y = bounds.y;
         float z = bounds.z;
         float w = bounds.w;
         float width = z - x;
         float height = w - y;
         if (this.getEsp().getItemName().getValue() && entity instanceof EntityPlayer player && player.getHeldItem() != null) {
            String itemName = player.getHeldItem().getDisplayName().toUpperCase().replaceAll("§[0-9]", "").replaceAll("§[a-z]", "");
            float textX = x + width / 2.0F - fontRenderer.getStringWidth(itemName) / 2.0F;
            float textY = y + height + this.padding * 2.0F + (this.getEsp().getArmorEnabled().getValue() ? 2.5F : 0.0F);
            fontRenderer.drawOutlinedString(this.textTask, itemName, textX, textY, 1.0F, -1);
         }
      }
   }

   @Override
   public boolean isEnabled(Entity entity) {
      return this.getEsp().getItemEnabled().getValue() && entity instanceof EntityPlayer;
   }
}
