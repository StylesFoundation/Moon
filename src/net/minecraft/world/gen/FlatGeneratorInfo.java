package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

public class FlatGeneratorInfo {
   private final List<FlatLayerInfo> flatLayers = Lists.newArrayList();
   private final Map<String, Map<String, String>> worldFeatures = Maps.newHashMap();
   private int biomeToUse;

   public int getBiome() {
      return this.biomeToUse;
   }

   public void setBiome(int integer) {
      this.biomeToUse = integer;
   }

   public Map<String, Map<String, String>> getWorldFeatures() {
      return this.worldFeatures;
   }

   public List<FlatLayerInfo> getFlatLayers() {
      return this.flatLayers;
   }

   public void func_82645_d() {
      int var3 = 0;

      for (FlatLayerInfo var5 : this.flatLayers) {
         var5.setMinY(var3);
         var3 += var5.getLayerCount();
      }
   }

   @Override
   public String toString() {
      StringBuilder var3 = new StringBuilder();
      var3.append(3);
      var3.append(";");

      for (int var4 = 0; var4 < this.flatLayers.size(); var4++) {
         if (var4 > 0) {
            var3.append(",");
         }

         var3.append(this.flatLayers.get(var4).toString());
      }

      var3.append(";");
      var3.append(this.biomeToUse);
      if (!this.worldFeatures.isEmpty()) {
         var3.append(";");
         int var11 = 0;

         for (Entry<String, Map<String, String>> var6 : this.worldFeatures.entrySet()) {
            if (var11++ > 0) {
               var3.append(",");
            }

            var3.append(var6.getKey().toLowerCase());
            Map<String, String> var7 = var6.getValue();
            if (!var7.isEmpty()) {
               var3.append("(");
               int var8 = 0;

               for (Entry<String, String> var10 : var7.entrySet()) {
                  if (var8++ > 0) {
                     var3.append(" ");
                  }

                  var3.append((String)var10.getKey());
                  var3.append("=");
                  var3.append((String)var10.getValue());
               }

               var3.append(")");
            }
         }
      } else {
         var3.append(";");
      }

      return var3.toString();
   }

   private static FlatLayerInfo func_180715_a(int integer1, String string, int integer3) {
      String[] var6 = integer1 >= 3 ? string.split("\\*", 2) : string.split("x", 2);
      int var7 = 1;
      int var8 = 0;
      if (var6.length == 2) {
         try {
            var7 = Integer.parseInt(var6[0]);
            if (integer3 + var7 >= 256) {
               var7 = 256 - integer3;
            }

            if (var7 < 0) {
               var7 = 0;
            }
         } catch (Throwable var11) {
            return null;
         }
      }

      Block var9;
      try {
         String var10 = var6[var6.length - 1];
         if (integer1 < 3) {
            var6 = var10.split(":", 2);
            if (var6.length > 1) {
               var8 = Integer.parseInt(var6[1]);
            }

            var9 = Block.getBlockById(Integer.parseInt(var6[0]));
         } else {
            var6 = var10.split(":", 3);
            var9 = var6.length > 1 ? Block.getBlockFromName(var6[0] + ":" + var6[1]) : null;
            if (var9 != null) {
               var8 = var6.length > 2 ? Integer.parseInt(var6[2]) : 0;
            } else {
               var9 = Block.getBlockFromName(var6[0]);
               if (var9 != null) {
                  var8 = var6.length > 1 ? Integer.parseInt(var6[1]) : 0;
               }
            }

            if (var9 == null) {
               return null;
            }
         }

         if (var9 == Blocks.air) {
            var8 = 0;
         }

         if (var8 < 0 || var8 > 15) {
            var8 = 0;
         }
      } catch (Throwable var12) {
         return null;
      }

      FlatLayerInfo var15 = new FlatLayerInfo(integer1, var7, var9, var8);
      var15.setMinY(integer3);
      return var15;
   }

   private static List<FlatLayerInfo> func_180716_a(int integer, String string) {
      if (string != null && string.length() >= 1) {
         ArrayList var5 = Lists.newArrayList();
         String[] var6 = string.split(",");
         int var7 = 0;

         for (String var11 : var6) {
            FlatLayerInfo var12 = func_180715_a(integer, var11, var7);
            if (var12 == null) {
               return null;
            }

            var5.add(var12);
            var7 += var12.getLayerCount();
         }

         return var5;
      } else {
         return null;
      }
   }

   public static FlatGeneratorInfo createFlatGeneratorFromString(String string) {
      if (string == null) {
         return getDefaultFlatGenerator();
      }

      String[] var4 = string.split(";", -1);
      int var5 = var4.length == 1 ? 0 : MathHelper.parseIntWithDefault(var4[0], 0);
      if (var5 >= 0 && var5 <= 3) {
         FlatGeneratorInfo var6 = new FlatGeneratorInfo();
         int var7 = var4.length == 1 ? 0 : 1;
         List var8 = func_180716_a(var5, var4[var7++]);
         if (var8 != null && !var8.isEmpty()) {
            var6.getFlatLayers().addAll(var8);
            var6.func_82645_d();
            int var9 = BiomeGenBase.plains.biomeID;
            if (var5 > 0 && var4.length > var7) {
               var9 = MathHelper.parseIntWithDefault(var4[var7++], var9);
            }

            var6.setBiome(var9);
            if (var5 > 0 && var4.length > var7) {
               String[] var11 = var4[var7++].toLowerCase().split(",");
               int var12 = var11.length;

               for (int var13 = 0; var13 < var12; var13++) {
                  String[] var15 = var11[var13].split("\\(", 2);
                  HashMap var16 = Maps.newHashMap();
                  if (var15[0].length() > 0) {
                     var6.getWorldFeatures().put(var15[0], var16);
                     if (var15.length > 1 && var15[1].endsWith(")") && var15[1].length() > 1) {
                        String[] var18 = var15[1].substring(0, var15[1].length() - 1).split(" ");
                        int var19 = var18.length;

                        for (int var20 = 0; var20 < var19; var20++) {
                           String[] var22 = var18[var20].split("=", 2);
                           if (var22.length == 2) {
                              var16.put(var22[0], var22[1]);
                           }
                        }
                     }
                  }
               }
            } else {
               var6.getWorldFeatures().put("village", Maps.newHashMap());
            }

            return var6;
         } else {
            return getDefaultFlatGenerator();
         }
      } else {
         return getDefaultFlatGenerator();
      }
   }

   public static FlatGeneratorInfo getDefaultFlatGenerator() {
      FlatGeneratorInfo var0 = new FlatGeneratorInfo();
      var0.setBiome(BiomeGenBase.plains.biomeID);
      var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
      var0.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
      var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
      var0.func_82645_d();
      var0.getWorldFeatures().put("village", Maps.newHashMap());
      return var0;
   }
}
