package com.moon.module.impl.player;

import com.moon.Moon;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.player.inventorymanager.InventoryHandler;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.util.client.Stopwatch;
import com.moon.util.math.MathUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class InventoryManager extends Module {
   public final EnumSetting<InventoryManager.Mode> requiredInventoryState = EnumSetting.builder("inventory-mode", InventoryManager.Mode.class)
      .defaults("Required Inventory State", InventoryManager.Mode.NONE)
      .build();
   public final MultipleChoiceSetting<InventoryManager.Features> features = MultipleChoiceSetting.builder("features", InventoryManager.Features.class)
      .defaults(
         "Features",
         InventoryManager.Features.AUTO_CLEAN,
         InventoryManager.Features.AUTO_ARMOR,
         InventoryManager.Features.AUTO_SWORD,
         InventoryManager.Features.AUTO_SOUP,
         InventoryManager.Features.AUTO_POTION,
         InventoryManager.Features.MOVE_BLOCKS
      )
      .build();
   public final IntegerSetting delay = IntegerSetting.builder("delay").defaults("Delay", 150, 1, 300, 50).build();
   public final IntegerSetting delayRandomization = IntegerSetting.builder("delay-randomization").defaults("Delay randomization", 50, 0, 500, 50).build();
   public final IntegerSetting blockLimit = IntegerSetting.builder("block-limit")
      .defaults("Block limit", 256, 64, 1024, 64)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN))
      .build();
   public final BooleanSetting ignoreCustomNames = BooleanSetting.builder("ignore-custom-display-names")
      .defaults("Ignore custom names", true)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN))
      .build();
   public final BooleanSetting keepBows = BooleanSetting.builder("keep-bows")
      .defaults("Keep bows", true)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN))
      .build();
   public final BooleanSetting keepTools = BooleanSetting.builder("keep-tools")
      .defaults("Keep tools", true)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN))
      .build();
   public final BooleanSetting hotbarTools = BooleanSetting.builder("hotbar-tools")
      .defaults("Hotbar tools", true)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN) && this.keepTools.getValue())
      .build();
   public final IntegerSetting toolsSlot = IntegerSetting.builder("tools-slot")
      .defaults("Tools slot", 1, 1, 6, 1)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN) && this.keepTools.getValue() && this.hotbarTools.getValue())
      .build();
   private final GroupSetting cleanOptions = GroupSetting.builder()
      .defaults("Clean Options", this.blockLimit, this.ignoreCustomNames, this.keepBows, this.keepTools, this.hotbarTools, this.toolsSlot)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN))
      .build();
   public final IntegerSetting potionThrowDelay = IntegerSetting.builder("potion-throw-delay")
      .defaults("Potion throw delay", 500, 50, 2500, 50)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_POTION))
      .build();
   public final MultipleChoiceSetting<InventoryManager.PotionType> allowedPotions = MultipleChoiceSetting.builder(
         "allowed-potions", InventoryManager.PotionType.class
      )
      .defaults(
         "Allowed Potions",
         InventoryManager.PotionType.INSTANT_HEAL,
         InventoryManager.PotionType.SPEED,
         InventoryManager.PotionType.REGENERATION,
         InventoryManager.PotionType.RESISTANCE
      )
      .build();
   private final IntegerSetting activationHealth = IntegerSetting.builder("activation-health")
      .defaults("Activation health", 15, 1, 20, 1)
      .visibleWhen(() -> this.allowedPotions.hasChecked(InventoryManager.PotionType.INSTANT_HEAL))
      .build();
   private final GroupSetting potionOptions = GroupSetting.builder()
      .defaults("Potion Options", this.allowedPotions, this.activationHealth, this.potionThrowDelay)
      .visibleWhen(() -> this.features.hasChecked(InventoryManager.Features.AUTO_POTION))
      .build();
   private final Stopwatch actionStopwatch = new Stopwatch();
   private final Stopwatch soupStopwatch = new Stopwatch();
   private final Stopwatch potionStopwatch = new Stopwatch();
   private InventoryHandler inventoryHandler;
   private Integer pendingPotionSlot;
   private long nextActionTime;
   private KillAura killAura;
   private boolean throwPendingPotion;

   public InventoryManager(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (this.inventoryHandler == null) {
         this.inventoryHandler = new InventoryHandler(this);
      }

      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }

      this.setSuffix(String.valueOf(this.delay.getValue()));
      if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {
         if (mc.currentScreen == null && this.requiredInventoryState.getValue() == InventoryManager.Mode.OPEN) {
            this.actionStopwatch.reset();
         } else if (event.getSequence() == UpdateEvent.Sequence.PRE) {
            if (this.throwPendingPotion) {
               int previousSlot = mc.thePlayer.inventory.currentItem;
               this.switchToSlot(this.pendingPotionSlot);
               mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
               this.switchToSlot(previousSlot);
               this.pendingPotionSlot = null;
               this.throwPendingPotion = false;
               this.actionStopwatch.reset();
               this.potionStopwatch.reset();
            } else if (this.pendingPotionSlot != null) {
               event.setPitch(90.0F);
               if (this.soupStopwatch.hasElapsedAndReset(this.potionThrowDelay.getValue().intValue())) {
                  this.throwPendingPotion = true;
               }
            } else {
               if (this.actionStopwatch.hasElapsedAndReset(this.nextActionTime - 5L)) {
                  this.nextActionTime = this.randomizedDelay();
                  if (this.features.hasChecked(InventoryManager.Features.AUTO_ARMOR)) {
                     for (int armorType = 0; armorType < 4; armorType++) {
                        int bestArmorSlot = this.inventoryHandler.getArmor(armorType);
                        if (bestArmorSlot != -1) {
                           if (mc.thePlayer.inventoryContainer.getSlot(armorType + 5).getStack() == null) {
                              this.inventoryHandler.shiftClick(bestArmorSlot);
                              return;
                           }

                           if (mc.thePlayer.inventory.getFirstEmptyStack() != -1) {
                              this.inventoryHandler.shiftClick(armorType + 5);
                              return;
                           }
                           break;
                        }
                     }
                  }

                  if (this.features.hasChecked(InventoryManager.Features.AUTO_SWORD) && this.swapIfPresent(0, this.inventoryHandler.getSword())) {
                     return;
                  }

                  if (this.keepTools.getValue() && this.hotbarTools.getValue()) {
                     if (this.swapIfPresent(this.toolsSlot.getValue(), this.inventoryHandler.getPickaxe())) {
                        return;
                     }

                     if (this.swapIfPresent(this.toolsSlot.getValue() + 1, this.inventoryHandler.getAxe())) {
                        return;
                     }

                     if (this.swapIfPresent(this.toolsSlot.getValue() + 2, this.inventoryHandler.getShovel())) {
                        return;
                     }
                  }

                  if (this.keepBows.getValue() && this.swapIfPresent(7, this.inventoryHandler.getBow())) {
                     return;
                  }

                  if (this.features.hasChecked(InventoryManager.Features.MOVE_BLOCKS) && this.pendingPotionSlot == null) {
                     int blockSlot = this.inventoryHandler.getBlock();
                     if (blockSlot != -1 && blockSlot != 8) {
                        this.inventoryHandler.swapWithHotbar(8, blockSlot);
                        return;
                     }
                  }

                  boolean hasNoTarget = this.killAura.getTargetSelector().getTarget() == null;
                  if (this.features.hasChecked(InventoryManager.Features.AUTO_POTION)
                     && hasNoTarget
                     && mc.thePlayer.onGround
                     && this.pendingPotionSlot == null
                     && this.potionStopwatch.hasElapsed(this.potionThrowDelay.getValue().intValue() + 2500L)) {
                     int healSlot = this.inventoryHandler.getPotion(Potion.heal.getId());
                     int regenSlot = this.inventoryHandler.getPotion(Potion.regeneration.getId());
                     int speedSlot = this.inventoryHandler.getPotion(Potion.moveSpeed.getId());
                     int resistanceSlot = this.inventoryHandler.getPotion(Potion.resistance.getId());
                     if (this.allowedPotions.hasChecked(InventoryManager.PotionType.INSTANT_HEAL)
                        && healSlot != -1
                        && mc.thePlayer.getHealth() <= this.activationHealth.getValue().intValue()) {
                        this.pendingPotionSlot = healSlot;
                     } else if (this.allowedPotions.hasChecked(InventoryManager.PotionType.REGENERATION)
                        && regenSlot != -1
                        && !mc.thePlayer.isPotionActive(Potion.regeneration)
                        && mc.thePlayer.getHealth() <= this.activationHealth.getValue().intValue()) {
                        this.pendingPotionSlot = regenSlot;
                     } else if (this.allowedPotions.hasChecked(InventoryManager.PotionType.SPEED)
                        && speedSlot != -1
                        && !mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        this.pendingPotionSlot = speedSlot;
                     } else if (this.allowedPotions.hasChecked(InventoryManager.PotionType.RESISTANCE)
                        && resistanceSlot != -1
                        && !mc.thePlayer.isPotionActive(Potion.resistance)) {
                        this.pendingPotionSlot = resistanceSlot;
                     }

                     if (this.pendingPotionSlot != null) {
                        if (this.pendingPotionSlot > 8) {
                           this.inventoryHandler.swapWithHotbar(8, this.pendingPotionSlot);
                           this.pendingPotionSlot = 8;
                        }

                        return;
                     }
                  }

                  if (this.features.hasChecked(InventoryManager.Features.AUTO_SOUP)) {
                     int soupSlot = this.inventoryHandler.getSoup();
                     if (soupSlot != -1 && mc.thePlayer.getHealth() + 1.0F <= this.activationHealth.getValue().intValue() && this.soupStopwatch.hasElapsed(100L)) {
                        if (soupSlot > 8) {
                           this.inventoryHandler.swapWithHotbar(8, soupSlot);
                           soupSlot = 8;
                        }

                        int currentSlot = mc.thePlayer.inventory.currentItem;
                        this.switchToSlot(soupSlot);
                        mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                        this.switchToSlot(currentSlot);
                        mc.thePlayer
                           .sendQueue
                           .Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        this.soupStopwatch.reset();
                        this.potionStopwatch.reset();
                     }
                  }

                  if (this.features.hasChecked(InventoryManager.Features.AUTO_CLEAN)) {
                     int trashSlot = this.inventoryHandler.getTrashSlot();
                     if (trashSlot != -1) {
                        this.inventoryHandler.drop(trashSlot, true);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean swapIfPresent(int hotbarSlot, int inventorySlot) {
      if (inventorySlot != -1) {
         this.inventoryHandler.swapWithHotbar(hotbarSlot, inventorySlot);
         return true;
      } else {
         return false;
      }
   }

   private void switchToSlot(int slot) {
      mc.thePlayer.inventory.currentItem = slot;
      mc.thePlayer.sendQueue.Method754(new C09PacketHeldItemChange(slot));
   }

   private long randomizedDelay() {
      int extraDelay = MathUtil.randomInt(0, this.delayRandomization.getValue()) + 25;
      extraDelay -= extraDelay % 50;
      return this.delay.getValue() + extraDelay;
   }

   @Override
   public void onEnable() {
      this.nextActionTime = this.randomizedDelay();
   }

   public enum Features implements LabelEnum {
      AUTO_CLEAN("Auto Clean"),
      AUTO_ARMOR("Auto Armor"),
      AUTO_SWORD("Auto Sword"),
      AUTO_SOUP("Auto Soup"),
      AUTO_POTION("Auto Potion"),
      MOVE_BLOCKS("Move Blocks");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      Features(String displayName) {
         this.displayName = displayName;
      }

      private static InventoryManager.Features[] createValues() {
         return new InventoryManager.Features[]{AUTO_CLEAN, AUTO_ARMOR, AUTO_SWORD, AUTO_SOUP, AUTO_POTION, MOVE_BLOCKS};
      }
   }

   public enum Mode implements LabelEnum {
      NONE("None"),
      OPEN("Open Only"),
      SPOOF("Spoof");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      Mode(String displayName) {
         this.displayName = displayName;
      }

      private static InventoryManager.Mode[] createValues() {
         return new InventoryManager.Mode[]{NONE, OPEN, SPOOF};
      }
   }

   public enum PotionType implements LabelEnum {
      INSTANT_HEAL("Instant Heal"),
      RESISTANCE("Resistance"),
      REGENERATION("Regeneration"),
      SPEED("Speed");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      PotionType(String displayName) {
         this.displayName = displayName;
      }

      private static InventoryManager.PotionType[] createValues() {
         return new InventoryManager.PotionType[]{INSTANT_HEAL, RESISTANCE, REGENERATION, SPEED};
      }
   }
}
