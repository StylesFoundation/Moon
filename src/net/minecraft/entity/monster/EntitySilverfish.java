package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntitySilverfish extends EntityMob {
   private final EntitySilverfish.AISummonSilverfish summonSilverfish;

   public EntitySilverfish(World world) {
      super(world);
      this.setSize(0.4F, 0.3F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(3, this.summonSilverfish = new EntitySilverfish.AISummonSilverfish());
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
      this.tasks.addTask(5, new EntitySilverfish.AIHideInStone());
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   @Override
   public double getYOffset() {
      return 0.2;
   }

   @Override
   public float getEyeHeight() {
      return 0.1F;
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   protected String getLivingSound() {
      return "mob.silverfish.say";
   }

   @Override
   protected String getHurtSound() {
      return "mob.silverfish.hit";
   }

   @Override
   protected String getDeathSound() {
      return "mob.silverfish.kill";
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (damageSource instanceof EntityDamageSource || damageSource == DamageSource.magic) {
         this.summonSilverfish.func_179462_f();
      }

      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   protected void playStepSound(BlockPos blockPos, Block block) {
      this.playSound("mob.silverfish.step", 0.15F, 1.0F);
   }

   @Override
   protected Item getDropItem() {
      return null;
   }

   @Override
   public void onUpdate() {
      this.renderYawOffset = this.rotationYaw;
      super.onUpdate();
   }

   @Override
   public float getBlockPathWeight(BlockPos blockPos) {
      return this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.stone ? 10.0F : super.getBlockPathWeight(blockPos);
   }

   @Override
   protected boolean isValidLightLevel() {
      return true;
   }

   @Override
   public boolean getCanSpawnHere() {
      if (super.getCanSpawnHere()) {
         EntityPlayer var4 = this.worldObj.getClosestPlayerToEntity(this, 5.0);
         return var4 == null;
      } else {
         return false;
      }
   }

   @Override
   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   class AIHideInStone extends EntityAIWander {
      private final EntitySilverfish silverfish = EntitySilverfish.this;
      private EnumFacing facing;
      private boolean field_179484_c;

      public AIHideInStone() {
         super(EntitySilverfish.this, 1.0, 10);
         this.setMutexBits(1);
      }

      @Override
      public boolean shouldExecute() {
         if (this.silverfish.getAttackTarget() != null) {
            return false;
         }

         if (!this.silverfish.getNavigator().noPath()) {
            return false;
         }

         Random var4 = this.silverfish.getRNG();
         if (var4.nextInt(10) == 0) {
            this.facing = EnumFacing.random(var4);
            BlockPos var5 = new BlockPos(this.silverfish.posX, this.silverfish.posY + 0.5, this.silverfish.posZ).offset(this.facing);
            IBlockState var6 = this.silverfish.worldObj.getBlockState(var5);
            if (BlockSilverfish.canContainSilverfish(var6)) {
               this.field_179484_c = true;
               return true;
            }
         }

         this.field_179484_c = false;
         return super.shouldExecute();
      }

      @Override
      public boolean continueExecuting() {
         return !this.field_179484_c && super.continueExecuting();
      }

      @Override
      public void startExecuting() {
         if (!this.field_179484_c) {
            super.startExecuting();
         } else {
            World var4 = this.silverfish.worldObj;
            BlockPos var5 = new BlockPos(this.silverfish.posX, this.silverfish.posY + 0.5, this.silverfish.posZ).offset(this.facing);
            IBlockState var6 = var4.getBlockState(var5);
            if (BlockSilverfish.canContainSilverfish(var6)) {
               var4.setBlockState(
                  var5, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.forModelBlock(var6)), 3
               );
               this.silverfish.spawnExplosionParticle();
               this.silverfish.setDead();
            }
         }
      }
   }

   class AISummonSilverfish extends EntityAIBase {
      private final EntitySilverfish silverfish = EntitySilverfish.this;
      private int field_179463_b;

      public AISummonSilverfish() {
      }

      public void func_179462_f() {
         if (this.field_179463_b == 0) {
            this.field_179463_b = 20;
         }
      }

      @Override
      public boolean shouldExecute() {
         return this.field_179463_b > 0;
      }

      @Override
      public void updateTask() {
         this.field_179463_b--;
         if (this.field_179463_b <= 0) {
            World var4 = this.silverfish.worldObj;
            Random var5 = this.silverfish.getRNG();
            BlockPos var6 = new BlockPos(this.silverfish);

            for (byte var7 = 0; var7 <= 5 && var7 >= -5; var7 = (byte)(var7 <= 0 ? 1 - var7 : -var7)) {
               for (byte var8 = 0; var8 <= 10 && var8 >= -10; var8 = (byte)(var8 <= 0 ? 1 - var8 : -var8)) {
                  for (byte var9 = 0; var9 <= 10 && var9 >= -10; var9 = (byte)(var9 <= 0 ? 1 - var9 : -var9)) {
                     BlockPos var10 = var6.add(var8, var7, var9);
                     IBlockState var11 = var4.getBlockState(var10);
                     if (var11.getBlock() == Blocks.monster_egg) {
                        if (var4.getGameRules().getBoolean("mobGriefing")) {
                           var4.Method329(var10, true);
                        } else {
                           var4.setBlockState(var10, var11.getValue(BlockSilverfish.VARIANT).getModelBlock(), 3);
                        }

                        if (var5.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
