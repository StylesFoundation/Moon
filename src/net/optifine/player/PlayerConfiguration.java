package net.optifine.player;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.src.Config;

public class PlayerConfiguration {
   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
   private boolean initialized = false;

   public void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer abstractClientPlayer, float float3, float float4) {
      if (this.initialized) {
         PlayerItemModel[] var8 = this.playerItemModels;
         int var9 = var8.length;

         for (int var10 = 0; var10 < var9; var10++) {
            var8[var10].render(modelBiped, abstractClientPlayer, float3, float4);
         }
      }
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setInitialized(boolean boolean1) {
      this.initialized = boolean1;
   }

   public PlayerItemModel[] getPlayerItemModels() {
      return this.playerItemModels;
   }

   public void addPlayerItemModel(PlayerItemModel playerItemModel) {
      this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, playerItemModel);
   }
}
