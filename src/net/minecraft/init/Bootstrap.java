package net.minecraft.init;

import com.mojang.authlib.GameProfile;
import java.io.PrintStream;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
   private static final PrintStream SYSOUT = System.out;
   private static boolean alreadyRegistered = false;
   private static final Logger LOGGER = LogManager.getLogger();

   public static boolean isRegistered() {
      return alreadyRegistered;
   }

   static void registerDispenserBehaviors() {
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
            EntityArrow var3 = new EntityArrow(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
            var3.canBePickedUp = 1;
            return var3;
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
            return new EntityEgg(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
            return new EntitySnowball(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
            return new EntityExpBottle(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
         }

         @Override
         protected float func_82498_a() {
            return super.func_82498_a() * 0.5F;
         }

         @Override
         protected float func_82500_b() {
            return super.func_82500_b() * 1.25F;
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
            return ItemPotion.isSplash(itemStack.getMetadata()) ? (new BehaviorProjectileDispense() {
               @Override
               protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
                  return new EntityPotion(world, iPosition.getX(), iPosition.getY(), iPosition.getZ(), itemStack.copy());
               }

               @Override
               protected float func_82498_a() {
                  return super.func_82498_a() * 0.5F;
               }

               @Override
               protected float func_82500_b() {
                  return super.func_82500_b() * 1.25F;
               }
            }).dispense(iBlockSource, itemStack) : this.field_150843_b.dispense(iBlockSource, itemStack);
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            EnumFacing var6 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            double var7 = iBlockSource.getX() + var6.getFrontOffsetX();
            double var9 = iBlockSource.getBlockPos().getY() + 0.2F;
            double var11 = iBlockSource.getZ() + var6.getFrontOffsetZ();
            Entity var13 = ItemMonsterPlacer.spawnCreature(iBlockSource.getWorld(), itemStack.getMetadata(), var7, var9, var11);
            if (var13 instanceof EntityLivingBase && itemStack.hasDisplayName()) {
               var13.setCustomNameTag(itemStack.getDisplayName());
            }

            itemStack.splitStack(1);
            return itemStack;
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            EnumFacing var3 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            double var4 = iBlockSource.getX() + var3.getFrontOffsetX();
            double var6 = iBlockSource.getBlockPos().getY() + 0.2F;
            double var8 = iBlockSource.getZ() + var3.getFrontOffsetZ();
            EntityFireworkRocket var10 = new EntityFireworkRocket(iBlockSource.getWorld(), var4, var6, var8, itemStack);
            iBlockSource.getWorld().spawnEntityInWorld(var10);
            itemStack.splitStack(1);
            return itemStack;
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            iBlockSource.getWorld().playAuxSFX(1002, iBlockSource.getBlockPos(), 0);
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            EnumFacing var6 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            IPosition var7 = BlockDispenser.getDispensePosition(iBlockSource);
            double var8 = var7.getX() + var6.getFrontOffsetX() * 0.3F;
            double var10 = var7.getY() + var6.getFrontOffsetY() * 0.3F;
            double var12 = var7.getZ() + var6.getFrontOffsetZ() * 0.3F;
            World var14 = iBlockSource.getWorld();
            Random var15 = var14.rand;
            double var16 = var15.nextGaussian() * 0.05 + var6.getFrontOffsetX();
            double var18 = var15.nextGaussian() * 0.05 + var6.getFrontOffsetY();
            double var20 = var15.nextGaussian() * 0.05 + var6.getFrontOffsetZ();
            var14.spawnEntityInWorld(new EntitySmallFireball(var14, var8, var10, var12, var16, var18, var20));
            itemStack.splitStack(1);
            return itemStack;
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            iBlockSource.getWorld().playAuxSFX(1009, iBlockSource.getBlockPos(), 0);
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            EnumFacing var6 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            World var7 = iBlockSource.getWorld();
            double var8 = iBlockSource.getX() + var6.getFrontOffsetX() * 1.125F;
            double var10 = iBlockSource.getY() + var6.getFrontOffsetY() * 1.125F;
            double var12 = iBlockSource.getZ() + var6.getFrontOffsetZ() * 1.125F;
            BlockPos var14 = iBlockSource.getBlockPos().offset(var6);
            Material var15 = var7.getBlockState(var14).getBlock().getMaterial();
            double var16;
            if (Material.water.equals(var15)) {
               var16 = 1.0;
            } else {
               if (!Material.air.equals(var15) || !Material.water.equals(var7.getBlockState(var14.down()).getBlock().getMaterial())) {
                  return this.field_150842_b.dispense(iBlockSource, itemStack);
               }

               var16 = 0.0;
            }

            EntityBoat var18 = new EntityBoat(var7, var8, var10 + var16, var12);
            var7.spawnEntityInWorld(var18);
            itemStack.splitStack(1);
            return itemStack;
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
         }
      });
      BehaviorDefaultDispenseItem var3 = new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            ItemBucket var6 = (ItemBucket)itemStack.getItem();
            BlockPos var7 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
            if (var6.tryPlaceContainedLiquid(iBlockSource.getWorld(), var7)) {
               itemStack.setItem(Items.bucket);
               itemStack.stackSize = 1;
               return itemStack;
            } else {
               return this.field_150841_b.dispense(iBlockSource, itemStack);
            }
         }
      };
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, var3);
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, var3);
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            World var6 = iBlockSource.getWorld();
            BlockPos var7 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
            IBlockState var8 = var6.getBlockState(var7);
            Block var9 = var8.getBlock();
            Material var10 = var9.getMaterial();
            Item var11;
            if (Material.water.equals(var10) && var9 instanceof BlockLiquid && var8.getValue(BlockLiquid.LEVEL) == 0) {
               var11 = Items.water_bucket;
            } else {
               if (!Material.lava.equals(var10) || !(var9 instanceof BlockLiquid) || var8.getValue(BlockLiquid.LEVEL) != 0) {
                  return super.dispenseStack(iBlockSource, itemStack);
               }

               var11 = Items.lava_bucket;
            }

            var6.setBlockToAir(var7);
            if (--itemStack.stackSize == 0) {
               itemStack.setItem(var11);
               itemStack.stackSize = 1;
            } else if (iBlockSource.<TileEntityDispenser>getBlockTileEntity().addItemStack(new ItemStack(var11)) < 0) {
               this.field_150840_b.dispense(iBlockSource, new ItemStack(var11));
            }

            return itemStack;
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem() {
         private boolean field_150839_b = true;

         @Override
         protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            World var6 = iBlockSource.getWorld();
            BlockPos var7 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
            if (var6.isAirBlock(var7)) {
               var6.setBlockState(var7, Blocks.fire.getDefaultState());
               if (itemStack.attemptDamageItem(1, var6.rand)) {
                  itemStack.stackSize = 0;
               }
            } else if (var6.getBlockState(var7).getBlock() == Blocks.tnt) {
               Blocks.tnt.onBlockDestroyedByPlayer(var6, var7, Blocks.tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.TRUE));
               var6.setBlockToAir(var7);
            } else {
               this.field_150839_b = false;
            }

            return itemStack;
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            if (this.field_150839_b) {
               iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            } else {
               iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
            }
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem() {
         private boolean field_150838_b = true;

         @Override
         protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(itemStack.getMetadata())) {
               World var6 = iBlockSource.getWorld();
               BlockPos var7 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
               if (ItemDye.applyBonemeal(itemStack, var6, var7)) {
                  if (!var6.isRemote) {
                     var6.playAuxSFX(2005, var7, 0);
                  }
               } else {
                  this.field_150838_b = false;
               }

               return itemStack;
            } else {
               return super.dispenseStack(iBlockSource, itemStack);
            }
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            if (this.field_150838_b) {
               iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            } else {
               iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
            }
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem() {
         @Override
         protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            World var3 = iBlockSource.getWorld();
            BlockPos var4 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
            EntityTNTPrimed var5 = new EntityTNTPrimed(var3, var4.getX() + 0.5, var4.getY(), var4.getZ() + 0.5, null);
            var3.spawnEntityInWorld(var5);
            var3.playSoundAtEntity(var5, "game.tnt.primed", 1.0F, 1.0F);
            itemStack.stackSize--;
            return itemStack;
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new BehaviorDefaultDispenseItem() {
         private boolean field_179240_b = true;

         @Override
         protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            World var6 = iBlockSource.getWorld();
            EnumFacing var7 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            BlockPos var8 = iBlockSource.getBlockPos().offset(var7);
            BlockSkull var9 = Blocks.skull;
            if (!var6.isAirBlock(var8) || !var9.canDispenserPlace(var6, var8, itemStack)) {
               this.field_179240_b = false;
            } else if (!var6.isRemote) {
               var6.setBlockState(var8, var9.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 3);
               TileEntity var10 = var6.getTileEntity(var8);
               if (var10 instanceof TileEntitySkull) {
                  if (itemStack.getMetadata() == 3) {
                     GameProfile var11 = null;
                     if (itemStack.hasTagCompound()) {
                        NBTTagCompound var12 = itemStack.getTagCompound();
                        if (var12.hasKey("SkullOwner", 10)) {
                           var11 = NBTUtil.readGameProfileFromNBT(var12.getCompoundTag("SkullOwner"));
                        } else if (var12.hasKey("SkullOwner", 8)) {
                           String var13 = var12.getString("SkullOwner");
                           if (!StringUtils.isNullOrEmpty(var13)) {
                              var11 = new GameProfile(null, var13);
                           }
                        }
                     }

                     ((TileEntitySkull)var10).setPlayerProfile(var11);
                  } else {
                     ((TileEntitySkull)var10).setType(itemStack.getMetadata());
                  }

                  ((TileEntitySkull)var10).setSkullRotation(var7.getOpposite().getHorizontalIndex() * 4);
                  Blocks.skull.checkWitherSpawn(var6, var8, (TileEntitySkull)var10);
               }

               itemStack.stackSize--;
            }

            return itemStack;
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            if (this.field_179240_b) {
               iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            } else {
               iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
            }
         }
      });
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new BehaviorDefaultDispenseItem() {
         private boolean field_179241_b = true;

         @Override
         protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            World var6 = iBlockSource.getWorld();
            BlockPos var7 = iBlockSource.getBlockPos().offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
            BlockPumpkin var8 = (BlockPumpkin)Blocks.pumpkin;
            if (var6.isAirBlock(var7) && var8.canDispenserPlace(var6, var7)) {
               if (!var6.isRemote) {
                  var6.setBlockState(var7, var8.getDefaultState(), 3);
               }

               itemStack.stackSize--;
            } else {
               this.field_179241_b = false;
            }

            return itemStack;
         }

         @Override
         protected void playDispenseSound(IBlockSource iBlockSource) {
            if (this.field_179241_b) {
               iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            } else {
               iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
            }
         }
      });
   }

   public static void register() {
      if (!alreadyRegistered) {
         alreadyRegistered = true;
         if (LOGGER.isDebugEnabled()) {
            redirectOutputToLog();
         }

         Block.registerBlocks();
         BlockFire.init();
         Item.registerItems();
         StatList.init();
         registerDispenserBehaviors();
      }
   }

   private static void redirectOutputToLog() {
      System.setErr(new LoggingPrintStream("STDERR", System.err));
      System.setOut(new LoggingPrintStream("STDOUT", SYSOUT));
   }

   public static void printToSYSOUT(String string) {
      SYSOUT.println(string);
   }
}
