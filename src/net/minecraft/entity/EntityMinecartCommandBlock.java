package net.minecraft.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
   private final CommandBlockLogic commandBlockLogic = new CommandBlockLogic() {
      @Override
      public void updateCommand() {
         EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, this.getCommand());
         EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
      }

      @Override
      public int func_145751_f() {
         return 1;
      }

      @Override
      public void func_145757_a(ByteBuf byteBuf) {
         byteBuf.writeInt(EntityMinecartCommandBlock.this.getEntityId());
      }

      @Override
      public BlockPos getPosition() {
         return new BlockPos(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY + 0.5, EntityMinecartCommandBlock.this.posZ);
      }

      @Override
      public Vec3 getPositionVector() {
         return new Vec3(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY, EntityMinecartCommandBlock.this.posZ);
      }

      @Override
      public World getEntityWorld() {
         return EntityMinecartCommandBlock.this.worldObj;
      }

      @Override
      public Entity getCommandSenderEntity() {
         return EntityMinecartCommandBlock.this;
      }
   };
   private int activatorRailCooldown = 0;

   public EntityMinecartCommandBlock(World world) {
      super(world);
   }

   public EntityMinecartCommandBlock(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.getDataWatcher().addObject(23, "");
      this.getDataWatcher().addObject(24, "");
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.commandBlockLogic.readDataFromNBT(nBTTagCompound);
      this.getDataWatcher().updateObject(23, this.getCommandBlockLogic().getCommand());
      this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getCommandBlockLogic().getLastOutput()));
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      this.commandBlockLogic.writeDataToNBT(nBTTagCompound);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.COMMAND_BLOCK;
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return Blocks.command_block.getDefaultState();
   }

   public CommandBlockLogic getCommandBlockLogic() {
      return this.commandBlockLogic;
   }

   @Override
   public void onActivatorRailPass(int integer1, int integer2, int integer3, boolean boolean4) {
      if (boolean4 && this.ticksExisted - this.activatorRailCooldown >= 4) {
         this.getCommandBlockLogic().trigger(this.worldObj);
         this.activatorRailCooldown = this.ticksExisted;
      }
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      this.commandBlockLogic.tryOpenEditCommandBlock(entityPlayer);
      return false;
   }

   @Override
   public void onDataWatcherUpdate(int integer) {
      super.onDataWatcherUpdate(integer);
      if (integer == 24) {
         try {
            this.commandBlockLogic.setLastOutput(IChatComponent.Serializer.jsonToComponent(this.getDataWatcher().getWatchableObjectString(24)));
         } catch (Throwable var2) {
         }
      } else if (integer == 23) {
         this.commandBlockLogic.setCommand(this.getDataWatcher().getWatchableObjectString(23));
      }
   }
}
