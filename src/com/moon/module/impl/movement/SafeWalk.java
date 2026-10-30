package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.SafeWalkEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
   private final BooleanSetting onlyIfNecessary = BooleanSetting.builder("only-if-necessary").defaults("Only if necessary", true).build();
   private final BooleanSetting sneak = BooleanSetting.builder("sneak").defaults("Sneak", true).build();

   public SafeWalk(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onSafeWalk(SafeWalkEvent event) {
      if (!this.sneak.getValue()) {
         if (!this.onlyIfNecessary.getValue()) {
            if (mc.thePlayer.onGround) {
               event.setCancelled(true);
            }
         } else {
            boolean hasBlockBelow = false;

            for (int yOffset = 0; yOffset <= 4; yOffset++) {
               BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - yOffset, mc.thePlayer.posZ);
               if (!mc.theWorld.isAirBlock(blockPos)) {
                  hasBlockBelow = true;
                  break;
               }
            }

            if (!hasBlockBelow && mc.thePlayer.onGround) {
               event.setCancelled(true);
            }
         }
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE && this.sneak.getValue() && mc.thePlayer != null && mc.theWorld != null) {
         if (!this.onlyIfNecessary.getValue()) {
            int sneakKeyCode = mc.gameSettings.keyBindSneak.getKeyCode();
            BlockPos belowPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
            KeyBinding.onTick(sneakKeyCode);
            boolean shouldSneak = mc.thePlayer.onGround && mc.theWorld.isAirBlock(belowPos)
               || Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && mc.currentScreen == null;
            KeyBinding.setKeyBindState(sneakKeyCode, shouldSneak);
            return;
         }

         boolean hasBlockBelow = false;

         for (int yOffset = 0; yOffset <= 4; yOffset++) {
            BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - yOffset, mc.thePlayer.posZ);
            if (!mc.theWorld.isAirBlock(blockPos)) {
               hasBlockBelow = true;
               break;
            }
         }

         int sneakKeyCode = mc.gameSettings.keyBindSneak.getKeyCode();
         KeyBinding.onTick(sneakKeyCode);
         boolean shouldSneak = !hasBlockBelow && mc.thePlayer.onGround || Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && mc.currentScreen == null;
         KeyBinding.setKeyBindState(sneakKeyCode, shouldSneak);
      }
   }
}
