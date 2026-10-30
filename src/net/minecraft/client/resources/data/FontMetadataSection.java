package net.minecraft.client.resources.data;

public class FontMetadataSection implements IMetadataSection {
   private final float[] charWidths;
   private final float[] charLefts;
   private final float[] charSpacings;

   public FontMetadataSection(float[] arr1, float[] arr2, float[] arr3) {
      this.charWidths = arr1;
      this.charLefts = arr2;
      this.charSpacings = arr3;
   }
}
