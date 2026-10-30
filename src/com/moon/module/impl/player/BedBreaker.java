package com.moon.module.impl.player;

import com.moon.Moon;
import com.moon.events.impl.player.DamageBlockEvent;
import com.moon.events.impl.player.KillAuraEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.world.WorldBoxCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.util.client.ChatUtil;
import com.moon.util.math.Vector3f;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import org.lwjgl.util.vector.Vector2f;

public class BedBreaker extends Module {
   private final IntegerSetting radius = IntegerSetting.builder("radius").defaults("Search Radius", 4, 1, 6, 1).build();
   private final BooleanSetting onlyRotateOnPacket = BooleanSetting.builder("only-rotate-on-packet").defaults("Only rotate on action", true).build();
   private final BooleanSetting showSwing = BooleanSetting.builder("show-swing").defaults("Show swing", false).build();
   private final BooleanSetting packetBreak = BooleanSetting.builder("packet-break").defaults("Packet break", true).build();
   private final BooleanSetting breakBlockAbove = BooleanSetting.builder("break-block-above").defaults("Break surrounding (Hypixel)", true).build();
   private final BooleanSetting maintainGroundSpeed = BooleanSetting.builder("maintain-ground-speed").defaults("Maintain ground speed", true).build();
   private final BooleanSetting markBreakingBlock = BooleanSetting.builder("mark-breaking-block").defaults("Mark breaking block", true).build();
   private final BooleanSetting colorByProgress = BooleanSetting.builder("color-by-progress")
      .defaults("Color by progress", true)
      .visibleWhen(this.markBreakingBlock::getValue)
      .build();
   private final ColorSetting progressColor = ColorSetting.builder("progress-color")
      .defaults("Progress color", -1)
      .visibleWhen(() -> !this.colorByProgress.getValue() && this.markBreakingBlock.getValue())
      .build();
   private BlockPos bedPos;
   private BlockPos surroundingPos;
   private boolean hasSurrounding;
   private boolean mining;
   private double breakProgress;
   private BlockPos targetPos;
   private boolean rotateNextUpdate;
   private BlockPos rotatePos;
   private int tickCounter = 0;
   private boolean reMining;
   private boolean packetMining;
   private int packetMineTicks;
   private final AnimatedValue overlayAnimation = new AnimatedValue();
   private final AnimatedValue progressAnimation = new AnimatedValue();

