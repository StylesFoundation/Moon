package net.minecraft.client.entity;

import com.moon.Moon;
import com.moon.events.impl.network.ChatEvent;
import com.moon.events.impl.player.MoveInputEvent;
import com.moon.events.impl.player.SlowdownEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.movement.Sprint;
import com.moon.util.client.Stopwatch;
import com.moon.viamcp.ViaMCP;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import org.lwjgl.opengl.Display;

public class EntityPlayerSP extends AbstractClientPlayer {
   public final NetHandlerPlayClient sendQueue;
   private final StatFileWriter statWriter;
   public MovementInput movementInput;
   public int sprintingTicksLeft;
   public float renderArmYaw;
   public float renderArmPitch;
   public float prevRenderArmYaw;
   public float prevRenderArmPitch;
   public float timeInPortal;
   public float prevTimeInPortal;
   public float cw;
   public float cx;
   protected Minecraft mc;
   public int sprintToggleTimer;
   private double cA;
   private double cB;
   private double cC;
   private boolean cD;
   private boolean cE;
   private int positionUpdateTicks;
   private boolean hasValidHealth;
   private String clientBrand;
   private int horseJumpPowerCounter;
   private float horseJumpPower;
   public float cK;
   public float cL;
   public float cM;
   public float cN;
   public boolean cP;
   private Sprint cQ;

   public EntityPlayerSP(Minecraft minecraft, World world, NetHandlerPlayClient netHandlerPlayClient, StatFileWriter statFileWriter) {
      super(world, netHandlerPlayClient.getGameProfile());
      this.sendQueue = netHandlerPlayClient;
      this.statWriter = statFileWriter;
      this.mc = minecraft;
      this.dimension = 0;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      return false;
   }

   @Override
   public void heal(float float1) {
   }

