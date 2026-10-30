package net.minecraft.client.resources.data;

import java.util.Collections;
import java.util.List;

public class TextureMetadataSection implements IMetadataSection {
   private final boolean textureBlur;
   private final boolean textureClamp;
   private final List<Integer> listMipmaps;

   public TextureMetadataSection(boolean boolean1, boolean boolean2, List<Integer> list) {
      this.textureBlur = boolean1;
      this.textureClamp = boolean2;
      this.listMipmaps = list;
   }

   public boolean getTextureBlur() {
      return this.textureBlur;
   }

   public boolean getTextureClamp() {
      return this.textureClamp;
   }

   public List<Integer> getListMipmaps() {
      return Collections.unmodifiableList(this.listMipmaps);
   }
}
