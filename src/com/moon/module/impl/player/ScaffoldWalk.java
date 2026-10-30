package com.moon.module.impl.player;

import com.moon.events.impl.input.InputEvent;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.HeldItemEvent;
import com.moon.events.impl.player.KillAuraEvent;
import com.moon.events.impl.player.LookEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.MoveInputEvent;
import com.moon.events.impl.player.SafeWalkEvent;
import com.moon.events.impl.player.SlotChangeEvent;
import com.moon.events.impl.player.StrafeEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.RenderHeldItemEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.player.scaffold.SprintScaffold;
import com.moon.module.impl.player.scaffold.Tower;
import com.moon.module.impl.player.scaffold.components.Placement;
import com.moon.module.impl.player.scaffold.components.Rotation;
import com.moon.module.impl.player.scaffold.components.Sneak;
import com.moon.module.impl.player.scaffold.sprint.HypixelSprint;
import com.moon.module.impl.player.scaffold.sprint.NormalSprint;
import com.moon.module.impl.player.scaffold.tower.HypixelTower;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.util.game.BlockUtil;
import com.moon.util.game.MovementUtil;
import com.moon.util.game.SlotUtil;
import com.moon.util.math.MathUtil;
import com.moon.util.math.RotationUtil;
import com.moon.util.math.Vector2f;
import java.util.Arrays;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

