package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging {
   public EntityPainting.EnumArt art;

   public EntityPainting(World world) {
      super(world);
   }

   public EntityPainting(World world, BlockPos blockPos, EnumFacing enumFacing) {
      super(world, blockPos);
      ArrayList var7 = Lists.newArrayList();

      for (EntityPainting.EnumArt var11 : EntityPainting.EnumArt.values()) {
         this.art = var11;
         this.updateFacingWithBoundingBox(enumFacing);
         if (this.onValidSurface()) {
            var7.add(var11);
         }
      }

      if (!var7.isEmpty()) {
         this.art = (EntityPainting.EnumArt)var7.get(this.rand.nextInt(var7.size()));
      }

      this.updateFacingWithBoundingBox(enumFacing);
   }

   public EntityPainting(World world, BlockPos blockPos, EnumFacing enumFacing, String string) {
      this(world, blockPos, enumFacing);

      for (EntityPainting.EnumArt var11 : EntityPainting.EnumArt.values()) {
         if (var11.title.equals(string)) {
            this.art = var11;
            break;
         }
      }

      this.updateFacingWithBoundingBox(enumFacing);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setString("Motive", this.art.title);
      super.writeEntityToNBT(nBTTagCompound);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      String var5 = nBTTagCompound.getString("Motive");

      for (EntityPainting.EnumArt var9 : EntityPainting.EnumArt.values()) {
         if (var9.title.equals(var5)) {
            this.art = var9;
         }
      }

      if (this.art == null) {
         this.art = EntityPainting.EnumArt.KEBAB;
      }

      super.readEntityFromNBT(nBTTagCompound);
   }

   @Override
   public int getWidthPixels() {
      return this.art.sizeX;
   }

   @Override
   public int getHeightPixels() {
      return this.art.sizeY;
   }

   @Override
   public void onBroken(Entity entity) {
      if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         if (entity instanceof EntityPlayer var5 && var5.capabilities.isCreativeMode) {
            return;
         }

         this.entityDropItem(new ItemStack(Items.painting), 0.0F);
      }
   }

   @Override
   public void setLocationAndAngles(double double1, double double2, double double3, float float4, float float5) {
      BlockPos var9 = this.hangingPosition.add(double1 - this.posX, double2 - this.posY, double3 - this.posZ);
      this.setPosition(var9.getX(), var9.getY(), var9.getZ());
   }

   @Override
   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      BlockPos var11 = this.hangingPosition.add(double1 - this.posX, double2 - this.posY, double3 - this.posZ);
      this.setPosition(var11.getX(), var11.getY(), var11.getZ());
   }

   public enum EnumArt {
      KEBAB("Kebab", 16, 16, 0, 0),
      AZTEC("Aztec", 16, 16, 16, 0),
      ALBAN("Alban", 16, 16, 32, 0),
      AZTEC_2("Aztec2", 16, 16, 48, 0),
      BOMB("Bomb", 16, 16, 64, 0),
      PLANT("Plant", 16, 16, 80, 0),
      WASTELAND("Wasteland", 16, 16, 96, 0),
      POOL("Pool", 32, 16, 0, 32),
      COURBET("Courbet", 32, 16, 32, 32),
      SEA("Sea", 32, 16, 64, 32),
      SUNSET("Sunset", 32, 16, 96, 32),
      CREEBET("Creebet", 32, 16, 128, 32),
      WANDERER("Wanderer", 16, 32, 0, 64),
      GRAHAM("Graham", 16, 32, 16, 64),
      MATCH("Match", 32, 32, 0, 128),
      BUST("Bust", 32, 32, 32, 128),
      STAGE("Stage", 32, 32, 64, 128),
      VOID("Void", 32, 32, 96, 128),
      SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
      WITHER("Wither", 32, 32, 160, 128),
      FIGHTERS("Fighters", 64, 32, 0, 96),
      POINTER("Pointer", 64, 64, 0, 192),
      PIGSCENE("Pigscene", 64, 64, 64, 192),
      BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
      SKELETON("Skeleton", 64, 48, 192, 64),
      DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);

      public static final int field_180001_A = 13;
      public final String title;
      public final int sizeX;
      public final int sizeY;
      public final int offsetX;
      public final int offsetY;

      EnumArt(String string3, int integer4, int integer5, int integer6, int integer7) {
         this.title = string3;
         this.sizeX = integer4;
         this.sizeY = integer5;
         this.offsetX = integer6;
         this.offsetY = integer7;
      }

      private static EntityPainting.EnumArt[] Method1021() {
         return new EntityPainting.EnumArt[]{
            KEBAB,
            AZTEC,
            ALBAN,
            AZTEC_2,
            BOMB,
            PLANT,
            WASTELAND,
            POOL,
            COURBET,
            SEA,
            SUNSET,
            CREEBET,
            WANDERER,
            GRAHAM,
            MATCH,
            BUST,
            STAGE,
            VOID,
            SKULL_AND_ROSES,
            WITHER,
            FIGHTERS,
            POINTER,
            PIGSCENE,
            BURNING_SKULL,
            SKELETON,
            DONKEY_KONG
         };
      }
   }
}
