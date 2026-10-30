package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityArmorEvent;
import com.moon.module.impl.visual.Glint;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomItems;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
   protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   protected T modelLeggings;
   protected T modelArmor;
   private final RendererLivingEntity<?> renderer;
   private final float e = 1.0F;
   private final float f = 1.0F;
   private final float g = 1.0F;
   private final float h = 1.0F;
   private boolean skipRenderGlint;
   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
   private Glint k;

   public LayerArmorBase(RendererLivingEntity<?> rendererLivingEntity) {
      this.renderer = rendererLivingEntity;
      this.initArmor();
   }

   @Override
   public void doRenderLayer(
      EntityLivingBase entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8
   ) {
      RenderEntityArmorEvent var9 = new RenderEntityArmorEvent(entityLivingBase);
      Moon.getInstance().getEventBus().publish(var9);
      if (!var9.isCancelled()) {
         this.renderLayer(entityLivingBase, float2, float3, float4, float5, float6, float7, float8, 4);
         this.renderLayer(entityLivingBase, float2, float3, float4, float5, float6, float7, float8, 3);
         this.renderLayer(entityLivingBase, float2, float3, float4, float5, float6, float7, float8, 2);
         this.renderLayer(entityLivingBase, float2, float3, float4, float5, float6, float7, float8, 1);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }

   private void renderLayer(
      EntityLivingBase entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8, int integer
   ) {
      ItemStack var12 = this.getCurrentArmor(entityLivingBase, integer);
      if (var12 != null && var12.getItem() instanceof ItemArmor var13) {
         ModelBase var20 = this.getArmorModel(integer);
         var20.setModelAttributes(this.renderer.getMainModel());
         var20.setLivingAnimations(entityLivingBase, float2, float3, float4);
         this.setModelPartVisible((T)var20, integer);
         boolean var15 = this.isSlotForLeggings(integer);
         if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var12, var15 ? 2 : 1, null)) {
            this.renderer.bindTexture(this.getArmorResource(var13, var15));
         }

         switch (var13.getArmorMaterial()) {
            case LEATHER:
               int var16 = var13.getColor(var12);
               float var17 = (var16 >> 16 & 0xFF) / 255.0F;
               float var18 = (var16 >> 8 & 0xFF) / 255.0F;
               float var19 = (var16 & 0xFF) / 255.0F;
               GlStateManager.color(1.0F * var17, 1.0F * var18, 1.0F * var19, 1.0F);
               var20.render(entityLivingBase, float2, float3, float5, float6, float7, float8);
               if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var12, var15 ? 2 : 1, "overlay")) {
                  this.renderer.bindTexture(this.getArmorResource(var13, var15, "overlay"));
               }
            case CHAIN:
            case IRON:
            case GOLD:
            case DIAMOND:
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               var20.render(entityLivingBase, float2, float3, float5, float6, float7, float8);
         }

         if (!this.skipRenderGlint
            && var12.isItemEnchanted()
            && (
               !Config.isCustomItems()
                  || !CustomItems.renderCustomArmorEffect(entityLivingBase, var12, var20, float2, float3, float4, float5, float6, float7, float8)
            )) {
            this.Method365(entityLivingBase, (T)var20, float2, float3, float4, float5, float6, float7, float8);
         }
      }
   }

   public ItemStack getCurrentArmor(EntityLivingBase entityLivingBase, int integer) {
      return entityLivingBase.getCurrentArmor(integer - 1);
   }

   public T getArmorModel(int integer) {
      return this.isSlotForLeggings(integer) ? this.modelLeggings : this.modelArmor;
   }

   private boolean isSlotForLeggings(int integer) {
      return integer == 2;
   }

   private void Method365(
      EntityLivingBase entityLivingBase, T modelBase, float float3, float float4, float float5, float float6, float float7, float float8, float float9
   ) {
      if (Minecraft.getMinecraft().getRenderManager().Method592()) {
         if (!Config.isShaders() || !Shaders.isShadowPass) {
            float var12 = entityLivingBase.ticksExisted + float5;
            this.renderer.bindTexture(ENCHANTED_ITEM_GLINT_RES);
            if (Config.isShaders()) {
               ShadersRender.renderEnchantedGlintBegin();
            }

            GlStateManager.enableBlend();
            GlStateManager.depthFunc(514);
            GlStateManager.depthMask(false);
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
            if (this.k == null) {
               this.k = Moon.getInstance().getModuleManager().getModule(Glint.class);
            }

            for (int var14 = 0; var14 < 2; var14++) {
               GlStateManager.disableLighting();
               GlStateManager.blendFunc(768, 1);
               if (this.k.isEnabled()) {
                  int var15 = this.k.color.getValue();
                  float var16 = (var15 >> 16 & 0xFF) / 255.0F;
                  float var17 = (var15 >> 8 & 0xFF) / 255.0F;
                  float var18 = (var15 & 0xFF) / 255.0F;
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  GlStateManager.color(var16, var17, var18, 1.0F);
               } else {
                  GlStateManager.color(0.38F, 0.19F, 0.608F, 1.0F);
               }

               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.scale(0.33333334F, 0.33333334F, 0.33333334F);
               GlStateManager.rotate(30.0F - var14 * 60.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(0.0F, var12 * (0.001F + var14 * 0.003F) * 20.0F, 0.0F);
               GlStateManager.matrixMode(5888);
               modelBase.render(entityLivingBase, float3, float4, float6, float7, float8, float9);
            }

            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (Config.isShaders()) {
               ShadersRender.renderEnchantedGlintEnd();
            }
         }
      }
   }

   private ResourceLocation getArmorResource(ItemArmor itemArmor, boolean boolean2) {
      return this.getArmorResource(itemArmor, boolean2, null);
   }

   private ResourceLocation getArmorResource(ItemArmor itemArmor, boolean boolean2, String string) {
      String var4 = String.format(
         "textures/models/armor/%s_layer_%d%s.png",
         itemArmor.getArmorMaterial().getName(),
         boolean2 ? 2 : 1,
         string == null ? "" : String.format("_%s", string)
      );
      ResourceLocation var5 = ARMOR_TEXTURE_RES_MAP.get(var4);
      if (var5 == null) {
         var5 = new ResourceLocation(var4);
         ARMOR_TEXTURE_RES_MAP.put(var4, var5);
      }

      return var5;
   }

   protected abstract void initArmor();

   protected abstract void setModelPartVisible(T modelBase, int integer);

   protected T getArmorModelHook(EntityLivingBase entityLivingBase, ItemStack itemStack, int integer, T modelBase) {
      return (T)modelBase;
   }

   public ResourceLocation Method366(Entity entity, ItemStack itemStack, int integer, String string) {
      String var8 = ((ItemArmor)itemStack.getItem()).getArmorMaterial().getName();
      String var9 = "minecraft";
      int var10 = var8.indexOf(58);
      if (var10 != -1) {
         var9 = var8.substring(0, var10);
         var8 = var8.substring(var10 + 1);
      }

      String var11 = String.format(
         "%s:textures/models/armor/%s_layer_%d%s.png", var9, var8, this.isSlotForLeggings(integer) ? 2 : 1, string == null ? "" : String.format("_%s", string)
      );
      ResourceLocation var12 = ARMOR_TEXTURE_RES_MAP.get(var11);
      if (var12 == null) {
         var12 = new ResourceLocation(var11);
         ARMOR_TEXTURE_RES_MAP.put(var11, var12);
      }

      return var12;
   }
}
