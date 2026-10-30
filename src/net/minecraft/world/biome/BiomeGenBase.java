package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeGenBase {
   private static final Logger logger = LogManager.getLogger();
   protected static final BiomeGenBase.Height height_Default = new BiomeGenBase.Height(0.1F, 0.2F);
   protected static final BiomeGenBase.Height height_ShallowWaters = new BiomeGenBase.Height(-0.5F, 0.0F);
   protected static final BiomeGenBase.Height height_Oceans = new BiomeGenBase.Height(-1.0F, 0.1F);
   protected static final BiomeGenBase.Height height_DeepOceans = new BiomeGenBase.Height(-1.8F, 0.1F);
   protected static final BiomeGenBase.Height height_LowPlains = new BiomeGenBase.Height(0.125F, 0.05F);
   protected static final BiomeGenBase.Height height_MidPlains = new BiomeGenBase.Height(0.2F, 0.2F);
   protected static final BiomeGenBase.Height height_LowHills = new BiomeGenBase.Height(0.45F, 0.3F);
   protected static final BiomeGenBase.Height height_HighPlateaus = new BiomeGenBase.Height(1.5F, 0.025F);
   protected static final BiomeGenBase.Height height_MidHills = new BiomeGenBase.Height(1.0F, 0.5F);
   protected static final BiomeGenBase.Height height_Shores = new BiomeGenBase.Height(0.0F, 0.025F);
   protected static final BiomeGenBase.Height height_RockyWaters = new BiomeGenBase.Height(0.1F, 0.8F);
   protected static final BiomeGenBase.Height height_LowIslands = new BiomeGenBase.Height(0.2F, 0.3F);
   protected static final BiomeGenBase.Height height_PartiallySubmerged = new BiomeGenBase.Height(-0.2F, 0.1F);
   private static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
   public static final Set<BiomeGenBase> explorationBiomesList = Sets.newHashSet();
   public static final Map<String, BiomeGenBase> BIOME_ID_MAP = Maps.newHashMap();
   public static final BiomeGenBase ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").setHeight(height_Oceans);
   public static final BiomeGenBase plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
   public static final BiomeGenBase desert = new BiomeGenDesert(2)
      .setColor(16421912)
      .setBiomeName("Desert")
      .setDisableRain()
      .setTemperatureRainfall(2.0F, 0.0F)
      .setHeight(height_LowPlains);
   public static final BiomeGenBase extremeHills = new BiomeGenHills(3, false)
      .setColor(6316128)
      .setBiomeName("Extreme Hills")
      .setHeight(height_MidHills)
      .setTemperatureRainfall(0.2F, 0.3F);
   public static final BiomeGenBase forest = new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
   public static final BiomeGenBase taiga = new BiomeGenTaiga(5, 0)
      .setColor(747097)
      .setBiomeName("Taiga")
      .setFillerBlockMetadata(5159473)
      .setTemperatureRainfall(0.25F, 0.8F)
      .setHeight(height_MidPlains);
   public static final BiomeGenBase swampland = new BiomeGenSwamp(6)
      .setColor(522674)
      .setBiomeName("Swampland")
      .setFillerBlockMetadata(9154376)
      .setHeight(height_PartiallySubmerged)
      .setTemperatureRainfall(0.8F, 0.9F);
   public static final BiomeGenBase river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").setHeight(height_ShallowWaters);
   public static final BiomeGenBase hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
   public static final BiomeGenBase sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("The End").setDisableRain();
   public static final BiomeGenBase frozenOcean = new BiomeGenOcean(10)
      .setColor(9474208)
      .setBiomeName("FrozenOcean")
      .setEnableSnow()
      .setHeight(height_Oceans)
      .setTemperatureRainfall(0.0F, 0.5F);
   public static final BiomeGenBase frozenRiver = new BiomeGenRiver(11)
      .setColor(10526975)
      .setBiomeName("FrozenRiver")
      .setEnableSnow()
      .setHeight(height_ShallowWaters)
      .setTemperatureRainfall(0.0F, 0.5F);
   public static final BiomeGenBase icePlains = new BiomeGenSnow(12, false)
      .setColor(16777215)
      .setBiomeName("Ice Plains")
      .setEnableSnow()
      .setTemperatureRainfall(0.0F, 0.5F)
      .setHeight(height_LowPlains);
   public static final BiomeGenBase iceMountains = new BiomeGenSnow(13, false)
      .setColor(10526880)
      .setBiomeName("Ice Mountains")
      .setEnableSnow()
      .setHeight(height_LowHills)
      .setTemperatureRainfall(0.0F, 0.5F);
   public static final BiomeGenBase mushroomIsland = new BiomeGenMushroomIsland(14)
      .setColor(16711935)
      .setBiomeName("MushroomIsland")
      .setTemperatureRainfall(0.9F, 1.0F)
      .setHeight(height_LowIslands);
   public static final BiomeGenBase mushroomIslandShore = new BiomeGenMushroomIsland(15)
      .setColor(10486015)
      .setBiomeName("MushroomIslandShore")
      .setTemperatureRainfall(0.9F, 1.0F)
      .setHeight(height_Shores);
   public static final BiomeGenBase beach = new BiomeGenBeach(16)
      .setColor(16440917)
      .setBiomeName("Beach")
      .setTemperatureRainfall(0.8F, 0.4F)
      .setHeight(height_Shores);
   public static final BiomeGenBase desertHills = new BiomeGenDesert(17)
      .setColor(13786898)
      .setBiomeName("DesertHills")
      .setDisableRain()
      .setTemperatureRainfall(2.0F, 0.0F)
      .setHeight(height_LowHills);
   public static final BiomeGenBase forestHills = new BiomeGenForest(18, 0).setColor(2250012).setBiomeName("ForestHills").setHeight(height_LowHills);
   public static final BiomeGenBase taigaHills = new BiomeGenTaiga(19, 0)
      .setColor(1456435)
      .setBiomeName("TaigaHills")
      .setFillerBlockMetadata(5159473)
      .setTemperatureRainfall(0.25F, 0.8F)
      .setHeight(height_LowHills);
   public static final BiomeGenBase extremeHillsEdge = new BiomeGenHills(20, true)
      .setColor(7501978)
      .setBiomeName("Extreme Hills Edge")
      .setHeight(height_MidHills.attenuate())
      .setTemperatureRainfall(0.2F, 0.3F);
   public static final BiomeGenBase jungle = new BiomeGenJungle(21, false)
      .setColor(5470985)
      .setBiomeName("Jungle")
      .setFillerBlockMetadata(5470985)
      .setTemperatureRainfall(0.95F, 0.9F);
   public static final BiomeGenBase jungleHills = new BiomeGenJungle(22, false)
      .setColor(2900485)
      .setBiomeName("JungleHills")
      .setFillerBlockMetadata(5470985)
      .setTemperatureRainfall(0.95F, 0.9F)
      .setHeight(height_LowHills);
   public static final BiomeGenBase jungleEdge = new BiomeGenJungle(23, true)
      .setColor(6458135)
      .setBiomeName("JungleEdge")
      .setFillerBlockMetadata(5470985)
      .setTemperatureRainfall(0.95F, 0.8F);
   public static final BiomeGenBase deepOcean = new BiomeGenOcean(24).setColor(48).setBiomeName("Deep Ocean").setHeight(height_DeepOceans);
   public static final BiomeGenBase stoneBeach = new BiomeGenStoneBeach(25)
      .setColor(10658436)
      .setBiomeName("Stone Beach")
      .setTemperatureRainfall(0.2F, 0.3F)
      .setHeight(height_RockyWaters);
   public static final BiomeGenBase coldBeach = new BiomeGenBeach(26)
      .setColor(16445632)
      .setBiomeName("Cold Beach")
      .setTemperatureRainfall(0.05F, 0.3F)
      .setHeight(height_Shores)
      .setEnableSnow();
   public static final BiomeGenBase birchForest = new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
   public static final BiomeGenBase birchForestHills = new BiomeGenForest(28, 2)
      .setBiomeName("Birch Forest Hills")
      .setColor(2055986)
      .setHeight(height_LowHills);
   public static final BiomeGenBase roofedForest = new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
   public static final BiomeGenBase coldTaiga = new BiomeGenTaiga(30, 0)
      .setColor(3233098)
      .setBiomeName("Cold Taiga")
      .setFillerBlockMetadata(5159473)
      .setEnableSnow()
      .setTemperatureRainfall(-0.5F, 0.4F)
      .setHeight(height_MidPlains)
      .func_150563_c(16777215);
   public static final BiomeGenBase coldTaigaHills = new BiomeGenTaiga(31, 0)
      .setColor(2375478)
      .setBiomeName("Cold Taiga Hills")
      .setFillerBlockMetadata(5159473)
      .setEnableSnow()
      .setTemperatureRainfall(-0.5F, 0.4F)
      .setHeight(height_LowHills)
      .func_150563_c(16777215);
   public static final BiomeGenBase megaTaiga = new BiomeGenTaiga(32, 1)
      .setColor(5858897)
      .setBiomeName("Mega Taiga")
      .setFillerBlockMetadata(5159473)
      .setTemperatureRainfall(0.3F, 0.8F)
      .setHeight(height_MidPlains);
   public static final BiomeGenBase megaTaigaHills = new BiomeGenTaiga(33, 1)
      .setColor(4542270)
      .setBiomeName("Mega Taiga Hills")
      .setFillerBlockMetadata(5159473)
      .setTemperatureRainfall(0.3F, 0.8F)
      .setHeight(height_LowHills);
   public static final BiomeGenBase extremeHillsPlus = new BiomeGenHills(34, true)
      .setColor(5271632)
      .setBiomeName("Extreme Hills+")
      .setHeight(height_MidHills)
      .setTemperatureRainfall(0.2F, 0.3F);
   public static final BiomeGenBase savanna = new BiomeGenSavanna(35)
      .setColor(12431967)
      .setBiomeName("Savanna")
      .setTemperatureRainfall(1.2F, 0.0F)
      .setDisableRain()
      .setHeight(height_LowPlains);
   public static final BiomeGenBase savannaPlateau = new BiomeGenSavanna(36)
      .setColor(10984804)
      .setBiomeName("Savanna Plateau")
      .setTemperatureRainfall(1.0F, 0.0F)
      .setDisableRain()
      .setHeight(height_HighPlateaus);
   public static final BiomeGenBase mesa = new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
   public static final BiomeGenBase mesaPlateau_F = new BiomeGenMesa(38, false, true)
      .setColor(11573093)
      .setBiomeName("Mesa Plateau F")
      .setHeight(height_HighPlateaus);
   public static final BiomeGenBase mesaPlateau = new BiomeGenMesa(39, false, false)
      .setColor(13274213)
      .setBiomeName("Mesa Plateau")
      .setHeight(height_HighPlateaus);
   public static final BiomeGenBase field_180279_ad = ocean;
   protected static final NoiseGeneratorPerlin temperatureNoise;
   protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE;
   protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR;
   public String biomeName;
   public int color;
   public int field_150609_ah;
   public IBlockState topBlock = Blocks.grass.getDefaultState();
   public IBlockState fillerBlock = Blocks.dirt.getDefaultState();
   public int fillerBlockMetadata = 5169201;
   public float minHeight = height_Default.rootHeight;
   public float maxHeight = height_Default.variation;
   public float temperature = 0.5F;
   public float rainfall = 0.5F;
   public int waterColorMultiplier = 16777215;
   public BiomeDecorator theBiomeDecorator;
   protected List<BiomeGenBase.SpawnListEntry> spawnableMonsterList = Lists.newArrayList();
   protected List<BiomeGenBase.SpawnListEntry> spawnableCreatureList = Lists.newArrayList();
   protected List<BiomeGenBase.SpawnListEntry> spawnableWaterCreatureList = Lists.newArrayList();
   protected List<BiomeGenBase.SpawnListEntry> spawnableCaveCreatureList = Lists.newArrayList();
   protected boolean enableSnow;
   protected boolean enableRain = true;
   public final int biomeID;
   protected WorldGenTrees worldGeneratorTrees = new WorldGenTrees(false);
   protected WorldGenBigTree worldGeneratorBigTree = new WorldGenBigTree(false);
   protected WorldGenSwamp worldGeneratorSwamp = new WorldGenSwamp();

   protected BiomeGenBase(int integer) {
      this.biomeID = integer;
      biomeList[integer] = this;
      this.theBiomeDecorator = this.createBiomeDecorator();
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityRabbit.class, 10, 3, 3));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 10, 4, 4));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 8, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 100, 4, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 5, 1, 1));
      this.spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 10, 4, 4));
      this.spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
   }

   protected BiomeDecorator createBiomeDecorator() {
      return new BiomeDecorator();
   }

   protected BiomeGenBase setTemperatureRainfall(float float1, float float2) {
      if (float1 > 0.1F && float1 < 0.2F) {
         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
      }

      this.temperature = float1;
      this.rainfall = float2;
      return this;
   }

   protected final BiomeGenBase setHeight(BiomeGenBase.Height height) {
      this.minHeight = height.rootHeight;
      this.maxHeight = height.variation;
      return this;
   }

   protected BiomeGenBase setDisableRain() {
      this.enableRain = false;
      return this;
   }

   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
   }

   public WorldGenerator getRandomWorldGenForGrass(Random random) {
      return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
   }

   public BlockFlower.EnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
      return random.nextInt(3) > 0 ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
   }

   protected BiomeGenBase setEnableSnow() {
      this.enableSnow = true;
      return this;
   }

   protected BiomeGenBase setBiomeName(String string) {
      this.biomeName = string;
      return this;
   }

   protected BiomeGenBase setFillerBlockMetadata(int integer) {
      this.fillerBlockMetadata = integer;
      return this;
   }

   protected BiomeGenBase setColor(int integer) {
      this.func_150557_a(integer, false);
      return this;
   }

   protected BiomeGenBase func_150563_c(int integer) {
      this.field_150609_ah = integer;
      return this;
   }

   protected BiomeGenBase func_150557_a(int integer, boolean boolean2) {
      this.color = integer;
      if (boolean2) {
         this.field_150609_ah = (integer & 16711422) >> 1;
      } else {
         this.field_150609_ah = integer;
      }

      return this;
   }

   public int getSkyColorByTemp(float float1) {
      float1 = MathHelper.clamp_float(float1 / 3.0F, -1.0F, 1.0F);
      return MathHelper.hsvToRGB(0.62222224F - float1 * 0.05F, 0.5F + float1 * 0.1F, 1.0F);
   }

   public List<BiomeGenBase.SpawnListEntry> getSpawnableList(EnumCreatureType enumCreatureType) {
      switch (enumCreatureType) {
         case MONSTER:
            return this.spawnableMonsterList;
         case CREATURE:
            return this.spawnableCreatureList;
         case WATER_CREATURE:
            return this.spawnableWaterCreatureList;
         case AMBIENT:
            return this.spawnableCaveCreatureList;
         default:
            return Collections.emptyList();
      }
   }

   public boolean getEnableSnow() {
      return this.isSnowyBiome();
   }

   public boolean canRain() {
      return !this.isSnowyBiome() && this.enableRain;
   }

   public boolean isHighHumidity() {
      return this.rainfall > 0.85F;
   }

   public float getSpawningChance() {
      return 0.1F;
   }

   public final int getIntRainfall() {
      return (int)(this.rainfall * 65536.0F);
   }

   public final float getFloatRainfall() {
      return this.rainfall;
   }

   public final float getFloatTemperature(BlockPos blockPos) {
      if (blockPos.getY() > 64) {
         float var4 = (float)(temperatureNoise.func_151601_a(blockPos.getX() / 8.0, blockPos.getZ() / 8.0) * 4.0);
         return this.temperature - (var4 + blockPos.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return this.temperature;
      }
   }

   public void decorate(World world, Random random, BlockPos blockPos) {
      this.theBiomeDecorator.decorate(world, random, this, blockPos);
   }

   public int getGrassColorAtPos(BlockPos blockPos) {
      double var2 = MathHelper.clamp_float(this.getFloatTemperature(blockPos), 0.0F, 1.0F);
      double var4 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return ColorizerGrass.getGrassColor(var2, var4);
   }

   public int getFoliageColorAtPos(BlockPos blockPos) {
      double var2 = MathHelper.clamp_float(this.getFloatTemperature(blockPos), 0.0F, 1.0F);
      double var4 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0F, 1.0F);
      return ColorizerFoliage.getFoliageColor(var2, var4);
   }

   public boolean isSnowyBiome() {
      return this.enableSnow;
   }

   public void genTerrainBlocks(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
      this.generateBiomeTerrain(world, random, chunkPrimer, integer4, integer5, double6);
   }

   public final void generateBiomeTerrain(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
      int var11 = world.getSeaLevel();
      IBlockState var12 = this.topBlock;
      IBlockState var13 = this.fillerBlock;
      int var14 = -1;
      int var15 = (int)(double6 / 3.0 + 3.0 + random.nextDouble() * 0.25);
      int var16 = integer4 & 15;
      int var17 = integer5 & 15;
      BlockPos.MutableBlockPos var18 = new BlockPos.MutableBlockPos();

      for (int var19 = 255; var19 >= 0; var19--) {
         if (var19 <= random.nextInt(5)) {
            chunkPrimer.setBlockState(var17, var19, var16, Blocks.bedrock.getDefaultState());
         } else {
            IBlockState var20 = chunkPrimer.getBlockState(var17, var19, var16);
            if (var20.getBlock().getMaterial() == Material.air) {
               var14 = -1;
            } else if (var20.getBlock() == Blocks.stone) {
               if (var14 == -1) {
                  if (var15 <= 0) {
                     var12 = null;
                     var13 = Blocks.stone.getDefaultState();
                  } else if (var19 >= var11 - 4 && var19 <= var11 + 1) {
                     var12 = this.topBlock;
                     var13 = this.fillerBlock;
                  }

                  if (var19 < var11 && (var12 == null || var12.getBlock().getMaterial() == Material.air)) {
                     if (this.getFloatTemperature(var18.set(integer4, var19, integer5)) < 0.15F) {
                        var12 = Blocks.ice.getDefaultState();
                     } else {
                        var12 = Blocks.water.getDefaultState();
                     }
                  }

                  var14 = var15;
                  if (var19 >= var11 - 1) {
                     chunkPrimer.setBlockState(var17, var19, var16, var12);
                  } else if (var19 < var11 - 7 - var15) {
                     var12 = null;
                     var13 = Blocks.stone.getDefaultState();
                     chunkPrimer.setBlockState(var17, var19, var16, Blocks.gravel.getDefaultState());
                  } else {
                     chunkPrimer.setBlockState(var17, var19, var16, var13);
                  }
               } else if (var14 > 0) {
                  var14--;
                  chunkPrimer.setBlockState(var17, var19, var16, var13);
                  if (var14 == 0 && var13.getBlock() == Blocks.sand) {
                     var14 = random.nextInt(4) + Math.max(0, var19 - 63);
                     var13 = var13.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND
                        ? Blocks.red_sandstone.getDefaultState()
                        : Blocks.sandstone.getDefaultState();
                  }
               }
            }
         }
      }
   }

   protected BiomeGenBase createMutation() {
      return this.createMutatedBiome(this.biomeID + 128);
   }

   protected BiomeGenBase createMutatedBiome(int integer) {
      return new BiomeGenMutated(integer, this);
   }

   public Class<? extends BiomeGenBase> getBiomeClass() {
      return (Class<? extends BiomeGenBase>)this.getClass();
   }

   public boolean isEqualTo(BiomeGenBase biomeGenBase) {
      return biomeGenBase == this || biomeGenBase != null && this.getBiomeClass() == biomeGenBase.getBiomeClass();
   }

   public BiomeGenBase.TempCategory getTempCategory() {
      return this.temperature < 0.2
         ? BiomeGenBase.TempCategory.COLD
         : (this.temperature < 1.0 ? BiomeGenBase.TempCategory.MEDIUM : BiomeGenBase.TempCategory.WARM);
   }

   public static BiomeGenBase[] getBiomeGenArray() {
      return biomeList;
   }

   public static BiomeGenBase getBiome(int integer) {
      return Method289(integer, null);
   }

   public static BiomeGenBase Method289(int integer, BiomeGenBase biomeGenBase) {
      if (integer >= 0 && integer <= biomeList.length) {
         BiomeGenBase var5 = biomeList[integer];
         return var5 == null ? biomeGenBase : var5;
      } else {
         logger.warn("Biome ID is out of bounds: " + integer + ", defaulting to 0 (Ocean)");
         return ocean;
      }
   }

   static {
      plains.createMutation();
      desert.createMutation();
      forest.createMutation();
      taiga.createMutation();
      swampland.createMutation();
      icePlains.createMutation();
      jungle.createMutation();
      jungleEdge.createMutation();
      coldTaiga.createMutation();
      savanna.createMutation();
      savannaPlateau.createMutation();
      mesa.createMutation();
      mesaPlateau_F.createMutation();
      mesaPlateau.createMutation();
      birchForest.createMutation();
      birchForestHills.createMutation();
      roofedForest.createMutation();
      megaTaiga.createMutation();
      extremeHills.createMutation();
      extremeHillsPlus.createMutation();
      megaTaiga.createMutatedBiome(megaTaigaHills.biomeID + 128).setBiomeName("Redwood Taiga Hills M");

      for (BiomeGenBase var27 : biomeList) {
         if (var27 != null) {
            if (BIOME_ID_MAP.containsKey(var27.biomeName)) {
               throw new Error("Biome \"" + var27.biomeName + "\" is defined as both ID " + BIOME_ID_MAP.get(var27.biomeName).biomeID + " and " + var27.biomeID);
            }

            BIOME_ID_MAP.put(var27.biomeName, var27);
            if (var27.biomeID < 128) {
               explorationBiomesList.add(var27);
            }
         }
      }

      explorationBiomesList.remove(hell);
      explorationBiomesList.remove(sky);
      explorationBiomesList.remove(frozenOcean);
      explorationBiomesList.remove(extremeHillsEdge);
      temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
      GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
      DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant();
   }

   public static class Height {
      public float rootHeight;
      public float variation;

      public Height(float float1, float float2) {
         this.rootHeight = float1;
         this.variation = float2;
      }

      public BiomeGenBase.Height attenuate() {
         return new BiomeGenBase.Height(this.rootHeight * 0.8F, this.variation * 0.6F);
      }
   }

   public static class SpawnListEntry extends WeightedRandom.Item {
      public Class<? extends EntityLiving> entityClass;
      public int minGroupCount;
      public int maxGroupCount;

      public SpawnListEntry(Class<? extends EntityLiving> class1, int integer2, int integer3, int integer4) {
         super(integer2);
         this.entityClass = class1;
         this.minGroupCount = integer3;
         this.maxGroupCount = integer4;
      }

      @Override
      public String toString() {
         return this.entityClass.getSimpleName() + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
      }
   }

   public enum TempCategory {
      OCEAN,
      COLD,
      MEDIUM,
      WARM;

      private static BiomeGenBase.TempCategory[] Method530() {
         return new BiomeGenBase.TempCategory[]{OCEAN, COLD, MEDIUM, WARM};
      }
   }
}