   public BedBreaker(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onEnable() {
      if (!Moon.getInstance().getModuleManager().<Module>getModule(AutoTool.class).isEnabled()) {
         ChatUtil.sendMessage("It is not recommended to use Bed Breaker without AutoTool enabled! Use at your own risk.");
      }

      this.bedPos = null;
      this.surroundingPos = null;
      this.hasSurrounding = false;
      this.targetPos = null;
      this.mining = false;
      this.packetMining = false;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      if (mc.thePlayer != null && mc.theWorld != null) {
         if (this.mining && this.targetPos != null) {
            this.sendDiggingStop(this.targetPos, true);
         }

         if (this.packetMining && this.packetMineTicks <= 2) {
            Moon.getInstance().getModuleManager().<AutoTool>getModule(AutoTool.class).reset();
         }

         this.breakProgress = 0.0;
         this.bedPos = null;
         this.surroundingPos = null;
         this.hasSurrounding = false;
         this.targetPos = null;
         this.mining = false;
         this.packetMining = false;
      }
   }

   @Subscribe
   public void onPauseCheck(KillAuraEvent event) {
      if (this.packetMining) {
         if (this.bedPos != null && this.packetMineTicks <= 1) {
            event.setCancelled(true);
         }
      } else {
         if (this.bedPos != null) {
            event.setCancelled(true);
         }
      }
   }

   @Subscribe
   public void onDamageBlock(DamageBlockEvent event) {
      if (this.packetMining && this.targetPos != null) {
         int bestSlot = this.findBestToolSlot(this.targetPos);
         event.setSlot(bestSlot);
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.BEFORE) {
         this.findNearestBed();
         if (this.bedPos != null) {
            if (this.packetBreak.getValue()) {
               if (!this.packetMining) {
                  BlockPos miningPos = this.hasSurrounding ? this.surroundingPos : this.bedPos;
                  Block block = mc.theWorld.getBlockState(miningPos).getBlock();
                  if (block instanceof BlockAir) {
                     if (this.hasSurrounding) {
                        this.surroundingPos = null;
                        this.hasSurrounding = false;
                     } else {
                        this.bedPos = null;
                     }

                     return;
                  }

                  mc.thePlayer.sendQueue.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, miningPos, EnumFacing.UP));
                  if (this.showSwing.getValue()) {
                     mc.thePlayer.swingItem();
                  } else {
                     mc.thePlayer.sendQueue.Method754(new C0APacketAnimation());
                  }

                  mc.thePlayer.sendQueue.Method755(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, miningPos, EnumFacing.UP));
                  this.rotateNextUpdate = true;
                  this.targetPos = miningPos;
                  this.rotatePos = miningPos;
                  this.tickCounter = 10;
                  this.packetMining = true;
                  this.packetMineTicks = 0;
               } else if (this.targetPos != null) {
                  Block block = mc.theWorld.getBlockState(this.targetPos).getBlock();
                  this.breakProgress = this.breakProgress
                     + block.Method435(mc.thePlayer, mc.thePlayer.worldObj, this.targetPos, this.maintainGroundSpeed.getValue());
                  if (this.breakProgress >= 1.0) {
                     if (block instanceof BlockAir) {
                        this.breakProgress = 0.0;
                        this.targetPos = null;
                        this.packetMining = false;
                        if (this.hasSurrounding) {
                           this.surroundingPos = null;
                           this.hasSurrounding = false;
                        } else {
                           this.bedPos = null;
                        }

                        AutoTool autoTool = Moon.getInstance().getModuleManager().getModule(AutoTool.class);
                        autoTool.reset();
                     } else if (++this.tickCounter >= 10) {
                        mc.thePlayer
                           .sendQueue
                           .Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetPos, EnumFacing.UP));
                        if (this.showSwing.getValue()) {
                           mc.thePlayer.swingItem();
                        } else {
                           mc.thePlayer.sendQueue.Method754(new C0APacketAnimation());
                        }

                        mc.thePlayer
                           .sendQueue
                           .Method755(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.targetPos, EnumFacing.UP));
                        this.rotateNextUpdate = true;
                        this.tickCounter = 0;
                        this.packetMineTicks = 0;
                     }
                  }

                  if (this.packetMineTicks == 1) {
                     Moon.getInstance().getModuleManager().<AutoTool>getModule(AutoTool.class).reset();
                  }

