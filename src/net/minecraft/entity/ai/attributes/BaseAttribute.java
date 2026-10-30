package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute {
   private final IAttribute field_180373_a;
   private final String unlocalizedName;
   private final double defaultValue;
   private boolean shouldWatch;

   protected BaseAttribute(IAttribute iAttribute, String string, double double3) {
      this.field_180373_a = iAttribute;
      this.unlocalizedName = string;
      this.defaultValue = double3;
      if (string == null) {
         throw new IllegalArgumentException("Name cannot be null!");
      }
   }

   @Override
   public String getAttributeUnlocalizedName() {
      return this.unlocalizedName;
   }

   @Override
   public double getDefaultValue() {
      return this.defaultValue;
   }

   @Override
   public boolean getShouldWatch() {
      return this.shouldWatch;
   }

   public BaseAttribute setShouldWatch(boolean boolean1) {
      this.shouldWatch = boolean1;
      return this;
   }

   @Override
   public IAttribute func_180372_d() {
      return this.field_180373_a;
   }

   @Override
   public int hashCode() {
      return this.unlocalizedName.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      return object instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)object).getAttributeUnlocalizedName());
   }
}
