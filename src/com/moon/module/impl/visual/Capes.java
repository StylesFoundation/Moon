package com.moon.module.impl.visual;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moon.Moon;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.entity.player.EntityPlayer;

public class Capes extends Module {
   private final BooleanSetting showOtherUsers = BooleanSetting.builder("show-other-users").defaults("Show other users", true).build();
   private final Cache<EntityPlayer, Boolean> verifiedUsersCache = CacheBuilder.newBuilder().expireAfterWrite(45L, TimeUnit.SECONDS).build();

   public Capes(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public boolean shouldRenderCape(EntityPlayer player) {
       return player == mc.thePlayer;
   }
}
