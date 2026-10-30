package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
   private static final Logger logger = LogManager.getLogger();
   private final Map<String, File> resourceMap = Maps.newHashMap();

   public ResourceIndex(File file, String string) {
      if (string != null) {
         File var6 = new File(file, "objects");
         File var7 = new File(file, "indexes/" + string + ".json");
         BufferedReader var8 = null;

         try {
            var8 = Files.newReader(var7, Charsets.UTF_8);
            JsonObject var10 = JsonUtils.getJsonObject(JsonParser.parseReader(var8).getAsJsonObject(), "objects", null);
            if (var10 != null) {
               for (Entry var12 : var10.entrySet()) {
                  JsonObject var13 = (JsonObject)var12.getValue();
                  String[] var15 = ((String)var12.getKey()).split("/", 2);
                  String var16 = var15.length == 1 ? var15[0] : var15[0] + ":" + var15[1];
                  String var17 = JsonUtils.Method741(var13, "hash");
                  File var18 = new File(var6, var17.substring(0, 2) + "/" + var17);
                  this.resourceMap.put(var16, var18);
               }
            }
         } catch (JsonParseException var23) {
            logger.error("Unable to parse resource index file: " + var7);
         } catch (FileNotFoundException var24) {
            logger.error("Can't find the resource index file: " + var7);
         } finally {
            IOUtils.closeQuietly(var8);
         }
      }
   }

   public Map<String, File> getResourceMap() {
      return this.resourceMap;
   }
}
