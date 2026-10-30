package com.moon.module.sub;

import com.moon.Moon;
import com.moon.module.Module;
import net.minecraft.client.Minecraft;

public class SubModule<T extends Module> {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   private T parent;
   private final Class<? extends Module> parentClass;

   public SubModule(Class<? extends Module> parentClass) {
      this.parentClass = parentClass;
   }

   public T getParent() {
      this.resolveParent();
      return this.parent;
   }

   private void resolveParent() {
      if (this.parent == null) {
         this.parent = Moon.getInstance().getModuleManager().getModule(this.parentClass);
      }
   }

   public void onEnable() {
   }

   public void onDisable() {
   }
}
