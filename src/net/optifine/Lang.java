package net.optifine;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class Lang {
   private static final Splitter splitter = Splitter.on('=').limit(2);
   private static final Pattern b = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");

   public static void resourcesReloaded() {
      Map var3 = I18n.getLocaleProperties();
      ArrayList<String> var4 = new ArrayList<String>();
      String var5 = "optifine/lang/";
      String var6 = "en_US";
      String var7 = ".lang";
      var4.add(var5 + var6 + var7);
      if (!Config.getGameSettings().language.equals(var6)) {
         var4.add(var5 + Config.getGameSettings().language + var7);
      }

      String[] var8 = var4.toArray(new String[0]);
      loadResources(Config.getDefaultResourcePack(), var8, var3);

      for (IResourcePack var13 : Config.getResourcePacks()) {
         loadResources(var13, var8, var3);
      }
   }

   private static void loadResources(IResourcePack iResourcePack, String[] arr, Map map) {
      try {
         for (String var9 : arr) {
            ResourceLocation var10 = new ResourceLocation(var9);
            if (iResourcePack.resourceExists(var10)) {
               InputStream var11 = iResourcePack.getInputStream(var10);
               if (var11 != null) {
                  loadLocaleData(var11, map);
               }
            }
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      }
   }

   public static void loadLocaleData(InputStream inputStream, Map map) throws IOException {
      Iterator var5 = IOUtils.readLines(inputStream, StandardCharsets.UTF_8).iterator();
      inputStream.close();

      while (var5.hasNext()) {
         String var6 = (String)var5.next();
         if (!var6.isEmpty() && var6.charAt(0) != '#') {
            String[] var7 = (String[])Iterables.toArray(splitter.split(var6), String.class);
            if (var7.length == 2) {
               String var8 = var7[0];
               String var9 = b.matcher(var7[1]).replaceAll("%$1s");
               map.put(var8, var9);
            }
         }
      }
   }

   public static String get(String string) {
      return I18n.format(string);
   }

   public static String get(String string1, String string2) {
      String var5 = I18n.format(string1);
      return var5 != null && !var5.equals(string1) ? var5 : string2;
   }

   public static String getOn() {
      return I18n.format("options.on");
   }

   public static String getOff() {
      return I18n.format("options.off");
   }

   public static String getFast() {
      return I18n.format("options.graphics.fast");
   }

   public static String getFancy() {
      return I18n.format("options.graphics.fancy");
   }

   public static String getDefault() {
      return I18n.format("generator.default");
   }
}