                  this.packetMineTicks++;
               }
            } else if (!this.mining) {
               BlockPos miningPos = this.hasSurrounding ? this.surroundingPos : this.bedPos;
               Block block = mc.theWorld.getBlockState(miningPos).getBlock();
               if (block instanceof BlockAir) {
                  if (this.hasSurrounding) {
                     this.surroundingPos = null;
                     this.hasSurrounding = false;
                  } else {
                     this.bedPos = null;
                  }

                  return;
               }

               this.startMining(miningPos);
               this.tickCounter = 15;
               this.reMining = false;
            } else if (this.targetPos != null) {
               Block block = mc.theWorld.getBlockState(this.targetPos).getBlock();
               this.breakProgress = this.breakProgress
                  + block.Method435(mc.thePlayer, mc.thePlayer.worldObj, this.targetPos, this.maintainGroundSpeed.getValue());
               if (this.breakProgress >= 1.0) {
                  if (block instanceof BlockAir) {
                     this.breakProgress = 0.0;
                     this.targetPos = null;
                     this.mining = false;
                     if (this.hasSurrounding) {
                        this.surroundingPos = null;
                        this.hasSurrounding = false;
                     } else {
                        this.bedPos = null;
                     }

                     AutoTool autoTool = Moon.getInstance().getModuleManager().getModule(AutoTool.class);
                     autoTool.reset();
                     this.reMining = false;
                     this.tickCounter = 15;
                  } else {
                     if (++this.tickCounter >= 15 && this.reMining) {
                        mc.thePlayer
                           .sendQueue
                           .Method755(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetPos, EnumFacing.UP));
                        this.breakProgress = 0.0;
                        this.tickCounter = 0;
                        this.reMining = false;
                        return;
                     }

                     if (!this.reMining) {
                        this.sendDiggingStop(this.targetPos, false);
                        this.reMining = true;
                     }
                  }
               }
            }
         }
      } else if (event.getSequence() == UpdateEvent.Sequence.PRE
         && (this.mining && !this.onlyRotateOnPacket.getValue() || this.rotateNextUpdate)
         && this.rotatePos != null) {
         Vector3f eyePos = new Vector3f((float) mc.thePlayer.posX, (float) mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), (float) mc.thePlayer.posZ);
         Vector3f targetVec = new Vector3f(this.rotatePos.getX() + 0.5F, this.rotatePos.getY() + 0.5F, this.rotatePos.getZ() + 0.5F);
         Vector2f rotations = this.computeRotation(eyePos, targetVec);
         event.setYaw(rotations.x);
         event.setPitch(MathHelper.clamp_float(rotations.y, -90.0F, 90.0F));
         this.rotateNextUpdate = false;
      }
   }

   @Subscribe
   public void onEffectRender(EffectRenderEvent event) {
      float alpha = this.overlayAnimation.get();
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      float x = scaledResolution.Method237() / 2.0F - 75.0F;
      float y = scaledResolution.Method238() - 60 - 10.0F * alpha;
      RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      task.addCommand(
         RoundedRectangleCommand.of(x, y, 150.0F, 10.0F, 4.0F, new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(1.0F * alpha).getRGB())
      );
      task.addCommand(
         RoundedRectangleCommand.of(
            x, y, Math.max(150.0F * this.progressAnimation.get(), 12.0F), 10.0F, 4.0F, new RenderColor(-7196417).withAlphaFraction(alpha).getRGB()
         )
      );
      task.render();
   }

   @Subscribe
   public void onOverlayRender(OverlayRenderEvent event) {
      if (event.getSequence() == OverlayRenderEvent.Sequence.POST) {
         if (this.markBreakingBlock.getValue() && this.targetPos != null) {
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            int progressRgb = Color.HSBtoRGB((float)(Math.max(0.0, this.breakProgress) / 3.0), 1.0F, 1.0F) | 0xFF000000;
            double renderPosX = mc.getRenderManager().Method588();
            double renderPosY = mc.getRenderManager().Method589();
            double renderPosZ = mc.getRenderManager().Method590();
            double x = this.targetPos.getX() - renderPosX;
            double y = this.targetPos.getY() - renderPosY;
            double z = this.targetPos.getZ() - renderPosZ;
            AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
            RenderTask fillTask = RenderTask.builder().withRenderType(RenderType.WORLD).withActiveRenderMatrices().build();
            RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.WORLD).withDrawMode(1).withActiveRenderMatrices().build();
            int boxColor = new RenderColor(this.colorByProgress.getValue() ? progressRgb : this.progressColor.getValue()).withAlphaFraction(0.25F).getRGB();
            fillTask.addCommand(WorldBoxCommand.of(bb, boxColor));
            outlineTask.addCommand(WorldBoxCommand.of(bb, boxColor));
            fillTask.render();
            outlineTask.render();
         }

         float alpha = this.overlayAnimation.get();
         if (alpha > 0.01) {
            GlStateManager.enableBlend();
            FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
            ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
            float x = scaledResolution.Method237() / 2.0F - 75.0F;
            float y = scaledResolution.Method238() - 60 - 10.0F * alpha;
            float progress = MathHelper.clamp_float((float)this.breakProgress, 0.0F, 1.0F);
            String progressText = String.format("%.2f%%", progress * 100.0F);
            float textWidth = fontRenderer.getTextWidth(progressText, 7.25F);
            if (this.bedPos != null) {
               this.progressAnimation.animateTo(progress, 0.4F);
            }

            RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
            RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
            rectTask.addCommand(
               RoundedRectangleCommand.of(x, y, 150.0F, 10.0F, 4.0F, new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(0.4F * alpha).getRGB())
            );
            rectTask.addCommand(
               RoundedRectangleCommand.of(
                  x, y, Math.max(150.0F * this.progressAnimation.get(), 12.0F), 10.0F, 4.0F, new RenderColor(-7196417).withAlphaFraction(alpha).getRGB()
               )
            );
            fontRenderer.draw(
               textTask,
               progressText,
               7.25F,
               x + 75.0F - textWidth / 2.0F,
               y + 5.0F - fontRenderer.getLineHeight(7.25F) / 2.0F + 0.5F,
               new RenderColor(Color.WHITE.getRGB()).withAlphaFraction(alpha).getRGB()
            );
            rectTask.render();
            textTask.render();
         }

         this.overlayAnimation.animateTo(this.bedPos != null ? 1.0F : 0.0F, 0.3F);
         GlStateManager.enableDepth();
      }
   }

   private void startMining(BlockPos pos) {
      mc.thePlayer.sendQueue.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
      if (this.showSwing.getValue()) {
         mc.thePlayer.swingItem();
      } else {
         mc.thePlayer.sendQueue.Method754(new C0APacketAnimation());
      }

      Block block = mc.theWorld.getBlockState(pos).getBlock();
      boolean notAir = block.getMaterial() != Material.air;
      if (notAir && block.Method434(mc.thePlayer, mc.thePlayer.worldObj, pos) >= 1.0F) {
         mc.thePlayer.sendQueue.Method754(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
      } else {
         this.targetPos = pos;
         this.rotatePos = pos;
         this.mining = true;
      }

      this.breakProgress = 0.0;
      this.rotateNextUpdate = true;
   }

   private void sendDiggingStop(BlockPos pos, boolean abort) {
      C07PacketPlayerDigging diggingPacket = new C07PacketPlayerDigging(
         abort ? C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK : C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP
      );
      if (abort) {
         mc.thePlayer.sendQueue.Method754(diggingPacket);
      } else {
         mc.thePlayer.sendQueue.Method755(diggingPacket);
         this.rotateNextUpdate = true;
      }
   }

   private void findNearestBed() {
      if (this.bedPos != null) {
         double distance = mc.thePlayer.getDistance(this.bedPos.getX(), this.bedPos.getY(), this.bedPos.getZ());
         if (distance > this.radius.getValue().intValue()) {
            if (this.mining && this.targetPos != null) {
               this.sendDiggingStop(this.targetPos, true);
            }

            this.breakProgress = 0.0;
            this.bedPos = null;
            this.surroundingPos = null;
            this.hasSurrounding = false;
            this.targetPos = null;
            this.mining = false;
            this.packetMining = false;
         }
      } else {
         int searchRadius = this.radius.getValue();
         double playerX = mc.thePlayer.posX;
         double playerY = mc.thePlayer.posY;
         double playerZ = mc.thePlayer.posZ;

         for (int x = -searchRadius; x < searchRadius; x++) {
            for (int y = -searchRadius; y < searchRadius; y++) {
               for (int z = -searchRadius; z < searchRadius; z++) {
                  int blockX = (int)playerX + x;
                  int blockY = (int)playerY + y;
                  int blockZ = (int)playerZ + z;
                  BlockPos checkPos = new BlockPos(blockX, blockY, blockZ);
                  Block block = mc.theWorld.getBlockState(checkPos).getBlock();
                  double dist = mc.thePlayer.getDistance(checkPos.getX(), checkPos.getY(), checkPos.getZ());
                  if (dist < searchRadius && block instanceof BlockBed) {
                     if (this.breakBlockAbove.getValue() && !this.hasBedSurrounding(checkPos, checkPos)) {
                        this.hasSurrounding = true;
                        this.surroundingPos = this.findBestSurrounding(checkPos, checkPos).getFirst();
                     }

                     this.bedPos = checkPos;
                     return;
                  }
               }
            }
         }
      }
   }

   private boolean hasBedSurrounding(BlockPos pos, BlockPos origin) {
      for (BlockPos offset : new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)}) {
         BlockPos neighborPos = pos.add(offset);
         if (!neighborPos.equals(origin)) {
            Block block = mc.theWorld.getBlockState(neighborPos).getBlock();
            if (block instanceof BlockBed) {
               boolean surrounded = this.hasBedSurrounding(neighborPos, origin);
               if (surrounded) {
                  return true;
               }
            } else if (block instanceof BlockAir) {
               return true;
            }
         }
      }

      return false;
   }

   private Tuple<BlockPos, Double> findBestSurrounding(BlockPos pos, BlockPos origin) {
      BlockPos[] offsets = new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)};
      BlockPos bestPos = null;
      double bestSpeed = 0.0;

      for (BlockPos offset : offsets) {
         BlockPos neighborPos = pos.add(offset);
         if (!neighborPos.equals(origin)) {
            Block block = mc.theWorld.getBlockState(neighborPos).getBlock();
            if (block instanceof BlockBed) {
               Tuple surrounding = this.findBestSurrounding(neighborPos, origin);
               Double speed = (Double)surrounding.getSecond();
               if (speed > bestSpeed) {
                  bestPos = (BlockPos)surrounding.getFirst();
                  bestSpeed = speed;
               }
            } else {
               int toolSlot = this.findBestToolSlot(neighborPos);
               float breakSpeed = block.Method436(mc.thePlayer, mc.theWorld, neighborPos, this.maintainGroundSpeed.getValue(), toolSlot);
               if (breakSpeed > bestSpeed) {
                  bestPos = neighborPos;
                  bestSpeed = breakSpeed;
               }
            }
         }
      }

      return new Tuple<>(bestPos, bestSpeed);
   }

   private int findBestToolSlot(BlockPos pos) {
      int bestSlot = mc.thePlayer.inventory.currentItem;
      Block block = mc.theWorld.getBlockState(pos).getBlock();
      if (block == null) {
         return bestSlot;
      }

      float bestSpeed = 1.0F;

      for (int i = 36; i < 45; i++) {
         ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (stack != null) {
            float speed = stack.getItem().getStrVsBlock(stack, block);
            if (speed > bestSpeed) {
               bestSlot = i - 36;
               bestSpeed = speed;
            }
         }
      }

      return bestSlot;
   }

   private Vector2f computeRotation(Vector3f from, Vector3f to) {
      Vector3f diff = to.sub(from);
      float dist = MathHelper.sqrt_float(diff.Method519() * diff.Method519() + diff.Method521() * diff.Method521());
      float yaw = MathHelper.Method565(diff.Method521(), diff.Method519()) * AntiFireball.RADIANS_TO_DEGREES - 90.0F;
      float pitch = -MathHelper.Method565(diff.Method520(), dist) * AntiFireball.RADIANS_TO_DEGREES;
      return new Vector2f(yaw, pitch);
   }

   public double getBreakProgress() {
      return this.breakProgress;
   }

   public BedBreaker setBreakProgress(double breakProgress) {
      this.breakProgress = breakProgress;
      return this;
   }

   public BlockPos getTargetPos() {
      return this.targetPos;
   }

   public BedBreaker setTargetPos(BlockPos targetPos) {
      this.targetPos = targetPos;
      return this;
   }
}
