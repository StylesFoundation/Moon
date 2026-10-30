package net.optifine.config;

import net.minecraft.src.Config;

public class VillagerProfession {
   private final int profession;
   private int[] careers;

   public VillagerProfession(int integer) {
      this(integer, null);
   }

   public VillagerProfession(int integer1, int integer2) {
      this(integer1, new int[]{integer2});
   }

   public VillagerProfession(int integer, int[] arr) {
      this.profession = integer;
      this.careers = arr;
   }

   public boolean matches(int integer1, int integer2) {
      return this.profession == integer1 && (this.careers == null || Config.equalsOne(integer2, this.careers));
   }

   private boolean hasCareer(int integer) {
      return this.careers != null && Config.equalsOne(integer, this.careers);
   }

   public boolean addCareer(int integer) {
      if (this.careers == null) {
         this.careers = new int[]{integer};
         return true;
      }

      if (this.hasCareer(integer)) {
         return false;
      }

      this.careers = Config.addIntToArray(this.careers, integer);
      return true;
   }

   public int getProfession() {
      return this.profession;
   }

   public int[] getCareers() {
      return this.careers;
   }

   @Override
   public String toString() {
      return this.careers == null ? this.profession + "" : this.profession + ":" + Config.arrayToString(this.careers);
   }
}
