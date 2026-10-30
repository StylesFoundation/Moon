package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraftforge.client.model.ISmartItemModel;
import net.optifine.CustomItems;

public class ItemModelMesher {
   private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
   private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
   private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
   private final ModelManager modelManager;

   public ItemModelMesher(ModelManager modelManager) {
      this.modelManager = modelManager;
   }

   public TextureAtlasSprite getParticleIcon(Item item) {
      return this.getParticleIcon(item, 0);
   }

   public TextureAtlasSprite getParticleIcon(Item item, int integer) {
      return this.getItemModel(new ItemStack(item, 1, integer)).getParticleTexture();
   }

   public IBakedModel getItemModel(ItemStack itemStack) {
      Item var2 = itemStack.getItem();
      IBakedModel var3 = this.getItemModel(var2, this.getMetadata(itemStack));
      if (var3 == null) {
         ItemMeshDefinition var4 = this.shapers.get(var2);
         if (var4 != null) {
            var3 = this.modelManager.getModel(var4.getModelLocation(itemStack));
         }
      }

      if (var3 instanceof ISmartItemModel) {
         var3 = ((ISmartItemModel)var3).handleItemState(itemStack);
      }

      if (var3 == null) {
         var3 = this.modelManager.getMissingModel();
      }

      if (Config.isCustomItems()) {
         var3 = CustomItems.getCustomItemModel(itemStack, var3, null, true);
      }

      return var3;
   }

   protected int getMetadata(ItemStack itemStack) {
      return itemStack.isItemStackDamageable() ? 0 : itemStack.getMetadata();
   }

   protected IBakedModel getItemModel(Item item, int integer) {
      return this.simpleShapesCache.get(this.getIndex(item, integer));
   }

   private int getIndex(Item item, int integer) {
      return Item.getIdFromItem(item) << 16 | integer;
   }

   public void register(Item item, int integer, ModelResourceLocation modelResourceLocation) {
      this.simpleShapes.put(this.getIndex(item, integer), modelResourceLocation);
      this.simpleShapesCache.put(this.getIndex(item, integer), this.modelManager.getModel(modelResourceLocation));
   }

   public void register(Item item, ItemMeshDefinition itemMeshDefinition) {
      this.shapers.put(item, itemMeshDefinition);
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public void rebuildCache() {
      this.simpleShapesCache.clear();

      for (Entry var2 : this.simpleShapes.entrySet()) {
         this.simpleShapesCache.put((Integer)var2.getKey(), this.modelManager.getModel((ModelResourceLocation)var2.getValue()));
      }
   }
}
