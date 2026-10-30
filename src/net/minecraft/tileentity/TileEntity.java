package net.minecraft.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector4f;

public abstract class TileEntity {
   private static final Logger logger = LogManager.getLogger();
   private static final Map<String, Class<? extends TileEntity>> nameToClassMap = Maps.newHashMap();
   private static final Map<Class<? extends TileEntity>, String> classToNameMap = Maps.newHashMap();
   protected World worldObj;
   protected BlockPos pos = BlockPos.ORIGIN;
   protected boolean tileEntityInvalid;
   private int blockMetadata = -1;
   protected Block blockType;
   public boolean i;
   private Vector4f j;

   private static void addMapping(Class<? extends TileEntity> class1, String string) {
      if (nameToClassMap.containsKey(string)) {
         throw new IllegalArgumentException("Duplicate id: " + string);
      }

      nameToClassMap.put(string, class1);
      classToNameMap.put(class1, string);
   }

   public World getWorld() {
      return this.worldObj;
   }

   public void setWorldObj(World world) {
      this.worldObj = world;
   }

   public boolean hasWorldObj() {
      return this.worldObj != null;
   }

   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      this.pos = new BlockPos(nBTTagCompound.getInteger("x"), nBTTagCompound.getInteger("y"), nBTTagCompound.getInteger("z"));
   }

   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      String var5 = classToNameMap.get(this.getClass());
      if (var5 == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      }

      nBTTagCompound.setString("id", var5);
      nBTTagCompound.setInteger("x", this.pos.getX());
      nBTTagCompound.setInteger("y", this.pos.getY());
      nBTTagCompound.setInteger("z", this.pos.getZ());
   }

   public static TileEntity createAndLoadEntity(NBTTagCompound nBTTagCompound) {
      TileEntity var4 = null;

      try {
         Class var5 = nameToClassMap.get(nBTTagCompound.getString("id"));
         if (var5 != null) {
            var4 = (TileEntity)var5.getDeclaredConstructor().newInstance();
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      if (var4 != null) {
         var4.readFromNBT(nBTTagCompound);
      } else {
         logger.warn("Skipping BlockEntity with id " + nBTTagCompound.getString("id"));
      }

      return var4;
   }

   public int getBlockMetadata() {
      if (this.blockMetadata == -1) {
         IBlockState var4 = this.worldObj.getBlockState(this.pos);
         this.blockMetadata = var4.getBlock().getMetaFromState(var4);
      }

      return this.blockMetadata;
   }

   public void markDirty() {
      if (this.worldObj != null) {
         IBlockState var4 = this.worldObj.getBlockState(this.pos);
         this.blockMetadata = var4.getBlock().getMetaFromState(var4);
         this.worldObj.markChunkDirty(this.pos, this);
         if (this.getBlockType() != Blocks.air) {
            this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
         }
      }
   }

   public double getDistanceSq(double double1, double double2, double double3) {
      double var7 = this.pos.getX() + 0.5 - double1;
      double var9 = this.pos.getY() + 0.5 - double2;
      double var11 = this.pos.getZ() + 0.5 - double3;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double getMaxRenderDistanceSquared() {
      return 4096.0;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Block getBlockType() {
      if (this.blockType == null) {
         this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
      }

      return this.blockType;
   }

   public Packet getDescriptionPacket() {
      return null;
   }

   public boolean isInvalid() {
      return this.tileEntityInvalid;
   }

   public void invalidate() {
      this.tileEntityInvalid = true;
   }

   public void validate() {
      this.tileEntityInvalid = false;
   }

   public boolean receiveClientEvent(int integer1, int integer2) {
      return false;
   }

   public void updateContainingBlockInfo() {
      this.blockType = null;
      this.blockMetadata = -1;
   }

   public AxisAlignedBB Method981() {
      AxisAlignedBB var4 = AxisAlignedBB.a;
      Block var5 = this.blockType;
      if (var5 == Blocks.enchanting_table) {
         var4 = new AxisAlignedBB(this.getPos(), this.getPos().add(1, 1, 1));
      } else if (var5 == Blocks.chest || var5 == Blocks.trapped_chest) {
         var4 = new AxisAlignedBB(this.getPos().add(-1, 0, -1), this.getPos().add(2, 2, 2));
      } else if (var5 != null && var5 != Blocks.beacon) {
         AxisAlignedBB var6;
         try {
            var6 = var5.getCollisionBoundingBox(this.worldObj, this.getPos(), this.worldObj.getBlockState(this.getPos()));
         } catch (Exception var7) {
            var6 = new AxisAlignedBB(this.getPos().add(-1, 0, -1), this.getPos().add(1, 1, 1));
         }

         if (var6 != null) {
            var4 = var6;
         }
      }

      return var4;
   }

   public void addInfoToCrashReport(CrashReportCategory crashReportCategory) {
      crashReportCategory.addCrashSectionCallable("Name", () -> classToNameMap.get(this.getClass()) + " // " + this.getClass().getCanonicalName());
      if (this.worldObj != null) {
         CrashReportCategory.addBlockInfo(crashReportCategory, this.pos, this.getBlockType(), this.getBlockMetadata());
         crashReportCategory.addCrashSectionCallable("Actual block type", () -> {
            int var1 = Block.getIdFromBlock(this.worldObj.getBlockState(this.pos).getBlock());

            try {
               return String.format("ID #%d (%s // %s)", var1, Block.getBlockById(var1).Method440(), Block.getBlockById(var1).getClass().getCanonicalName());
            } catch (Throwable var2) {
               return "ID #" + var1;
            }
         });
         crashReportCategory.addCrashSectionCallable("Actual block data value", () -> {
            IBlockState var4 = this.worldObj.getBlockState(this.pos);
            int var5 = var4.getBlock().getMetaFromState(var4);
            if (var5 < 0) {
               return "Unknown? (Got " + var5 + ")";
            }

            String var6 = String.format("%4s", Integer.toBinaryString(var5)).replace(" ", "0");
            return String.format("%1$d / 0x%1$X / 0b%2$s", var5, var6);
         });
      }
   }

   public void setPos(BlockPos blockPos) {
      this.pos = blockPos;
   }

   public boolean func_183000_F() {
      return false;
   }

   public Vector4f Method982() {
      return this.j;
   }

   public TileEntity Method983(Vector4f vector4f) {
      this.j = vector4f;
      return this;
   }

   static {
      addMapping(TileEntityFurnace.class, "Furnace");
      addMapping(TileEntityChest.class, "Chest");
      addMapping(TileEntityEnderChest.class, "EnderChest");
      addMapping(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
      addMapping(TileEntityDispenser.class, "Trap");
      addMapping(TileEntityDropper.class, "Dropper");
      addMapping(TileEntitySign.class, "Sign");
      addMapping(TileEntityMobSpawner.class, "MobSpawner");
      addMapping(TileEntityNote.class, "Music");
      addMapping(TileEntityPiston.class, "Piston");
      addMapping(TileEntityBrewingStand.class, "Cauldron");
      addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
      addMapping(TileEntityEndPortal.class, "Airportal");
      addMapping(TileEntityCommandBlock.class, "Control");
      addMapping(TileEntityBeacon.class, "Beacon");
      addMapping(TileEntitySkull.class, "Skull");
      addMapping(TileEntityDaylightDetector.class, "DLDetector");
      addMapping(TileEntityHopper.class, "Hopper");
      addMapping(TileEntityComparator.class, "Comparator");
      addMapping(TileEntityFlowerPot.class, "FlowerPot");
      addMapping(TileEntityBanner.class, "Banner");
   }

   public static int Method984() {
      return 0;
   }
}
