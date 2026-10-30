package com.moon.module.impl.combat;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.util.client.Stopwatch;
import com.moon.util.math.FractionAccumulator;
import com.moon.util.math.MathUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public final FloatSetting cps = FloatSetting.builder("cps").defaults("Click per second", 14.0F, 1.0F, 20.0F, 0.1F).build();
   public final BooleanSetting autoBlock = BooleanSetting.builder("auto-block").defaults("Auto-block", true).build();
   public final BooleanSetting targetHurtTimeSync = BooleanSetting.builder("target-hurt-time-sync").defaults("Target hurt-time sync", true).build();
   public final BooleanSetting removeAirSwingDelay = BooleanSetting.builder("remove-air-swing-delay").defaults("Remove 1.8 swing delay", true).build();
   public final BooleanSetting doubleClicks = BooleanSetting.builder("double").defaults("Double clicks", true).build();
   public final BooleanSetting forceBalancedDeviation = BooleanSetting.builder("force-balanced-deviation").defaults("Force balanced deviation", true).build();
   private final FractionAccumulator accumulator = new FractionAccumulator();
   private double lastBaseCps = Double.MAX_VALUE;
   private double nextClickDelay = this.computeNextDelay(this.cps.getValue().floatValue(), this.doubleClicks.getValue(), this.forceBalancedDeviation.getValue());
   private double lastDelay;
   private int tickCounter;
   private final Stopwatch stopwatch = new Stopwatch();
   private long recalibrationDelay = MathUtil.randomInt(5000, 25000);
   private double currentBaseCps = this.cps.getValue().floatValue();
   private double lastConfiguredCps = this.cps.getValue().floatValue();
   private Entity lastTargetedEntity;

   public AutoClicker(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   private double computeJitter(double maxJitter, double minJitter) {
      double cpsDelta = 20.0F - this.cps.getValue();
      return Math.max(minJitter, maxJitter - cpsDelta / 4.0);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE && mc.thePlayer != null && mc.theWorld != null) {
         this.setSuffix(String.format(MathUtil.round(this.cps.getValue().floatValue(), 1) + "c/s"));
         if (this.stopwatch.hasElapsedAndReset(this.recalibrationDelay) || this.cps.getValue().floatValue() != this.lastConfiguredCps) {
            double jitter = this.computeJitter(4.5, 1.0);
            double minCps = Math.max(1.0, this.cps.getValue().floatValue() - jitter);
            double maxCps = Math.min(this.doubleClicks.getValue() ? 22.0F : 20.0F, this.cps.getValue() + 2.0F);
            this.currentBaseCps = MathUtil.random(minCps, maxCps);
            this.lastConfiguredCps = this.cps.getValue().floatValue();
            this.recalibrationDelay = MathUtil.randomInt(5000, 25000);
         }

         int attackMouseButton = mc.gameSettings.keyBindAttack.getKeyCode() + 100;
         int clickCount = 0;

         while (this.tickCounter >= this.nextClickDelay) {
            if (Mouse.isButtonDown(attackMouseButton) && mc.currentScreen == null && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit != null) {
               switch (mc.objectMouseOver.typeOfHit) {
                  case MISS:
                  case ENTITY:
                     this.dispatchClick();
                  default:
                     if (mc.objectMouseOver.entityHit != null) {
                        this.lastTargetedEntity = mc.objectMouseOver.entityHit;
                     }

                     clickCount++;
               }
            }

            this.lastDelay = this.nextClickDelay;
            boolean allowDouble = this.doubleClicks.getValue() && this.lastDelay > 0.0;
            this.nextClickDelay = this.computeNextDelay(this.currentBaseCps, allowDouble, this.forceBalancedDeviation.getValue());
            this.tickCounter = 0;
         }

         if (Mouse.isButtonDown(attackMouseButton) && this.autoBlock.getValue()) {
            if (this.tickCounter + 1 < this.nextClickDelay && this.tickCounter <= 2) {
               this.setBlocking(Mouse.isButtonDown(1));
            } else {
               this.setBlocking(false);
            }
         }

         this.tickCounter++;
      }
   }

   private void dispatchClick() {
      int attackKey = mc.gameSettings.keyBindAttack.getKeyCode();
      KeyBinding.setKeyBindState(attackKey, true);
      KeyBinding.onTick(attackKey);
      KeyBinding.setKeyBindState(attackKey, false);
      KeyBinding.onTick(attackKey);
   }

   private void setBlocking(boolean blocking) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), blocking);
   }

   private double computeNextDelay(double baseCps, boolean allowDoubleClicks, boolean forceBalancedDeviation) {
      double jitter = this.computeJitter(6.0, 2.0);
      double minCps = Math.max(1.0, baseCps - jitter);
      double maxCps = Math.min(allowDoubleClicks ? 22.0 : 20.0, baseCps + 6.0);
      double randomizedCps = MathUtil.random(minCps, maxCps);
      if (forceBalancedDeviation) {
         double midCps = (minCps + maxCps) / 2.0;
         double threshold = Math.min(baseCps - 1.0, Math.min(22.0 - baseCps, midCps)) * 0.6;
         byte maxAttempts = 50;
         int attempts = 0;

         while (Math.abs(randomizedCps - this.lastBaseCps) <= threshold) {
            if (++attempts >= maxAttempts) {
               break;
            }

            randomizedCps = MathUtil.random(minCps, maxCps);
         }

         this.lastBaseCps = randomizedCps;
      }

      double delay = 20.0 / randomizedCps;
      double weight = MathUtil.random(0.15, 0.75);
      boolean syncingHurtTime = this.targetHurtTimeSync.getValue()
         && this.lastTargetedEntity != null
         && this.lastTargetedEntity.hurtResistantTime >= 6
         && this.lastTargetedEntity.hurtResistantTime <= 12;
      if (syncingHurtTime) {
         weight = 1.0;
      }

      delay = this.accumulator.accumulate(delay, weight);
      double carryThreshold = this.accumulator.getCarry() >= 50.0 ? 40.0 : 15.0;
      double roll = MathUtil.randomInt(0, 100);
      if (roll <= carryThreshold && this.accumulator.getCarry() >= 6.0) {
         boolean highBonus = roll <= carryThreshold * (0.05 + baseCps * 0.01);
         double consumeAmount = highBonus ? MathUtil.random(3.5, 11.0) : MathUtil.random(1.5, 3.4);
         if (this.targetHurtTimeSync.getValue() && this.lastTargetedEntity != null && this.lastTargetedEntity.hurtResistantTime >= 12.0) {
            consumeAmount = Math.min(this.lastTargetedEntity.hurtResistantTime - 12.0, consumeAmount);
         }

         delay += this.accumulator.consume(consumeAmount);
      }

      return delay;
   }
}
