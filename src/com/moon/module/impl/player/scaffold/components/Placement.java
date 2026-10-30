package com.moon.module.impl.player.scaffold.components;

import com.moon.module.impl.player.ScaffoldWalk;
import com.moon.util.math.MathUtil;
import com.moon.util.math.Vector2f;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Placement {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final ScaffoldWalk scaffold;

   public void place(BlockPos blockPos, EnumFacing facing) {
      ItemStack heldItem = mc.thePlayer.inventory.getStackInSlot(mc.playerController.Method587());
      Vec3 hitVec = this.getHitVec(blockPos, this.scaffold.getRotationExecutor().getRotations().getRotation());
      if (this.rightClickBlock(heldItem, blockPos, facing, hitVec)) {
         this.swing();
      }
   }

   public void placeAtCrosshair() {
      if (!mc.playerController.getIsHittingBlock()) {
         mc.rightClickDelayTimer = 4;
         if (mc.objectMouseOver != null) {
            switch (mc.objectMouseOver.typeOfHit) {
               case ENTITY:
                  if (!mc.playerController.isPlayerRightClickingOnEntity(mc.thePlayer, mc.objectMouseOver.entityHit, mc.objectMouseOver)
                     && mc.playerController.interactWithEntitySendPacket(mc.thePlayer, mc.objectMouseOver.entityHit)) {
                  }
                  break;
               case BLOCK:
                  ItemStack heldItem = mc.thePlayer.inventory.getCurrentItem();
                  int currentSlot = mc.thePlayer.inventory.currentItem;
                  BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                  IBlockState blockState = mc.theWorld.getBlockState(blockPos);
                  if (blockState.getBlock().getMaterial() != Material.air && blockPos.equals(this.scaffold.getBlockUtil().getBlockPos())) {
                     int initialStackSize = heldItem != null ? heldItem.stackSize : 0;
                     if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, heldItem, blockPos, mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec)) {
                        this.swing();
                     }

                     if (heldItem == null) {
                        return;
                     }

                     if (heldItem.stackSize == 0) {
                        mc.thePlayer.inventory.mainInventory[currentSlot] = null;
                     } else if ((heldItem.stackSize != initialStackSize || mc.playerController.isInCreativeMode()) && this.scaffold.getSwingAfterPlace().getValue()) {
                        mc.entityRenderer.itemRenderer.Method809();
                     }
                  }
            }
         }

         if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.MISS) {
         }
      }
   }

   private Vec3 getHitVec(BlockPos blockPos, Vector2f rotation) {
      MovingObjectPosition rayTraceResult = this.rayTrace(rotation);
      Vec3 hitVec;
      if (rayTraceResult == null) {
         hitVec = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
      } else {
         hitVec = rayTraceResult.hitVec;
      }

      return hitVec;
   }

   private MovingObjectPosition rayTrace(Vector2f rotation) {
      Vec3 eyesPos = mc.thePlayer.getPositionEyes(1.0F);
      Vec3 lookVec = MathUtil.getLookVector(rotation.getX(), rotation.getY());
      Vec3 reachVec = eyesPos.add(lookVec.Method759(mc.playerController.getBlockReachDistance()));
      return mc.theWorld.rayTraceBlocks(eyesPos, reachVec, false);
   }

   private boolean rightClickBlock(ItemStack heldItem, BlockPos blockPos, EnumFacing facing, Vec3 hitVec) {
      return mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, heldItem, blockPos, facing, hitVec);
   }

   private void swing() {
      this.scaffold.setPlaced(true);
      if (this.scaffold.getSwingAfterPlace().getValue()) {
         mc.thePlayer.swingItem();
      } else {
         mc.thePlayer.sendQueue.Method755(new C0APacketAnimation());
      }
   }

   public Placement(ScaffoldWalk scaffold) {
      this.scaffold = scaffold;
   }
}
