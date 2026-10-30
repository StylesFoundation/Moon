package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.src.Config;
import net.optifine.SmartAnimations;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.Shaders;
import net.optifine.util.LockCounter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL13;

public class GlStateManager {
   private static final GlStateManager.AlphaState alphaState = new GlStateManager.AlphaState();
   private static final GlStateManager.BooleanState lightingState = new GlStateManager.BooleanState(2896);
   private static final GlStateManager.BooleanState[] lightState = new GlStateManager.BooleanState[8];
   private static final GlStateManager.ColorMaterialState colorMaterialState = new GlStateManager.ColorMaterialState();
   private static final GlStateManager.BlendState blendState = new GlStateManager.BlendState();
   private static final GlStateManager.DepthState depthState = new GlStateManager.DepthState();
   private static final GlStateManager.FogState fogState = new GlStateManager.FogState();
   private static final GlStateManager.CullState cullState = new GlStateManager.CullState();
   private static final GlStateManager.PolygonOffsetState polygonOffsetState = new GlStateManager.PolygonOffsetState();
   private static final GlStateManager.ColorLogicState colorLogicState = new GlStateManager.ColorLogicState();
   private static final GlStateManager.TexGenState texGenState = new GlStateManager.TexGenState();
   private static final GlStateManager.ClearState clearState = new GlStateManager.ClearState();
   private static final GlStateManager.StencilState stencilState = new GlStateManager.StencilState();
   private static final GlStateManager.BooleanState normalizeState = new GlStateManager.BooleanState(2977);
   private static int activeTextureUnit = 0;
   private static final GlStateManager.TextureState[] textureState = new GlStateManager.TextureState[32];
   private static int activeShadeModel = 7425;
   private static final GlStateManager.BooleanState rescaleNormalState = new GlStateManager.BooleanState(32826);
   private static final GlStateManager.ColorMask colorMaskState = new GlStateManager.ColorMask();
   private static final GlStateManager.Color colorState = new GlStateManager.Color();
   private static final LockCounter alphaLock = new LockCounter();
   private static final GlAlphaState alphaLockState = new GlAlphaState();
   private static final LockCounter blendLock = new LockCounter();
   private static final GlBlendState blendLockState = new GlBlendState();
   private static boolean creatingDisplayList = false;

   public static void pushAttrib() {
      GL11.glPushAttrib(8256);
   }

   public static void popAttrib() {
      GL11.glPopAttrib();
   }

   public static void disableAlpha() {
      if (alphaLock.isLocked()) {
         alphaLockState.setDisabled();
      } else {
         alphaState.alphaTest.setDisabled();
      }
   }

   public static void enableAlpha() {
      if (alphaLock.isLocked()) {
         alphaLockState.setEnabled();
      } else {
         alphaState.alphaTest.setEnabled();
      }
   }

   public static void alphaFunc(int integer, float float2) {
      if (alphaLock.isLocked()) {
         alphaLockState.setFuncRef(integer, float2);
      } else if (integer != alphaState.func || float2 != alphaState.ref) {
         alphaState.func = integer;
         alphaState.ref = float2;
         GL11.glAlphaFunc(integer, float2);
      }
   }

   public static void enableLighting() {
      lightingState.setEnabled();
   }

   public static void disableLighting() {
      lightingState.setDisabled();
   }

   public static void enableLight(int integer) {
      lightState[integer].setEnabled();
   }

   public static void disableLight(int integer) {
      lightState[integer].setDisabled();
   }

   public static void enableColorMaterial() {
      colorMaterialState.colorMaterial.setEnabled();
   }

   public static void disableColorMaterial() {
      colorMaterialState.colorMaterial.setDisabled();
   }

   public static void colorMaterial(int integer1, int integer2) {
      if (integer1 != colorMaterialState.face || integer2 != colorMaterialState.mode) {
         colorMaterialState.face = integer1;
         colorMaterialState.mode = integer2;
         GL11.glColorMaterial(integer1, integer2);
      }
   }

   public static void disableDepth() {
      depthState.depthTest.setDisabled();
   }

   public static void enableDepth() {
      depthState.depthTest.setEnabled();
   }

