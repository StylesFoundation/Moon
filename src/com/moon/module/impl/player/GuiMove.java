package com.moon.module.impl.player;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import java.util.Arrays;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.lwjgl.input.Keyboard;

public class GuiMove extends Module {
   private final BooleanSetting allowInInventories = BooleanSetting.builder("allow-in-inventories").defaults("Allow in inventories", false).build();
   private final BooleanSetting cancelOpen = BooleanSetting.builder("cancel-open")
      .defaults("Cancel open packet", false)
      .visibleWhen(this.allowInInventories::getValue)
      .build();
   private final BooleanSetting watchdog = BooleanSetting.builder("watchdog").defaults("Watchdog", false).build();
   private final KeyBinding[] movementKeys = new KeyBinding[]{
      mc.gameSettings.keyBindForward,
      mc.gameSettings.keyBindLeft,
      mc.gameSettings.keyBindForward,
      mc.gameSettings.keyBindBack,
      mc.gameSettings.keyBindRight,
      mc.gameSettings.keyBindJump
   };

   public GuiMove(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onDisable() {
      Arrays.stream(this.movementKeys).forEach(keyBinding -> KeyBinding.setKeyBindState(keyBinding.getKeyCode(), false));
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.isSend()) {
         if (this.cancelOpen.getValue()
            && event.getPacket() instanceof C16PacketClientStatus statusPacket
            && statusPacket.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
            event.setCancelled(true);
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.BEFORE
         && !(mc.currentScreen instanceof GuiChat)
         && (!(mc.currentScreen instanceof GuiContainer) || this.allowInInventories.getValue())
         && mc.currentScreen != null) {
         Arrays.stream(this.movementKeys)
            .forEach(keyBinding -> KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode())));
         if (this.watchdog.getValue() && mc.currentScreen instanceof GuiContainer) {
            mc.thePlayer.motionX *= 0.55;
            mc.thePlayer.motionZ *= 0.55;
         }
      }
   }
}
