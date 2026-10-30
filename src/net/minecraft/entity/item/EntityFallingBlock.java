package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFallingBlock extends Entity {
   private IBlockState fallTile;
   public int fallTime;
   public boolean shouldDropItem = true;
   private boolean canSetAsBlock;
   private boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public NBTTagCompound tileEntityData;

   public EntityFallingBlock(World world) {
      super(world);
   }

   public EntityFallingBlock(World world, double double2, double double3, double double4, IBlockState iBlockState) {
      super(world);
      this.fallTile = iBlockState;
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
      this.setPosition(double2, double3, double4);
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.prevPosX = double2;
      this.prevPosY = double3;
      this.prevPosZ = double4;
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @Override
   public void onUpdate() {
      Block var4 = this.fallTile.getBlock();
      if (var4.getMaterial() == Material.air) {
         this.setDead();
      } else {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         if (this.fallTime++ == 0) {
            BlockPos var5 = new BlockPos(this);
            if (this.worldObj.getBlockState(var5).getBlock() == var4) {
               this.worldObj.setBlockToAir(var5);
            } else if (!this.worldObj.isRemote) {
               this.setDead();
               return;
            }
         }

         this.motionY -= 0.04F;
         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         this.motionX *= 0.98F;
         this.motionY *= 0.98F;
         this.motionZ *= 0.98F;
         if (!this.worldObj.isRemote) {
            BlockPos var11 = new BlockPos(this);
            if (this.onGround) {
               this.motionX *= 0.7F;
               this.motionZ *= 0.7F;
               this.motionY *= -0.5;
               if (this.worldObj.getBlockState(var11).getBlock() != Blocks.piston_extension) {
                  this.setDead();
                  if (!this.canSetAsBlock) {
                     if (this.worldObj.canBlockBePlaced(var4, var11, true, EnumFacing.UP, null, null)
                        && !BlockFalling.canFallInto(this.worldObj, var11.down())
                        && this.worldObj.setBlockState(var11, this.fallTile, 3)) {
                        if (var4 instanceof BlockFalling) {
                           ((BlockFalling)var4).onEndFalling(this.worldObj, var11);
                        }

                        if (this.tileEntityData != null && var4 instanceof ITileEntityProvider) {
                           TileEntity var6 = this.worldObj.getTileEntity(var11);
                           if (var6 != null) {
                              NBTTagCompound var7 = new NBTTagCompound();
                              var6.writeToNBT(var7);

                              for (String var9 : this.tileEntityData.getKeySet()) {
                                 NBTBase var10 = this.tileEntityData.getTag(var9);
                                 if (!var9.equals("x") && !var9.equals("y") && !var9.equals("z")) {
                                    var7.setTag(var9, var10.copy());
                                 }
                              }

                              var6.readFromNBT(var7);
                              var6.markDirty();
                           }
                        }
                     } else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                        this.entityDropItem(new ItemStack(var4, 1, var4.damageDropped(this.fallTile)), 0.0F);
                     }
                  }
               }
            } else if (this.fallTime > 100 && !this.worldObj.isRemote && (var11.getY() < 1 || var11.getY() > 256) || this.fallTime > 600) {
               if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                  this.entityDropItem(new ItemStack(var4, 1, var4.damageDropped(this.fallTile)), 0.0F);
               }

               this.setDead();
            }
         }
      }
   }

   @Override
   public void fall(float float1, float float2) {
      Block var6 = this.fallTile.getBlock();
      if (this.hurtEntities) {
         int var7 = MathHelper.ceiling_float_int(float1 - 1.0F);
         if (var7 > 0) {
            ArrayList<Entity> var8 = Lists.newArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
            boolean var9 = var6 == Blocks.anvil;
            DamageSource var10 = var9 ? DamageSource.anvil : DamageSource.fallingBlock;

            for (Entity var12 : var8) {
               var12.attackEntityFrom(var10, Math.min(MathHelper.floor_float(var7 * this.fallHurtAmount), this.fallHurtMax));
            }

            if (var9 && this.rand.nextFloat() < 0.05F + var7 * 0.05) {
               int var13 = this.fallTile.getValue(BlockAnvil.DAMAGE);
               if (++var13 > 2) {
                  this.canSetAsBlock = true;
               } else {
                  this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, var13);
               }
            }
         }
      }
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      Block var5 = this.fallTile != null ? this.fallTile.getBlock() : Blocks.air;
      ResourceLocation var6 = Block.blockRegistry.getNameForObject(var5);
      nBTTagCompound.setString("Block", var6 == null ? "" : var6.toString());
      nBTTagCompound.setByte("Data", (byte)var5.getMetaFromState(this.fallTile));
      nBTTagCompound.setByte("Time", (byte)this.fallTime);
      nBTTagCompound.setBoolean("DropItem", this.shouldDropItem);
      nBTTagCompound.setBoolean("HurtEntities", this.hurtEntities);
      nBTTagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
      nBTTagCompound.setInteger("FallHurtMax", this.fallHurtMax);
      if (this.tileEntityData != null) {
         nBTTagCompound.setTag("TileEntityData", this.tileEntityData);
      }
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      int var5 = nBTTagCompound.getByte("Data") & 255;
      if (nBTTagCompound.hasKey("Block", 8)) {
         this.fallTile = Block.getBlockFromName(nBTTagCompound.getString("Block")).getStateFromMeta(var5);
      } else if (nBTTagCompound.hasKey("TileID", 99)) {
         this.fallTile = Block.getBlockById(nBTTagCompound.getInteger("TileID")).getStateFromMeta(var5);
      } else {
         this.fallTile = Block.getBlockById(nBTTagCompound.getByte("Tile") & 255).getStateFromMeta(var5);
      }

      this.fallTime = nBTTagCompound.getByte("Time") & 255;
      Block var6 = this.fallTile.getBlock();
      if (nBTTagCompound.hasKey("HurtEntities", 99)) {
         this.hurtEntities = nBTTagCompound.getBoolean("HurtEntities");
         this.fallHurtAmount = nBTTagCompound.getFloat("FallHurtAmount");
         this.fallHurtMax = nBTTagCompound.getInteger("FallHurtMax");
      } else if (var6 == Blocks.anvil) {
         this.hurtEntities = true;
      }

      if (nBTTagCompound.hasKey("DropItem", 99)) {
         this.shouldDropItem = nBTTagCompound.getBoolean("DropItem");
      }

      if (nBTTagCompound.hasKey("TileEntityData", 10)) {
         this.tileEntityData = nBTTagCompound.getCompoundTag("TileEntityData");
      }

      if (var6 == null || var6.getMaterial() == Material.air) {
         this.fallTile = Blocks.sand.getDefaultState();
      }
   }

   public World getWorldObj() {
      return this.worldObj;
   }

   public void setHurtEntities(boolean boolean1) {
      this.hurtEntities = boolean1;
   }

   @Override
   public boolean canRenderOnFire() {
      return false;
   }

   @Override
   public void addEntityCrashInfo(CrashReportCategory crashReportCategory) {
      super.addEntityCrashInfo(crashReportCategory);
      if (this.fallTile != null) {
         Block var5 = this.fallTile.getBlock();
         crashReportCategory.addCrashSection("Immitating block ID", Block.getIdFromBlock(var5));
         crashReportCategory.addCrashSection("Immitating block data", var5.getMetaFromState(this.fallTile));
      }
   }

   public IBlockState getBlock() {
      return this.fallTile;
   }
}