   public static void depthFunc(int integer) {
      if (integer != depthState.depthFunc) {
         depthState.depthFunc = integer;
         GL11.glDepthFunc(integer);
      }
   }

   public static void depthMask(boolean boolean1) {
      if (boolean1 != depthState.maskEnabled) {
         depthState.maskEnabled = boolean1;
         GL11.glDepthMask(boolean1);
      }
   }

   public static void disableBlend() {
      if (blendLock.isLocked()) {
         blendLockState.setDisabled();
      } else {
         blendState.blend.setDisabled();
      }
   }

   public static void enableBlend() {
      if (blendLock.isLocked()) {
         blendLockState.setEnabled();
      } else {
         blendState.blend.setEnabled();
      }
   }

   public static void blendFunc(int integer1, int integer2) {
      if (blendLock.isLocked()) {
         blendLockState.setFactors(integer1, integer2);
      } else if (integer1 != blendState.srcFactor
         || integer2 != blendState.dstFactor
         || integer1 != blendState.srcFactorAlpha
         || integer2 != blendState.dstFactorAlpha) {
         blendState.srcFactor = integer1;
         blendState.dstFactor = integer2;
         blendState.srcFactorAlpha = integer1;
         blendState.dstFactorAlpha = integer2;
         if (Config.isShaders()) {
            Shaders.uniform_blendFunc.setValue(integer1, integer2, integer1, integer2);
         }

         GL11.glBlendFunc(integer1, integer2);
      }
   }

   public static void tryBlendFuncSeparate(int integer1, int integer2, int integer3, int integer4) {
      if (blendLock.isLocked()) {
         blendLockState.setFactors(integer1, integer2, integer3, integer4);
      } else if (integer1 != blendState.srcFactor
         || integer2 != blendState.dstFactor
         || integer3 != blendState.srcFactorAlpha
         || integer4 != blendState.dstFactorAlpha) {
         blendState.srcFactor = integer1;
         blendState.dstFactor = integer2;
         blendState.srcFactorAlpha = integer3;
         blendState.dstFactorAlpha = integer4;
         if (Config.isShaders()) {
            Shaders.uniform_blendFunc.setValue(integer1, integer2, integer3, integer4);
         }

         OpenGlHelper.glBlendFunc(integer1, integer2, integer3, integer4);
      }
   }

   public static void enableFog() {
      fogState.fog.setEnabled();
   }

   public static void disableFog() {
      fogState.fog.setDisabled();
   }

   public static void setFog(int integer) {
      if (integer != fogState.mode) {
         fogState.mode = integer;
         GL11.glFogi(GL11.GL_FOG_MODE, integer);
         if (Config.isShaders()) {
            Shaders.setFogMode(integer);
         }
      }
   }

   public static void setFogDensity(float float1) {
      if (float1 < 0.0F) {
         float1 = 0.0F;
      }

      if (float1 != fogState.density) {
         fogState.density = float1;
         GL11.glFogf(GL11.GL_FOG_DENSITY, float1);
         if (Config.isShaders()) {
            Shaders.setFogDensity(float1);
         }
      }
   }

   public static void setFogStart(float float1) {
      if (float1 != fogState.start) {
         fogState.start = float1;
         GL11.glFogf(GL11.GL_FOG_START, float1);
      }
   }

   public static void setFogEnd(float float1) {
      if (float1 != fogState.end) {
         fogState.end = float1;
         GL11.glFogf(GL11.GL_FOG_END, float1);
      }
   }

   public static void glFog(int integer, FloatBuffer floatBuffer) {
      GL11.glFog(integer, floatBuffer);
   }

   public static void glFogi(int integer1, int integer2) {
      GL11.glFogi(integer1, integer2);
   }

   public static void enableCull() {
      cullState.cullFace.setEnabled();
   }

   public static void disableCull() {
      cullState.cullFace.setDisabled();
   }

   public static void cullFace(int integer) {
      if (integer != cullState.mode) {
         cullState.mode = integer;
         GL11.glCullFace(integer);
      }
   }

   public static void enablePolygonOffset() {
      polygonOffsetState.polygonOffsetFill.setEnabled();
   }

