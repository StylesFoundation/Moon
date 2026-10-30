package net.minecraft.nbt;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import net.minecraft.util.StringUtils;

public final class NBTUtil {
   public static GameProfile readGameProfileFromNBT(NBTTagCompound nBTTagCompound) {
      String var4 = null;
      String var5 = null;
      if (nBTTagCompound.hasKey("Name", 8)) {
         var4 = nBTTagCompound.getString("Name");
      }

      if (nBTTagCompound.hasKey("Id", 8)) {
         var5 = nBTTagCompound.getString("Id");
      }

      if (StringUtils.isNullOrEmpty(var4) && StringUtils.isNullOrEmpty(var5)) {
         return null;
      }

      UUID var6;
      try {
         var6 = UUID.fromString(var5);
      } catch (Throwable var15) {
         var6 = null;
      }

      GameProfile var7 = new GameProfile(var6, var4);
      if (nBTTagCompound.hasKey("Properties", 10)) {
         NBTTagCompound var8 = nBTTagCompound.getCompoundTag("Properties");

         for (String var10 : var8.getKeySet()) {
            NBTTagList var11 = var8.getTagList(var10, 10);

            for (int var12 = 0; var12 < var11.tagCount(); var12++) {
               NBTTagCompound var13 = var11.getCompoundTagAt(var12);
               String var14 = var13.getString("Value");
               if (var13.hasKey("Signature", 8)) {
                  var7.getProperties().put(var10, new Property(var10, var14, var13.getString("Signature")));
               } else {
                  var7.getProperties().put(var10, new Property(var10, var14));
               }
            }
         }
      }

      return var7;
   }

   public static NBTTagCompound writeGameProfile(NBTTagCompound nBTTagCompound, GameProfile gameProfile) {
      if (!StringUtils.isNullOrEmpty(gameProfile.getName())) {
         nBTTagCompound.setString("Name", gameProfile.getName());
      }

      if (gameProfile.getId() != null) {
         nBTTagCompound.setString("Id", gameProfile.getId().toString());
      }

      if (!gameProfile.getProperties().isEmpty()) {
         NBTTagCompound var5 = new NBTTagCompound();

         for (String var7 : gameProfile.getProperties().keySet()) {
            NBTTagList var8 = new NBTTagList();

            for (Property var10 : gameProfile.getProperties().get(var7)) {
               NBTTagCompound var11 = new NBTTagCompound();
               var11.setString("Value", var10.getValue());
               if (var10.hasSignature()) {
                  var11.setString("Signature", var10.getSignature());
               }

               var8.appendTag(var11);
            }

            var5.setTag(var7, var8);
         }

         nBTTagCompound.setTag("Properties", var5);
      }

      return nBTTagCompound;
   }

   public static boolean func_181123_a(NBTBase nBTBase1, NBTBase nBTBase2, boolean boolean3) {
      if (nBTBase1 == nBTBase2) {
         return true;
      }

      if (nBTBase1 == null) {
         return true;
      }

      if (nBTBase2 == null) {
         return false;
      }

      if (!nBTBase1.getClass().equals(nBTBase2.getClass())) {
         return false;
      }

      if (nBTBase1 instanceof NBTTagCompound var6) {
         NBTTagCompound var13 = (NBTTagCompound)nBTBase2;

         for (String var15 : var6.getKeySet()) {
            NBTBase var16 = var6.getTag(var15);
            if (!func_181123_a(var16, var13.getTag(var15), boolean3)) {
               return false;
            }
         }

         return true;
      } else if (nBTBase1 instanceof NBTTagList var7 && boolean3) {
         NBTTagList var8 = (NBTTagList)nBTBase2;
         if (var7.tagCount() == 0) {
            return var8.tagCount() == 0;
         }

         for (int var9 = 0; var9 < var7.tagCount(); var9++) {
            NBTBase var10 = var7.get(var9);
            boolean var11 = false;

            for (int var12 = 0; var12 < var8.tagCount(); var12++) {
               if (func_181123_a(var10, var8.get(var12), boolean3)) {
                  var11 = true;
                  break;
               }
            }

            if (!var11) {
               return false;
            }
         }

         return true;
      } else {
         return nBTBase1.equals(nBTBase2);
      }
   }
}
