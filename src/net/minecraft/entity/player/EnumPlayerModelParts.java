package net.minecraft.entity.player;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public enum EnumPlayerModelParts {
   CAPE(0, "cape"),
   JACKET(1, "jacket"),
   LEFT_SLEEVE(2, "left_sleeve"),
   RIGHT_SLEEVE(3, "right_sleeve"),
   LEFT_PANTS_LEG(4, "left_pants_leg"),
   RIGHT_PANTS_LEG(5, "right_pants_leg"),
   HAT(6, "hat");

   private final int partId;
   private final int partMask;
   private final String partName;
   private final IChatComponent d;

   EnumPlayerModelParts(int integer3, String string4) {
      this.partId = integer3;
      this.partMask = 1 << integer3;
      this.partName = string4;
      this.d = new ChatComponentTranslation("options.modelPart." + string4);
   }

   public int getPartMask() {
      return this.partMask;
   }

   public int getPartId() {
      return this.partId;
   }

   public String getPartName() {
      return this.partName;
   }

   public IChatComponent func_179326_d() {
      return this.d;
   }

   private static EnumPlayerModelParts[] Method240() {
      return new EnumPlayerModelParts[]{CAPE, JACKET, LEFT_SLEEVE, RIGHT_SLEEVE, LEFT_PANTS_LEG, RIGHT_PANTS_LEG, HAT};
   }
}
