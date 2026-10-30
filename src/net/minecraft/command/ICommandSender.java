package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ICommandSender {
   String getName();

   IChatComponent getDisplayName();

   void addChatMessage(IChatComponent iChatComponent);

   boolean canCommandSenderUseCommand(int integer, String string);

   BlockPos getPosition();

   Vec3 getPositionVector();

   World getEntityWorld();

   Entity getCommandSenderEntity();

   boolean sendCommandFeedback();

   void setCommandStat(CommandResultStats.Type type, int integer);
}
