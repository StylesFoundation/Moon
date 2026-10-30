package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.player.PlayerItemsLayer;
import net.optifine.shaders.Shaders;

public class RenderManager {
   private final Map<String, RenderPlayer> skinMap = Maps.newHashMap();
   private final RenderPlayer playerRenderer;
   public TextureManager renderEngine;
   public World worldObj;
   public Entity livingPlayer;
   public Entity pointedEntity;
   public float playerViewY;
   public float playerViewX;
   public GameSettings options;
   public double viewerPosX;
   public double viewerPosY;
   public double viewerPosZ;
   public Render renderRender = null;
   private Map<Class, Render> entityRenderMap = Maps.newHashMap();
   private FontRenderer textRenderer;
   private double renderPosX;
   private double renderPosY;
   private double renderPosZ;
   private boolean renderOutlines = false;
   private boolean renderShadow = true;
   private boolean debugBoundingBox = false;
   private boolean v = false;
   private boolean w = true;
   private boolean x = true;
   private boolean y = true;
   private boolean z = false;

   public RenderManager(TextureManager textureManager, RenderItem renderItem) {
      this.renderEngine = textureManager;
      this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
      this.entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
      this.entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7F));
      this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7F));
      this.entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7F));
      this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7F));
      this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5F));
      this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3F));
      this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4F));
      this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3F));
      this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
      this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
      this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
      this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
      this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
      this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
      this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
      this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
      this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
      this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
      this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25F));
      this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
      this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5F, 6.0F));
      this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
      this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7F));
      this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
      this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
      this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
      this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
      this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
      this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
      this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
      this.entityRenderMap.put(Entity.class, new RenderEntity(this));
      this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
      this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, renderItem));
      this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
      this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
      this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(this, Items.snowball, renderItem));
      this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ender_pearl, renderItem));
      this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(this, Items.ender_eye, renderItem));
      this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(this, Items.egg, renderItem));
      this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, renderItem));
      this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(this, Items.experience_bottle, renderItem));
      this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.fireworks, renderItem));
      this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
      this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
      this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
      this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, renderItem));
      this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
      this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
      this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
      this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
      this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
      this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
      this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
      this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
      this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
      this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
      this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
      this.playerRenderer = new RenderPlayer(this);
      this.skinMap.put("default", this.playerRenderer);
      this.skinMap.put("slim", new RenderPlayer(this, true));
      PlayerItemsLayer.register(this.skinMap);
   }

   public void setRenderPosition(double double1, double double2, double double3) {
      this.renderPosX = double1;
      this.renderPosY = double2;
      this.renderPosZ = double3;
   }

   public <T extends Entity> Render<T> getEntityClassRenderObject(Class<? extends Entity> class1) {
      Render var2 = this.entityRenderMap.get(class1);
      if (var2 == null && class1 != Entity.class) {
         var2 = this.getEntityClassRenderObject((Class<? extends Entity>)class1.getSuperclass());
         this.entityRenderMap.put(class1, var2);
      }

      return (Render<T>)var2;
   }

   public <T extends Entity> Render<T> getEntityRenderObject(Entity entity) {
      if (entity instanceof AbstractClientPlayer) {
         String var2 = ((AbstractClientPlayer)entity).getSkinType();
         RenderPlayer var3 = this.skinMap.get(var2);
         return (Render<T>)(var3 != null ? var3 : this.playerRenderer);
      } else {
         return this.getEntityClassRenderObject((Class<? extends Entity>)entity.getClass());
      }
   }

   public void cacheActiveRenderInfo(World world, FontRenderer fontRenderer, Entity entity3, Entity entity4, GameSettings gameSettings, float float6) {
      this.worldObj = world;
      this.options = gameSettings;
      this.livingPlayer = entity3;
      this.pointedEntity = entity4;
      this.textRenderer = fontRenderer;
      if (entity3 instanceof EntityLivingBase && ((EntityLivingBase)entity3).isPlayerSleeping()) {
         IBlockState var9 = world.getBlockState(new BlockPos(entity3));
         if (var9.getBlock() == Blocks.bed) {
            int var11 = var9.getValue(BlockBed.FACING).getHorizontalIndex();
            this.playerViewY = var11 * 90 + 180;
            this.playerViewX = 0.0F;
         }
      } else {
         this.playerViewY = entity3.prevRotationYaw + (entity3.rotationYaw - entity3.prevRotationYaw) * float6;
         this.playerViewX = entity3.prevRotationPitch + (entity3.rotationPitch - entity3.prevRotationPitch) * float6;
      }

      if (gameSettings.thirdPersonView == 2) {
         this.playerViewY += 180.0F;
      }

      this.viewerPosX = entity3.lastTickPosX + (entity3.posX - entity3.lastTickPosX) * float6;
      this.viewerPosY = entity3.lastTickPosY + (entity3.posY - entity3.lastTickPosY) * float6;
      this.viewerPosZ = entity3.lastTickPosZ + (entity3.posZ - entity3.lastTickPosZ) * float6;
   }

   public void setPlayerViewY(float float1) {
      this.playerViewY = float1;
   }

   public boolean isRenderShadow() {
      return this.renderShadow;
   }

   public void setRenderShadow(boolean boolean1) {
      this.renderShadow = boolean1;
   }

   public boolean isDebugBoundingBox() {
      return this.debugBoundingBox;
   }

   public void setDebugBoundingBox(boolean boolean1) {
      this.debugBoundingBox = boolean1;
   }

   public boolean renderEntitySimple(Entity entity, float float2) {
      return this.renderEntityStatic(entity, float2, false);
   }

   public boolean shouldRender(Entity entity, ICamera iCamera, double double3, double double4, double double5) {
      Render var9 = this.getEntityRenderObject(entity);
      return var9 != null && var9.shouldRender(entity, iCamera, double3, double4, double5);
   }

   public boolean renderEntityStatic(Entity entity, float float2, boolean boolean3) {
      if (entity.ticksExisted == 0) {
         entity.lastTickPosX = entity.posX;
         entity.lastTickPosY = entity.posY;
         entity.lastTickPosZ = entity.posZ;
      }

      double var6 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float2;
      double var8 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float2;
      double var10 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float2;
      float var12 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * float2;
      int var13 = entity.getBrightnessForRender(float2);
      if (entity.isBurning()) {
         var13 = 15728880;
      }

      int var14 = var13 % 65536;
      int var15 = var13 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var14, var15);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      return this.doRenderEntity(entity, var6 - this.renderPosX, var8 - this.renderPosY, var10 - this.renderPosZ, var12, float2, boolean3);
   }

   public void renderWitherSkull(Entity entity, float float2) {
      double var5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float2;
      double var7 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float2;
      double var9 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float2;
      Render var11 = this.getEntityRenderObject(entity);
      if (var11 != null && this.renderEngine != null) {
         int var12 = entity.getBrightnessForRender(float2);
         int var13 = var12 % 65536;
         int var14 = var12 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var13, var14);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         var11.renderName(entity, var5 - this.renderPosX, var7 - this.renderPosY, var9 - this.renderPosZ);
      }
   }

   public boolean renderEntityWithPosYaw(Entity entity, double double2, double double3, double double4, float float5, float float6) {
      return this.doRenderEntity(entity, double2, double3, double4, float5, float6, false);
   }

   public boolean doRenderEntity(Entity entity, double double2, double double3, double double4, float float5, float float6, boolean boolean7) {
      Render var11 = null;

      try {
         var11 = this.getEntityRenderObject(entity);
         if (var11 != null && this.renderEngine != null) {
            try {
               if (var11 instanceof RendererLivingEntity) {
                  ((RendererLivingEntity)var11).setRenderOutlines(this.renderOutlines);
               }

               if (CustomEntityModels.isActive()) {
                  this.renderRender = var11;
               }

               var11.doRender(entity, double2, double3, double4, float5, float6);
            } catch (Throwable var18) {
               throw new ReportedException(CrashReport.makeCrashReport(var18, "Rendering entity in world"));
            }

            try {
               if (!this.renderOutlines && this.x) {
                  var11.doRenderShadowAndFire(entity, double2, double3, double4, float5, float6);
               }
            } catch (Throwable var17) {
               throw new ReportedException(CrashReport.makeCrashReport(var17, "Post-rendering entity in world"));
            }

            if (this.debugBoundingBox && !entity.isInvisible() && !boolean7 && !this.v) {
               try {
                  this.renderDebugBoundingBox(entity, double2, double3, double4, float5, float6);
               } catch (Throwable var16) {
                  throw new ReportedException(CrashReport.makeCrashReport(var16, "Rendering entity hitbox in world"));
               }
            }

            return true;
         } else {
            return this.renderEngine == null;
         }
      } catch (Throwable var19) {
         CrashReport var13 = CrashReport.makeCrashReport(var19, "Rendering entity in world");
         CrashReportCategory var14 = var13.makeCategory("Entity being rendered");
         entity.addEntityCrashInfo(var14);
         CrashReportCategory var15 = var13.makeCategory("Renderer details");
         var15.addCrashSection("Assigned renderer", var11);
         var15.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(double2, double3, double4));
         var15.addCrashSection("Rotation", float5);
         var15.addCrashSection("Delta", float6);
         throw new ReportedException(var13);
      }
   }

   private void renderDebugBoundingBox(Entity entity, double double2, double double3, double double4, float float5, float float6) {
      if (!Shaders.isShadowPass) {
         GlStateManager.depthMask(false);
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.disableCull();
         GlStateManager.disableBlend();
         float var12 = entity.width / 2.0F;
         AxisAlignedBB var13 = entity.getEntityBoundingBox();
         RenderGlobal.drawOutlinedBoundingBox(
            new AxisAlignedBB(
               var13.minX - entity.posX + double2,
               var13.minY - entity.posY + double3,
               var13.minZ - entity.posZ + double4,
               var13.maxX - entity.posX + double2,
               var13.maxY - entity.posY + double3,
               var13.maxZ - entity.posZ + double4
            ),
            255,
            255,
            255,
            255
         );
         if (entity instanceof EntityLivingBase) {
            RenderGlobal.drawOutlinedBoundingBox(
               new AxisAlignedBB(
                  double2 - var12,
                  double3 + entity.getEyeHeight() - 0.01F,
                  double4 - var12,
                  double2 + var12,
                  double3 + entity.getEyeHeight() + 0.01F,
                  double4 + var12
               ),
               255,
               0,
               0,
               255
            );
         }

         Tessellator var15 = Tessellator.getInstance();
         WorldRenderer var16 = var15.getWorldRenderer();
         Vec3 var17 = entity.getLook(float6);
         var16.begin(3, DefaultVertexFormats.POSITION_COLOR);
         var16.pos(double2, double3 + entity.getEyeHeight(), double4).color(0, 0, 255, 255).endVertex();
         var16.pos(double2 + var17.xCoord * 2.0, double3 + entity.getEyeHeight() + var17.yCoord * 2.0, double4 + var17.zCoord * 2.0)
            .color(0, 0, 255, 255)
            .endVertex();
         var15.draw();
         GlStateManager.enableTexture2D();
         GlStateManager.enableLighting();
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
      }
   }

   public void set(World world) {
      this.worldObj = world;
   }

   public double getDistanceToCamera(double double1, double double2, double double3) {
      double var7 = double1 - this.viewerPosX;
      double var9 = double2 - this.viewerPosY;
      double var11 = double3 - this.viewerPosZ;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public FontRenderer getFontRenderer() {
      return this.textRenderer;
   }

   public void setRenderOutlines(boolean boolean1) {
      this.renderOutlines = boolean1;
   }

   public Map<Class, Render> getEntityRenderMap() {
      return this.entityRenderMap;
   }

   public void setEntityRenderMap(Map map) {
      this.entityRenderMap = map;
   }

   public Map<String, RenderPlayer> getSkinMap() {
      return Collections.unmodifiableMap(this.skinMap);
   }

   public double Method588() {
      return this.renderPosX;
   }

   public double Method589() {
      return this.renderPosY;
   }

   public double Method590() {
      return this.renderPosZ;
   }

   public boolean Method591() {
      return this.v;
   }

   public boolean Method592() {
      return this.w;
   }

   public boolean Method593() {
      return this.x;
   }

   public boolean Method594() {
      return this.y;
   }

   public boolean Method595() {
      return this.z;
   }

   public RenderManager Method596(boolean boolean1) {
      this.v = boolean1;
      return this;
   }

   public RenderManager Method597(boolean boolean1) {
      this.w = boolean1;
      return this;
   }

   public RenderManager Method598(boolean boolean1) {
      this.x = boolean1;
      return this;
   }

   public RenderManager Method599(boolean boolean1) {
      this.y = boolean1;
      return this;
   }

   public RenderManager Method600(boolean boolean1) {
      this.z = boolean1;
      return this;
   }
}
