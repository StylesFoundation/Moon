package com.moon.module.impl.combat;

import com.moon.Moon;
import com.moon.events.impl.input.KeyStateEvent;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.AttackInputEvent;
import com.moon.events.impl.player.JumpEvent;
import com.moon.events.impl.player.KillAuraEvent;
import com.moon.events.impl.player.LookEvent;
import com.moon.events.impl.player.MoveInputEvent;
import com.moon.events.impl.player.PickEvent;
import com.moon.events.impl.player.SlowdownEvent;
import com.moon.events.impl.player.StrafeEvent;
import com.moon.events.impl.player.TickbaseTickEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.FirstPersonItemRenderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.killaura.AutoBlock;
import com.moon.module.impl.combat.killaura.Sort;
import com.moon.module.impl.combat.killaura.autoblock.AttackSyncAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.FakeAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.HoldAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.HurtTimeAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.InvalidAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.OptimizedAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.PostAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.PreAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.SpoofAutoBlock;
import com.moon.module.impl.combat.killaura.autoblock.WatchdogAutoBlock;
import com.moon.module.impl.combat.killaura.components.Attack;
import com.moon.module.impl.combat.killaura.components.Rotation;
import com.moon.module.impl.combat.killaura.components.TargetSelect;
import com.moon.module.impl.combat.killaura.sort.AngleSort;
import com.moon.module.impl.combat.killaura.sort.DistanceSort;
import com.moon.module.impl.combat.killaura.sort.HealthSort;
import com.moon.module.impl.player.ScaffoldWalk;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.util.math.RotationUtil;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import org.lwjgl.util.vector.Vector2f;

