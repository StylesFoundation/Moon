package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

public class Stitcher {
   private final int mipmapLevelStitcher;
   private final Set<Stitcher.Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
   private final List<Stitcher.Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
   private int currentWidth;
   private int currentHeight;
   private final int maxWidth;
   private final int maxHeight;
   private final boolean forcePowerOf2;
   private final int maxTileDimension;

   public Stitcher(int integer1, int integer2, boolean boolean3, int integer4, int integer5) {
      this.mipmapLevelStitcher = integer5;
      this.maxWidth = integer1;
      this.maxHeight = integer2;
      this.forcePowerOf2 = boolean3;
      this.maxTileDimension = integer4;
   }

   public int Method375() {
      return this.currentWidth;
   }

   public int Method376() {
      return this.currentHeight;
   }

   public void addSprite(TextureAtlasSprite textureAtlasSprite) {
      Stitcher.Holder var2 = new Stitcher.Holder(textureAtlasSprite, this.mipmapLevelStitcher);
      if (this.maxTileDimension > 0) {
         var2.setNewDimension(this.maxTileDimension);
      }

      this.setStitchHolders.add(var2);
   }

   public void doStitch() {
      Stitcher.Holder[] var3 = this.setStitchHolders.toArray(new Stitcher.Holder[0]);
      Arrays.sort(var3);

      for (Stitcher.Holder var7 : var3) {
         if (!this.allocateSlot(var7)) {
            String var8 = String.format(
               "Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?",
               var7.getAtlasSprite().getIconName(),
               var7.getAtlasSprite().getIconWidth(),
               var7.getAtlasSprite().getIconHeight(),
               this.currentWidth,
               this.currentHeight,
               this.maxWidth,
               this.maxHeight
            );
            throw new StitcherException(var7, var8);
         }
      }

      if (this.forcePowerOf2) {
         this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
         this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
      }
   }

   public List<TextureAtlasSprite> getStichSlots() {
      List<Stitcher.Slot> var1 = Lists.newArrayList();

      for (Stitcher.Slot var3 : this.stitchSlots) {
         var3.getAllStitchSlots(var1);
      }

      List<TextureAtlasSprite> var7 = Lists.newArrayList();

      for (Stitcher.Slot var4 : var1) {
         Stitcher.Holder var5 = var4.getStitchHolder();
         TextureAtlasSprite var6 = var5.getAtlasSprite();
         var6.initSprite(this.currentWidth, this.currentHeight, var4.getOriginX(), var4.getOriginY(), var5.isRotated());
         var7.add(var6);
      }

      return var7;
   }

   private static int getMipmapDimension(int integer1, int integer2) {
      return (integer1 >> integer2) + ((integer1 & (1 << integer2) - 1) == 0 ? 0 : 1) << integer2;
   }

   private boolean allocateSlot(Stitcher.Holder holder) {
      for (Stitcher.Slot var3 : this.stitchSlots) {
         if (var3.addSlot(holder)) {
            return true;
         }

         holder.rotate();
         if (var3.addSlot(holder)) {
            return true;
         }

         holder.rotate();
      }

      return this.expandAndAllocateSlot(holder);
   }

   private boolean expandAndAllocateSlot(Stitcher.Holder holder) {
      int var2 = Math.min(holder.getWidth(), holder.getHeight());
      boolean var3 = this.currentWidth == 0 && this.currentHeight == 0;
      boolean var4;
      if (this.forcePowerOf2) {
         int var5 = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
         int var6 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
         int var7 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + var2);
         int var8 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + var2);
         boolean var9 = var7 <= this.maxWidth;
         boolean var10 = var8 <= this.maxHeight;
         if (!var9 && !var10) {
            return false;
         }

