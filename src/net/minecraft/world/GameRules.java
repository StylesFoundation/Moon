package net.minecraft.world;

import java.util.TreeMap;
import net.minecraft.nbt.NBTTagCompound;

public class GameRules {
   private final TreeMap<String, GameRules.Value> theGameRules = new TreeMap<>();

   public GameRules() {
      this.addGameRule("doFireTick", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("mobGriefing", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("keepInventory", "false", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("doMobSpawning", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("doMobLoot", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("doTileDrops", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("doEntityDrops", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("commandBlockOutput", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("naturalRegeneration", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("doDaylightCycle", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("logAdminCommands", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("showDeathMessages", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("randomTickSpeed", "3", GameRules.ValueType.NUMERICAL_VALUE);
      this.addGameRule("sendCommandFeedback", "true", GameRules.ValueType.BOOLEAN_VALUE);
      this.addGameRule("reducedDebugInfo", "false", GameRules.ValueType.BOOLEAN_VALUE);
   }

   public void addGameRule(String string1, String string2, GameRules.ValueType valueType) {
      this.theGameRules.put(string1, new GameRules.Value(string2, valueType));
   }

   public void setOrCreateGameRule(String string1, String string2) {
      GameRules.Value var6 = this.theGameRules.get(string1);
      if (var6 != null) {
         var6.setValue(string2);
      } else {
         this.addGameRule(string1, string2, GameRules.ValueType.ANY_VALUE);
      }
   }

   public String getString(String string) {
      GameRules.Value var5 = this.theGameRules.get(string);
      return var5 != null ? var5.getString() : "";
   }

   public boolean getBoolean(String string) {
      GameRules.Value var5 = this.theGameRules.get(string);
      return var5 != null && var5.getBoolean();
   }

   public int getInt(String string) {
      GameRules.Value var5 = this.theGameRules.get(string);
      return var5 != null ? var5.getInt() : 0;
   }

   public NBTTagCompound writeToNBT() {
      NBTTagCompound var4 = new NBTTagCompound();

      for (String var6 : this.theGameRules.keySet()) {
         GameRules.Value var7 = this.theGameRules.get(var6);
         var4.setString(var6, var7.getString());
      }

      return var4;
   }

   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      for (String var6 : nBTTagCompound.getKeySet()) {
         String var7 = nBTTagCompound.getString(var6);
         this.setOrCreateGameRule(var6, var7);
      }
   }

   public String[] getRules() {
      return this.theGameRules.keySet().toArray(new String[0]);
   }

   public boolean hasRule(String string) {
      return this.theGameRules.containsKey(string);
   }

   public boolean areSameType(String string, GameRules.ValueType valueType) {
      GameRules.Value var6 = this.theGameRules.get(string);
      return var6 != null && (var6.getType() == valueType || valueType == GameRules.ValueType.ANY_VALUE);
   }

   static class Value {
      private String valueString;
      private boolean valueBoolean;
      private int valueInteger;
      private double valueDouble;
      private final GameRules.ValueType type;

      public Value(String string, GameRules.ValueType valueType) {
         this.type = valueType;
         this.setValue(string);
      }

      public void setValue(String string) {
         this.valueString = string;
         if (string != null) {
            if (string.equals("false")) {
               this.valueBoolean = false;
               return;
            }

            if (string.equals("true")) {
               this.valueBoolean = true;
               return;
            }
         }

         this.valueBoolean = Boolean.parseBoolean(string);
         this.valueInteger = this.valueBoolean ? 1 : 0;

         try {
            this.valueInteger = Integer.parseInt(string);
         } catch (NumberFormatException var3) {
         }

         try {
            this.valueDouble = Double.parseDouble(string);
         } catch (NumberFormatException var2) {
         }
      }

      public String getString() {
         return this.valueString;
      }

      public boolean getBoolean() {
         return this.valueBoolean;
      }

      public int getInt() {
         return this.valueInteger;
      }

      public GameRules.ValueType getType() {
         return this.type;
      }
   }

   public enum ValueType {
      ANY_VALUE,
      BOOLEAN_VALUE,
      NUMERICAL_VALUE;

      private static GameRules.ValueType[] Method933() {
         return new GameRules.ValueType[]{ANY_VALUE, BOOLEAN_VALUE, NUMERICAL_VALUE};
      }
   }
}