public class KillAura extends Module {
   private final FloatSetting aimReach = FloatSetting.builder("aim-reach").defaults("Aiming Reach", 3.0F, 3.0F, 7.0F, 0.1F).build();
   private final FloatSetting attackReach = FloatSetting.builder("reach")
      .defaults("Attack Reach", 3.0F, 3.0F, 6.0F, 0.1F)
      .onChange((oldValue, newValue) -> this.aimReach.setMinLimit(newValue))
      .build();
   private final IntegerSetting cps = IntegerSetting.builder("cps").defaults("CPS", 8, 1, 20, 1).build();
   private final BooleanSetting velocityAttackSync = BooleanSetting.builder("velocity-attack-sync").defaults("Attack sync with velocity", false).build();
   private final MultipleChoiceSetting<KillAura.Targets> targets = MultipleChoiceSetting.builder("targets", KillAura.Targets.class)
      .defaults("Targets", KillAura.Targets.PLAYERS)
      .build();
   private final EnumSetting<KillAura.SelectionMode> selectionMode = EnumSetting.builder("selection-mode", KillAura.SelectionMode.class)
      .defaults("Selection Mode", KillAura.SelectionMode.SINGLE)
      .build();
   private final IntegerSetting switchDelay = IntegerSetting.builder("switch-delay")
      .defaults("Switch Delay", 50, 0, 1000, 10)
      .visibleWhen(() -> this.selectionMode.getValue() == KillAura.SelectionMode.SWITCH)
      .build();
   private final EnumSetting<KillAura.SortType> sortType = EnumSetting.builder("sorting-type", KillAura.SortType.class)
      .defaults("Sorting Mode", KillAura.SortType.FOV)
      .visibleWhen(() -> this.selectionMode.getValue() == KillAura.SelectionMode.SINGLE)
      .build();
   private final GroupSetting targetSettings = GroupSetting.builder()
      .defaults("Target Settings", this.targets, this.selectionMode, this.switchDelay, this.sortType)
      .build();
   private final BooleanSetting rotate = BooleanSetting.builder("rotate").defaults("Rotate", true).build();
   private final BooleanSetting correctMovement = BooleanSetting.builder("correct-movement").defaults("Correct movement", false).build();
   private final BooleanSetting silentlyCorrect = BooleanSetting.builder("silently-correct")
      .defaults("Silently correct", false)
      .visibleWhen(this.correctMovement::getValue)
      .build();
   private final FloatSetting rotationSpeed = FloatSetting.builder("rotation-speed").defaults("Rotation Speed", 90.0F, 1.0F, 180.0F, 1.0F).build();
   private final BooleanSetting onlyRotateWhenNeeded = BooleanSetting.builder("only-rotate-when-needed").defaults("Only rotate if required", false).build();
   private final BooleanSetting dontRotateInHitbox = BooleanSetting.builder("dont-rotate-in-hitbox").defaults("Don't rotate in hit-box", true).build();
   private final BooleanSetting preventBackSnap = BooleanSetting.builder("prevent-back-snap").defaults("Prevent Back-snap", false).build();
   private final MultipleChoiceSetting<KillAura.RandomizationType> randomizationTypes = MultipleChoiceSetting.builder(
         "randomization-types", KillAura.RandomizationType.class
      )
      .defaults("Randomization Types")
      .build();
   private final FloatSetting circularSpeed = FloatSetting.builder("circular-speed")
      .defaults("Circular speed", 0.5F, 0.05F, 2.0F, 0.05F)
      .visibleWhen(() -> this.randomizationTypes.hasChecked(KillAura.RandomizationType.CIRCULAR))
      .build();
   private final FloatSetting randomizationAmount = FloatSetting.builder("randomization-amount")
      .defaults("Randomization Amount", 2.5F, 0.1F, 25.0F, 0.1F)
      .visibleWhen(() -> this.randomizationTypes.hasChecked(KillAura.RandomizationType.NORMAL))
      .build();
   private final GroupSetting rotationSettings = GroupSetting.builder()
      .defaults(
         "Rotation Settings",
         this.rotate,
         this.correctMovement,
         this.silentlyCorrect,
         this.rotationSpeed,
         this.preventBackSnap,
         this.onlyRotateWhenNeeded,
         this.dontRotateInHitbox,
         this.randomizationTypes,
         this.randomizationAmount,
         this.circularSpeed
      )
      .build();
   private final EnumSetting<KillAura.ClickPattern> clickPattern = EnumSetting.builder("click-pattern", KillAura.ClickPattern.class)
      .defaults("Click Pattern", KillAura.ClickPattern.ADVANCED)
      .build();
   public final BooleanSetting doubleClicks = BooleanSetting.builder("double")
      .defaults("Double clicks", true)
      .visibleWhen(() -> this.clickPattern.getValue() == KillAura.ClickPattern.ADVANCED || this.clickPattern.getValue() == KillAura.ClickPattern.HURT_TIME)
      .build();
   public final BooleanSetting forceBalancedDeviation = BooleanSetting.builder("force-balanced-deviation")
      .defaults("Force balanced deviation", true)
      .visibleWhen(() -> this.clickPattern.getValue() == KillAura.ClickPattern.ADVANCED || this.clickPattern.getValue() == KillAura.ClickPattern.HURT_TIME)
      .build();
   public final BooleanSetting forceClickOnTickbase = BooleanSetting.builder("force-click-on-tickbase").defaults("Force click on tick-base", true).build();
   public final BooleanSetting realClick = BooleanSetting.builder("real-click").defaults("Real click", false).build();
   public final BooleanSetting ignoreBlocking = BooleanSetting.builder("ignore-blocking")
      .defaults("Ignore blocking", false)
      .visibleWhen(this.realClick::getValue)
      .build();
   private final GroupSetting attackSettings = GroupSetting.builder()
      .defaults(
         "Attack Settings",
         this.clickPattern,
         this.doubleClicks,
         this.forceBalancedDeviation,
         this.forceClickOnTickbase,
         this.velocityAttackSync,
         this.realClick,
         this.ignoreBlocking
      )
      .build();
   public final BooleanSetting autoBlock = BooleanSetting.builder("auto-block").defaults("Auto block", false).build();
   public final BooleanSetting clickAutoBlock = BooleanSetting.builder("click-auto-block")
      .defaults("Click auto block", false)
      .visibleWhen(this.autoBlock::getValue)
      .build();
   public final BooleanSetting unblockOnAttack = BooleanSetting.builder("unblock-on-attack")
      .defaults("Unblock on attack", true)
      .visibleWhen(this.autoBlock::getValue)
      .build();
   public final EnumSetting<KillAura.AutoBlockMode> autoBlockMode = EnumSetting.builder("auto-block-mode", KillAura.AutoBlockMode.class)
      .defaults("Blocking mode", KillAura.AutoBlockMode.POST)
      .visibleWhen(() -> !this.clickAutoBlock.getValue() && this.autoBlock.getValue())
      .build();
   public final EnumSetting<KillAura.ClickAutoBlockMode> clickAutoBlockMode = EnumSetting.builder("click-auto-block-mode", KillAura.ClickAutoBlockMode.class)
      .defaults("Blocking mode", KillAura.ClickAutoBlockMode.HURT_TIME)
      .visibleWhen(() -> this.clickAutoBlock.getValue() && this.autoBlock.getValue())
      .build();
   private final BooleanSetting watchdogAlternative = BooleanSetting.builder("watchdog-alternative")
      .defaults("Alternative bypass", false)
      .visibleWhen(() -> this.autoBlockMode.getValue() == KillAura.AutoBlockMode.WATCHDOG && this.autoBlock.getValue() && !this.clickAutoBlock.getValue())
      .build();
   private final GroupSetting blockSettings = GroupSetting.builder()
      .defaults(
         "Block Settings", this.autoBlock, this.clickAutoBlock, this.unblockOnAttack, this.autoBlockMode, this.clickAutoBlockMode, this.watchdogAlternative
      )
      .build();
   private final TargetSelect targetSelector = new TargetSelect(this);
   private final Rotation rotationHandler = new Rotation(this);
   private final Attack attackScheduler = new Attack(this);
   private ScaffoldWalk scaffoldWalk;
   private boolean attacked;

