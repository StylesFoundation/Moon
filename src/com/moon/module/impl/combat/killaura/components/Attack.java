package com.moon.module.impl.combat.killaura.components;

import com.moon.Moon;
import com.moon.events.impl.player.InteractEvent;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.util.client.Stopwatch;
import com.moon.util.math.FractionAccumulator;
import com.moon.util.math.MathUtil;
import com.moon.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

public class Attack {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final KillAura killAura;
   private final FractionAccumulator accumulator = new FractionAccumulator();
   private double lastBaseCps = Double.MAX_VALUE;
   private double nextAttackTick = 0.0;
   private double lastDeviationCps;
   private int tickCounter;
   private final Stopwatch stopwatch = new Stopwatch();
   private long recalibrationDelay = MathUtil.randomInt(5000, 25000);
   private double currentBaseCps = 0.0;
   private double lastConfiguredCps = 0.0;
   private boolean forceNextTick;
   private boolean suppressScheduling;

   public void tick() {
      BooleanSetting doubleClicksSetting = this.killAura.getDoubleClicks();
      float configuredCps = this.killAura.getCps().getValue().intValue();
      if (this.stopwatch.hasElapsedAndReset(this.recalibrationDelay) || configuredCps != this.lastConfiguredCps) {
         double minCps = Math.max(1.0F, configuredCps - 3.0F);
         double maxCps = Math.min(doubleClicksSetting.getValue() ? 23.0F : 20.0F, configuredCps + 4.0F);
         this.currentBaseCps = MathUtil.random(minCps, maxCps);
         this.lastConfiguredCps = configuredCps;
         this.recalibrationDelay = MathUtil.randomInt(5000, 25000);
      }

      boolean canAttack = !this.forceNextTick && this.tickCounter >= this.nextAttackTick;
      boolean shouldBlock = this.killAura.isAutoBlockEnabled() && canAttack;
      if (shouldBlock) {
         this.killAura.getAutoBlock().startBlocking();
      }

      if (!this.suppressScheduling) {
         if (this.killAura.getVelocityAttackSync().getValue() && mc.thePlayer.hurtResistantTime >= 17) {
            this.nextAttackTick = 0.0;
         }

         boolean forceHurtTimeAttack = false;
         boolean hasTarget = false;
         if (this.killAura.getClickPattern().getValue() == KillAura.ClickPattern.HURT_TIME) {
            EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
            boolean targetPresent = target != null;
            boolean hurtCondition = target != null && target.hurtResistantTime != 0 || this.tickCounter >= this.nextAttackTick;
            if (targetPresent && hurtCondition && target.hurtResistantTime <= 14) {
               forceHurtTimeAttack = true;
            }

            hasTarget = targetPresent;
         }

         while (this.tickCounter >= this.nextAttackTick && !hasTarget || forceHurtTimeAttack) {
            if (!canAttack) {
               this.forceNextTick = false;
               break;
            }

            this.attack();
            this.lastDeviationCps = this.nextAttackTick;
            boolean allowDoubleClicks = doubleClicksSetting.getValue() && this.lastDeviationCps > 0.0 && !forceHurtTimeAttack;
            this.nextAttackTick = this.computeNextDelay(this.currentBaseCps, allowDoubleClicks, this.killAura.getForceBalancedDeviation().getValue());
            this.tickCounter = 0;
            forceHurtTimeAttack = false;
         }
      }

      this.suppressScheduling = false;
      if (shouldBlock) {
         this.killAura.getAutoBlock().stopBlocking();
      }

      this.tickCounter++;
   }

   public void attack() {
      if (this.killAura.realClick.getValue()) {
         this.dispatchRealClick();
      } else {
         EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
         if (target != null) {
            double distance = target.getDistanceToEntity(mc.thePlayer);
            int version = ViaMCP.getInstance().getVersion();
            if (distance <= this.killAura.getAttackReach().getValue().floatValue()) {
               if (version <= 47) {
                  mc.thePlayer.swingItem();
               }

               InteractEvent interactEvent = new InteractEvent(target);
               Moon.getInstance().getEventBus().publish(interactEvent);
               if (!interactEvent.isCancelled()) {
                  mc.playerController.attackEntity(mc.thePlayer, target);
               }

               if (version > 47) {
                  mc.thePlayer.swingItem();
               }
            } else {
               mc.thePlayer.swingItem();
            }
         }
      }
   }

   public void forceNextTick() {
      this.forceNextTick = true;
   }

