package net.optifine.shaders;

import java.nio.ByteBuffer;
import net.optifine.texture.InternalFormat;
import net.optifine.texture.PixelFormat;
import net.optifine.texture.PixelType;
import net.optifine.texture.TextureType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL31;

public class CustomTextureRaw implements ICustomTexture {
   private final TextureType type;
   private final int textureUnit;
   private int textureId;

   public CustomTextureRaw(
      TextureType textureType,
      InternalFormat internalFormat,
      int integer3,
      int integer4,
      int integer5,
      PixelFormat pixelFormat,
      PixelType pixelType,
      ByteBuffer byteBuffer,
      int integer9,
      boolean boolean10,
      boolean boolean11
   ) {
      this.type = textureType;
      this.textureUnit = integer9;
      this.textureId = GL11.glGenTextures();
      GL11.glBindTexture(this.getTarget(), this.textureId);
      int var15 = boolean11 ? 33071 : 10497;
      int var16 = boolean10 ? 9729 : 9728;
      switch (textureType) {
         case TEXTURE_1D:
            GL11.glTexImage1D(GL11.GL_TEXTURE_1D, 0, internalFormat.getId(), integer3, 0, pixelFormat.getId(), pixelType.getId(), byteBuffer);
            GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_WRAP_S, var15);
            GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MAG_FILTER, var16);
            GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MIN_FILTER, var16);
            break;
         case TEXTURE_2D:
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat.getId(), integer3, integer4, 0, pixelFormat.getId(), pixelType.getId(), byteBuffer);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var15);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var15);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var16);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var16);
            break;
         case TEXTURE_3D:
            GL12.glTexImage3D(GL12.GL_TEXTURE_3D, 0, internalFormat.getId(), integer3, integer4, integer5, 0, pixelFormat.getId(), pixelType.getId(), byteBuffer);
            GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_S, var15);
            GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_T, var15);
            GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL12.GL_TEXTURE_WRAP_R, var15);
            GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MAG_FILTER, var16);
            GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MIN_FILTER, var16);
            break;
         case TEXTURE_RECTANGLE:
            GL11.glTexImage2D(GL31.GL_TEXTURE_RECTANGLE, 0, internalFormat.getId(), integer3, integer4, 0, pixelFormat.getId(), pixelType.getId(), byteBuffer);
            GL11.glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL11.GL_TEXTURE_WRAP_S, var15);
            GL11.glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL11.GL_TEXTURE_WRAP_T, var15);
            GL11.glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL11.GL_TEXTURE_MAG_FILTER, var16);
            GL11.glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL11.GL_TEXTURE_MIN_FILTER, var16);
      }

      GL11.glBindTexture(this.getTarget(), 0);
   }

   @Override
   public int getTarget() {
      return this.type.getId();
   }

   @Override
   public int getTextureId() {
      return this.textureId;
   }

   @Override
   public int getTextureUnit() {
      return this.textureUnit;
   }

   @Override
   public void deleteTexture() {
      if (this.textureId > 0) {
         GL11.glDeleteTextures(this.textureId);
         this.textureId = 0;
      }
   }
}
