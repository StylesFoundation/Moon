package net.minecraft.block;

import com.moon.Moon;
import com.moon.events.impl.player.DamageBlockEvent;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block {
   private static final ResourceLocation AIR_ID = new ResourceLocation("air");
   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> blockRegistry = new RegistryNamespacedDefaultedByKey<>(AIR_ID);
   public static final ObjectIntIdentityMap<IBlockState> BLOCK_STATE_IDS = new ObjectIntIdentityMap<>();
   private CreativeTabs displayOnCreativeTab;
   public static final Block.SoundType soundTypeStone = new Block.SoundType("stone", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeWood = new Block.SoundType("wood", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeGravel = new Block.SoundType("gravel", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeGrass = new Block.SoundType("grass", 1.0F, 1.0F);
   public static final Block.SoundType soundTypePiston = new Block.SoundType("stone", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeMetal = new Block.SoundType("stone", 1.0F, 1.5F);
   public static final Block.SoundType soundTypeGlass = new Block.SoundType("stone", 1.0F, 1.0F) {
      @Override
      public String getBreakSound() {
         return "dig.glass";
      }

      @Override
      public String getPlaceSound() {
         return "step.stone";
      }
   };
   public static final Block.SoundType soundTypeCloth = new Block.SoundType("cloth", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeSand = new Block.SoundType("sand", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeSnow = new Block.SoundType("snow", 1.0F, 1.0F);
   public static final Block.SoundType soundTypeLadder = new Block.SoundType("ladder", 1.0F, 1.0F) {
      @Override
      public String getBreakSound() {
         return "dig.wood";
      }
   };
   public static final Block.SoundType soundTypeAnvil = new Block.SoundType("anvil", 0.3F, 1.0F) {
      @Override
      public String getBreakSound() {
         return "dig.stone";
      }

      @Override
      public String getPlaceSound() {
         return "random.anvil_land";
      }
   };
   public static final Block.SoundType SLIME_SOUND = new Block.SoundType("slime", 1.0F, 1.0F) {
      @Override
      public String getBreakSound() {
         return "mob.slime.big";
      }

      @Override
      public String getPlaceSound() {
         return "mob.slime.big";
      }

      @Override
      public String getStepSound() {
         return "mob.slime.small";
      }
   };
   protected boolean fullBlock;
   protected int lightOpacity;
   protected boolean translucent;
   protected int lightValue;
   protected boolean useNeighborBrightness;
   protected float blockHardness;
   protected float blockResistance;
   protected boolean enableStats = true;
   protected boolean needsRandomTick;
   protected boolean isBlockContainer;
   protected double minX;
   protected double minY;
   protected double minZ;
   protected double maxX;
   protected double maxY;
   protected double maxZ;
   public Block.SoundType stepSound = soundTypeStone;
   public float blockParticleGravity = 1.0F;
   protected final Material blockMaterial;
   protected final MapColor blockMapColor;
   public float slipperiness = 0.6F;
   protected final BlockState blockState;
   private IBlockState defaultBlockState;
   private String unlocalizedName;

   public static int getIdFromBlock(Block block) {
      return blockRegistry.getIDForObject(block);
   }

   public static int getStateId(IBlockState iBlockState) {
      Block var3 = iBlockState.getBlock();
      return getIdFromBlock(var3) + (var3.getMetaFromState(iBlockState) << 12);
   }

   public static Block getBlockById(int integer) {
      return blockRegistry.getObjectById(integer);
   }

   public static IBlockState getStateById(int integer) {
      int var3 = integer & 4095;
      int var4 = integer >> 12 & 15;
      return getBlockById(var3).getStateFromMeta(var4);
   }

   public static Block getBlockFromItem(Item item) {
      return item instanceof ItemBlock ? ((ItemBlock)item).getBlock() : null;
   }

   public static Block getBlockFromName(String string) {
      ResourceLocation var4 = new ResourceLocation(string);
      if (blockRegistry.containsKey(var4)) {
         return blockRegistry.getObject(var4);
      }

      try {
         return blockRegistry.getObjectById(Integer.parseInt(string));
      } catch (NumberFormatException var5) {
         return null;
      }
   }

   public boolean isFullBlock() {
      return this.fullBlock;
   }

   public int getLightOpacity() {
      return this.lightOpacity;
   }

   public boolean isTranslucent() {
      return this.translucent;
   }

   public int getLightValue() {
      return this.lightValue;
   }

   public boolean getUseNeighborBrightness() {
      return this.useNeighborBrightness;
   }

   public Material getMaterial() {
      return this.blockMaterial;
   }

   public MapColor getMapColor(IBlockState iBlockState) {
      return this.blockMapColor;
   }

   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState();
   }

   public int getMetaFromState(IBlockState iBlockState) {
      if (iBlockState != null && !iBlockState.getPropertyNames().isEmpty()) {
         throw new IllegalArgumentException("Don't know how to convert " + iBlockState + " back into data...");
      } else {
         return 0;
      }
   }

   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState;
   }

   public Block(Material material, MapColor mapColor) {
      this.blockMaterial = material;
      this.blockMapColor = mapColor;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.fullBlock = this.isOpaqueCube();
      this.lightOpacity = this.isOpaqueCube() ? 255 : 0;
      this.translucent = !material.blocksLight();
      this.blockState = this.createBlockState();
      this.setDefaultState(this.blockState.getBaseState());
   }

   protected Block(Material material) {
      this(material, material.getMaterialMapColor());
   }

   protected Block setStepSound(Block.SoundType soundType) {
      this.stepSound = soundType;
      return this;
   }

   protected Block setLightOpacity(int integer) {
      this.lightOpacity = integer;
      return this;
   }

   protected Block setLightLevel(float float1) {
      this.lightValue = (int)(15.0F * float1);
      return this;
   }

   protected Block setResistance(float float1) {
      this.blockResistance = float1 * 3.0F;
      return this;
   }

   public boolean isBlockNormalCube() {
      return this.blockMaterial.blocksMovement() && this.isFullCube();
   }

   public boolean isNormalCube() {
      return this.blockMaterial.isOpaque() && this.isFullCube() && !this.canProvidePower();
   }

   public boolean isVisuallyOpaque() {
      return this.blockMaterial.blocksMovement() && this.isFullCube();
   }

   public boolean isFullCube() {
      return true;
   }

   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return !this.blockMaterial.blocksMovement();
   }

   public int getRenderType() {
      return 3;
   }

   public boolean isReplaceable(World world, BlockPos blockPos) {
      return false;
   }

   protected Block setHardness(float float1) {
      this.blockHardness = float1;
      if (this.blockResistance < float1 * 5.0F) {
         this.blockResistance = float1 * 5.0F;
      }

      return this;
   }

   protected Block setBlockUnbreakable() {
      this.setHardness(-1.0F);
      return this;
   }

   public float getBlockHardness(World world, BlockPos blockPos) {
      return this.blockHardness;
   }

   protected Block setTickRandomly(boolean boolean1) {
      this.needsRandomTick = boolean1;
      return this;
   }

   public boolean getTickRandomly() {
      return this.needsRandomTick;
   }

   public boolean hasTileEntity() {
      return this.isBlockContainer;
   }

   protected final void setBlockBounds(float float1, float float2, float float3, float float4, float float5, float float6) {
      this.minX = float1;
      this.minY = float2;
      this.minZ = float3;
      this.maxX = float4;
      this.maxY = float5;
      this.maxZ = float6;
   }

   public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var6 = iBlockAccess.getBlockState(blockPos).getBlock();
      int var7 = iBlockAccess.getCombinedLight(blockPos, var6.getLightValue());
      if (var7 == 0 && var6 instanceof BlockSlab) {
         blockPos = blockPos.down();
         var6 = iBlockAccess.getBlockState(blockPos).getBlock();
         return iBlockAccess.getCombinedLight(blockPos, var6.getLightValue());
      } else {
         return var7;
      }
   }

   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return enumFacing == EnumFacing.DOWN && this.minY > 0.0
         || enumFacing == EnumFacing.UP && this.maxY < 1.0
         || enumFacing == EnumFacing.NORTH && this.minZ > 0.0
         || enumFacing == EnumFacing.SOUTH && this.maxZ < 1.0
         || enumFacing == EnumFacing.WEST && this.minX > 0.0
         || enumFacing == EnumFacing.EAST && this.maxX < 1.0
         || !iBlockAccess.getBlockState(blockPos).getBlock().isOpaqueCube();
   }

   public boolean isBlockSolid(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial().isSolid();
   }

   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      return new AxisAlignedBB(
         blockPos.getX() + this.minX,
         blockPos.getY() + this.minY,
         blockPos.getZ() + this.minZ,
         blockPos.getX() + this.maxX,
         blockPos.getY() + this.maxY,
         blockPos.getZ() + this.maxZ
      );
   }

   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      AxisAlignedBB var10 = this.getCollisionBoundingBox(world, blockPos, iBlockState);
      if (var10 != null && axisAlignedBB.intersectsWith(var10)) {
         list.add(var10);
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return new AxisAlignedBB(
         blockPos.getX() + this.minX,
         blockPos.getY() + this.minY,
         blockPos.getZ() + this.minZ,
         blockPos.getX() + this.maxX,
         blockPos.getY() + this.maxY,
         blockPos.getZ() + this.maxZ
      );
   }

   public boolean isOpaqueCube() {
      return true;
   }

   public boolean canCollideCheck(IBlockState iBlockState, boolean boolean2) {
      return this.isCollidable();
   }

   public boolean isCollidable() {
      return true;
   }

   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      this.updateTick(world, blockPos, iBlockState, random);
   }

   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, IBlockState iBlockState) {
   }

   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
   }

   public int tickRate(World world) {
      return 10;
   }

   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
   }

   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
   }

   public int quantityDropped(Random random) {
      return 1;
   }

   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(this);
   }

   public float Method434(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
      DamageBlockEvent var7 = new DamageBlockEvent(entityPlayer.inventory.currentItem);
      Moon.getInstance().getEventBus().publish(var7);
      float var8 = this.getBlockHardness(world, blockPos);
      return var8 < 0.0F
         ? 0.0F
         : (
            !entityPlayer.canHarvestBlock(this, var7.getSlot())
               ? entityPlayer.getToolDigEfficiency(this, var7.getSlot()) / var8 / 100.0F
               : entityPlayer.getToolDigEfficiency(this, var7.getSlot()) / var8 / 30.0F
         );
   }

   public float Method435(EntityPlayer entityPlayer, World world, BlockPos blockPos, boolean boolean4) {
      DamageBlockEvent var8 = new DamageBlockEvent(entityPlayer.inventory.currentItem);
      Moon.getInstance().getEventBus().publish(var8);
      float var9 = this.getBlockHardness(world, blockPos);
      return var9 < 0.0F
         ? 0.0F
         : (
            !entityPlayer.canHarvestBlock(this, var8.getSlot())
               ? entityPlayer.Method547(this, var8.getSlot(), boolean4) / var9 / 100.0F
               : entityPlayer.Method547(this, var8.getSlot(), boolean4) / var9 / 30.0F
         );
   }

   public float Method436(EntityPlayer entityPlayer, World world, BlockPos blockPos, boolean boolean4, int integer) {
      float var9 = this.getBlockHardness(world, blockPos);
      return var9 < 0.0F
         ? 0.0F
         : (
            !entityPlayer.canHarvestBlock(this, integer)
               ? entityPlayer.Method548(this, integer, boolean4) / var9 / 100.0F
               : entityPlayer.Method548(this, integer, boolean4) / var9 / 30.0F
         );
   }

   public final void dropBlockAsItem(World world, BlockPos blockPos, IBlockState iBlockState, int integer) {
      this.dropBlockAsItemWithChance(world, blockPos, iBlockState, 1.0F, integer);
   }

   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      if (!world.isRemote) {
         int var9 = this.quantityDroppedWithBonus(integer, world.rand);

         for (int var10 = 0; var10 < var9; var10++) {
            if (world.rand.nextFloat() <= float4) {
               Item var11 = this.getItemDropped(iBlockState, world.rand, integer);
               if (var11 != null) {
                  spawnAsEntity(world, blockPos, new ItemStack(var11, 1, this.damageDropped(iBlockState)));
               }
            }
         }
      }
   }

   public static void spawnAsEntity(World world, BlockPos blockPos, ItemStack itemStack) {
      if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
         double var7 = world.rand.nextFloat() * 0.5F + 0.25;
         double var9 = world.rand.nextFloat() * 0.5F + 0.25;
         double var11 = world.rand.nextFloat() * 0.5F + 0.25;
         EntityItem var13 = new EntityItem(world, blockPos.getX() + var7, blockPos.getY() + var9, blockPos.getZ() + var11, itemStack);
         var13.setDefaultPickupDelay();
         world.spawnEntityInWorld(var13);
      }
   }

   protected void dropXpOnBlockBreak(World world, BlockPos blockPos, int integer) {
      if (!world.isRemote) {
         while (integer > 0) {
            int var7 = EntityXPOrb.getXPSplit(integer);
            integer -= var7;
            world.spawnEntityInWorld(new EntityXPOrb(world, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, var7));
         }
      }
   }

   public int damageDropped(IBlockState iBlockState) {
      return 0;
   }

   public float getExplosionResistance(Entity entity) {
      return this.blockResistance / 5.0F;
   }

   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      vec33 = vec33.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
      vec34 = vec34.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
      Vec3 var8 = vec33.getIntermediateWithXValue(vec34, this.minX);
      Vec3 var9 = vec33.getIntermediateWithXValue(vec34, this.maxX);
      Vec3 var10 = vec33.getIntermediateWithYValue(vec34, this.minY);
      Vec3 var11 = vec33.getIntermediateWithYValue(vec34, this.maxY);
      Vec3 var12 = vec33.getIntermediateWithZValue(vec34, this.minZ);
      Vec3 var13 = vec33.getIntermediateWithZValue(vec34, this.maxZ);
      if (!this.Method437(var8)) {
         var8 = null;
      }

      if (!this.Method437(var9)) {
         var9 = null;
      }

      if (!this.Method438(var10)) {
         var10 = null;
      }

      if (!this.Method438(var11)) {
         var11 = null;
      }

      if (!this.Method439(var12)) {
         var12 = null;
      }

      if (!this.Method439(var13)) {
         var13 = null;
      }

      Vec3 var14 = null;
      if (var8 != null && (null == null || vec33.squareDistanceTo(var8) < vec33.squareDistanceTo(null))) {
         var14 = var8;
      }

      if (var9 != null && (var14 == null || vec33.squareDistanceTo(var9) < vec33.squareDistanceTo(var14))) {
         var14 = var9;
      }

      if (var10 != null && (var14 == null || vec33.squareDistanceTo(var10) < vec33.squareDistanceTo(var14))) {
         var14 = var10;
      }

      if (var11 != null && (var14 == null || vec33.squareDistanceTo(var11) < vec33.squareDistanceTo(var14))) {
         var14 = var11;
      }

      if (var12 != null && (var14 == null || vec33.squareDistanceTo(var12) < vec33.squareDistanceTo(var14))) {
         var14 = var12;
      }

      if (var13 != null && (var14 == null || vec33.squareDistanceTo(var13) < vec33.squareDistanceTo(var14))) {
         var14 = var13;
      }

      if (var14 == null) {
         return null;
      }

      EnumFacing var15 = null;
      if (var14 == var8) {
         var15 = EnumFacing.WEST;
      }

      if (var14 == var9) {
         var15 = EnumFacing.EAST;
      }

      if (var14 == var10) {
         var15 = EnumFacing.DOWN;
      }

      if (var14 == var11) {
         var15 = EnumFacing.UP;
      }

      if (var14 == var12) {
         var15 = EnumFacing.NORTH;
      }

      if (var14 == var13) {
         var15 = EnumFacing.SOUTH;
      }

      return new MovingObjectPosition(var14.addVector(blockPos.getX(), blockPos.getY(), blockPos.getZ()), var15, blockPos);
   }

   private boolean Method437(Vec3 vec3) {
      return vec3 != null && vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ;
   }

   private boolean Method438(Vec3 vec3) {
      return vec3 != null && vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.zCoord >= this.minZ && vec3.zCoord <= this.maxZ;
   }

   private boolean Method439(Vec3 vec3) {
      return vec3 != null && vec3.xCoord >= this.minX && vec3.xCoord <= this.maxX && vec3.yCoord >= this.minY && vec3.yCoord <= this.maxY;
   }

   public void onBlockDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.SOLID;
   }

   public boolean canReplace(World world, BlockPos blockPos, EnumFacing enumFacing, ItemStack itemStack) {
      return this.canPlaceBlockOnSide(world, blockPos, enumFacing);
   }

   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return this.canPlaceBlockAt(world, blockPos);
   }

   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable();
   }

   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      return false;
   }

   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
   }

   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getStateFromMeta(integer);
   }

   public void onBlockClicked(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
   }

   public Vec3 modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3 vec3) {
      return vec3;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
   }

   public final double getBlockBoundsMinX() {
      return this.minX;
   }

   public final double getBlockBoundsMaxX() {
      return this.maxX;
   }

   public final double getBlockBoundsMinY() {
      return this.minY;
   }

   public final double getBlockBoundsMaxY() {
      return this.maxY;
   }

   public final double getBlockBoundsMinZ() {
      return this.minZ;
   }

   public final double getBlockBoundsMaxZ() {
      return this.maxZ;
   }

   public int getBlockColor() {
      return 16777215;
   }

   public int getRenderColor(IBlockState iBlockState) {
      return 16777215;
   }

   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return 16777215;
   }

   public final int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return this.colorMultiplier(iBlockAccess, blockPos, 0);
   }

   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return 0;
   }

   public boolean canProvidePower() {
      return false;
   }

   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
   }

   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return 0;
   }

   public void setBlockBoundsForItemRender() {
   }

   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      entityPlayer.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
      entityPlayer.addExhaustion(0.025F);
      if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(entityPlayer)) {
         ItemStack var10 = this.createStackedBlock(iBlockState);
         if (var10 != null) {
            spawnAsEntity(world, blockPos, var10);
         }
      } else {
         int var9 = EnchantmentHelper.getFortuneModifier(entityPlayer);
         this.dropBlockAsItem(world, blockPos, iBlockState, var9);
      }
   }

   protected boolean canSilkHarvest() {
      return this.isFullCube() && !this.isBlockContainer;
   }

   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      int var5 = 0;
      Item var6 = Item.getItemFromBlock(this);
      if (var6 != null && var6.getHasSubtypes()) {
         var5 = this.getMetaFromState(iBlockState);
      }

      return new ItemStack(var6, 1, var5);
   }

   public int quantityDroppedWithBonus(int integer, Random random) {
      return this.quantityDropped(random);
   }

   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
   }

   public boolean canSpawnInBlock() {
      return !this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid();
   }

   public Block setUnlocalizedName(String string) {
      this.unlocalizedName = string;
      return this;
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.Method440() + ".name");
   }

   public String Method440() {
      return "tile." + this.unlocalizedName;
   }

   public boolean onBlockEventReceived(World world, BlockPos blockPos, IBlockState iBlockState, int integer4, int integer5) {
      return false;
   }

   public boolean getEnableStats() {
      return this.enableStats;
   }

   protected Block disableStats() {
      this.enableStats = false;
      return this;
   }

   public int getMobilityFlag() {
      return this.blockMaterial.getMaterialMobility();
   }

   public float getAmbientOcclusionLightValue() {
      return this.isBlockNormalCube() ? 0.2F : 1.0F;
   }

   public void onFallenUpon(World world, BlockPos blockPos, Entity entity, float float4) {
      entity.fall(float4, 1.0F);
   }

   public void onLanded(World world, Entity entity) {
      entity.motionY = 0.0;
   }

   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(this);
   }

   public int getDamageValue(World world, BlockPos blockPos) {
      return this.damageDropped(world.getBlockState(blockPos));
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, 0));
   }

   public CreativeTabs getCreativeTabToDisplayOn() {
      return this.displayOnCreativeTab;
   }

   public Block setCreativeTab(CreativeTabs creativeTabs) {
      this.displayOnCreativeTab = creativeTabs;
      return this;
   }

   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
   }

   public void fillWithRain(World world, BlockPos blockPos) {
   }

   public boolean isFlowerPot() {
      return false;
   }

   public boolean requiresUpdates() {
      return true;
   }

   public boolean canDropFromExplosion(Explosion explosion) {
      return true;
   }

   public boolean isAssociatedBlock(Block block) {
      return this == block;
   }

   public static boolean isEqualTo(Block block1, Block block2) {
      return block1 != null && block2 != null && (block1 == block2 || block1.isAssociatedBlock(block2));
   }

   public boolean hasComparatorInputOverride() {
      return false;
   }

   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return 0;
   }

   public IBlockState getStateForEntityRender(IBlockState iBlockState) {
      return iBlockState;
   }

   protected BlockState createBlockState() {
      return new BlockState(this);
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   protected final void setDefaultState(IBlockState iBlockState) {
      this.defaultBlockState = iBlockState;
   }

   public final IBlockState getDefaultState() {
      return this.defaultBlockState;
   }

   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.NONE;
   }

   @Override
   public String toString() {
      return "Block{" + blockRegistry.getNameForObject(this) + "}";
   }

   public static void registerBlocks() {
      registerBlock(0, AIR_ID, new BlockAir().setUnlocalizedName("air"));
      registerBlock(1, "stone", new BlockStone().setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stone"));
      registerBlock(2, "grass", new BlockGrass().setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("grass"));
      registerBlock(3, "dirt", new BlockDirt().setHardness(0.5F).setStepSound(soundTypeGravel).setUnlocalizedName("dirt"));
      Block var3 = new Block(Material.rock)
         .setHardness(2.0F)
         .setResistance(10.0F)
         .setStepSound(soundTypePiston)
         .setUnlocalizedName("stonebrick")
         .setCreativeTab(CreativeTabs.tabBlock);
      registerBlock(4, "cobblestone", var3);
      Block var4 = new BlockPlanks().setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("wood");
      registerBlock(5, "planks", var4);
      registerBlock(6, "sapling", new BlockSapling().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("sapling"));
      registerBlock(
         7,
         "bedrock",
         new Block(Material.rock)
            .setBlockUnbreakable()
            .setResistance(6000000.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("bedrock")
            .disableStats()
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(
         8, "flowing_water", new BlockDynamicLiquid(Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats()
      );
      registerBlock(9, "water", new BlockStaticLiquid(Material.water).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
      registerBlock(10, "flowing_lava", new BlockDynamicLiquid(Material.lava).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
      registerBlock(11, "lava", new BlockStaticLiquid(Material.lava).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
      registerBlock(12, "sand", new BlockSand().setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("sand"));
      registerBlock(13, "gravel", new BlockGravel().setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("gravel"));
      registerBlock(14, "gold_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreGold"));
      registerBlock(15, "iron_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreIron"));
      registerBlock(16, "coal_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreCoal"));
      registerBlock(17, "log", new BlockOldLog().setUnlocalizedName("log"));
      registerBlock(18, "leaves", new BlockOldLeaf().setUnlocalizedName("leaves"));
      registerBlock(19, "sponge", new BlockSponge().setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("sponge"));
      registerBlock(20, "glass", new BlockGlass(Material.glass, false).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("glass"));
      registerBlock(21, "lapis_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreLapis"));
      registerBlock(
         22,
         "lapis_block",
         new Block(Material.iron, MapColor.lapisColor)
            .setHardness(3.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("blockLapis")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(23, "dispenser", new BlockDispenser().setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dispenser"));
      Block var5 = new BlockSandStone().setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("sandStone");
      registerBlock(24, "sandstone", var5);
      registerBlock(25, "noteblock", new BlockNote().setHardness(0.8F).setUnlocalizedName("musicBlock"));
      registerBlock(26, "bed", new BlockBed().setStepSound(soundTypeWood).setHardness(0.2F).setUnlocalizedName("bed").disableStats());
      registerBlock(27, "golden_rail", new BlockRailPowered().setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("goldenRail"));
      registerBlock(28, "detector_rail", new BlockRailDetector().setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("detectorRail"));
      registerBlock(29, "sticky_piston", new BlockPistonBase(true).setUnlocalizedName("pistonStickyBase"));
      registerBlock(30, "web", new BlockWeb().setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
      registerBlock(31, "tallgrass", new BlockTallGrass().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tallgrass"));
      registerBlock(32, "deadbush", new BlockDeadBush().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("deadbush"));
      registerBlock(33, "piston", new BlockPistonBase(false).setUnlocalizedName("pistonBase"));
      registerBlock(34, "piston_head", new BlockPistonExtension().setUnlocalizedName("pistonBase"));
      registerBlock(35, "wool", new BlockColored(Material.cloth).setHardness(0.8F).setStepSound(soundTypeCloth).setUnlocalizedName("cloth"));
      registerBlock(36, "piston_extension", new BlockPistonMoving());
      registerBlock(37, "yellow_flower", new BlockYellowFlower().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower1"));
      registerBlock(38, "red_flower", new BlockRedFlower().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower2"));
      Block var6 = new BlockMushroom().setHardness(0.0F).setStepSound(soundTypeGrass).setLightLevel(0.125F).setUnlocalizedName("mushroom");
      registerBlock(39, "brown_mushroom", var6);
      Block var7 = new BlockMushroom().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("mushroom");
      registerBlock(40, "red_mushroom", var7);
      registerBlock(
         41,
         "gold_block",
         new Block(Material.iron, MapColor.goldColor)
            .setHardness(3.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypeMetal)
            .setUnlocalizedName("blockGold")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(
         42,
         "iron_block",
         new Block(Material.iron, MapColor.ironColor)
            .setHardness(5.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypeMetal)
            .setUnlocalizedName("blockIron")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(
         43,
         "double_stone_slab",
         new BlockDoubleStoneSlab().setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab")
      );
      registerBlock(
         44, "stone_slab", new BlockHalfStoneSlab().setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab")
      );
      Block var8 = new Block(Material.rock, MapColor.redColor)
         .setHardness(2.0F)
         .setResistance(10.0F)
         .setStepSound(soundTypePiston)
         .setUnlocalizedName("brick")
         .setCreativeTab(CreativeTabs.tabBlock);
      registerBlock(45, "brick_block", var8);
      registerBlock(46, "tnt", new BlockTNT().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tnt"));
      registerBlock(47, "bookshelf", new BlockBookshelf().setHardness(1.5F).setStepSound(soundTypeWood).setUnlocalizedName("bookshelf"));
      registerBlock(
         48,
         "mossy_cobblestone",
         new Block(Material.rock)
            .setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("stoneMoss")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(49, "obsidian", new BlockObsidian().setHardness(50.0F).setResistance(2000.0F).setStepSound(soundTypePiston).setUnlocalizedName("obsidian"));
      registerBlock(50, "torch", new BlockTorch().setHardness(0.0F).setLightLevel(0.9375F).setStepSound(soundTypeWood).setUnlocalizedName("torch"));
      registerBlock(51, "fire", new BlockFire().setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeCloth).setUnlocalizedName("fire").disableStats());
      registerBlock(52, "mob_spawner", new BlockMobSpawner().setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
      registerBlock(
         53, "oak_stairs", new BlockStairs(var4.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK)).setUnlocalizedName("stairsWood")
      );
      registerBlock(54, "chest", new BlockChest(0).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chest"));
      registerBlock(
         55, "redstone_wire", new BlockRedstoneWire().setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("redstoneDust").disableStats()
      );
      registerBlock(56, "diamond_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreDiamond"));
      registerBlock(
         57,
         "diamond_block",
         new Block(Material.iron, MapColor.diamondColor)
            .setHardness(5.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypeMetal)
            .setUnlocalizedName("blockDiamond")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(58, "crafting_table", new BlockWorkbench().setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("workbench"));
      registerBlock(59, "wheat", new BlockCrops().setUnlocalizedName("crops"));
      Block var9 = new BlockFarmland().setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("farmland");
      registerBlock(60, "farmland", var9);
      registerBlock(
         61,
         "furnace",
         new BlockFurnace(false).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations)
      );
      registerBlock(
         62, "lit_furnace", new BlockFurnace(true).setHardness(3.5F).setStepSound(soundTypePiston).setLightLevel(0.875F).setUnlocalizedName("furnace")
      );
      registerBlock(63, "standing_sign", new BlockStandingSign().setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
      registerBlock(64, "wooden_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorOak").disableStats());
      registerBlock(65, "ladder", new BlockLadder().setHardness(0.4F).setStepSound(soundTypeLadder).setUnlocalizedName("ladder"));
      registerBlock(66, "rail", new BlockRail().setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("rail"));
      registerBlock(67, "stone_stairs", new BlockStairs(var3.getDefaultState()).setUnlocalizedName("stairsStone"));
      registerBlock(68, "wall_sign", new BlockWallSign().setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
      registerBlock(69, "lever", new BlockLever().setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("lever"));
      registerBlock(
         70,
         "stone_pressure_plate",
         new BlockPressurePlate(Material.rock, BlockPressurePlate.Sensitivity.MOBS)
            .setHardness(0.5F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("pressurePlateStone")
      );
      registerBlock(71, "iron_door", new BlockDoor(Material.iron).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
      registerBlock(
         72,
         "wooden_pressure_plate",
         new BlockPressurePlate(Material.wood, BlockPressurePlate.Sensitivity.EVERYTHING)
            .setHardness(0.5F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("pressurePlateWood")
      );
      registerBlock(
         73,
         "redstone_ore",
         new BlockRedstoneOre(false)
            .setHardness(3.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("oreRedstone")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(
         74,
         "lit_redstone_ore",
         new BlockRedstoneOre(true).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone")
      );
      registerBlock(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("notGate"));
      registerBlock(
         76,
         "redstone_torch",
         new BlockRedstoneTorch(true)
            .setHardness(0.0F)
            .setLightLevel(0.5F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("notGate")
            .setCreativeTab(CreativeTabs.tabRedstone)
      );
      registerBlock(77, "stone_button", new BlockButtonStone().setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("button"));
      registerBlock(78, "snow_layer", new BlockSnow().setHardness(0.1F).setStepSound(soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
      registerBlock(79, "ice", new BlockIce().setHardness(0.5F).setLightOpacity(3).setStepSound(soundTypeGlass).setUnlocalizedName("ice"));
      registerBlock(80, "snow", new BlockSnowBlock().setHardness(0.2F).setStepSound(soundTypeSnow).setUnlocalizedName("snow"));
      registerBlock(81, "cactus", new BlockCactus().setHardness(0.4F).setStepSound(soundTypeCloth).setUnlocalizedName("cactus"));
      registerBlock(82, "clay", new BlockClay().setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("clay"));
      registerBlock(83, "reeds", new BlockReed().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("reeds").disableStats());
      registerBlock(84, "jukebox", new BlockJukebox().setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("jukebox"));
      registerBlock(
         85,
         "fence",
         new BlockFence(Material.wood, BlockPlanks.EnumType.OAK.getMapColor())
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("fence")
      );
      Block var10 = new BlockPumpkin().setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkin");
      registerBlock(86, "pumpkin", var10);
      registerBlock(87, "netherrack", new BlockNetherrack().setHardness(0.4F).setStepSound(soundTypePiston).setUnlocalizedName("hellrock"));
      registerBlock(88, "soul_sand", new BlockSoulSand().setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("hellsand"));
      registerBlock(
         89, "glowstone", new BlockGlowstone(Material.glass).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("lightgem")
      );
      registerBlock(90, "portal", new BlockPortal().setHardness(-1.0F).setStepSound(soundTypeGlass).setLightLevel(0.75F).setUnlocalizedName("portal"));
      registerBlock(91, "lit_pumpkin", new BlockPumpkin().setHardness(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
      registerBlock(92, "cake", new BlockCake().setHardness(0.5F).setStepSound(soundTypeCloth).setUnlocalizedName("cake").disableStats());
      registerBlock(
         93, "unpowered_repeater", new BlockRedstoneRepeater(false).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats()
      );
      registerBlock(
         94, "powered_repeater", new BlockRedstoneRepeater(true).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats()
      );
      registerBlock(
         95, "stained_glass", new BlockStainedGlass(Material.glass).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("stainedGlass")
      );
      registerBlock(
         96, "trapdoor", new BlockTrapDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("trapdoor").disableStats()
      );
      registerBlock(97, "monster_egg", new BlockSilverfish().setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
      Block var11 = new BlockStoneBrick().setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebricksmooth");
      registerBlock(98, "stonebrick", var11);
      registerBlock(
         99,
         "brown_mushroom_block",
         new BlockHugeMushroom(Material.wood, MapColor.dirtColor, var6).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom")
      );
      registerBlock(
         100,
         "red_mushroom_block",
         new BlockHugeMushroom(Material.wood, MapColor.redColor, var7).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom")
      );
      registerBlock(
         101,
         "iron_bars",
         new BlockPane(Material.iron, true).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("fenceIron")
      );
      registerBlock(102, "glass_pane", new BlockPane(Material.glass, false).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinGlass"));
      Block var12 = new BlockMelon().setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("melon");
      registerBlock(103, "melon_block", var12);
      registerBlock(104, "pumpkin_stem", new BlockStem(var10).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
      registerBlock(105, "melon_stem", new BlockStem(var12).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
      registerBlock(106, "vine", new BlockVine().setHardness(0.2F).setStepSound(soundTypeGrass).setUnlocalizedName("vine"));
      registerBlock(
         107,
         "fence_gate",
         new BlockFenceGate(BlockPlanks.EnumType.OAK).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fenceGate")
      );
      registerBlock(108, "brick_stairs", new BlockStairs(var8.getDefaultState()).setUnlocalizedName("stairsBrick"));
      registerBlock(
         109,
         "stone_brick_stairs",
         new BlockStairs(var11.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT))
            .setUnlocalizedName("stairsStoneBrickSmooth")
      );
      registerBlock(110, "mycelium", new BlockMycelium().setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("mycel"));
      registerBlock(111, "waterlily", new BlockLilyPad().setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("waterlily"));
      Block var13 = new BlockNetherBrick()
         .setHardness(2.0F)
         .setResistance(10.0F)
         .setStepSound(soundTypePiston)
         .setUnlocalizedName("netherBrick")
         .setCreativeTab(CreativeTabs.tabBlock);
      registerBlock(112, "nether_brick", var13);
      registerBlock(
         113,
         "nether_brick_fence",
         new BlockFence(Material.rock, MapColor.netherrackColor)
            .setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("netherFence")
      );
      registerBlock(114, "nether_brick_stairs", new BlockStairs(var13.getDefaultState()).setUnlocalizedName("stairsNetherBrick"));
      registerBlock(115, "nether_wart", new BlockNetherWart().setUnlocalizedName("netherStalk"));
      registerBlock(116, "enchanting_table", new BlockEnchantmentTable().setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable"));
      registerBlock(117, "brewing_stand", new BlockBrewingStand().setHardness(0.5F).setLightLevel(0.125F).setUnlocalizedName("brewingStand"));
      registerBlock(118, "cauldron", new BlockCauldron().setHardness(2.0F).setUnlocalizedName("cauldron"));
      registerBlock(119, "end_portal", new BlockEndPortal(Material.portal).setHardness(-1.0F).setResistance(6000000.0F));
      registerBlock(
         120,
         "end_portal_frame",
         new BlockEndPortalFrame()
            .setStepSound(soundTypeGlass)
            .setLightLevel(0.125F)
            .setHardness(-1.0F)
            .setUnlocalizedName("endPortalFrame")
            .setResistance(6000000.0F)
            .setCreativeTab(CreativeTabs.tabDecorations)
      );
      registerBlock(
         121,
         "end_stone",
         new Block(Material.rock, MapColor.sandColor)
            .setHardness(3.0F)
            .setResistance(15.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("whiteStone")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(
         122,
         "dragon_egg",
         new BlockDragonEgg().setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setLightLevel(0.125F).setUnlocalizedName("dragonEgg")
      );
      registerBlock(
         123,
         "redstone_lamp",
         new BlockRedstoneLight(false)
            .setHardness(0.3F)
            .setStepSound(soundTypeGlass)
            .setUnlocalizedName("redstoneLight")
            .setCreativeTab(CreativeTabs.tabRedstone)
      );
      registerBlock(124, "lit_redstone_lamp", new BlockRedstoneLight(true).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight"));
      registerBlock(
         125, "double_wooden_slab", new BlockDoubleWoodSlab().setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab")
      );
      registerBlock(
         126, "wooden_slab", new BlockHalfWoodSlab().setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab")
      );
      registerBlock(127, "cocoa", new BlockCocoa().setHardness(0.2F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("cocoa"));
      registerBlock(
         128,
         "sandstone_stairs",
         new BlockStairs(var5.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH)).setUnlocalizedName("stairsSandStone")
      );
      registerBlock(129, "emerald_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreEmerald"));
      registerBlock(
         130,
         "ender_chest",
         new BlockEnderChest().setHardness(22.5F).setResistance(1000.0F).setStepSound(soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(0.5F)
      );
      registerBlock(131, "tripwire_hook", new BlockTripWireHook().setUnlocalizedName("tripWireSource"));
      registerBlock(132, "tripwire", new BlockTripWire().setUnlocalizedName("tripWire"));
      registerBlock(
         133,
         "emerald_block",
         new Block(Material.iron, MapColor.emeraldColor)
            .setHardness(5.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypeMetal)
            .setUnlocalizedName("blockEmerald")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(
         134,
         "spruce_stairs",
         new BlockStairs(var4.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE)).setUnlocalizedName("stairsWoodSpruce")
      );
      registerBlock(
         135,
         "birch_stairs",
         new BlockStairs(var4.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH)).setUnlocalizedName("stairsWoodBirch")
      );
      registerBlock(
         136,
         "jungle_stairs",
         new BlockStairs(var4.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE)).setUnlocalizedName("stairsWoodJungle")
      );
      registerBlock(137, "command_block", new BlockCommandBlock().setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("commandBlock"));
      registerBlock(138, "beacon", new BlockBeacon().setUnlocalizedName("beacon").setLightLevel(1.0F));
      registerBlock(139, "cobblestone_wall", new BlockWall(var3).setUnlocalizedName("cobbleWall"));
      registerBlock(140, "flower_pot", new BlockFlowerPot().setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("flowerPot"));
      registerBlock(141, "carrots", new BlockCarrot().setUnlocalizedName("carrots"));
      registerBlock(142, "potatoes", new BlockPotato().setUnlocalizedName("potatoes"));
      registerBlock(143, "wooden_button", new BlockButtonWood().setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("button"));
      registerBlock(144, "skull", new BlockSkull().setHardness(1.0F).setStepSound(soundTypePiston).setUnlocalizedName("skull"));
      registerBlock(145, "anvil", new BlockAnvil().setHardness(5.0F).setStepSound(soundTypeAnvil).setResistance(2000.0F).setUnlocalizedName("anvil"));
      registerBlock(146, "trapped_chest", new BlockChest(1).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chestTrap"));
      registerBlock(
         147,
         "light_weighted_pressure_plate",
         new BlockPressurePlateWeighted(Material.iron, 15, MapColor.goldColor)
            .setHardness(0.5F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("weightedPlate_light")
      );
      registerBlock(
         148,
         "heavy_weighted_pressure_plate",
         new BlockPressurePlateWeighted(Material.iron, 150).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_heavy")
      );
      registerBlock(
         149,
         "unpowered_comparator",
         new BlockRedstoneComparator(false).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats()
      );
      registerBlock(
         150,
         "powered_comparator",
         new BlockRedstoneComparator(true).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats()
      );
      registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
      registerBlock(
         152,
         "redstone_block",
         new BlockCompressedPowered(Material.iron, MapColor.tntColor)
            .setHardness(5.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypeMetal)
            .setUnlocalizedName("blockRedstone")
            .setCreativeTab(CreativeTabs.tabRedstone)
      );
      registerBlock(
         153,
         "quartz_ore",
         new BlockOre(MapColor.netherrackColor).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherquartz")
      );
      registerBlock(154, "hopper", new BlockHopper().setHardness(3.0F).setResistance(8.0F).setStepSound(soundTypeMetal).setUnlocalizedName("hopper"));
      Block var14 = new BlockQuartz().setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("quartzBlock");
      registerBlock(155, "quartz_block", var14);
      registerBlock(
         156,
         "quartz_stairs",
         new BlockStairs(var14.getDefaultState().withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.DEFAULT)).setUnlocalizedName("stairsQuartz")
      );
      registerBlock(157, "activator_rail", new BlockRailPowered().setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("activatorRail"));
      registerBlock(158, "dropper", new BlockDropper().setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dropper"));
      registerBlock(
         159,
         "stained_hardened_clay",
         new BlockColored(Material.rock).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardenedStained")
      );
      registerBlock(
         160, "stained_glass_pane", new BlockStainedGlassPane().setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinStainedGlass")
      );
      registerBlock(161, "leaves2", new BlockNewLeaf().setUnlocalizedName("leaves"));
      registerBlock(162, "log2", new BlockNewLog().setUnlocalizedName("log"));
      registerBlock(
         163,
         "acacia_stairs",
         new BlockStairs(var4.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA)).setUnlocalizedName("stairsWoodAcacia")
      );
      registerBlock(
         164,
         "dark_oak_stairs",
         new BlockStairs(var4.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK)).setUnlocalizedName("stairsWoodDarkOak")
      );
      registerBlock(165, "slime", new BlockSlime().setUnlocalizedName("slime").setStepSound(SLIME_SOUND));
      registerBlock(166, "barrier", new BlockBarrier().setUnlocalizedName("barrier"));
      registerBlock(
         167,
         "iron_trapdoor",
         new BlockTrapDoor(Material.iron).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats()
      );
      registerBlock(
         168, "prismarine", new BlockPrismarine().setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("prismarine")
      );
      registerBlock(
         169,
         "sea_lantern",
         new BlockSeaLantern(Material.glass).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("seaLantern")
      );
      registerBlock(
         170, "hay_block", new BlockHay().setHardness(0.5F).setStepSound(soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(171, "carpet", new BlockCarpet().setHardness(0.1F).setStepSound(soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
      registerBlock(
         172, "hardened_clay", new BlockHardenedClay().setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardened")
      );
      registerBlock(
         173,
         "coal_block",
         new Block(Material.rock, MapColor.blackColor)
            .setHardness(5.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setUnlocalizedName("blockCoal")
            .setCreativeTab(CreativeTabs.tabBlock)
      );
      registerBlock(174, "packed_ice", new BlockPackedIce().setHardness(0.5F).setStepSound(soundTypeGlass).setUnlocalizedName("icePacked"));
      registerBlock(175, "double_plant", new BlockDoublePlant());
      registerBlock(
         176,
         "standing_banner",
         new BlockBanner.BlockBannerStanding().setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats()
      );
      registerBlock(
         177, "wall_banner", new BlockBanner.BlockBannerHanging().setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats()
      );
      registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
      Block var15 = new BlockRedSandstone().setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("redSandStone");
      registerBlock(179, "red_sandstone", var15);
      registerBlock(
         180,
         "red_sandstone_stairs",
         new BlockStairs(var15.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH))
            .setUnlocalizedName("stairsRedSandStone")
      );
      registerBlock(
         181,
         "double_stone_slab2",
         new BlockDoubleStoneSlabNew().setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2")
      );
      registerBlock(
         182, "stone_slab2", new BlockHalfStoneSlabNew().setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2")
      );
      registerBlock(
         183,
         "spruce_fence_gate",
         new BlockFenceGate(BlockPlanks.EnumType.SPRUCE)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("spruceFenceGate")
      );
      registerBlock(
         184,
         "birch_fence_gate",
         new BlockFenceGate(BlockPlanks.EnumType.BIRCH).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFenceGate")
      );
      registerBlock(
         185,
         "jungle_fence_gate",
         new BlockFenceGate(BlockPlanks.EnumType.JUNGLE)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("jungleFenceGate")
      );
      registerBlock(
         186,
         "dark_oak_fence_gate",
         new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("darkOakFenceGate")
      );
      registerBlock(
         187,
         "acacia_fence_gate",
         new BlockFenceGate(BlockPlanks.EnumType.ACACIA)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("acaciaFenceGate")
      );
      registerBlock(
         188,
         "spruce_fence",
         new BlockFence(Material.wood, BlockPlanks.EnumType.SPRUCE.getMapColor())
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("spruceFence")
      );
      registerBlock(
         189,
         "birch_fence",
         new BlockFence(Material.wood, BlockPlanks.EnumType.BIRCH.getMapColor())
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("birchFence")
      );
      registerBlock(
         190,
         "jungle_fence",
         new BlockFence(Material.wood, BlockPlanks.EnumType.JUNGLE.getMapColor())
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("jungleFence")
      );
      registerBlock(
         191,
         "dark_oak_fence",
         new BlockFence(Material.wood, BlockPlanks.EnumType.DARK_OAK.getMapColor())
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("darkOakFence")
      );
      registerBlock(
         192,
         "acacia_fence",
         new BlockFence(Material.wood, BlockPlanks.EnumType.ACACIA.getMapColor())
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setStepSound(soundTypeWood)
            .setUnlocalizedName("acaciaFence")
      );
      registerBlock(
         193, "spruce_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorSpruce").disableStats()
      );
      registerBlock(
         194, "birch_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorBirch").disableStats()
      );
      registerBlock(
         195, "jungle_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorJungle").disableStats()
      );
      registerBlock(
         196, "acacia_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorAcacia").disableStats()
      );
      registerBlock(
         197, "dark_oak_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats()
      );
      blockRegistry.validateKey();

      for (Block var17 : blockRegistry) {
         if (var17.blockMaterial == Material.air) {
            var17.useNeighborBrightness = false;
         } else {
            boolean var18 = false;
            boolean var19 = var17 instanceof BlockStairs;
            boolean var20 = var17 instanceof BlockSlab;
            boolean var21 = var17 == var9;
            boolean var22 = var17.translucent;
            boolean var23 = var17.lightOpacity == 0;
            if (var19 || var20 || var21 || var22 || var23) {
               var18 = true;
            }

            var17.useNeighborBrightness = var18;
         }
      }

      for (Block var25 : blockRegistry) {
         for (IBlockState var27 : var25.getBlockState().getValidStates()) {
            int var28 = blockRegistry.getIDForObject(var25) << 4 | var25.getMetaFromState(var27);
            BLOCK_STATE_IDS.put(var27, var28);
         }
      }
   }

   private static void registerBlock(int integer, ResourceLocation resourceLocation, Block block) {
      blockRegistry.register(integer, resourceLocation, block);
   }

   private static void registerBlock(int integer, String string, Block block) {
      registerBlock(integer, new ResourceLocation(string), block);
   }

   public enum EnumOffsetType {
      NONE,
      XZ,
      XYZ;

      private static Block.EnumOffsetType[] Method423() {
         return new Block.EnumOffsetType[]{NONE, XZ, XYZ};
      }
   }

   public static class SoundType {
      public final String soundName;
      public final float volume;
      public final float frequency;

      public SoundType(String string, float float2, float float3) {
         this.soundName = string;
         this.volume = float2;
         this.frequency = float3;
      }

      public float getVolume() {
         return this.volume;
      }

      public float getFrequency() {
         return this.frequency;
      }

      public String getBreakSound() {
         return "dig." + this.soundName;
      }

      public String getStepSound() {
         return "step." + this.soundName;
      }

      public String getPlaceSound() {
         return this.getBreakSound();
      }
   }
}
