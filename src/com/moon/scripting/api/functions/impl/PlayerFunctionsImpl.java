package com.moon.scripting.api.functions.impl;

import com.moon.scripting.api.enums.ClientStatusActions;
import com.moon.scripting.api.enums.DiggingActions;
import com.moon.scripting.api.enums.EntityAction;
import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.enums.HitType;
import com.moon.scripting.api.enums.InteractActions;
import com.moon.scripting.api.functions.PlayerFunctions;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.api.types.RayCastResult;
import com.moon.scripting.api.types.Vec2f;
import com.moon.scripting.api.types.Vec3d;
import com.moon.scripting.api.types.Vec3i;
import com.moon.util.client.ChatUtil;
import com.moon.util.client.NumberCastUtil;
import com.moon.util.game.MovementUtil;
import com.moon.util.game.RaytraceUtil;
import com.moon.util.math.MathUtil;
import com.moon.util.math.Vector3f;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.luaj.vm2.LuaTable;

public class PlayerFunctionsImpl implements PlayerFunctions {
   private static final Minecraft mc = Minecraft.getMinecraft();

   @Override
   public void jump() {
      if (mc.thePlayer != null) {
         mc.thePlayer.jump();
      }
   }

   @Override
   public void left_click() {
      if (mc.thePlayer != null) {
         int keyCode = mc.gameSettings.keyBindAttack.getKeyCode();
         KeyBinding.setKeyBindState(keyCode, true);
         KeyBinding.onTick(keyCode);
         KeyBinding.setKeyBindState(keyCode, false);
         KeyBinding.onTick(keyCode);
      }
   }

   @Override
   public void right_click() {
      if (mc.thePlayer != null) {
         int keyCode = mc.gameSettings.keyBindUseItem.getKeyCode();
         KeyBinding.setKeyBindState(keyCode, true);
         KeyBinding.setKeyBindState(keyCode, false);
         KeyBinding.onTick(keyCode);
      }
   }

   @Override
   public void swing_item() {
      if (mc.thePlayer != null) {
         mc.thePlayer.swingItem();
      }
   }

