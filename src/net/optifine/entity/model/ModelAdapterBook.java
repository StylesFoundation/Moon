package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;

public class ModelAdapterBook extends ModelAdapter {
   public ModelAdapterBook() {
      super(TileEntityEnchantmentTable.class, "book", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelBook();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelBook var6) {
         return string.equals("cover_right")
            ? var6.coverRight
            : (
               string.equals("cover_left")
                  ? var6.coverLeft
                  : (
                     string.equals("pages_right")
                        ? var6.pagesRight
                        : (
                           string.equals("pages_left")
                              ? var6.pagesLeft
                              : (
                                 string.equals("flipping_page_right")
                                    ? var6.flippingPageRight
                                    : (string.equals("flipping_page_left") ? var6.flippingPageLeft : (string.equals("book_spine") ? var6.bookSpine : null))
                              )
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      TileEntityRendererDispatcher var6 = TileEntityRendererDispatcher.instance;
      TileEntitySpecialRenderer var7 = var6.getSpecialRendererByClass(TileEntityEnchantmentTable.class);
      if (!(var7 instanceof TileEntityEnchantmentTableRenderer)) {
         return null;
      }

      if (var7.getEntityClass() == null) {
         var7 = new TileEntityEnchantmentTableRenderer();
         var7.setRendererDispatcher(var6);
      }

      ((TileEntityEnchantmentTableRenderer)var7).g = (ModelBook)modelBase;
      return var7;
   }
}
