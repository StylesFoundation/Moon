package net.minecraft.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class StringTranslate {
   private static final Pattern a = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
   private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);
   private static final StringTranslate instance = new StringTranslate();
   private final Map<String, String> languageList = Maps.newHashMap();
   private long lastUpdateTimeInMilliseconds;

   public StringTranslate() {
      try {
         for (String var5 : IOUtils.readLines(StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang"), Charsets.UTF_8)) {
            if (!var5.isEmpty() && var5.charAt(0) != '#') {
               String[] var6 = (String[])Iterables.toArray(equalSignSplitter.split(var5), String.class);
               if (var6.length == 2) {
                  String var7 = var6[0];
                  String var8 = a.matcher(var6[1]).replaceAll("%$1s");
                  this.languageList.put(var7, var8);
               }
            }
         }

         this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
      } catch (IOException var9) {
      }
   }

   static StringTranslate getInstance() {
      return instance;
   }

   public static synchronized void replaceWith(Map<String, String> map) {
      instance.languageList.clear();
      instance.languageList.putAll(map);
      instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
   }

   public synchronized String translateKey(String string) {
      return this.tryTranslateKey(string);
   }

   public synchronized String Method902(String string, Object... arr) {
      String var3 = this.tryTranslateKey(string);

      try {
         return String.format(var3, arr);
      } catch (IllegalFormatException var4) {
         return "Format error: " + var3;
      }
   }

   private String tryTranslateKey(String string) {
      String var5 = this.languageList.get(string);
      return var5 == null ? string : var5;
   }

   public synchronized boolean isKeyTranslated(String string) {
      return this.languageList.containsKey(string);
   }

   public long getLastUpdateTimeInMilliseconds() {
      return this.lastUpdateTimeInMilliseconds;
   }
}
