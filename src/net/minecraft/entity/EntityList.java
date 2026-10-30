package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
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
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityList {
   private static final Logger logger = LogManager.getLogger();
   private static final Map<String, Class<? extends Entity>> stringToClassMapping = Maps.newHashMap();
   private static final Map<Class<? extends Entity>, String> classToStringMapping = Maps.newHashMap();
   private static final Map<Integer, Class<? extends Entity>> idToClassMapping = Maps.newHashMap();
   private static final Map<Class<? extends Entity>, Integer> classToIDMapping = Maps.newHashMap();
   private static final Map<String, Integer> stringToIDMapping = Maps.newHashMap();
   public static final Map<Integer, EntityList.EntityEggInfo> entityEggs = Maps.newLinkedHashMap();

   private static void Method511(Class<? extends Entity> class1, String string, int integer) {
      if (stringToClassMapping.containsKey(string)) {
         throw new IllegalArgumentException("ID is already registered: " + string);
      }

      if (idToClassMapping.containsKey(integer)) {
         throw new IllegalArgumentException("ID is already registered: " + integer);
      }

      if (integer == 0) {
         throw new IllegalArgumentException("Cannot register to reserved id: " + integer);
      }

      if (class1 == null) {
         throw new IllegalArgumentException("Cannot register null clazz for id: " + integer);
      }

      stringToClassMapping.put(string, class1);
      classToStringMapping.put(class1, string);
      idToClassMapping.put(integer, class1);
      classToIDMapping.put(class1, integer);
      stringToIDMapping.put(string, integer);
   }

   private static void addMapping(Class<? extends Entity> class1, String string, int integer3, int integer4, int integer5) {
      Method511(class1, string, integer3);
      entityEggs.put(integer3, new EntityList.EntityEggInfo(integer3, integer4, integer5));
   }

   public static Entity createEntityByName(String string, World world) {
      Entity var5 = null;

      try {
         Class var6 = stringToClassMapping.get(string);
         if (var6 != null) {
            var5 = (Entity)var6.getConstructor(World.class).newInstance(world);
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return var5;
   }

   public static Entity createEntityFromNBT(NBTTagCompound nBTTagCompound, World world) {
      Entity var5 = null;
      if ("Minecart".equals(nBTTagCompound.getString("id"))) {
         nBTTagCompound.setString("id", EntityMinecart.EnumMinecartType.byNetworkID(nBTTagCompound.getInteger("Type")).getName());
         nBTTagCompound.removeTag("Type");
      }

      try {
         Class var6 = stringToClassMapping.get(nBTTagCompound.getString("id"));
         if (var6 != null) {
            var5 = (Entity)var6.getConstructor(World.class).newInstance(world);
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      if (var5 != null) {
         var5.readFromNBT(nBTTagCompound);
      } else {
         logger.warn("Skipping Entity with id " + nBTTagCompound.getString("id"));
      }

      return var5;
   }

   public static Entity Method512(int integer, World world) {
      Entity var5 = null;

      try {
         Class var6 = getClassFromID(integer);
         if (var6 != null) {
            var5 = (Entity)var6.getConstructor(World.class).newInstance(world);
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      if (var5 == null) {
         logger.warn("Skipping Entity with id " + integer);
      }

      return var5;
   }

   public static int getEntityID(Entity entity) {
      Integer var4 = classToIDMapping.get(entity.getClass());
      return var4 == null ? 0 : var4;
   }

   public static Class<? extends Entity> getClassFromID(int integer) {
      return idToClassMapping.get(integer);
   }

   public static String getEntityString(Entity entity) {
      return classToStringMapping.get(entity.getClass());
   }

   public static int getIDFromString(String string) {
      Integer var4 = stringToIDMapping.get(string);
      return var4 == null ? 90 : var4;
   }

   public static String getStringFromID(int integer) {
      return classToStringMapping.get(getClassFromID(integer));
   }

   public static void func_151514_a() {
   }

   public static List<String> getEntityNameList() {
      Set<String> var3 = stringToClassMapping.keySet();
      ArrayList<String> var4 = Lists.newArrayList();

      for (String var6 : var3) {
         Class var7 = stringToClassMapping.get(var6);
         if ((var7.getModifiers() & 1024) != 1024) {
            var4.add(var6);
         }
      }

      var4.add("LightningBolt");
      return var4;
   }

   public static boolean isStringEntityName(Entity entity, String string) {
      String var5 = getEntityString(entity);
      if (var5 == null && entity instanceof EntityPlayer) {
         var5 = "Player";
      } else if (var5 == null && entity instanceof EntityLightningBolt) {
         var5 = "LightningBolt";
      }

      return string.equals(var5);
   }

   public static boolean isStringValidEntityName(String string) {
      return "Player".equals(string) || getEntityNameList().contains(string);
   }

   static {
      Method511(EntityItem.class, "Item", 1);
      Method511(EntityXPOrb.class, "XPOrb", 2);
      Method511(EntityEgg.class, "ThrownEgg", 7);
      Method511(EntityLeashKnot.class, "LeashKnot", 8);
      Method511(EntityPainting.class, "Painting", 9);
      Method511(EntityArrow.class, "Arrow", 10);
      Method511(EntitySnowball.class, "Snowball", 11);
      Method511(EntityLargeFireball.class, "Fireball", 12);
      Method511(EntitySmallFireball.class, "SmallFireball", 13);
      Method511(EntityEnderPearl.class, "ThrownEnderpearl", 14);
      Method511(EntityEnderEye.class, "EyeOfEnderSignal", 15);
      Method511(EntityPotion.class, "ThrownPotion", 16);
      Method511(EntityExpBottle.class, "ThrownExpBottle", 17);
      Method511(EntityItemFrame.class, "ItemFrame", 18);
      Method511(EntityWitherSkull.class, "WitherSkull", 19);
      Method511(EntityTNTPrimed.class, "PrimedTnt", 20);
      Method511(EntityFallingBlock.class, "FallingSand", 21);
      Method511(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
      Method511(EntityArmorStand.class, "ArmorStand", 30);
      Method511(EntityBoat.class, "Boat", 41);
      Method511(EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.getName(), 42);
      Method511(EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.getName(), 43);
      Method511(EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.getName(), 44);
      Method511(EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.getName(), 45);
      Method511(EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.getName(), 46);
      Method511(EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.getName(), 47);
      Method511(EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.getName(), 40);
      Method511(EntityLiving.class, "Mob", 48);
      Method511(EntityMob.class, "Monster", 49);
      addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
      addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
      addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
      Method511(EntityGiantZombie.class, "Giant", 53);
      addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
      addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
      addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
      addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
      addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
      addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
      addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
      addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
      addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
      Method511(EntityDragon.class, "EnderDragon", 63);
      Method511(EntityWither.class, "WitherBoss", 64);
      addMapping(EntityBat.class, "Bat", 65, 4996656, 986895);
      addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
      addMapping(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
      addMapping(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
      addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
      addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
      addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
      addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
      addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
      addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
      addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
      Method511(EntitySnowman.class, "SnowMan", 97);
      addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
      Method511(EntityIronGolem.class, "VillagerGolem", 99);
      addMapping(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
      addMapping(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
      addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
      Method511(EntityEnderCrystal.class, "EnderCrystal", 200);
   }

   public static class EntityEggInfo {
      public final int spawnedID;
      public final int primaryColor;
      public final int secondaryColor;
      public final StatBase field_151512_d;
      public final StatBase field_151513_e;

      public EntityEggInfo(int integer1, int integer2, int integer3) {
         this.spawnedID = integer1;
         this.primaryColor = integer2;
         this.secondaryColor = integer3;
         this.field_151512_d = StatList.getStatKillEntity(this);
         this.field_151513_e = StatList.getStatEntityKilledBy(this);
      }
   }
}