public class ScaffoldWalk extends Module {
   private final EnumSetting<ScaffoldWalk.EdgeDetection> edgeDetection = EnumSetting.builder("edge-detection", ScaffoldWalk.EdgeDetection.class)
      .defaults("Edge detection", ScaffoldWalk.EdgeDetection.SIMPLE)
      .build();
   private final FloatSetting edgeBoxSize = FloatSetting.builder("edge-box-size")
      .defaults("Edge box size", 0.25F, 0.05F, 0.3F, 0.025F)
      .visibleWhen(() -> this.edgeDetection.getValue() == ScaffoldWalk.EdgeDetection.BOX)
      .build();
   private final FloatSetting searchRadius = FloatSetting.builder("search-radius").defaults("Search radius", 3.5F, 1.0F, 6.0F, 0.5F).build();
   private final BooleanSetting switchToBlock = BooleanSetting.builder("switch-to-block").defaults("Switch to block", true).build();
   private final BooleanSetting switchSilently = BooleanSetting.builder("switch-silently")
      .defaults("Switch silently", false)
      .visibleWhen(this.switchToBlock::getValue)
      .build();
   private final GroupSetting generalSettings = GroupSetting.builder()
      .defaults("General Settings", this.edgeDetection, this.edgeBoxSize, this.searchRadius, this.switchToBlock, this.switchSilently)
      .build();
   private final BooleanSetting tower = BooleanSetting.builder("tower").defaults("Tower", false).build();
   private final EnumSetting<ScaffoldWalk.TowerMode> towerMode = EnumSetting.builder("tower-mode", ScaffoldWalk.TowerMode.class)
      .defaults("Tower Mode", ScaffoldWalk.TowerMode.HYPIXEL)
      .visibleWhen(this.tower::getValue)
      .build();
   private final BooleanSetting allowSprinting = BooleanSetting.builder("allow-sprinting").defaults("Allow sprinting", false).build();
   private final EnumSetting<ScaffoldWalk.SprintMode> sprintMode = EnumSetting.builder("sprint-mode", ScaffoldWalk.SprintMode.class)
      .defaults("Sprint mode", ScaffoldWalk.SprintMode.NORMAL)
      .visibleWhen(this.allowSprinting::getValue)
      .build();
   private final BooleanSetting hypixelSlowSprint = BooleanSetting.builder("hypixel-slow-sprint")
      .defaults("Slow Sprint", true)
      .visibleWhen(() -> this.allowSprinting.getValue() && this.sprintMode.getValue() == ScaffoldWalk.SprintMode.HYPIXEL)
      .build();
   private final BooleanSetting unSprintOnPlace = BooleanSetting.builder("un-sprint-on-place")
      .defaults("Un-sprint on place", false)
      .visibleWhen(this.allowSprinting::getValue)
      .build();
   private final BooleanSetting keepY = BooleanSetting.builder("keep-y").defaults("Maintain same y", false).build();
   private final BooleanSetting breezlyPattern = BooleanSetting.builder("breezly-pattern").defaults("Breezly pattern", false).build();
   private final IntegerSetting breezlyDelay = IntegerSetting.builder("breezlyDelay")
      .defaults("Breezly switch delay", 2, 1, 5, 1)
      .visibleWhen(this.breezlyPattern::getValue)
      .build();
   private final EnumSetting<ScaffoldWalk.Mode> safeWalkMode = EnumSetting.builder("safe-walk-mode", ScaffoldWalk.Mode.class)
      .defaults("Safe-walk mode", ScaffoldWalk.Mode.NORMAL)
      .build();
   private final BooleanSetting correctMovement = BooleanSetting.builder("correct-movement").defaults("Correct movement", true).build();
   private final BooleanSetting limitMovementSpeed = BooleanSetting.builder("limit-movement-speed").defaults("Limit speed", false).build();
   private final FloatSetting speedLimit = FloatSetting.builder("speed-limit")
      .defaults("Speed limit", 0.2F, 0.01F, 0.5F, 0.001F)
      .visibleWhen(this.limitMovementSpeed::getValue)
      .build();
   public final MultipleChoiceSetting<ScaffoldWalk.SneakFeatures> sneakFeatures = MultipleChoiceSetting.builder(
         "sneak-features", ScaffoldWalk.SneakFeatures.class
      )
      .defaults("Sneak methods", ScaffoldWalk.SneakFeatures.EDGE)
      .build();
   private final IntegerSetting minSneakDuration = IntegerSetting.builder("min-sneak-duration")
      .defaults("Min sneak duration", 1, 1, 10, 1)
      .visibleWhen(() -> Arrays.stream(ScaffoldWalk.SneakFeatures.values()).anyMatch(this.sneakFeatures::hasChecked))
      .build();
   private final IntegerSetting maxSneakDuration = IntegerSetting.builder("max-sneak-duration")
      .defaults("Max sneak duration", 1, 1, 10, 1)
      .onChange((previousValue, newValue) -> {
         if (newValue < this.minSneakDuration.getValue()) {
            this.minSneakDuration.setRawValueClamped(newValue - this.minSneakDuration.getInc());
         }
      })
      .visibleWhen(() -> Arrays.stream(ScaffoldWalk.SneakFeatures.values()).anyMatch(this.sneakFeatures::hasChecked))
      .build();
   private final IntegerSetting sneakActivationDelay = IntegerSetting.builder("sneak-activation-delay")
      .defaults("Sneak activation delay", 1, 1, 30, 1)
      .visibleWhen(() -> this.sneakFeatures.hasChecked(ScaffoldWalk.SneakFeatures.DELAY))
      .build();
   private final FloatSetting sneakActivationDistance = FloatSetting.builder("sneak-activation-distance")
      .defaults("Sneak activation distance", 1.5F, 0.075F, 10.0F, 0.075F)
      .visibleWhen(() -> this.sneakFeatures.hasChecked(ScaffoldWalk.SneakFeatures.DISTANCE))
      .build();
   private final BooleanSetting swingAfterPlace = BooleanSetting.builder("swing-after-place").defaults("Swing after place", false).build();
   private final BooleanSetting raytrace = BooleanSetting.builder("raytrace").defaults("Raytrace", true).build();
   private final IntegerSetting missClickChance = IntegerSetting.builder("miss-click-chance")
      .defaults("Miss click chance", 0, 0, 100, 1)
      .displayName("The chance for a random click while not placing a block")
      .build();
   private final IntegerSetting doubleClickChance = IntegerSetting.builder("double-click-chance")
      .defaults("Double click chance", 10, 1, 100, 1)
      .visibleWhen(() -> this.missClickChance.getValue() > 0)
      .build();
   private final GroupSetting placementSettings = GroupSetting.builder()
      .defaults("Placement Settings", this.swingAfterPlace, this.raytrace, this.missClickChance, this.doubleClickChance)
      .build();
   private final GroupSetting movementSettings = GroupSetting.builder()
      .defaults(
         "Movement Settings",
         this.tower,
         this.keepY,
         this.allowSprinting,
         this.hypixelSlowSprint,
         this.unSprintOnPlace,
         this.breezlyPattern,
         this.correctMovement,
         this.limitMovementSpeed,
         this.speedLimit,
         this.breezlyDelay,
         this.towerMode,
         this.sprintMode,
         this.safeWalkMode
      )
      .build();
   private final GroupSetting sneakSettings = GroupSetting.builder()
      .defaults("Sneak Settings", this.sneakFeatures, this.minSneakDuration, this.maxSneakDuration, this.sneakActivationDelay, this.sneakActivationDistance)
      .build();
   private final BooleanSetting rotate = BooleanSetting.builder("rotate").defaults("Rotate", true).build();
   private final BooleanSetting backOnStart = BooleanSetting.builder("back-on-start")
      .defaults("Back on start", true)
      .description("Applies backwards rotations on enabling of the module")
      .visibleWhen(this.rotate::getValue)
      .build();
   private final BooleanSetting keepRotation = BooleanSetting.builder("keep-rotation")
      .defaults("Keep rotation", true)
      .visibleWhen(this.rotate::getValue)
      .build();
   private final BooleanSetting onlyRotateIfRequired = BooleanSetting.builder("only-rotate-if-required")
      .defaults("Only rotate if required", false)
      .visibleWhen(() -> this.rotate.getValue() && this.keepRotation.getValue())
      .build();
   private final BooleanSetting middlePoint = BooleanSetting.builder("middle-point").defaults("Middle point", false).visibleWhen(this.rotate::getValue).build();
   private final BooleanSetting useSensitivity = BooleanSetting.builder("use-sensitivity")
      .defaults("Use sensitivity", false)
      .description("More legit, can cause rotation inaccuracies")
      .visibleWhen(this.rotate::getValue)
      .build();
   private final IntegerSetting rotationSpeed = IntegerSetting.builder("rotation-speed")
      .defaults("Rotation Speed", 180, 1, 180, 1)
      .visibleWhen(this.rotate::getValue)
      .build();
   private final EnumSetting<ScaffoldWalk.RotationMode> rotationMode = EnumSetting.builder("rotation-mode", ScaffoldWalk.RotationMode.class)
      .defaults("Rotation Mode", ScaffoldWalk.RotationMode.BRUTEFORCE)
      .visibleWhen(this.rotate::getValue)
      .build();
   private final GroupSetting rotationSettings = GroupSetting.builder()
      .defaults(
         "Rotation Settings",
         this.rotate,
         this.backOnStart,
         this.keepRotation,
         this.onlyRotateIfRequired,
         this.middlePoint,
         this.useSensitivity,
         this.rotationMode,
         this.rotationSpeed
      )
      .build();
   private final Rotation rotationExecutor = new Rotation(this);
   private final BlockUtil blockUtil = new BlockUtil();
   private final Placement blockPlacer = new Placement(this);
   private boolean placed;
   private int previousSlot;
   private double targetY;
   private boolean breezlyLeft;
   private int breezlyTicks;
   private final Sneak sneakController = new Sneak();

