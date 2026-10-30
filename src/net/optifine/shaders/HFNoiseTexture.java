package net.optifine.shaders;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class HFNoiseTexture implements ICustomTexture {
   private int texID = GL11.glGenTextures();
   private final int b = 15;

   public HFNoiseTexture(int integer1, int integer2) {
      byte[] var5 = this.genHFNoiseImage(integer1, integer2);
      ByteBuffer var6 = BufferUtils.createByteBuffer(var5.length);
      var6.put(var5);
      var6.flip();
      GlStateManager.bindTexture(this.texID);
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, integer1, integer2, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, var6);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
      GlStateManager.bindTexture(0);
   }

   public int getID() {
      return this.texID;
   }

   @Override
   public void deleteTexture() {
      GlStateManager.deleteTexture(this.texID);
      this.texID = 0;
   }

   private int random(int integer) {
      integer ^= integer << 13;
      integer ^= integer >> 17;
      return integer ^ integer << 5;
   }

   private byte random(int integer1, int integer2, int integer3) {
      int var6 = (this.random(integer1) + this.random(integer2 * 19)) * this.random(integer3 * 23) - integer3;
      return (byte)(this.random(var6) % 128);
   }

   private byte[] genHFNoiseImage(int integer1, int integer2) {
      byte[] var6 = new byte[integer1 * integer2 * 3];
      int var7 = 0;

      for (int var8 = 0; var8 < integer2; var8++) {
         for (int var9 = 0; var9 < integer1; var9++) {
            for (int var10 = 1; var10 < 4; var10++) {
               var6[var7++] = this.random(var9, var8, var10);
            }
         }
      }

      return var6;
   }

   @Override
   public int getTextureId() {
      return this.texID;
   }

   @Override
   public int getTextureUnit() {
      return 15;
   }

   @Override
   public int getTarget() {
      return 3553;
   }
}
