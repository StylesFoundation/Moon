package com.moon.module.impl.player;

import com.moon.Moon;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.player.inventorymanager.InventoryHandler;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.util.client.Stopwatch;
import com.moon.util.math.MathUtil;
import java.util.Collections;
import java.util.LinkedList;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ChestStealer extends Module {
   private static final String[] GAME_GUI_KEYWORDS = new String[]{
      "menu",
      "selector",
      "game",
      "gui",
      "server",
      "inventory",
      "play",
      "teleporter",
      "shop",
      "melee",
      "armor",
      "block",
      "castle",
      "mini",
      "warp",
      "teleport",
      "user",
      "team",
      "tool",
      "sure",
      "trade",
      "cancel",
      "accept",
      "soul",
      "book",
      "recipe",
      "profile",
      "tele",
      "port",
      "map",
      "kit",
      "select",
      "lobby",
      "vault",
      "lock",
      "quick",
      "travel",
      "cake",
      "war",
      "pvp",
      "choose",
      "collectibles",
      "modos",
      "jogo",
      "clique"
   };
   private static final String[] KEYWORD_WHITELIST = new String[]{"    gui"};
   public final BooleanSetting ignoreTrash = BooleanSetting.builder("ignore-trash").defaults("Ignore trash", true).build();
   public final BooleanSetting shuffle = BooleanSetting.builder("shuffle").defaults("Shuffle", false).build();
   public final BooleanSetting miss = BooleanSetting.builder("miss").defaults("Miss", false).build();
   public final BooleanSetting renderGui = BooleanSetting.builder("silent").defaults("Silent steal", true).build();
   public final BooleanSetting renderGuiAfterSteal = BooleanSetting.builder("render-gui")
      .defaults("Render gui", true)
      .visibleWhen(this.renderGui::getValue)
      .build();
   public final BooleanSetting gameGui = BooleanSetting.builder("game-gui").defaults("Game GUI", false).build();
   public final BooleanSetting customInventory = BooleanSetting.builder("custom-inventory").defaults("Custom inventories", false).build();
   public final IntegerSetting delay = IntegerSetting.builder("delay").defaults("Delay", 150, 0, 300, 50).build();
   public final IntegerSetting delayRandomization = IntegerSetting.builder("delay-randomization").defaults("Delay randomization", 50, 0, 500, 50).build();
   public final IntegerSetting missChanceValue = IntegerSetting.builder("miss-chance")
      .defaults("Miss Chance", 20, 0, 100, 1)
      .visibleWhen(this.miss::getValue)
      .build();
   private final Stopwatch stopwatch = new Stopwatch();
   private InventoryHandler inventoryHandler = null;
   private long nextStealTime;
   private boolean chestEmpty;

   public ChestStealer(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (this.inventoryHandler == null) {
            this.inventoryHandler = new InventoryHandler((InventoryManager)Moon.getInstance().getModuleManager().getModuleByName("InventoryManager"));
         }

         if (!(mc.currentScreen instanceof GuiChest guiChest)) {
            this.stopwatch.reset();
            return;
         }

         if (!this.customInventory.getValue() && guiChest.upperChestInventory.hasCustomName()) {
            return;
         }

         if (!this.gameGui.getValue()) {
            String chestTitle = guiChest.lowerChestInventory.getDisplayName().getUnformattedText().toLowerCase();

            for (String keyword : GAME_GUI_KEYWORDS) {
               for (String whitelistKeyword : KEYWORD_WHITELIST) {
                  if (chestTitle.contains(keyword) && !chestTitle.contains(whitelistKeyword)) {
                     return;
                  }
               }
            }
         }

         if (this.stopwatch.hasElapsed(this.nextStealTime - 5L)) {
            if (this.chestEmpty) {
               mc.thePlayer.closeScreen();
               this.chestEmpty = false;
               if (this.renderGui.getValue() && !this.renderGuiAfterSteal.getValue()) {
               }

               return;
            }

            int totalSlots = guiChest.Method427() * 9;
            LinkedList<Integer> slots = new LinkedList<>();

            for (int i = 0; i < totalSlots; i++) {
               slots.add(i);
            }

            if (this.shuffle.getValue()) {
               Collections.shuffle(slots);
            }

            boolean shouldMiss = this.miss.getValue() && this.missChanceValue.getValue() >= MathUtil.randomInt(0, 100);
            int missSlot = MathUtil.randomInt(0, totalSlots - 1);
            Container container = guiChest.inventorySlots;
            boolean hasItems = false;

            for (int slotIndex : slots) {
               Slot slot = container.getSlot(slotIndex);
               if (shouldMiss) {
                  if (missSlot == slot.slotNumber) {
                     if (slot.getHasStack()) {
                        hasItems = true;
                        if (slots.size() - 1 > slotIndex) {
                           missSlot = (Integer)slots.get(slotIndex + 1);
                        }
                     } else if (!this.ignoreTrash.getValue() || !this.inventoryHandler.shouldDrop(slot)) {
                        this.stealSlot(container.windowId, slot.slotNumber);
                        hasItems = true;
                        if (this.delay.getValue() != 0 || this.delayRandomization.getValue() != 0) {
                           break;
                        }
                     }
                  }
               } else if (slot.getHasStack() && (!this.ignoreTrash.getValue() || !this.inventoryHandler.shouldDrop(slot))) {
                  this.stealSlot(container.windowId, slot.slotNumber);
                  hasItems = true;
                  if (this.delay.getValue() != 0 || this.delayRandomization.getValue() != 0) {
                     break;
                  }
               }
            }

            if (!hasItems) {
               this.chestEmpty = true;
            }
         }
      }
   }

   public void stealSlot(int windowId, int slotNumber) {
      mc.playerController.windowClick(windowId, slotNumber, 0, 1, mc.thePlayer);
      this.stopwatch.reset();
      this.nextStealTime = this.randomizedDelay();
   }

   private long randomizedDelay() {
      int extraDelay = MathUtil.randomInt(0, this.delayRandomization.getValue()) + 25;
      extraDelay -= extraDelay % 50;
      return this.delay.getValue() + extraDelay;
   }
}
