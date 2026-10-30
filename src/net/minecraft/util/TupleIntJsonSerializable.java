package net.minecraft.util;

public class TupleIntJsonSerializable {
   private int integerValue;
   private IJsonSerializable jsonSerializableValue;

   public int getIntegerValue() {
      return this.integerValue;
   }

   public void setIntegerValue(int integer) {
      this.integerValue = integer;
   }

   public <T extends IJsonSerializable> T getJsonSerializableValue() {
      return (T)this.jsonSerializableValue;
   }

   public void setJsonSerializableValue(IJsonSerializable iJsonSerializable) {
      this.jsonSerializableValue = iJsonSerializable;
   }
}