         boolean var11 = var5 != var7;
         boolean var12 = var6 != var8;
         if (var11 ^ var12) {
            var4 = !var11;
         } else {
            var4 = var9 && var5 <= var6;
         }
      } else {
         boolean var13 = this.currentWidth + var2 <= this.maxWidth;
         boolean var15 = this.currentHeight + var2 <= this.maxHeight;
         if (!var13 && !var15) {
            return false;
         }

         var4 = var13 && (var3 || this.currentWidth <= this.currentHeight);
      }

      int var14 = Math.max(holder.getWidth(), holder.getHeight());
      if (MathHelper.roundUpToPowerOfTwo((!var4 ? this.currentHeight : this.currentWidth) + var14) > (!var4 ? this.maxHeight : this.maxWidth)) {
         return false;
      }

      Stitcher.Slot var16;
      if (var4) {
         if (holder.getWidth() > holder.getHeight()) {
            holder.rotate();
         }

         if (this.currentHeight == 0) {
            this.currentHeight = holder.getHeight();
         }

         var16 = new Stitcher.Slot(this.currentWidth, 0, holder.getWidth(), this.currentHeight);
         this.currentWidth = this.currentWidth + holder.getWidth();
      } else {
         var16 = new Stitcher.Slot(0, this.currentHeight, this.currentWidth, holder.getHeight());
         this.currentHeight = this.currentHeight + holder.getHeight();
      }

      var16.addSlot(holder);
      this.stitchSlots.add(var16);
      return true;
   }

   public static class Holder implements Comparable<Stitcher.Holder> {
      private final TextureAtlasSprite theTexture;
      private final int width;
      private final int height;
      private final int mipmapLevelHolder;
      private boolean rotated;
      private float scaleFactor = 1.0F;

      public Holder(TextureAtlasSprite textureAtlasSprite, int integer) {
         this.theTexture = textureAtlasSprite;
         this.width = textureAtlasSprite.getIconWidth();
         this.height = textureAtlasSprite.getIconHeight();
         this.mipmapLevelHolder = integer;
         this.rotated = Stitcher.getMipmapDimension(this.height, integer) > Stitcher.getMipmapDimension(this.width, integer);
      }

      public TextureAtlasSprite getAtlasSprite() {
         return this.theTexture;
      }

      public int getWidth() {
         return this.rotated
            ? Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder)
            : Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
      }

      public int getHeight() {
         return this.rotated
            ? Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder)
            : Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
      }

      public void rotate() {
         this.rotated = !this.rotated;
      }

      public boolean isRotated() {
         return this.rotated;
      }

      public void setNewDimension(int integer) {
         if (this.width > integer && this.height > integer) {
            this.scaleFactor = (float)integer / Math.min(this.width, this.height);
         }
      }

      @Override
      public String toString() {
         return "Holder{width=" + this.width + ", height=" + this.height + "}";
      }

      public int compareTo(Stitcher.Holder holder) {
         int var2;
         if (this.getHeight() == holder.getHeight()) {
            if (this.getWidth() == holder.getWidth()) {
               if (this.theTexture.getIconName() == null) {
                  return holder.theTexture.getIconName() == null ? 0 : -1;
               }

               return this.theTexture.getIconName().compareTo(holder.theTexture.getIconName());
            }

            var2 = this.getWidth() < holder.getWidth() ? 1 : -1;
         } else {
            var2 = this.getHeight() < holder.getHeight() ? 1 : -1;
         }

         return var2;
      }
   }

   public static class Slot {
      private final int originX;
      private final int originY;
      private final int width;
      private final int height;
      private List<Stitcher.Slot> subSlots;
      private Stitcher.Holder holder;

      public Slot(int integer1, int integer2, int integer3, int integer4) {
         this.originX = integer1;
         this.originY = integer2;
         this.width = integer3;
         this.height = integer4;
      }

      public Stitcher.Holder getStitchHolder() {
         return this.holder;
      }

      public int getOriginX() {
         return this.originX;
      }

      public int getOriginY() {
         return this.originY;
      }

      public boolean addSlot(Stitcher.Holder holder) {
         if (this.holder != null) {
            return false;
         }

         int var2 = holder.getWidth();
         int var3 = holder.getHeight();
         if (var2 <= this.width && var3 <= this.height) {
            if (var2 == this.width && var3 == this.height) {
               this.holder = holder;
               return true;
            }

            if (this.subSlots == null) {
               this.subSlots = Lists.newArrayListWithCapacity(1);
               this.subSlots.add(new Stitcher.Slot(this.originX, this.originY, var2, var3));
               int var4 = this.width - var2;
               int var5 = this.height - var3;
               if (var5 > 0 && var4 > 0) {
                  int var6 = Math.max(this.height, var4);
                  int var7 = Math.max(this.width, var5);
                  if (var6 >= var7) {
                     this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + var3, var2, var5));
                     this.subSlots.add(new Stitcher.Slot(this.originX + var2, this.originY, var4, this.height));
                  } else {
                     this.subSlots.add(new Stitcher.Slot(this.originX + var2, this.originY, var4, var3));
                     this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + var3, this.width, var5));
                  }
               } else if (var4 == 0) {
                  this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + var3, var2, var5));
               } else if (var5 == 0) {
                  this.subSlots.add(new Stitcher.Slot(this.originX + var2, this.originY, var4, var3));
               }
            }

            for (Stitcher.Slot var9 : this.subSlots) {
               if (var9.addSlot(holder)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }

      public void getAllStitchSlots(List<Stitcher.Slot> list) {
         if (this.holder != null) {
            list.add(this);
         } else if (this.subSlots != null) {
            Iterator var2 = this.subSlots.iterator();

            while (var2.hasNext()) {
               ((Stitcher.Slot)var2.next()).getAllStitchSlots(list);
            }
         }
      }

      @Override
      public String toString() {
         return "Slot{originX="
            + this.originX
            + ", originY="
            + this.originY
            + ", width="
            + this.width
            + ", height="
            + this.height
            + ", texture="
            + this.holder
            + ", subSlots="
            + this.subSlots
            + "}";
      }
   }
}
