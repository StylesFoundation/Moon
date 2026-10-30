package net.optifine.config;

import java.util.Arrays;
import java.util.regex.Pattern;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.src.Config;
import net.optifine.util.StrUtils;
import org.apache.commons.text.StringEscapeUtils;

public class NbtTagValue {
   private String[] parents = null;
   private String name = null;
   private boolean negative = false;
   private int type = 0;
   private String value = null;
   private int valueFormat = 0;
   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");

   public NbtTagValue(String string1, String string2) {
      String[] var6 = Config.tokenize(string1, ".");
      this.parents = Arrays.copyOfRange(var6, 0, var6.length - 1);
      this.name = var6[var6.length - 1];
      if (string2.startsWith("!")) {
         this.negative = true;
         string2 = string2.substring(1);
      }

      if (string2.startsWith("pattern:")) {
         this.type = 1;
         string2 = string2.substring(8);
      } else if (string2.startsWith("ipattern:")) {
         this.type = 2;
         string2 = string2.substring(9).toLowerCase();
      } else if (string2.startsWith("regex:")) {
         this.type = 3;
         string2 = string2.substring(6);
      } else if (string2.startsWith("iregex:")) {
         this.type = 4;
         string2 = string2.substring(7).toLowerCase();
      } else {
         this.type = 0;
      }

      string2 = StringEscapeUtils.unescapeJava(string2);
      if (this.type == 0 && PATTERN_HEX_COLOR.matcher(string2).matches()) {
         this.valueFormat = 1;
      }

      this.value = string2;
   }

   public boolean matches(NBTTagCompound nBTTagCompound) {
      return this.negative != this.matchesCompound(nBTTagCompound);
   }

   public boolean matchesCompound(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound == null) {
         return false;
      }

      NBTBase var5 = nBTTagCompound;

      for (String var9 : this.parents) {
         var5 = getChildTag(var5, var9);
         if (var5 == null) {
            return false;
         }
      }

      if (this.name.equals("*")) {
         return this.matchesAnyChild(var5);
      }

      var5 = getChildTag(var5, this.name);
      return var5 == null ? false : this.matchesBase(var5);
   }

   private boolean matchesAnyChild(NBTBase nBTBase) {
      if (nBTBase instanceof NBTTagCompound var5) {
         for (String var7 : var5.getKeySet()) {
            NBTBase var8 = var5.getTag(var7);
            if (this.matchesBase(var8)) {
               return true;
            }
         }
      }

      if (nBTBase instanceof NBTTagList var9) {
         int var10 = var9.tagCount();

         for (int var11 = 0; var11 < var10; var11++) {
            NBTBase var12 = var9.get(var11);
            if (this.matchesBase(var12)) {
               return true;
            }
         }
      }

      return false;
   }

   private static NBTBase getChildTag(NBTBase nBTBase, String string) {
      if (nBTBase instanceof NBTTagCompound) {
         return ((NBTTagCompound)nBTBase).getTag(string);
      }

      if (nBTBase instanceof NBTTagList var6) {
         if (string.equals("count")) {
            return new NBTTagInt(var6.tagCount());
         }

         int var7 = Config.parseInt(string, -1);
         return var7 >= 0 && var7 < var6.tagCount() ? var6.get(var7) : null;
      } else {
         return null;
      }
   }

   public boolean matchesBase(NBTBase nBTBase) {
      if (nBTBase == null) {
         return false;
      }

      String var5 = getNbtString(nBTBase, this.valueFormat);
      return this.matchesValue(var5);
   }

   public boolean matchesValue(String string) {
      if (string == null) {
         return false;
      }

      switch (this.type) {
         case 0:
            return string.equals(this.value);
         case 1:
            return this.matchesPattern(string, this.value);
         case 2:
            return this.matchesPattern(string.toLowerCase(), this.value);
         case 3:
            return this.matchesRegex(string, this.value);
         case 4:
            return this.matchesRegex(string.toLowerCase(), this.value);
         default:
            throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
      }
   }

   private boolean matchesPattern(String string1, String string2) {
      return StrUtils.equalsMask(string1, string2, '*', '?');
   }

   private boolean matchesRegex(String string1, String string2) {
      return string1.matches(string2);
   }

   private static String getNbtString(NBTBase nBTBase, int integer) {
      if (nBTBase == null) {
         return null;
      } else if (nBTBase instanceof NBTTagString) {
         return ((NBTTagString)nBTBase).getString();
      } else if (nBTBase instanceof NBTTagInt var6) {
         return integer == 1 ? "#" + StrUtils.fillLeft(Integer.toHexString(var6.getInt()), 6, '0') : Integer.toString(var6.getInt());
      } else if (nBTBase instanceof NBTTagByte) {
         return Byte.toString(((NBTTagByte)nBTBase).getByte());
      } else if (nBTBase instanceof NBTTagShort) {
         return Short.toString(((NBTTagShort)nBTBase).getShort());
      } else if (nBTBase instanceof NBTTagLong) {
         return Long.toString(((NBTTagLong)nBTBase).getLong());
      } else if (nBTBase instanceof NBTTagFloat) {
         return Float.toString(((NBTTagFloat)nBTBase).getFloat());
      } else {
         return nBTBase instanceof NBTTagDouble ? Double.toString(((NBTTagDouble)nBTBase).getDouble()) : nBTBase.toString();
      }
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder();

      for (int var5 = 0; var5 < this.parents.length; var5++) {
         String var6 = this.parents[var5];
         if (var5 > 0) {
            var4.append(".");
         }

         var4.append(var6);
      }

      if (var4.length() > 0) {
         var4.append(".");
      }

      var4.append(this.name);
      var4.append(" = ");
      var4.append(this.value);
      return var4.toString();
   }
}
