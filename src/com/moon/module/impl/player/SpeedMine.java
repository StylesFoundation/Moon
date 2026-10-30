package com.moon.module.impl.player;

import com.moon.Moon;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class SpeedMine extends Module {
   private final BooleanSetting usePercentage = BooleanSetting.builder("use-percentage").defaults("Use percentage", false).build();
   private final FloatSetting normalSpeed = FloatSetting.builder("normal-speed")
      .defaults("Speed", 0.2F, 0.0F, 1.0F, 0.05F)
      .visibleWhen(() -> !this.usePercentage.getValue())
      .build();
   private final FloatSetting percentageSpeed = FloatSetting.builder("percentage-speed")
      .defaults("Speed", 50.0F, 0.0F, 100.0F, 1.0F)
      .visibleWhen(this.usePercentage::getValue)
      .build();

   public SpeedMine(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         mc.playerController.blockHitDelay = 0;
         float speed = 0.0F;
         BedBreaker bedBreaker = Moon.getInstance().getModuleManager().getModule(BedBreaker.class);
         if (bedBreaker != null) {
            BlockPos targetPos = bedBreaker.getTargetPos();
            if (targetPos != null && bedBreaker.isEnabled()) {
               if (!this.usePercentage.getValue()) {
                  speed = this.normalSpeed.getValue();
               } else {
                  Block block = mc.theWorld.getBlockState(targetPos).getBlock();
                  if (block != null) {
                     float hardness = block.Method434(mc.thePlayer, mc.theWorld, targetPos);
                     speed = hardness * (1.0F - this.percentageSpeed.getValue() / 100.0F) * (1.0F / hardness);
                  }
               }

               if (bedBreaker.getBreakProgress() > 1.0F - speed) {
                  bedBreaker.setBreakProgress(1.0);
               }
            } else {
               if (!this.usePercentage.getValue()) {
                  speed = this.normalSpeed.getValue();
               } else if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                  BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                  Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                  if (block != null) {
                     float hardness = block.Method434(mc.thePlayer, mc.theWorld, blockPos);
                     speed = hardness * (1.0F - this.percentageSpeed.getValue() / 100.0F) * (1.0F / hardness);
                  }
               }

               if (mc.playerController.curBlockDamageMP > 1.0F - speed) {
                  mc.playerController.curBlockDamageMP = 1.0F;
               }
            }
         }
      }
   }
}
