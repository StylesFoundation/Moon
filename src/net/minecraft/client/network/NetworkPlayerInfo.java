package net.minecraft.client.network;

import com.google.common.base.MoreObjects;
import com.mojang.authlib.GameProfile;
import com.moon.Moon;
import com.moon.events.impl.network.PlayerJoinEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

public class NetworkPlayerInfo {
   private final GameProfile gameProfile;
   private WorldSettings.GameType gameType;
   private int responseTime;
   private boolean playerTexturesLoaded = false;
   private ResourceLocation locationSkin;
   private ResourceLocation locationCape;
   private String skinType;
   private IChatComponent displayName;
   private int field_178873_i = 0;
   private int field_178870_j = 0;
   private long field_178871_k = 0L;
   private long field_178868_l = 0L;
   private long field_178869_m = 0L;

   public NetworkPlayerInfo(GameProfile gameProfile) {
      this.gameProfile = gameProfile;
   }

   public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData addPlayerData) {
      this.gameProfile = addPlayerData.getProfile();
      this.gameType = addPlayerData.getGameMode();
      PlayerJoinEvent var2 = new PlayerJoinEvent(this.gameProfile, addPlayerData.getPing());
      Moon.getInstance().getEventBus().publish(var2);
      this.responseTime = addPlayerData.getPing();
      this.displayName = addPlayerData.getDisplayName();
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public int getResponseTime() {
      return this.responseTime;
   }

   protected void setGameType(WorldSettings.GameType gameType) {
      this.gameType = gameType;
   }

   protected void setResponseTime(int integer) {
      PlayerJoinEvent var2 = new PlayerJoinEvent(this.gameProfile, integer);
      Moon.getInstance().getEventBus().publish(var2);
      this.responseTime = integer;
   }

   public boolean hasLocationSkin() {
      return this.locationSkin != null;
   }

   public String getSkinType() {
      return this.skinType == null ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType;
   }

   public ResourceLocation getLocationSkin() {
      if (this.locationSkin == null) {
         this.loadPlayerTextures();
      }

      return (ResourceLocation)MoreObjects.firstNonNull(this.locationSkin, DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
   }

   public ResourceLocation getLocationCape() {
      if (this.locationCape == null) {
         this.loadPlayerTextures();
      }

      return this.locationCape;
   }

   public ScorePlayerTeam getPlayerTeam() {
      return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
   }

   protected void loadPlayerTextures() {
      synchronized (this) {
         if (!this.playerTexturesLoaded) {
            this.playerTexturesLoaded = true;
            Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.gameProfile, (type, resourceLocation, minecraftProfileTexture) -> {
               switch (type) {
                  case SKIN:
                     this.locationSkin = resourceLocation;
                     this.skinType = minecraftProfileTexture.getMetadata("model");
                     if (this.skinType == null) {
                        this.skinType = "default";
                     }
                     break;
                  case CAPE:
                     this.locationCape = resourceLocation;
               }
            }, true);
         }
      }
   }

   public void setDisplayName(IChatComponent iChatComponent) {
      this.displayName = iChatComponent;
   }

   public IChatComponent getDisplayName() {
      return this.displayName;
   }

   public int func_178835_l() {
      return this.field_178873_i;
   }

   public void func_178836_b(int integer) {
      this.field_178873_i = integer;
   }

   public int func_178860_m() {
      return this.field_178870_j;
   }

   public void func_178857_c(int integer) {
      this.field_178870_j = integer;
   }

   public long func_178847_n() {
      return this.field_178871_k;
   }

   public void func_178846_a(long long1) {
      this.field_178871_k = long1;
   }

   public long func_178858_o() {
      return this.field_178868_l;
   }

   public void func_178844_b(long long1) {
      this.field_178868_l = long1;
   }

   public long func_178855_p() {
      return this.field_178869_m;
   }

   public void func_178843_c(long long1) {
      this.field_178869_m = long1;
   }
}
