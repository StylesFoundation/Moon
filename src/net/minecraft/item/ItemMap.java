package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase {
   protected ItemMap() {
      this.setHasSubtypes(true);
   }

   public static MapData Method528(int integer, World world) {
      String var5 = "map_" + integer;
      MapData var6 = (MapData)world.loadItemData(MapData.class, var5);
      if (var6 == null) {
         var6 = new MapData(var5);
         world.setItemData(var5, var6);
      }

      return var6;
   }

   public MapData getMapData(ItemStack itemStack, World world) {
      String var6 = "map_" + itemStack.getMetadata();
      MapData var7 = (MapData)world.loadItemData(MapData.class, var6);
      if (var7 == null && !world.isRemote) {
         itemStack.setItemDamage(world.getUniqueDataId("map"));
         var6 = "map_" + itemStack.getMetadata();
         var7 = new MapData(var6);
         var7.scale = 3;
         var7.calculateMapCenter(world.getWorldInfo().getSpawnX(), world.getWorldInfo().getSpawnZ(), var7.scale);
         var7.dimension = (byte)world.provider.getDimensionId();
         var7.markDirty();
         world.setItemData(var6, var7);
      }

      return var7;
   }

   public void updateMapData(World world, Entity entity, MapData mapData) {
      if (world.provider.getDimensionId() == mapData.dimension && entity instanceof EntityPlayer) {
         int var7 = 1 << mapData.scale;
         int var8 = mapData.xCenter;
         int var9 = mapData.zCenter;
         int var10 = MathHelper.floor_double(entity.posX - var8) / var7 + 64;
         int var11 = MathHelper.floor_double(entity.posZ - var9) / var7 + 64;
         int var12 = 128 / var7;
         if (world.provider.getHasNoSky()) {
            var12 /= 2;
         }

         MapData.MapInfo var13 = mapData.getMapInfo((EntityPlayer)entity);
         var13.field_82569_d++;
         boolean var14 = false;

         for (int var15 = var10 - var12 + 1; var15 < var10 + var12; var15++) {
            if ((var15 & 15) == (var13.field_82569_d & 15) || var14) {
               var14 = false;
               double var16 = 0.0;

               for (int var18 = var11 - var12 - 1; var18 < var11 + var12; var18++) {
                  if (var15 >= 0 && var18 >= -1 && var15 < 128 && var18 < 128) {
                     int var19 = var15 - var10;
                     int var20 = var18 - var11;
                     boolean var21 = var19 * var19 + var20 * var20 > (var12 - 2) * (var12 - 2);
                     int var22 = (var8 / var7 + var15 - 64) * var7;
                     int var23 = (var9 / var7 + var18 - 64) * var7;
                     HashMultiset var24 = HashMultiset.create();
                     Chunk var25 = world.getChunkFromBlockCoords(new BlockPos(var22, 0, var23));
                     if (!var25.isEmpty()) {
                        int var26 = var22 & 15;
                        int var27 = var23 & 15;
                        int var28 = 0;
                        double var29 = 0.0;
                        if (world.provider.getHasNoSky()) {
                           int var31 = var22 + var23 * 231871;
                           var31 = var31 * var31 * 31287121 + var31 * 11;
                           if ((var31 >> 20 & 1) == 0) {
                              var24.add(Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 10);
                           } else {
                              var24.add(
                                 Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE)), 100
                              );
                           }

                           var29 = 100.0;
                        } else {
                           BlockPos.MutableBlockPos var40 = new BlockPos.MutableBlockPos();

                           for (int var32 = 0; var32 < var7; var32++) {
                              for (int var33 = 0; var33 < var7; var33++) {
                                 int var34 = var25.getHeightValue(var32 + var26, var33 + var27) + 1;
                                 IBlockState var35 = Blocks.air.getDefaultState();
                                 if (var34 > 1) {
                                    do {
                                       var35 = var25.getBlockState(var40.set(var32 + var26, --var34, var33 + var27));
                                    } while (var35.getBlock().getMapColor(var35) == MapColor.airColor && var34 > 0);

                                    if (var34 > 0 && var35.getBlock().getMaterial().isLiquid()) {
                                       int var36 = var34 - 1;

                                       Block var37;
                                       do {
                                          var37 = var25.getBlock(var32 + var26, var36--, var33 + var27);
                                          var28++;
                                       } while (var36 > 0 && var37.getMaterial().isLiquid());
                                    }
                                 }

                                 var29 += (double)var34 / (var7 * var7);
                                 var24.add(var35.getBlock().getMapColor(var35));
                              }
                           }
                        }

                        var28 /= var7 * var7;
                        double var41 = (var29 - var16) * 4.0 / (var7 + 4) + ((var15 + var18 & 1) - 0.5) * 0.4;
                        byte var43 = 1;
                        if (var41 > 0.6) {
                           var43 = 2;
                        }

                        if (var41 < -0.6) {
                           var43 = 0;
                        }

                        MapColor var44 = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(var24), MapColor.airColor);
                        if (var44 == MapColor.waterColor) {
                           var41 = var28 * 0.1 + (var15 + var18 & 1) * 0.2;
                           var43 = 1;
                           if (var41 < 0.5) {
                              var43 = 2;
                           }

                           if (var41 > 0.9) {
                              var43 = 0;
                           }
                        }

                        var16 = var29;
                        if (var18 >= 0 && var19 * var19 + var20 * var20 < var12 * var12 && (!var21 || (var15 + var18 & 1) != 0)) {
                           byte var45 = mapData.colors[var15 + var18 * 128];
                           byte var46 = (byte)(var44.colorIndex * 4 + var43);
                           if (var45 != var46) {
                              mapData.colors[var15 + var18 * 128] = var46;
                              mapData.updateMapData(var15, var18);
                              var14 = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onUpdate(ItemStack itemStack, World world, Entity entity, int integer, boolean boolean5) {
      if (!world.isRemote) {
         MapData var9 = this.getMapData(itemStack, world);
         if (entity instanceof EntityPlayer var10) {
            var9.updateVisiblePlayers(var10, itemStack);
         }

         if (boolean5) {
            this.updateMapData(world, entity, var9);
         }
      }
   }

   @Override
   public Packet createMapDataPacket(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      return this.getMapData(itemStack, world).getMapPacket(itemStack, world, entityPlayer);
   }

   @Override
   public void onCreated(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("map_is_scaling")) {
         MapData var7 = Items.filled_map.getMapData(itemStack, world);
         itemStack.setItemDamage(world.getUniqueDataId("map"));
         MapData var8 = new MapData("map_" + itemStack.getMetadata());
         var8.scale = (byte)(var7.scale + 1);
         if (var8.scale > 4) {
            var8.scale = 4;
         }

         var8.calculateMapCenter(var7.xCenter, var7.zCenter, var8.scale);
         var8.dimension = var7.dimension;
         var8.markDirty();
         world.setItemData("map_" + itemStack.getMetadata(), var8);
      }
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      MapData var8 = this.getMapData(itemStack, entityPlayer.worldObj);
      if (boolean4) {
         if (var8 == null) {
            list.add("Unknown map");
         } else {
            list.add("Scaling at 1:" + (1 << var8.scale));
            list.add("(Level " + var8.scale + "/4)");
         }
      }
   }
}