   public static void disablePolygonOffset() {
      polygonOffsetState.polygonOffsetFill.setDisabled();
   }

   public static void doPolygonOffset(float float1, float float2) {
      if (float1 != polygonOffsetState.factor || float2 != polygonOffsetState.units) {
         polygonOffsetState.factor = float1;
         polygonOffsetState.units = float2;
         GL11.glPolygonOffset(float1, float2);
      }
   }

   public static void enableColorLogic() {
      colorLogicState.colorLogicOp.setEnabled();
   }

   public static void disableColorLogic() {
      colorLogicState.colorLogicOp.setDisabled();
   }

   public static void colorLogicOp(int integer) {
      if (integer != colorLogicState.opcode) {
         colorLogicState.opcode = integer;
         GL11.glLogicOp(integer);
      }
   }

   public static void enableTexGenCoord(GlStateManager.TexGen texGen) {
      texGenCoord(texGen).textureGen.setEnabled();
   }

   public static void disableTexGenCoord(GlStateManager.TexGen texGen) {
      texGenCoord(texGen).textureGen.setDisabled();
   }

   public static void texGen(GlStateManager.TexGen texGen, int integer) {
      GlStateManager.TexGenCoord var4 = texGenCoord(texGen);
      if (integer != var4.param) {
         var4.param = integer;
         GL11.glTexGeni(var4.coord, GL11.GL_TEXTURE_GEN_MODE, integer);
      }
   }

   public static void texGen(GlStateManager.TexGen texGen, int integer, FloatBuffer floatBuffer) {
      GL11.glTexGen(texGenCoord(texGen).coord, integer, floatBuffer);
   }

   private static GlStateManager.TexGenCoord texGenCoord(GlStateManager.TexGen texGen) {
      switch (texGen) {
         case T:
            return texGenState.t;
         case R:
            return texGenState.r;
         case Q:
            return texGenState.q;
         default:
            return texGenState.s;
      }
   }

   public static void setActiveTexture(int integer) {
      if (activeTextureUnit != integer - OpenGlHelper.defaultTexUnit) {
         activeTextureUnit = integer - OpenGlHelper.defaultTexUnit;
         OpenGlHelper.setActiveTexture(integer);
      }
   }

   public static void enableTexture2D() {
      textureState[activeTextureUnit].texture2DState.setEnabled();
   }

   public static void disableTexture2D() {
      textureState[activeTextureUnit].texture2DState.setDisabled();
   }

   public static int generateTexture() {
      return GL11.glGenTextures();
   }

   public static void deleteTexture(int integer) {
      if (integer != 0) {
         GL11.glDeleteTextures(integer);

         for (GlStateManager.TextureState var4 : textureState) {
            if (var4.textureName == integer) {
               var4.textureName = 0;
            }
         }
      }
   }

   public static void bindTexture(int integer) {
      if (integer != textureState[activeTextureUnit].textureName) {
         textureState[activeTextureUnit].textureName = integer;
         GL11.glBindTexture(GL11.GL_TEXTURE_2D, integer);
         if (SmartAnimations.isActive()) {
            SmartAnimations.textureRendered(integer);
         }
      }
   }

   public static void enableNormalize() {
      normalizeState.setEnabled();
   }

   public static void disableNormalize() {
      normalizeState.setDisabled();
   }

   public static void shadeModel(int integer) {
      if (integer != activeShadeModel) {
         activeShadeModel = integer;
         GL11.glShadeModel(integer);
      }
   }

   public static void enableRescaleNormal() {
      rescaleNormalState.setEnabled();
   }

   public static void disableRescaleNormal() {
      rescaleNormalState.setDisabled();
   }

   public static void viewport(int integer1, int integer2, int integer3, int integer4) {
      GL11.glViewport(integer1, integer2, integer3, integer4);
   }

   public static void colorMask(boolean boolean1, boolean boolean2, boolean boolean3, boolean boolean4) {
      if (boolean1 != colorMaskState.red || boolean2 != colorMaskState.green || boolean3 != colorMaskState.blue || boolean4 != colorMaskState.alpha) {
         colorMaskState.red = boolean1;
         colorMaskState.green = boolean2;
         colorMaskState.blue = boolean3;
         colorMaskState.alpha = boolean4;
         GL11.glColorMask(boolean1, boolean2, boolean3, boolean4);
      }
   }

