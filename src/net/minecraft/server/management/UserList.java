package net.minecraft.server.management;

import com.google.common.base.Charsets;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList<K, V extends UserListEntry<K>> {
   protected static final Logger logger = LogManager.getLogger();
   protected final Gson gson;
   private final File saveFile;
   private final Map<String, V> values = Maps.newHashMap();
   private boolean lanServer = true;
   private static final ParameterizedType saveFileFormat = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{UserListEntry.class};
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

   public UserList(File file) {
      this.saveFile = file;
      GsonBuilder var2 = new GsonBuilder().setPrettyPrinting();
      var2.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer());
      this.gson = var2.create();
   }

   public boolean isLanServer() {
      return this.lanServer;
   }

   public void setLanServer(boolean boolean1) {
      this.lanServer = boolean1;
   }

   public void addEntry(V userListEntry) {
      this.values.put(this.getObjectKey((K)userListEntry.getValue()), (V)userListEntry);

      try {
         this.writeChanges();
      } catch (IOException var3) {
         logger.warn("Could not save the list after adding a user.", var3);
      }
   }

   public V getEntry(K object) {
      this.removeExpired();
      return this.values.get(this.getObjectKey((K)object));
   }

   public void removeEntry(K object) {
      this.values.remove(this.getObjectKey((K)object));

      try {
         this.writeChanges();
      } catch (IOException var3) {
         logger.warn("Could not save the list after removing a user.", var3);
      }
   }

   public String[] getKeys() {
      return this.values.keySet().toArray(new String[0]);
   }

   protected String getObjectKey(K object) {
      return object.toString();
   }

   protected boolean hasEntry(K object) {
      return this.values.containsKey(this.getObjectKey((K)object));
   }

   private void removeExpired() {
      ArrayList var4 = Lists.newArrayList();

      for (UserListEntry var6 : this.values.values()) {
         if (var6.hasBanExpired()) {
            var4.add(var6.getValue());
         }
      }

      for (Object var8 : var4) {
         this.values.remove(var8);
      }
   }

   protected UserListEntry<K> createEntry(JsonObject jsonObject) {
      return new UserListEntry<>(null, jsonObject);
   }

   protected Map<String, V> getValues() {
      return this.values;
   }

   public void writeChanges() throws IOException {
      Collection var1 = this.values.values();
      String var2 = this.gson.toJson(var1);
      BufferedWriter var3 = null;

      try {
         var3 = Files.newWriter(this.saveFile, Charsets.UTF_8);
         var3.write(var2);
      } finally {
         IOUtils.closeQuietly(var3);
      }
   }

   class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>> {
      private Serializer() {
      }

      public JsonElement serialize(UserListEntry<K> userListEntry, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject var4 = new JsonObject();
         userListEntry.onSerialization(var4);
         return var4;
      }

      public UserListEntry<K> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonObject()) {
            JsonObject var7 = jsonElement.getAsJsonObject();
            return UserList.this.createEntry(var7);
         } else {
            return null;
         }
      }
   }
}
