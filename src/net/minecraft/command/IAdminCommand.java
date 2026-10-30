package net.minecraft.command;

public interface IAdminCommand {
   void notifyOperators(ICommandSender iCommandSender, ICommand iCommand, int integer, String string, Object... arr);
}