   public static void clearDepth(double double1) {
      if (double1 != clearState.depth) {
         clearState.depth = double1;
         GL11.glClearDepth(double1);
      }
   }

   public static void clearColor(float float1, float float2, float float3, float float4) {
      if (float1 != clearState.color.red || float2 != clearState.color.green || float3 != clearState.color.blue || float4 != clearState.color.alpha) {
         clearState.color.red = float1;
         clearState.color.green = float2;
         clearState.color.blue = float3;
         clearState.color.alpha = float4;
         GL11.glClearColor(float1, float2, float3, float4);
      }
   }

   public static void clear(int integer) {
      if (true) {
         GL11.glClear(integer);
      }
   }

   public static void matrixMode(int integer) {
      GL11.glMatrixMode(integer);
   }

   public static void loadIdentity() {
      GL11.glLoadIdentity();
   }

   public static void pushMatrix() {
      GL11.glPushMatrix();
   }

   public static void popMatrix() {
      GL11.glPopMatrix();
   }

   public static void getFloat(int integer, FloatBuffer floatBuffer) {
      GL11.glGetFloat(integer, floatBuffer);
   }

   public static void ortho(double double1, double double2, double double3, double double4, double double5, double double6) {
      GL11.glOrtho(double1, double2, double3, double4, double5, double6);
   }

   public static void rotate(float float1, float float2, float float3, float float4) {
      GL11.glRotatef(float1, float2, float3, float4);
   }

   public static void scale(float float1, float float2, float float3) {
      GL11.glScalef(float1, float2, float3);
   }

   public static void scale(double double1, double double2, double double3) {
      GL11.glScaled(double1, double2, double3);
   }

   public static void translate(float float1, float float2, float float3) {
      GL11.glTranslatef(float1, float2, float3);
   }

   public static void translate(double double1, double double2, double double3) {
      GL11.glTranslated(double1, double2, double3);
   }

   public static void multMatrix(FloatBuffer floatBuffer) {
      GL11.glMultMatrix(floatBuffer);
   }

   public static void color(float float1, float float2, float float3, float float4) {
      if (float1 != colorState.red || float2 != colorState.green || float3 != colorState.blue || float4 != colorState.alpha) {
         colorState.red = float1;
         colorState.green = float2;
         colorState.blue = float3;
         colorState.alpha = float4;
         GL11.glColor4f(float1, float2, float3, float4);
      }
   }

   public static void color(float float1, float float2, float float3) {
      color(float1, float2, float3, 1.0F);
   }

   public static void resetColor() {
      colorState.red = colorState.green = colorState.blue = colorState.alpha = -1.0F;
   }

   public static void glNormalPointer(int integer1, int integer2, ByteBuffer byteBuffer) {
      GL11.glNormalPointer(integer1, integer2, byteBuffer);
   }

   public static void glTexCoordPointer(int integer1, int integer2, int integer3, int integer4) {
      GL11.glTexCoordPointer(integer1, integer2, integer3, integer4);
   }

   public static void glTexCoordPointer(int integer1, int integer2, int integer3, ByteBuffer byteBuffer) {
      GL11.glTexCoordPointer(integer1, integer2, integer3, byteBuffer);
   }

   public static void glVertexPointer(int integer1, int integer2, int integer3, int integer4) {
      GL11.glVertexPointer(integer1, integer2, integer3, integer4);
   }

   public static void glVertexPointer(int integer1, int integer2, int integer3, ByteBuffer byteBuffer) {
      GL11.glVertexPointer(integer1, integer2, integer3, byteBuffer);
   }

   public static void glColorPointer(int integer1, int integer2, int integer3, int integer4) {
      GL11.glColorPointer(integer1, integer2, integer3, integer4);
   }

   public static void glColorPointer(int integer1, int integer2, int integer3, ByteBuffer byteBuffer) {
      GL11.glColorPointer(integer1, integer2, integer3, byteBuffer);
   }

   public static void glDisableClientState(int integer) {
      GL11.glDisableClientState(integer);
   }

