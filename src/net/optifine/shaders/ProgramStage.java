package net.optifine.shaders;

public enum ProgramStage {
   NONE(""),
   SHADOW("shadow"),
   GBUFFERS("gbuffers"),
   DEFERRED("deferred"),
   COMPOSITE("composite");

   private final String name;

   ProgramStage(String string3) {
      this.name = string3;
   }

   public String getName() {
      return this.name;
   }
}
