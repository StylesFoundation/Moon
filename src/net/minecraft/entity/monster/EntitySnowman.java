package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
   public EntitySnowman(World world) {
      super(world);
      this.setSize(0.7F, 1.9F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0F));
      this.tasks.addTask(2, new EntityAIWander(this, 1.0));
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(4, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10, true, false, IMob.mobSelector));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (!this.worldObj.isRemote) {
         int var4 = MathHelper.floor_double(this.posX);
         int var5 = MathHelper.floor_double(this.posY);
         int var6 = MathHelper.floor_double(this.posZ);
         if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
         }

         if (this.worldObj.getBiomeGenForCoords(new BlockPos(var4, 0, var6)).getFloatTemperature(new BlockPos(var4, var5, var6)) > 1.0F) {
            this.attackEntityFrom(DamageSource.onFire, 1.0F);
         }

         for (int var7 = 0; var7 < 4; var7++) {
            var4 = MathHelper.floor_double(this.posX + (var7 % 2 * 2 - 1) * 0.25F);
            var5 = MathHelper.floor_double(this.posY);
            var6 = MathHelper.floor_double(this.posZ + (var7 / 2 % 2 * 2 - 1) * 0.25F);
            BlockPos var8 = new BlockPos(var4, var5, var6);
            if (this.worldObj.getBlockState(var8).getBlock().getMaterial() == Material.air
               && this.worldObj.getBiomeGenForCoords(new BlockPos(var4, 0, var6)).getFloatTemperature(var8) < 0.8F
               && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, var8)) {
               this.worldObj.setBlockState(var8, Blocks.snow_layer.getDefaultState());
            }
         }
      }
   }

   @Override
   protected Item getDropItem() {
      return Items.snowball;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      int var6 = this.rand.nextInt(16);

      for (int var7 = 0; var7 < var6; var7++) {
         this.dropItem(Items.snowball, 1);
      }
   }

   @Override
   public void attackEntityWithRangedAttack(EntityLivingBase entityLivingBase, float float2) {
      EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
      double var4 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - 1.1F;
      double var6 = entityLivingBase.posX - this.posX;
      double var8 = var4 - var3.posY;
      double var10 = entityLivingBase.posZ - this.posZ;
      float var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10) * 0.2F;
      var3.setThrowableHeading(var6, var8 + var12, var10, 1.6F, 12.0F);
      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.worldObj.spawnEntityInWorld(var3);
   }

   @Override
   public float getEyeHeight() {
      return 1.7F;
   }
}