   public KillAura(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onEnable() {
      if (this.isAutoBlockEnabled()) {
         this.getAutoBlock().onEnable();
      }

      this.rotationHandler.init();
      this.targetSelector.enable();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      if (this.isAutoBlockEnabled()) {
         this.getAutoBlock().onDisable();
      }

      this.rotationHandler.disable();
      this.targetSelector.disable();
      super.onDisable();
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.aimReach.setMinLimit(this.attackReach.getValue());
      if (event.getSequence() == TickEvent.Sequence.PRE && mc.thePlayer != null && mc.theWorld != null) {
         if (!this.isPaused()) {
            this.targetSelector.updateTarget();
            this.rotationHandler.update(this.targetSelector.getTarget());
         }
      }
   }

   @Subscribe
   public void onMoveInput(MoveInputEvent event) {
      if (!this.isPaused()) {
         if (this.correctMovement.getValue() && this.silentlyCorrect.getValue() && mc.thePlayer.ae()) {
            float targetYaw = this.rotationHandler.getTargetRotation().getX();
            float[] movementRotations = RotationUtil.getMovementRotations(mc.thePlayer.rotationYaw, targetYaw);
            event.setForward(movementRotations[0]);
         }
      }
   }

   @Subscribe
   public void onStrafe(StrafeEvent event) {
      if (!this.isPaused()) {
         if (this.rotationHandler.hasTargetRotation() && this.correctMovement.getValue()) {
            float movementSq = event.getForward() * event.getForward() + event.getStrafe() * event.getStrafe();
            float targetYaw = this.rotationHandler.getTargetRotation().getX();
            if (this.silentlyCorrect.getValue()) {
               if (movementSq > 1.0E-6F) {
                  float[] movementRotations = RotationUtil.getMovementRotations(mc.thePlayer.rotationYaw, targetYaw);
                  float speedModifier = 0.98F;
                  if (mc.thePlayer.isUsingItem()) {
                     SlowdownEvent itemSlowdown = new SlowdownEvent(SlowdownEvent.Type.ITEM);
                     Moon.getInstance().getEventBus().publish(itemSlowdown);
                     if (!itemSlowdown.isCancelled()) {
                        speedModifier = 0.19600001F;
                     }
                  }

                  if (mc.thePlayer.isSneaking()) {
                     SlowdownEvent sneakingSlowdown = new SlowdownEvent(SlowdownEvent.Type.SNEAKING);
                     Moon.getInstance().getEventBus().publish(sneakingSlowdown);
                     if (!sneakingSlowdown.isCancelled()) {
                        speedModifier *= 0.3F;
                     }
                  }

                  event.setYaw(targetYaw);
                  event.setForward(movementRotations[0] * speedModifier);
                  event.setStrafe(movementRotations[1] * speedModifier);
               }
            } else {
               event.setYaw(targetYaw);
            }
         }
      }
   }

   @Subscribe
   public void onTickbaseTick(TickbaseTickEvent event) {
      if (this.forceClickOnTickbase.getValue() && this.targetSelector.hasTarget()) {
         this.attackScheduler.attack();
         this.attackScheduler.setTickCounter(0);
         this.attackScheduler.setSuppressScheduling(true);
      }
   }

   @Subscribe
   public void onKey(KeyStateEvent event) {
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (!this.isPaused()) {
         if (this.isAutoBlockEnabled()) {
            this.getAutoBlock().onPacket(event);
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (!this.isPaused()) {
         this.setSuffix(this.selectionMode.getValue().label);
         if (this.isAutoBlockEnabled()) {
            this.getAutoBlock().onUpdate(event);
         }

         if (this.rotationHandler.hasTargetRotation() && event.getSequence() == UpdateEvent.Sequence.PRE) {
            Vector2f targetRotation = this.rotationHandler.getTargetRotation();
            if (this.rotate.getValue()) {
               event.setYaw(targetRotation.getX());
               event.setPitch(targetRotation.getY());
            }

            if (this.targetSelector.hasTarget()) {
               this.attackScheduler.tick();
            }
         }
      }
   }

   @Subscribe
   public void onLook(LookEvent event) {
      if (!this.isPaused()) {
         if (this.rotationHandler.hasTargetRotation()) {
            Vector2f targetRotation = this.rotationHandler.getTargetRotation();
            event.setYaw(targetRotation.getX());
            event.setPitch(targetRotation.getY());
         }
      }
   }

   @Subscribe
   public void onJump(JumpEvent event) {
      if (!this.isPaused()) {
         if (this.rotationHandler.hasTargetRotation() && this.correctMovement.getValue()) {
            event.setYaw(this.rotationHandler.getTargetRotation().getX());
         }
      }
   }

   @Subscribe
   public void onFirstPersonItemRender(FirstPersonItemRenderEvent event) {
      if (!this.isPaused()) {
         if (this.isAutoBlockEnabled()
            && mc.thePlayer.getHeldItem() != null
            && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
            && this.targetSelector.hasTarget()) {
            event.setUsing(true);
         }
      }
   }

   @Subscribe
   public void onPick(PickEvent event) {
      if (this.realClick.getValue()) {
         event.setRange(this.attackReach.getValue().floatValue());
      }
   }

   @Subscribe
   public void onAttackInput(AttackInputEvent event) {
      if (this.ignoreBlocking.getValue() && this.targetSelector.hasTarget()) {
         event.setCancelled(true);
      }
   }

   public boolean isAutoBlockEnabled() {
      return this.autoBlock.getValue();
   }

   public AutoBlock getAutoBlock() {
      AutoBlock autoBlock = this.clickAutoBlock.getValue() ? this.clickAutoBlockMode.getValue().getMode() : this.autoBlockMode.getValue().getMode();
      autoBlock.setKillAura(this);
      return autoBlock;
   }

   private void resolveScaffold() {
      if (this.scaffoldWalk == null) {
         this.scaffoldWalk = Moon.getInstance().getModuleManager().getModule(ScaffoldWalk.class);
      }
   }

   private boolean isPaused() {
      KillAuraEvent event = new KillAuraEvent();
      Moon.getInstance().getEventBus().publish(event);
      return event.isCancelled();
   }

   public FloatSetting getAimReach() {
      return this.aimReach;
   }

   public FloatSetting getAttackReach() {
      return this.attackReach;
   }

   public IntegerSetting getCps() {
      return this.cps;
   }

   public BooleanSetting getVelocityAttackSync() {
      return this.velocityAttackSync;
   }

   public MultipleChoiceSetting<KillAura.Targets> getTargets() {
      return this.targets;
   }

   public EnumSetting<KillAura.SelectionMode> getSelectionMode() {
      return this.selectionMode;
   }

   public IntegerSetting getSwitchDelay() {
      return this.switchDelay;
   }

   public EnumSetting<KillAura.SortType> getSortType() {
      return this.sortType;
   }

   public GroupSetting getTargetSettings() {
      return this.targetSettings;
   }

   public BooleanSetting getRotate() {
      return this.rotate;
   }

   public BooleanSetting getCorrectMovement() {
      return this.correctMovement;
   }

   public BooleanSetting getSilentlyCorrect() {
      return this.silentlyCorrect;
   }

   public FloatSetting getRotationSpeed() {
      return this.rotationSpeed;
   }

   public BooleanSetting getOnlyRotateWhenNeeded() {
      return this.onlyRotateWhenNeeded;
   }

   public BooleanSetting getDontRotateInHitbox() {
      return this.dontRotateInHitbox;
   }

   public BooleanSetting getPreventBackSnap() {
      return this.preventBackSnap;
   }

   public MultipleChoiceSetting<KillAura.RandomizationType> getRandomizationTypes() {
      return this.randomizationTypes;
   }

   public FloatSetting getCircularSpeed() {
      return this.circularSpeed;
   }

   public FloatSetting getRandomizationAmount() {
      return this.randomizationAmount;
   }

   public GroupSetting getRotationSettings() {
      return this.rotationSettings;
   }

   public EnumSetting<KillAura.ClickPattern> getClickPattern() {
      return this.clickPattern;
   }

   public BooleanSetting getDoubleClicks() {
      return this.doubleClicks;
   }

   public BooleanSetting getForceBalancedDeviation() {
      return this.forceBalancedDeviation;
   }

   public BooleanSetting getForceClickOnTickbase() {
      return this.forceClickOnTickbase;
   }

   public BooleanSetting getRealClick() {
      return this.realClick;
   }

   public BooleanSetting getIgnoreBlocking() {
      return this.ignoreBlocking;
   }

   public GroupSetting getAttackSettings() {
      return this.attackSettings;
   }

   public BooleanSetting getAutoBlockSetting() {
      return this.autoBlock;
   }

   public BooleanSetting getClickAutoBlockSetting() {
      return this.clickAutoBlock;
   }

   public BooleanSetting getUnblockOnAttack() {
      return this.unblockOnAttack;
   }

   public EnumSetting<KillAura.AutoBlockMode> getAutoBlockMode() {
      return this.autoBlockMode;
   }

   public EnumSetting<KillAura.ClickAutoBlockMode> getClickAutoBlockMode() {
      return this.clickAutoBlockMode;
   }

   public BooleanSetting getWatchdogAlternative() {
      return this.watchdogAlternative;
   }

   public GroupSetting getBlockSettings() {
      return this.blockSettings;
   }

   public TargetSelect getTargetSelector() {
      return this.targetSelector;
   }

   public Rotation getRotationHandler() {
      return this.rotationHandler;
   }

   public Attack getAttackScheduler() {
      return this.attackScheduler;
   }

   public ScaffoldWalk getScaffoldWalk() {
      return this.scaffoldWalk;
   }

   public boolean isAttacked() {
      return this.attacked;
   }

   public enum AutoBlockMode implements LabelEnum {
      PRE("Pre", new PreAutoBlock()),
      POST("Post", new PostAutoBlock()),
      WATCHDOG("Watchdog", new WatchdogAutoBlock()),
      FAKE("Fake", new FakeAutoBlock()),
      SPOOF("Spoof", new SpoofAutoBlock()),
      INVALID("Invalid", new InvalidAutoBlock());

      private final String label;
      private final AutoBlock mode;

      @Override
      public String label() {
         return this.label;
      }

      public AutoBlock getMode() {
         return this.mode;
      }

      AutoBlockMode(String label, AutoBlock mode) {
         this.label = label;
         this.mode = mode;
      }
   }

   public enum ClickAutoBlockMode implements LabelEnum {
      HURT_TIME("Hurt time", new HurtTimeAutoBlock()),
      ATTACK_SYNC("Attack sync", new AttackSyncAutoBlock()),
      OPTIMIZED("Optimized", new OptimizedAutoBlock()),
      FAKE("Fake", new FakeAutoBlock()),
      HOLD("Hold", new HoldAutoBlock());

      private final String label;
      private final AutoBlock mode;

      @Override
      public String label() {
         return this.label;
      }

      public AutoBlock getMode() {
         return this.mode;
      }

      ClickAutoBlockMode(String label, AutoBlock mode) {
         this.label = label;
         this.mode = mode;
      }
   }

   public enum ClickPattern implements LabelEnum {
      BASIC("Basic"),
      ADVANCED("Advanced"),
      LATEST("1.9+"),
      HURT_TIME("Hurt time");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      ClickPattern(String label) {
         this.label = label;
      }
   }

   public enum RandomizationType implements LabelEnum {
      NORMAL("Normal"),
      ATTACK_POINT("Attack Point"),
      CIRCULAR("Circular");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      RandomizationType(String label) {
         this.label = label;
      }
   }

   public enum SelectionMode implements LabelEnum {
      SINGLE("Single"),
      SWITCH("Switch"),
      DYNAMIC("Dynamic");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      SelectionMode(String label) {
         this.label = label;
      }
   }

   public enum SortType implements LabelEnum {
      FOV("Field of View", new AngleSort()),
      HEALTH("Health", new HealthSort()),
      DISTANCE("Distance", new DistanceSort());

      private final String label;
      private final Sort sort;

      @Override
      public String label() {
         return this.label;
      }

      public Sort getSort() {
         return this.sort;
      }

      SortType(String label, Sort sort) {
         this.label = label;
         this.sort = sort;
      }
   }

   public enum Targets implements LabelEnum {
      PLAYERS("Players", entity -> entity instanceof EntityPlayer),
      ANIMALS("Animals", entity -> entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityVillager),
      MOBS("Mobs", entity -> entity instanceof EntityMob || entity instanceof EntityIronGolem),
      INVISIBLES("Invisibles", Entity::isInvisible);

      private final String label;
      private final Predicate<Entity> predicate;

      @Override
      public String label() {
         return this.label;
      }

      public Predicate<Entity> getPredicate() {
         return this.predicate;
      }

      Targets(String label, Predicate<Entity> predicate) {
         this.label = label;
         this.predicate = predicate;
      }
   }
}
