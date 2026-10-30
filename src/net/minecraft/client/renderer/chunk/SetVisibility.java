package net.minecraft.client.renderer.chunk;

import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
   private static final int COUNT_FACES = EnumFacing.values().length;
   private long bits;

   public void setManyVisible(Set<EnumFacing> set) {
      for (EnumFacing var3 : set) {
         for (EnumFacing var5 : set) {
            this.setVisible(var3, var5, true);
         }
      }
   }

   public void setVisible(EnumFacing enumFacing1, EnumFacing enumFacing2, boolean boolean3) {
      this.setBit(enumFacing1.ordinal() + enumFacing2.ordinal() * COUNT_FACES, boolean3);
      this.setBit(enumFacing2.ordinal() + enumFacing1.ordinal() * COUNT_FACES, boolean3);
   }

   public void setAllVisible(boolean boolean1) {
      if (boolean1) {
         this.bits = -1L;
      } else {
         this.bits = 0L;
      }
   }

   public boolean isVisible(EnumFacing enumFacing1, EnumFacing enumFacing2) {
      return this.getBit(enumFacing1.ordinal() + enumFacing2.ordinal() * COUNT_FACES);
   }

   @Override
   public String toString() {
      StringBuilder var3 = new StringBuilder();
      var3.append(' ');

      for (EnumFacing var7 : EnumFacing.values()) {
         var3.append(' ').append(var7.toString().toUpperCase().charAt(0));
      }

      var3.append('\n');

      for (EnumFacing var16 : EnumFacing.values()) {
         var3.append(var16.toString().toUpperCase().charAt(0));

         for (EnumFacing var11 : EnumFacing.values()) {
            if (var16 == var11) {
               var3.append("  ");
            } else {
               boolean var12 = this.isVisible(var16, var11);
               var3.append(' ').append((char)(var12 ? 'Y' : 'n'));
            }
         }

         var3.append('\n');
      }

      return var3.toString();
   }

   private boolean getBit(int integer) {
      return (this.bits & 1L << integer) != 0L;
   }

   private void setBit(int integer, boolean boolean2) {
      if (boolean2) {
         this.setBit(integer);
      } else {
         this.clearBit(integer);
      }
   }

   private void setBit(int integer) {
      this.bits |= 1L << integer;
   }

   private void clearBit(int integer) {
      this.bits &= ~(1L << integer);
   }
}
