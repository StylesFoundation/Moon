package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.IOUtils;

public class PlayerProfileCache {
   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private final Map<String, PlayerProfileCache.ProfileEntry> usernameToProfileEntryMap = Maps.newHashMap();
   private final Map<UUID, PlayerProfileCache.ProfileEntry> uuidToProfileEntryMap = Maps.newHashMap();
   private final LinkedList<GameProfile> gameProfiles = Lists.newLinkedList();
   private final MinecraftServer mcServer;
   protected final Gson gson;
   private final File usercacheFile;
   private static final ParameterizedType TYPE = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{PlayerProfileCache.ProfileEntry.class};
      }

      @Override
      public Type getRawType() {
         return List.class;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   };

   public PlayerProfileCache(MinecraftServer minecraftServer, File file) {
      this.mcServer = minecraftServer;
      this.usercacheFile = file;
      GsonBuilder var3 = new GsonBuilder();
      var3.registerTypeHierarchyAdapter(PlayerProfileCache.ProfileEntry.class, new PlayerProfileCache.Serializer());
      this.gson = var3.create();
      this.load();
   }

   private static GameProfile getGameProfile(MinecraftServer minecraftServer, String string) {
      final GameProfile[] var5 = new GameProfile[1];
      ProfileLookupCallback var6 = new ProfileLookupCallback() {
         @Override
         public void onProfileLookupSucceeded(GameProfile gameProfile) {
            var5[0] = gameProfile;
         }

         @Override
         public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
            var5[0] = null;
         }
      };
      minecraftServer.getGameProfileRepository().findProfilesByNames(new String[]{string}, Agent.MINECRAFT, var6);
      if (!minecraftServer.isServerInOnlineMode() && var5[0] == null) {
         UUID var7 = EntityPlayer.getUUID(new GameProfile(null, string));
         GameProfile var8 = new GameProfile(var7, string);
         var6.onProfileLookupSucceeded(var8);
      }

      return var5[0];
   }

   public void addEntry(GameProfile gameProfile) {
      this.addEntry(gameProfile, null);
   }

   private void addEntry(GameProfile gameProfile, Date date) {
      UUID var6 = gameProfile.getId();
      if (date == null) {
         Calendar var7 = Calendar.getInstance();
         var7.setTime(new Date());
         var7.add(2, 1);
         date = var7.getTime();
      }

      gameProfile.getName().toLowerCase(Locale.ROOT);
      PlayerProfileCache.ProfileEntry var8 = new PlayerProfileCache.ProfileEntry(gameProfile, date);
      if (this.uuidToProfileEntryMap.containsKey(var6)) {
         PlayerProfileCache.ProfileEntry var9 = this.uuidToProfileEntryMap.get(var6);
         this.usernameToProfileEntryMap.remove(var9.getGameProfile().getName().toLowerCase(Locale.ROOT));
         this.gameProfiles.remove(gameProfile);
      }

      this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), var8);
      this.uuidToProfileEntryMap.put(var6, var8);
      this.gameProfiles.addFirst(gameProfile);
      this.save();
   }

   public GameProfile getGameProfileForUsername(String string) {
      String var5 = string.toLowerCase(Locale.ROOT);
      PlayerProfileCache.ProfileEntry var6 = this.usernameToProfileEntryMap.get(var5);
      if (var6 != null && new Date().getTime() >= var6.expirationDate.getTime()) {
         this.uuidToProfileEntryMap.remove(var6.getGameProfile().getId());
         this.usernameToProfileEntryMap.remove(var6.getGameProfile().getName().toLowerCase(Locale.ROOT));
         this.gameProfiles.remove(var6.getGameProfile());
         var6 = null;
      }

      if (var6 != null) {
         GameProfile var7 = var6.getGameProfile();
         this.gameProfiles.remove(var7);
         this.gameProfiles.addFirst(var7);
      } else {
         GameProfile var8 = getGameProfile(this.mcServer, var5);
         if (var8 != null) {
            this.addEntry(var8);
            var6 = this.usernameToProfileEntryMap.get(var5);
         }
      }

      this.save();
      return var6 == null ? null : var6.getGameProfile();
   }

   public String[] getUsernames() {
      return Lists.newArrayList(this.usernameToProfileEntryMap.keySet()).toArray(new String[0]);
   }

   public GameProfile getProfileByUUID(UUID uUID) {
      PlayerProfileCache.ProfileEntry var5 = this.uuidToProfileEntryMap.get(uUID);
      return var5 == null ? null : var5.getGameProfile();
   }

   private PlayerProfileCache.ProfileEntry getByUUID(UUID uUID) {
      PlayerProfileCache.ProfileEntry var5 = this.uuidToProfileEntryMap.get(uUID);
      if (var5 != null) {
         GameProfile var6 = var5.getGameProfile();
         this.gameProfiles.remove(var6);
         this.gameProfiles.addFirst(var6);
      }

      return var5;
   }

   public void load() {
      BufferedReader var4 = null;

      try {
         var4 = Files.newReader(this.usercacheFile, Charsets.UTF_8);
         List<PlayerProfileCache.ProfileEntry> var5 = (List)this.gson.fromJson(var4, TYPE);
         this.usernameToProfileEntryMap.clear();
         this.uuidToProfileEntryMap.clear();
         this.gameProfiles.clear();

         for (PlayerProfileCache.ProfileEntry var7 : Lists.reverse(var5)) {
            if (var7 != null) {
               this.addEntry(var7.getGameProfile(), var7.getExpirationDate());
            }
         }
      } catch (FileNotFoundException | JsonParseException var11) {
      } finally {
         IOUtils.closeQuietly(var4);
      }
   }

   public void save() {
      String var3 = this.gson.toJson(this.getEntriesWithLimit(1000));
      BufferedWriter var4 = null;

      try {
         var4 = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
         var4.write(var3);
      } catch (IOException var9) {
      } finally {
         IOUtils.closeQuietly(var4);
      }
   }

   private List<PlayerProfileCache.ProfileEntry> getEntriesWithLimit(int integer) {
      ArrayList var5 = Lists.newArrayList();

      for (GameProfile var7 : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), integer))) {
         PlayerProfileCache.ProfileEntry var8 = this.getByUUID(var7.getId());
         if (var8 != null) {
            var5.add(var8);
         }
      }

      return var5;
   }

   static class ProfileEntry {
      private final GameProfile gameProfile;
      private final Date expirationDate;

      private ProfileEntry(GameProfile gameProfile, Date date) {
         this.gameProfile = gameProfile;
         this.expirationDate = date;
      }

      public GameProfile getGameProfile() {
         return this.gameProfile;
      }

      public Date getExpirationDate() {
         return this.expirationDate;
      }
   }

   static class Serializer implements JsonDeserializer<PlayerProfileCache.ProfileEntry>, JsonSerializer<PlayerProfileCache.ProfileEntry> {
      private Serializer() {
      }

      public JsonElement serialize(PlayerProfileCache.ProfileEntry profileEntry, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject var7 = new JsonObject();
         var7.addProperty("name", profileEntry.getGameProfile().getName());
         UUID var8 = profileEntry.getGameProfile().getId();
         var7.addProperty("uuid", var8 == null ? "" : var8.toString());
         var7.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(profileEntry.getExpirationDate()));
         return var7;
      }

      public PlayerProfileCache.ProfileEntry deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonObject()) {
            JsonObject var7 = jsonElement.getAsJsonObject();
            JsonElement var8 = var7.get("name");
            JsonElement var9 = var7.get("uuid");
            JsonElement var10 = var7.get("expiresOn");
            if (var8 != null && var9 != null) {
               String var11 = var9.getAsString();
               String var12 = var8.getAsString();
               Date var13 = null;
               if (var10 != null) {
                  try {
                     var13 = PlayerProfileCache.dateFormat.parse(var10.getAsString());
                  } catch (ParseException var16) {
                     var13 = null;
                  }
               }

               if (var12 != null && var11 != null) {
                  UUID var14;
                  try {
                     var14 = UUID.fromString(var11);
                  } catch (Throwable var15) {
                     return null;
                  }

                  return new PlayerProfileCache.ProfileEntry(new GameProfile(var14, var12), var13);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }
}
