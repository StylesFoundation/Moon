package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private static final Logger LOGGER = LogManager.getLogger();
   private final VertexFormatElement.EnumType type;
   private final VertexFormatElement.EnumUsage usage;
   private final int index;
   private final int elementCount;

   public VertexFormatElement(int integer1, VertexFormatElement.EnumType enumType, VertexFormatElement.EnumUsage enumUsage, int integer4) {
      if (!this.func_177372_a(integer1, enumUsage)) {
         LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.usage = VertexFormatElement.EnumUsage.UV;
      } else {
         this.usage = enumUsage;
      }

      this.type = enumType;
      this.index = integer1;
      this.elementCount = integer4;
   }

   private boolean func_177372_a(int integer, VertexFormatElement.EnumUsage enumUsage) {
      return integer == 0 || enumUsage == VertexFormatElement.EnumUsage.UV;
   }

   public final VertexFormatElement.EnumType getType() {
      return this.type;
   }

   public final VertexFormatElement.EnumUsage getUsage() {
      return this.usage;
   }

   public final int getElementCount() {
      return this.elementCount;
   }

   public final int getIndex() {
      return this.index;
   }

   @Override
   public String toString() {
      return this.elementCount + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
   }

   public final int getSize() {
      return this.type.getSize() * this.elementCount;
   }

   public final boolean isPositionElement() {
      return this.usage == VertexFormatElement.EnumUsage.POSITION;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         VertexFormatElement var2 = (VertexFormatElement)object;
         return this.elementCount == var2.elementCount && this.index == var2.index && this.type == var2.type && this.usage == var2.usage;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var3 = this.type.hashCode();
      var3 = 31 * var3 + this.usage.hashCode();
      var3 = 31 * var3 + this.index;
      return 31 * var3 + this.elementCount;
   }

   public enum EnumType {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int size;
      private final String displayName;
      private final int glConstant;

      EnumType(int integer3, String string4, int integer5) {
         this.size = integer3;
         this.displayName = string4;
         this.glConstant = integer5;
      }

      public int getSize() {
         return this.size;
      }

      public String getDisplayName() {
         return this.displayName;
      }

      public int getGlConstant() {
         return this.glConstant;
      }

      private static VertexFormatElement.EnumType[] Method424() {
         return new VertexFormatElement.EnumType[]{FLOAT, UBYTE, BYTE, USHORT, SHORT, UINT, INT};
      }
   }

   public enum EnumUsage {
      POSITION("Position"),
      NORMAL("Normal"),
      COLOR("Vertex Color"),
      UV("UV"),
      MATRIX("Bone Matrix"),
      BLEND_WEIGHT("Blend Weight"),
      PADDING("Padding");

      private final String displayName;

      EnumUsage(String string3) {
         this.displayName = string3;
      }

      public String getDisplayName() {
         return this.displayName;
      }

      private static VertexFormatElement.EnumUsage[] Method428() {
         return new VertexFormatElement.EnumUsage[]{POSITION, NORMAL, COLOR, UV, MATRIX, BLEND_WEIGHT, PADDING};
      }
   }
}
