package com.moon.command.impl;

import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.impl.FloatArgument;

public class VclipCommand extends Command {
   private final FloatArgument distance = FloatArgument.builder()
      .range(-10.0F, 10.0F)
      .value("distance")
      .description("The distance you want to clip")
      .required()
      .build();

   public VclipCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      minecraft.thePlayer
         .setPositionAndUpdate(minecraft.thePlayer.posX, minecraft.thePlayer.posY + this.distance.getValue().floatValue(), minecraft.thePlayer.posZ);
      return CommandExecutionResult.builder().success(true).build();
   }
}
