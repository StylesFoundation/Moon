package net.optifine.player;

import java.util.Map;
import java.util.Set;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.Config;

public class PlayerItemsLayer implements LayerRenderer {
   private RenderPlayer renderPlayer = null;

   public PlayerItemsLayer(RenderPlayer renderPlayer) {
      this.renderPlayer = renderPlayer;
   }

   @Override
   public void doRenderLayer(
      EntityLivingBase entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8
   ) {
      this.renderEquippedItems(entityLivingBase, float8, float4);
   }

   protected void renderEquippedItems(EntityLivingBase entityLivingBase, float float2, float float3) {
      if (Config.isShowCapes() && entityLivingBase instanceof AbstractClientPlayer var7) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableRescaleNormal();
         GlStateManager.enableCull();
         ModelPlayer var8 = this.renderPlayer.getMainModel();
         PlayerConfigurations.renderPlayerItems(var8, var7, float2, float3);
         GlStateManager.disableCull();
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return false;
   }

   public static void register(Map map) {
      Set var4 = map.keySet();
      boolean var5 = false;

      for (Object var7 : var4) {
         if (map.get(var7) instanceof RenderPlayer var9) {
            var9.addLayer(new PlayerItemsLayer(var9));
            var5 = true;
         }
      }

      if (!var5) {
         Config.Method252("PlayerItemsLayer not registered");
      }
   }
}
