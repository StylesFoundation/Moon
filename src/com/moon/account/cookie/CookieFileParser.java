package com.moon.account.cookie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class CookieFileParser {
   private static final TaggedLogger logger = Logger.tag("cookies/parser");

   public static CookieMap parse(File file) {
      try {
         CookieMap cookieMap = new CookieMap();
         String[] lines = Files.readString(file.toPath()).split("\n");
         int length = lines.length;

         for (int i = 0; i < length; i++) {
            String[] parts = lines[i].split("\t");
            if (parts.length != 7) {
               logger.error("The cookie file provided has an unsupported format!");
               return null;
            }

            String name = parts[5].replaceAll("\n", "");
            String value = parts[6].replace("\r", "").replace("\n", "");
            cookieMap.getCookies().put(name, value);
         }

         return cookieMap;
      } catch (IOException e) {
         logger.error(e, "Failed to parse cookie alt file");
         return null;
      }
   }
}
