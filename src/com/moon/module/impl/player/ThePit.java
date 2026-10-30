package com.moon.module.impl.player;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.player.thepit.PitTarget;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.util.client.ChatUtil;
import com.moon.util.client.Stopwatch;
import com.moon.util.game.SlotUtil;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;

public class ThePit extends Module {
   private final MultipleChoiceSetting<ThePit.Features> autoUseFeatures = MultipleChoiceSetting.builder("auto-use-features", ThePit.Features.class)
      .defaults("Auto-use features", ThePit.Features.GOLDEN_HEAD, ThePit.Features.AAA_RATED_STEAK, ThePit.Features.FRACTURED_SOUL, ThePit.Features.RAGE_POTATO)
      .build();
   private final Stopwatch stopwatch = new Stopwatch();
   private final SlotUtil slotUtil = new SlotUtil();

   public ThePit(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.POST) {
         if (this.stopwatch.hasElapsedAndReset(250L)) {
            for (PitTarget target : this.findPitTargets()) {
               ThePit.Features feature = target.getFeature();
               Slot slot = target.getSlot();
               if (this.autoUseFeatures.hasChecked(feature)) {
                  this.useItem(slot.slotNumber, target.getItemStack());
                  ChatUtil.sendMessage("<red>" + feature.label() + " <gray>was successfully used!");
                  break;
               }
            }
         }
      }
   }

   private void useItem(int slot, ItemStack itemStack) {
      this.slotUtil.switchToSlot(slot - 36);
      mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(itemStack));
      this.slotUtil.deprecatedNoOp3();
   }

   private Set<PitTarget> findPitTargets() {
      Set<PitTarget> targets = new HashSet<>();
      if (mc.thePlayer != null && mc.thePlayer.inventoryContainer != null) {
         ThePit.Features[] features = ThePit.Features.values();

         for (Slot slot : mc.thePlayer.inventoryContainer.inventorySlots) {
            if (slot.slotNumber >= 36) {
               ItemStack stack = slot.getStack();
               if (stack != null) {
                  for (ThePit.Features feature : features) {
                     boolean canUse = feature.getUsePredicate().test(mc, stack);
                     boolean matchesItem = feature.getCooldownTicks() == Item.getIdFromItem(stack.getItem());
                     if (canUse && matchesItem) {
                        targets.add(new PitTarget(feature, slot, stack));
                     }
                  }
               }
            }
         }

         return targets;
      } else {
         return targets;
      }
   }

   public enum Features implements LabelEnum {
      GOLDEN_HEAD(
         "Golden head",
         397,
         (minecraft, itemStack) -> minecraft.thePlayer.getAbsorptionAmount() <= 0.0F
            && minecraft.thePlayer.getHealth() <= 16.0F
            && !itemStack.getDisplayName().contains("Pizza")
      ),
      AAA_RATED_STEAK(
         "AAA rated steak",
         423,
         (minecraft, itemStack) -> (!minecraft.thePlayer.isPotionActive(Potion.moveSpeed) || !minecraft.thePlayer.isPotionActive(Potion.resistance))
            && minecraft.thePlayer.getHealth() <= 19.0F
      ),
      FRACTURED_SOUL(
         "Fractured soul", 378, (minecraft, itemStack) -> minecraft.thePlayer.getAbsorptionAmount() <= 0.0F && minecraft.thePlayer.getHealth() <= 12.0F
      ),
      RAGE_POTATO(
         "Rage potato",
         393,
         (minecraft, itemStack) -> (!minecraft.thePlayer.isPotionActive(Potion.moveSpeed) || !minecraft.thePlayer.isPotionActive(Potion.resistance))
            && minecraft.thePlayer.getHealth() <= 18.0F
            && minecraft.thePlayer.getAbsorptionAmount() <= 0.0F
      );

      private final String displayName;
      private final int cooldownTicks;
      private final BiPredicate<Minecraft, ItemStack> usePredicate;

      @Override
      public String label() {
         return this.displayName;
      }

      public int getCooldownTicks() {
         return this.cooldownTicks;
      }

      public BiPredicate<Minecraft, ItemStack> getUsePredicate() {
         return this.usePredicate;
      }

      Features(String displayName, int cooldownTicks, BiPredicate<Minecraft, ItemStack> usePredicate) {
         this.displayName = displayName;
         this.cooldownTicks = cooldownTicks;
         this.usePredicate = usePredicate;
      }

      private static ThePit.Features[] createValues() {
         return new ThePit.Features[]{GOLDEN_HEAD, AAA_RATED_STEAK, FRACTURED_SOUL, RAGE_POTATO};
      }
   }
}
