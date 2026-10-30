package net.minecraft.server.management;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreYggdrasilConverter {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
   public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
   public static final File OLD_OPS_FILE = new File("ops.txt");
   public static final File OLD_WHITELIST_FILE = new File("white-list.txt");

   private static void lookupNames(MinecraftServer minecraftServer, Collection<String> collection, ProfileLookupCallback profileLookupCallback) {
      String[] var6 = (String[])Iterators.toArray(Iterators.filter(collection.iterator(), string -> !StringUtils.isNullOrEmpty(string)), String.class);
      if (minecraftServer.isServerInOnlineMode()) {
         minecraftServer.getGameProfileRepository().findProfilesByNames(var6, Agent.MINECRAFT, profileLookupCallback);
      } else {
         for (String var10 : var6) {
            UUID var11 = EntityPlayer.getUUID(new GameProfile(null, var10));
            GameProfile var12 = new GameProfile(var11, var10);
            profileLookupCallback.onProfileLookupSucceeded(var12);
         }
      }
   }

   public static String getStringUUIDFromName(String string) {
      if (!StringUtils.isNullOrEmpty(string) && string.length() <= 16) {
         final MinecraftServer var4 = MinecraftServer.getServer();
         GameProfile var5 = var4.getPlayerProfileCache().getGameProfileForUsername(string);
         if (var5 != null && var5.getId() != null) {
            return var5.getId().toString();
         } else if (!var4.isSinglePlayer() && var4.isServerInOnlineMode()) {
            final ArrayList var6 = Lists.newArrayList();
            ProfileLookupCallback var7 = new ProfileLookupCallback() {
               @Override
               public void onProfileLookupSucceeded(GameProfile gameProfile) {
                  var4.getPlayerProfileCache().addEntry(gameProfile);
                  var6.add(gameProfile);
               }

               @Override
               public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                  PreYggdrasilConverter.LOGGER.warn("Could not lookup user whitelist entry for " + gameProfile.getName(), exception);
               }
            };
            lookupNames(var4, Lists.newArrayList(new String[]{string}), var7);
            return var6.size() > 0 && ((GameProfile)var6.get(0)).getId() != null ? ((GameProfile)var6.get(0)).getId().toString() : "";
         } else {
            return EntityPlayer.getUUID(new GameProfile(null, string)).toString();
         }
      } else {
         return string;
      }
   }
}
