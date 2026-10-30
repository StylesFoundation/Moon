package com.moon.module.impl.player.scaffold;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import net.minecraft.client.Minecraft;

public abstract class Tower {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();

   public abstract void onUpdate(UpdateEvent event);

   public abstract void onMove(MoveEvent event);

   public abstract void onEnable();

   protected boolean isJumpKeyDown() {
      return minecraft.gameSettings.keyBindJump.isKeyDown();
   }
}
