package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.optifine.model.BlockModelUtils;
import net.optifine.util.PropertiesOrdered;

public class BetterGrass {
   private static boolean betterGrass = true;
   private static boolean betterMycelium = true;
   private static boolean betterPodzol = true;
   private static boolean betterGrassSnow = true;
   private static boolean betterMyceliumSnow = true;
   private static boolean betterPodzolSnow = true;
   private static boolean grassMultilayer = false;
   private static TextureAtlasSprite spriteGrass = null;
   private static TextureAtlasSprite spriteGrassSide = null;
   private static TextureAtlasSprite spriteMycelium = null;
   private static TextureAtlasSprite spritePodzol = null;
   private static TextureAtlasSprite spriteSnow = null;
   private static boolean spritesLoaded = false;
   private static IBakedModel modelCubeGrass = null;
   private static IBakedModel modelCubeMycelium = null;
   private static IBakedModel modelCubePodzol = null;
   private static IBakedModel modelCubeSnow = null;
   private static boolean modelsLoaded = false;

   public static void updateIcons(TextureMap textureMap) {
      spritesLoaded = false;
      modelsLoaded = false;
      loadProperties(textureMap);
   }

   public static void update() {
      if (spritesLoaded) {
         modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
         if (grassMultilayer) {
            modelCubeGrass = BlockModelUtils.joinModelsCube(BlockModelUtils.makeModelCube(spriteGrassSide, -1), modelCubeGrass);
         }

         modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
         modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
         modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
         modelsLoaded = true;
      }
   }

   private static void loadProperties(TextureMap textureMap) {
      betterGrass = true;
      betterMycelium = true;
      betterPodzol = true;
      betterGrassSnow = true;
      betterMyceliumSnow = true;
      betterPodzolSnow = true;
      spriteGrass = textureMap.registerSprite(new ResourceLocation("blocks/grass_top"));
      spriteGrassSide = textureMap.registerSprite(new ResourceLocation("blocks/grass_side"));
      spriteMycelium = textureMap.registerSprite(new ResourceLocation("blocks/mycelium_top"));
      spritePodzol = textureMap.registerSprite(new ResourceLocation("blocks/dirt_podzol_top"));
      spriteSnow = textureMap.registerSprite(new ResourceLocation("blocks/snow"));
      spritesLoaded = true;
      String var4 = "optifine/bettergrass.properties";

      try {
         ResourceLocation var5 = new ResourceLocation(var4);
         if (!Config.hasResource(var5)) {
            return;
         }

         InputStream var6 = Config.getResourceStream(var5);
         if (var6 == null) {
            return;
         }

         boolean var7 = Config.isFromDefaultResourcePack(var5);
         if (var7) {
            Config.Method251("BetterGrass: Parsing default configuration " + "optifine/bettergrass.properties");
         } else {
            Config.Method251("BetterGrass: Parsing configuration " + "optifine/bettergrass.properties");
         }

         PropertiesOrdered var8 = new PropertiesOrdered();
         var8.load(var6);
         var6.close();
         betterGrass = getBoolean(var8, "grass", true);
         betterMycelium = getBoolean(var8, "mycelium", true);
         betterPodzol = getBoolean(var8, "podzol", true);
         betterGrassSnow = getBoolean(var8, "grass.snow", true);
         betterMyceliumSnow = getBoolean(var8, "mycelium.snow", true);
         betterPodzolSnow = getBoolean(var8, "podzol.snow", true);
         grassMultilayer = getBoolean(var8, "grass.multilayer", false);
         spriteGrass = registerSprite(var8, "texture.grass", "blocks/grass_top", textureMap);
         spriteGrassSide = registerSprite(var8, "texture.grass_side", "blocks/grass_side", textureMap);
         spriteMycelium = registerSprite(var8, "texture.mycelium", "blocks/mycelium_top", textureMap);
         spritePodzol = registerSprite(var8, "texture.podzol", "blocks/dirt_podzol_top", textureMap);
         spriteSnow = registerSprite(var8, "texture.snow", "blocks/snow", textureMap);
      } catch (IOException var12) {
         String var10001 = var12.getClass().getName();
         String var9 = var12.getMessage();
         String var10 = var10001;
         String var11 = var4;
         Config.Method252("Error reading: " + var11 + ", " + var10 + ": " + var9);
      }
   }

   private static TextureAtlasSprite registerSprite(Properties properties, String string2, String string3, TextureMap textureMap) {
      String var7 = properties.getProperty(string2);
      if (var7 == null) {
         var7 = string3;
      }

      ResourceLocation var8 = new ResourceLocation("textures/" + var7 + ".png");
      if (!Config.hasResource(var8)) {
         Config.Method252("BetterGrass texture not found: " + var8);
         var7 = string3;
      }

      ResourceLocation var9 = new ResourceLocation(var7);
      return textureMap.registerSprite(var9);
   }

