package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityEnderChest;

public class ModelAdapterEnderChest extends ModelAdapter {
   public ModelAdapterEnderChest() {
      super(TileEntityEnderChest.class, "ender_chest", 0.0F);
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
      TileEntitySpecialRenderer var7 = var6.getSpecialRendererByClass(TileEntityEnderChest.class);
      if (!(var7 instanceof TileEntityEnderChestRenderer)) {
         return null;
      }

      if (var7.getEntityClass() == null) {
         var7 = new TileEntityEnderChestRenderer();
         var7.setRendererDispatcher(var6);
      }

      ((TileEntityEnderChestRenderer)var7).g = (ModelChest)modelBase;
      return var7;
   }
}