   @Override
   public void use_item() {
      if (mc.thePlayer != null) {
         ItemStack currentItem = mc.thePlayer.inventory.getCurrentItem();
         if (currentItem != null && mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, currentItem)) {
            mc.entityRenderer.itemRenderer.Method810();
         }
      }
   }

   @Override
   public boolean is_on_edge() {
      if (mc.thePlayer == null) {
         return false;
      }

      if (!mc.thePlayer.onGround) {
         return false;
      }

      BlockPos posBelow = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
      return mc.theWorld.isAirBlock(posBelow);
   }

   @Override
   public Vec2f get_input() {
      return mc.thePlayer == null ? null : new Vec2f(mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
   }

   @Override
   public float get_yaw() {
      return mc.thePlayer == null ? 0.0F : mc.thePlayer.rotationYaw;
   }

   @Override
   public float get_pitch() {
      return mc.thePlayer == null ? 0.0F : mc.thePlayer.rotationPitch;
   }

   @Override
   public float get_prev_yaw() {
      return mc.thePlayer == null ? 0.0F : mc.thePlayer.prevRotationYaw;
   }

   @Override
   public float get_prev_pitch() {
      return mc.thePlayer == null ? 0.0F : mc.thePlayer.prevRotationPitch;
   }

   @Override
   public double get_motion_x() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.motionX;
   }

   @Override
   public double get_motion_y() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.motionY;
   }

   @Override
   public double get_motion_z() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.motionZ;
   }

   @Override
   public Vec3d get_motion() {
      return mc.thePlayer == null ? null : new Vec3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
   }

   @Override
   public double get_position_x() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.posX;
   }

   @Override
   public double get_position_y() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.posY;
   }

   @Override
   public double get_position_z() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.posZ;
   }

   @Override
   public Vec3d get_position() {
      return mc.thePlayer == null ? null : new Vec3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
   }

   @Override
   public double get_prev_position_x() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.prevPosX;
   }

   @Override
   public double get_prev_position_y() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.prevPosY;
   }

   @Override
   public double get_prev_position_z() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.prevPosZ;
   }

   @Override
   public Vec3d get_prev_position() {
      return mc.thePlayer == null ? null : new Vec3d(mc.thePlayer.prevPosX, mc.thePlayer.prevPosY, mc.thePlayer.prevPosZ);
   }

   @Override
   public boolean is_moving() {
      return mc.thePlayer == null ? false : mc.thePlayer.ae();
   }

   @Override
   public boolean is_on_ground() {
      return mc.thePlayer == null ? false : mc.thePlayer.onGround;
   }

   @Override
   public boolean is_sprinting() {
      return mc.thePlayer == null ? false : mc.thePlayer.isSprinting();
   }

   @Override
   public boolean is_using_item() {
      return mc.thePlayer == null ? false : mc.thePlayer.isUsingItem();
   }

   @Override
   public boolean is_blocking() {
      return mc.thePlayer == null ? false : mc.thePlayer.isUsingItem();
   }

   @Override
   public boolean is_in_water() {
      return mc.thePlayer == null ? false : mc.thePlayer.isInWater();
   }

   @Override
   public boolean is_in_lava() {
      return mc.thePlayer == null ? false : mc.thePlayer.isInLava();
   }

   @Override
   public boolean is_burning() {
      return mc.thePlayer == null ? false : mc.thePlayer.isBurning();
   }

   @Override
   public boolean is_sneaking() {
      return mc.thePlayer == null ? false : mc.thePlayer.isSneaking();
   }

   @Override
   public boolean is_in_cobweb() {
      return mc.thePlayer == null ? false : mc.thePlayer.Method938();
   }

   @Override
   public boolean is_dead() {
      return mc.thePlayer == null ? false : mc.thePlayer.isDead;
   }

   @Override
   public boolean is_riding() {
      return mc.thePlayer == null ? false : mc.thePlayer.isRiding();
   }

   @Override
   public boolean is_on_ladder() {
      return mc.thePlayer == null ? false : mc.thePlayer.isOnLadder();
   }

   @Override
   public boolean collided_vertically() {
      return mc.thePlayer == null ? false : mc.thePlayer.isCollidedVertically;
   }

   @Override
   public boolean collided_horizontally() {
      return mc.thePlayer == null ? false : mc.thePlayer.isCollidedHorizontally;
   }

   @Override
   public int get_ticks_existed() {
      return mc.thePlayer == null ? 0 : mc.thePlayer.ticksExisted;
   }

   @Override
   public double get_speed() {
      return mc.thePlayer == null ? 0.0 : MovementUtil.getSpeed();
   }

   @Override
   public double get_base_move_speed() {
      return mc.thePlayer == null ? 0.0 : MovementUtil.getBaseSpeed();
   }

   @Override
   public double get_fall_distance() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.fallDistance;
   }

   @Override
   public double get_health() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.getHealth();
   }

   @Override
   public double get_max_health() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.getMaxHealth();
   }

   @Override
   public double get_absorption() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.getAbsorptionAmount();
   }

   @Override
   public double get_food_amount() {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.getFoodStats().getFoodLevel();
   }

   @Override
   public int get_hurt_time() {
      return mc.thePlayer == null ? 0 : mc.thePlayer.hurtTime;
   }

   @Override
   public int get_held_item_slot() {
      return mc.thePlayer == null ? 0 : mc.thePlayer.inventory.currentItem;
   }

   @Override
   public String get_name() {
      return mc.thePlayer == null ? null : mc.thePlayer.getName();
   }

   @Override
   public int get_id() {
      return mc.thePlayer == null ? 0 : mc.thePlayer.getEntityId();
   }

   @Override
   public Facing get_facing() {
      return mc.thePlayer == null ? Facing.UP : Facing.values()[mc.thePlayer.getHorizontalFacing().ordinal()];
   }

   @Override
   public RayCastResult get_mouse_over() {
      MovingObjectPosition mouseOver = mc.objectMouseOver;
      if (mouseOver == null) {
         return null;
      }

      BlockPos blockPos = mouseOver.getBlockPos();
      Vec3 hitVec = mouseOver.hitVec;
      if (blockPos == null) {
         blockPos = new BlockPos(0, 0, 0);
      }

      if (hitVec == null) {
         hitVec = new Vec3(0.0, 0.0, 0.0);
      }

      return new RayCastResult(
         HitType.values()[mouseOver.typeOfHit == null ? 0 : mouseOver.typeOfHit.ordinal()],
         Facing.values()[mouseOver.sideHit == null ? 0 : mouseOver.sideHit.ordinal()],
         new Vec3i(blockPos.getX(), blockPos.getY(), blockPos.getZ()),
         new Vec3d(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord),
         mouseOver.entityHit == null ? 0 : mouseOver.entityHit.getEntityId()
      );
   }

   @Override
   public Potion[] get_active_effects() {
      if (mc.thePlayer == null) {
         return new Potion[0];
      }

      Map<Integer, ?> activeEffects = mc.thePlayer.Method1032();
      Potion[] potions = new Potion[activeEffects.size()];
      int index = 0;

      for (Integer effectId : activeEffects.keySet()) {
         potions[index] = this.get_effect(effectId);
         index++;
      }

      return potions;
   }

   @Override
   public boolean has_effect(int effectId) {
      return mc.thePlayer == null ? false : mc.thePlayer.Method1032().get(effectId) != null;
   }

   @Override
   public Potion get_effect(int effectId) {
      if (mc.thePlayer == null) {
         return null;
      }

      PotionEffect effect = mc.thePlayer.Method1032().get(effectId);
      if (effect == null) {
         return null;
      }

      net.minecraft.potion.Potion potionType = net.minecraft.potion.Potion.potionTypes[effect.getPotionID()];
      Potion potion = new Potion();
      potion.id = effect.getPotionID();
      potion.amplifier = effect.getAmplifier();
      potion.duration = effect.getDuration();
      potion.color = potionType.getLiquidColor();
      potion.name = potionType.getName();
      potion.bad_effect = potionType.isBadEffect();
      return potion;
   }

   @Override
   public void set_speed(LuaTable moveEvent, double speed) {
      if (mc.thePlayer != null) {
         float yaw = MovementUtil.getMovementYaw();
         moveEvent.set("x", (double)(-Math.sin(yaw) * speed));
         moveEvent.set("z", (double)(Math.cos(yaw) * speed));
      }
   }

   @Override
   public void set_motion_speed(double speed) {
      if (mc.thePlayer != null) {
         MovementUtil.setSpeed(speed);
      }
   }

   @Override
   public boolean place_block(int slot, Vec3i blockPos, Vec3d hitVec, Facing facing) {
      return mc.thePlayer == null
         ? false
         : mc.playerController
            .onPlayerRightClick(
               mc.thePlayer,
               mc.theWorld,
               mc.thePlayer.inventory.getStackInSlot(slot),
               new BlockPos(blockPos.x, blockPos.y, hitVec.z),
               EnumFacing.values()[facing.ordinal()],
               new Vec3(hitVec.x, hitVec.y, hitVec.z)
            );
   }

   @Override
   public RayCastResult ray_cast(float yaw, float pitch, float reach) {
      if (mc.thePlayer == null) {
         return null;
      }

      MovingObjectPosition mouseOver = RaytraceUtil.raytraceWithEntities(yaw, pitch, reach, 1.0F);
      if (mouseOver == null) {
         return null;
      }

      BlockPos blockPos = mouseOver.getBlockPos();
      Vec3 hitVec = mouseOver.hitVec;
      if (blockPos == null) {
         blockPos = new BlockPos(0, 0, 0);
      }

      if (hitVec == null) {
         hitVec = new Vec3(0.0, 0.0, 0.0);
      }

      return new RayCastResult(
         HitType.values()[mouseOver.typeOfHit == null ? 0 : mouseOver.typeOfHit.ordinal()],
         Facing.values()[mouseOver.sideHit == null ? 0 : mouseOver.sideHit.ordinal()],
         new Vec3i(blockPos.getX(), blockPos.getY(), blockPos.getZ()),
         new Vec3d(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord),
         mouseOver.entityHit == null ? 0 : mouseOver.entityHit.getEntityId()
      );
   }

   @Override
   public void set_yaw(float yaw) {
      if (mc.thePlayer != null) {
         mc.thePlayer.rotationYaw = yaw;
      }
   }

   @Override
   public void set_pitch(float pitch) {
      if (mc.thePlayer != null) {
         mc.thePlayer.rotationPitch = pitch;
      }
   }

   @Override
   public void set_held_item(int slot) {
      if (mc.thePlayer != null) {
         mc.thePlayer.inventory.currentItem = slot;
         mc.playerController.syncCurrentPlayItem();
      }
   }

   @Override
   public void set_sprinting(boolean sprinting) {
      if (mc.thePlayer != null) {
         mc.thePlayer.setSprinting(sprinting);
      }
   }

   @Override
   public void set_motion_x(double motionX) {
      if (mc.thePlayer != null) {
         mc.thePlayer.motionX = motionX;
      }
   }

   @Override
   public void set_motion_y(double motionY) {
      if (mc.thePlayer != null) {
         mc.thePlayer.motionY = motionY;
      }
   }

   @Override
   public void set_motion_z(double motionZ) {
      if (mc.thePlayer != null) {
         mc.thePlayer.motionZ = motionZ;
      }
   }

   @Override
   public void set_motion(Vec3d motion) {
      if (mc.thePlayer != null) {
         mc.thePlayer.motionX = motion.x;
         mc.thePlayer.motionY = motion.y;
         mc.thePlayer.motionZ = motion.z;
      }
   }

   @Override
   public void set_position(Vec3d position) {
      if (mc.thePlayer != null) {
         mc.thePlayer.setPosition(position.x, position.y, position.z);
      }
   }

   @Override
   public void set_position_and_update(Vec3d position) {
      if (mc.thePlayer != null) {
         mc.thePlayer.setPositionAndUpdate(position.x, position.y, position.z);
      }
   }

   @Override
   public void set_sneaking(boolean sneaking) {
      if (mc.currentScreen == null) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sneaking);
      }
   }

   @Override
   public void send_packet(int packetId, Object... args) {
      switch (packetId) {
         case 0:
            if (args.length == 1) {
               int keepAliveId = NumberCastUtil.toInt(args[0]);
               mc.thePlayer.sendQueue.Method754(new C00PacketKeepAlive(keepAliveId));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
         case 1:
         case 12:
         case 14:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         default:
            break;
         case 2:
            if (args.length == 2) {
               int entityId = NumberCastUtil.toInt(args[0]);
               Object actionOrVec = args[1];
               if (actionOrVec instanceof InteractActions interactAction) {
                  C02PacketUseEntity.Action action = C02PacketUseEntity.Action.values()[interactAction.ordinal()];
                  mc.thePlayer.sendQueue.Method754(new C02PacketUseEntity(entityId, action));
               } else if (actionOrVec instanceof Vec3d hitVec) {
                  mc.thePlayer.sendQueue.Method754(new C02PacketUseEntity(entityId, new Vec3(hitVec.x, hitVec.y, hitVec.z)));
               }
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 2);
            }
            break;
         case 3:
            if (args.length == 1) {
               boolean onGround = (Boolean)args[0];
               mc.thePlayer.sendQueue.Method754(new C03PacketPlayer(onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
            break;
         case 4:
            if (args.length == 4) {
               double posX = NumberCastUtil.toDouble(args[0]);
               double posY = NumberCastUtil.toDouble(args[1]);
               double posZ = NumberCastUtil.toDouble(args[2]);
               boolean onGround = (Boolean)args[3];
               mc.thePlayer.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 4);
            }
            break;
         case 5:
            if (args.length == 3) {
               float yaw = NumberCastUtil.toFloat(args[0]);
               float pitch = NumberCastUtil.toFloat(args[1]);
               boolean onGround = (Boolean)args[2];
               mc.thePlayer.sendQueue.Method754(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 3);
            }
            break;
         case 6:
            if (args.length == 6) {
               double posX = NumberCastUtil.toDouble(args[0]);
               double posY = NumberCastUtil.toDouble(args[1]);
               double posZ = NumberCastUtil.toDouble(args[2]);
               float yaw = NumberCastUtil.toFloat(args[3]);
               float pitch = NumberCastUtil.toFloat(args[4]);
               boolean onGround = (Boolean)args[5];
               mc.thePlayer.sendQueue.Method754(new C03PacketPlayer.C06PacketPlayerPosLook(posX, posY, posZ, yaw, pitch, onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 6);
            }
            break;
         case 7:
            if (args.length == 3) {
               DiggingActions diggingAction = (DiggingActions)args[0];
               Vec3i blockPos = (Vec3i)args[1];
               Facing facing = (Facing)args[2];
               mc.thePlayer
                  .sendQueue
                  .Method754(
                     new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.values()[diggingAction.ordinal()], new BlockPos(blockPos.x, blockPos.y, blockPos.z), EnumFacing.values()[facing.ordinal()]
                     )
                  );
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 3);
            }
            break;
         case 8:
            if (args.length == 6) {
               Vec3i blockPos = (Vec3i)args[0];
               int placedBlockDirection = NumberCastUtil.toInt(args[1]);
               int slot = NumberCastUtil.toInt(args[2]);
               float facingX = NumberCastUtil.toFloat(args[3]);
               float facingY = NumberCastUtil.toFloat(args[4]);
               float facingZ = NumberCastUtil.toFloat(args[5]);
               mc.thePlayer
                  .sendQueue
                  .Method754(
                     new C08PacketPlayerBlockPlacement(
                        new BlockPos(blockPos.x, blockPos.y, blockPos.z), placedBlockDirection, slot == -1 ? null : mc.thePlayer.inventory.getStackInSlot(slot), facingX, facingY, facingZ
                     )
                  );
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 6);
            }
            break;
         case 9:
            if (args.length == 0) {
               mc.thePlayer.sendQueue.Method755(new C09PacketHeldItemChange());
            } else if (args.length == 1) {
               int slot = NumberCastUtil.toInt(args[0]);
               mc.thePlayer.sendQueue.Method755(new C09PacketHeldItemChange(slot));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
            break;
         case 10:
            if (args.length == 0) {
               mc.thePlayer.sendQueue.Method754(new C0APacketAnimation());
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 0);
            }
            break;
         case 11:
            if (args.length == 2) {
               int entityId = NumberCastUtil.toInt(args[0]);
               EntityAction entityAction = (EntityAction)args[1];
               mc.thePlayer
                  .sendQueue
                  .Method754(new C0BPacketEntityAction(mc.theWorld.getEntityByID(entityId), C0BPacketEntityAction.Action.values()[entityAction.ordinal()]));
            } else if (args.length == 3) {
               int entityId = NumberCastUtil.toInt(args[0]);
               EntityAction entityAction = (EntityAction)args[1];
               int auxData = NumberCastUtil.toInt(args[2]);
               mc.thePlayer
                  .sendQueue
                  .Method754(new C0BPacketEntityAction(mc.theWorld.getEntityByID(entityId), C0BPacketEntityAction.Action.values()[entityAction.ordinal()], auxData));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 2);
            }
            break;
         case 13:
            if (args.length == 1) {
               int windowId = NumberCastUtil.toInt(args[0]);
               mc.thePlayer.sendQueue.Method754(new C0DPacketCloseWindow(windowId));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
            break;
         case 15:
            if (args.length == 3) {
               int windowId = NumberCastUtil.toInt(args[0]);
               short uid = NumberCastUtil.toShort(args[1]);
               boolean accepted = (Boolean)args[2];
               mc.thePlayer.sendQueue.Method754(new C0FPacketConfirmTransaction(windowId, uid, accepted));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 3);
            }
            break;
         case 22:
            if (args.length == 1) {
               ClientStatusActions clientStatusAction = (ClientStatusActions)args[0];
               mc.thePlayer.sendQueue.Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.values()[clientStatusAction.ordinal()]));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
      }
   }

   @Override
   public void send_packet_no_event(int packetId, Object... args) {
      switch (packetId) {
         case 0:
            if (args.length == 1) {
               int keepAliveId = NumberCastUtil.toInt(args[0]);
               mc.thePlayer.sendQueue.Method755(new C00PacketKeepAlive(keepAliveId));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
         case 1:
         case 12:
         case 14:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         default:
            break;
         case 2:
            if (args.length == 2) {
               int entityId = NumberCastUtil.toInt(args[0]);
               Object actionOrVec = args[1];
               if (actionOrVec instanceof InteractActions interactAction) {
                  C02PacketUseEntity.Action action = C02PacketUseEntity.Action.values()[interactAction.ordinal()];
                  mc.thePlayer.sendQueue.Method755(new C02PacketUseEntity(entityId, action));
               } else if (actionOrVec instanceof Vec3d hitVec) {
                  mc.thePlayer.sendQueue.Method755(new C02PacketUseEntity(entityId, new Vec3(hitVec.x, hitVec.y, hitVec.z)));
               }
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 2);
            }
            break;
         case 3:
            if (args.length == 1) {
               boolean onGround = (Boolean)args[0];
               mc.thePlayer.sendQueue.Method755(new C03PacketPlayer(onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
            break;
         case 4:
            if (args.length == 4) {
               double posX = NumberCastUtil.toDouble(args[0]);
               double posY = NumberCastUtil.toDouble(args[1]);
               double posZ = NumberCastUtil.toDouble(args[2]);
               boolean onGround = (Boolean)args[3];
               mc.thePlayer.sendQueue.Method755(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 4);
            }
            break;
         case 5:
            if (args.length == 3) {
               float yaw = NumberCastUtil.toFloat(args[0]);
               float pitch = NumberCastUtil.toFloat(args[1]);
               boolean onGround = (Boolean)args[2];
               mc.thePlayer.sendQueue.Method755(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 3);
            }
            break;
         case 6:
            if (args.length == 6) {
               double posX = NumberCastUtil.toDouble(args[0]);
               double posY = NumberCastUtil.toDouble(args[1]);
               double posZ = NumberCastUtil.toDouble(args[2]);
               float yaw = NumberCastUtil.toFloat(args[3]);
               float pitch = NumberCastUtil.toFloat(args[4]);
               boolean onGround = (Boolean)args[5];
               mc.thePlayer.sendQueue.Method755(new C03PacketPlayer.C06PacketPlayerPosLook(posX, posY, posZ, yaw, pitch, onGround));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 6);
            }
            break;
         case 7:
            if (args.length == 3) {
               DiggingActions diggingAction = (DiggingActions)args[0];
               Vec3i blockPos = (Vec3i)args[1];
               Facing facing = (Facing)args[2];
               mc.thePlayer
                  .sendQueue
                  .Method755(
                     new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.values()[diggingAction.ordinal()], new BlockPos(blockPos.x, blockPos.y, blockPos.z), EnumFacing.values()[facing.ordinal()]
                     )
                  );
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 3);
            }
            break;
         case 8:
            if (args.length == 6) {
               Vec3i blockPos = (Vec3i)args[0];
               int placedBlockDirection = NumberCastUtil.toInt(args[1]);
               int slot = NumberCastUtil.toInt(args[2]);
               float facingX = NumberCastUtil.toFloat(args[3]);
               float facingY = NumberCastUtil.toFloat(args[4]);
               float facingZ = NumberCastUtil.toFloat(args[5]);
               mc.thePlayer
                  .sendQueue
                  .Method755(
                     new C08PacketPlayerBlockPlacement(
                        new BlockPos(blockPos.x, blockPos.y, blockPos.z), placedBlockDirection, slot == -1 ? null : mc.thePlayer.inventory.getStackInSlot(slot), facingX, facingY, facingZ
                     )
                  );
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 6);
            }
            break;
         case 9:
            if (args.length == 0) {
               mc.thePlayer.sendQueue.Method755(new C09PacketHeldItemChange());
            } else if (args.length == 1) {
               int slot = NumberCastUtil.toInt(args[0]);
               mc.thePlayer.sendQueue.Method755(new C09PacketHeldItemChange(slot));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
            break;
         case 10:
            if (args.length == 0) {
               mc.thePlayer.sendQueue.Method755(new C0APacketAnimation());
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 0);
            }
            break;
         case 11:
            if (args.length == 2) {
               int entityId = NumberCastUtil.toInt(args[0]);
               EntityAction entityAction = (EntityAction)args[1];
               mc.thePlayer
                  .sendQueue
                  .Method755(new C0BPacketEntityAction(mc.theWorld.getEntityByID(entityId), C0BPacketEntityAction.Action.values()[entityAction.ordinal()]));
            } else if (args.length == 3) {
               int entityId = NumberCastUtil.toInt(args[0]);
               EntityAction entityAction = (EntityAction)args[1];
               int auxData = NumberCastUtil.toInt(args[2]);
               mc.thePlayer
                  .sendQueue
                  .Method755(new C0BPacketEntityAction(mc.theWorld.getEntityByID(entityId), C0BPacketEntityAction.Action.values()[entityAction.ordinal()], auxData));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 2);
            }
            break;
         case 13:
            if (args.length == 1) {
               int windowId = NumberCastUtil.toInt(args[0]);
               mc.thePlayer.sendQueue.Method755(new C0DPacketCloseWindow(windowId));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
            break;
         case 15:
            if (args.length == 3) {
               int windowId = NumberCastUtil.toInt(args[0]);
               short uid = NumberCastUtil.toShort(args[1]);
               boolean accepted = (Boolean)args[2];
               mc.thePlayer.sendQueue.Method755(new C0FPacketConfirmTransaction(windowId, uid, accepted));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 3);
            }
            break;
         case 22:
            if (args.length == 1) {
               ClientStatusActions clientStatusAction = (ClientStatusActions)args[0];
               mc.thePlayer.sendQueue.Method755(new C16PacketClientStatus(C16PacketClientStatus.EnumState.values()[clientStatusAction.ordinal()]));
            } else {
               this.reportInvalidPacketArgumentCount(args.length, 1);
            }
      }
   }

   private void reportInvalidPacketArgumentCount(int actualCount, int expectedCount) {
      ChatUtil.sendMessage("<dark_red>Invalid packet parameter size, got " + actualCount + " expected " + expectedCount + "!");
   }

   @Override
   public double distance_to(float x, float y, float z) {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.getDistanceSq(x, y, z);
   }

   @Override
   public double distance_to_vec(Vec3d vec) {
      return mc.thePlayer == null ? 0.0 : mc.thePlayer.getDistanceSq(vec.x, vec.y, vec.z);
   }

   @Override
   public double distance_to_entity(int entityId) {
      Entity entity = mc.theWorld.getEntityByID(entityId);
      if (entity == null) {
         return 0.0;
      } else {
         return mc.thePlayer == null ? 0.0 : mc.thePlayer.getDistanceSqToEntity(entity);
      }
   }

   @Override
   public Vec2f rotations_to(Vec3d target) {
      if (mc.thePlayer == null) {
         return null;
      }

      Vector3f eyePos = new Vector3f((float)mc.thePlayer.posX, (float)mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), (float)mc.thePlayer.posZ);
      System.out.println(target.x);
      Vector3f delta = new Vector3f((float)target.x, (float)target.y, (float)target.z).sub(eyePos);
      float horizontalDistance = MathHelper.sqrt_float(delta.Method519() * delta.Method519() + delta.Method521() * delta.Method521());
      float yaw = MathHelper.Method565(delta.Method521(), delta.Method519()) * MathUtil.TO_DEGREES - 90.0F;
      float pitch = -MathHelper.Method565(delta.Method520(), horizontalDistance) * MathUtil.TO_DEGREES;
      return new Vec2f(yaw, MathHelper.clamp_float(pitch, -90.0F, 90.0F));
   }
}
