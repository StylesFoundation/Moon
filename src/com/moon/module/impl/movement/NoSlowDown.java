package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.SlowdownEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.noslowdown.InvalidNoSlowDown;
import com.moon.module.impl.movement.noslowdown.NCPNoSlowDown;
import com.moon.module.impl.movement.noslowdown.SpoofNoSlowDown;
import com.moon.module.impl.movement.noslowdown.UpdatedNCPNoSlowDown;
import com.moon.module.impl.movement.noslowdown.VanillaNoSlowDown;
import com.moon.module.impl.movement.noslowdown.WatchdogNoSlowDown;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;

public class NoSlowDown extends Module {
   public final MultipleChoiceSetting<NoSlowDown.Features> features = MultipleChoiceSetting.builder("features", NoSlowDown.Features.class)
      .defaults(
         "Features",
         NoSlowDown.Features.SNEAKING,
         NoSlowDown.Features.SWORDS,
         NoSlowDown.Features.CONSUMABLE,
         NoSlowDown.Features.SPRINTING,
         NoSlowDown.Features.BLOCKS
      )
      .build();
   public final ModeSetting<NoSlowDown.Mode> bypassMode = ModeSetting.builder("bypass-mode", NoSlowDown.Mode.class, this)
      .defaults("Bypass Mode", NoSlowDown.Mode.VANILLA)
      .build();
   public final BooleanSetting reBlock = BooleanSetting.builder("re-block")
      .defaults("Re-block", false)
      .visibleWhen(() -> this.bypassMode.getValue() == NoSlowDown.Mode.SPOOF)
      .build();

   public NoSlowDown(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.POST) {
         this.setSuffix(this.bypassMode.getValue().label());
      }
   }

   @Subscribe
   public void onSlowdown(SlowdownEvent event) {
      switch (event.getType()) {
         case ITEM:
            if (mc.thePlayer.getHeldItem() == null) {
               return;
            }

            Item heldItem = mc.thePlayer.getHeldItem().getItem();
            if (heldItem instanceof ItemSword) {
               event.setCancelled(this.features.hasChecked(NoSlowDown.Features.SWORDS));
            } else if (heldItem instanceof ItemBow) {
               event.setCancelled(this.features.hasChecked(NoSlowDown.Features.BOW));
            } else {
               event.setCancelled(this.features.hasChecked(NoSlowDown.Features.CONSUMABLE));
            }

            if (mc.gameSettings.keyBindSprint.isKeyDown() && mc.thePlayer.movementInput.moveForward > 0.0F) {
               mc.thePlayer.setSprinting(true);
            }
            break;
         case SNEAKING:
            if (this.features.hasChecked(NoSlowDown.Features.SNEAKING)) {
               event.setCancelled(true);
            }
            break;
         case SPRINTING:
            if (this.features.hasChecked(NoSlowDown.Features.SPRINTING)) {
               event.setCancelled(true);
            }
            break;
         case BLOCKS:
            if (this.features.hasChecked(NoSlowDown.Features.BLOCKS)) {
               event.setCancelled(true);
            }
      }
   }

   public enum Features implements LabelEnum {
      SWORDS("Swords"),
      CONSUMABLE("Consumable"),
      BOW("Bow"),
      SNEAKING("Sneaking"),
      SPRINTING("Sprinting"),
      BLOCKS("Blocks");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Features(String label) {
         this.label = label;
      }
   }

   enum Mode implements LabelEnum, SubModuleRegistry<NoSlowDown> {
      VANILLA("Vanilla", new VanillaNoSlowDown()),
      NCP("NCP", new NCPNoSlowDown()),
      UPDATED_NCP("Updated NCP", new UpdatedNCPNoSlowDown()),
      WATCHDOG("Watchdog", new WatchdogNoSlowDown()),
      SPOOF("Spoof", new SpoofNoSlowDown()),
      INVALID("Invalid", new InvalidNoSlowDown());

      private final String label;
      private final SubModule<NoSlowDown> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<NoSlowDown> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<NoSlowDown> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
