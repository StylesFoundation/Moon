package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.IVertexProducer;
import net.optifine.model.QuadBounds;

public class BakedQuad implements IVertexProducer {
   protected int[] vertexData;
   protected final int tintIndex;
   protected EnumFacing face;
   protected TextureAtlasSprite sprite;
   private int[] vertexDataSingle = null;
   private QuadBounds quadBounds;
   private boolean quadEmissiveChecked;
   private BakedQuad quadEmissive;

   public BakedQuad(int[] arr, int integer, EnumFacing enumFacing, TextureAtlasSprite textureAtlasSprite) {
      this.vertexData = arr;
      this.tintIndex = integer;
      this.face = enumFacing;
      this.sprite = textureAtlasSprite;
      this.fixVertexData();
   }

   public BakedQuad(int[] arr, int integer, EnumFacing enumFacing) {
      this.vertexData = arr;
      this.tintIndex = integer;
      this.face = enumFacing;
      this.fixVertexData();
   }

   public TextureAtlasSprite getSprite() {
      if (this.sprite == null) {
         this.sprite = getSpriteByUv(this.getVertexData());
      }

      return this.sprite;
   }

   public int[] getVertexData() {
      this.fixVertexData();
      return this.vertexData;
   }

   public boolean hasTintIndex() {
      return this.tintIndex != -1;
   }

   public int getTintIndex() {
      return this.tintIndex;
   }

   public EnumFacing getFace() {
      if (this.face == null) {
         this.face = FaceBakery.Method880(this.getVertexData());
      }

      return this.face;
   }

   public int[] getVertexDataSingle() {
      if (this.vertexDataSingle == null) {
         this.vertexDataSingle = makeVertexDataSingle(this.getVertexData(), this.getSprite());
      }

      return this.vertexDataSingle;
   }

   private static int[] makeVertexDataSingle(int[] arr, TextureAtlasSprite textureAtlasSprite) {
      int[] var2 = (int[])arr.clone();
      int var3 = var2.length / 4;

      for (int var4 = 0; var4 < 4; var4++) {
         int var5 = var4 * var3;
         float var6 = Float.intBitsToFloat(var2[var5 + 4]);
         float var7 = Float.intBitsToFloat(var2[var5 + 4 + 1]);
         float var8 = textureAtlasSprite.toSingleU(var6);
         float var9 = textureAtlasSprite.toSingleV(var7);
         var2[var5 + 4] = Float.floatToRawIntBits(var8);
         var2[var5 + 4 + 1] = Float.floatToRawIntBits(var9);
      }

      return var2;
   }

   private static TextureAtlasSprite getSpriteByUv(int[] arr) {
      float var1 = 1.0F;
      float var2 = 1.0F;
      float var3 = 0.0F;
      float var4 = 0.0F;
      int var5 = arr.length / 4;

      for (int var6 = 0; var6 < 4; var6++) {
         int var7 = var6 * var5;
         float var8 = Float.intBitsToFloat(arr[var7 + 4]);
         float var9 = Float.intBitsToFloat(arr[var7 + 4 + 1]);
         var1 = Math.min(var1, var8);
         var2 = Math.min(var2, var9);
         var3 = Math.max(var3, var8);
         var4 = Math.max(var4, var9);
      }

      float var10 = (var1 + var3) / 2.0F;
      float var11 = (var2 + var4) / 2.0F;
      return Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(var10, var11);
   }

   protected void fixVertexData() {
      if (Config.isShaders()) {
         if (this.vertexData.length == 28) {
            this.vertexData = expandVertexData(this.vertexData);
         }
      } else if (this.vertexData.length == 56) {
         this.vertexData = compactVertexData(this.vertexData);
      }
   }

   private static int[] expandVertexData(int[] arr) {
      int var1 = arr.length / 4;
      int var2 = var1 * 2;
      int[] var3 = new int[var2 * 4];

      for (int var4 = 0; var4 < 4; var4++) {
         System.arraycopy(arr, var4 * var1, var3, var4 * var2, var1);
      }

      return var3;
   }

   private static int[] compactVertexData(int[] arr) {
      int var1 = arr.length / 4;
      int var2 = var1 / 2;
      int[] var3 = new int[var2 * 4];

      for (int var4 = 0; var4 < 4; var4++) {
         System.arraycopy(arr, var4 * var1, var3, var4 * var2, var2);
      }

      return var3;
   }

   public QuadBounds getQuadBounds() {
      if (this.quadBounds == null) {
         this.quadBounds = new QuadBounds(this.getVertexData());
      }

      return this.quadBounds;
   }

   public float getMidX() {
      QuadBounds var1 = this.getQuadBounds();
      return (var1.Method706() + var1.Method703()) / 2.0F;
   }

   public double getMidY() {
      QuadBounds var1 = this.getQuadBounds();
      return (var1.Method707() + var1.Method704()) / 2.0F;
   }

   public double getMidZ() {
      QuadBounds var1 = this.getQuadBounds();
      return (var1.Method708() + var1.Method705()) / 2.0F;
   }

   public boolean isFaceQuad() {
      return this.getQuadBounds().Method709(this.face);
   }

   public boolean isFullQuad() {
      return this.getQuadBounds().Method710(this.face);
   }

   public boolean isFullFaceQuad() {
      return this.isFullQuad() && this.isFaceQuad();
   }

   public BakedQuad getQuadEmissive() {
      if (!this.quadEmissiveChecked) {
         if (this.quadEmissive == null && this.sprite != null && this.sprite.spriteEmissive != null) {
            this.quadEmissive = new BreakingFour(this, this.sprite.spriteEmissive);
         }

         this.quadEmissiveChecked = true;
      }

      return this.quadEmissive;
   }

   @Override
   public String toString() {
      return "vertex: " + this.vertexData.length / 7 + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
   }

   @Override
   public void pipe(IVertexConsumer iVertexConsumer) {
   }
}