   public static List getFaceQuads(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, EnumFacing enumFacing, List list) {
      if (enumFacing == EnumFacing.UP || enumFacing == EnumFacing.DOWN) {
         return list;
      }

      if (!modelsLoaded) {
         return list;
      }

      Block var8 = iBlockState.getBlock();
      return var8 instanceof BlockMycelium
         ? getFaceQuadsMycelium(iBlockAccess, iBlockState, blockPos, enumFacing, list)
         : (
            var8 instanceof BlockDirt
               ? getFaceQuadsDirt(iBlockAccess, iBlockState, blockPos, enumFacing, list)
               : (var8 instanceof BlockGrass ? getFaceQuadsGrass(iBlockAccess, iBlockState, blockPos, enumFacing, list) : list)
         );
   }

   private static List getFaceQuadsMycelium(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, EnumFacing enumFacing, List list) {
      Block var8 = iBlockAccess.getBlockState(blockPos.up()).getBlock();
      boolean var9 = var8 == Blocks.snow || var8 == Blocks.snow_layer;
      if (Config.isBetterGrassFancy()) {
         if (var9) {
            if (betterMyceliumSnow && getBlockAt(blockPos, enumFacing, iBlockAccess) == Blocks.snow_layer) {
               return modelCubeSnow.getFaceQuads(enumFacing);
            }
         } else if (betterMycelium && getBlockAt(blockPos.down(), enumFacing, iBlockAccess) == Blocks.mycelium) {
            return modelCubeMycelium.getFaceQuads(enumFacing);
         }
      } else if (var9) {
         if (betterMyceliumSnow) {
            return modelCubeSnow.getFaceQuads(enumFacing);
         }
      } else if (betterMycelium) {
         return modelCubeMycelium.getFaceQuads(enumFacing);
      }

      return list;
   }

   private static List getFaceQuadsDirt(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, EnumFacing enumFacing, List list) {
      Block var8 = getBlockAt(blockPos, EnumFacing.UP, iBlockAccess);
      if (iBlockState.getValue(BlockDirt.VARIANT) != BlockDirt.DirtType.PODZOL) {
         return list;
      }

      boolean var9 = var8 == Blocks.snow || var8 == Blocks.snow_layer;
      if (Config.isBetterGrassFancy()) {
         if (var9) {
            if (betterPodzolSnow && getBlockAt(blockPos, enumFacing, iBlockAccess) == Blocks.snow_layer) {
               return modelCubeSnow.getFaceQuads(enumFacing);
            }
         } else if (betterPodzol) {
            BlockPos var10 = blockPos.down().offset(enumFacing);
            IBlockState var11 = iBlockAccess.getBlockState(var10);
            if (var11.getBlock() == Blocks.dirt && var11.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) {
               return modelCubePodzol.getFaceQuads(enumFacing);
            }
         }
      } else if (var9) {
         if (betterPodzolSnow) {
            return modelCubeSnow.getFaceQuads(enumFacing);
         }
      } else if (betterPodzol) {
         return modelCubePodzol.getFaceQuads(enumFacing);
      }

      return list;
   }

   private static List getFaceQuadsGrass(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, EnumFacing enumFacing, List list) {
      Block var8 = iBlockAccess.getBlockState(blockPos.up()).getBlock();
      boolean var9 = var8 == Blocks.snow || var8 == Blocks.snow_layer;
      if (Config.isBetterGrassFancy()) {
         if (var9) {
            if (betterGrassSnow && getBlockAt(blockPos, enumFacing, iBlockAccess) == Blocks.snow_layer) {
               return modelCubeSnow.getFaceQuads(enumFacing);
            }
         } else if (betterGrass && getBlockAt(blockPos.down(), enumFacing, iBlockAccess) == Blocks.grass) {
            return modelCubeGrass.getFaceQuads(enumFacing);
         }
      } else if (var9) {
         if (betterGrassSnow) {
            return modelCubeSnow.getFaceQuads(enumFacing);
         }
      } else if (betterGrass) {
         return modelCubeGrass.getFaceQuads(enumFacing);
      }

      return list;
   }

   private static Block getBlockAt(BlockPos blockPos, EnumFacing enumFacing, IBlockAccess iBlockAccess) {
      BlockPos var3 = blockPos.offset(enumFacing);
      return iBlockAccess.getBlockState(var3).getBlock();
   }

   private static boolean getBoolean(Properties properties, String string, boolean boolean3) {
      String var6 = properties.getProperty(string);
      return var6 == null ? boolean3 : Boolean.parseBoolean(var6);
   }
}
