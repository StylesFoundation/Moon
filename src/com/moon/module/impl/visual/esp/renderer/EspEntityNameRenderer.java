package com.moon.module.impl.visual.esp.renderer;

import com.moon.Moon;
import com.moon.module.impl.visual.ESP;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.storage.dataset.Friend;
import com.moon.storage.dataset.Target;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.util.vector.Vector4f;

public class EspEntityNameRenderer extends EspEntityRenderer {
   public EspEntityNameRenderer(ESP esp) {
      super(esp);
   }

   @Override
   public void render(Entity entity) {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.VERDANA);
      Vector4f bounds = entity.Method940();
      float x = bounds.x;
      float y = bounds.y;
      float z = bounds.z;
      float w = bounds.w;
      float width = z - x;
      float height = w - y;
      if (entity instanceof EntityLivingBase livingBase && livingBase.getName() != null) {
         String name = livingBase.getDisplayName().getUnformattedText();
         if (this.getEsp().getNameCustomColor().getValue()) {
            name = livingBase.getName();
         }

         if (livingBase instanceof EntityPlayer player) {
            Friend friend = Moon.getInstance().getFriendStorage().getFriend(player);
            Target target = Moon.getInstance().getTargetStorage().getTarget(player);
            if (friend != null) {
               name = friend.getAlias().isEmpty() ? "§d§l" + friend.getName() : "§d§l" + friend.getAlias();
            }

            if (target != null) {
               name = name + " §c§l[Target]";
            }
         }

         float textWidth = fontRenderer.getTextWidth(name, 5.25F);
         float textX = x + width / 2.0F - textWidth / 2.0F;
         float textY = y - fontRenderer.getTextHeight(name, 5.25F) - this.padding;
         fontRenderer.drawWithShadow(
            this.nameTask, name, 5.25F, textX, textY, this.getEsp().getNameCustomColor().getValue() ? this.getEsp().getNameColor().getValue() : -1
         );
      }
   }

   @Override
   public boolean isEnabled(Entity entity) {
      return this.getEsp().getNameEnabled().getValue() && entity instanceof EntityLivingBase;
   }
}
