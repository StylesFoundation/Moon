package net.optifine.entity.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomEntityModels {
   private static boolean active = false;
   private static Map<Class, Render> originalEntityRenderMap = null;
   private static Map<Class, TileEntitySpecialRenderer> originalTileEntityRenderMap = null;

   public static void update() {
      Map var3 = getEntityRenderMap();
      Map var4 = getTileEntityRenderMap();
      if (var3 == null) {
         Config.Method252("Entity render map not found, custom entity models are DISABLED.");
      } else if (var4 == null) {
         Config.Method252("Tile entity render map not found, custom entity models are DISABLED.");
      } else {
         active = false;
         var3.clear();
         var4.clear();
         var3.putAll(originalEntityRenderMap);
         var4.putAll(originalTileEntityRenderMap);
         if (Config.isCustomEntityModels()) {
            for (ResourceLocation var9 : getModelLocations()) {
               Config.Method251("CustomEntityModel: " + var9.getResourcePath());
               IEntityRenderer var10 = parseEntityRender(var9);
               if (var10 != null) {
                  Class var11 = var10.getEntityClass();
                  if (var11 != null) {
                     if (var10 instanceof Render) {
                        var3.put(var11, (Render)var10);
                     } else if (var10 instanceof TileEntitySpecialRenderer) {
                        var4.put(var11, (TileEntitySpecialRenderer)var10);
                     } else {
                        Config.Method252("Unknown renderer type: " + var10.getClass().getName());
                     }

                     active = true;
                  }
               }
            }
         }
      }
   }

   private static Map<Class, Render> getEntityRenderMap() {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      Map var4 = var3.getEntityRenderMap();
      if (var4 == null) {
         return null;
      }

      if (originalEntityRenderMap == null) {
         originalEntityRenderMap = new HashMap<>(var4);
      }

      return var4;
   }

   private static Map<Class, TileEntitySpecialRenderer> getTileEntityRenderMap() {
      Map var3 = TileEntityRendererDispatcher.instance.mapSpecialRenderers;
      if (originalTileEntityRenderMap == null) {
         originalTileEntityRenderMap = new HashMap<>(var3);
      }

      return var3;
   }

   private static ResourceLocation[] getModelLocations() {
      String var3 = "optifine/cem/";
      String var4 = ".jem";
      ArrayList var5 = new ArrayList();

      for (String var10 : CustomModelRegistry.getModelNames()) {
         String var11 = var3 + var10 + var4;
         ResourceLocation var12 = new ResourceLocation(var11);
         if (Config.hasResource(var12)) {
            var5.add(var12);
         }
      }

      return (ResourceLocation[])var5.toArray(new ResourceLocation[0]);
   }

   private static IEntityRenderer parseEntityRender(ResourceLocation resourceLocation) {
      try {
         return parseEntityRender(CustomEntityModelParser.loadJson(resourceLocation), resourceLocation.getResourcePath());
      } catch (IOException | JsonParseException var4) {
         Config.Method253(var4.getClass().getName() + ": " + var4.getMessage());
         return null;
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }

   private static IEntityRenderer parseEntityRender(JsonObject jsonObject, String string) {
      CustomEntityRenderer var5 = CustomEntityModelParser.parseEntityRender(jsonObject, string);
      String var6 = var5.getName();
      ModelAdapter var7 = CustomModelRegistry.getModelAdapter(var6);
      checkNull(var7, "Entity not found: " + var6);
      Class var8 = var7.getEntityClass();
      checkNull(var8, "Entity class not found: " + var6);
      IEntityRenderer var9 = makeEntityRender(var7, var5);
      if (var9 == null) {
         return null;
      }

      var9.setEntityClass(var8);
      return var9;
   }

   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer customEntityRenderer) {
      ResourceLocation var5 = customEntityRenderer.getTextureLocation();
      CustomModelRenderer[] var6 = customEntityRenderer.getCustomModelRenderers();
      float var7 = customEntityRenderer.getShadowSize();
      if (var7 < 0.0F) {
         var7 = modelAdapter.getShadowSize();
      }

      ModelBase var8 = modelAdapter.makeModel();
      if (var8 == null) {
         return null;
      }

      ModelResolver var9 = new ModelResolver(modelAdapter, var8, var6);
      if (!modifyModel(modelAdapter, var8, var6, var9)) {
         return null;
      }

      IEntityRenderer var10 = modelAdapter.makeEntityRender(var8, var7);
      if (var10 == null) {
         throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
      }

      if (var5 != null) {
         var10.setLocationTextureCustom(var5);
      }

      return var10;
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase modelBase, CustomModelRenderer[] arr, ModelResolver modelResolver) {
      for (CustomModelRenderer var10 : arr) {
         if (!modifyModel(modelAdapter, modelBase, var10, modelResolver)) {
            return false;
         }
      }

      return true;
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase modelBase, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
      String var7 = customModelRenderer.getModelPart();
      ModelRenderer var8 = modelAdapter.getModelRenderer(modelBase, var7);
      if (var8 == null) {
         Config.Method252("Model part not found: " + var7 + ", model: " + modelBase);
         return false;
      }

      if (!customModelRenderer.isAttach()) {
         if (var8.cubeList != null) {
            var8.cubeList.clear();
         }

         if (var8.spriteList != null) {
            var8.spriteList.clear();
         }

         if (var8.childModels != null) {
            ModelRenderer[] var9 = modelAdapter.getModelRenderers(modelBase);
            Set var10 = Collections.newSetFromMap(new IdentityHashMap());
            var10.addAll(Arrays.asList(var9));
            Iterator var12 = var8.childModels.iterator();

            while (var12.hasNext()) {
               ModelRenderer var13 = (ModelRenderer)var12.next();
               if (!var10.contains(var13)) {
                  var12.remove();
               }
            }
         }
      }

      var8.addChild(customModelRenderer.getModelRenderer());
      ModelUpdater var14 = customModelRenderer.getModelUpdater();
      if (var14 != null) {
         modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
         modelResolver.setPartModelRenderer(var8);
         if (!var14.initialize(modelResolver)) {
            return false;
         }

         customModelRenderer.getModelRenderer().setModelUpdater(var14);
      }

      return true;
   }

   private static void checkNull(Object object, String string) {
      if (object == null) {
         throw new JsonParseException(string);
      }
   }

   public static boolean isActive() {
      return active;
   }
}
