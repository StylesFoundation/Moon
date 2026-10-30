package net.optifine;

import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.ConnectedParser;

public class RandomEntityProperties {
   public String name = null;
   public String basePath = null;
   public ResourceLocation[] resourceLocations = null;
   public RandomEntityRule[] rules = null;

   public RandomEntityProperties(String string, ResourceLocation[] arr) {
      ConnectedParser var5 = new ConnectedParser("RandomEntities");
      this.name = var5.parseName(string);
      this.basePath = var5.parseBasePath(string);
      this.resourceLocations = arr;
   }

   public RandomEntityProperties(Properties properties, String string, ResourceLocation resourceLocation) {
      ConnectedParser var6 = new ConnectedParser("RandomEntities");
      this.name = var6.parseName(string);
      this.basePath = var6.parseBasePath(string);
      this.rules = this.parseRules(properties, string, resourceLocation, var6);
   }

   public ResourceLocation getTextureLocation(ResourceLocation resourceLocation, IRandomEntity iRandomEntity) {
      if (this.rules != null) {
         for (RandomEntityRule var9 : this.rules) {
            if (var9.matches(iRandomEntity)) {
               return var9.getTextureLocation(resourceLocation, iRandomEntity.getId());
            }
         }
      }

      if (this.resourceLocations != null) {
         int var10 = iRandomEntity.getId() % this.resourceLocations.length;
         return this.resourceLocations[var10];
      } else {
         return resourceLocation;
      }
   }

   private RandomEntityRule[] parseRules(Properties properties, String string, ResourceLocation resourceLocation, ConnectedParser connectedParser) {
      ArrayList<RandomEntityRule> var8 = new ArrayList<RandomEntityRule>();
      int var9 = properties.size();

      for (int var10 = 0; var10 < var9; var10++) {
         int var11 = var10 + 1;
         String var12 = properties.getProperty("textures." + var11);
         if (var12 == null) {
            var12 = properties.getProperty("skins." + var11);
         }

         if (var12 != null) {
            RandomEntityRule var13 = new RandomEntityRule(properties, string, resourceLocation, var11, var12, connectedParser);
            if (var13.isValid(string)) {
               var8.add(var13);
            }
         }
      }

      return var8.toArray(new RandomEntityRule[0]);
   }

   public boolean isValid(String string) {
      if (this.resourceLocations == null && this.rules == null) {
         Config.Method252("No skins specified: " + string);
         return false;
      }

      if (this.rules != null) {
         for (RandomEntityRule var8 : this.rules) {
            if (!var8.isValid(string)) {
               return false;
            }
         }
      }

      if (this.resourceLocations != null) {
         for (ResourceLocation var12 : this.resourceLocations) {
            if (!Config.hasResource(var12)) {
               Config.Method252("Texture not found: " + var12.getResourcePath());
               return false;
            }
         }
      }

      return true;
   }

   public boolean isDefault() {
      return this.rules == null && this.resourceLocations == null;
   }
}
