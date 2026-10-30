package net.optifine.entity.model.anim;

public class ModelUpdater {
   private final ModelVariableUpdater[] modelVariableUpdaters;

   public ModelUpdater(ModelVariableUpdater[] arr) {
      this.modelVariableUpdaters = arr;
   }

   public void update() {
      ModelVariableUpdater[] var4 = this.modelVariableUpdaters;
      int var5 = var4.length;

      for (int var6 = 0; var6 < var5; var6++) {
         var4[var6].update();
      }
   }

   public boolean initialize(IModelResolver iModelResolver) {
      for (ModelVariableUpdater var8 : this.modelVariableUpdaters) {
         if (!var8.initialize(iModelResolver)) {
            return false;
         }
      }

      return true;
   }
}
