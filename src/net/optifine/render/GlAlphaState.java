package net.optifine.render;

public class GlAlphaState {
   private boolean enabled;
   private int func;
   private float ref;

   public GlAlphaState() {
      this(false, 519, 0.0F);
   }

   public GlAlphaState(boolean boolean1) {
      this(boolean1, 519, 0.0F);
   }

   public GlAlphaState(boolean boolean1, int integer, float float3) {
      this.enabled = boolean1;
      this.func = integer;
      this.ref = float3;
   }

   public void setState(boolean boolean1, int integer, float float3) {
      this.enabled = boolean1;
      this.func = integer;
      this.ref = float3;
   }

   public void setState(GlAlphaState glAlphaState) {
      this.enabled = glAlphaState.enabled;
      this.func = glAlphaState.func;
      this.ref = glAlphaState.ref;
   }

   public void setFuncRef(int integer, float float2) {
      this.func = integer;
      this.ref = float2;
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

   public boolean isEnabled() {
      return this.enabled;
   }

   public int getFunc() {
      return this.func;
   }

   public float getRef() {
      return this.ref;
   }

   @Override
   public String toString() {
      float var3 = this.ref;
      int var4 = this.func;
      boolean var5 = this.enabled;
      return "enabled: " + var5 + ", func: " + var4 + ", ref: " + var3;
   }
}