   public ScaffoldWalk(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onEnable() {
      if (mc.thePlayer != null) {
         this.targetY = mc.thePlayer.getEntityBoundingBox().minY;
         mc.thePlayer.bC.deprecatedNoOp();
         this.rotationExecutor.applyBackRotation();
         this.previousSlot = mc.thePlayer.inventory.currentItem;
         if (this.allowSprinting.getValue()) {
            this.sprintMode.getValue().getSprintHandler().setScaffoldWalk(this);
            this.sprintMode.getValue().getSprintHandler().onEnable();
         }

         super.onEnable();
         this.breezlyLeft = false;
         this.breezlyTicks = 0;
      }
   }

   @Override
   public void onDisable() {
      if (mc.thePlayer != null) {
         mc.thePlayer.bC.switchToSlot(this.previousSlot);
         mc.thePlayer.bC.deprecatedNoOp2();
         if (this.allowSprinting.getValue()) {
            this.sprintMode.getValue().getSprintHandler().setScaffoldWalk(this);
            this.sprintMode.getValue().getSprintHandler().onDisable();
         }

         this.sneakController.reset();
         int leftKey = mc.gameSettings.keyBindLeft.getKeyCode();
         int rightKey = mc.gameSettings.keyBindRight.getKeyCode();
         KeyBinding.setKeyBindState(rightKey, Keyboard.isKeyDown(rightKey));
         KeyBinding.setKeyBindState(leftKey, Keyboard.isKeyDown(leftKey));
         super.onDisable();
      }
   }

   private int randomSneakDuration() {
      return MathUtil.randomInt(this.minSneakDuration.getValue(), Math.max(this.maxSneakDuration.getValue(), this.minSneakDuration.getValue()));
   }

   @Subscribe
   public void onHeldItem(HeldItemEvent event) {
      if (this.switchSilently.getValue()) {
         event.setSlot(this.previousSlot);
      }
   }

   @Subscribe
   public void onRenderHeldItem(RenderHeldItemEvent event) {
      if (this.switchSilently.getValue()) {
         event.setSlot(this.previousSlot);
      }
   }

   @Subscribe
   public void onSlotChange(SlotChangeEvent event) {
      if (this.switchSilently.getValue()) {
         this.previousSlot = event.getSlot();
      }

      event.setCancelled(true);
   }

   @Subscribe
   public void onSafeWalk(SafeWalkEvent event) {
      if (this.safeWalkMode.getValue() == ScaffoldWalk.Mode.NORMAL && mc.thePlayer.onGround) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onStrafe(StrafeEvent event) {
      Vector2f rotation = this.rotationExecutor.getRotations().getRotation();
      float motionSq = event.getForward() * event.getForward() + event.getStrafe() * event.getStrafe();
      if ((this.placed || this.getKeepRotation().getValue()) && motionSq > 1.0E-6F & this.correctMovement.getValue() && this.rotate.getValue()) {
         float yaw = rotation.getX();
         float[] movementRotations = RotationUtil.getMovementRotations(mc.thePlayer.rotationYaw, yaw);
         float multiplier = 0.98F * (mc.thePlayer.isUsingItem() ? 0.2F : 1.0F) * (mc.thePlayer.isSneaking() ? 0.3F : 1.0F);
         event.setYaw(yaw);
         event.setForward(movementRotations[0] * multiplier);
         event.setStrafe(movementRotations[1] * multiplier);
      }
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent event) {
      if (this.correctMovement.getValue() && mc.thePlayer.ae()) {
         float yaw = this.rotationExecutor.getRotations().getRotation().getX();
         float[] movementRotations = RotationUtil.getMovementRotations(mc.thePlayer.rotationYaw, yaw);
         event.setForward(movementRotations[0]);
      }
   }

   private void ensureBlockSelected() {
      if (this.switchToBlock.getValue()) {
         SlotUtil slotUtil = mc.thePlayer.bC;
         if (slotUtil.hasBlock()) {
            SlotUtil.Entry entry = slotUtil.findBlock();
            if (entry.isPresent()) {
               mc.thePlayer.bC.switchToSlot(entry.getSlot());
            }
         }
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE && mc.thePlayer != null) {
         this.ensureBlockSelected();
         if (mc.thePlayer.onGround
            || this.tower.getValue() && mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.ae()
            || !this.keepY.getValue()
            || !mc.thePlayer.ae()) {
            this.targetY = Math.floor(mc.thePlayer.getEntityBoundingBox().minY);
         }

         BlockPos targetPos = new BlockPos(mc.thePlayer.posX, this.targetY - 0.5, mc.thePlayer.posZ);
         boolean shouldPlace = this.shouldPlace();
         if (shouldPlace || this.shouldPlaceFallback()) {
            this.blockUtil.findNearestBlock(targetPos, this.searchRadius.getValue().floatValue());
            BlockPos blockPos = this.blockUtil.getBlockPos();
            EnumFacing facing = this.blockUtil.getFacing();
            if (shouldPlace && blockPos != null) {
               this.rotationExecutor.computeRotations(blockPos, facing);
            }
         }

         this.rotationExecutor.getRotations().stepRotation(this.getRotationSpeed().getValue().intValue(), this.getUseSensitivity().getValue());
      }
   }

   private boolean shouldPlace() {
      BlockPos targetPos = new BlockPos(mc.thePlayer.posX, this.targetY - 0.5, mc.thePlayer.posZ);

      return switch ((ScaffoldWalk.EdgeDetection)this.edgeDetection.getValue()) {
         case SIMPLE -> mc.theWorld.getBlockState(targetPos).getBlock().isReplaceable(mc.theWorld, targetPos);
         case BOX -> !this.isOverEdge();
      };
   }

   private boolean shouldPlaceFallback() {
      BlockPos targetPos = new BlockPos(mc.thePlayer.posX, this.targetY - 0.5, mc.thePlayer.posZ);
      return mc.theWorld.getBlockState(targetPos).getBlock().isReplaceable(mc.theWorld, targetPos) || !this.isOverEdge();
   }

   private boolean isOverEdge() {
      double offsetY = -0.1;
      AxisAlignedBB boundingBox = new AxisAlignedBB(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)
         .expand(this.edgeBoxSize.getValue().floatValue(), 0.0, this.edgeBoxSize.getValue().floatValue());

      for (AxisAlignedBB collisionBox : mc.thePlayer.worldObj.getCollidingBoundingBoxes(mc.thePlayer, boundingBox.addCoord(0.0, -0.1, 0.0))) {
         offsetY = collisionBox.calculateYOffset(boundingBox, offsetY);
      }

      return offsetY == 0.0;
   }

   @Subscribe
   public void onInput(InputEvent event) {
      if (this.shouldPlaceFallback() && this.blockUtil.getBlockPos() != null) {
         BlockPos targetPos = new BlockPos(mc.thePlayer.posX, this.targetY - 0.5, mc.thePlayer.posZ);

         for (int i = 0; i < 3; i++) {
            boolean lookingAtBlock = !this.getRaytrace().getValue() || this.rotationExecutor.isLookingAt(this.blockUtil.getBlockPos(), this.blockUtil.getFacing());
            if (!lookingAtBlock) {
               break;
            }

            this.blockPlacer.place(this.blockUtil.getBlockPos(), this.blockUtil.getFacing());
            if (this.isOverEdge()) {
               break;
            }

            this.blockUtil.findNearestBlock(targetPos, this.searchRadius.getValue().floatValue());
            if (this.blockUtil.getBlockPos() == null) {
               break;
            }
         }

         boolean overEdge = this.isOverEdge();
         if (mc.thePlayer.onGround && !overEdge && this.safeWalkMode.getValue() == ScaffoldWalk.Mode.JUMP) {
            mc.thePlayer.jump();
         }

         if (this.sneakFeatures.hasChecked(ScaffoldWalk.SneakFeatures.EDGE)) {
            this.sneakController.extend(this.randomSneakDuration());
         }

         if (this.unSprintOnPlace.getValue()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
            mc.thePlayer.setSprinting(false);
         }

         if (this.tower.getValue() && (!this.keepY.getValue() || !mc.thePlayer.ae())) {
            this.towerMode.getValue().getTowerHandler().onEnable();
         }
      } else if (mc.objectMouseOver != null) {
         this.ensureBlockSelected();
         boolean missClick = this.missClickChance.getValue() >= MathUtil.randomInt(1, 100);
         BlockPos mouseOverPos = mc.objectMouseOver.getBlockPos();
         ItemStack equippedItem = mc.thePlayer.getCurrentEquippedItem();
         if (missClick && mouseOverPos != null && equippedItem != null) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, equippedItem);
            if (this.doubleClickChance.getValue() >= MathUtil.randomInt(1, 100)) {
               mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, equippedItem);
            }
         }
      }

