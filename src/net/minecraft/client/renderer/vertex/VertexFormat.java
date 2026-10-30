package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<VertexFormatElement> elements = Lists.newArrayList();
   private final List<Integer> offsets = Lists.newArrayList();
   private int nextOffset = 0;
   private int colorElementOffset = -1;
   private final List<Integer> uvOffsetsById = Lists.newArrayList();
   private int normalElementOffset = -1;

   public VertexFormat(VertexFormat vertexFormat) {
      this();

      for (int var2 = 0; var2 < vertexFormat.getElementCount(); var2++) {
         this.addElement(vertexFormat.getElement(var2));
      }

      this.nextOffset = vertexFormat.getNextOffset();
   }

   public VertexFormat() {
   }

   public void clear() {
      this.elements.clear();
      this.offsets.clear();
      this.colorElementOffset = -1;
      this.uvOffsetsById.clear();
      this.normalElementOffset = -1;
      this.nextOffset = 0;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public VertexFormat addElement(VertexFormatElement vertexFormatElement) {
      if (vertexFormatElement.isPositionElement() && this.hasPosition()) {
         LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
         return this;
      }

      this.elements.add(vertexFormatElement);
      this.offsets.add(this.nextOffset);
      switch (vertexFormatElement.getUsage()) {
         case NORMAL:
            this.normalElementOffset = this.nextOffset;
            break;
         case COLOR:
            this.colorElementOffset = this.nextOffset;
            break;
         case UV:
            this.uvOffsetsById.add(vertexFormatElement.getIndex(), this.nextOffset);
      }

      this.nextOffset = this.nextOffset + vertexFormatElement.getSize();
      return this;
   }

   public boolean hasNormal() {
      return this.normalElementOffset >= 0;
   }

   public int getNormalOffset() {
      return this.normalElementOffset;
   }

   public boolean hasColor() {
      return this.colorElementOffset >= 0;
   }

   public int getColorOffset() {
      return this.colorElementOffset;
   }

   public boolean hasUvOffset(int integer) {
      return this.uvOffsetsById.size() - 1 >= integer;
   }

   public int getUvOffsetById(int integer) {
      return this.uvOffsetsById.get(integer);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("format: " + this.elements.size() + " elements: ");

      for (int var2 = 0; var2 < this.elements.size(); var2++) {
         var1.append(this.elements.get(var2).toString());
         if (var2 != this.elements.size() - 1) {
            var1.append(" ");
         }
      }

      return var1.toString();
   }

   private boolean hasPosition() {
      int var1 = 0;

      for (int var2 = this.elements.size(); var1 < var2; var1++) {
         VertexFormatElement var3 = this.elements.get(var1);
         if (var3.isPositionElement()) {
            return true;
         }
      }

      return false;
   }

   public int getIntegerSize() {
      return this.getNextOffset() / 4;
   }

   public int getNextOffset() {
      return this.nextOffset;
   }

   public List<VertexFormatElement> getElements() {
      return this.elements;
   }

   public int getElementCount() {
      return this.elements.size();
   }

   public VertexFormatElement getElement(int integer) {
      return this.elements.get(integer);
   }

   public int getOffset(int integer) {
      return this.offsets.get(integer);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         VertexFormat var2 = (VertexFormat)object;
         return this.nextOffset == var2.nextOffset && this.elements.equals(var2.elements) && this.offsets.equals(var2.offsets);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var3 = this.elements.hashCode();
      var3 = 31 * var3 + this.offsets.hashCode();
      return 31 * var3 + this.nextOffset;
   }
}