   private double computeNextDelay(double baseCps, boolean doubleClicks, boolean forceBalancedDeviation) {
      switch ((KillAura.ClickPattern)this.killAura.getClickPattern().getValue()) {
         case LATEST :
            double attackSpeed = 4.0;
            if (mc.thePlayer.getHeldItem() != null) {
               Item item = mc.thePlayer.getHeldItem().getItem();
               if (item instanceof ItemSword) {
                  attackSpeed = 1.6;
               } else if (item instanceof ItemSpade) {
                  attackSpeed = 1.0;
               } else if (item instanceof ItemPickaxe) {
                  attackSpeed = 1.2;
               } else if (item instanceof ItemAxe axe) {
                  switch (axe.getToolMaterial()) {
                     case STONE:
                     case WOOD:
                        attackSpeed = 0.8;
                        break;
                     case IRON:
                        attackSpeed = 0.9;
                        break;
                     default:
                        attackSpeed = 1.0;
                  }
               } else if (item instanceof ItemHoe hoe) {
                  switch (hoe.Method415()) {
                     case STONE:
                        attackSpeed = 2.0;
                        break;
                     case WOOD:
                     case GOLD:
                        attackSpeed = 1.0;
                        break;
                     case IRON:
                        attackSpeed = 3.0;
                        break;
                     default:
                        attackSpeed = 4.0;
                  }
               }
            }

            return Math.max(1.0, 1.0 / attackSpeed * 20.0);
         case BASIC :
            return Math.max(1L, Math.round(20.0 / this.killAura.getCps().getValue().intValue()));
         case ADVANCED :
         case HURT_TIME :
            double minCps = Math.max(1.0, baseCps - 3.0);
            double maxCps = Math.min(doubleClicks ? 23.0 : 20.0, baseCps + 4.0);
            double randomCps = MathUtil.random(minCps, maxCps);
            if (forceBalancedDeviation) {
               double avgCps = (minCps + maxCps) / 2.0;
               double threshold = Math.min(baseCps - 1.0, Math.min(23.0 - baseCps, avgCps)) * 0.6;
               byte maxAttempts = 50;
               int attempts = 0;

               while (Math.abs(randomCps - this.lastBaseCps) <= threshold) {
                  if (++attempts >= maxAttempts) {
                     break;
                  }

                  randomCps = MathUtil.random(minCps, maxCps);
               }

               this.lastBaseCps = randomCps;
            }

            double delay = 20.0 / randomCps;
            delay = this.accumulator.accumulate(delay, MathUtil.random(0.2, 0.8));
            double chance = 10.0;
            double roll = MathUtil.randomInt(0, 100);
            if (roll <= chance && this.accumulator.getCarry() >= 6.0) {
               double carryAmount = MathUtil.random(1.5, 3.4);
               delay += this.accumulator.consume(carryAmount);
            }

            return delay;
         default:
            return 0.0;
      }
   }

   private void dispatchRealClick() {
      if (mc.currentScreen == null) {
         int keyCode = mc.gameSettings.keyBindAttack.getKeyCode();
         KeyBinding.setKeyBindState(keyCode, true);
         KeyBinding.onTick(keyCode);
         KeyBinding.setKeyBindState(keyCode, false);
         KeyBinding.onTick(keyCode);
      }
   }

   public KillAura getKillAura() {
      return this.killAura;
   }

   public FractionAccumulator getAccumulator() {
      return this.accumulator;
   }

   public double getLastBaseCps() {
      return this.lastBaseCps;
   }

   public double getNextAttackTick() {
      return this.nextAttackTick;
   }

   public double getLastDeviationCps() {
      return this.lastDeviationCps;
   }

   public int getTickCounter() {
      return this.tickCounter;
   }

   public Stopwatch getStopwatch() {
      return this.stopwatch;
   }

   public long getRecalibrationDelay() {
      return this.recalibrationDelay;
   }

   public double getCurrentBaseCps() {
      return this.currentBaseCps;
   }

   public double getLastConfiguredCps() {
      return this.lastConfiguredCps;
   }

   public Attack(KillAura killAura) {
      this.killAura = killAura;
   }

   public Attack setTickCounter(int tickCounter) {
      this.tickCounter = tickCounter;
      return this;
   }

   public boolean isForceNextTick() {
      return this.forceNextTick;
   }

   public Attack setSuppressScheduling(boolean suppressScheduling) {
      this.suppressScheduling = suppressScheduling;
      return this;
   }

   public boolean isSuppressScheduling() {
      return this.suppressScheduling;
   }
}