      if (this.sneakFeatures.hasChecked(ScaffoldWalk.SneakFeatures.DISTANCE)
         && this.sneakController.getDistance() >= this.sneakActivationDistance.getValue().floatValue()) {
         this.sneakController.extend(this.randomSneakDuration());
      }

      if (this.sneakFeatures.hasChecked(ScaffoldWalk.SneakFeatures.DELAY) && mc.thePlayer.ticksExisted % this.sneakActivationDelay.getValue() == 0) {
         this.sneakController.extend(this.randomSneakDuration());
      }

      this.sneakController.update();
   }

   @Subscribe
   public void onLook(LookEvent event) {
      Vector2f rotation = this.rotationExecutor.getRotations().getRotation();
      if (rotation != null) {
         event.setYaw(rotation.getX());
         event.setPitch(rotation.getY());
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (!this.allowSprinting.getValue()) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
         mc.thePlayer.setSprinting(false);
      }

      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         int leftKey = mc.gameSettings.keyBindLeft.getKeyCode();
         int rightKey = mc.gameSettings.keyBindRight.getKeyCode();
         KeyBinding.setKeyBindState(rightKey, Keyboard.isKeyDown(rightKey) && mc.currentScreen == null);
         KeyBinding.setKeyBindState(leftKey, Keyboard.isKeyDown(leftKey) && mc.currentScreen == null);
         boolean canBreezly = mc.thePlayer.onGround && mc.thePlayer.moveForward > 0.0F && mc.currentScreen == null;
         if (canBreezly && this.breezlyPattern.getValue()) {
            if (this.breezlyTicks++ >= this.breezlyDelay.getValue()) {
               this.breezlyLeft = !this.breezlyLeft;
               this.breezlyTicks = 0;
            }

            if (this.breezlyLeft) {
               KeyBinding.setKeyBindState(leftKey, true);
            } else {
               KeyBinding.setKeyBindState(rightKey, true);
            }
         }
      }

      if (this.limitMovementSpeed.getValue()
         && event.getSequence() == UpdateEvent.Sequence.PRE
         && MovementUtil.getBaseSpeed() > this.speedLimit.getValue().floatValue()
         && mc.thePlayer.ae()
         && mc.thePlayer.onGround) {
         MovementUtil.setSpeed(this.speedLimit.getValue().floatValue());
      }

      Vector2f rotation = this.rotationExecutor.getRotations().getRotation();
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.rotate.getValue() && rotation != null && (this.placed || this.getKeepRotation().getValue())) {
         event.setYaw(rotation.getX());
         event.setPitch(rotation.getY());
         this.placed = false;
      }

      if (this.tower.getValue() && (!this.keepY.getValue() || !mc.thePlayer.ae())) {
         this.towerMode.getValue().getTowerHandler().onUpdate(event);
      }

      if (this.allowSprinting.getValue()) {
         this.sprintMode.getValue().getSprintHandler().setScaffoldWalk(this);
         this.sprintMode.getValue().getSprintHandler().onUpdate(event);
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (this.tower.getValue() && (!this.keepY.getValue() || !mc.thePlayer.ae())) {
         this.towerMode.getValue().getTowerHandler().onMove(event);
      }

      if (this.allowSprinting.getValue()) {
         this.sprintMode.getValue().getSprintHandler().setScaffoldWalk(this);
         this.sprintMode.getValue().getSprintHandler().onMove(event);
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (this.allowSprinting.getValue()) {
         this.sprintMode.getValue().getSprintHandler().setScaffoldWalk(this);
         this.sprintMode.getValue().getSprintHandler().onPacket(event);
      }
   }

   @Subscribe
   public void onPauseCheck(KillAuraEvent event) {
      event.setCancelled(true);
   }

   public EnumSetting<ScaffoldWalk.EdgeDetection> getEdgeDetection() {
      return this.edgeDetection;
   }

   public FloatSetting getEdgeBoxSize() {
      return this.edgeBoxSize;
   }

   public FloatSetting getSearchRadius() {
      return this.searchRadius;
   }

   public BooleanSetting getSwitchToBlock() {
      return this.switchToBlock;
   }

   public BooleanSetting getSwitchSilently() {
      return this.switchSilently;
   }

   public GroupSetting getGeneralSettings() {
      return this.generalSettings;
   }

   public BooleanSetting getTower() {
      return this.tower;
   }

   public EnumSetting<ScaffoldWalk.TowerMode> getTowerMode() {
      return this.towerMode;
   }

   public BooleanSetting getAllowSprinting() {
      return this.allowSprinting;
   }

   public EnumSetting<ScaffoldWalk.SprintMode> getSprintMode() {
      return this.sprintMode;
   }

   public BooleanSetting getHypixelSlowSprint() {
      return this.hypixelSlowSprint;
   }

   public BooleanSetting getUnSprintOnPlace() {
      return this.unSprintOnPlace;
   }

   public BooleanSetting getKeepY() {
      return this.keepY;
   }

   public BooleanSetting getBreezlyPattern() {
      return this.breezlyPattern;
   }

   public IntegerSetting getBreezlyDelay() {
      return this.breezlyDelay;
   }

   public EnumSetting<ScaffoldWalk.Mode> getSafeWalkMode() {
      return this.safeWalkMode;
   }

   public BooleanSetting getCorrectMovement() {
      return this.correctMovement;
   }

   public BooleanSetting getLimitMovementSpeed() {
      return this.limitMovementSpeed;
   }

   public FloatSetting getSpeedLimit() {
      return this.speedLimit;
   }

   public MultipleChoiceSetting<ScaffoldWalk.SneakFeatures> getSneakFeatures() {
      return this.sneakFeatures;
   }

   public IntegerSetting getMinSneakDuration() {
      return this.minSneakDuration;
   }

   public IntegerSetting getMaxSneakDuration() {
      return this.maxSneakDuration;
   }

   public IntegerSetting getSneakActivationDelay() {
      return this.sneakActivationDelay;
   }

   public FloatSetting getSneakActivationDistance() {
      return this.sneakActivationDistance;
   }

   public BooleanSetting getSwingAfterPlace() {
      return this.swingAfterPlace;
   }

   public BooleanSetting getRaytrace() {
      return this.raytrace;
   }

   public IntegerSetting getMissClickChance() {
      return this.missClickChance;
   }

   public IntegerSetting getDoubleClickChance() {
      return this.doubleClickChance;
   }

   public GroupSetting getPlacementSettings() {
      return this.placementSettings;
   }

   public GroupSetting getMovementSettings() {
      return this.movementSettings;
   }

   public GroupSetting getSneakSettings() {
      return this.sneakSettings;
   }

   public BooleanSetting getRotate() {
      return this.rotate;
   }

   public BooleanSetting getBackOnStart() {
      return this.backOnStart;
   }

   public BooleanSetting getKeepRotation() {
      return this.keepRotation;
   }

   public BooleanSetting getOnlyRotateIfRequired() {
      return this.onlyRotateIfRequired;
   }

   public BooleanSetting getMiddlePoint() {
      return this.middlePoint;
   }

   public BooleanSetting getUseSensitivity() {
      return this.useSensitivity;
   }

   public IntegerSetting getRotationSpeed() {
      return this.rotationSpeed;
   }

   public EnumSetting<ScaffoldWalk.RotationMode> getRotationMode() {
      return this.rotationMode;
   }

   public GroupSetting getRotationSettings() {
      return this.rotationSettings;
   }

   public Rotation getRotationExecutor() {
      return this.rotationExecutor;
   }

   public BlockUtil getBlockUtil() {
      return this.blockUtil;
   }

   public Placement getBlockPlacer() {
      return this.blockPlacer;
   }

   public boolean isPlaced() {
      return this.placed;
   }

   public int getPreviousSlot() {
      return this.previousSlot;
   }

   public double getTargetY() {
      return this.targetY;
   }

   public boolean isBreezlyLeft() {
      return this.breezlyLeft;
   }

   public int getBreezlyTicks() {
      return this.breezlyTicks;
   }

   public Sneak getSneakController() {
      return this.sneakController;
   }

   public ScaffoldWalk setPlaced(boolean placed) {
      this.placed = placed;
      return this;
   }

   public enum EdgeDetection implements LabelEnum {
      SIMPLE("Simple"),
      BOX("Box");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      EdgeDetection(String displayName) {
         this.displayName = displayName;
      }

      private static ScaffoldWalk.EdgeDetection[] createValues() {
         return new ScaffoldWalk.EdgeDetection[]{SIMPLE, BOX};
      }
   }

   public enum Mode implements LabelEnum {
      NONE("None"),
      NORMAL("Normal"),
      JUMP("Jump");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      Mode(String displayName) {
         this.displayName = displayName;
      }

      private static ScaffoldWalk.Mode[] createValues() {
         return new ScaffoldWalk.Mode[]{NONE, NORMAL, JUMP};
      }
   }

   public enum RotationMode implements LabelEnum {
      CALCULATED("Calculated"),
      BRUTEFORCE("Bruteforce"),
      BACKWARDS("Backwards (180°)"),
      STATIC("Static"),
      ECONOMIC("Economic");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      RotationMode(String displayName) {
         this.displayName = displayName;
      }

      private static ScaffoldWalk.RotationMode[] createValues() {
         return new ScaffoldWalk.RotationMode[]{CALCULATED, BRUTEFORCE, BACKWARDS, STATIC, ECONOMIC};
      }
   }

   public enum SneakFeatures implements LabelEnum {
      EDGE("Edge"),
      DISTANCE("Traveled distance"),
      DELAY("Delay");

      private final String displayName;

      @Override
      public String label() {
         return this.displayName;
      }

      SneakFeatures(String displayName) {
         this.displayName = displayName;
      }

      private static ScaffoldWalk.SneakFeatures[] createValues() {
         return new ScaffoldWalk.SneakFeatures[]{EDGE, DISTANCE, DELAY};
      }
   }

   public enum SprintMode implements LabelEnum {
      NORMAL("Normal", new NormalSprint()),
      HYPIXEL("Hypixel", new HypixelSprint());

      private final String displayName;
      private final SprintScaffold sprintHandler;

      @Override
      public String label() {
         return this.displayName;
      }

      public SprintScaffold getSprintHandler() {
         return this.sprintHandler;
      }

      SprintMode(String displayName, SprintScaffold sprintHandler) {
         this.displayName = displayName;
         this.sprintHandler = sprintHandler;
      }

      private static ScaffoldWalk.SprintMode[] createValues() {
         return new ScaffoldWalk.SprintMode[]{NORMAL, HYPIXEL};
      }
   }

   public enum TowerMode implements LabelEnum {
      HYPIXEL("Hypixel", new HypixelTower());

      private final String displayName;
      private final Tower towerHandler;

      @Override
      public String label() {
         return this.displayName;
      }

      public Tower getTowerHandler() {
         return this.towerHandler;
      }

      TowerMode(String displayName, Tower towerHandler) {
         this.displayName = displayName;
         this.towerHandler = towerHandler;
      }

      private static ScaffoldWalk.TowerMode[] createValues() {
         return new ScaffoldWalk.TowerMode[]{HYPIXEL};
      }
   }
}
