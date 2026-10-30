package com.moon.handlers;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.util.math.MathUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class TimeSyncHandler {
   private static float timeSyncValue = 0.0F;
   private static long lastUpdateTime;
   private final List<Float> samples = new ArrayList<>();

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof S03PacketTimeUpdate) {
         float sum = 0.0F;

         for (Float sample : this.samples) {
            sum += sample;
         }

         timeSyncValue = (float)MathUtil.round(sum / this.samples.size(), 2);
         if (this.samples.size() >= 5) {
            this.samples.clear();
         }

         this.samples.add((float)(System.currentTimeMillis() - lastUpdateTime) / 50.0F);
         lastUpdateTime = System.currentTimeMillis();
      }
   }

   public static float getTimeSyncValue() {
      return timeSyncValue;
   }
}
