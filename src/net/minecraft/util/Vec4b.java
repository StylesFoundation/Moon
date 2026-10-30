package net.minecraft.util;

public class Vec4b {
   private final byte field_176117_a;
   private final byte field_176115_b;
   private final byte field_176116_c;
   private final byte field_176114_d;

   public Vec4b(byte byte1, byte byte2, byte byte3, byte byte4) {
      this.field_176117_a = byte1;
      this.field_176115_b = byte2;
      this.field_176116_c = byte3;
      this.field_176114_d = byte4;
   }

   public Vec4b(Vec4b vec4b) {
      this.field_176117_a = vec4b.field_176117_a;
      this.field_176115_b = vec4b.field_176115_b;
      this.field_176116_c = vec4b.field_176116_c;
      this.field_176114_d = vec4b.field_176114_d;
   }

   public byte Method972() {
      return this.field_176117_a;
   }

   public byte Method973() {
      return this.field_176115_b;
   }

   public byte Method974() {
      return this.field_176116_c;
   }

   public byte Method975() {
      return this.field_176114_d;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof Vec4b var5)
            ? false
            : this.field_176117_a == var5.field_176117_a
               && this.field_176114_d == var5.field_176114_d
               && this.field_176115_b == var5.field_176115_b
               && this.field_176116_c == var5.field_176116_c;
      }
   }

   @Override
   public int hashCode() {
      int var3 = this.field_176117_a;
      var3 = 31 * var3 + this.field_176115_b;
      var3 = 31 * var3 + this.field_176116_c;
      return 31 * var3 + this.field_176114_d;
   }
}
