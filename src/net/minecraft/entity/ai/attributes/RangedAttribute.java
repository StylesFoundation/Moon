package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

public class RangedAttribute extends BaseAttribute {
   private final double minimumValue;
   private final double maximumValue;
   private String description;

   public RangedAttribute(IAttribute iAttribute, String string, double double3, double double4, double double5) {
      super(iAttribute, string, double3);
      this.minimumValue = double4;
      this.maximumValue = double5;
      if (double4 > double5) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      }

      if (double3 < double4) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      }

      if (double3 > double5) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public RangedAttribute setDescription(String string) {
      this.description = string;
      return this;
   }

   public String getDescription() {
      return this.description;
   }

   @Override
   public double clampValue(double double1) {
      return MathHelper.clamp_double(double1, this.minimumValue, this.maximumValue);
   }
}
