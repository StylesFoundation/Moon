package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityChest;

public class ModelAdapterChest extends ModelAdapter {
   public ModelAdapterChest() {
      super(TileEntityChest.class, "chest", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelChest();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelChest var6) {
         return string.equals("lid") ? var6.chestLid : (string.equals("base") ? var6.chestBelow : (string.equals("knob") ? var6.chestKnob : null));
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"lid", "base", "knob"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      TileEntityRendererDispatcher var6 = TileEntityRendererDispatcher.instance;
      TileEntitySpecialRenderer var7 = var6.getSpecialRendererByClass(TileEntityChest.class);
      if (!(var7 instanceof TileEntityChestRenderer)) {
         return null;
      }

      if (var7.getEntityClass() == null) {
         var7 = new TileEntityChestRenderer();
         var7.setRendererDispatcher(var6);
      }

      ((TileEntityChestRenderer)var7).l = (ModelChest)modelBase;
      return var7;
   }
}
