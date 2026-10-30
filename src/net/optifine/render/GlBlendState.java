package net.optifine.render;

public class GlBlendState {
   private boolean enabled;
   private int srcFactor;
   private int dstFactor;
   private int srcFactorAlpha;
   private int dstFactorAlpha;

   public GlBlendState() {
      this(false, 1, 0);
   }

   public GlBlendState(boolean boolean1) {
      this(boolean1, 1, 0);
   }

   public GlBlendState(boolean boolean1, int integer2, int integer3, int integer4, int integer5) {
      this.enabled = boolean1;
      this.srcFactor = integer2;
      this.dstFactor = integer3;
      this.srcFactorAlpha = integer4;
      this.dstFactorAlpha = integer5;
   }

   public GlBlendState(boolean boolean1, int integer2, int integer3) {
      this(boolean1, integer2, integer3, integer2, integer3);
   }

   public void setState(boolean boolean1, int integer2, int integer3, int integer4, int integer5) {
      this.enabled = boolean1;
      this.srcFactor = integer2;
      this.dstFactor = integer3;
      this.srcFactorAlpha = integer4;
      this.dstFactorAlpha = integer5;
   }

   public void setState(GlBlendState glBlendState) {
      this.enabled = glBlendState.enabled;
      this.srcFactor = glBlendState.srcFactor;
      this.dstFactor = glBlendState.dstFactor;
      this.srcFactorAlpha = glBlendState.srcFactorAlpha;
      this.dstFactorAlpha = glBlendState.dstFactorAlpha;
   }

   public void setEnabled(boolean boolean1) {
      this.enabled = boolean1;
   }

   public void setEnabled() {
      this.enabled = true;
   }

   public void setDisabled() {
      this.enabled = false;
   }

   public void setFactors(int integer1, int integer2) {
      this.srcFactor = integer1;
      this.dstFactor = integer2;
      this.srcFactorAlpha = integer1;
      this.dstFactorAlpha = integer2;
   }

   public void setFactors(int integer1, int integer2, int integer3, int integer4) {
      this.srcFactor = integer1;
      this.dstFactor = integer2;
      this.srcFactorAlpha = integer3;
      this.dstFactorAlpha = integer4;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public int Method226() {
      return this.srcFactor;
   }

   public int Method227() {
      return this.dstFactor;
   }

   public int Method228() {
      return this.srcFactorAlpha;
   }

   public int Method229() {
      return this.dstFactorAlpha;
   }

   public boolean isSeparate() {
      return this.srcFactor != this.srcFactorAlpha || this.dstFactor != this.dstFactorAlpha;
   }

   @Override
   public String toString() {
      int var3 = this.dstFactorAlpha;
      int var4 = this.srcFactorAlpha;
      int var5 = this.dstFactor;
      int var6 = this.srcFactor;
      boolean var7 = this.enabled;
      return "enabled: " + var7 + ", src: " + var6 + ", dst: " + var5 + ", srcAlpha: " + var4 + ", dstAlpha: " + var3;
   }
}
