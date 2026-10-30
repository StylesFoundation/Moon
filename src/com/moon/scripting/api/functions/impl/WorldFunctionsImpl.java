package com.moon.scripting.api.functions.impl;

import com.moon.Moon;
import com.moon.module.impl.combat.AntiBot;
import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.functions.WorldFunctions;
import com.moon.scripting.api.types.Block;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.api.types.Vec3d;
import java.util.List;
import java.util.Map;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public class WorldFunctionsImpl implements WorldFunctions {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private AntiBot antiBot;

   @Override
   public void remove(int entityId) {
      if (mc.theWorld != null) {
         mc.theWorld.removeEntityFromWorld(entityId);
      }
   }

   @Override
   public Block get_block_at(double x, double y, double z) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         BlockPos pos = new BlockPos(x, y, z);
         IBlockState blockState = mc.theWorld.getBlockState(pos);
         if (blockState == null) {
            return null;
         }

         net.minecraft.block.Block block = blockState.getBlock();
         return block == null
            ? null
            : new Block(
               block.Method440(),
               block.getLocalizedName(),
               block.isFullBlock(),
               block.isTranslucent(),
               block.getBlockHardness(mc.theWorld, pos),
               block.Method434(mc.thePlayer, mc.theWorld, pos),
               block.slipperiness
            );
      } else {
         return null;
      }
   }

   @Override
   public Block get_block_at_vec(Vec3d vec3d) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         BlockPos pos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
         IBlockState blockState = mc.theWorld.getBlockState(pos);
         if (blockState == null) {
            return null;
         }

         net.minecraft.block.Block block = blockState.getBlock();
         return block == null
            ? null
            : new Block(
               block.Method440(),
               block.getLocalizedName(),
               block.isFullBlock(),
               block.isTranslucent(),
               block.getBlockHardness(mc.theWorld, pos),
               block.Method434(mc.thePlayer, mc.theWorld, pos),
               block.slipperiness
            );
      } else {
         return null;
      }
   }

   @Override
   public int[] get_entities() {
      if (mc.theWorld == null) {
         return new int[0];
      }

      List loadedEntities = mc.theWorld.loadedEntityList;
      int[] entityIds = new int[loadedEntities.size()];

      for (int i = 0; i < loadedEntities.size(); i++) {
         Entity entity = (Entity)loadedEntities.get(i);
         entityIds[i] = entity.getEntityId();
      }

      return entityIds;
   }

   @Override
   public int[] get_players() {
      if (mc.theWorld == null) {
         return new int[0];
      }

      List playerEntities = mc.theWorld.playerEntities;
      int[] playerIds = new int[playerEntities.size()];

      for (int i = 0; i < playerEntities.size(); i++) {
         Entity entity = (Entity)playerEntities.get(i);
         playerIds[i] = entity.getEntityId();
      }

      return playerIds;
   }

   @Override
   public String get_name(int entityId) {
      if (mc.theWorld == null) {
         return null;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? null : entity.getName();
   }

   @Override
   public String get_display_name(int entityId) {
      if (mc.theWorld == null) {
         return null;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? null : entity.getDisplayName().getUnformattedText();
   }

   @Override
   public int get_hurt_time(int entityId) {
      if (mc.theWorld == null) {
         return 0;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityLivingBase living ? living.hurtTime : 0;
      }
   }

   @Override
   public int get_hurt_resistant_time(int entityId) {
      if (mc.theWorld == null) {
         return 0;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityLivingBase living ? living.hurtResistantTime : 0;
      }
   }

   @Override
   public double get_health(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityLivingBase living ? living.getHealth() : 0.0;
      }
   }

   @Override
   public double get_max_health(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityLivingBase living ? living.getMaxHealth() : 0.0;
      }
   }

   @Override
   public boolean is_sprinting(int entityId) {
      if (mc.theWorld == null) {
         return false;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? false : entity.isSprinting();
   }

   @Override
   public boolean is_sneaking(int entityId) {
      if (mc.theWorld == null) {
         return false;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? false : entity.isSneaking();
   }

   @Override
   public boolean is_invisible(int entityId) {
      if (mc.theWorld == null) {
         return false;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? false : entity.isInvisible();
   }

   @Override
   public boolean is_burning(int entityId) {
      if (mc.theWorld == null) {
         return false;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? false : entity.isBurning();
   }

   @Override
   public boolean is_riding(int entityId) {
      if (mc.theWorld == null) {
         return false;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? false : entity.isInvisible();
   }

   @Override
   public Facing get_facing(int entityId) {
      if (mc.theWorld == null) {
         return null;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? Facing.UP : Facing.values()[entity.getHorizontalFacing().ordinal()];
   }

   @Override
   public float get_yaw(int entityId) {
      if (mc.theWorld == null) {
         return 0.0F;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0F : entity.rotationYaw;
   }

   @Override
   public float get_pitch(int entityId) {
      if (mc.theWorld == null) {
         return 0.0F;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0F : entity.rotationPitch;
   }

   @Override
   public int get_ticks_existed(int entityId) {
      if (mc.theWorld == null) {
         return 0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0 : entity.ticksExisted;
   }

   @Override
   public boolean is_player(int entityId) {
      return mc.theWorld == null ? false : mc.theWorld.getEntityByID(entityId) instanceof EntityPlayer;
   }

   @Override
   public boolean is_bot(int entityId) {
      if (mc.theWorld == null) {
         return false;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      if (entity == null) {
         return false;
      }

      if (this.antiBot == null) {
         this.antiBot = Moon.getInstance().getModuleManager().getModule(AntiBot.class);
      }

      return this.antiBot.isBot(entityId);
   }

   @Override
   public boolean is_target(int entityId) {
      if (mc.theWorld == null) {
         return false;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityPlayer player ? Moon.getInstance().getTargetStorage().isTarget(player) : false;
      }
   }

   @Override
   public boolean is_friend(int entityId) {
      if (mc.theWorld == null) {
         return false;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityPlayer player ? Moon.getInstance().getFriendStorage().isFriend(player) : false;
      }
   }

   @Override
   public String held_item(int entityId) {
      if (mc.theWorld == null) {
         return "";
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityPlayer player ? player.getHeldItem().getUnlocalizedName() : "";
      }
   }

   @Override
   public float get_width(int entityId) {
      if (mc.theWorld == null) {
         return 0.0F;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0F : entity.width;
   }

   @Override
   public float get_height(int entityId) {
      if (mc.theWorld == null) {
         return 0.0F;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0F : entity.height;
   }

   @Override
   public double get_position_x(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0 : entity.posX;
   }

   @Override
   public double get_position_y(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0 : entity.posY;
   }

   @Override
   public double get_position_z(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0 : entity.posZ;
   }

   @Override
   public Vec3d get_position(int entityId) {
      if (mc.theWorld == null) {
         return null;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? null : new Vec3d(entity.posX, entity.posY, entity.posZ);
   }

   @Override
   public double get_prev_position_x(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0 : entity.prevPosX;
   }

   @Override
   public double get_prev_position_y(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0 : entity.prevPosY;
   }

   @Override
   public double get_prev_position_z(int entityId) {
      if (mc.theWorld == null) {
         return 0.0;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? 0.0 : entity.prevPosZ;
   }

   @Override
   public Vec3d get_prev_position(int entityId) {
      if (mc.theWorld == null) {
         return null;
      }

      Entity entity = mc.theWorld.getEntityByID(entityId);
      return entity == null ? null : new Vec3d(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
   }

   @Override
   public Potion[] get_active_effects(int entityId) {
      if (mc.theWorld == null) {
         return new Potion[0];
      } else if (!(mc.theWorld.getEntityByID(entityId) instanceof EntityLiving living)) {
         return null;
      } else {
         Map<Integer, PotionEffect> activeEffects = living.Method1032();
         Potion[] effects = new Potion[activeEffects.size()];
         int index = 0;

         for (Integer potionId : activeEffects.keySet()) {
            effects[index] = this.get_effect(entityId, potionId);
            index++;
         }

         return effects;
      }
   }

   @Override
   public boolean has_effect(int entityId, int potionId) {
      if (mc.theWorld == null) {
         return false;
      } else {
         return mc.theWorld.getEntityByID(entityId) instanceof EntityLiving living ? living.Method1032().get(entityId) != null : false;
      }
   }

   @Override
   public Potion get_effect(int entityId, int potionId) {
      if (mc.theWorld == null) {
         return null;
      }

      if (mc.theWorld.getEntityByID(entityId) instanceof EntityLiving living) {
         PotionEffect potionEffect = living.Method1032().get(entityId);
         if (potionEffect == null) {
            return null;
         }

         net.minecraft.potion.Potion potionType = net.minecraft.potion.Potion.potionTypes[potionEffect.getPotionID()];
         Potion potion = new Potion();
         potion.id = potionEffect.getPotionID();
         potion.amplifier = potionEffect.getAmplifier();
         potion.duration = potionEffect.getDuration();
         potion.color = potionType.getLiquidColor();
         potion.name = potionType.getName();
         potion.bad_effect = potionType.isBadEffect();
         return potion;
      } else {
         return null;
      }
   }

   @Override
   public String get_biome() {
      return mc.theWorld == null ? null : mc.theWorld.getBiomeGenForCoords(mc.thePlayer.playerLocation).biomeName;
   }
}
