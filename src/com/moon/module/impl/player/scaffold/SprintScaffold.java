package com.moon.module.impl.player.scaffold;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.player.ScaffoldWalk;
import net.minecraft.client.Minecraft;

public abstract class SprintScaffold {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();
   protected ScaffoldWalk scaffoldWalk;

   public abstract void onUpdate(UpdateEvent event);

   public abstract void onMove(MoveEvent event);

   public abstract void onPacket(PacketEvent event);

   public abstract void onUpdateState();

   public abstract void onDisable();

   public abstract void onEnable();

   public SprintScaffold setScaffoldWalk(ScaffoldWalk scaffoldWalk) {
      this.scaffoldWalk = scaffoldWalk;
      return this;
   }
}
