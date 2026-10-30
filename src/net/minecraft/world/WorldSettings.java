package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {
   private final long seed;
   private final WorldSettings.GameType theGameType;
   private final boolean mapFeaturesEnabled;
   private final boolean hardcoreEnabled;
   private final WorldType terrainType;
   private boolean commandsAllowed;
   private boolean bonusChestEnabled;
   private String worldName = "";

   public WorldSettings(long long1, WorldSettings.GameType gameType, boolean boolean3, boolean boolean4, WorldType worldType) {
      this.seed = long1;
      this.theGameType = gameType;
      this.mapFeaturesEnabled = boolean3;
      this.hardcoreEnabled = boolean4;
      this.terrainType = worldType;
   }

   public WorldSettings(WorldInfo worldInfo) {
      this(worldInfo.getSeed(), worldInfo.getGameType(), worldInfo.isMapFeaturesEnabled(), worldInfo.isHardcoreModeEnabled(), worldInfo.getTerrainType());
   }

   public WorldSettings enableBonusChest() {
      this.bonusChestEnabled = true;
      return this;
   }

   public WorldSettings enableCommands() {
      this.commandsAllowed = true;
      return this;
   }

   public WorldSettings setWorldName(String string) {
      this.worldName = string;
      return this;
   }

   public boolean isBonusChestEnabled() {
      return this.bonusChestEnabled;
   }

   public long getSeed() {
      return this.seed;
   }

   public WorldSettings.GameType getGameType() {
      return this.theGameType;
   }

   public boolean getHardcoreEnabled() {
      return this.hardcoreEnabled;
   }

   public boolean isMapFeaturesEnabled() {
      return this.mapFeaturesEnabled;
   }

   public WorldType getTerrainType() {
      return this.terrainType;
   }

   public boolean areCommandsAllowed() {
      return this.commandsAllowed;
   }

   public static WorldSettings.GameType getGameTypeById(int integer) {
      return WorldSettings.GameType.getByID(integer);
   }

   public String getWorldName() {
      return this.worldName;
   }

   public enum GameType {
      NOT_SET(-1, ""),
      SURVIVAL(0, "survival"),
      CREATIVE(1, "creative"),
      ADVENTURE(2, "adventure"),
      SPECTATOR(3, "spectator");

      int id;
      String name;

      GameType(int integer3, String string4) {
         this.id = integer3;
         this.name = string4;
      }

      public int getID() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public void configurePlayerCapabilities(PlayerCapabilities playerCapabilities) {
         if (this == CREATIVE) {
            playerCapabilities.allowFlying = true;
            playerCapabilities.isCreativeMode = true;
            playerCapabilities.disableDamage = true;
         } else if (this == SPECTATOR) {
            playerCapabilities.allowFlying = true;
            playerCapabilities.isCreativeMode = false;
            playerCapabilities.disableDamage = true;
            playerCapabilities.isFlying = true;
         } else {
            playerCapabilities.allowFlying = false;
            playerCapabilities.isCreativeMode = false;
            playerCapabilities.disableDamage = false;
            playerCapabilities.isFlying = false;
         }

         playerCapabilities.allowEdit = !this.isAdventure();
      }

      public boolean isAdventure() {
         return this == ADVENTURE || this == SPECTATOR;
      }

      public boolean isCreative() {
         return this == CREATIVE;
      }

      public boolean isSurvivalOrAdventure() {
         return this == SURVIVAL || this == ADVENTURE;
      }

      public static WorldSettings.GameType getByID(int integer) {
         for (WorldSettings.GameType var7 : values()) {
            if (var7.id == integer) {
               return var7;
            }
         }

         return SURVIVAL;
      }

      public static WorldSettings.GameType getByName(String string) {
         for (WorldSettings.GameType var7 : values()) {
            if (var7.name.equals(string)) {
               return var7;
            }
         }

         return SURVIVAL;
      }

      private static WorldSettings.GameType[] Method630() {
         return new WorldSettings.GameType[]{NOT_SET, SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR};
      }
   }
}