   @Override
   public void mountEntity(Entity entity) {
      super.mountEntity(entity);
      if (entity instanceof EntityMinecart) {
         this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entity));
      }
   }

   @Override
   public void onUpdate() {
      if (this.worldObj.Method322(new BlockPos(this.posX, 0.0, this.posZ))) {
         Moon.getInstance().getEventBus().publish(new UpdateEvent(UpdateEvent.Sequence.BEFORE, 0.0, 0.0, 0.0, 0.0F, 0.0F, false));
         super.onUpdate();
         if (this.isRiding()) {
            this.sendQueue.Method754(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
            this.sendQueue.Method754(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
         } else {
            this.Method508();
         }
      }
   }

   public void Method508() {
      double var4 = this.posX - this.lastTickPosX;
      double var6 = this.posZ - this.lastTickPosZ;
      this.j = Math.sqrt(var4 * var4 + var6 * var6);
      UpdateEvent var8 = new UpdateEvent(
         UpdateEvent.Sequence.PRE, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround
      );
      Moon.getInstance().getEventBus().publish(var8);
      if (var8.isCancelled()) {
         Moon.getInstance()
            .getEventBus()
            .publish(
               new UpdateEvent(
                  UpdateEvent.Sequence.POST, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround
               )
            );
      } else {
         boolean var9 = this.isSprinting();
         if (var9 != this.cE) {
            if (var9) {
               this.sendQueue.Method754(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            } else {
               this.sendQueue.Method754(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.cP = false;
            this.cE = var9;
         }

         boolean var10 = this.isSneaking();
         if (var10 != this.cD) {
            if (var10) {
               this.sendQueue.Method754(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            } else {
               this.sendQueue.Method754(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.cD = var10;
         }

         if (this.isCurrentViewEntity()) {
            double var11 = var8.getX() - this.cA;
            double var13 = var8.getY() - this.cB;
            double var15 = var8.getZ() - this.cC;
            double var17 = var8.getYaw() - this.cw;
            double var19 = var8.getPitch() - this.cx;
            boolean var21 = var11 * var11 + var13 * var13 + var15 * var15 > 9.0E-4 || this.positionUpdateTicks >= 20;
            boolean var22 = var17 != 0.0 || var19 != 0.0;
            if (this.ridingEntity != null) {
               this.sendQueue
                  .Method754(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0, this.motionZ, var8.getYaw(), var8.getPitch(), var8.isOnGround()));
               var21 = false;
            } else if (var21 && var22) {
               this.sendQueue
                  .Method754(
                     new C03PacketPlayer.C06PacketPlayerPosLook(var8.getX(), var8.getY(), var8.getZ(), var8.getYaw(), var8.getPitch(), var8.isOnGround())
                  );
            } else if (var21) {
               this.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(var8.getX(), var8.getY(), var8.getZ(), var8.isOnGround()));
            } else if (var22) {
               this.sendQueue.Method754(new C03PacketPlayer.C05PacketPlayerLook(var8.getYaw(), var8.getPitch(), var8.isOnGround()));
            } else if (ViaMCP.getInstance().getVersion() <= 47) {
               this.sendQueue.Method754(new C03PacketPlayer(var8.isOnGround()));
            }

            this.positionUpdateTicks++;
            if (var21) {
               this.cA = var8.getX();
               this.cB = var8.getY();
               this.cC = var8.getZ();
               this.positionUpdateTicks = 0;
            }

            if (var22) {
               this.cw = var8.getYaw();
               this.cx = var8.getPitch();
            }

            this.cL = this.cK;
            this.cK = var8.getPitch();
            this.cN = this.cM;
            this.cM = var8.getYaw();
            this.rotationYawHead = var8.getYaw();
         }

         Moon.getInstance()
            .getEventBus()
            .publish(new UpdateEvent(UpdateEvent.Sequence.POST, var8.getX(), var8.getY(), var8.getZ(), var8.getYaw(), var8.getPitch(), var8.isOnGround()));
      }
   }

   @Override
   public EntityItem dropOneItem(boolean boolean1) {
      C07PacketPlayerDigging.Action var4 = boolean1 ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
      this.sendQueue.Method754(new C07PacketPlayerDigging(var4, BlockPos.ORIGIN, EnumFacing.DOWN));
      return null;
   }

   @Override
   protected void joinEntityItemWithWorld(EntityItem entityItem) {
   }

   public void sendChatMessage(String string) {
      ChatEvent var5 = new ChatEvent(string);
      Moon.getInstance().getEventBus().publish(var5);
      if (!var5.isCancelled()) {
         string = var5.getMessage();
         this.sendQueue.Method754(new C01PacketChatMessage(string));
      }
   }

   @Override
   public void swingItem() {
      super.swingItem();
      this.sendQueue.Method754(new C0APacketAnimation());
   }

   @Override
   public void respawnPlayer() {
      this.sendQueue.Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
   }

   @Override
   protected void damageEntity(DamageSource damageSource, float float2) {
      if (!this.isEntityInvulnerable(damageSource)) {
         this.setHealth(this.getHealth() - float2);
      }
   }

   @Override
   public void closeScreen() {
      this.sendQueue.Method754(new C0DPacketCloseWindow(this.openContainer.windowId));
      this.closeScreenAndDropStack();
   }

   public void closeScreenAndDropStack() {
      this.inventory.setItemStack(null);
      super.closeScreen();
      this.mc.displayGuiScreen(null);
   }

   public void setPlayerSPHealth(float float1) {
      if (this.hasValidHealth) {
         float var5 = this.getHealth() - float1;
         if (var5 <= 0.0F) {
            this.setHealth(float1);
            if (var5 < 0.0F) {
               this.hurtResistantTime = this.maxHurtResistantTime / 2;
            }
         } else {
            this.lastDamage = var5;
            this.setHealth(this.getHealth());
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(DamageSource.generic, var5);
            this.hurtTime = this.maxHurtTime = 10;
         }
      } else {
         this.setHealth(float1);
         this.hasValidHealth = true;
      }
   }

   @Override
   public void addStat(StatBase statBase, int integer) {
      if (statBase != null && statBase.isIndependent) {
         super.addStat(statBase, integer);
      }
   }

   @Override
   public void sendPlayerAbilities() {
      this.sendQueue.Method754(new C13PacketPlayerAbilities(this.capabilities));
   }

   @Override
   public boolean isUser() {
      return true;
   }

   protected void sendHorseJump() {
      this.sendQueue.Method754(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0F)));
   }

   public void sendHorseInventory() {
      this.sendQueue.Method754(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
   }

   public String getClientBrand() {
      return this.clientBrand;
   }

   public void setClientBrand(String string) {
      this.clientBrand = string;
   }

   public StatFileWriter getStatFileWriter() {
      return this.statWriter;
   }

   @Override
   public void addChatComponentMessage(IChatComponent iChatComponent) {
      this.mc.ingameGUI.getChatGUI().printChatMessage(iChatComponent);
   }

   @Override
   protected boolean pushOutOfBlocks(double double1, double double2, double double3) {
      if (!this.noClip) {
         BlockPos var10 = new BlockPos(double1, double2, double3);
         double var11 = double1 - var10.getX();
         double var13 = double3 - var10.getZ();
         if (!this.isOpenBlockSpace(var10)) {
            byte var15 = -1;
            double var16 = 9999.0;
            if (this.isOpenBlockSpace(var10.west()) && var11 < 9999.0) {
               var16 = var11;
               var15 = 0;
            }

            if (this.isOpenBlockSpace(var10.east()) && 1.0 - var11 < var16) {
               var16 = 1.0 - var11;
               var15 = 1;
            }

            if (this.isOpenBlockSpace(var10.north()) && var13 < var16) {
               var16 = var13;
               var15 = 4;
            }

            if (this.isOpenBlockSpace(var10.south()) && 1.0 - var13 < var16) {
               double var10000 = 1.0 - var13;
               var15 = 5;
            }

            if (var15 == 0) {
               this.motionX = -0.1F;
            }

            if (var15 == 1) {
               this.motionX = 0.1F;
            }

            if (var15 == 4) {
               this.motionZ = -0.1F;
            }

            if (var15 == 5) {
               this.motionZ = 0.1F;
            }
         }
      }

      return false;
   }

   private boolean isOpenBlockSpace(BlockPos blockPos) {
      return !this.worldObj.getBlockState(blockPos).getBlock().isNormalCube() && !this.worldObj.getBlockState(blockPos.up()).getBlock().isNormalCube();
   }

   @Override
   public void setSprinting(boolean boolean1) {
      super.setSprinting(boolean1);
      this.sprintingTicksLeft = boolean1 ? 600 : 0;
   }

   public void setXPStats(float float1, int integer2, int integer3) {
      this.experience = float1;
      this.experienceTotal = integer2;
      this.experienceLevel = integer3;
   }

   @Override
   public void addChatMessage(IChatComponent iChatComponent) {
      this.mc.ingameGUI.getChatGUI().printChatMessage(iChatComponent);
   }

   @Override
   public boolean canCommandSenderUseCommand(int integer, String string) {
      return integer <= 0;
   }

   @Override
   public BlockPos getPosition() {
      return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
   }

   @Override
   public void playSound(String string, float float2, float float3) {
      this.worldObj.playSound(this.posX, this.posY, this.posZ, string, float2, float3, false);
   }

   @Override
   public boolean isServerWorld() {
      return true;
   }

   public boolean isRidingHorse() {
      return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
   }

   public float getHorseJumpPower() {
      return this.horseJumpPower;
   }

   @Override
   public void openEditSign(TileEntitySign tileEntitySign) {
      this.mc.displayGuiScreen(new GuiEditSign(tileEntitySign));
   }

   @Override
   public void openEditCommandBlock(CommandBlockLogic commandBlockLogic) {
      this.mc.displayGuiScreen(new GuiCommandBlock(commandBlockLogic));
   }

   @Override
   public void displayGUIBook(ItemStack itemStack) {
      Item var4 = itemStack.getItem();
      if (var4 == Items.writable_book) {
         this.mc.displayGuiScreen(new GuiScreenBook(this, itemStack, true));
      }
   }

   @Override
   public void displayGUIChest(IInventory iInventory) {
      String var5 = iInventory instanceof IInteractionObject ? ((IInteractionObject)iInventory).getGuiID() : "minecraft:container";
      if ("minecraft:chest".equals(var5)) {
         this.mc.displayGuiScreen(new GuiChest(this.inventory, iInventory));
      } else if ("minecraft:hopper".equals(var5)) {
         this.mc.displayGuiScreen(new GuiHopper(this.inventory, iInventory));
      } else if ("minecraft:furnace".equals(var5)) {
         this.mc.displayGuiScreen(new GuiFurnace(this.inventory, iInventory));
      } else if ("minecraft:brewing_stand".equals(var5)) {
         this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, iInventory));
      } else if ("minecraft:beacon".equals(var5)) {
         this.mc.displayGuiScreen(new GuiBeacon(this.inventory, iInventory));
      } else if (!"minecraft:dispenser".equals(var5) && !"minecraft:dropper".equals(var5)) {
         this.mc.displayGuiScreen(new GuiChest(this.inventory, iInventory));
      } else {
         this.mc.displayGuiScreen(new GuiDispenser(this.inventory, iInventory));
      }
   }

   @Override
   public void displayGUIHorse(EntityHorse entityHorse, IInventory iInventory) {
      this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, iInventory, entityHorse));
   }

   @Override
   public void displayGui(IInteractionObject iInteractionObject) {
      String var5 = iInteractionObject.getGuiID();
      if ("minecraft:crafting_table".equals(var5)) {
         this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
      } else if ("minecraft:enchanting_table".equals(var5)) {
         this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, iInteractionObject));
      } else if ("minecraft:anvil".equals(var5)) {
         this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
      }
   }

   @Override
   public void displayVillagerTradeGui(IMerchant iMerchant) {
      this.mc.displayGuiScreen(new GuiMerchant(this.inventory, iMerchant, this.worldObj));
   }

   @Override
   public void onCriticalHit(Entity entity) {
      this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
   }

   @Override
   public void onEnchantmentCritical(Entity entity) {
      this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
   }

   @Override
   public boolean isSneaking() {
      boolean var4 = this.movementInput != null && this.movementInput.sneak;
      return var4 && !this.sleeping;
   }

   @Override
   public void updateEntityActionState() {
      super.updateEntityActionState();
      if (this.isCurrentViewEntity()) {
         this.moveStrafing = this.movementInput.moveStrafe;
         this.moveForward = this.movementInput.moveForward;
         this.isJumping = this.movementInput.jump;
         this.prevRenderArmYaw = this.renderArmYaw;
         this.prevRenderArmPitch = this.renderArmPitch;
         this.renderArmPitch = (float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5);
         this.renderArmYaw = (float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5);
      }
   }

   protected boolean isCurrentViewEntity() {
      return this.mc.getRenderViewEntity() == this;
   }

   @Override
   public void onLivingUpdate() {
      if (this.sprintingTicksLeft > 0) {
         this.sprintingTicksLeft--;
         if (this.sprintingTicksLeft == 0) {
            this.setSprinting(false);
         }
      }

      if (this.sprintToggleTimer > 0) {
         this.sprintToggleTimer--;
      }

      this.prevTimeInPortal = this.timeInPortal;
      if (!this.inPortal) {
         if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
            this.timeInPortal += 0.006666667F;
            if (this.timeInPortal > 1.0F) {
               this.timeInPortal = 1.0F;
            }
         } else {
            if (this.timeInPortal > 0.0F) {
               this.timeInPortal -= 0.05F;
            }

            if (this.timeInPortal < 0.0F) {
               this.timeInPortal = 0.0F;
            }
         }
      } else {
         if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
            this.mc.displayGuiScreen(null);
         }

         if (this.timeInPortal == 0.0F) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
         }

         this.timeInPortal += 0.0125F;
         if (this.timeInPortal >= 1.0F) {
            this.timeInPortal = 1.0F;
         }

         this.inPortal = false;
      }

      if (this.timeUntilPortal > 0) {
         this.timeUntilPortal--;
      }

      boolean var5 = this.movementInput.jump;
      boolean var6 = this.movementInput.sneak;
      boolean var8 = this.movementInput.moveForward >= 0.8F;
      this.movementInput.updatePlayerMoveState();
      if (this.isUsingItem() && !this.isRiding()) {
         SlowdownEvent var9 = new SlowdownEvent(SlowdownEvent.Type.ITEM);
         Moon.getInstance().getEventBus().publish(var9);
         if (!var9.isCancelled()) {
            this.movementInput.moveStrafe *= 0.2F;
            this.movementInput.moveForward *= 0.2F;
            this.sprintToggleTimer = 0;
         }
      }
      this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
      this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
      this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
      this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);

      boolean var13 = this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
      float var10 = this.movementInput.moveForward;
      if (this.cP) {
         this.movementInput.moveForward = 0.0F;
      }

      if (this.onGround
         && !var6
         && !var8
         && this.movementInput.moveForward >= 0.8F
         && !this.isSprinting()
         && var13
         && !this.isUsingItem()
         && !this.isPotionActive(Potion.blindness)) {
         if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.sprintToggleTimer = 7;
         } else {
            this.setSprinting(true);
         }
      }

      if (this.cQ == null) {
         this.cQ = Moon.getInstance().getModuleManager().getModule(Sprint.class);
      }

      MoveInputEvent var11 = new MoveInputEvent(this.movementInput.moveForward);
      Moon.getInstance().getEventBus().publish(var11);
      boolean var12 = this.cQ.isEnabled() && this.cQ.getOmni().getValue();
      if (!var12) {
         if (!this.isSprinting()
            && var13
            && var11.getForward() >= 0.8F
            && !this.isUsingItem()
            && !this.isPotionActive(Potion.blindness)
            && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
         }

         if (this.isSprinting() && (var11.getForward() < 0.8F || this.isCollidedHorizontally || !var13)) {
            this.setSprinting(false);
         }
      } else {
         if (!this.isSprinting()
            && var13
            && this.ae()
            && !this.isUsingItem()
            && !this.isPotionActive(Potion.blindness)
            && this.mc.gameSettings.keyBindSprint.isKeyDown()
            && !this.isSneaking()) {
            this.setSprinting(true);
         }

         if (this.isSprinting() && (this.isCollidedHorizontally || !this.ae()) || !var13 || this.isSneaking()) {
            this.setSprinting(false);
         }
      }

      if (this.cP) {
         this.movementInput.moveForward = var10;
         this.cP = false;
      }

      if (this.capabilities.allowFlying) {
         if (this.mc.playerController.isSpectatorMode()) {
            if (!this.capabilities.isFlying) {
               this.capabilities.isFlying = true;
               this.sendPlayerAbilities();
            }
         } else if (!var5 && this.movementInput.jump) {
            if (this.flyToggleTimer == 0) {
               this.flyToggleTimer = 7;
            } else {
               this.capabilities.isFlying = !this.capabilities.isFlying;
               this.sendPlayerAbilities();
               this.flyToggleTimer = 0;
            }
         }
      }

      if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
         if (this.movementInput.sneak) {
            this.motionY = this.motionY - this.capabilities.getFlySpeed() * 3.0F;
         }

         if (this.movementInput.jump) {
            this.motionY = this.motionY + this.capabilities.getFlySpeed() * 3.0F;
         }
      }

      if (this.isRidingHorse()) {
         if (this.horseJumpPowerCounter < 0) {
            this.horseJumpPowerCounter++;
            if (this.horseJumpPowerCounter == 0) {
               this.horseJumpPower = 0.0F;
            }
         }

         if (var5 && !this.movementInput.jump) {
            this.horseJumpPowerCounter = -10;
            this.sendHorseJump();
         } else if (!var5 && this.movementInput.jump) {
            this.horseJumpPowerCounter = 0;
            this.horseJumpPower = 0.0F;
         } else if (var5) {
            this.horseJumpPowerCounter++;
            if (this.horseJumpPowerCounter < 10) {
               this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
            } else {
               this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
            }
         }
      } else {
         this.horseJumpPower = 0.0F;
      }

      super.onLivingUpdate();
      if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
         this.capabilities.isFlying = false;
         this.sendPlayerAbilities();
      }
   }

   public boolean ae() {
      return this.mc.gameSettings.keyBindForward.isKeyDown()
         || this.mc.gameSettings.keyBindLeft.isKeyDown()
         || this.mc.gameSettings.keyBindRight.isKeyDown()
         || this.mc.gameSettings.keyBindBack.isKeyDown();
   }
}
