package net.optifine.shaders;

import java.nio.IntBuffer;
import java.util.Arrays;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.config.RenderScale;

public class Program {
   private final int index;
   private final String name;
   private final ProgramStage programStage;
   private final Program programBackup;
   private GlAlphaState alphaState;
   private GlBlendState blendState;
   private RenderScale renderScale;
   private final Boolean[] buffersFlip = new Boolean[8];
   private int id;
   private int ref;
   private String drawBufSettings;
   private IntBuffer drawBuffers;
   private IntBuffer drawBuffersBuffer;
   private int compositeMipmapSetting;
   private int countInstances;
   private final boolean[] toggleColorTextures = new boolean[8];

   public Program(int integer, String string, ProgramStage programStage, Program program) {
      this.index = integer;
      this.name = string;
      this.programStage = programStage;
      this.programBackup = program;
   }

   public Program(int integer, String string, ProgramStage programStage, boolean boolean4) {
      this.index = integer;
      this.name = string;
      this.programStage = programStage;
      this.programBackup = boolean4 ? this : null;
   }

   public void resetProperties() {
      this.alphaState = null;
      this.blendState = null;
      this.renderScale = null;
      Arrays.fill(this.buffersFlip, null);
   }

   public void resetId() {
      this.id = 0;
      this.ref = 0;
   }

   public void resetConfiguration() {
      this.drawBufSettings = null;
      this.compositeMipmapSetting = 0;
      this.countInstances = 0;
      if (this.drawBuffersBuffer == null) {
         this.drawBuffersBuffer = Shaders.nextIntBuffer(8);
      }
   }

   public void copyFrom(Program program) {
      this.id = program.getId();
      this.alphaState = program.getAlphaState();
      this.blendState = program.getBlendState();
      this.renderScale = program.getRenderScale();
      System.arraycopy(program.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
      this.drawBufSettings = program.getDrawBufSettings();
      this.drawBuffers = program.getDrawBuffers();
      this.compositeMipmapSetting = program.getCompositeMipmapSetting();
      this.countInstances = program.getCountInstances();
      System.arraycopy(program.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
   }

   public int getIndex() {
      return this.index;
   }

   public String getName() {
      return this.name;
   }

   public ProgramStage getProgramStage() {
      return this.programStage;
   }

   public Program getProgramBackup() {
      return this.programBackup;
   }

   public int getId() {
      return this.id;
   }

   public int getRef() {
      return this.ref;
   }

   public String getDrawBufSettings() {
      return this.drawBufSettings;
   }

   public IntBuffer getDrawBuffers() {
      return this.drawBuffers;
   }

   public IntBuffer getDrawBuffersBuffer() {
      return this.drawBuffersBuffer;
   }

   public int getCompositeMipmapSetting() {
      return this.compositeMipmapSetting;
   }

   public int getCountInstances() {
      return this.countInstances;
   }

   public GlAlphaState getAlphaState() {
      return this.alphaState;
   }

   public GlBlendState getBlendState() {
      return this.blendState;
   }

   public RenderScale getRenderScale() {
      return this.renderScale;
   }

   public Boolean[] getBuffersFlip() {
      return this.buffersFlip;
   }

   public boolean[] getToggleColorTextures() {
      return this.toggleColorTextures;
   }

   public void setId(int integer) {
      this.id = integer;
   }

   public void setRef(int integer) {
      this.ref = integer;
   }

   public void setDrawBufSettings(String string) {
      this.drawBufSettings = string;
   }

   public void setDrawBuffers(IntBuffer intBuffer) {
      this.drawBuffers = intBuffer;
   }

   public void setCompositeMipmapSetting(int integer) {
      this.compositeMipmapSetting = integer;
   }

   public void setCountInstances(int integer) {
      this.countInstances = integer;
   }

   public void setAlphaState(GlAlphaState glAlphaState) {
      this.alphaState = glAlphaState;
   }

   public void setBlendState(GlBlendState glBlendState) {
      this.blendState = glBlendState;
   }

   public void setRenderScale(RenderScale renderScale) {
      this.renderScale = renderScale;
   }

   public String getRealProgramName() {
      if (this.id == 0) {
         return "none";
      }

      Program var4;
      for (var4 = this; var4.getRef() != this.id; var4 = var4.getProgramBackup()) {
         if (var4.getProgramBackup() == null || var4.getProgramBackup() == var4) {
            return "unknown";
         }
      }

      return var4.getName();
   }

   @Override
   public String toString() {
      String var10000 = this.name;
      int var10001 = this.id;
      int var10002 = this.ref;
      String var3 = this.getRealProgramName();
      int var4 = var10002;
      int var5 = var10001;
      String var6 = var10000;
      return "name: " + var6 + ", id: " + var5 + ", ref: " + var4 + ", real: " + var3;
   }
}
