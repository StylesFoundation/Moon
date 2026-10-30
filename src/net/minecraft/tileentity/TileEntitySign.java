package net.minecraft.tileentity;

import com.google.gson.JsonParseException;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntitySign extends TileEntity {
   public final IChatComponent[] signText = new IChatComponent[]{
      new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")
   };
   public int lineBeingEdited = -1;
   private boolean isEditable = true;
   private EntityPlayer player;
   private final CommandResultStats stats = new CommandResultStats();

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);

      for (int var5 = 0; var5 < 4; var5++) {
         String var6 = IChatComponent.Serializer.componentToJson(this.signText[var5]);
         nBTTagCompound.setString("Text" + (var5 + 1), var6);
      }

      this.stats.writeStatsToNBT(nBTTagCompound);
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      this.isEditable = false;
      super.readFromNBT(nBTTagCompound);
      ICommandSender var5 = new ICommandSender() {
         @Override
         public String getName() {
            return "Sign";
         }

         @Override
         public IChatComponent getDisplayName() {
            return new ChatComponentText(this.getName());
         }

         @Override
         public void addChatMessage(IChatComponent iChatComponent) {
         }

         @Override
         public boolean canCommandSenderUseCommand(int integer, String string) {
            return true;
         }

         @Override
         public BlockPos getPosition() {
            return TileEntitySign.this.pos;
         }

         @Override
         public Vec3 getPositionVector() {
            return new Vec3(TileEntitySign.this.pos.getX() + 0.5, TileEntitySign.this.pos.getY() + 0.5, TileEntitySign.this.pos.getZ() + 0.5);
         }

         @Override
         public World getEntityWorld() {
            return TileEntitySign.this.worldObj;
         }

         @Override
         public Entity getCommandSenderEntity() {
            return null;
         }

         @Override
         public boolean sendCommandFeedback() {
            return false;
         }

         @Override
         public void setCommandStat(CommandResultStats.Type type, int integer) {
         }
      };

      for (int var6 = 0; var6 < 4; var6++) {
         String var7 = nBTTagCompound.getString("Text" + (var6 + 1));

         try {
            IChatComponent var8 = IChatComponent.Serializer.jsonToComponent(var7);

            try {
               this.signText[var6] = ChatComponentProcessor.processComponent(var5, var8, null);
            } catch (CommandException var9) {
               this.signText[var6] = var8;
            }
         } catch (JsonParseException var10) {
            this.signText[var6] = new ChatComponentText(var7);
         }
      }

      this.stats.readStatsFromNBT(nBTTagCompound);
   }

   @Override
   public Packet getDescriptionPacket() {
      IChatComponent[] var1 = new IChatComponent[4];
      System.arraycopy(this.signText, 0, var1, 0, 4);
      return new S33PacketUpdateSign(this.worldObj, this.pos, var1);
   }

   @Override
   public boolean func_183000_F() {
      return true;
   }

   public boolean getIsEditable() {
      return this.isEditable;
   }

   public void setEditable(boolean boolean1) {
      this.isEditable = boolean1;
      if (!boolean1) {
         this.player = null;
      }
   }

   public void setPlayer(EntityPlayer entityPlayer) {
      this.player = entityPlayer;
   }

   public EntityPlayer getPlayer() {
      return this.player;
   }

   public boolean executeCommand(EntityPlayer entityPlayer) {
      ICommandSender var5 = new ICommandSender() {
         @Override
         public String getName() {
            return entityPlayer.getName();
         }

         @Override
         public IChatComponent getDisplayName() {
            return entityPlayer.getDisplayName();
         }

         @Override
         public void addChatMessage(IChatComponent iChatComponent) {
         }

         @Override
         public boolean canCommandSenderUseCommand(int integer, String string) {
            return integer <= 2;
         }

         @Override
         public BlockPos getPosition() {
            return TileEntitySign.this.pos;
         }

         @Override
         public Vec3 getPositionVector() {
            return new Vec3(TileEntitySign.this.pos.getX() + 0.5, TileEntitySign.this.pos.getY() + 0.5, TileEntitySign.this.pos.getZ() + 0.5);
         }

         @Override
         public World getEntityWorld() {
            return entityPlayer.getEntityWorld();
         }

         @Override
         public Entity getCommandSenderEntity() {
            return entityPlayer;
         }

         @Override
         public boolean sendCommandFeedback() {
            return false;
         }

         @Override
         public void setCommandStat(CommandResultStats.Type type, int integer) {
            TileEntitySign.this.stats.setCommandStatScore(this, type, integer);
         }
      };

      for (IChatComponent var9 : this.signText) {
         ChatStyle var10 = var9 == null ? null : var9.getChatStyle();
         if (var10 != null && var10.getChatClickEvent() != null) {
            ClickEvent var11 = var10.getChatClickEvent();
            if (var11.getAction() == ClickEvent.Action.RUN_COMMAND) {
               MinecraftServer.getServer().getCommandManager().executeCommand(var5, var11.getValue());
            }
         }
      }

      return true;
   }

   public CommandResultStats getStats() {
      return this.stats;
   }
}
