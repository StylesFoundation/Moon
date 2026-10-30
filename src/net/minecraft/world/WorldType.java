package net.minecraft.world;

public class WorldType {
   public static final WorldType[] worldTypes = new WorldType[16];
   public static final WorldType DEFAULT = new WorldType(0, "default", 1).setVersioned();
   public static final WorldType FLAT = new WorldType(1, "flat");
   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
   public static final WorldType AMPLIFIED = new WorldType(3, "amplified").setNotificationData();
   public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
   public static final WorldType DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
   public static final WorldType DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
   private final int worldTypeId;
   private final String worldType;
   private final int generatorVersion;
   private boolean canBeCreated;
   private boolean isWorldTypeVersioned;
   private boolean hasNotificationData;

   private WorldType(int integer, String string) {
      this(integer, string, 0);
   }

   private WorldType(int integer1, String string, int integer3) {
      this.worldType = string;
      this.generatorVersion = integer3;
      this.canBeCreated = true;
      this.worldTypeId = integer1;
      worldTypes[integer1] = this;
   }

   public String getWorldTypeName() {
      return this.worldType;
   }

   public String Method712() {
      return "generator." + this.worldType;
   }

   public String Method713() {
      return this.Method712() + ".info";
   }

   public int getGeneratorVersion() {
      return this.generatorVersion;
   }

   public WorldType getWorldTypeForGeneratorVersion(int integer) {
      return this == DEFAULT && integer == 0 ? DEFAULT_1_1 : this;
   }

   private WorldType setCanBeCreated(boolean boolean1) {
      this.canBeCreated = boolean1;
      return this;
   }

   public boolean getCanBeCreated() {
      return this.canBeCreated;
   }

   private WorldType setVersioned() {
      this.isWorldTypeVersioned = true;
      return this;
   }

   public boolean isVersioned() {
      return this.isWorldTypeVersioned;
   }

   public static WorldType parseWorldType(String string) {
      for (WorldType var7 : worldTypes) {
         if (var7 != null && var7.worldType.equalsIgnoreCase(string)) {
            return var7;
         }
      }

      return null;
   }

   public int getWorldTypeID() {
      return this.worldTypeId;
   }

   public boolean showWorldInfoNotice() {
      return this.hasNotificationData;
   }

   private WorldType setNotificationData() {
      this.hasNotificationData = true;
      return this;
   }
}
