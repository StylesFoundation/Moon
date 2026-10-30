package net.minecraft.client.particle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.moon.util.render.FrustumUtil;
import com.moon.util.render.ParticleCullingUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class EffectRenderer {
   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
   protected World worldObj;
   private final List<EntityFX>[][] fxLayers = new List[4][];
   private final List<EntityParticleEmitter> particleEmitters = Lists.newArrayList();
   private final TextureManager renderer;
   private final Random rand = new Random();
   private final Map<Integer, IParticleFactory> particleTypes = Maps.newHashMap();
   private final List<EntityFX> h = new ArrayList<>();
   private final List<EntityFX> i = new ArrayList<>();

   public EffectRenderer(World world, TextureManager textureManager) {
      this.worldObj = world;
      this.renderer = textureManager;

      for (int var3 = 0; var3 < 4; var3++) {
         this.fxLayers[var3] = new List[2];

         for (int var4 = 0; var4 < 2; var4++) {
            this.fxLayers[var3][var4] = Lists.newArrayList();
         }
      }

      this.registerVanillaParticles();
   }

   private void registerVanillaParticles() {
      this.registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX.Factory());
      this.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX.Factory());
      this.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX.Factory());
      this.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX.Factory());
      this.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX.Factory());
      this.registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX.Factory());
      this.registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX.Factory());
      this.registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX.Factory());
      this.registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX.MagicFactory());
      this.registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX.Factory());
      this.registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX.Factory());
      this.registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX.Factory());
      this.registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX.InstantFactory());
      this.registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX.MobFactory());
      this.registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX.AmbientMobFactory());
      this.registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX.WitchFactory());
      this.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX.WaterFactory());
      this.registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX.LavaFactory());
      this.registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX.AngryVillagerFactory());
      this.registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX.HappyVillagerFactory());
      this.registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX.Factory());
      this.registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX.Factory());
      this.registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX.Factory());
      this.registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
      this.registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX.Factory());
      this.registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX.Factory());
      this.registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX.Factory());
      this.registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX.Factory());
      this.registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX.Factory());
      this.registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX.SnowballFactory());
      this.registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX.Factory());
      this.registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX.SlimeFactory());
      this.registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX.Factory());
      this.registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
      this.registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX.Factory());
      this.registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX.Factory());
      this.registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX.Factory());
      this.registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX.Factory());
      this.registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX.Factory());
      this.registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework.Factory());
      this.registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance.Factory());
   }

   public void registerParticle(int integer, IParticleFactory iParticleFactory) {
      this.particleTypes.put(integer, iParticleFactory);
   }

   public void emitParticleAtEntity(Entity entity, EnumParticleTypes enumParticleTypes) {
      this.particleEmitters.add(new EntityParticleEmitter(this.worldObj, entity, enumParticleTypes));
   }

   public EntityFX spawnEffectParticle(int integer, double double2, double double3, double double4, double double5, double double6, double double7, int... arr) {
      IParticleFactory var15 = this.particleTypes.get(integer);
      if (var15 != null) {
         EntityFX var16 = var15.getEntityFX(integer, this.worldObj, double2, double3, double4, double5, double6, double7, arr);
         if (var16 != null) {
            this.addEffect(var16);
            return var16;
         }
      }

      return null;
   }

   public void addEffect(EntityFX entityFX) {
      if (entityFX != null && (!(entityFX instanceof EntityFirework.SparkFX) || Config.isFireworkParticles())) {
         int var4 = entityFX.getFXLayer();
         int var5 = entityFX.getAlpha() != 1.0F ? 0 : 1;
         if (this.fxLayers[var4][var5].size() >= 4000) {
            this.fxLayers[var4][var5].remove(0);
         }

         this.fxLayers[var4][var5].add(entityFX);
      }
   }

   public void updateEffects() {
      for (int var1 = 0; var1 < 4; var1++) {
         this.updateEffectLayer(var1);
      }

      ArrayList var4 = Lists.newArrayList();

      for (EntityParticleEmitter var3 : this.particleEmitters) {
         var3.onUpdate();
         if (var3.isDead) {
            var4.add(var3);
         }
      }

      this.particleEmitters.removeAll(var4);
   }

   private void updateEffectLayer(int integer) {
      for (int var2 = 0; var2 < 2; var2++) {
         this.updateEffectAlphaLayer(this.fxLayers[integer][var2]);
      }
   }

   private void updateEffectAlphaLayer(List<EntityFX> list) {
      ArrayList var4 = Lists.newArrayList();
      long var5 = System.currentTimeMillis();
      int var7 = list.size();

      for (int var8 = 0; var8 < list.size(); var8++) {
         EntityFX var9 = (EntityFX)list.get(var8);
         this.tickParticle(var9);
         if (var9.isDead) {
            var4.add(var9);
         }

         var7--;
         if (System.currentTimeMillis() > var5 + 20L) {
            break;
         }
      }

      if (var7 > 0) {
         int var10 = var7;

         for (Iterator var11 = list.iterator(); var11.hasNext() && var10 > 0; var10--) {
            ((EntityFX)var11.next()).setDead();
            var11.remove();
         }
      }

      list.removeAll(var4);
   }

   private void tickParticle(EntityFX entityFX) {
      try {
         entityFX.onUpdate();
      } catch (Throwable var6) {
         CrashReport var3 = CrashReport.makeCrashReport(var6, "Ticking Particle");
         CrashReportCategory var4 = var3.makeCategory("Particle being ticked");
         int var5 = entityFX.getFXLayer();
         var4.addCrashSectionCallable("Particle", entityFX::toString);
         var4.addCrashSectionCallable(
            "Particle Type", () -> var5 == 0 ? "MISC_TEXTURE" : (var5 == 1 ? "TERRAIN_TEXTURE" : (var5 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var5))
         );
         throw new ReportedException(var3);
      }
   }

   public void renderParticles(Entity entity, float float2) {
      float var5 = ActiveRenderInfo.Method339();
      float var6 = ActiveRenderInfo.Method341();
      float var7 = ActiveRenderInfo.Method342();
      float var8 = ActiveRenderInfo.Method343();
      float var9 = ActiveRenderInfo.Method340();
      EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float2;
      EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float2;
      EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float2;
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.alphaFunc(516, 0.003921569F);
      Block var10 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.worldObj, entity, float2);
      boolean var11 = var10.getMaterial() == Material.water;
      this.h.clear();
      this.i.clear();

      for (int var12 = 0; var12 < 3; var12++) {
         for (int var13 = 0; var13 < 2; var13++) {
            int var14 = var12;
            if (!this.fxLayers[var12][var13].isEmpty()) {
               switch (var13) {
                  case 0:
                     GlStateManager.depthMask(false);
                     break;
                  case 1:
                     GlStateManager.depthMask(true);
               }

               switch (var12) {
                  case 0:
                  default:
                     this.renderer.bindTexture(particleTextures);
                     break;
                  case 1:
                     this.renderer.bindTexture(TextureMap.locationBlocksTexture);
               }

               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               Tessellator var15 = Tessellator.getInstance();
               WorldRenderer var16 = var15.getWorldRenderer();
               var16.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

               for (int var17 = 0; var17 < this.fxLayers[var12][var13].size(); var17++) {
                  EntityFX var18 = this.fxLayers[var12][var13].get(var17);
                  if (FrustumUtil.isVisible(var18.getEntityBoundingBox())) {
                     try {
                        if ((var11 || !(var18 instanceof EntitySuspendFX)) && ParticleCullingUtil.isParticleVisible(var18)) {
                           var18.renderParticle(var16, entity, float2, var5, var9, var6, var7, var8);
                        }
                     } catch (Throwable var22) {
                        CrashReport var20 = CrashReport.makeCrashReport(var22, "Rendering Particle");
                        CrashReportCategory var21 = var20.makeCategory("Particle being rendered");
                        var21.addCrashSectionCallable("Particle", () -> var18.toString());
                        var21.addCrashSectionCallable(
                           "Particle Type",
                           () -> var14 == 0
                              ? "MISC_TEXTURE"
                              : (var14 == 1 ? "TERRAIN_TEXTURE" : (var14 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var14))
                        );
                        throw new ReportedException(var20);
                     }
                  }
               }

               var15.draw();
            }
         }
      }

      GlStateManager.depthMask(true);
      GlStateManager.disableBlend();
      GlStateManager.alphaFunc(516, 0.1F);
   }

   public void renderLitParticles(Entity entity, float float2) {
      float var4 = MathHelper.cos(entity.rotationYaw * (float) (Math.PI / 180.0));
      float var5 = MathHelper.sin(entity.rotationYaw * (float) (Math.PI / 180.0));
      float var6 = -var5 * MathHelper.sin(entity.rotationPitch * (float) (Math.PI / 180.0));
      float var7 = var4 * MathHelper.sin(entity.rotationPitch * (float) (Math.PI / 180.0));
      float var8 = MathHelper.cos(entity.rotationPitch * (float) (Math.PI / 180.0));

      for (int var9 = 0; var9 < 2; var9++) {
         List<EntityFX> var10 = this.fxLayers[3][var9];
         if (!var10.isEmpty()) {
            WorldRenderer var12 = Tessellator.getInstance().getWorldRenderer();

            for (EntityFX var14 : var10) {
               var14.renderParticle(var12, entity, float2, var4, var8, var5, var6, var7);
            }
         }
      }
   }

   public void clearEffects(World world) {
      this.worldObj = world;

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 2; var3++) {
            this.fxLayers[var2][var3].clear();
         }
      }

      this.particleEmitters.clear();
   }

   public void addBlockDestroyEffects(BlockPos blockPos, IBlockState iBlockState) {
      if (iBlockState.getBlock().getMaterial() != Material.air) {
         iBlockState = iBlockState.getBlock().getActualState(iBlockState, this.worldObj, blockPos);

         for (int var5 = 0; var5 < 4; var5++) {
            for (int var6 = 0; var6 < 4; var6++) {
               for (int var7 = 0; var7 < 4; var7++) {
                  double var8 = blockPos.getX() + (var5 + 0.5) / 4.0;
                  double var10 = blockPos.getY() + (var6 + 0.5) / 4.0;
                  double var12 = blockPos.getZ() + (var7 + 0.5) / 4.0;
                  this.addEffect(
                     new EntityDiggingFX(
                           this.worldObj,
                           var8,
                           var10,
                           var12,
                           var8 - blockPos.getX() - 0.5,
                           var10 - blockPos.getY() - 0.5,
                           var12 - blockPos.getZ() - 0.5,
                           iBlockState
                        )
                        .setBlockPos(blockPos)
                  );
               }
            }
         }
      }
   }

   public void addBlockHitEffects(BlockPos blockPos, EnumFacing enumFacing) {
      IBlockState var3 = this.worldObj.getBlockState(blockPos);
      Block var4 = var3.getBlock();
      if (var4.getRenderType() != -1) {
         int var5 = blockPos.getX();
         int var6 = blockPos.getY();
         int var7 = blockPos.getZ();
         double var9 = var5 + this.rand.nextDouble() * (var4.getBlockBoundsMaxX() - var4.getBlockBoundsMinX() - 0.2F) + 0.1F + var4.getBlockBoundsMinX();
         double var11 = var6 + this.rand.nextDouble() * (var4.getBlockBoundsMaxY() - var4.getBlockBoundsMinY() - 0.2F) + 0.1F + var4.getBlockBoundsMinY();
         double var13 = var7 + this.rand.nextDouble() * (var4.getBlockBoundsMaxZ() - var4.getBlockBoundsMinZ() - 0.2F) + 0.1F + var4.getBlockBoundsMinZ();
         if (enumFacing == EnumFacing.DOWN) {
            var11 = var6 + var4.getBlockBoundsMinY() - 0.1F;
         }

         if (enumFacing == EnumFacing.UP) {
            var11 = var6 + var4.getBlockBoundsMaxY() + 0.1F;
         }

         if (enumFacing == EnumFacing.NORTH) {
            var13 = var7 + var4.getBlockBoundsMinZ() - 0.1F;
         }

         if (enumFacing == EnumFacing.SOUTH) {
            var13 = var7 + var4.getBlockBoundsMaxZ() + 0.1F;
         }

         if (enumFacing == EnumFacing.WEST) {
            var9 = var5 + var4.getBlockBoundsMinX() - 0.1F;
         }

         if (enumFacing == EnumFacing.EAST) {
            var9 = var5 + var4.getBlockBoundsMaxX() + 0.1F;
         }

         this.addEffect(
            new EntityDiggingFX(this.worldObj, var9, var11, var13, 0.0, 0.0, 0.0, var3)
               .setBlockPos(blockPos)
               .multiplyVelocity(0.2F)
               .multipleParticleScaleBy(0.6F)
         );
      }
   }

   public void moveToAlphaLayer(EntityFX entityFX) {
      this.moveToLayer(entityFX, 1, 0);
   }

   public void moveToNoAlphaLayer(EntityFX entityFX) {
      this.moveToLayer(entityFX, 0, 1);
   }

   private void moveToLayer(EntityFX entityFX, int integer2, int integer3) {
      for (int var4 = 0; var4 < 4; var4++) {
         if (this.fxLayers[var4][integer2].contains(entityFX)) {
            this.fxLayers[var4][integer2].remove(entityFX);
            this.fxLayers[var4][integer3].add(entityFX);
         }
      }
   }

   public String getStatistics() {
      int var1 = 0;

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 2; var3++) {
            var1 += this.fxLayers[var2][var3].size();
         }
      }

      return var1 + "";
   }
}
