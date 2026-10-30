package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.optifine.SmartAnimations;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.util.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {
   private ByteBuffer byteBuffer;
   public IntBuffer rawIntBuffer;
   private ShortBuffer rawShortBuffer;
   public FloatBuffer rawFloatBuffer;
   public int vertexCount;
   private VertexFormatElement vertexFormatElement;
   private int vertexFormatIndex;
   private boolean noColor;
   public int drawMode;
   private double xOffset;
   private double yOffset;
   private double zOffset;
   private VertexFormat vertexFormat;
   private boolean isDrawing;
   private EnumWorldBlockLayer blockLayer = null;
   private boolean[] drawnIcons = new boolean[256];
   private TextureAtlasSprite[] quadSprites = null;
   private TextureAtlasSprite[] quadSpritesPrev = null;
   private TextureAtlasSprite quadSprite = null;
   public SVertexBuilder sVertexBuilder;
   public RenderEnv renderEnv = null;
   public BitSet animatedSprites = null;
   public BitSet animatedSpritesCached = new BitSet();
   private boolean modeTriangles = false;
   private ByteBuffer byteBufferTriangles;

   public WorldRenderer(int integer) {
      this.byteBuffer = GLAllocation.createDirectByteBuffer(integer * 4);
      this.rawIntBuffer = this.byteBuffer.asIntBuffer();
      this.rawShortBuffer = this.byteBuffer.asShortBuffer();
      this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
      SVertexBuilder.initVertexBuilder(this);
   }

   private void growBuffer(int integer) {
      if (integer > this.rawIntBuffer.remaining()) {
         int var4 = this.byteBuffer.capacity();
         int var5 = var4 % 2097152;
         int var6 = var5 + (((this.rawIntBuffer.position() + integer) * 4 - var5) / 2097152 + 1) * 2097152;
         LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + var4 + " bytes, new size " + var6 + " bytes.");
         int var7 = this.rawIntBuffer.position();
         ByteBuffer var8 = GLAllocation.createDirectByteBuffer(var6);
         this.byteBuffer.position(0);
         var8.put(this.byteBuffer);
         var8.rewind();
         this.byteBuffer = var8;
         this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
         this.rawIntBuffer = this.byteBuffer.asIntBuffer();
         this.rawIntBuffer.position(var7);
         this.rawShortBuffer = this.byteBuffer.asShortBuffer();
         this.rawShortBuffer.position(var7 << 1);
         if (this.quadSprites != null) {
            TextureAtlasSprite[] var9 = this.quadSprites;
            int var10 = this.getBufferQuadSize();
            this.quadSprites = new TextureAtlasSprite[var10];
            System.arraycopy(var9, 0, this.quadSprites, 0, Math.min(var9.length, this.quadSprites.length));
            this.quadSpritesPrev = null;
         }
      }
   }

   public void sortVertexData(float float1, float float2, float float3) {
      int var4 = this.vertexCount / 4;
      float[] var5 = new float[var4];

      for (int var6 = 0; var6 < var4; var6++) {
         var5[var6] = getDistanceSq(
            this.rawFloatBuffer,
            (float)(float1 + this.xOffset),
            (float)(float2 + this.yOffset),
            (float)(float3 + this.zOffset),
            this.vertexFormat.getIntegerSize(),
            var6 * this.vertexFormat.getNextOffset()
         );
      }

      Integer[] var15 = new Integer[var4];

      for (int var7 = 0; var7 < var15.length; var7++) {
         var15[var7] = var7;
      }

      Arrays.sort(var15, (integer2, integer3) -> Floats.compare(var5[integer3], var5[integer2]));
      BitSet var16 = new BitSet();
      int var8 = this.vertexFormat.getNextOffset();
      int[] var9 = new int[var8];

      for (int var17 = 0; (var17 = var16.nextClearBit(var17)) < var15.length; var17++) {
         int var11 = var15[var17];
         if (var11 != var17) {
            this.rawIntBuffer.limit(var11 * var8 + var8);
            this.rawIntBuffer.position(var11 * var8);
            this.rawIntBuffer.get(var9);
            int var12 = var11;

            for (int var13 = var15[var11]; var12 != var17; var13 = var15[var13]) {
               this.rawIntBuffer.limit(var13 * var8 + var8);
               this.rawIntBuffer.position(var13 * var8);
               IntBuffer var14 = this.rawIntBuffer.slice();
               this.rawIntBuffer.limit(var12 * var8 + var8);
               this.rawIntBuffer.position(var12 * var8);
               this.rawIntBuffer.put(var14);
               var16.set(var12);
               var12 = var13;
            }

            this.rawIntBuffer.limit(var17 * var8 + var8);
            this.rawIntBuffer.position(var17 * var8);
            this.rawIntBuffer.put(var9);
         }

         var16.set(var17);
      }

      this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
      this.rawIntBuffer.position(this.getBufferSize());
      if (this.quadSprites != null) {
         TextureAtlasSprite[] var18 = new TextureAtlasSprite[this.vertexCount / 4];
         int var10000 = this.vertexFormat.getNextOffset() / 4 * 4;

         for (int var19 = 0; var19 < var15.length; var19++) {
            int var20 = var15[var19];
            var18[var19] = this.quadSprites[var20];
         }

         System.arraycopy(var18, 0, this.quadSprites, 0, var18.length);
      }
   }

   public WorldRenderer.State getVertexState() {
      this.rawIntBuffer.rewind();
      int var1 = this.getBufferSize();
      this.rawIntBuffer.limit(var1);
      int[] var2 = new int[var1];
      this.rawIntBuffer.get(var2);
      this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
      this.rawIntBuffer.position(var1);
      TextureAtlasSprite[] var3 = null;
      if (this.quadSprites != null) {
         int var4 = this.vertexCount / 4;
         var3 = new TextureAtlasSprite[var4];
         System.arraycopy(this.quadSprites, 0, var3, 0, var4);
      }

      return new WorldRenderer.State(var2, new VertexFormat(this.vertexFormat), var3);
   }

   public int getBufferSize() {
      return this.vertexCount * this.vertexFormat.getIntegerSize();
   }

   private static float getDistanceSq(FloatBuffer floatBuffer, float float2, float float3, float float4, int integer5, int integer6) {
      float var6 = floatBuffer.get(integer6);
      float var7 = floatBuffer.get(integer6 + 1);
      float var8 = floatBuffer.get(integer6 + 2);
      float var9 = floatBuffer.get(integer6 + integer5);
      float var10 = floatBuffer.get(integer6 + integer5 + 1);
      float var11 = floatBuffer.get(integer6 + integer5 + 2);
      float var12 = floatBuffer.get(integer6 + integer5 * 2);
      float var13 = floatBuffer.get(integer6 + integer5 * 2 + 1);
      float var14 = floatBuffer.get(integer6 + integer5 * 2 + 2);
      float var15 = floatBuffer.get(integer6 + integer5 * 3);
      float var16 = floatBuffer.get(integer6 + integer5 * 3 + 1);
      float var17 = floatBuffer.get(integer6 + integer5 * 3 + 2);
      float var18 = (var6 + var9 + var12 + var15) * 0.25F - float2;
      float var19 = (var7 + var10 + var13 + var16) * 0.25F - float3;
      float var20 = (var8 + var11 + var14 + var17) * 0.25F - float4;
      return var18 * var18 + var19 * var19 + var20 * var20;
   }

   public void setVertexState(WorldRenderer.State state) {
      this.rawIntBuffer.clear();
      this.growBuffer(state.getRawBuffer().length);
      this.rawIntBuffer.put(state.getRawBuffer());
      this.vertexCount = state.getVertexCount();
      this.vertexFormat = new VertexFormat(state.getVertexFormat());
      if (state.stateQuadSprites != null) {
         if (this.quadSprites == null) {
            this.quadSprites = this.quadSpritesPrev;
         }

         if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
            this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
         }

         TextureAtlasSprite[] var2 = state.stateQuadSprites;
         System.arraycopy(var2, 0, this.quadSprites, 0, var2.length);
      } else {
         if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
      }
   }

   public void reset() {
      this.vertexCount = 0;
      this.vertexFormatElement = null;
      this.vertexFormatIndex = 0;
      this.quadSprite = null;
      if (SmartAnimations.isActive()) {
         if (this.animatedSprites == null) {
            this.animatedSprites = this.animatedSpritesCached;
         }

         this.animatedSprites.clear();
      } else if (this.animatedSprites != null) {
         this.animatedSprites = null;
      }

      this.modeTriangles = false;
   }

   public void begin(int integer, VertexFormat vertexFormat) {
      if (this.isDrawing) {
         throw new IllegalStateException("Already building!");
      }

      this.isDrawing = true;
      this.reset();
      this.drawMode = integer;
      this.vertexFormat = vertexFormat;
      this.vertexFormatElement = vertexFormat.getElement(this.vertexFormatIndex);
      this.noColor = false;
      this.byteBuffer.limit(this.byteBuffer.capacity());
      if (Config.isShaders()) {
         SVertexBuilder.endSetVertexFormat(this);
      }

      if (Config.isMultiTexture()) {
         if (this.blockLayer != null) {
            if (this.quadSprites == null) {
               this.quadSprites = this.quadSpritesPrev;
            }

            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
               this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
         }
      } else {
         if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
      }
   }

   public WorldRenderer tex(double double1, double double2) {
      if (this.quadSprite != null && this.quadSprites != null) {
         double1 = this.quadSprite.toSingleU((float)double1);
         double2 = this.quadSprite.toSingleV((float)double2);
         this.quadSprites[this.vertexCount / 4] = this.quadSprite;
      }

      int var5 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
      switch (this.vertexFormatElement.getType()) {
         case FLOAT:
            this.byteBuffer.putFloat(var5, (float)double1);
            this.byteBuffer.putFloat(var5 + 4, (float)double2);
            break;
         case UINT:
         case INT:
            this.byteBuffer.putInt(var5, (int)double1);
            this.byteBuffer.putInt(var5 + 4, (int)double2);
            break;
         case USHORT:
         case SHORT:
            this.byteBuffer.putShort(var5, (short)double2);
            this.byteBuffer.putShort(var5 + 2, (short)double1);
            break;
         case UBYTE:
         case BYTE:
            this.byteBuffer.put(var5, (byte)double2);
            this.byteBuffer.put(var5 + 1, (byte)double1);
      }

      this.nextVertexFormatIndex();
      return this;
   }

   public WorldRenderer lightmap(int integer1, int integer2) {
      int var3 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
      switch (this.vertexFormatElement.getType()) {
         case FLOAT:
            this.byteBuffer.putFloat(var3, integer1);
            this.byteBuffer.putFloat(var3 + 4, integer2);
            break;
         case UINT:
         case INT:
            this.byteBuffer.putInt(var3, integer1);
            this.byteBuffer.putInt(var3 + 4, integer2);
            break;
         case USHORT:
         case SHORT:
            this.byteBuffer.putShort(var3, (short)integer2);
            this.byteBuffer.putShort(var3 + 2, (short)integer1);
            break;
         case UBYTE:
         case BYTE:
            this.byteBuffer.put(var3, (byte)integer2);
            this.byteBuffer.put(var3 + 1, (byte)integer1);
      }

      this.nextVertexFormatIndex();
      return this;
   }

   public void putBrightness4(int integer1, int integer2, int integer3, int integer4) {
      int var5 = (this.vertexCount - 4) * this.vertexFormat.getIntegerSize() + this.vertexFormat.getUvOffsetById(1) / 4;
      int var6 = this.vertexFormat.getNextOffset() >> 2;
      this.rawIntBuffer.put(var5, integer1);
      this.rawIntBuffer.put(var5 + var6, integer2);
      this.rawIntBuffer.put(var5 + var6 * 2, integer3);
      this.rawIntBuffer.put(var5 + var6 * 3, integer4);
   }

   public void putPosition(double double1, double double2, double double3) {
      int var7 = this.vertexFormat.getIntegerSize();
      int var8 = (this.vertexCount - 4) * var7;

      for (int var9 = 0; var9 < 4; var9++) {
         int var10 = var8 + var9 * var7;
         int var11 = var10 + 1;
         int var12 = var11 + 1;
         this.rawIntBuffer.put(var10, Float.floatToRawIntBits((float)(double1 + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var10))));
         this.rawIntBuffer.put(var11, Float.floatToRawIntBits((float)(double2 + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var11))));
         this.rawIntBuffer.put(var12, Float.floatToRawIntBits((float)(double3 + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var12))));
      }
   }

   public int getColorIndex(int integer) {
      return ((this.vertexCount - integer) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
   }

   public void putColorMultiplier(float float1, float float2, float float3, int integer) {
      int var7 = this.getColorIndex(integer);
      int var8 = -1;
      if (!this.noColor) {
         var8 = this.rawIntBuffer.get(var7);
         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int var9 = (int)((var8 & 0xFF) * float1);
            int var10 = (int)((var8 >> 8 & 0xFF) * float2);
            int var11 = (int)((var8 >> 16 & 0xFF) * float3);
            var8 &= -16777216;
            var8 = var8 | var11 << 16 | var10 << 8 | var9;
         } else {
            int var15 = (int)((var8 >> 24 & 0xFF) * float1);
            int var16 = (int)((var8 >> 16 & 0xFF) * float2);
            int var17 = (int)((var8 >> 8 & 0xFF) * float3);
            var8 &= 255;
            var8 = var8 | var15 << 24 | var16 << 16 | var17 << 8;
         }
      }

      this.rawIntBuffer.put(var7, var8);
   }

   private void putColor(int integer1, int integer2) {
      int var5 = this.getColorIndex(integer2);
      int var6 = integer1 >> 16 & 0xFF;
      int var7 = integer1 >> 8 & 0xFF;
      int var8 = integer1 & 0xFF;
      int var9 = integer1 >> 24 & 0xFF;
      this.putColorRGBA(var5, var6, var7, var8, var9);
   }

   public void putColorRGB_F(float float1, float float2, float float3, int integer) {
      int var7 = this.getColorIndex(integer);
      int var8 = MathHelper.clamp_int((int)(float1 * 255.0F), 0, 255);
      int var9 = MathHelper.clamp_int((int)(float2 * 255.0F), 0, 255);
      int var10 = MathHelper.clamp_int((int)(float3 * 255.0F), 0, 255);
      this.putColorRGBA(var7, var8, var9, var10, 255);
   }

   public void putColorRGBA(int integer1, int integer2, int integer3, int integer4, int integer5) {
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.rawIntBuffer.put(integer1, integer5 << 24 | integer4 << 16 | integer3 << 8 | integer2);
      } else {
         this.rawIntBuffer.put(integer1, integer2 << 24 | integer3 << 16 | integer4 << 8 | integer5);
      }
   }

   public void noColor() {
      this.noColor = true;
   }

   public WorldRenderer color(float float1, float float2, float float3, float float4) {
      return this.color((int)(float1 * 255.0F), (int)(float2 * 255.0F), (int)(float3 * 255.0F), (int)(float4 * 255.0F));
   }

   public WorldRenderer color(int integer1, int integer2, int integer3, int integer4) {
      if (!this.noColor) {
         int var7 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
         switch (this.vertexFormatElement.getType()) {
            case FLOAT:
               this.byteBuffer.putFloat(var7, integer1 / 255.0F);
               this.byteBuffer.putFloat(var7 + 4, integer2 / 255.0F);
               this.byteBuffer.putFloat(var7 + 8, integer3 / 255.0F);
               this.byteBuffer.putFloat(var7 + 12, integer4 / 255.0F);
               break;
            case UINT:
            case INT:
               this.byteBuffer.putFloat(var7, integer1);
               this.byteBuffer.putFloat(var7 + 4, integer2);
               this.byteBuffer.putFloat(var7 + 8, integer3);
               this.byteBuffer.putFloat(var7 + 12, integer4);
               break;
            case USHORT:
            case SHORT:
               this.byteBuffer.putShort(var7, (short)integer1);
               this.byteBuffer.putShort(var7 + 2, (short)integer2);
               this.byteBuffer.putShort(var7 + 4, (short)integer3);
               this.byteBuffer.putShort(var7 + 6, (short)integer4);
               break;
            case UBYTE:
            case BYTE:
               if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                  this.byteBuffer.put(var7, (byte)integer1);
                  this.byteBuffer.put(var7 + 1, (byte)integer2);
                  this.byteBuffer.put(var7 + 2, (byte)integer3);
                  this.byteBuffer.put(var7 + 3, (byte)integer4);
               } else {
                  this.byteBuffer.put(var7, (byte)integer4);
                  this.byteBuffer.put(var7 + 1, (byte)integer3);
                  this.byteBuffer.put(var7 + 2, (byte)integer2);
                  this.byteBuffer.put(var7 + 3, (byte)integer1);
               }
         }

         this.nextVertexFormatIndex();
      }

      return this;
   }

   public void addVertexData(int[] arr) {
      if (Config.isShaders()) {
         SVertexBuilder.beginAddVertexData(this, arr);
      }

      this.growBuffer(arr.length);
      this.rawIntBuffer.position(this.getBufferSize());
      this.rawIntBuffer.put(arr);
      this.vertexCount = this.vertexCount + arr.length / this.vertexFormat.getIntegerSize();
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertexData(this);
      }
   }

   public void endVertex() {
      this.vertexCount++;
      this.growBuffer(this.vertexFormat.getIntegerSize());
      this.vertexFormatIndex = 0;
      this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }
   }

   public WorldRenderer pos(double double1, double double2, double double3) {
      if (Config.isShaders()) {
         SVertexBuilder.beginAddVertex(this);
      }

      int var9 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
      switch (this.vertexFormatElement.getType()) {
         case FLOAT:
            this.byteBuffer.putFloat(var9, (float)(double1 + this.xOffset));
            this.byteBuffer.putFloat(var9 + 4, (float)(double2 + this.yOffset));
            this.byteBuffer.putFloat(var9 + 8, (float)(double3 + this.zOffset));
            break;
         case UINT:
         case INT:
            this.byteBuffer.putInt(var9, Float.floatToRawIntBits((float)(double1 + this.xOffset)));
            this.byteBuffer.putInt(var9 + 4, Float.floatToRawIntBits((float)(double2 + this.yOffset)));
            this.byteBuffer.putInt(var9 + 8, Float.floatToRawIntBits((float)(double3 + this.zOffset)));
            break;
         case USHORT:
         case SHORT:
            this.byteBuffer.putShort(var9, (short)(double1 + this.xOffset));
            this.byteBuffer.putShort(var9 + 2, (short)(double2 + this.yOffset));
            this.byteBuffer.putShort(var9 + 4, (short)(double3 + this.zOffset));
            break;
         case UBYTE:
         case BYTE:
            this.byteBuffer.put(var9, (byte)(double1 + this.xOffset));
            this.byteBuffer.put(var9 + 1, (byte)(double2 + this.yOffset));
            this.byteBuffer.put(var9 + 2, (byte)(double3 + this.zOffset));
      }

      this.nextVertexFormatIndex();
      return this;
   }

   public void putNormal(float float1, float float2, float float3) {
      int var6 = (byte)(float1 * 127.0F) & 255;
      int var7 = (byte)(float2 * 127.0F) & 255;
      int var8 = (byte)(float3 * 127.0F) & 255;
      int var9 = var6 | var7 << 8 | var8 << 16;
      int var10 = this.vertexFormat.getNextOffset() >> 2;
      int var11 = (this.vertexCount - 4) * var10 + this.vertexFormat.getNormalOffset() / 4;
      this.rawIntBuffer.put(var11, var9);
      this.rawIntBuffer.put(var11 + var10, var9);
      this.rawIntBuffer.put(var11 + var10 * 2, var9);
      this.rawIntBuffer.put(var11 + var10 * 3, var9);
   }

   private void nextVertexFormatIndex() {
      this.vertexFormatIndex++;
      this.vertexFormatIndex = this.vertexFormatIndex % this.vertexFormat.getElementCount();
      this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
      if (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
         this.nextVertexFormatIndex();
      }
   }

   public WorldRenderer normal(float float1, float float2, float float3) {
      int var6 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
      switch (this.vertexFormatElement.getType()) {
         case FLOAT:
            this.byteBuffer.putFloat(var6, float1);
            this.byteBuffer.putFloat(var6 + 4, float2);
            this.byteBuffer.putFloat(var6 + 8, float3);
            break;
         case UINT:
         case INT:
            this.byteBuffer.putInt(var6, (int)float1);
            this.byteBuffer.putInt(var6 + 4, (int)float2);
            this.byteBuffer.putInt(var6 + 8, (int)float3);
            break;
         case USHORT:
         case SHORT:
            this.byteBuffer.putShort(var6, (short)((int)(float1 * 32767.0F) & 65535));
            this.byteBuffer.putShort(var6 + 2, (short)((int)(float2 * 32767.0F) & 65535));
            this.byteBuffer.putShort(var6 + 4, (short)((int)(float3 * 32767.0F) & 65535));
            break;
         case UBYTE:
         case BYTE:
            this.byteBuffer.put(var6, (byte)((int)(float1 * 127.0F) & 0xFF));
            this.byteBuffer.put(var6 + 1, (byte)((int)(float2 * 127.0F) & 0xFF));
            this.byteBuffer.put(var6 + 2, (byte)((int)(float3 * 127.0F) & 0xFF));
      }

      this.nextVertexFormatIndex();
      return this;
   }

   public void setTranslation(double double1, double double2, double double3) {
      this.xOffset = double1;
      this.yOffset = double2;
      this.zOffset = double3;
   }

   public void finishDrawing() {
      if (!this.isDrawing) {
         throw new IllegalStateException("Not building!");
      }

      this.isDrawing = false;
      this.byteBuffer.position(0);
      this.byteBuffer.limit(this.getBufferSize() * 4);
   }

   public ByteBuffer getByteBuffer() {
      return this.modeTriangles ? this.byteBufferTriangles : this.byteBuffer;
   }

   public VertexFormat getVertexFormat() {
      return this.vertexFormat;
   }

   public int getVertexCount() {
      return this.modeTriangles ? this.vertexCount / 4 * 6 : this.vertexCount;
   }

   public int getDrawMode() {
      return this.modeTriangles ? 4 : this.drawMode;
   }

   public void putColor4(int integer) {
      for (int var2 = 0; var2 < 4; var2++) {
         this.putColor(integer, var2 + 1);
      }
   }

   public void putColorRGB_F4(float float1, float float2, float float3) {
      for (int var4 = 0; var4 < 4; var4++) {
         this.putColorRGB_F(float1, float2, float3, var4 + 1);
      }
   }

   public void putSprite(TextureAtlasSprite textureAtlasSprite) {
      if (this.animatedSprites != null && textureAtlasSprite != null && textureAtlasSprite.getAnimationIndex() >= 0) {
         this.animatedSprites.set(textureAtlasSprite.getAnimationIndex());
      }

      if (this.quadSprites != null) {
         int var2 = this.vertexCount / 4;
         this.quadSprites[var2 - 1] = textureAtlasSprite;
      }
   }

   public void setSprite(TextureAtlasSprite textureAtlasSprite) {
      if (this.animatedSprites != null && textureAtlasSprite != null && textureAtlasSprite.getAnimationIndex() >= 0) {
         this.animatedSprites.set(textureAtlasSprite.getAnimationIndex());
      }

      if (this.quadSprites != null) {
         this.quadSprite = textureAtlasSprite;
      }
   }

   public boolean isMultiTexture() {
      return this.quadSprites != null;
   }

   public void drawMultiTexture() {
      if (this.quadSprites != null) {
         int var1 = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
         if (this.drawnIcons.length <= var1) {
            this.drawnIcons = new boolean[var1 + 1];
         }

         Arrays.fill(this.drawnIcons, false);
         int var2 = 0;
         int var3 = -1;
         int var4 = this.vertexCount / 4;

         for (int var5 = 0; var5 < var4; var5++) {
            TextureAtlasSprite var6 = this.quadSprites[var5];
            if (var6 != null) {
               int var7 = var6.getIndexInMap();
               if (!this.drawnIcons[var7]) {
                  if (var6 == TextureUtils.iconGrassSideOverlay) {
                     if (var3 < 0) {
                        var3 = var5;
                     }
                  } else {
                     var5 = this.drawForIcon(var6, var5) - 1;
                     var2++;
                     if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                        this.drawnIcons[var7] = true;
                     }
                  }
               }
            }
         }

         if (var3 >= 0) {
            this.drawForIcon(TextureUtils.iconGrassSideOverlay, var3);
            var2++;
         }

         if (var2 > 0) {
         }
      }
   }

   private int drawForIcon(TextureAtlasSprite textureAtlasSprite, int integer) {
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureAtlasSprite.glSpriteTextureId);
      int var5 = -1;
      int var6 = -1;
      int var7 = this.vertexCount / 4;

      for (int var8 = integer; var8 < var7; var8++) {
         TextureAtlasSprite var9 = this.quadSprites[var8];
         if (var9 == textureAtlasSprite) {
            if (var6 < 0) {
               var6 = var8;
            }
         } else if (var6 >= 0) {
            this.draw(var6, var8);
            if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
               return var8;
            }

            var6 = -1;
            if (var5 < 0) {
               var5 = var8;
            }
         }
      }

      if (var6 >= 0) {
         this.draw(var6, var7);
      }

      if (var5 < 0) {
         var5 = var7;
      }

      return var5;
   }

   private void draw(int integer1, int integer2) {
      int var3 = integer2 - integer1;
      if (var3 > 0) {
         int var4 = integer1 * 4;
         int var5 = var3 * 4;
         GL11.glDrawArrays(this.drawMode, var4, var5);
      }
   }

   public void setBlockLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
      this.blockLayer = enumWorldBlockLayer;
      if (enumWorldBlockLayer == null) {
         if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
         this.quadSprite = null;
      }
   }

   private int getBufferQuadSize() {
      return this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.getIntegerSize() * 4);
   }

   public RenderEnv getRenderEnv(IBlockState iBlockState, BlockPos blockPos) {
      if (this.renderEnv == null) {
         this.renderEnv = new RenderEnv(iBlockState, blockPos);
      } else {
         this.renderEnv.reset(iBlockState, blockPos);
      }

      return this.renderEnv;
   }

   public boolean isDrawing() {
      return this.isDrawing;
   }

   public double getXOffset() {
      return this.xOffset;
   }

   public double getYOffset() {
      return this.yOffset;
   }

   public double getZOffset() {
      return this.zOffset;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.blockLayer;
   }

   public void putColorMultiplierRgba(float float1, float float2, float float3, float float4, int integer) {
      int var8 = this.getColorIndex(integer);
      int var9 = -1;
      if (!this.noColor) {
         var9 = this.rawIntBuffer.get(var8);
         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int var10 = (int)((var9 & 0xFF) * float1);
            int var11 = (int)((var9 >> 8 & 0xFF) * float2);
            int var12 = (int)((var9 >> 16 & 0xFF) * float3);
            var9 = (int)((var9 >> 24 & 0xFF) * float4) << 24 | var12 << 16 | var11 << 8 | var10;
         } else {
            int var15 = (int)((var9 >> 24 & 0xFF) * float1);
            int var16 = (int)((var9 >> 16 & 0xFF) * float2);
            int var17 = (int)((var9 >> 8 & 0xFF) * float3);
            int var13 = (int)((var9 & 0xFF) * float4);
            var9 = var15 << 24 | var16 << 16 | var17 << 8 | var13;
         }
      }

      this.rawIntBuffer.put(var8, var9);
   }

   public void quadsToTriangles() {
      if (this.drawMode == 7) {
         if (this.byteBufferTriangles == null) {
            this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
         }

         if (this.byteBufferTriangles.capacity() < this.byteBuffer.capacity() * 2) {
            this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
         }

         int var3 = this.vertexFormat.getNextOffset();
         int var4 = this.byteBuffer.limit();
         this.byteBuffer.rewind();
         this.byteBufferTriangles.clear();

         for (byte var5 = 0; var5 < this.vertexCount; var5 += 4) {
            this.byteBuffer.limit((var5 + 3) * var3);
            this.byteBuffer.position(var5 * var3);
            this.byteBufferTriangles.put(this.byteBuffer);
            this.byteBuffer.limit((var5 + 1) * var3);
            this.byteBuffer.position(var5 * var3);
            this.byteBufferTriangles.put(this.byteBuffer);
            this.byteBuffer.limit((var5 + 2 + 2) * var3);
            this.byteBuffer.position((var5 + 2) * var3);
            this.byteBufferTriangles.put(this.byteBuffer);
         }

         this.byteBuffer.limit(var4);
         this.byteBuffer.rewind();
         this.byteBufferTriangles.flip();
         this.modeTriangles = true;
      }
   }

   public boolean isColorDisabled() {
      return this.noColor;
   }

   public static class State {
      private final int[] stateRawBuffer;
      private final VertexFormat stateVertexFormat;
      private TextureAtlasSprite[] stateQuadSprites;

      public State(int[] arr, VertexFormat vertexFormat, TextureAtlasSprite[] textureAtlasSprites) {
         this.stateRawBuffer = arr;
         this.stateVertexFormat = vertexFormat;
         this.stateQuadSprites = textureAtlasSprites;
      }

      public State(int[] arr, VertexFormat vertexFormat) {
         this.stateRawBuffer = arr;
         this.stateVertexFormat = vertexFormat;
      }

      public int[] getRawBuffer() {
         return this.stateRawBuffer;
      }

      public int getVertexCount() {
         return this.stateRawBuffer.length / this.stateVertexFormat.getIntegerSize();
      }

      public VertexFormat getVertexFormat() {
         return this.stateVertexFormat;
      }
   }
}