   public static void glEnableClientState(int integer) {
      GL11.glEnableClientState(integer);
   }

   public static void glBegin(int integer) {
      GL11.glBegin(integer);
   }

   public static void glEnd() {
      GL11.glEnd();
   }

   public static void glDrawArrays(int integer1, int integer2, int integer3) {
      GL11.glDrawArrays(integer1, integer2, integer3);
      if (Config.isShaders() && !creatingDisplayList) {
         int var3 = Shaders.activeProgram.getCountInstances();
         if (var3 > 1) {
            for (int var4 = 1; var4 < var3; var4++) {
               Shaders.uniform_instanceId.setValue(var4);
               GL11.glDrawArrays(integer1, integer2, integer3);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void callList(int integer) {
      GL11.glCallList(integer);
      if (Config.isShaders() && !creatingDisplayList) {
         int var1 = Shaders.activeProgram.getCountInstances();
         if (var1 > 1) {
            for (int var2 = 1; var2 < var1; var2++) {
               Shaders.uniform_instanceId.setValue(var2);
               GL11.glCallList(integer);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void callLists(IntBuffer intBuffer) {
      GL11.glCallLists(intBuffer);
      if (Config.isShaders() && !creatingDisplayList) {
         int var1 = Shaders.activeProgram.getCountInstances();
         if (var1 > 1) {
            for (int var2 = 1; var2 < var1; var2++) {
               Shaders.uniform_instanceId.setValue(var2);
               GL11.glCallLists(intBuffer);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   public static void glDeleteLists(int integer1, int integer2) {
      GL11.glDeleteLists(integer1, integer2);
   }

   public static void glNewList(int integer1, int integer2) {
      GL11.glNewList(integer1, integer2);
      creatingDisplayList = true;
   }

   public static void glEndList() {
      GL11.glEndList();
      creatingDisplayList = false;
   }

   public static int glGetError() {
      return GL11.glGetError();
   }

   public static void glTexImage2D(
      int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, IntBuffer intBuffer
   ) {
      GL11.glTexImage2D(integer1, integer2, integer3, integer4, integer5, integer6, integer7, integer8, intBuffer);
   }

   public static void glTexSubImage2D(
      int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, IntBuffer intBuffer
   ) {
      GL11.glTexSubImage2D(integer1, integer2, integer3, integer4, integer5, integer6, integer7, integer8, intBuffer);
   }

   public static void glCopyTexSubImage2D(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
      GL11.glCopyTexSubImage2D(integer1, integer2, integer3, integer4, integer5, integer6, integer7, integer8);
   }

   public static void glGetTexImage(int integer1, int integer2, int integer3, int integer4, IntBuffer intBuffer) {
      GL11.glGetTexImage(integer1, integer2, integer3, integer4, intBuffer);
   }

   public static void glTexParameterf(int integer1, int integer2, float float3) {
      GL11.glTexParameterf(integer1, integer2, float3);
   }

   public static void glTexParameteri(int integer1, int integer2, int integer3) {
      GL11.glTexParameteri(integer1, integer2, integer3);
   }

   public static int glGetTexLevelParameteri(int integer1, int integer2, int integer3) {
      return GL11.glGetTexLevelParameteri(integer1, integer2, integer3);
   }

   public static int getActiveTextureUnit() {
      return OpenGlHelper.defaultTexUnit + activeTextureUnit;
   }

   public static void bindCurrentTexture() {
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureState[activeTextureUnit].textureName);
   }

   public static int getBoundTexture() {
      return textureState[activeTextureUnit].textureName;
   }

   public static void checkBoundTexture() {
      if (Config.isMinecraftThread()) {
         int var2 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
         int var3 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
         int var4 = getActiveTextureUnit();
         int var5 = getBoundTexture();
         if (var5 > 0 && (var2 != var4 || var3 != var5)) {
            Config.Method251("checkTexture: act: " + var4 + ", glAct: " + var2 + ", tex: " + var5 + ", glTex: " + var3);
         }
      }
   }

   public static void deleteTextures(IntBuffer intBuffer) {
      intBuffer.rewind();

      while (intBuffer.position() < intBuffer.limit()) {
         deleteTexture(intBuffer.get());
      }

      intBuffer.rewind();
   }

   public static boolean isFogEnabled() {
      return fogState.fog.currentState;
   }

   public static void setFogEnabled(boolean boolean1) {
      fogState.fog.setState(boolean1);
   }

   public static void lockAlpha(GlAlphaState glAlphaState) {
      if (!alphaLock.isLocked()) {
         getAlphaState(alphaLockState);
         setAlphaState(glAlphaState);
         alphaLock.lock();
      }
   }

   public static void unlockAlpha() {
      if (alphaLock.unlock()) {
         setAlphaState(alphaLockState);
      }
   }

   public static void getAlphaState(GlAlphaState glAlphaState) {
      if (alphaLock.isLocked()) {
         glAlphaState.setState(alphaLockState);
      } else {
         glAlphaState.setState(alphaState.alphaTest.currentState, alphaState.func, alphaState.ref);
      }
   }

   public static void setAlphaState(GlAlphaState glAlphaState) {
      if (alphaLock.isLocked()) {
         alphaLockState.setState(glAlphaState);
      } else {
         alphaState.alphaTest.setState(glAlphaState.isEnabled());
         alphaFunc(glAlphaState.getFunc(), glAlphaState.getRef());
      }
   }

   public static void lockBlend(GlBlendState glBlendState) {
      if (!blendLock.isLocked()) {
         getBlendState(blendLockState);
         setBlendState(glBlendState);
         blendLock.lock();
      }
   }

   public static void unlockBlend() {
      if (blendLock.unlock()) {
         setBlendState(blendLockState);
      }
   }

   public static void getBlendState(GlBlendState glBlendState) {
      if (blendLock.isLocked()) {
         glBlendState.setState(blendLockState);
      } else {
         glBlendState.setState(blendState.blend.currentState, blendState.srcFactor, blendState.dstFactor, blendState.srcFactorAlpha, blendState.dstFactorAlpha);
      }
   }

   public static void setBlendState(GlBlendState glBlendState) {
      if (blendLock.isLocked()) {
         blendLockState.setState(glBlendState);
      } else {
         blendState.blend.setState(glBlendState.isEnabled());
         if (!glBlendState.isSeparate()) {
            blendFunc(glBlendState.Method226(), glBlendState.Method227());
         } else {
            tryBlendFuncSeparate(glBlendState.Method226(), glBlendState.Method227(), glBlendState.Method228(), glBlendState.Method229());
         }
      }
   }

   public static void glMultiDrawArrays(int integer, IntBuffer intBuffer2, IntBuffer intBuffer3) {
      GL14.glMultiDrawArrays(integer, intBuffer2, intBuffer3);
      if (Config.isShaders() && !creatingDisplayList) {
         int var3 = Shaders.activeProgram.getCountInstances();
         if (var3 > 1) {
            for (int var4 = 1; var4 < var3; var4++) {
               Shaders.uniform_instanceId.setValue(var4);
               GL14.glMultiDrawArrays(integer, intBuffer2, intBuffer3);
            }

            Shaders.uniform_instanceId.setValue(0);
         }
      }
   }

   static {
      for (int var13 = 0; var13 < 8; var13++) {
         lightState[var13] = new GlStateManager.BooleanState(16384 + var13);
      }

      for (int var14 = 0; var14 < textureState.length; var14++) {
         textureState[var14] = new GlStateManager.TextureState();
      }
   }

   static class AlphaState {
      public GlStateManager.BooleanState alphaTest = new GlStateManager.BooleanState(3008);
      public int func = 519;
      public float ref = -1.0F;

      private AlphaState() {
      }
   }

   static class BlendState {
      public GlStateManager.BooleanState blend = new GlStateManager.BooleanState(3042);
      public int srcFactor = 1;
      public int dstFactor = 0;
      public int srcFactorAlpha = 1;
      public int dstFactorAlpha = 0;

      private BlendState() {
      }
   }

   static class BooleanState {
      private final int capability;
      private boolean currentState = false;

      public BooleanState(int integer) {
         this.capability = integer;
      }

      public void setDisabled() {
         this.setState(false);
      }

      public void setEnabled() {
         this.setState(true);
      }

      public void setState(boolean boolean1) {
         if (boolean1 != this.currentState) {
            this.currentState = boolean1;
            if (boolean1) {
               GL11.glEnable(this.capability);
            } else {
               GL11.glDisable(this.capability);
            }
         }
      }
   }

   static class ClearState {
      public double depth = 1.0;
      public GlStateManager.Color color = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
      public int field_179204_c = 0;

      private ClearState() {
      }
   }

   static class Color {
      public float red = 1.0F;
      public float green = 1.0F;
      public float blue = 1.0F;
      public float alpha = 1.0F;

      public Color() {
      }

      public Color(float float1, float float2, float float3, float float4) {
         this.red = float1;
         this.green = float2;
         this.blue = float3;
         this.alpha = float4;
      }
   }

   static class ColorLogicState {
      public GlStateManager.BooleanState colorLogicOp = new GlStateManager.BooleanState(3058);
      public int opcode = 5379;

      private ColorLogicState() {
      }
   }

   static class ColorMask {
      public boolean red = true;
      public boolean green = true;
      public boolean blue = true;
      public boolean alpha = true;

      private ColorMask() {
      }
   }

   static class ColorMaterialState {
      public GlStateManager.BooleanState colorMaterial = new GlStateManager.BooleanState(2903);
      public int face = 1032;
      public int mode = 5634;

      private ColorMaterialState() {
      }
   }

   static class CullState {
      public GlStateManager.BooleanState cullFace = new GlStateManager.BooleanState(2884);
      public int mode = 1029;

      private CullState() {
      }
   }

   static class DepthState {
      public GlStateManager.BooleanState depthTest = new GlStateManager.BooleanState(2929);
      public boolean maskEnabled = true;
      public int depthFunc = 513;

      private DepthState() {
      }
   }

   static class FogState {
      public GlStateManager.BooleanState fog = new GlStateManager.BooleanState(2912);
      public int mode = 2048;
      public float density = 1.0F;
      public float start = 0.0F;
      public float end = 1.0F;

      private FogState() {
      }
   }

   static class PolygonOffsetState {
      public GlStateManager.BooleanState polygonOffsetFill = new GlStateManager.BooleanState(32823);
      public GlStateManager.BooleanState polygonOffsetLine = new GlStateManager.BooleanState(10754);
      public float factor = 0.0F;
      public float units = 0.0F;

      private PolygonOffsetState() {
      }
   }

   static class StencilFunc {
      public int field_179081_a = 519;
      public int field_179079_b = 0;
      public int field_179080_c = -1;

      private StencilFunc() {
      }
   }

   static class StencilState {
      public GlStateManager.StencilFunc field_179078_a = new GlStateManager.StencilFunc();
      public int field_179076_b = -1;
      public int field_179077_c = 7680;
      public int field_179074_d = 7680;
      public int field_179075_e = 7680;

      private StencilState() {
      }
   }

   public enum TexGen {
      S,
      T,
      R,
      Q;

      private static GlStateManager.TexGen[] Method506() {
         return new GlStateManager.TexGen[]{S, T, R, Q};
      }
   }

   static class TexGenCoord {
      public GlStateManager.BooleanState textureGen;
      public int coord;
      public int param = -1;

      public TexGenCoord(int integer1, int integer2) {
         this.coord = integer1;
         this.textureGen = new GlStateManager.BooleanState(integer2);
      }
   }

   static class TexGenState {
      public GlStateManager.TexGenCoord s = new GlStateManager.TexGenCoord(8192, 3168);
      public GlStateManager.TexGenCoord t = new GlStateManager.TexGenCoord(8193, 3169);
      public GlStateManager.TexGenCoord r = new GlStateManager.TexGenCoord(8194, 3170);
      public GlStateManager.TexGenCoord q = new GlStateManager.TexGenCoord(8195, 3171);

      private TexGenState() {
      }
   }

   static class TextureState {
      public GlStateManager.BooleanState texture2DState = new GlStateManager.BooleanState(3553);
      public int textureName = 0;

      private TextureState() {
      }
   }
}
