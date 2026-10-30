package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public interface IEntityRenderer {
   Class getEntityClass();

   void setEntityClass(Class class1);

   ResourceLocation getLocationTextureCustom();

   void setLocationTextureCustom(ResourceLocation resourceLocation);
}
