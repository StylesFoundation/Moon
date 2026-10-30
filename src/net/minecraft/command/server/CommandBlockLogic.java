package net.minecraft.command.server;

import io.netty.buffer.ByteBuf;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public abstract class CommandBlockLogic implements ICommandSender {
   private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
   private int successCount;
   private boolean trackOutput = true;
   private IChatComponent lastOutput = null;
   private String commandStored = "";
   private String customName = "@";
   private final CommandResultStats resultStats = new CommandResultStats();

   public int getSuccessCount() {
      return this.successCount;
   }

   public IChatComponent getLastOutput() {
      return this.lastOutput;
   }

   public void writeDataToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setString("Command", this.commandStored);
      nBTTagCompound.setInteger("SuccessCount", this.successCount);
      nBTTagCompound.setString("CustomName", this.customName);
      nBTTagCompound.setBoolean("TrackOutput", this.trackOutput);
      if (this.lastOutput != null && this.trackOutput) {
         nBTTagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
      }

      this.resultStats.writeStatsToNBT(nBTTagCompound);
   }

   public void readDataFromNBT(NBTTagCompound nBTTagCompound) {
      this.commandStored = nBTTagCompound.getString("Command");
      this.successCount = nBTTagCompound.getInteger("SuccessCount");
      if (nBTTagCompound.hasKey("CustomName", 8)) {
         this.customName = nBTTagCompound.getString("CustomName");
      }

      if (nBTTagCompound.hasKey("TrackOutput", 1)) {
         this.trackOutput = nBTTagCompound.getBoolean("TrackOutput");
      }

      if (nBTTagCompound.hasKey("LastOutput", 8) && this.trackOutput) {
         this.lastOutput = IChatComponent.Serializer.jsonToComponent(nBTTagCompound.getString("LastOutput"));
      }

      this.resultStats.readStatsFromNBT(nBTTagCompound);
   }

   @Override
   public boolean canCommandSenderUseCommand(int integer, String string) {
      return integer <= 2;
   }

   public void setCommand(String string) {
      this.commandStored = string;
      this.successCount = 0;
   }

   public String getCommand() {
      return this.commandStored;
   }

   public void trigger(World world) {
      if (world.isRemote) {
         this.successCount = 0;
      }

      MinecraftServer var5 = MinecraftServer.getServer();
      if (var5 != null && var5.isAnvilFileSet() && var5.isCommandBlockEnabled()) {
         ICommandManager var6 = var5.getCommandManager();

         try {
            this.lastOutput = null;
            this.successCount = var6.executeCommand(this, this.commandStored);
         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Executing command block");
            CrashReportCategory var9 = var8.makeCategory("Command to be executed");
            var9.addCrashSectionCallable("Command", this::getCommand);
            var9.addCrashSectionCallable("Name", this::getName);
            throw new ReportedException(var8);
         }
      } else {
         this.successCount = 0;
      }
   }

   @Override
   public String getName() {
      return this.customName;
   }

   @Override
   public IChatComponent getDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public void setName(String string) {
      this.customName = string;
   }

   @Override
   public void addChatMessage(IChatComponent iChatComponent) {
      if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote) {
         this.lastOutput = new ChatComponentText("[" + timestampFormat.format(new Date()) + "] ").appendSibling(iChatComponent);
         this.updateCommand();
      }
   }

   @Override
   public boolean sendCommandFeedback() {
      MinecraftServer var4 = MinecraftServer.getServer();
      return var4 == null || !var4.isAnvilFileSet() || var4.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
   }

   @Override
   public void setCommandStat(CommandResultStats.Type type, int integer) {
      this.resultStats.setCommandStatScore(this, type, integer);
   }

   public abstract void updateCommand();

   public abstract int func_145751_f();

   public abstract void func_145757_a(ByteBuf byteBuf);

   public void setLastOutput(IChatComponent iChatComponent) {
      this.lastOutput = iChatComponent;
   }

   public void setTrackOutput(boolean boolean1) {
      this.trackOutput = boolean1;
   }

   public boolean shouldTrackOutput() {
      return this.trackOutput;
   }

   public boolean tryOpenEditCommandBlock(EntityPlayer entityPlayer) {
      if (!entityPlayer.capabilities.isCreativeMode) {
         return false;
      }

      if (entityPlayer.getEntityWorld().isRemote) {
         entityPlayer.openEditCommandBlock(this);
      }

      return true;
   }

   public CommandResultStats getCommandResultStats() {
      return this.resultStats;
   }
}
