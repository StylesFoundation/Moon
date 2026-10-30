package com.moon.command.impl;

import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.impl.FloatArgument;
import com.moon.util.game.MovementUtil;

public class HclipCommand extends Command {
   private final FloatArgument distance = FloatArgument.builder()
      .range(0.0F, 10.0F)
      .value("distance")
      .description("The distance you want to clip")
      .required()
      .build();

   public HclipCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      float yaw = MovementUtil.getMovementYaw();
      float dist = this.distance.getValue();
      minecraft.thePlayer
         .setPositionAndUpdate(minecraft.thePlayer.posX + -Math.sin(yaw) * dist, minecraft.thePlayer.posY, minecraft.thePlayer.posZ + Math.cos(yaw) * dist);
      return CommandExecutionResult.builder().success(true).build();
   }
}
