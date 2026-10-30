package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.Matches;
import net.optifine.model.BlockModelUtils;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.render.RenderEnv;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.TileEntityUtils;

public class ConnectedTextures {
   private static Map[] spriteQuadMaps = null;
   private static Map[] spriteQuadFullMaps = null;
   private static Map[][] spriteQuadCompactMaps = null;
   private static ConnectedProperties[][] blockProperties = null;
   private static ConnectedProperties[][] tileProperties = null;
   private static boolean multipass = false;
   public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
   private static TextureAtlasSprite emptySprite = null;
   private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH};
   private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH};
   private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[]{BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[]{BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[]{BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP};
   private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN};
   private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS = new BlockDir[]{BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN};
   private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[]{BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST};
   private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[]{BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST};
   private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[]{BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST};
   private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[]{BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST};
   private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[]{BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH};
   private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[]{BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH};
   private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[]{BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST};
   private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS = new BlockDir[]{BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH};
   public static final TextureAtlasSprite SPRITE_DEFAULT = new TextureAtlasSprite("<default>");

   public static BakedQuad[] getConnectedTexture(
      IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, BakedQuad bakedQuad, RenderEnv renderEnv
   ) {
      TextureAtlasSprite var8 = bakedQuad.getSprite();
      if (var8 == null) {
         return renderEnv.getArrayQuadsCtm(bakedQuad);
      } else {
         iBlockState.getBlock();
         if (skipConnectedTexture(iBlockAccess, iBlockState, blockPos, bakedQuad, renderEnv)) {
            bakedQuad = getQuad(emptySprite, bakedQuad);
            return renderEnv.getArrayQuadsCtm(bakedQuad);
         } else {
            EnumFacing var10 = bakedQuad.getFace();
            return getConnectedTextureMultiPass(iBlockAccess, iBlockState, blockPos, var10, bakedQuad, renderEnv);
         }
      }
   }

   private static boolean skipConnectedTexture(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, BakedQuad bakedQuad, RenderEnv renderEnv) {
      Block var8 = iBlockState.getBlock();
      if (var8 instanceof BlockPane) {
         TextureAtlasSprite var9 = bakedQuad.getSprite();
         if (var9.getIconName().startsWith("minecraft:blocks/glass_pane_top")) {
            IBlockState var17 = iBlockAccess.getBlockState(blockPos.offset(bakedQuad.getFace()));
            return var17 == iBlockState;
         }
      }

      if (var8 instanceof BlockPane) {
         EnumFacing var16 = bakedQuad.getFace();
         if (var16 != EnumFacing.UP && var16 != EnumFacing.DOWN) {
            return false;
         } else if (!bakedQuad.isFaceQuad()) {
            return false;
         } else {
            BlockPos var10 = blockPos.offset(bakedQuad.getFace());
            IBlockState var11 = iBlockAccess.getBlockState(var10);
            if (var11.getBlock() != var8) {
               return false;
            } else if (var8 == Blocks.stained_glass_pane && var11.getValue(BlockStainedGlassPane.COLOR) != iBlockState.getValue(BlockStainedGlassPane.COLOR)) {
               return false;
            } else {
               var11 = var11.getBlock().getActualState(var11, iBlockAccess, var10);
               double var12 = bakedQuad.getMidX();
               if (var12 < 0.4) {
                  return var11.getValue(BlockPane.WEST);
               } else if (var12 > 0.6) {
                  return var11.getValue(BlockPane.EAST);
               } else {
                  double var14 = bakedQuad.getMidZ();
                  if (var14 < 0.4) {
                     return var11.getValue(BlockPane.NORTH);
                  } else {
                     return var14 <= 0.6 ? true : var11.getValue(BlockPane.SOUTH);
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   protected static BakedQuad[] getQuads(TextureAtlasSprite textureAtlasSprite, BakedQuad bakedQuad, RenderEnv renderEnv) {
      if (textureAtlasSprite == null) {
         return null;
      }

      if (textureAtlasSprite == SPRITE_DEFAULT) {
         return renderEnv.getArrayQuadsCtm(bakedQuad);
      }

      BakedQuad var6 = getQuad(textureAtlasSprite, bakedQuad);
      return renderEnv.getArrayQuadsCtm(var6);
   }

   private static synchronized BakedQuad getQuad(TextureAtlasSprite textureAtlasSprite, BakedQuad bakedQuad) {
      if (spriteQuadMaps == null) {
         return bakedQuad;
      }

      int var5 = textureAtlasSprite.getIndexInMap();
      if (var5 >= 0 && var5 < spriteQuadMaps.length) {
         Map var6 = spriteQuadMaps[var5];
         if (var6 == null) {
            var6 = new IdentityHashMap(1);
            spriteQuadMaps[var5] = var6;
         }

         BakedQuad var7 = (BakedQuad)var6.get(bakedQuad);
         if (var7 == null) {
            var7 = makeSpriteQuad(bakedQuad, textureAtlasSprite);
            var6.put(bakedQuad, var7);
         }

         return var7;
      } else {
         return bakedQuad;
      }
   }

   private static synchronized BakedQuad getQuadFull(TextureAtlasSprite textureAtlasSprite, BakedQuad bakedQuad, int integer) {
      if (spriteQuadFullMaps == null) {
         return null;
      }

      if (textureAtlasSprite == null) {
         return null;
      }

      int var6 = textureAtlasSprite.getIndexInMap();
      if (var6 >= 0 && var6 < spriteQuadFullMaps.length) {
         Map var7 = spriteQuadFullMaps[var6];
         if (var7 == null) {
            var7 = new EnumMap<>(EnumFacing.class);
            spriteQuadFullMaps[var6] = var7;
         }

         EnumFacing var8 = bakedQuad.getFace();
         BakedQuad var9 = (BakedQuad)var7.get(var8);
         if (var9 == null) {
            var9 = BlockModelUtils.makeBakedQuad(var8, textureAtlasSprite, integer);
            var7.put(var8, var9);
         }

         return var9;
      } else {
         return null;
      }
   }

   private static BakedQuad makeSpriteQuad(BakedQuad bakedQuad, TextureAtlasSprite textureAtlasSprite) {
      int[] var5 = (int[])bakedQuad.getVertexData().clone();
      TextureAtlasSprite var6 = bakedQuad.getSprite();

      for (int var7 = 0; var7 < 4; var7++) {
         fixVertex(var5, var7, var6, textureAtlasSprite);
      }

      return new BakedQuad(var5, bakedQuad.getTintIndex(), bakedQuad.getFace(), textureAtlasSprite);
   }

   private static void fixVertex(int[] arr, int integer, TextureAtlasSprite textureAtlasSprite3, TextureAtlasSprite textureAtlasSprite4) {
      int var5 = arr.length / 4 * integer;
      float var6 = Float.intBitsToFloat(arr[var5 + 4]);
      float var7 = Float.intBitsToFloat(arr[var5 + 4 + 1]);
      double var8 = textureAtlasSprite3.getSpriteU16(var6);
      double var10 = textureAtlasSprite3.getSpriteV16(var7);
      arr[var5 + 4] = Float.floatToRawIntBits(textureAtlasSprite4.getInterpolatedU(var8));
      arr[var5 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite4.getInterpolatedV(var10));
   }

   private static BakedQuad[] getConnectedTextureMultiPass(
      IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, EnumFacing enumFacing, BakedQuad bakedQuad, RenderEnv renderEnv
   ) {
      BakedQuad[] var9 = getConnectedTextureSingle(iBlockAccess, iBlockState, blockPos, enumFacing, bakedQuad, true, 0, renderEnv);
      if (!multipass) {
         return var9;
      }

      if (var9.length == 1 && var9[0] == bakedQuad) {
         return var9;
      }

      List var10 = renderEnv.getListQuadsCtmMultipass(var9);

      for (int var11 = 0; var11 < var10.size(); var11++) {
         BakedQuad var12 = (BakedQuad)var10.get(var11);

         for (int var13 = 0; var13 < 3; var13++) {
            BakedQuad[] var14 = getConnectedTextureSingle(iBlockAccess, iBlockState, blockPos, enumFacing, var12, false, var13 + 1, renderEnv);
            if (var14.length != 1 || var14[0] == var12) {
               break;
            }

            var12 = var14[0];
         }

         var10.set(var11, var12);
      }

      for (int var15 = 0; var15 < var9.length; var15++) {
         var9[var15] = (BakedQuad)var10.get(var15);
      }

      return var9;
   }

   public static BakedQuad[] getConnectedTextureSingle(
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      EnumFacing enumFacing,
      BakedQuad bakedQuad,
      boolean boolean6,
      int integer,
      RenderEnv renderEnv
   ) {
      iBlockState.getBlock();
      if (!(iBlockState instanceof BlockStateBase var12)) {
         return renderEnv.getArrayQuadsCtm(bakedQuad);
      } else {
         TextureAtlasSprite var13 = bakedQuad.getSprite();
         if (tileProperties != null) {
            int var14 = var13.getIndexInMap();
            if (var14 >= 0 && var14 < tileProperties.length) {
               ConnectedProperties[] var15 = tileProperties[var14];
               if (var15 != null) {
                  int var16 = getSide(enumFacing);

                  for (ConnectedProperties var20 : var15) {
                     if (var20 != null && var20.matchesBlockId(var12.getBlockId())) {
                        BakedQuad[] var21 = getConnectedTexture(var20, iBlockAccess, var12, blockPos, var16, bakedQuad, integer, renderEnv);
                        if (var21 != null) {
                           return var21;
                        }
                     }
                  }
               }
            }
         }

         if (blockProperties != null && boolean6) {
            int var22 = renderEnv.getBlockId();
            if (var22 >= 0 && var22 < blockProperties.length) {
               ConnectedProperties[] var23 = blockProperties[var22];
               if (var23 != null) {
                  int var24 = getSide(enumFacing);

                  for (ConnectedProperties var28 : var23) {
                     if (var28 != null && var28.matchesIcon(var13)) {
                        BakedQuad[] var29 = getConnectedTexture(var28, iBlockAccess, var12, blockPos, var24, bakedQuad, integer, renderEnv);
                        if (var29 != null) {
                           return var29;
                        }
                     }
                  }
               }
            }
         }

         return renderEnv.getArrayQuadsCtm(bakedQuad);
      }
   }

   public static int getSide(EnumFacing enumFacing) {
      if (enumFacing == null) {
         return -1;
      }

      switch (enumFacing) {
         case DOWN:
            return 0;
         case UP:
            return 1;
         case EAST:
            return 5;
         case WEST:
            return 4;
         case NORTH:
            return 2;
         case SOUTH:
            return 3;
         default:
            return -1;
      }
   }

   private static EnumFacing getFacing(int integer) {
      switch (integer) {
         case 0:
            return EnumFacing.DOWN;
         case 1:
         default:
            return EnumFacing.UP;
         case 2:
            return EnumFacing.NORTH;
         case 3:
            return EnumFacing.SOUTH;
         case 4:
            return EnumFacing.WEST;
         case 5:
            return EnumFacing.EAST;
      }
   }

   private static BakedQuad[] getConnectedTexture(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      BlockStateBase blockStateBase,
      BlockPos blockPos,
      int integer5,
      BakedQuad bakedQuad,
      int integer7,
      RenderEnv renderEnv
   ) {
      int var11 = 0;
      int var12 = blockStateBase.getMetadata();
      int var13 = var12;
      Block var14 = blockStateBase.getBlock();
      if (var14 instanceof BlockRotatedPillar) {
         var11 = getWoodAxis(integer5, var12);
         if (connectedProperties.getMetadataMax() <= 3) {
            var13 = var12 & 3;
         }
      }

      if (var14 instanceof BlockQuartz) {
         var11 = getQuartzAxis(integer5, var12);
         if (connectedProperties.getMetadataMax() <= 2 && var13 > 2) {
            var13 = 2;
         }
      }

      if (!connectedProperties.matchesBlock(blockStateBase.getBlockId(), var13)) {
         return null;
      }

      if (integer5 >= 0 && connectedProperties.faces != 63) {
         int var15 = integer5;
         if (var11 != 0) {
            var15 = fixSideByAxis(integer5, var11);
         }

         if ((1 << var15 & connectedProperties.faces) == 0) {
            return null;
         }
      }

      int var17 = blockPos.getY();
      if (connectedProperties.heights != null && !connectedProperties.heights.isInRange(var17)) {
         return null;
      }

      if (connectedProperties.biomes != null) {
         BiomeGenBase var16 = iBlockAccess.getBiomeGenForCoords(blockPos);
         if (!connectedProperties.matchesBiome(var16)) {
            return null;
         }
      }

      if (connectedProperties.nbtName != null) {
         String var18 = TileEntityUtils.getTileEntityName(iBlockAccess, blockPos);
         if (!connectedProperties.nbtName.matchesValue(var18)) {
            return null;
         }
      }

      TextureAtlasSprite var19 = bakedQuad.getSprite();
      switch (connectedProperties.method) {
         case 1:
            return getQuads(
               getConnectedTextureCtm(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, var19, var12, renderEnv),
               bakedQuad,
               renderEnv
            );
         case 2:
            return getQuads(
               getConnectedTextureHorizontal(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, var19, var12), bakedQuad, renderEnv
            );
         case 3:
            return getQuads(
               getConnectedTextureTop(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, var19, var12), bakedQuad, renderEnv
            );
         case 4:
            return getQuads(getConnectedTextureRandom(connectedProperties, iBlockAccess, blockStateBase, blockPos, integer5), bakedQuad, renderEnv);
         case 5:
            return getQuads(getConnectedTextureRepeat(connectedProperties, blockPos, integer5), bakedQuad, renderEnv);
         case 6:
            return getQuads(
               getConnectedTextureVertical(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, var19, var12), bakedQuad, renderEnv
            );
         case 7:
            return getQuads(getConnectedTextureFixed(connectedProperties), bakedQuad, renderEnv);
         case 8:
            return getQuads(
               getConnectedTextureHorizontalVertical(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, var19, var12),
               bakedQuad,
               renderEnv
            );
         case 9:
            return getQuads(
               getConnectedTextureVerticalHorizontal(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, var19, var12),
               bakedQuad,
               renderEnv
            );
         case 10:
            if (integer7 == 0) {
               return getConnectedTextureCtmCompact(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, bakedQuad, var12, renderEnv);
            }
         default:
            return null;
         case 11:
            return getConnectedTextureOverlay(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, bakedQuad, var12, renderEnv);
         case 12:
            return getConnectedTextureOverlayFixed(connectedProperties, bakedQuad, renderEnv);
         case 13:
            return getConnectedTextureOverlayRandom(connectedProperties, iBlockAccess, blockStateBase, blockPos, integer5, bakedQuad, renderEnv);
         case 14:
            return getConnectedTextureOverlayRepeat(connectedProperties, blockPos, integer5, bakedQuad, renderEnv);
         case 15:
            return getConnectedTextureOverlayCtm(connectedProperties, iBlockAccess, blockStateBase, blockPos, var11, integer5, bakedQuad, var12, renderEnv);
      }
   }

   private static int fixSideByAxis(int integer1, int integer2) {
      switch (integer2) {
         case 1:
            switch (integer1) {
               case 0:
                  return 2;
               case 1:
                  return 3;
               case 2:
                  return 1;
               case 3:
                  return 0;
               default:
                  return integer1;
            }
         case 2:
            switch (integer1) {
               case 0:
                  return 4;
               case 1:
                  return 5;
               case 2:
               case 3:
               default:
                  return integer1;
               case 4:
                  return 1;
               case 5:
                  return 0;
            }
         default:
            return integer1;
      }
   }

   private static int getWoodAxis(int integer1, int integer2) {
      int var5 = (integer2 & 12) >> 2;
      switch (var5) {
         case 1:
            return 2;
         case 2:
            return 1;
         default:
            return 0;
      }
   }

   private static int getQuartzAxis(int integer1, int integer2) {
      switch (integer2) {
         case 3:
            return 2;
         case 4:
            return 1;
         default:
            return 0;
      }
   }

   private static TextureAtlasSprite getConnectedTextureRandom(
      ConnectedProperties connectedProperties, IBlockAccess iBlockAccess, BlockStateBase blockStateBase, BlockPos blockPos, int integer
   ) {
      if (connectedProperties.tileIcons.length == 1) {
         return connectedProperties.tileIcons[0];
      }

      int var8 = integer / connectedProperties.symmetry * connectedProperties.symmetry;
      if (connectedProperties.linked) {
         BlockPos var9 = blockPos.down();

         for (IBlockState var10 = iBlockAccess.getBlockState(var9); var10.getBlock() == blockStateBase.getBlock(); var10 = iBlockAccess.getBlockState(var9)) {
            blockPos = var9;
            var9 = var9.down();
            if (var9.getY() < 0) {
               break;
            }
         }
      }

      int var14 = Config.getRandom(blockPos, var8) & 2147483647;

      for (int var15 = 0; var15 < connectedProperties.randomLoops; var15++) {
         var14 = Config.intHash(var14);
      }

      int var16 = 0;
      if (connectedProperties.weights == null) {
         var16 = var14 % connectedProperties.tileIcons.length;
      } else {
         int var11 = var14 % connectedProperties.sumAllWeights;
         int[] var12 = connectedProperties.sumWeights;

         for (int var13 = 0; var13 < var12.length; var13++) {
            if (var11 < var12[var13]) {
               var16 = var13;
               break;
            }
         }
      }

      return connectedProperties.tileIcons[var16];
   }

   private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties connectedProperties) {
      return connectedProperties.tileIcons[0];
   }

   private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties connectedProperties, BlockPos blockPos, int integer) {
      if (connectedProperties.tileIcons.length == 1) {
         return connectedProperties.tileIcons[0];
      }

      int var6 = blockPos.getX();
      int var7 = blockPos.getY();
      int var8 = blockPos.getZ();
      int var9 = 0;
      int var10 = 0;
      switch (integer) {
         case 0:
            var9 = var6;
            var10 = -var8 - 1;
            break;
         case 1:
            var9 = var6;
            var10 = var8;
            break;
         case 2:
            var9 = -var6 - 1;
            var10 = -var7;
            break;
         case 3:
            var9 = var6;
            var10 = -var7;
            break;
         case 4:
            var9 = var8;
            var10 = -var7;
            break;
         case 5:
            var9 = -var8 - 1;
            var10 = -var7;
      }

      var9 %= connectedProperties.width;
      var10 %= connectedProperties.height;
      if (var9 < 0) {
         var9 += connectedProperties.width;
      }

      if (var10 < 0) {
         var10 += connectedProperties.height;
      }

      int var11 = var10 * connectedProperties.width + var9;
      return connectedProperties.tileIcons[var11];
   }

   private static TextureAtlasSprite getConnectedTextureCtm(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8,
      RenderEnv renderEnv
   ) {
      int var9 = getConnectedTextureCtmIndex(
         connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, textureAtlasSprite, integer8, renderEnv
      );
      return connectedProperties.tileIcons[var9];
   }

   private static synchronized BakedQuad[] getConnectedTextureCtmCompact(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      BakedQuad bakedQuad,
      int integer8,
      RenderEnv renderEnv
   ) {
      TextureAtlasSprite var9 = bakedQuad.getSprite();
      return ConnectedTexturesCompact.getConnectedTextureCtmCompact(
         getConnectedTextureCtmIndex(connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, var9, integer8, renderEnv),
         connectedProperties,
         integer6,
         bakedQuad,
         renderEnv
      );
   }

   private static BakedQuad[] getConnectedTextureOverlay(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      BakedQuad bakedQuad,
      int integer8,
      RenderEnv renderEnv
   ) {
      if (!bakedQuad.isFullQuad()) {
         return null;
      }

      TextureAtlasSprite var12 = bakedQuad.getSprite();
      BlockDir[] var13 = Method517(integer6, integer5);
      boolean[] var14 = renderEnv.getBorderFlags();

      for (int var15 = 0; var15 < 4; var15++) {
         var14[var15] = isNeighbourOverlay(connectedProperties, iBlockAccess, iBlockState, var13[var15].offset(blockPos), integer6, var12, integer8);
      }

      ListQuadsOverlay var25 = renderEnv.getListQuadsOverlay(connectedProperties.layer);

      try {
         if (!var14[0] || !var14[1] || !var14[2] || !var14[3]) {
            if (var14[0] && var14[1] && var14[2]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[5], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               return (BakedQuad[])null;
            }

            if (var14[0] && var14[2] && var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[6], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               return (BakedQuad[])null;
            }

            if (var14[1] && var14[2] && var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[12], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               return (BakedQuad[])null;
            }

            if (var14[0] && var14[1] && var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[13], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               return (BakedQuad[])null;
            }

            BlockDir[] var17 = Method518(integer6, integer5);
            boolean[] var18 = renderEnv.getBorderFlags2();

            for (int var19 = 0; var19 < 4; var19++) {
               var18[var19] = isNeighbourOverlay(connectedProperties, iBlockAccess, iBlockState, var17[var19].offset(blockPos), integer6, var12, integer8);
            }

            if (var14[1] && var14[2]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[3], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               if (var18[3]) {
                  var25.addQuad(getQuadFull(connectedProperties.tileIcons[16], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               }

               return (BakedQuad[])null;
            }

            if (var14[0] && var14[2]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[4], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               if (var18[2]) {
                  var25.addQuad(getQuadFull(connectedProperties.tileIcons[14], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               }

               return (BakedQuad[])null;
            }

            if (var14[1] && var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[10], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               if (var18[1]) {
                  var25.addQuad(getQuadFull(connectedProperties.tileIcons[2], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               }

               return (BakedQuad[])null;
            }

            if (var14[0] && var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[11], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               if (var18[0]) {
                  var25.addQuad(getQuadFull(connectedProperties.tileIcons[0], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
               }

               return (BakedQuad[])null;
            }

            boolean[] var26 = renderEnv.getBorderFlags3();

            for (int var20 = 0; var20 < 4; var20++) {
               var26[var20] = isNeighbourMatching(connectedProperties, iBlockAccess, iBlockState, var13[var20].offset(blockPos), integer6, var12, integer8);
            }

            if (var14[0]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[9], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var14[1]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[7], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var14[2]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[1], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[15], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var18[0] && (var26[1] || var26[2]) && !var14[1] && !var14[2]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[0], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var18[1] && (var26[0] || var26[2]) && !var14[0] && !var14[2]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[2], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var18[2] && (var26[1] || var26[3]) && !var14[1] && !var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[14], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            if (var18[3] && (var26[0] || var26[3]) && !var14[0] && !var14[3]) {
               var25.addQuad(getQuadFull(connectedProperties.tileIcons[16], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
            }

            return (BakedQuad[])null;
         }

         var25.addQuad(getQuadFull(connectedProperties.tileIcons[8], bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
      } finally {
         if (var25.size() > 0) {
            renderEnv.setOverlaysRendered(true);
         }
      }

      return (BakedQuad[])null;
   }

   private static BakedQuad[] getConnectedTextureOverlayFixed(ConnectedProperties connectedProperties, BakedQuad bakedQuad, RenderEnv renderEnv) {
      if (!bakedQuad.isFullQuad()) {
         return null;
      }

      ListQuadsOverlay var6 = renderEnv.getListQuadsOverlay(connectedProperties.layer);

      try {
         TextureAtlasSprite var8 = getConnectedTextureFixed(connectedProperties);
         if (var8 != null) {
            var6.addQuad(getQuadFull(var8, bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
         }
      } finally {
         if (var6.size() > 0) {
            renderEnv.setOverlaysRendered(true);
         }
      }

      return (BakedQuad[])null;
   }

   private static BakedQuad[] getConnectedTextureOverlayRandom(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      BlockStateBase blockStateBase,
      BlockPos blockPos,
      int integer,
      BakedQuad bakedQuad,
      RenderEnv renderEnv
   ) {
      if (!bakedQuad.isFullQuad()) {
         return null;
      }

      ListQuadsOverlay var10 = renderEnv.getListQuadsOverlay(connectedProperties.layer);

      try {
         TextureAtlasSprite var12 = getConnectedTextureRandom(connectedProperties, iBlockAccess, blockStateBase, blockPos, integer);
         if (var12 != null) {
            var10.addQuad(getQuadFull(var12, bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
         }
      } finally {
         if (var10.size() > 0) {
            renderEnv.setOverlaysRendered(true);
         }
      }

      return (BakedQuad[])null;
   }

   private static BakedQuad[] getConnectedTextureOverlayRepeat(
      ConnectedProperties connectedProperties, BlockPos blockPos, int integer, BakedQuad bakedQuad, RenderEnv renderEnv
   ) {
      if (!bakedQuad.isFullQuad()) {
         return null;
      }

      ListQuadsOverlay var8 = renderEnv.getListQuadsOverlay(connectedProperties.layer);

      try {
         TextureAtlasSprite var10 = getConnectedTextureRepeat(connectedProperties, blockPos, integer);
         if (var10 != null) {
            var8.addQuad(getQuadFull(var10, bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
         }
      } finally {
         if (var8.size() > 0) {
            renderEnv.setOverlaysRendered(true);
         }
      }

      return (BakedQuad[])null;
   }

   private static BakedQuad[] getConnectedTextureOverlayCtm(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      BakedQuad bakedQuad,
      int integer8,
      RenderEnv renderEnv
   ) {
      if (!bakedQuad.isFullQuad()) {
         return null;
      }

      ListQuadsOverlay var12 = renderEnv.getListQuadsOverlay(connectedProperties.layer);

      try {
         TextureAtlasSprite var14 = getConnectedTextureCtm(
            connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, bakedQuad.getSprite(), integer8, renderEnv
         );
         if (var14 != null) {
            var12.addQuad(getQuadFull(var14, bakedQuad, connectedProperties.tintIndex), connectedProperties.tintBlockState);
         }
      } finally {
         if (var12.size() > 0) {
            renderEnv.setOverlaysRendered(true);
         }
      }

      return (BakedQuad[])null;
   }

   private static BlockDir[] Method517(int integer1, int integer2) {
      switch (integer1) {
         case 0:
            return SIDES_Y_NEG_DOWN;
         case 1:
            return SIDES_Y_POS_UP;
         case 2:
            if (integer2 == 1) {
               return SIDES_Z_NEG_NORTH_Z_AXIS;
            }

            return SIDES_Z_NEG_NORTH;
         case 3:
            return SIDES_Z_POS_SOUTH;
         case 4:
            return SIDES_X_NEG_WEST;
         case 5:
            if (integer2 == 2) {
               return SIDES_X_POS_EAST_X_AXIS;
            }

            return SIDES_X_POS_EAST;
         default:
            throw new IllegalArgumentException("Unknown side: " + integer1);
      }
   }

   private static BlockDir[] Method518(int integer1, int integer2) {
      switch (integer1) {
         case 0:
            return EDGES_Y_NEG_DOWN;
         case 1:
            return EDGES_Y_POS_UP;
         case 2:
            if (integer2 == 1) {
               return EDGES_Z_NEG_NORTH_Z_AXIS;
            }

            return EDGES_Z_NEG_NORTH;
         case 3:
            return EDGES_Z_POS_SOUTH;
         case 4:
            return EDGES_X_NEG_WEST;
         case 5:
            if (integer2 == 2) {
               return EDGES_X_POS_EAST_X_AXIS;
            }

            return EDGES_X_POS_EAST;
         default:
            throw new IllegalArgumentException("Unknown side: " + integer1);
      }
   }

   protected static Map[][] getSpriteQuadCompactMaps() {
      return spriteQuadCompactMaps;
   }

   private static int getConnectedTextureCtmIndex(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8,
      RenderEnv renderEnv
   ) {
      boolean[] var12 = renderEnv.getBorderFlags();
      switch (integer6) {
         case 0:
            var12[0] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
            var12[1] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
            var12[2] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
            var12[3] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var19 = blockPos.down();
               var12[0] = var12[0] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var19.west(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var19.east(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var19.north(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var19.south(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 1:
            var12[0] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
            var12[1] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
            var12[2] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
            var12[3] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var18 = blockPos.up();
               var12[0] = var12[0] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var18.west(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var18.east(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var18.south(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var18.north(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 2:
            var12[0] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
            var12[1] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
            var12[2] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            var12[3] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var17 = blockPos.north();
               var12[0] = var12[0] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var17.east(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var17.west(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var17.down(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var17.up(), integer6, textureAtlasSprite, integer8);
            }

            if (integer5 == 1) {
               switchValues(0, 1, var12);
               switchValues(2, 3, var12);
            }
            break;
         case 3:
            var12[0] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
            var12[1] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
            var12[2] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            var12[3] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var16 = blockPos.south();
               var12[0] = var12[0] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var16.west(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var16.east(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var16.down(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var16.up(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 4:
            var12[0] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
            var12[1] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
            var12[2] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            var12[3] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var15 = blockPos.west();
               var12[0] = var12[0] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var15.north(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var15.south(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var15.down(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var15.up(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 5:
            var12[0] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
            var12[1] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
            var12[2] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            var12[3] = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var13 = blockPos.east();
               var12[0] = var12[0] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var13.south(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var13.north(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var13.down(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, var13.up(), integer6, textureAtlasSprite, integer8);
            }

            if (integer5 == 2) {
               switchValues(0, 1, var12);
               switchValues(2, 3, var12);
            }
      }

      byte var20 = 0;
      if (var12[0] & !var12[1] & !var12[2] & !var12[3]) {
         var20 = 3;
      } else if (!var12[0] & var12[1] & !var12[2] & !var12[3]) {
         var20 = 1;
      } else if (!var12[0] & !var12[1] & var12[2] & !var12[3]) {
         var20 = 12;
      } else if (!var12[0] & !var12[1] & !var12[2] & var12[3]) {
         var20 = 36;
      } else if (var12[0] & var12[1] & !var12[2] & !var12[3]) {
         var20 = 2;
      } else if (!var12[0] & !var12[1] & var12[2] & var12[3]) {
         var20 = 24;
      } else if (var12[0] & !var12[1] & var12[2] & !var12[3]) {
         var20 = 15;
      } else if (var12[0] & !var12[1] & !var12[2] & var12[3]) {
         var20 = 39;
      } else if (!var12[0] & var12[1] & var12[2] & !var12[3]) {
         var20 = 13;
      } else if (!var12[0] & var12[1] & !var12[2] & var12[3]) {
         var20 = 37;
      } else if (!var12[0] & var12[1] & var12[2] & var12[3]) {
         var20 = 25;
      } else if (var12[0] & !var12[1] & var12[2] & var12[3]) {
         var20 = 27;
      } else if (var12[0] & var12[1] & !var12[2] & var12[3]) {
         var20 = 38;
      } else if (var12[0] & var12[1] & var12[2] & !var12[3]) {
         var20 = 14;
      } else if (var12[0] & var12[1] & var12[2] & var12[3]) {
         var20 = 26;
      }

      if (var20 == 0) {
         return var20;
      }

      if (!Config.isConnectedTexturesFancy()) {
         return var20;
      }

      switch (integer6) {
         case 0:
            var12[0] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().north(), integer6, textureAtlasSprite, integer8);
            var12[1] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().north(), integer6, textureAtlasSprite, integer8);
            var12[2] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().south(), integer6, textureAtlasSprite, integer8);
            var12[3] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().south(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var25 = blockPos.down();
               var12[0] = var12[0] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var25.east().north(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var25.west().north(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var25.east().south(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var25.west().south(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 1:
            var12[0] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().south(), integer6, textureAtlasSprite, integer8);
            var12[1] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().south(), integer6, textureAtlasSprite, integer8);
            var12[2] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().north(), integer6, textureAtlasSprite, integer8);
            var12[3] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().north(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var24 = blockPos.up();
               var12[0] = var12[0] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var24.east().south(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var24.west().south(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var24.east().north(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var24.west().north(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 2:
            var12[0] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().down(), integer6, textureAtlasSprite, integer8);
            var12[1] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().down(), integer6, textureAtlasSprite, integer8);
            var12[2] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().up(), integer6, textureAtlasSprite, integer8);
            var12[3] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().up(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var23 = blockPos.north();
               var12[0] = var12[0] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var23.west().down(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var23.east().down(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var23.west().up(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var23.east().up(), integer6, textureAtlasSprite, integer8);
            }

            if (integer5 == 1) {
               switchValues(0, 3, var12);
               switchValues(1, 2, var12);
            }
            break;
         case 3:
            var12[0] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().down(), integer6, textureAtlasSprite, integer8);
            var12[1] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().down(), integer6, textureAtlasSprite, integer8);
            var12[2] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east().up(), integer6, textureAtlasSprite, integer8);
            var12[3] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west().up(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var22 = blockPos.south();
               var12[0] = var12[0] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var22.east().down(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var22.west().down(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var22.east().up(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var22.west().up(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 4:
            var12[0] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down().south(), integer6, textureAtlasSprite, integer8);
            var12[1] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down().north(), integer6, textureAtlasSprite, integer8);
            var12[2] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up().south(), integer6, textureAtlasSprite, integer8);
            var12[3] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up().north(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var21 = blockPos.west();
               var12[0] = var12[0] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var21.down().south(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var21.down().north(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var21.up().south(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var21.up().north(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 5:
            var12[0] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down().north(), integer6, textureAtlasSprite, integer8);
            var12[1] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down().south(), integer6, textureAtlasSprite, integer8);
            var12[2] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up().north(), integer6, textureAtlasSprite, integer8);
            var12[3] = !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up().south(), integer6, textureAtlasSprite, integer8);
            if (connectedProperties.innerSeams) {
               BlockPos var14 = blockPos.east();
               var12[0] = var12[0] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var14.down().north(), integer6, textureAtlasSprite, integer8);
               var12[1] = var12[1] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var14.down().south(), integer6, textureAtlasSprite, integer8);
               var12[2] = var12[2] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var14.up().north(), integer6, textureAtlasSprite, integer8);
               var12[3] = var12[3] || isNeighbour(connectedProperties, iBlockAccess, iBlockState, var14.up().south(), integer6, textureAtlasSprite, integer8);
            }

            if (integer5 == 2) {
               switchValues(0, 3, var12);
               switchValues(1, 2, var12);
            }
      }

      if (var20 == 13 && var12[0]) {
         var20 = 4;
      } else if (var20 == 15 && var12[1]) {
         var20 = 5;
      } else if (var20 == 37 && var12[2]) {
         var20 = 16;
      } else if (var20 == 39 && var12[3]) {
         var20 = 17;
      } else if (var20 == 14 && var12[0] && var12[1]) {
         var20 = 7;
      } else if (var20 == 25 && var12[0] && var12[2]) {
         var20 = 6;
      } else if (var20 == 27 && var12[3] && var12[1]) {
         var20 = 19;
      } else if (var20 == 38 && var12[3] && var12[2]) {
         var20 = 18;
      } else if (var20 == 14 && !var12[0] && var12[1]) {
         var20 = 31;
      } else if (var20 == 25 && var12[0] && !var12[2]) {
         var20 = 30;
      } else if (var20 == 27 && !var12[3] && var12[1]) {
         var20 = 41;
      } else if (var20 == 38 && var12[3] && !var12[2]) {
         var20 = 40;
      } else if (var20 == 14 && var12[0] && !var12[1]) {
         var20 = 29;
      } else if (var20 == 25 && !var12[0] && var12[2]) {
         var20 = 28;
      } else if (var20 == 27 && var12[3] && !var12[1]) {
         var20 = 43;
      } else if (var20 == 38 && !var12[3] && var12[2]) {
         var20 = 42;
      } else if (var20 == 26 && var12[0] && var12[1] && var12[2] && var12[3]) {
         var20 = 46;
      } else if (var20 == 26 && !var12[0] && var12[1] && var12[2] && var12[3]) {
         var20 = 9;
      } else if (var20 == 26 && var12[0] && !var12[1] && var12[2] && var12[3]) {
         var20 = 21;
      } else if (var20 == 26 && var12[0] && var12[1] && !var12[2] && var12[3]) {
         var20 = 8;
      } else if (var20 == 26 && var12[0] && var12[1] && var12[2] && !var12[3]) {
         var20 = 20;
      } else if (var20 == 26 && var12[0] && var12[1] && !var12[2] && !var12[3]) {
         var20 = 11;
      } else if (var20 == 26 && !var12[0] && !var12[1] && var12[2] && var12[3]) {
         var20 = 22;
      } else if (var20 == 26 && !var12[0] && var12[1] && !var12[2] && var12[3]) {
         var20 = 23;
      } else if (var20 == 26 && var12[0] && !var12[1] && var12[2] && !var12[3]) {
         var20 = 10;
      } else if (var20 == 26 && var12[0] && !var12[1] && !var12[2] && var12[3]) {
         var20 = 34;
      } else if (var20 == 26 && !var12[0] && var12[1] && var12[2] && !var12[3]) {
         var20 = 35;
      } else if (var20 == 26 && var12[0] && !var12[1] && !var12[2] && !var12[3]) {
         var20 = 32;
      } else if (var20 == 26 && !var12[0] && var12[1] && !var12[2] && !var12[3]) {
         var20 = 33;
      } else if (var20 == 26 && !var12[0] && !var12[1] && var12[2] && !var12[3]) {
         var20 = 44;
      } else if (var20 == 26 && !var12[0] && !var12[1] && !var12[2] && var12[3]) {
         var20 = 45;
      }

      return var20;
   }

   private static void switchValues(int integer1, int integer2, boolean[] arr) {
      boolean var3 = arr[integer1];
      arr[integer1] = arr[integer2];
      arr[integer2] = var3;
   }

   private static boolean isNeighbourOverlay(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      TextureAtlasSprite textureAtlasSprite,
      int integer7
   ) {
      IBlockState var10 = iBlockAccess.getBlockState(blockPos);
      if (!isFullCubeModel(var10)) {
         return false;
      }

      if (connectedProperties.connectBlocks != null) {
         BlockStateBase var11 = (BlockStateBase)var10;
         if (!Matches.block(var11.getBlockId(), var11.getMetadata(), connectedProperties.connectBlocks)) {
            return false;
         }
      }

      if (connectedProperties.connectTileIcons != null) {
         TextureAtlasSprite var12 = getNeighbourIcon(iBlockAccess, iBlockState, blockPos, var10, integer5);
         if (!Config.isSameOne(var12, connectedProperties.connectTileIcons)) {
            return false;
         }
      }

      IBlockState var13 = iBlockAccess.getBlockState(blockPos.offset(getFacing(integer5)));
      return !var13.getBlock().isOpaqueCube()
         && (integer5 != 1 || var13.getBlock() != Blocks.snow_layer)
         && !isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos, var10, integer5, textureAtlasSprite, integer7);
   }

   private static boolean isFullCubeModel(IBlockState iBlockState) {
      if (iBlockState.getBlock().isFullCube()) {
         return true;
      }

      Block var4 = iBlockState.getBlock();
      return var4 instanceof BlockGlass || var4 instanceof BlockStainedGlass;
   }

   private static boolean isNeighbourMatching(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      TextureAtlasSprite textureAtlasSprite,
      int integer7
   ) {
      IBlockState var10 = iBlockAccess.getBlockState(blockPos);
      if (var10 == AIR_DEFAULT_STATE) {
         return false;
      } else if (connectedProperties.matchBlocks != null
         && var10 instanceof BlockStateBase var11
         && !connectedProperties.matchesBlock(var11.getBlockId(), var11.getMetadata())) {
         return false;
      } else {
         if (connectedProperties.matchTileIcons != null) {
            TextureAtlasSprite var12 = getNeighbourIcon(iBlockAccess, iBlockState, blockPos, var10, integer5);
            if (var12 != textureAtlasSprite) {
               return false;
            }
         }

         IBlockState var13 = iBlockAccess.getBlockState(blockPos.offset(getFacing(integer5)));
         return !var13.getBlock().isOpaqueCube() && (integer5 != 1 || var13.getBlock() != Blocks.snow_layer);
      }
   }

   private static boolean isNeighbour(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      TextureAtlasSprite textureAtlasSprite,
      int integer7
   ) {
      IBlockState var7 = iBlockAccess.getBlockState(blockPos);
      return isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos, var7, integer5, textureAtlasSprite, integer7);
   }

   private static boolean isNeighbour(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState3,
      BlockPos blockPos,
      IBlockState iBlockState5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8
   ) {
      if (iBlockState3 == iBlockState5) {
         return true;
      }

      if (connectedProperties.connect == 2) {
         if (iBlockState5 == null) {
            return false;
         }

         if (iBlockState5 == AIR_DEFAULT_STATE) {
            return false;
         }

         TextureAtlasSprite var14 = getNeighbourIcon(iBlockAccess, iBlockState3, blockPos, iBlockState5, integer6);
         return var14 == textureAtlasSprite;
      } else if (connectedProperties.connect == 3) {
         return iBlockState5 != null && iBlockState5 != AIR_DEFAULT_STATE && iBlockState5.getBlock().getMaterial() == iBlockState3.getBlock().getMaterial();
      } else if (!(iBlockState5 instanceof BlockStateBase var11)) {
         return false;
      } else {
         Block var12 = var11.getBlock();
         int var13 = var11.getMetadata();
         return var12 == iBlockState3.getBlock() && var13 == integer8;
      }
   }

   private static TextureAtlasSprite getNeighbourIcon(
      IBlockAccess iBlockAccess, IBlockState iBlockState2, BlockPos blockPos, IBlockState iBlockState4, int integer
   ) {
      iBlockState4 = iBlockState4.getBlock().getActualState(iBlockState4, iBlockAccess, blockPos);
      IBakedModel var8 = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(iBlockState4);
      if (var8 == null) {
         return null;
      }

      EnumFacing var9 = getFacing(integer);
      List var10 = var8.getFaceQuads(var9);
      if (var10 == null) {
         return null;
      }

      if (Config.isBetterGrass()) {
         var10 = BetterGrass.getFaceQuads(iBlockAccess, iBlockState4, blockPos, var9, var10);
      }

      if (var10.size() > 0) {
         return ((BakedQuad)var10.get(0)).getSprite();
      }

      List<BakedQuad> var11 = var8.getGeneralQuads();
      if (var11 != null) {
         for (BakedQuad var14 : var11) {
            if (var14.getFace() == var9) {
               return var14.getSprite();
            }
         }
      }

      return null;
   }

   private static TextureAtlasSprite getConnectedTextureHorizontal(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8
   ) {
      boolean var11;
      boolean var12;
      var11 = false;
      var12 = false;
      label40:
      switch (integer5) {
         case 0:
            switch (integer6) {
               case 0:
               case 1:
               case 3:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
                  break label40;
               case 2:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
                  break label40;
               case 4:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
                  break label40;
               case 5:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
               default:
                  break label40;
            }
         case 1:
            switch (integer6) {
               case 0:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
                  break label40;
               case 1:
               case 2:
               case 3:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
                  break label40;
               case 4:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
                  break label40;
               case 5:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
               default:
                  break label40;
            }
         case 2:
            switch (integer6) {
               case 0:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
                  break;
               case 1:
               case 4:
               case 5:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
                  break;
               case 2:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
                  break;
               case 3:
                  var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
                  var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            }
      }

      byte var13;
      if (var11) {
         if (var12) {
            var13 = 1;
         } else {
            var13 = 2;
         }
      } else if (var12) {
         var13 = 0;
      } else {
         var13 = 3;
      }

      return connectedProperties.tileIcons[var13];
   }

   private static TextureAtlasSprite getConnectedTextureVertical(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8
   ) {
      boolean var11 = false;
      boolean var12 = false;
      switch (integer5) {
         case 0:
            if (integer6 == 1) {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
            } else if (integer6 == 0) {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
            } else {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 1:
            if (integer6 == 3) {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            } else if (integer6 == 2) {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            } else {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.north(), integer6, textureAtlasSprite, integer8);
            }
            break;
         case 2:
            if (integer6 == 5) {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
            } else if (integer6 == 4) {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.down(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            } else {
               var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.west(), integer6, textureAtlasSprite, integer8);
               var12 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
            }
      }

      byte var13;
      if (var11) {
         if (var12) {
            var13 = 1;
         } else {
            var13 = 2;
         }
      } else if (var12) {
         var13 = 0;
      } else {
         var13 = 3;
      }

      return connectedProperties.tileIcons[var13];
   }

   private static TextureAtlasSprite getConnectedTextureHorizontalVertical(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8
   ) {
      TextureAtlasSprite[] var11 = connectedProperties.tileIcons;
      TextureAtlasSprite var12 = getConnectedTextureHorizontal(
         connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, textureAtlasSprite, integer8
      );
      if (var12 != null && var12 != textureAtlasSprite && var12 != var11[3]) {
         return var12;
      }

      TextureAtlasSprite var13 = getConnectedTextureVertical(
         connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, textureAtlasSprite, integer8
      );
      return var13 == var11[0] ? var11[4] : (var13 == var11[1] ? var11[5] : (var13 == var11[2] ? var11[6] : var13));
   }

   private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8
   ) {
      TextureAtlasSprite[] var11 = connectedProperties.tileIcons;
      TextureAtlasSprite var12 = getConnectedTextureVertical(
         connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, textureAtlasSprite, integer8
      );
      if (var12 != null && var12 != textureAtlasSprite && var12 != var11[3]) {
         return var12;
      }

      TextureAtlasSprite var13 = getConnectedTextureHorizontal(
         connectedProperties, iBlockAccess, iBlockState, blockPos, integer5, integer6, textureAtlasSprite, integer8
      );
      return var13 == var11[0] ? var11[4] : (var13 == var11[1] ? var11[5] : (var13 == var11[2] ? var11[6] : var13));
   }

   private static TextureAtlasSprite getConnectedTextureTop(
      ConnectedProperties connectedProperties,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      int integer5,
      int integer6,
      TextureAtlasSprite textureAtlasSprite,
      int integer8
   ) {
      boolean var11 = false;
      switch (integer5) {
         case 0:
            if (integer6 == 1 || integer6 == 0) {
               return null;
            }

            var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.up(), integer6, textureAtlasSprite, integer8);
            break;
         case 1:
            if (integer6 == 3 || integer6 == 2) {
               return null;
            }

            var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.south(), integer6, textureAtlasSprite, integer8);
            break;
         case 2:
            if (integer6 == 5 || integer6 == 4) {
               return null;
            }

            var11 = isNeighbour(connectedProperties, iBlockAccess, iBlockState, blockPos.east(), integer6, textureAtlasSprite, integer8);
      }

      return var11 ? connectedProperties.tileIcons[0] : null;
   }

   public static void updateIcons(TextureMap textureMap) {
      blockProperties = null;
      tileProperties = null;
      spriteQuadMaps = null;
      spriteQuadCompactMaps = null;
      if (Config.isConnectedTextures()) {
         IResourcePack[] var4 = Config.getResourcePacks();

         for (int var5 = var4.length - 1; var5 >= 0; var5--) {
            IResourcePack var6 = var4[var5];
            updateIcons(textureMap, var6);
         }

         updateIcons(textureMap, Config.getDefaultResourcePack());
         ResourceLocation var7 = new ResourceLocation("mcpatcher/ctm/default/empty");
         emptySprite = textureMap.registerSprite(var7);
         spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
         spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
         spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
         if (blockProperties.length == 0) {
            blockProperties = null;
         }

         if (tileProperties.length == 0) {
            tileProperties = null;
         }
      }
   }

   private static void updateIconEmpty(TextureMap textureMap) {
   }

   public static void updateIcons(TextureMap textureMap, IResourcePack iResourcePack) {
      String[] var5 = ResUtils.collectFiles(iResourcePack, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
      Arrays.sort(var5);
      List var6 = makePropertyList(tileProperties);
      List var7 = makePropertyList(blockProperties);

      for (String var11 : var5) {
         Config.Method251("ConnectedTextures: " + var11);

         try {
            ResourceLocation var12 = new ResourceLocation(var11);
            InputStream var13 = iResourcePack.getInputStream(var12);
            if (var13 == null) {
               Config.Method252("ConnectedTextures file not found: " + var11);
            } else {
               PropertiesOrdered var14 = new PropertiesOrdered();
               var14.load(var13);
               var13.close();
               ConnectedProperties var15 = new ConnectedProperties(var14, var11);
               if (var15.isValid(var11)) {
                  var15.updateIcons(textureMap);
                  addToTileList(var15, var6);
                  addToBlockList(var15, var7);
               }
            }
         } catch (FileNotFoundException var16) {
            Config.Method252("ConnectedTextures file not found: " + var11);
         } catch (Exception var17) {
            var17.printStackTrace();
         }
      }

      blockProperties = propertyListToArray(var7);
      tileProperties = propertyListToArray(var6);
      multipass = detectMultipass();
      Config.Method251("Multipass connected textures: " + multipass);
   }

   private static List makePropertyList(ConnectedProperties[][] arr) {
      ArrayList var4 = new ArrayList();
      if (arr != null) {
         for (ConnectedProperties[] var8 : arr) {
            ArrayList var9 = null;
            if (var8 != null) {
               var9 = new ArrayList<>(Arrays.asList(var8));
            }

            var4.add(var9);
         }
      }

      return var4;
   }

   private static boolean detectMultipass() {
      ArrayList var3 = new ArrayList();

      for (ConnectedProperties[] var7 : tileProperties) {
         if (var7 != null) {
            var3.addAll(Arrays.asList(var7));
         }
      }

      for (ConnectedProperties[] var17 : blockProperties) {
         if (var17 != null) {
            var3.addAll(Arrays.asList(var17));
         }
      }

      ConnectedProperties[] var12 = (ConnectedProperties[])var3.toArray(new ConnectedProperties[0]);
      HashSet var14 = new HashSet();
      HashSet var16 = new HashSet();

      for (ConnectedProperties var10 : var12) {
         if (var10.matchTileIcons != null) {
            var14.addAll(Arrays.asList(var10.matchTileIcons));
         }

         if (var10.tileIcons != null) {
            var16.addAll(Arrays.asList(var10.tileIcons));
         }
      }

      var14.retainAll(var16);
      return !var14.isEmpty();
   }

   private static ConnectedProperties[][] propertyListToArray(List list) {
      ConnectedProperties[][] var4 = new ConnectedProperties[list.size()][];

      for (int var5 = 0; var5 < list.size(); var5++) {
         List var6 = (List)list.get(var5);
         if (var6 != null) {
            ConnectedProperties[] var7 = (ConnectedProperties[])var6.toArray(new ConnectedProperties[0]);
            var4[var5] = var7;
         }
      }

      return var4;
   }

   private static void addToTileList(ConnectedProperties connectedProperties, List list) {
      if (connectedProperties.matchTileIcons != null) {
         for (int var5 = 0; var5 < connectedProperties.matchTileIcons.length; var5++) {
            TextureAtlasSprite var6 = connectedProperties.matchTileIcons[var5];
            if (!(var6 instanceof TextureAtlasSprite)) {
               Config.Method252("TextureAtlasSprite is not TextureAtlasSprite: " + var6 + ", name: " + var6.getIconName());
            } else {
               int var7 = var6.getIndexInMap();
               if (var7 < 0) {
                  Config.Method252("Invalid tile ID: " + var7 + ", icon: " + var6.getIconName());
               } else {
                  addToList(connectedProperties, list, var7);
               }
            }
         }
      }
   }

   private static void addToBlockList(ConnectedProperties connectedProperties, List list) {
      if (connectedProperties.matchBlocks != null) {
         for (int var5 = 0; var5 < connectedProperties.matchBlocks.length; var5++) {
            int var6 = connectedProperties.matchBlocks[var5].getBlockId();
            if (var6 < 0) {
               Config.Method252("Invalid block ID: " + var6);
            } else {
               addToList(connectedProperties, list, var6);
            }
         }
      }
   }

   private static void addToList(ConnectedProperties connectedProperties, List list, int integer) {
      while (integer >= list.size()) {
         list.add(null);
      }

      List var6 = (List)list.get(integer);
      if (var6 == null) {
         var6 = new ArrayList();
         list.set(integer, var6);
      }

      var6.add(connectedProperties);
   }

   private static String[] getDefaultCtmPaths() {
      ArrayList var3 = new ArrayList();
      String var4 = "mcpatcher/ctm/default/";
      if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
         var3.add(var4 + "glass.properties");
         var3.add(var4 + "glasspane.properties");
      }

      if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
         var3.add(var4 + "bookshelf.properties");
      }

      if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
         var3.add(var4 + "sandstone.properties");
      }

      String[] var5 = new String[]{
         "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"
      };

      for (int var6 = 0; var6 < var5.length; var6++) {
         String var7 = var5[var6];
         if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + var7 + ".png"))) {
            var3.add(var4 + var6 + "_glass_" + var7 + "/glass_" + var7 + ".properties");
            var3.add(var4 + var6 + "_glass_" + var7 + "/glass_pane_" + var7 + ".properties");
         }
      }

      return (String[])var3.toArray(new String[0]);
   }
}
